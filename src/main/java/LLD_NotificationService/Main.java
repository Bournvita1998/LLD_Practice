package LLD_NotificationService;

public class Main {
    public static void main(String[] args) {
        NotificationService notificationService = new NotificationService();

        Notification emailNotification = new Notification("Email message", ChannelType.EMAIL, 1);
        Notification smsNotification = new Notification("SMS message", ChannelType.SMS, 2);
        Notification pushNotification = new Notification("Push notification message", ChannelType.PUSH_NOTIFICATION, 3);

        notificationService.sendNotification(emailNotification);
        notificationService.sendNotification(smsNotification);
        notificationService.sendNotification(pushNotification);
    }
}
