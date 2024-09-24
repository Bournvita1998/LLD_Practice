package LLD_Airbnb;

import java.time.LocalDateTime;

public class Reservation {
    private String id;
    private Resource resource;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String userId; // User who made the reservation

    public Reservation(String id, Resource resource, LocalDateTime startTime, LocalDateTime endTime, String userId) {
        this.id = id;
        this.resource = resource;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
    }

    // Getters and Setters
    public String getId() { return id; }
    public Resource getResource() { return resource; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public String getUserId() { return userId; }
}
