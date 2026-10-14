package payment;
import model.Employee;
import reward.CustomerReward;

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
    private CustomerReward Reward;

    public EWalletPayment(String viDienTu, String soTaiKhoan, String tenTaiKhoan, String payId, Employee employee, CustomerReward  Reward) {
        this.viDienTu = viDienTu;
        this.soTaiKhoan = soTaiKhoan;
        this.payId = payId;
        this.employee = employee;
        this.tenTaiKhoan = tenTaiKhoan;
        this.Reward = Reward;
    }

    @Override
    public void pay(double So_Tien) {
        double T_So_Tien=So_Tien+Reward.calculateReward(0,So_Tien);
        System.out.println("[Thanh toan vi dien tu] cho khach hang:"
        + T_So_Tien +" "+ viDienTu +" "+ soTaiKhoan + " "+ tenTaiKhoan + " " + payId +"\n"+ "NV -> " + employee.getThongTin());
    }
    @Override
    public String getPayment(){
        return "Vi dien tu: "+viDienTu+" So tai khoan: "+ soTaiKhoan + " Ma giao dich: " + payId + " Ten tai khoan: "+ tenTaiKhoan +"\n"+ employee.getThongT();
    }
}
