//package org.qiot.covid19.datahub.registration.persistence;
//
//import java.util.Date;
//
//import javax.annotation.PostConstruct;
//import javax.enterprise.context.ApplicationScoped;
//import javax.enterprise.event.Observes;
//import javax.inject.Inject;
//
//import org.bson.Document;
//import org.bson.codecs.configuration.CodecProvider;
//import org.bson.codecs.configuration.CodecRegistries;
//import org.bson.codecs.configuration.CodecRegistry;
//import org.bson.codecs.pojo.PojoCodecProvider;
//import org.qiot.covid19.datahub.registration.domain.action.MeasurementStationAction;
//import org.qiot.covid19.datahub.registration.domain.station.MeasurementStation;
//import org.slf4j.Logger;
//
//import com.mongodb.MongoClientSettings;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.IndexOptions;
//import com.mongodb.client.model.Indexes;
//
//import io.quarkus.runtime.StartupEvent;
//
//@ApplicationScoped
//public class MeasurementStationActionRepository {
//
//    @Inject
//    Logger LOGGER;
//    private  final String DATABASE_NAME = "qiot";
//
//    private  final String COLLECTION_NAME = "measurementstationaction";
//
//    @Inject
//    MongoClient mongoClient;
//
//    MongoDatabase qiotDatabase;
//    MongoCollection<MeasurementStationAction> msaCollection = null;
//    CodecProvider pojoCodecProvider;
//    CodecRegistry pojoCodecRegistry;
//
//    void onStart(@Observes StartupEvent ev) {
//    }
//
//    @PostConstruct
//    void init() {
//        qiotDatabase = mongoClient.getDatabase(DATABASE_NAME);
//        try {
//            qiotDatabase.createCollection(COLLECTION_NAME);
//        } catch (Exception e) {
//            LOGGER.info("Collection {} already exists", COLLECTION_NAME);
//        }
//        msaCollection = qiotDatabase.getCollection(COLLECTION_NAME,
//                MeasurementStationAction.class);
//        /*
//         * ensure indexes exist
//         */
//        ensureIndexes();
//
//        // Create a CodecRegistry containing the PojoCodecProvider instance.
//        pojoCodecProvider = PojoCodecProvider.builder()
//                .register("org.qiot.covid19.datahub.registration.domain.action")
//                .automatic(true).build();
//        pojoCodecRegistry = CodecRegistries.fromRegistries(
//                MongoClientSettings.getDefaultCodecRegistry(),
//                CodecRegistries.fromProviders(pojoCodecProvider));
//        msaCollection = msaCollection.withCodecRegistry(pojoCodecRegistry);
//    }
//
//    private void ensureIndexes() {
//        IndexOptions uniqueIndexOption = new IndexOptions().unique(true);
//        msaCollection.createIndex(Indexes.ascending("stationId", "time"),
//                uniqueIndexOption);
//    }
//
//    public void save(MeasurementStationAction msa) {
//        msaCollection.insertOne(msa);
//    }
//
//    public MeasurementStationAction getLastActionByStationId(int stationId) {
//        return null;
//    }
//
//    public MeasurementStationAction getActionsStationId(int stationId) {
//        return null;
//    }
//
//}
