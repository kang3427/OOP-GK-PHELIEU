package payment;

/**
 * Triển khai phương thức thanh toán qua Chuyển khoản Ngân hàng (Bank Transfer).
 * - Nghiệp vụ: Dùng cho các đơn hàng lớn, đại lý hoặc khách VIP,
 *   lưu trữ thông tin số tài khoản, ngân hàng thụ hưởng và mã giao dịch chuyển khoản.
 */

public class ChuyenKhoan implements ThanhToan {
    private String nganHang;
    private String soTaiKhoan;
    private String tenTaiKhoan;
    public ChuyenKhoan(String nganHang, String soTaiKhoan, String tenTaiKhoan) {
        this.nganHang = nganHang;
        this.soTaiKhoan = soTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
    }
    @Override
    public void Thanhtoan(double So_Tien) {
        System.out.println("[Chuyển Khoản Ngân Hàng] cho khách hàng:"
                + So_Tien +" "+ nganHang +" "+ soTaiKhoan +" "+ tenTaiKhoan);
    }
    @Override
    public String layTenPhuongThuc() {
        return "Ngân Hàng: "+nganHang +" Số Tài Khoản: "+ soTaiKhoan;
    }
}
