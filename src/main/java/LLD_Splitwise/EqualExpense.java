package LLD_Splitwise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqualExpense extends Expense {

    public EqualExpense(String paidBy, double amount, List<String> participants, String name, String notes, String imageUrl) {
        super(paidBy, amount, participants, name, notes, imageUrl);
    }

    @Override
    public Map<String, Double> calculateShares() {
        double share = Math.round((amount / participants.size()) * 100.0) / 100.0;
        Map<String, Double> shares = new HashMap<>();
        for (String participant : participants) {
            if (!participant.equals(paidBy)) {
                shares.put(participant, share);
            }
        }
        return shares;
    }
}
