package payment;

/**
 * Triển khai phương thức thanh toán bằng Tiền mặt (Cash Payment).
 * - Nghiệp vụ: Dùng cho các giao dịch trực tiếp tại cân vựa phế liệu,
 *   chi trả tiền mặt tức thì cho khách vãng lai hoặc hộ gom nhỏ lẻ.
 */

public class CashPayment implements Payment{
    private String ten_ThuNgan;
    public CashPayment(String ten_ThuNgan) {
        this.ten_ThuNgan = ten_ThuNgan;
    }
    @Override
    public void pay(double So_Tien) {
        System.out.println("[Thanh toan tien mat] cho khach hang:"
                + So_Tien + " Thu ngan: " + ten_ThuNgan);
    }
    @Override
    public String getPayment() {
        return "Tien mat (Thu ngan: " + ten_ThuNgan+")";
    }
}
