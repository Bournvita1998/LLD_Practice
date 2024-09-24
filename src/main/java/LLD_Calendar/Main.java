package LLD_Calendar;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Create a user
        User user = new User("John Doe");

        // Create a calendar
        Calendar personalCalendar = new Calendar("Personal");

        // Add calendar to user
        user.addCalendar(personalCalendar);

        // Create an event
        Event meeting = new Event(
                "1",
                "Team Meeting",
                "Discuss project updates",
                LocalDateTime.of(2024, 8, 1, 10, 0),
                LocalDateTime.of(2024, 8, 1, 11, 0),
                false,
                "Conference Room"
        );

        // Add event to calendar
        personalCalendar.addEvent(meeting);

        // Create a notification for the event
        Notification notification = new Notification(meeting, LocalDateTime.of(2024, 8, 1, 9, 45));

        // Output user's calendars and events
        user.getCalendars().forEach(calendar -> {
            System.out.println("Calendar: " + calendar.getName());
            calendar.getEvents().forEach(event -> {
                System.out.println("Event: " + event.getTitle() + " at " + event.getStartTime());
            });
        });
    }
}

