package LLD_Splitwise;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager();

        // Add users
        expenseManager.addUser(new User("u1", "User1", "user1@example.com", "1234567890"));
        expenseManager.addUser(new User("u2", "User2", "user2@example.com", "0987654321"));
        expenseManager.addUser(new User("u3", "User3", "user3@example.com", "1231231234"));
        expenseManager.addUser(new User("u4", "User4", "user4@example.com", "4321432143"));

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String command = scanner.nextLine();
            if (command.startsWith("SHOW")) {
                String[] parts = command.split(" ");
                if (parts.length == 1) {
                    System.out.println(expenseManager.showBalances());
                } else {
                    System.out.println(expenseManager.showUserBalance(parts[1]));
                }
            } else if (command.startsWith("EXPENSE")) {
                String[] parts = command.split(" ");
                String paidBy = parts[1];
                double amount = Double.parseDouble(parts[2]);
                int numUsers = Integer.parseInt(parts[3]);
                List<String> participants = Arrays.asList(Arrays.copyOfRange(parts, 4, 4 + numUsers));
                String expenseType = parts[4 + numUsers];
                String name = "No name", notes = "", imageUrl = "";
                int extraArgsIndex = 5 + numUsers;

                if (parts.length > extraArgsIndex) {
                    name = parts[extraArgsIndex];
                }
                if (parts.length > extraArgsIndex + 1) {
                    notes = parts[extraArgsIndex + 1];
                }
                if (parts.length > extraArgsIndex + 2) {
                    imageUrl = parts[extraArgsIndex + 2];
                }

                if (expenseType.equals("EQUAL")) {
                    Expense expense = new EqualExpense(paidBy, amount, participants, name, notes, imageUrl);
                    expenseManager.addExpense(expense);
                } else if (expenseType.equals("EXACT")) {
                    List<Double> shares = new ArrayList<>();
                    for (int i = extraArgsIndex + 3; i < parts.length; i++) {
                        shares.add(Double.parseDouble(parts[i]));
                    }
                    Expense expense = new ExactExpense(paidBy, amount, participants, shares, name, notes, imageUrl);
                    expenseManager.addExpense(expense);
                } else if (expenseType.equals("PERCENT")) {
                    List<Double> percentages = new ArrayList<>();
                    for (int i = extraArgsIndex + 3; i < parts.length; i++) {
                        percentages.add(Double.parseDouble(parts[i]));
                    }
                    Expense expense = new PercentExpense(paidBy, amount, participants, percentages, name, notes, imageUrl);
                    expenseManager.addExpense(expense);
                } else if (expenseType.equals("SHARE")) {
                    List<Integer> shares = new ArrayList<>();
                    for (int i = extraArgsIndex + 3; i < parts.length; i++) {
                        shares.add(Integer.parseInt(parts[i]));
                    }
                    Expense expense = new ShareExpense(paidBy, amount, participants, shares, name, notes, imageUrl);
                    expenseManager.addExpense(expense);
                }
            } else if (command.startsWith("PASSBOOK")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    System.out.println(expenseManager.showPassbook(parts[1]));
                } else {
                    System.out.println("Invalid PASSBOOK command.");
                }
            } else if (command.equals("SIMPLIFY")) {
                expenseManager.simplifyBalances();
                System.out.println("Balances simplified.");
            }
        }
        scanner.close();
    }
}
