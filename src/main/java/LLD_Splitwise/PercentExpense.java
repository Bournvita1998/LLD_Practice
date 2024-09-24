package LLD_Splitwise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PercentExpense extends Expense {
    private List<Double> percentages;

    public PercentExpense(String paidBy, double amount, List<String> participants, List<Double> percentages, String name, String notes, String imageUrl) {
        super(paidBy, amount, participants, name, notes, imageUrl);
        this.percentages = percentages;
    }

    @Override
    public Map<String, Double> calculateShares() {
        Map<String, Double> sharesMap = new HashMap<>();
        for (int i = 0; i < participants.size(); i++) {
            if (!participants.get(i).equals(paidBy)) {
                double share = Math.round((amount * (percentages.get(i) / 100.0)) * 100.0) / 100.0;
                sharesMap.put(participants.get(i), share);
            }
        }
        return sharesMap;
    }
}
