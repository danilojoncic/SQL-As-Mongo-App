import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.CustomMongoDatabase;
import database.settings.Settings;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import third_perspective.AdapterMDB;

import java.util.List;

public class ConnectionTest {
    @Test
    public void connectTest(){
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("hr");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("locations");
        Assertions.assertNotNull(mongoCollection);
        Assertions.assertTrue(mongoCollection.countDocuments() > 0);
        mongoClient.close();
    }
}
