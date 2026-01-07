package db;

import com.mongodb.client.*;
import org.bson.Document;

public class MongoConnection {

    public static final MongoClient client =
            MongoClients.create("mongodb://localhost:27017");

    public static final MongoDatabase database =
            client.getDatabase("votingdb");

    public static final MongoCollection<Document> voters =
            database.getCollection("voters");

    public static final MongoCollection<Document> parties =
            database.getCollection("parties");

    public static final MongoCollection<Document> candidates =
            database.getCollection("candidates");
}
