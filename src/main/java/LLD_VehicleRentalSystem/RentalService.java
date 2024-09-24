package LLD_VehicleRentalSystem;

import java.time.LocalDate;

public class RentalService {
    private UserManager userManager;
    private VehicleManager vehicleManager;
    private RentalManager rentalManager;
    private PaymentManager paymentManager;

    public RentalService(UserManager userManager, VehicleManager vehicleManager, RentalManager rentalManager, PaymentManager paymentManager) {
        this.userManager = userManager;
        this.vehicleManager = vehicleManager;
        this.rentalManager = rentalManager;
        this.paymentManager = paymentManager;
    }

    public Rental createRental(String userId, String vehicleId, LocalDate startDate, LocalDate endDate) {
        User user = userManager.getUser(userId);
        Vehicle vehicle = vehicleManager.getVehicle(vehicleId);

        if (user == null || vehicle == null || !vehicle.isAvailable()) {
            throw new IllegalArgumentException("Invalid user or vehicle information.");
        }

        double totalAmount = vehicle.getRentalPricePerDay() * (endDate.toEpochDay() - startDate.toEpochDay());
        Rental rental = new Rental();
        rental.setUser(user);
        rental.setVehicle(vehicle);
        rental.setStartDate(startDate);
        rental.setEndDate(endDate);
        rental.setTotalAmount(totalAmount);

        rentalManager.addRental(rental);
        vehicleManager.updateVehicleAvailability(vehicleId, false);

        return rental;
    }

    public Payment processPayment(String rentalId, double amount, String paymentMethod) {
        Rental rental = rentalManager.getRental(rentalId);

        if (rental == null || amount != rental.getTotalAmount()) {
            throw new IllegalArgumentException("Invalid rental or payment amount.");
        }

        Payment payment = new Payment();
        payment.setRental(rental);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMethod(paymentMethod);

        paymentManager.addPayment(payment);

        return payment;
    }

    public void completeRental(String rentalId) {
        Rental rental = rentalManager.getRental(rentalId);

        if (rental != null) {
            vehicleManager.updateVehicleAvailability(rental.getVehicle().getVehicleId(), true);
        }
    }
}

