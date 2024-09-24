package LLD_Uber.Ride;

import java.util.UUID;

public class RideManager {
    public static RideRequest requestRide(String userId, Location pickupLocation, Location dropoffLocation) {
        String requestId = generateId();
        RideRequest rideRequest = new RideRequest(requestId, userId, pickupLocation, dropoffLocation);
        // Save ride request to database
        return rideRequest;
    }

    public static Ride matchRide(RideRequest rideRequest, String driverId) {
        String rideId = generateId();
        Ride ride = new Ride(rideId, rideRequest, driverId);
        rideRequest.setStatus("matched");
        // Save ride to database
        return ride;
    }

    public static void startRide(Ride ride) {
        ride.setStatus("ongoing");
        // Update ride status in database
    }

    public static void endRide(Ride ride) {
        ride.setStatus("completed");
        // Update ride status in database
    }

    public static void cancelRide(RideRequest rideRequest) {
        rideRequest.setStatus("cancelled");
        // Update ride request status in database
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }
}

