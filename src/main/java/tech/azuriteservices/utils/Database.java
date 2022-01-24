package tech.azuriteservices.utils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import tech.azuriteservices.Globus;
import xyz.invisraidinq.rocketapi.api.database.mongo.RocketMongo;

public class Database {
    public MongoCollection<Document> mongoCollection;

    public Database(){

        String type = Globus.getINSTANCE().getConfig().getString("database");

        if (type == "mongo") {
            MongoDatabase mongoDatabase = new RocketMongo(
                    Globus.getINSTANCE().getConfig().getString("mongo.database"),
                    Globus.getINSTANCE().getConfig().getString("mongo.host"),
                    Globus.getINSTANCE().getConfig().getInt("mongo.port"),
                    Globus.getINSTANCE().getConfig().getBoolean("mongo.auth"),
                    Globus.getINSTANCE().getConfig().getString("mongo.username"),
                    Globus.getINSTANCE().getConfig().getString("mongo.password"),
                    Globus.getINSTANCE().getConfig().getString("mongo.database")
            ).connect();
            mongoCollection = mongoDatabase.getCollection("Globus");
            System.out.println("Connected");

        } else if (type == "mysql") {

        }
    }
}
