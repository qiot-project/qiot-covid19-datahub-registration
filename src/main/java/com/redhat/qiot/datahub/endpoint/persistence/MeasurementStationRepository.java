package com.redhat.qiot.datahub.endpoint.persistence;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import com.redhat.qiot.datahub.endpoint.domain.station.MeasurementStation;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class MeasurementStationRepository {

    private static final String DATABASE_NAME = "qiot";

    private static final String COLLECTION_NAME = "measurementstation";

    @Inject
    Logger LOGGER;

    @Inject
    MongoClient mongoClient;

    MongoDatabase qiotDatabase;
    MongoCollection<MeasurementStation> msCollection = null;
    CodecProvider pojoCodecProvider;
    CodecRegistry pojoCodecRegistry;

    void onStart(@Observes StartupEvent ev) {
    }

    @PostConstruct
    void init() {
        qiotDatabase = mongoClient.getDatabase(DATABASE_NAME);
        try {
            qiotDatabase.createCollection(COLLECTION_NAME);
        } catch (Exception e) {
            LOGGER.info("Collection {} already exists", COLLECTION_NAME);
        }
        msCollection = qiotDatabase.getCollection(COLLECTION_NAME,
                MeasurementStation.class);
        /*
         * ensure indexes exist
         */
        ensureIndexes();

        // Create a CodecRegistry containing the PojoCodecProvider instance.
        pojoCodecProvider = PojoCodecProvider.builder()
                .register("com.redhat.qiot.datahub.endpoint.domain.station")
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

    public int save(String serial, double longitude, double latitude) {
        MeasurementStation ms = new MeasurementStation();
        ms.id = (int) getNextSequence("userid");
        ms.serial = serial;
        ms.location = new Point(
                new Position(Arrays.asList(longitude, latitude)));
        ms.active = true;
        msCollection.insertOne(ms);
        return ms.id;
    }

    public MeasurementStation findById(int stationId) {
        LOGGER.debug("Searching for Measurement Station with ID {}", stationId);
        MeasurementStation ms = msCollection.find(eq("_id", stationId)).first();
        if (ms == null)
            return null;
        LOGGER.debug("Found MeasurementStation {}", ms);
        return ms;
    }

    public MeasurementStation findBySerial(String serial) {
        LOGGER.debug("Searching for Measurement Station with serialID {}",
                serial);
        MeasurementStation ms = msCollection.find(eq("serial", serial)).first();
        if (ms == null)
            return null;
        LOGGER.debug("Found MeasurementStation {}", ms);
        return ms;
    }

    public List<MeasurementStation> findAll() {
        LOGGER.debug("Searching for all Measurement Stations");
        List<MeasurementStation> mss = new ArrayList<MeasurementStation>(
                (int) msCollection.countDocuments());
        for (MeasurementStation ms : msCollection.find()) {
            mss.add(ms);
        }
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
        msCollection.updateOne(eq("_id", id),
                new Document("$set", new Document("active", true)));
    }

    public void setInactive(int id) {
//        MeasurementStation ms=findById(id);
//        ms.active=false;
        msCollection.updateOne(eq("_id", id),
                new Document("$set", new Document("active", false)));
    }
}
