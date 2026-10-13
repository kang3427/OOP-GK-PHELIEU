package reward;

import enums.CustomerType;
import model.Customer;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Chính sách ưu đãi dành cho nhà cung cấp phế liệu.
 *
 * Hỗ trợ:
 * 1. Chính sách mặc định theo loại nhà cung cấp:
 *    REGULAR, VIP, AGENCY.
 *
 * 2. Chính sách riêng theo mã nhà cung cấp.
 *    Chính sách riêng được ưu tiên hơn chính sách theo loại.
 *
 * 3. Hai hình thức thưởng:
 *    - Theo phần trăm tổng tiền phế liệu.
 *    - Theo khối lượng, đơn vị VNĐ/kg.
 *
 * Kết quả ưu đãi được lưu vào PurchaseOrder khi áp dụng,
 * để khi in lại hóa đơn không bị thay đổi theo chính sách mới.
 */
public final class SupplierBenefitPolicy {

    /**
     * Các hình thức tính ưu đãi.
     */
    public enum BenefitType {
        PERCENTAGE,
        PER_KILOGRAM
    }

    // Chính sách mặc định theo loại nhà cung cấp.
    private final Map<CustomerType, BenefitRule> typeRules =
            new EnumMap<>(CustomerType.class);

    // Chính sách riêng theo mã nhà cung cấp.
    private final Map<String, BenefitRule> customerRules =
            new HashMap<>();

    /**
     * Thiết lập chính sách thưởng theo phần trăm
     * cho một loại nhà cung cấp.
     *
     * Ví dụ: rate = 0.05 nghĩa là thưởng 5%.
     */
    public void setTypePercentagePolicy(
            CustomerType type,
            String name,
            double rate) {

        requireCustomerType(type);

        typeRules.put(
                type,
                BenefitRule.percentage(name, rate)
        );
    }

    /**
     * Thiết lập chính sách thưởng theo khối lượng
     * cho một loại nhà cung cấp.
     *
     * Ví dụ: amountPerKg = 500 nghĩa là thưởng 500 VNĐ/kg.
     */
    public void setTypePerKilogramPolicy(
            CustomerType type,
            String name,
            double amountPerKg) {

        requireCustomerType(type);

        typeRules.put(
                type,
                BenefitRule.perKilogram(name, amountPerKg)
        );
    }

    /**
     * Thiết lập ưu đãi riêng theo mã nhà cung cấp,
     * tính theo phần trăm tổng tiền phế liệu.
     */
    public void setCustomerPercentagePolicy(
            String customerId,
            String name,
            double rate) {

        customerRules.put(
                normalizeCustomerId(customerId),
                BenefitRule.percentage(name, rate)
        );
    }

    /**
     * Thiết lập ưu đãi riêng theo mã nhà cung cấp,
     * tính theo số tiền thưởng trên mỗi kg.
     */
    public void setCustomerPerKilogramPolicy(
            String customerId,
            String name,
            double amountPerKg) {

        customerRules.put(
                normalizeCustomerId(customerId),
                BenefitRule.perKilogram(name, amountPerKg)
        );
    }

    /**
     * Xóa chính sách riêng của một nhà cung cấp.
     * Nếu có chính sách theo loại, hệ thống sẽ quay về chính sách đó.
     */
    public void removeCustomerPolicy(String customerId) {
        customerRules.remove(
                normalizeCustomerId(customerId)
        );
    }

    /**
     * Tìm và tính chính sách ưu đãi áp dụng cho nhà cung cấp.
     *
     * Thứ tự ưu tiên:
     * 1. Chính sách riêng theo customerId.
     * 2. Chính sách theo CustomerType.
     * 3. Không có chính sách thì tiền thưởng bằng 0.
     */
    public BenefitResult evaluate(
            Customer customer,
            double totalWeightKg,
            double baseAmount) {

        validateNonNegativeFinite(
                totalWeightKg,
                "Tổng khối lượng"
        );

        validateNonNegativeFinite(
                baseAmount,
                "Tổng tiền phế liệu"
        );

        // Khách vãng lai không có hồ sơ nhà cung cấp.
        if (customer == null) {
            return BenefitResult.none(
                    "Không áp dụng cho khách vãng lai",
                    "Khách vãng lai không có hồ sơ để tra chính sách"
            );
        }

        // Ưu tiên chính sách riêng theo mã khách hàng.
        BenefitRule rule =
                customerRules.get(customer.getCustomerId());

        if (rule != null) {
            return rule.toResult(
                    totalWeightKg,
                    baseAmount,
                    "Ưu đãi riêng theo mã nhà cung cấp "
                            + customer.getCustomerId()
            );
        }

        // Nếu không có chính sách riêng, tra theo loại nhà cung cấp.
        rule = typeRules.get(customer.getCustomerType());

        if (rule != null) {
            return rule.toResult(
                    totalWeightKg,
                    baseAmount,
                    "Ưu đãi theo loại nhà cung cấp "
                            + customer.getCustomerType()
            );
        }

        // Không có chính sách phù hợp.
        return BenefitResult.none(
                "Không áp dụng ưu đãi",
                "Chưa cấu hình chính sách cho nhà cung cấp này"
        );
    }

