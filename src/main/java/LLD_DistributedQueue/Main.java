package LLD_DistributedQueue;

public class Main {
    public static void main(String[] args) {
        QueueSystem queueSystem = new QueueSystem();

        queueSystem.createTopic("topic1");
        queueSystem.createTopic("topic2");

        Thread producer1 = new Thread(new Producer("producer1", queueSystem, "topic1", "Message 1"));
        Thread producer2 = new Thread(new Producer("producer1", queueSystem, "topic1", "Message 2"));
        Thread producer3 = new Thread(new Producer("producer2", queueSystem, "topic1", "Message 3"));
        Thread producer4 = new Thread(new Producer("producer1", queueSystem, "topic2", "Message 4"));
        Thread producer5 = new Thread(new Producer("producer2", queueSystem, "topic2", "Message 5"));

        Thread consumer1 = new Thread(new Consumer("consumer1", queueSystem, "topic1"));
        Thread consumer2 = new Thread(new Consumer("consumer2", queueSystem, "topic1"));
        Thread consumer3 = new Thread(new Consumer("consumer3", queueSystem, "topic1"));
        Thread consumer4 = new Thread(new Consumer("consumer4", queueSystem, "topic1"));
        Thread consumer5 = new Thread(new Consumer("consumer5", queueSystem, "topic1"));

        Thread consumer6 = new Thread(new Consumer("consumer1", queueSystem, "topic2"));
        Thread consumer7 = new Thread(new Consumer("consumer3", queueSystem, "topic2"));
        Thread consumer8 = new Thread(new Consumer("consumer4", queueSystem, "topic2"));

        producer1.start();
        producer2.start();
        producer3.start();
        producer4.start();
        producer5.start();

        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();
        consumer5.start();

        consumer6.start();
        consumer7.start();
        consumer8.start();
    }
}
