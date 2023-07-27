package third_perspective;

import org.bson.Document;

import java.util.List;

public interface AdaptateMDB {
    List<Document> generateMongoDBQuery(Query query);
}
