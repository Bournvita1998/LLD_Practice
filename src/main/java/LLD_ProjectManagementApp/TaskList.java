package LLD_ProjectManagementApp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskList {
    private String listId;
    private String name;
    private List<Card> cards;

    public TaskList(String name) {
        this.listId = UUID.randomUUID().toString();
        this.name = name;
        this.cards = new ArrayList<>();
    }

    public String getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(String cardId) {
        cards.removeIf(card -> card.getCardId().equals(cardId));
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "listId='" + listId + '\'' +
                ", name='" + name + '\'' +
                ", cards=" + cards +
                '}';
    }
}


