package LLD_Restaurant_Reservation_System;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        RestaurantService restaurantService = new RestaurantService();
        ReservationService reservationService = new ReservationService(restaurantService);

        // Create restaurant
        List<Table> tables = Arrays.asList(new Table(1, 4), new Table(2, 2));
        Map<String, String> openingHours = new HashMap<>();
        openingHours.put("Monday", "08:00-22:00");
        openingHours.put("Tuesday", "08:00-22:00");
        Restaurant restaurant = restaurantService.createOrUpdateRestaurant(1, "Chez Gourmet", "123 Fine Dining St.", tables, openingHours);

        // Book a table
        Reservation reservation = reservationService.bookTable(1, 1, 1, LocalDateTime.of(2024, 8, 1, 19, 0), 2);
        System.out.println("Reservation ID: " + reservation.getReservationId());

        // Get available slots
        List<Table> availableSlots = reservationService.getAvailableSlots(1, LocalDateTime.of(2024, 8, 1, 18, 0), LocalDateTime.of(2024, 8, 1, 20, 0));
        for (Table table : availableSlots) {
            System.out.println("Available Table ID: " + table.getTableId() + ", Capacity: " + table.getCapacity());
        }
    }
}

