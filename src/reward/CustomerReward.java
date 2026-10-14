package reward;

/**
 * Lớp triển khai chính sách tính thưởng / chiết khấu thực tế cho khách hàng (Customer Reward Module).
 * Triển khai interface Rewardable để tính toán tiền thưởng dựa trên tỷ lệ phần trăm (rewardRate)
 * hoặc các ưu đãi dành cho khách hàng bán phế liệu.
 */

public class CustomerReward implements Rewardable {
    private double rewardRate; // Tỷ lệ thưởng (ví dụ: 0.02 = 2%)

    public CustomerReward(double rewardRate) {
        if (rewardRate < 0 || rewardRate > 1.0) {
            throw new IllegalArgumentException("Tỷ lệ thưởng phải nằm trong khoảng từ 0.0 đến 1.0 (0% - 100%)!");
        }
        this.rewardRate = rewardRate;
    }

    @Override
    public double calculateReward(double weight, double basePrice) {
        if (basePrice <= 0) return 0;
        return basePrice * rewardRate;
    }
}
