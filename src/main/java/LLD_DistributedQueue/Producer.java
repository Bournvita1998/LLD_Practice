package LLD_DistributedQueue;

public class Producer implements Runnable {
    private String producerId;
    private QueueSystem queueSystem;
    private String topicName;
    private String messageContent;

    public Producer(String producerId, QueueSystem queueSystem, String topicName, String messageContent) {
        this.producerId = producerId;
        this.queueSystem = queueSystem;
        this.topicName = topicName;
        this.messageContent = messageContent;
    }

    @Override
    public void run() {
        Topic topic = queueSystem.getTopic(topicName);
        if (topic != null) {
            topic.publish(new Message(messageContent));
            System.out.println(producerId + " published " + messageContent + " to " + topicName);
        } else {
            System.err.println("Topic " + topicName + " does not exist.");
        }
    }
}
