package LLD_Splitwise;

import java.util.List;
import java.util.Map;

public abstract class Expense {
    protected String paidBy;
    protected double amount;
    protected List<String> participants;
    protected String name;
    protected String notes;
    protected String imageUrl;

    public Expense(String paidBy, double amount, List<String> participants, String name, String notes, String imageUrl) {
        this.paidBy = paidBy;
        this.amount = amount;
        this.participants = participants;
        this.name = name;
        this.notes = notes;
        this.imageUrl = imageUrl;
    }

    public abstract Map<String, Double> calculateShares();

    @Override
    public String toString() {
        return "Expense{" +
                "paidBy='" + paidBy + '\'' +
                ", amount=" + amount +
                ", participants=" + participants +
                ", name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
