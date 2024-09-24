package LLD_Restaurant_Reservation_System;

import java.time.Instant;
import java.time.LocalDateTime;

public class Reservation {
    private int reservationId;
    private int restaurantId;
    private int tableId;
    private int userId;
    private LocalDateTime dateTime;
    private int numberOfPeople;

    // Constructor, Getters, and Setters
    public Reservation(int reservationId, int restaurantId, int tableId, int userId, LocalDateTime dateTime, int numberOfPeople) {
        this.reservationId = reservationId;
        this.restaurantId = restaurantId;
        this.tableId = tableId;
        this.userId = userId;
        this.dateTime = dateTime;
        this.numberOfPeople = numberOfPeople;
    }

    public int getReservationId() {
        return reservationId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public int getTableId() {
        return tableId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // Getters and Setters
}

