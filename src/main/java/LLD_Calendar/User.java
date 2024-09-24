package LLD_Calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private String id;
    private String name;
    private List<Calendar> calendars;

    // Constructors, getters, and setters

    public User(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.calendars = new ArrayList<>();
    }

    public void addCalendar(Calendar calendar) {
        calendars.add(calendar);
    }

    public void removeCalendar(String calendarId) {
        calendars.removeIf(calendar -> calendar.getId().equals(calendarId));
    }

    public List<Calendar> getCalendars() {
        return calendars;
    }
}

