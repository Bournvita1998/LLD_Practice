package LLD_Twitter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Twitter twitter = new Twitter();
        User user1 = twitter.createUser("user1");
        User user2 = twitter.createUser("user2");

        user1.follow("user2");
        user2.postTweet("hello twitter");
        user1.postTweet("hello user2");

        List<String> user1NewsFeed = user1.getNewsFeed(twitter);
        System.out.println(user1NewsFeed); // => ['user2:hello twitter', 'user1:hello user2']

        user1.unfollow("user2");

        user1NewsFeed = user1.getNewsFeed(twitter);
        System.out.println(user1NewsFeed); // => ['user1:hello user2']
    }
}

