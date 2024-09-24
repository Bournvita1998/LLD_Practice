package LLD_NotificationService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationService {
    private final ExecutorService executorService;
    private final DeduplicationService deduplicationService;
    private final RetryService retryService;
    private final Scheduler scheduler;

    public NotificationService() {
        this.executorService = Executors.newFixedThreadPool(10);
        this.deduplicationService = new DeduplicationService();
        this.retryService = new RetryService();
        this.scheduler = new Scheduler();
    }

    public void sendNotification(Notification notification) {
        if (!deduplicationService.isDuplicate(notification)) {
            scheduler.schedule(() -> {
                try {
                    NotificationChannel channel = ChannelFactory.getChannel(notification.getChannelType());
                    executorService.submit(() -> retryService.sendWithRetry(channel, notification));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, notification.getPriority());
        }
    }
}

