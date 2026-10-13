package reward;

/**
 * Chính sách thưởng theo tỷ lệ phần trăm trên giá trị giao dịch.
 * rewardRate thuộc khoảng [0, 1], ví dụ 0.05 tương ứng 5%.
 */
public class CustomerReward implements Rewardable {
    private final double rewardRate;

    public CustomerReward(double rewardRate) {
        if (!Double.isFinite(rewardRate) || rewardRate < 0 || rewardRate > 1) {
            throw new IllegalArgumentException("Tỷ lệ thưởng phải nằm trong khoảng từ 0 đến 1.");
        }
        this.rewardRate = rewardRate;
    }

    @Override
    public double calculateReward(double weight, double basePrice) {
        if (!Double.isFinite(weight) || weight < 0) {
            throw new IllegalArgumentException("Khối lượng tính thưởng phải là số hữu hạn không âm.");
        }
        if (!Double.isFinite(basePrice) || basePrice < 0) {
            throw new IllegalArgumentException("Giá trị giao dịch phải là số hữu hạn không âm.");
        }
        return basePrice * rewardRate;
    }
}
