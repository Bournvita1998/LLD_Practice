package LLD_Rate_Limiter;

public class RateLimiterMain {
    public static void main(String[] args) {
        RateLimiter fixedWindowRateLimiter = new FixedWindowRateLimiter(10, 60000); // 10 requests per minute
        RateLimiterManager manager = new RateLimiterManager(fixedWindowRateLimiter);

        String userId = "user1";
        for (int i = 0; i < 15; i++) {
            if (manager.isRequestAllowed(userId)) {
                System.out.println("Request " + (i + 1) + " allowed");
            } else {
                System.out.println("Request " + (i + 1) + " denied");
            }
        }
    }
}

