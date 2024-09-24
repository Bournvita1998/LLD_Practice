package LLD_Airbnb;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ReservationManager {
    Map<String, Reservation> reservations = new HashMap<>();
    private ResourceManager resourceManager;

    public ReservationManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public boolean makeReservation(String reservationId, String resourceId, LocalDateTime startTime, LocalDateTime endTime, String userId) {
        Resource resource = resourceManager.getResource(resourceId);
        if (resource == null || !resource.isAvailable()) {
            return false; // Resource not available
        }

        for (Reservation reservation : reservations.values()) {
            if (reservation.getResource().getId().equals(resourceId) &&
                    (startTime.isBefore(reservation.getEndTime()) && endTime.isAfter(reservation.getStartTime()))) {
                return false; // Overlapping reservation
            }
        }

        Reservation newReservation = new Reservation(reservationId, resource, startTime, endTime, userId);
        reservations.put(reservationId, newReservation);
        resourceManager.updateResourceAvailability(resourceId, false);
        return true;
    }

    public boolean cancelReservation(String reservationId) {
        Reservation reservation = reservations.remove(reservationId);
        if (reservation == null) {
            return false; // Reservation not found
        }

        Resource resource = reservation.getResource();
        resourceManager.updateResourceAvailability(resource.getId(), true);
        return true;
    }

    public boolean modifyReservation(String reservationId, LocalDateTime newStartTime, LocalDateTime newEndTime) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null || !resourceManager.getResource(reservation.getResource().getId()).isAvailable()) {
            return false; // Reservation not found or resource not available
        }

        cancelReservation(reservationId);
        return makeReservation(reservationId, reservation.getResource().getId(), newStartTime, newEndTime, reservation.getUserId());
    }
}

