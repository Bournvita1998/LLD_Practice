package LLD_Rate_Limiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowRateLimiter implements RateLimiter {
    private final int maxRequests;
    private final long windowSizeInMillis;
    private final ConcurrentHashMap<String, Queue<Long>> userRequestMap;
    private final ReentrantLock lock;

    public SlidingWindowRateLimiter(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.userRequestMap = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
    }

    @Override
    public boolean allowRequest(String userId) {
        long currentTime = System.currentTimeMillis();
        userRequestMap.putIfAbsent(userId, new LinkedList<>());
        Queue<Long> requestTimes = userRequestMap.get(userId);

        lock.lock();
        try {
            while (!requestTimes.isEmpty() && currentTime - requestTimes.peek() > windowSizeInMillis) {
                requestTimes.poll();
            }

            if (requestTimes.size() < maxRequests) {
                requestTimes.offer(currentTime);
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }
}

