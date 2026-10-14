package payment;
import model.Employee;
import reward.CustomerReward;

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
    private Employee employee;
    private CustomerReward Reward;

    public BankPayment(String nganHang, String soTaiKhoan, String tenTaiKhoan, String payId, Employee employee, CustomerReward  Reward) {
        this.nganHang = nganHang;
        this.soTaiKhoan = soTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
        this.payId = payId;
        this.employee = employee;
        this.Reward = Reward;
    }

    @Override
    public void pay(double So_Tien) {
        double T_So_Tien=So_Tien+Reward.calculateReward(0,So_Tien);
        System.out.println("[Chuyen khoan ngan hang] cho  khach hang:"
                + T_So_Tien +" "+ nganHang +" "+ soTaiKhoan +" "+ tenTaiKhoan + " " + payId +"\n"+ "NV -> " + employee.getThongTin());
    }
    @Override
    public String getPayment() {
        return "Ngan hang: "+nganHang +" So tai khoan: "+ soTaiKhoan + " Ma giao dich: " + payId +"\n"+ employee.getThongT();
    }
}
