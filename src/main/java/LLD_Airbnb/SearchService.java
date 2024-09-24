package LLD_Airbnb;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchService {
    private ResourceManager resourceManager;
    private ReservationManager reservationManager;

    public SearchService(ResourceManager resourceManager, ReservationManager reservationManager) {
        this.resourceManager = resourceManager;
        this.reservationManager = reservationManager;
    }

    public List<Resource> searchAvailableResources(LocalDateTime startTime, LocalDateTime endTime) {
        List<Resource> availableResources = new ArrayList<>();
        Map<String, Resource> resources = resourceManager.getAllResources();

        for (Resource resource : resources.values()) {
            boolean isAvailable = true;
            for (Reservation reservation : reservationManager.reservations.values()) {
                if (reservation.getResource().getId().equals(resource.getId()) &&
                        (startTime.isBefore(reservation.getEndTime()) && endTime.isAfter(reservation.getStartTime()))) {
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                availableResources.add(resource);
            }
        }
        return availableResources;
    }
}

