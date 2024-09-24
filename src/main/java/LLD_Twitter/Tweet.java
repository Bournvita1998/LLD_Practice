package LLD_Twitter;

public class Tweet {
    String userName;
    String tweet;
    int timestamp;
    private static int globalTimestamp = 0;

    Tweet(String userName, String tweet) {
        this.userName = userName;
        this.tweet = tweet;
        this.timestamp = globalTimestamp++;
    }
}
