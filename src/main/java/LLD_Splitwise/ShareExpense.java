package LLD_Splitwise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareExpense extends Expense {
    private List<Integer> shares;

    public ShareExpense(String paidBy, double amount, List<String> participants, List<Integer> shares, String name, String notes, String imageUrl) {
        super(paidBy, amount, participants, name, notes, imageUrl);
        this.shares = shares;
    }

    @Override
    public Map<String, Double> calculateShares() {
        Map<String, Double> sharesMap = new HashMap<>();
        int totalShares = shares.stream().mapToInt(Integer::intValue).sum();
        for (int i = 0; i < participants.size(); i++) {
            if (!participants.get(i).equals(paidBy)) {
                double shareAmount = Math.round((amount * shares.get(i) / totalShares) * 100.0) / 100.0;
                sharesMap.put(participants.get(i), shareAmount);
            }
        }
        return sharesMap;
    }
}

