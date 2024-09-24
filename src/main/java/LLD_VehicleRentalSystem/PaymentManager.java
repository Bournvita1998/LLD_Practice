package LLD_VehicleRentalSystem;

import java.util.HashMap;
import java.util.Map;

public class PaymentManager {
    private Map<String, Payment> payments;

    public PaymentManager() {
        payments = new HashMap<>();
    }

    public void addPayment(Payment payment) {
        payments.put(payment.getPaymentId(), payment);
    }

    public Payment getPayment(String paymentId) {
        return payments.get(paymentId);
    }
}

