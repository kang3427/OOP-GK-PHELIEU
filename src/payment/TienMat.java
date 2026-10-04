package payment;

/**
 * Triển khai phương thức thanh toán bằng Tiền mặt (Cash Payment).
 * - Nghiệp vụ: Dùng cho các giao dịch trực tiếp tại cân vựa phế liệu,
 *   chi trả tiền mặt tức thì cho khách vãng lai hoặc hộ gom nhỏ lẻ.
 */

public class TienMat implements ThanhToan {
    private String ten_ThuNgan;
    public TienMat(String ten_ThuNgan) {
        this.ten_ThuNgan = ten_ThuNgan;
    }
    @Override
    public void Thanhtoan(double So_Tien) {
        System.out.println("[Thanh Toán Tiền Mặt] cho khách hàng:"
                + So_Tien + " Thu ngân: " + ten_ThuNgan);
    }
    @Override
    public String layTenPhuongThuc() {
        return "Tiền mặt (Thu ngân: " + ten_ThuNgan+")";

    }
}
