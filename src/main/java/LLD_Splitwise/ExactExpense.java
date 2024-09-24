package LLD_Splitwise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExactExpense extends Expense {
    private List<Double> shares;

    public ExactExpense(String paidBy, double amount, List<String> participants, List<Double> shares, String name, String notes, String imageUrl) {
        super(paidBy, amount, participants, name, notes, imageUrl);
        this.shares = shares;
    }

    @Override
    public Map<String, Double> calculateShares() {
        Map<String, Double> sharesMap = new HashMap<>();
        for (int i = 0; i < participants.size(); i++) {
            if (!participants.get(i).equals(paidBy)) {
                sharesMap.put(participants.get(i), shares.get(i));
            }
        }
        return sharesMap;
    }
}
