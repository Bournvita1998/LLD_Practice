package LLD_Uber.Payments;

public class PricingModel {
    private double baseFare;
    private double perKmRate;
    private double perMinuteRate;

    public PricingModel(double baseFare, double perKmRate, double perMinuteRate) {
        this.baseFare = baseFare;
        this.perKmRate = perKmRate;
        this.perMinuteRate = perMinuteRate;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public double getPerKmRate() {
        return perKmRate;
    }

    public double getPerMinuteRate() {
        return perMinuteRate;
    }

    // Getters and setters
}

