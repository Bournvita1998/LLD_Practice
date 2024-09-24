package LLD_DistributedQueue;

public class Consumer implements Runnable {
    private String consumerId;
    private QueueSystem queueSystem;
    private String topicName;

    public Consumer(String consumerId, QueueSystem queueSystem, String topicName) {
        this.consumerId = consumerId;
        this.queueSystem = queueSystem;
        this.topicName = topicName;
    }

    @Override
    public void run() {
        Topic topic = queueSystem.getTopic(topicName);
        if (topic != null) {
            try {
                while (true) {
                    Message message = topic.consume();
                    System.out.println(consumerId + " received " + message.getContent());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(consumerId + " interrupted.");
            }
        } else {
            System.err.println("Topic " + topicName + " does not exist.");
        }
    }
}
