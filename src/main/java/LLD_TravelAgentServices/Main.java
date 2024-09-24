package LLD_TravelAgentServices;

public class Main {
    public static void main(String[] args) {
        TravelAgencySystem system = new TravelAgencySystem();

        // Create a travel package
        TravelPackage package1 = new TravelPackage("1", "Europe Tour");

        // Create itineraries for the travel package
        Itinerary itinerary1 = new Itinerary("1", "Visit Eiffel Tower", "Paris, France", "2024-08-01");
        Itinerary itinerary2 = new Itinerary("2", "Visit Colosseum", "Rome, Italy", "2024-08-05");

        // Add itineraries to the travel package
        package1.addItinerary(itinerary1);
        package1.addItinerary(itinerary2);

        // Create passengers
        Passenger passenger1 = new Passenger("1", "John Doe", "john.doe@example.com");
        Passenger passenger2 = new Passenger("2", "Jane Smith", "jane.smith@example.com");

        // Add passengers to the travel package
        package1.addPassenger(passenger1);
        package1.addPassenger(passenger2);

        // Add the travel package to the system
        system.addTravelPackage(package1);

        // Display travel packages and their details
        for (TravelPackage travelPackage : system.getTravelPackages()) {
            System.out.println("Travel Package: " + travelPackage.getPackageName());
            System.out.println("Itineraries:");
            for (Itinerary itinerary : travelPackage.getItineraries()) {
                System.out.println("  " + itinerary.getDescription() + " in " + itinerary.getLocation() + " on " + itinerary.getDate());
            }
            System.out.println("Passengers:");
            for (Passenger passenger : travelPackage.getPassengers()) {
                System.out.println("  " + passenger.getName() + " (" + passenger.getEmail() + ")");
            }
        }
    }
}

