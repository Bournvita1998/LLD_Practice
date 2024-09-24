package LLD_NotificationService;

class ScheduledTask implements Comparable<ScheduledTask> {
    private final Runnable task;
    private final int priority;

    public ScheduledTask(Runnable task, int priority) {
        this.task = task;
        this.priority = priority;
    }

    public Runnable getTask() {
        return task;
    }

    @Override
    public int compareTo(ScheduledTask other) {
        return Integer.compare(other.priority, this.priority);
    }
}
