package LLD_Splitwise;

import java.util.*;

public class ExpenseManager {
    private Map<String, User> users;
    private Map<String, Map<String, Double>> balances;
    private Map<String, List<Expense>> passbook;

    public ExpenseManager() {
        this.users = new HashMap<>();
        this.balances = new HashMap<>();
        this.passbook = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
        balances.put(user.getUserId(), new HashMap<>());
        passbook.put(user.getUserId(), new ArrayList<>());
    }

    public void addExpense(Expense expense) {
        Map<String, Double> shares = expense.calculateShares();
        String paidBy = expense.paidBy;

        // Add expense to the passbook
        for (String participant : expense.participants) {
            passbook.get(participant).add(expense);
        }

        for (Map.Entry<String, Double> entry : shares.entrySet()) {
            String participant = entry.getKey();
            double amount = entry.getValue();
            balances.get(paidBy).put(participant, balances.get(paidBy).getOrDefault(participant, 0.0) + amount);
            balances.get(participant).put(paidBy, balances.get(participant).getOrDefault(paidBy, 0.0) - amount);
        }
    }

    public String showBalances() {
        StringBuilder result = new StringBuilder();
        boolean noBalances = true;
        for (Map.Entry<String, Map<String, Double>> userBalance : balances.entrySet()) {
            for (Map.Entry<String, Double> balance : userBalance.getValue().entrySet()) {
                if (balance.getValue() > 0) {
                    result.append(String.format("%s owes %s: %.2f\n", balance.getKey(), userBalance.getKey(), balance.getValue()));
                    noBalances = false;
                }
            }
        }
        if (noBalances) {
            return "No balances";
        }
        return result.toString();
    }

    public String showUserBalance(String userId) {
        if (!balances.containsKey(userId)) {
            return "No balances";
        }
        StringBuilder result = new StringBuilder();
        boolean noBalances = true;
        for (Map.Entry<String, Double> balance : balances.get(userId).entrySet()) {
            if (balance.getValue() < 0) {
                result.append(String.format("%s owes %s: %.2f\n", userId, balance.getKey(), -balance.getValue()));
                noBalances = false;
            }
        }
        for (Map.Entry<String, Map<String, Double>> userBalance : balances.entrySet()) {
            if (userBalance.getValue().containsKey(userId) && userBalance.getValue().get(userId) > 0) {
                result.append(String.format("%s owes %s: %.2f\n", userBalance.getKey(), userId, userBalance.getValue().get(userId)));
                noBalances = false;
            }
        }
        if (noBalances) {
            return "No balances";
        }
        return result.toString();
    }

    public String showPassbook(String userId) {
        if (!passbook.containsKey(userId)) {
            return "No transactions";
        }
        StringBuilder result = new StringBuilder();
        List<Expense> transactions = passbook.get(userId);
        for (Expense expense : transactions) {
            result.append(expense.toString()).append("\n");
        }
        return result.toString();
    }

    public void simplifyBalances() {
        for (Map.Entry<String, Map<String, Double>> userBalance : balances.entrySet()) {
            String userId = userBalance.getKey();
            for (Map.Entry<String, Double> balance : new HashMap<>(userBalance.getValue()).entrySet()) {
                if (balance.getValue() > 0) {
                    settleDebt(userId, balance.getKey(), balance.getValue());
                }
            }
        }
    }

    private void settleDebt(String user1, String user2, double amount) {
        if (amount <= 0) {
            return;
        }
        Map<String, Double> user1Balances = balances.get(user1);
        Map<String, Double> user2Balances = balances.get(user2);

        for (Map.Entry<String, Double> entry : new HashMap<>(user1Balances).entrySet()) {
            String intermediateUser = entry.getKey();
            if (!intermediateUser.equals(user2) && user2Balances.containsKey(intermediateUser) && user2Balances.get(intermediateUser) < 0) {
                double intermediateAmount = Math.min(amount, -user2Balances.get(intermediateUser));
                user1Balances.put(intermediateUser, user1Balances.get(intermediateUser) - intermediateAmount);
                user2Balances.put(intermediateUser, user2Balances.get(intermediateUser) + intermediateAmount);
                balances.get(intermediateUser).put(user1, balances.get(intermediateUser).getOrDefault(user1, 0.0) - intermediateAmount);
                balances.get(intermediateUser).put(user2, balances.get(intermediateUser).getOrDefault(user2, 0.0) + intermediateAmount);
                amount -= intermediateAmount;
                if (amount <= 0) {
                    break;
                }
            }
        }
        user1Balances.put(user2, amount);
        user2Balances.put(user1, -amount);
    }
}


