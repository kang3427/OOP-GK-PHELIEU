package reward;

import enums.CustomerType;
import model.Customer;

public class CustomerReward implements Rewardable {
    private double rewardRate;

    // Khởi tạo tự động xác định tỷ lệ thưởng dựa trên loại khách hàng
    public CustomerReward(Customer customer) {
        if (customer == null || customer.getCustomerType() == null) {
            this.rewardRate = 0.0;
        } else if (customer.getCustomerType() == CustomerType.AGENCY) {
            this.rewardRate = 0.05; // Khách Đại lý thưởng 5%
        } else if (customer.getCustomerType() == CustomerType.REGULAR) {
            this.rewardRate = 0.01; // Khách Thường thưởng 1%
        } else if (customer.getCustomerType() == CustomerType.VIP){
            this.rewardRate = 0.03;
        } else {
            this.rewardRate = 0.0;
        }
    }

    // Vẫn giữ Constructor cũ nếu muốn tự nhập tỷ lệ thủ công
    public CustomerReward(double rewardRate) {
        if (rewardRate < 0 || rewardRate > 1.0) {
            throw new IllegalArgumentException("Tỷ lệ thưởng phải nằm trong khoảng từ 0.0 đến 1.0!");
        }
        this.rewardRate = rewardRate;
    }

    @Override
    public double calculateReward(double weight, double basePrice) {
        if (basePrice <= 0) return 0;
        return basePrice * rewardRate;
    }

    public double getRewardRate() {
        return rewardRate;
    }
}