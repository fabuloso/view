import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BookRepository implements Repository{

    private static List<String> events = new ArrayList<>();
    private static BookRepository instance = null;
    private final static Logger LOG = LoggerFactory.getLogger(BookRepository.class);

    private BookRepository() {}

    public static BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    public void add(String event) {
        events.add(event);
    }

    public List<String> get() {
        return events;
    }

    public String lookingFor(String title) {
        for (String event : events) {
            String titlePattern = "\"title\":\""+title+"\"";
            LOG.info("Looking for: "+ title+ " with pattern: "+ titlePattern);
            if (event.contains(titlePattern))
                return event;
            }
        return "There is nothing here!";
    }


}