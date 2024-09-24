package LLD_Rate_Limiter;

import java.util.concurrent.ConcurrentHashMap;

public class RateLimiterManager {
    private final ConcurrentHashMap<String, RateLimiter> userRateLimiters;
    private final RateLimiter rateLimiter;

    public RateLimiterManager(RateLimiter rateLimiter) {
        this.userRateLimiters = new ConcurrentHashMap<>();
        this.rateLimiter = rateLimiter;
    }

    public boolean isRequestAllowed(String userId) {
        return rateLimiter.allowRequest(userId);
    }

    public void addUserRateLimiter(String userId, RateLimiter rateLimiter) {
        userRateLimiters.put(userId, rateLimiter);
    }

    public boolean allowRequest(String userId) {
        return userRateLimiters.get(userId).allowRequest(userId);
    }
}
