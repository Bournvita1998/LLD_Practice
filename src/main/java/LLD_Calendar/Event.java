package LLD_Calendar;

import java.time.LocalDateTime;

public class Event {
    private String id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean allDay;
    private String location;

    // Constructors, getters, and setters

    public Event(String id, String title, String description, LocalDateTime startTime, LocalDateTime endTime, boolean allDay, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.allDay = allDay;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    // Additional methods like toString(), equals(), hashCode(), etc.
}

