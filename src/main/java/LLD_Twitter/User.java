package LLD_Twitter;

import java.util.*;

public class User {
    private String userName;
    private List<String> tweets;
    private Set<String> following;
    private static final int NEWS_FEED_LIMIT = 10;

    public User(String userName) {
        this.userName = userName;
        this.tweets = new ArrayList<>();
        this.following = new HashSet<>();
    }

    public void postTweet(String tweet) {
        tweets.add(tweet);
    }

    public List<String> getNewsFeed(Twitter twitter) {
        List<String> newsFeed = new ArrayList<>();
        PriorityQueue<Tweet> maxHeap = new PriorityQueue<>((a, b) -> b.timestamp - a.timestamp);

        for (String tweet : tweets) {
            maxHeap.add(new Tweet(userName, tweet));
        }

        for (String followee : following) {
            User user = twitter.getUser(followee);
            if (user != null) {
                for (String tweet : user.getTweets()) {
                    maxHeap.add(new Tweet(followee, tweet));
                }
            }
        }

        while (!maxHeap.isEmpty() && newsFeed.size() < NEWS_FEED_LIMIT) {
            Tweet tweet = maxHeap.poll();
            newsFeed.add(tweet.userName + ":" + tweet.tweet);
        }

        return newsFeed;
    }

    public void follow(String userName) {
        if (following.contains(userName)) {
            System.out.println("Already following " + userName);
        } else {
            following.add(userName);
        }
    }

    public void unfollow(String userName) {
        if (following.contains(userName)) {
            following.remove(userName);
        } else {
            System.out.println("Not following " + userName);
        }
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getTweets() {
        return tweets;
    }
}

