package payment;
import model.Employee;

/**
 * Triển khai phương thức thanh toán qua Ví điện tử (MoMo, ZaloPay, Viettel Money...).
 * - Nghiệp vụ: Hỗ trợ chuyển tiền nhanh qua số điện thoại hoặc mã QR ví điện tử
 *   của khách hàng khi thu gom tại chỗ.
 */

public class EWalletPayment implements Payment{
    private String viDienTu;
    private String soTaiKhoan;
    private String payId;
    private Employee employee;
    private String tenTaiKhoan;

    public EWalletPayment(String viDienTu, String soTaiKhoan, String tenTaiKhoan, String payId, Employee employee) {
        this.viDienTu = viDienTu;
        this.soTaiKhoan = soTaiKhoan;
        this.payId = payId;
        this.employee = employee;
        this.tenTaiKhoan = tenTaiKhoan;
    }
    public EWalletPayment(String viDienTu, String soTaiKhoan, String tenTaiKhoan, String payId) {
        this.viDienTu = viDienTu;
        this.soTaiKhoan = soTaiKhoan;
        this.payId = payId;
        this.tenTaiKhoan = tenTaiKhoan;
    }

    @Override
    public void pay(double So_Tien) {
        System.out.println("[Thanh toan vi dien tu] cho khach hang:"
        + So_Tien +" "+ viDienTu +" "+ soTaiKhoan + " "+ tenTaiKhoan + " " + payId);
    }
    @Override
    public String getPayment(){
        String thongTinNv="";
        if (employee!=null)
        {
            thongTinNv=employee.getThongTin();
        }
        return "Vi dien tu: "+viDienTu+" So tai khoan: "+ soTaiKhoan + " Ma giao dich: " + payId + " Ten tai khoan: "+ tenTaiKhoan +"\n"+ thongTinNv;
    }
}
