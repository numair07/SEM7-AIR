import java.util.HashMap;

public class Organisation {
    public String name;
    private HashMap<String, String> events;

    public Organisation(String name) {
        this.name = name;
        events = new HashMap<>();
    }

    public void addEvent(String title, String description) {
        events.put(title, description);
    }

    public boolean containsDescription(String title) {
        return events.containsKey(title);
    }

    public String returnDescription(String title) {
        return events.get(title);
    }
}
