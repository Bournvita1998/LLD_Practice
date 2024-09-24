package LLD_Calendar;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class Notification {
    private String id;
    private Event event;
    private LocalDateTime reminderTime;

    // Constructors, getters, and setters

    public Notification(Event event, LocalDateTime reminderTime) {
        this.id = UUID.randomUUID().toString();
        this.event = event;
        this.reminderTime = reminderTime;
        scheduleReminder();
    }

    private void scheduleReminder() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // Code to send notification
                System.out.println("Reminder: " + event.getTitle() + " at " + event.getStartTime());
            }
        };
        timer.schedule(task, java.sql.Timestamp.valueOf(reminderTime));
    }
}

