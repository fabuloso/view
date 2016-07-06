import com.spotify.apollo.Environment;
import com.spotify.apollo.RequestContext;
import com.spotify.apollo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.spotify.apollo.httpservice.HttpService.boot;
import static com.spotify.apollo.route.Route.sync;

public class View {

    private final static Logger LOG = LoggerFactory.getLogger(View.class);

    public static void main(String[] args) throws Exception {
        BookEventsConsumer demo = new BookEventsConsumer();
        boot(View::init, "view", args);
    }

    private static void init(Environment environment) {
        environment.routingEngine()
                .registerAutoRoute(sync("GET", "/first", View::first))
                .registerAutoRoute(sync("GET", "/findBy", View::findBy));
    }

    private static Response<String> findBy(RequestContext context) {
        Repository repo = MongoDbRepository.getInstance();
        Map<String, List<String>> parameters = context.request().parameters();
        String title = parameters.get("title").get(0);
        return Response.forPayload(repo.lookingFor(title));
    }

    private static Response<String> first(RequestContext context) {
        Repository repo = MongoDbRepository.getInstance();
        return Response.forPayload(repo.get().toString());
    }

}
