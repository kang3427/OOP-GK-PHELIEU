package payment;

/**
 * Hợp đồng chung cho các phương thức chi trả.
 * Đây là mô phỏng console; khi tích hợp cổng thanh toán thật cần thay phần pay()
 * bằng kết quả giao dịch từ nhà cung cấp thanh toán.
 */
public interface Payment {
    void pay(double soTien);

    String getPayment();

    /** Dùng chung để mọi phương thức thanh toán từ chối số tiền âm/NaN/vô hạn. */
    static void validateAmount(double soTien) {
        if (!Double.isFinite(soTien) || soTien < 0) {
            throw new IllegalArgumentException("Số tiền thanh toán phải là số hữu hạn không âm.");
        }
    }
}
