package LLD_Uber.Notifications_And_Alerts;

import java.util.UUID;

public class NotificationManager {
    public static void sendNotification(String userId, String message) {
        String notificationId = generateId();
        Notification notification = new Notification(notificationId, userId, message);
        // Save notification to database and send to user
    }

    public static void alertUser(String userId, String message) {
        String alertId = generateId();
        Alert alert = new Alert(alertId, userId, message);
        // Save alert to database and send to user
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }
}

