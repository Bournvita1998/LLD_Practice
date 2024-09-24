package LLD_VehicleRentalSystem;

import java.time.LocalDate;

public class Payment {
    private String paymentId;
    private Rental rental;
    private double amount;
    private LocalDate paymentDate;
    private String paymentMethod; // e.g., Credit Card, PayPal

    public String getPaymentId() {
        return paymentId;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentDate(LocalDate now) {
        this.paymentDate = now;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Constructors, getters, and setters
}

