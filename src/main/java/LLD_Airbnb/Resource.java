package LLD_Airbnb;

public class Resource {
    private String id;
    private String type; // e.g., "table", "room"
    private String description;
    private boolean isAvailable;

    public Resource(String id, String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.isAvailable = true;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }
}

