package LLD_NotificationService;

import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Scheduler {
    private final PriorityQueue<ScheduledTask> taskQueue;
    private final ExecutorService executorService;

    public Scheduler() {
        this.taskQueue = new PriorityQueue<>();
        this.executorService = Executors.newSingleThreadExecutor();
        start();
    }

    private void start() {
        executorService.submit(() -> {
            while (true) {
                ScheduledTask task = taskQueue.poll();
                if (task != null) {
                    task.getTask().run();
                }
                try {
                    Thread.sleep(100); // polling interval
                } catch (InterruptedException ignored) {}
            }
        });
    }

    public void schedule(Runnable task, int priority) {
        taskQueue.offer(new ScheduledTask(task, priority));
    }
}
