package reward;

public class CustomerReward implements Rewardable {
    private double rewardRate; // Tỷ lệ thưởng (ví dụ: 0.02 = 2%)

    public CustomerReward(double rewardRate) {
        this.rewardRate = rewardRate;
    }

    @Override
    public double calculateReward(double weight, double basePrice) {
        if (basePrice <= 0) return 0;
        return basePrice * rewardRate;
    }
}
