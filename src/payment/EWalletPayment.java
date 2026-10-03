package payment;

/**
 * Triển khai phương thức thanh toán qua Ví điện tử (MoMo, ZaloPay, Viettel Money...).
 * - Nghiệp vụ: Hỗ trợ chuyển tiền nhanh qua số điện thoại hoặc mã QR ví điện tử
 *   của khách hàng khi thu gom tại chỗ.
 */

public class EWalletPayment implements Payment{
    private String viDienTu;
    private String soTaiKhoan;
    public EWalletPayment(String viDienTu, String soTaiKhoan) {
        this.viDienTu = viDienTu;
        this.soTaiKhoan = soTaiKhoan;
    }
    @Override
    public void pay(double So_Tien) {
        System.out.println("[Thanh toan vi dien tu] cho khach hang:"
        + So_Tien +" "+ viDienTu +" "+ soTaiKhoan);
    }
    @Override
    public String getPayment(){
        return "Vi dien tu: "+viDienTu+" So tai khoan: "+ soTaiKhoan;
    }
}
