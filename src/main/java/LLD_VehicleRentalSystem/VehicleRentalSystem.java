package LLD_VehicleRentalSystem;

import java.time.LocalDate;

public class VehicleRentalSystem {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        VehicleManager vehicleManager = new VehicleManager();
        RentalManager rentalManager = new RentalManager();
        PaymentManager paymentManager = new PaymentManager();

        RentalService rentalService = new RentalService(userManager, vehicleManager, rentalManager, paymentManager);

        User user = new User("U1", "John Doe", "john@example.com", "1234567890");
        Vehicle vehicle = new Vehicle("V1", "Toyota", "Camry", "Car", true, 50);

        userManager.addUser(user);
        vehicleManager.addVehicle(vehicle);

        Rental rental = rentalService.createRental("U1", "V1", LocalDate.now(), LocalDate.now().plusDays(3));
        Payment payment = rentalService.processPayment(rental.getRentalId(), rental.getTotalAmount(), "Credit Card");

        rentalService.completeRental(rental.getRentalId());
    }
}

