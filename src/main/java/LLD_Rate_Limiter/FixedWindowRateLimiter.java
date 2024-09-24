package LLD_Rate_Limiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class FixedWindowRateLimiter implements RateLimiter {
    private final int maxRequests;
    private final long windowSizeInMillis;
    private final ConcurrentHashMap<String, UserRequestInfo> userRequestMap;
    private final ReentrantLock lock;

    public FixedWindowRateLimiter(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.userRequestMap = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
    }

    @Override
    public boolean allowRequest(String userId) {
        long currentTime = System.currentTimeMillis();
        userRequestMap.putIfAbsent(userId, new UserRequestInfo(0, currentTime));
        UserRequestInfo userInfo = userRequestMap.get(userId);

        lock.lock();
        try {
            if (currentTime - userInfo.getWindowStart() > windowSizeInMillis) {
                userInfo.setWindowStart(currentTime);
                userInfo.setRequestCount(0);
            }

            if (userInfo.getRequestCount() < maxRequests) {
                userInfo.incrementRequestCount();
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }
}

