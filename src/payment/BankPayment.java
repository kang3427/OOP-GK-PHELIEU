package payment;

/**
 * Triển khai phương thức thanh toán qua Chuyển khoản Ngân hàng (Bank Transfer).
 * - Nghiệp vụ: Dùng cho các đơn hàng lớn, đại lý hoặc khách VIP,
 *   lưu trữ thông tin số tài khoản, ngân hàng thụ hưởng và mã giao dịch chuyển khoản.
 */

public class BankPayment implements Payment{
    private String nganHang;
    private String soTaiKhoan;
    private String tenTaiKhoan;
    public BankPayment(String nganHang, String soTaiKhoan, String tenTaiKhoan) {
        this.nganHang = nganHang;
        this.soTaiKhoan = soTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
    }
    @Override
    public void pay(double So_Tien) {
        System.out.println("[Chuyen khoan ngan hang] cho  khach hang:"
                + So_Tien +" "+ nganHang +" "+ soTaiKhoan +" "+ tenTaiKhoan);
    }
    @Override
    public String getPayment() {
        return "Ngan hang: "+nganHang +" So tai khoan: "+ soTaiKhoan;
    }
}
