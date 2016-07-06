import java.util.List;

public interface Repository {

    void add(String event);

    List<String> get();

    String lookingFor(String title);

}