    /**
     * Kiểm tra loại nhà cung cấp.
     */
    private static void requireCustomerType(CustomerType type) {
        if (type == null) {
            throw new IllegalArgumentException(
                    "Loại nhà cung cấp không được null."
            );
        }
    }

    /**
     * Kiểm tra và chuẩn hóa mã nhà cung cấp.
     */
    private static String normalizeCustomerId(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Mã nhà cung cấp không được để trống."
            );
        }

        return customerId.trim();
    }

    /**
     * Kiểm tra tên chính sách.
     */
    private static String normalizeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Tên chính sách không được để trống."
            );
        }

        return name.trim();
    }

    /**
     * Kiểm tra giá trị đầu vào phải hữu hạn và không âm.
     */
    private static void validateNonNegativeFinite(
            double value,
            String label) {

        if (!Double.isFinite(value) || value < 0) {
            throw new IllegalArgumentException(
                    label + " phải là số hữu hạn không âm."
            );
        }
    }

    /**
     * Lớp nội bộ lưu cấu hình của một chính sách ưu đãi.
     */
    private static final class BenefitRule {

        private final String name;
        private final BenefitType type;
        private final double value;

        private BenefitRule(
                String name,
                BenefitType type,
                double value) {

            this.name = normalizeName(name);
            this.type = type;

            if (!Double.isFinite(value)
                    || value < 0
                    || (type == BenefitType.PERCENTAGE && value > 1)) {

                throw new IllegalArgumentException(
                        type == BenefitType.PERCENTAGE
                                ? "Tỷ lệ thưởng phải nằm trong khoảng từ 0 đến 1."
                                : "Mức thưởng theo kg phải là số hữu hạn không âm."
                );
            }

            this.value = value;
        }

        /**
         * Tạo chính sách thưởng theo phần trăm.
         */
        static BenefitRule percentage(String name, double rate) {
            return new BenefitRule(
                    name,
                    BenefitType.PERCENTAGE,
                    rate
            );
        }

        /**
         * Tạo chính sách thưởng theo khối lượng.
         */
        static BenefitRule perKilogram(
                String name,
                double amountPerKg) {

            return new BenefitRule(
                    name,
                    BenefitType.PER_KILOGRAM,
                    amountPerKg
            );
        }

        /**
         * Tính tiền thưởng và tạo kết quả ưu đãi.
         */
        BenefitResult toResult(
                double weightKg,
                double baseAmount,
                String source) {

            double rawAmount;

            if (type == BenefitType.PERCENTAGE) {
                rawAmount = baseAmount * value;
            } else {
                rawAmount = weightKg * value;
            }

            // Làm tròn tiền thưởng về số VNĐ nguyên.
            double roundedAmount = Math.round(rawAmount);

            String calculation;

            if (type == BenefitType.PERCENTAGE) {
                calculation = String.format(
                        "%.2f%% × tổng tiền phế liệu",
                        value * 100
                );
            } else {
                calculation = String.format(
                        "%.2f VNĐ/kg × %.2f kg",
                        value,
                        weightKg
                );
            }

            return new BenefitResult(
                    name,
                    source,
                    calculation,
                    type,
                    value,
                    baseAmount,
                    weightKg,
                    roundedAmount
            );
        }
    }

    /**
     * Kết quả ưu đãi được lưu trên PurchaseOrder.
     *
     * Dùng các trường final để không thay đổi kết quả
     * sau khi đã tính và áp dụng vào phiếu thu mua.
     */
    public static final class BenefitResult {

        private final String policyName;
        private final String policySource;
        private final String calculationDescription;
        private final BenefitType benefitType;
        private final double configuredValue;
        private final double baseAmount;
        private final double totalWeightKg;
        private final double bonusAmount;

        private BenefitResult(
                String policyName,
                String policySource,
                String calculationDescription,
                BenefitType benefitType,
                double configuredValue,
                double baseAmount,
                double totalWeightKg,
                double bonusAmount) {

            this.policyName = policyName;
            this.policySource = policySource;
            this.calculationDescription = calculationDescription;
            this.benefitType = benefitType;
            this.configuredValue = configuredValue;
            this.baseAmount = baseAmount;
            this.totalWeightKg = totalWeightKg;
            this.bonusAmount = bonusAmount;
        }

        /**
         * Tạo kết quả không có ưu đãi.
         */
        private static BenefitResult none(
                String policyName,
                String source) {

            return new BenefitResult(
                    policyName,
                    source,
                    "Không có tiền thưởng",
                    null,
                    0,
                    0,
                    0,
                    0
            );
        }

        public String getPolicyName() {
            return policyName;
        }

        public String getPolicySource() {
            return policySource;
        }

        public String getCalculationDescription() {
            return calculationDescription;
        }

        public BenefitType getBenefitType() {
            return benefitType;
        }

        public double getConfiguredValue() {
            return configuredValue;
        }

        public double getBaseAmount() {
            return baseAmount;
        }

        public double getTotalWeightKg() {
            return totalWeightKg;
        }

        public double getBonusAmount() {
            return bonusAmount;
        }
    }
}