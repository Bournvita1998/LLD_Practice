package LLD_Rate_Limiter;

public interface RateLimiter {
    boolean allowRequest(String userId);
}
