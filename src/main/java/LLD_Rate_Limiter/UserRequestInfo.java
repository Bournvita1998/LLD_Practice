package LLD_Rate_Limiter;

public class UserRequestInfo {
    private int requestCount;
    private long windowStart;

    public UserRequestInfo(int requestCount, long windowStart) {
        this.requestCount = requestCount;
        this.windowStart = windowStart;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public void incrementRequestCount() {
        this.requestCount++;
    }

    public long getWindowStart() {
        return windowStart;
    }

    public void setWindowStart(long windowStart) {
        this.windowStart = windowStart;
    }
}
