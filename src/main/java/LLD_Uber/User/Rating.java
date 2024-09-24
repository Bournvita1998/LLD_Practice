package LLD_Uber.User;

public class Rating {
    private String userId;
    private String driverId;
    private int score;
    private String review;

    public Rating(String userId, String driverId, int score, String review) {
        this.userId = userId;
        this.driverId = driverId;
        this.score = score;
        this.review = review;
    }

    // Getters and setters
}

