package reward;

/**
 * Lớp triển khai chính sách tính thưởng / chiết khấu thực tế cho khách hàng (Customer Reward Module).
 * Triển khai interface Rewardable để tính toán tiền thưởng dựa trên tỷ lệ phần trăm (rewardRate)
 * hoặc các ưu đãi dành cho khách hàng bán phế liệu.
 */

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
