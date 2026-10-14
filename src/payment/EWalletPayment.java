package payment;

/**
 * Triển khai phương thức thanh toán qua Ví điện tử (MoMo, ZaloPay, Viettel Money...).
 * - Nghiệp vụ: Hỗ trợ chuyển tiền nhanh qua số điện thoại hoặc mã QR ví điện tử
 *   của khách hàng khi thu gom tại chỗ.
 */

public class EWalletPayment implements Payment{
    private String viDienTu;
    private String soTaiKhoan;
    private String payId;
    private String tenTaiKhoan;

    public EWalletPayment(String payId, String viDienTu, String soTaiKhoan, String tenTaiKhoan) {
        if (tenTaiKhoan == null) {
            throw  new IllegalArgumentException("Tên tài khoản không được để trống!");
        }
        if (payId == null) {
            throw new IllegalArgumentException("Mã giao dịch không được để trống!");
        }
        if (viDienTu == null) {
            throw new IllegalArgumentException("Ví điện tử không được để trống!");
        }
        if (soTaiKhoan == null) {
            throw new IllegalArgumentException("Số tài khoản không được để trống!");
        }

        this.viDienTu = viDienTu;
        this.soTaiKhoan = soTaiKhoan;
        this.payId = payId;
        this.tenTaiKhoan = tenTaiKhoan;
    }

    @Override
    public void pay(double So_Tien) {
        if  (So_Tien <= 0) {
            throw new IllegalArgumentException("Số tiền phải lớn hơn 0!");
        }
        System.out.println("[Thanh toan vi dien tu] cho khach hang: "
        + So_Tien + " " + payId +" | "+ viDienTu +" | "+ soTaiKhoan + " | "+ tenTaiKhoan );
    }
    @Override
    public String getPayment(){
        return "Hình thức: Ví điện tử -> " + "Ma giao dich: " + payId + " Vi dien tu: "+viDienTu+" So tai khoan: "+ soTaiKhoan +  " Ten tai khoan: "+ tenTaiKhoan;
    }
}
