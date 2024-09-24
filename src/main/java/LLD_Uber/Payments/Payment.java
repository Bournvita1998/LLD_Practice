package LLD_Uber.Payments;

public class Payment {
    private String paymentId;
    private String userId;
    private double amount;
    private String method;

    public Payment(String paymentId, String userId, double amount, String method) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.amount = amount;
        this.method = method;
    }

    public String getPaymentId() {
        return paymentId;
    }

    // Getters and setters
}

