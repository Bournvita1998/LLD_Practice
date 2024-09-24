package LLD_Restaurant_Reservation_System;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private List<Reservation> reservations = new ArrayList<>();
    private RestaurantService restaurantService;

    public ReservationService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public Reservation bookTable(int restaurantId, int tableId, int userId, LocalDateTime dateTime, int numberOfPeople) {
        Reservation reservation = new Reservation(reservations.size() + 1, restaurantId, tableId, userId, dateTime, numberOfPeople);
        reservations.add(reservation);
        return reservation;
    }

    public List<Table> getAvailableSlots(int restaurantId, LocalDateTime start, LocalDateTime end) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        List<Table> availableTables = new ArrayList<>();

        if (restaurant != null) {
            for (Table table : restaurant.getTables()) {
                boolean isAvailable = true;
                for (Reservation reservation : reservations) {
                    if (reservation.getRestaurantId() == restaurantId && reservation.getTableId() == table.getTableId()
                            && reservation.getDateTime().isAfter(start) && reservation.getDateTime().isBefore(end)) {
                        isAvailable = false;
                        break;
                    }
                }
                if (isAvailable) {
                    availableTables.add(table);
                }
            }
        }
        return availableTables;
    }
}

