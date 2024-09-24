package LLD_NotificationService;

public class RetryService {
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_INTERVAL = 2000; // in milliseconds

    public void sendWithRetry(NotificationChannel channel, Notification notification) {
        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            try {
                channel.send(notification);
                return;
            } catch (Exception e) {
                attempts++;
                try {
                    Thread.sleep(RETRY_INTERVAL);
                } catch (InterruptedException ignored) {}
            }
        }
        System.err.println("Failed to send notification after " + MAX_RETRIES + " attempts");
    }
}

