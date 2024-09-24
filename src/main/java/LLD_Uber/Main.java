package LLD_Uber;

import LLD_Uber.Notifications_And_Alerts.NotificationManager;
import LLD_Uber.Payments.Invoice;
import LLD_Uber.Payments.Payment;
import LLD_Uber.Payments.PricingManager;
import LLD_Uber.Payments.PricingModel;
import LLD_Uber.Ride.*;
import LLD_Uber.User.Driver;
import LLD_Uber.User.User;
import LLD_Uber.User.UserManager;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        // Create users
        User user1 = UserManager.createUser("Alice", "alice@example.com", "1234567890");
        Driver driver1 = UserManager.createDriver("Bob", "bob@example.com", "0987654321", "LIC123", "Toyota Prius");

        // Create locations
        Location pickupLocation = new Location(37.7749, -122.4194); // San Francisco
        Location dropoffLocation = new Location(37.7849, -122.4094); // Nearby location in San Francisco

        // User requests a ride
        RideRequest rideRequest = RideManager.requestRide(user1.getUserId(), pickupLocation, dropoffLocation);

        // System matches the ride request with a driver
        Ride ride = RideManager.matchRide(rideRequest, driver1.getUserId());

        // Start the ride
        RideManager.startRide(ride);

        // End the ride
        RideManager.endRide(ride);

        // Define a pricing model
        PricingModel pricingModel = new PricingModel(2.50, 1.75, 0.35); // Base fare: $2.50, $1.75 per km, $0.35 per minute

        // Calculate the fare
        Route route = new Route(pickupLocation, dropoffLocation);
        double fare = PricingManager.calculateFare(route, pricingModel);

        // Process payment
        Payment payment = PricingManager.processPayment(user1.getUserId(), fare, "credit_card");

        // Generate an invoice
        Invoice invoice = PricingManager.generateInvoice(ride.getRideId(), fare);

        // Send notifications
        NotificationManager.sendNotification(user1.getUserId(), "Your ride has been completed. Fare: $" + fare);
        NotificationManager.sendNotification(driver1.getUserId(), "Ride completed. Fare: $" + fare);

        // Print out the results
        System.out.println("Ride Details:");
        System.out.println("User: " + user1.getName());
        System.out.println("Driver: " + driver1.getName());
        System.out.println("Pickup Location: " + pickupLocation.getLatitude() + ", " + pickupLocation.getLongitude());
        System.out.println("Dropoff Location: " + dropoffLocation.getLatitude() + ", " + dropoffLocation.getLongitude());
        System.out.println("Fare: $" + fare);
        System.out.println("Payment ID: " + payment.getPaymentId());
        System.out.println("Invoice ID: " + invoice.getInvoiceId());
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }
}

