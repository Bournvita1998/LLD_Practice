package LLD_DistributedQueue;

import java.util.HashMap;
import java.util.Map;

public class QueueSystem {
    private Map<String, Topic> topics;

    public QueueSystem() {
        topics = new HashMap<>();
    }

    public void createTopic(String name) {
        topics.put(name, new Topic(name));
    }

    public Topic getTopic(String name) {
        return topics.get(name);
    }
}
