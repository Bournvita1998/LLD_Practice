package LLD_NotificationService;

public class EmailChannel implements NotificationChannel {
    @Override
    public void send(Notification notification) throws Exception {
        // Implementation for sending email
        System.out.println("Sending Email: " + notification.getMessage());
    }
}
