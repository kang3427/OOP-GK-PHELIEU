package payment;

/**
 * Triển khai phương thức thanh toán qua Ví điện tử (MoMo, ZaloPay, Viettel Money...).
 * - Nghiệp vụ: Hỗ trợ chuyển tiền nhanh qua số điện thoại hoặc mã QR ví điện tử
 *   của khách hàng khi thu gom tại chỗ.
 */

public class ViDienTu implements ThanhToan {
    private String viDienTu;
    private String soTaiKhoan;
    public ViDienTu(String viDienTu, String soTaiKhoan) {
        this.viDienTu = viDienTu;
        this.soTaiKhoan = soTaiKhoan;
    }
    @Override
    public void Thanhtoan(double So_Tien) {
        System.out.println("[Thanh toán ví điện tử] cho khách hàng:"
        + So_Tien +" "+ viDienTu +" "+ soTaiKhoan);
    }
    @Override
    public String layTenPhuongThuc(){
        return "Ví điện tử: "+viDienTu+" Số tài khoản: "+ soTaiKhoan;
    }
}
