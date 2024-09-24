package LLD_NotificationService;

public class ChannelFactory {
    public static NotificationChannel getChannel(ChannelType channelType) {
        switch (channelType) {
            case EMAIL:
                return new EmailChannel();
            case SMS:
                return new SMSChannel();
            case PUSH_NOTIFICATION:
                return new PushNotificationChannel();
            default:
                throw new IllegalArgumentException("Invalid channel type");
        }
    }
}
