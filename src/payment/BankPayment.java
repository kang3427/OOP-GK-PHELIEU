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
    private String payId;

    public BankPayment(String payId, String nganHang, String soTaiKhoan, String tenTaiKhoan) {
        if (tenTaiKhoan == null) {
            throw  new IllegalArgumentException("Tên tài khoản không được để trống!");
        }
        if (payId == null) {
            throw new IllegalArgumentException("Mã giao dịch không được để trống!");
        }
        if (nganHang == null) {
            throw new IllegalArgumentException("Ngân hàng không được để trống!");
        }
        if (soTaiKhoan == null) {
            throw new IllegalArgumentException("Số tài khoản không được để trống!");
        }

        this.nganHang = nganHang;
        this.soTaiKhoan = soTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
        this.payId = payId;
    }

    @Override
    public void pay(double So_Tien) {
        if  (So_Tien <= 0) {
            throw new IllegalArgumentException("Số tiền phải lớn hơn 0!");
        }
        System.out.println("[Chuyen khoan ngan hang] cho  khach hang: "
                + So_Tien +" | "+ payId +" | "+ nganHang +" | "+ soTaiKhoan +" | "+ tenTaiKhoan );
    }
    @Override
    public String getPayment() {
        return "Hình thức: Ngân hàng -> "+"Ma giao dich: " + payId + " Ngan hang: "+nganHang +" So tai khoan: "+ soTaiKhoan ;
    }
}
