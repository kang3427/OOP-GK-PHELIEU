package reward;

/**
 * Interface định nghĩa khả năng tích điểm thưởng (Rewardable System) cho giao dịch thu gom phế liệu.
 * Các mặt hàng phế liệu giá trị cao hoặc đạt chuẩn an toàn/nguồn gốc (như Kim loại màu, Điện tử sạch)
 * sẽ triển khai interface này để tính toán số điểm thưởng tích lũy cộng cho khách hàng.
 */

@FunctionalInterface
public interface Rewardable {
    /*
    weight tong khoi luong phe lieu da ban(kg)
    basePrice tong gia tri don hang(VND)
    */
    double calculateReward(double weight, double basePrice);
}
