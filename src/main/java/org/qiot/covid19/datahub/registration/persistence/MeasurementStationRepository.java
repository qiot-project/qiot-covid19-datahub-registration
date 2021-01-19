package org.qiot.covid19.datahub.registration.persistence;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.qiot.covid19.datahub.registration.domain.station.Counters;
import org.qiot.covid19.datahub.registration.domain.station.MeasurementStation;
import org.slf4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class MeasurementStationRepository {

    @ConfigProperty(name = "qiot.database.name")
    String DATABASE_NAME;

    @ConfigProperty(name = "qiot.collection.measurementstation.name")
    String MS_COLLECTION_NAME;
    @ConfigProperty(name = "qiot.collection.counters.name")
    String COUNTERS_COLLECTION_NAME;

    @Inject
    Logger LOGGER;

    @Inject
    MongoClient mongoClient;

    MongoDatabase qiotDatabase;
    MongoCollection<Counters> countersCollection = null;
    MongoCollection<MeasurementStation> msCollection = null;
    CodecProvider pojoCodecProvider;
    CodecRegistry pojoCodecRegistry;

    void onStart(@Observes StartupEvent ev) {
    }

    @PostConstruct
    void init() {

        /*
         * Instantiate COUNTERS collection
         */
        qiotDatabase = mongoClient.getDatabase(DATABASE_NAME);
        try {
            qiotDatabase.createCollection(COUNTERS_COLLECTION_NAME);
        } catch (Exception e) {
            LOGGER.info("Collection {} already exists",
                    COUNTERS_COLLECTION_NAME);
        }
        countersCollection = qiotDatabase
                .getCollection(COUNTERS_COLLECTION_NAME, Counters.class);
        if (countersCollection.countDocuments() == 0) {
            Counters c=new Counters();
            c.id="userid";
            c.seq=0;
            countersCollection.insertOne(c);
        }

        /*
         * Instantiate MEASUREMENT STATION collection
         */
        qiotDatabase = mongoClient.getDatabase(DATABASE_NAME);
        try {
            qiotDatabase.createCollection(MS_COLLECTION_NAME);
        } catch (Exception e) {
            LOGGER.info("Collection {} already exists", MS_COLLECTION_NAME);
        }
        msCollection = qiotDatabase.getCollection(MS_COLLECTION_NAME,
                MeasurementStation.class);
        /*
         * ensure indexes exist
         */
        ensureIndexes();

        // Create a CodecRegistry containing the PojoCodecProvider instance.
        pojoCodecProvider = PojoCodecProvider.builder()
                .register("org.qiot.covid19.datahub.registration.domain.station")
                .automatic(true).build();
        pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(pojoCodecProvider));
        msCollection = msCollection.withCodecRegistry(pojoCodecRegistry);
    }

    private void ensureIndexes() {
        IndexOptions indexOptions = new IndexOptions().unique(true);
        msCollection.createIndex(Indexes.ascending("serial"), indexOptions);
        msCollection.createIndex(Indexes.geo2dsphere("location"));
    }

    Object getNextSequence(String name) {
        MongoCollection<Document> countersCollection = mongoClient
                .getDatabase(DATABASE_NAME).getCollection("counters");
        BasicDBObject find = new BasicDBObject();
        find.put("_id", name);
        BasicDBObject update = new BasicDBObject();
        update.put("$inc", new BasicDBObject("seq", 1));
        Document obj = countersCollection.findOneAndUpdate(find, update);
        return obj.get("seq");
    }

    public int save(String serial, String name, double longitude,
            double latitude) {
        MeasurementStation ms = new MeasurementStation();
        ms.id = (int) getNextSequence("userid");
        ms.serial = serial;
        ms.name = name;
        ms.location = new Point(
                new Position(Arrays.asList(longitude, latitude)));
        ms.active = true;
        msCollection.insertOne(ms);
        return ms.id;
    }

    public MeasurementStation findById(int stationId) {
        LOGGER.debug("Searching for Measurement Station with ID {}", stationId);
        MeasurementStation ms = msCollection.find(Filters.eq("_id", stationId))
                .first();
        if (ms == null)
            return null;
        LOGGER.debug("Found MeasurementStation {}", ms);
        return ms;
    }

    public MeasurementStation findBySerial(String serial) {
        LOGGER.debug("Searching for Measurement Station with serialID {}",
                serial);
        MeasurementStation ms = msCollection.find(Filters.eq("serial", serial))
                .first();
        if (ms == null)
            return null;
        LOGGER.debug("Found MeasurementStation {}", ms);
        return ms;
    }

    public Set<MeasurementStation> findAll() {
        LOGGER.debug("Searching for all Measurement Stations");
        Set<MeasurementStation> mss = new TreeSet<>();
        for (MeasurementStation ms : msCollection.find()) {
            mss.add(ms);
        }
        LOGGER.info(Arrays.toString(mss.toArray(new MeasurementStation[0])));
        return mss;
    }

    //
    // private MeasurementStation mongoDocumentToBean(Document msDoc) {
    // MeasurementStation ms = new MeasurementStation();
    // ms.setId(msDoc.getInteger("_id"));
    // ms.setSerial(msDoc.getString("serial"));
    // Document location = msDoc.get("location", Document.class);
    // List<Double> coordinates = (List<Double>) location.get("coordinates");
    // ms.setLongitude(coordinates.get(0));
    // ms.setLatitude(coordinates.get(1));
    // ms.setActive(msDoc.getBoolean("active"));
    // return ms;
    // }
    //
    // public int save(String serial, double longitude, double latitude) {
    // Object stationId = null;
    // Document document = null;
    //
    // document = new Document();
    // stationId = getNextSequence("userid");
    // document.put("_id", stationId);
    // document.put("serial", serial);
    // document.put("location", new Point(new Position(longitude, latitude)));
    // document.put("active", true);
    // measurementStationCollection.insertOne(document);
    //
    // return (Integer) stationId;
    // }
    //
    public void setActive(int id) {
        // update("active='true' where id=?1", id);
        msCollection.updateOne(Filters.eq("_id", id),
                new Document("$set", new Document("active", true)));
    }

    public void setInactive(int id) {
        // MeasurementStation ms=findById(id);
        // ms.active=false;
        msCollection.updateOne(Filters.eq("_id", id),
                new Document("$set", new Document("active", false)));
    }
}
