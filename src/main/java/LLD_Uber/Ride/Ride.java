package LLD_Uber.Ride;

public class Ride {
    private String rideId;
    private RideRequest rideRequest;
    private String driverId;
    private String status;

    public Ride(String rideId, RideRequest rideRequest, String driverId) {
        this.rideId = rideId;
        this.rideRequest = rideRequest;
        this.driverId = driverId;
        this.status = "ongoing";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRideId() {
        return rideId;
    }

    // Getters and setters
}

