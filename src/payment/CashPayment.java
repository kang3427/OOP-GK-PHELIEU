package payment;

/**
 * Triển khai phương thức thanh toán bằng Tiền mặt (Cash Payment).
 * - Nghiệp vụ: Dùng cho các giao dịch trực tiếp tại cân vựa phế liệu,
 *   chi trả tiền mặt tức thì cho khách vãng lai hoặc hộ gom nhỏ lẻ.
 */

public class CashPayment implements Payment{
    private String payId;
    private String tenKhachHang;

    public CashPayment(String payId, String tenKhachHang) {
        if (tenKhachHang == null) {
            throw  new IllegalArgumentException("Tên khách hàng không được để trống!");
        }
        if (payId == null) {
            throw new IllegalArgumentException("Mã giao dịch không được để trống!");
        }

        this.payId = payId;
        this.tenKhachHang = tenKhachHang;
    }

    @Override
    public void pay(double So_Tien) {
        if  (So_Tien <= 0) {
            throw new IllegalArgumentException("Số tiền phải lớn hơn 0!");
        }
        System.out.println("[Thanh toan tien mat] cho khach hang: "
                + So_Tien + " | " + payId +" | "+ tenKhachHang);
    }

    @Override
    public String getPayment() {
        return "Hình thức: Tiền mặt -> " + "Mã giao dịch: " + payId + " Tên khách hàng: " + tenKhachHang;
    }
}
