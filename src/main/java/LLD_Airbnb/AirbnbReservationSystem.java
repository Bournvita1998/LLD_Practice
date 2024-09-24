package LLD_Airbnb;

import java.time.LocalDateTime;
import java.util.List;

public class AirbnbReservationSystem {

    public static void main(String[] args) {
        // Initialize Managers
        ResourceManager resourceManager = new ResourceManager();
        ReservationManager reservationManager = new ReservationManager(resourceManager);
        SearchService searchService = new SearchService(resourceManager, reservationManager);

        // Create and add resources (rooms)
        Resource room1 = new Resource("room1", "room", "Luxury Suite");
        Resource room2 = new Resource("room2", "room", "Standard Room");
        Resource room3 = new Resource("room3", "room", "Economy Room");
        resourceManager.addResource(room1);
        resourceManager.addResource(room2);
        resourceManager.addResource(room3);

        // Create users
        User user1 = new User("user1", "Alice");
        User user2 = new User("user2", "Bob");

        // Make reservations
        LocalDateTime startTime1 = LocalDateTime.of(2024, 8, 1, 14, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2024, 8, 5, 11, 0);
        boolean reserved1 = reservationManager.makeReservation("res1", "room1", startTime1, endTime1, user1.getId());

        LocalDateTime startTime2 = LocalDateTime.of(2024, 8, 3, 14, 0);
        LocalDateTime endTime2 = LocalDateTime.of(2024, 8, 7, 11, 0);
        boolean reserved2 = reservationManager.makeReservation("res2", "room2", startTime2, endTime2, user2.getId());

        // Display reservation results
        System.out.println("Reservation for room1 (Luxury Suite) made: " + reserved1);
        System.out.println("Reservation for room2 (Standard Room) made: " + reserved2);

        // Search for available rooms during a certain period
        LocalDateTime searchStartTime = LocalDateTime.of(2024, 8, 2, 14, 0);
        LocalDateTime searchEndTime = LocalDateTime.of(2024, 8, 4, 11, 0);
        List<Resource> availableRooms = searchService.searchAvailableResources(searchStartTime, searchEndTime);

        System.out.println("Available rooms from " + searchStartTime + " to " + searchEndTime + ":");
        for (Resource room : availableRooms) {
            System.out.println(" - " + room.getDescription());
        }

        // Modify a reservation
        LocalDateTime newStartTime = LocalDateTime.of(2024, 8, 4, 14, 0);
        LocalDateTime newEndTime = LocalDateTime.of(2024, 8, 10, 11, 0);
        boolean modified = reservationManager.modifyReservation("res2", newStartTime, newEndTime);

        System.out.println("Reservation res2 modified: " + modified);

        // Search again after modification
        availableRooms = searchService.searchAvailableResources(searchStartTime, searchEndTime);

        System.out.println("Available rooms from " + searchStartTime + " to " + searchEndTime + " after modification:");
        for (Resource room : availableRooms) {
            System.out.println(" - " + room.getDescription());
        }

        // Cancel a reservation
        boolean canceled = reservationManager.cancelReservation("res1");
        System.out.println("Reservation res1 canceled: " + canceled);

        // Search again after cancellation
        availableRooms = searchService.searchAvailableResources(searchStartTime, searchEndTime);

        System.out.println("Available rooms from " + searchStartTime + " to " + searchEndTime + " after cancellation:");
        for (Resource room : availableRooms) {
            System.out.println(" - " + room.getDescription());
        }
    }
}
