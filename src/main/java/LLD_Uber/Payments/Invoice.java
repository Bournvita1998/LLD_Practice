package LLD_Uber.Payments;

public class Invoice {
    private String invoiceId;
    private String rideId;
    private double amount;
    private String paymentStatus;

    public Invoice(String invoiceId, String rideId, double amount, String paymentStatus) {
        this.invoiceId = invoiceId;
        this.rideId = rideId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    // Getters and setters
}

