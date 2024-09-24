package LLD_VehicleRentalSystem;

import java.time.LocalDate;

public class Rental {
    private String rentalId;
    private User user;
    private Vehicle vehicle;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalAmount;

    public String getRentalId() {
        return rentalId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    // Constructors, getters, and setters
}

