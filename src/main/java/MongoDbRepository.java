import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.Document.parse;

public class MongoDbRepository implements Repository {

    private static Repository instance = null;
    private static MongoDatabase db;

    private MongoDbRepository() {
        MongoClient mongoClient = new MongoClient("mongo");
        db = mongoClient.getDatabase("database");
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new MongoDbRepository();
        }
        return instance;
    }

    @Override
    public void add(String event) {
        MongoCollection<Document> view = db.getCollection("view");
        view.insertOne(parse(event));
    }

    @Override
    public List<String> get() {
        FindIterable<Document> documents = db.getCollection("view").find();
        List<String> eventi = new ArrayList<>();
        for (Document document : documents) {
            eventi.add(document.toJson());
        }
        return eventi;
    }

    @Override
    public String lookingFor(String title) {
        return db.getCollection("view")
                .find(eq("title", title)).first()
                .toJson();
    }
}
