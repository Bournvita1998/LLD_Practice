package LLD_DistributedQueue;

import java.util.LinkedList;
import java.util.Queue;

public class Topic {
    private String name;
    private Queue<Message> messages;

    public Topic(String name) {
        this.name = name;
        this.messages = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public synchronized void publish(Message message) {
        messages.add(message);
        notifyAll();
    }

    public synchronized Message consume() throws InterruptedException {
        while (messages.isEmpty()) {
            wait();
        }
        return messages.poll();
    }
}
