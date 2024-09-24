package LLD_NotificationService;

import java.util.HashSet;
import java.util.Set;

public class DeduplicationService {
    private final Set<Notification> sentNotifications;

    public DeduplicationService() {
        this.sentNotifications = new HashSet<>();
    }

    public boolean isDuplicate(Notification notification) {
        return !sentNotifications.add(notification);
    }
}

