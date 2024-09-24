package LLD_VehicleRentalSystem;

import java.util.HashMap;
import java.util.Map;

public class RentalManager {
    private Map<String, Rental> rentals;

    public RentalManager() {
        rentals = new HashMap<>();
    }

    public void addRental(Rental rental) {
        rentals.put(rental.getRentalId(), rental);
    }

    public Rental getRental(String rentalId) {
        return rentals.get(rentalId);
    }
}

