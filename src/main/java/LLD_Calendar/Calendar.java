package LLD_Calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Calendar {
    private String id;
    private String name;
    private List<Event> events;

    // Constructors, getters, and setters

    public Calendar(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(String eventId) {
        events.removeIf(event -> event.getId().equals(eventId));
    }

    public List<Event> getEvents() {
        return events;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Additional methods like getEventsByDate(), getEventsByTitle(), etc.
}

