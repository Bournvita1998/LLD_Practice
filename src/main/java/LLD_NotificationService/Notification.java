package LLD_NotificationService;

public class Notification {
    private final String message;
    private final ChannelType channelType;
    private final int priority;

    public Notification(String message, ChannelType channelType, int priority) {
        this.message = message;
        this.channelType = channelType;
        this.priority = priority;
    }

    public String getMessage() {
        return message;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public int getPriority() {
        return priority;
    }
}
