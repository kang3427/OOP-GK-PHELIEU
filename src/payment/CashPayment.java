package payment;

/**
 * Triển khai phương thức thanh toán bằng Tiền mặt (Cash Payment).
 * - Nghiệp vụ: Dùng cho các giao dịch trực tiếp tại cân vựa phế liệu,
 *   chi trả tiền mặt tức thì cho khách vãng lai hoặc hộ gom nhỏ lẻ.
 */

public class CashPayment implements Payment{
    private String tenThuNgan;
    private String payId;

    public CashPayment(String tenThuNgan,  String payId) {
        this.tenThuNgan = tenThuNgan;
        this.payId = payId;
    }
    @Override
    public void pay(double So_Tien) {
        System.out.println("[Thanh toan tien mat] cho khach hang:"
                + So_Tien + " Thu ngan: " + tenThuNgan + " Ma giao dich: " + payId);
    }
    @Override
    public String getPayment() {
        return "Tien mat (Thu ngan: " + tenThuNgan+")" + " Ma giao dich: " + payId;
    }
}
