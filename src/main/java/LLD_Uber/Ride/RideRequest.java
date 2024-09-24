package LLD_Uber.Ride;

public class RideRequest {
    private String requestId;
    private String userId;
    private Location pickupLocation;
    private Location dropoffLocation;
    private String status;

    public RideRequest(String requestId, String userId, Location pickupLocation, Location dropoffLocation) {
        this.requestId = requestId;
        this.userId = userId;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.status = "pending";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getters and setters
}

