package LLD_ProjectManagementApp;

import java.util.UUID;

public class Card {
    private String cardId;
    private String name;
    private String description;
    private User assignedUser;

    public Card(String name, String description) {
        this.cardId = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.assignedUser = null;
    }

    public String getCardId() {
        return cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void assignUser(User user) {
        this.assignedUser = user;
    }

    public void unassignUser() {
        this.assignedUser = null;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId='" + cardId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", assignedUser=" + (assignedUser != null ? assignedUser.getName() : "null") +
                '}';
    }
}
