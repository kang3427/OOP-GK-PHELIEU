package payment;
import model.Employee;
import reward.CustomerReward;

/**
 * Triển khai phương thức thanh toán bằng Tiền mặt (Cash Payment).
 * - Nghiệp vụ: Dùng cho các giao dịch trực tiếp tại cân vựa phế liệu,
 *   chi trả tiền mặt tức thì cho khách vãng lai hoặc hộ gom nhỏ lẻ.
 */

public class CashPayment implements Payment{
    private String payId;
    private Employee employee;
    private String tenKhachHang;
    private CustomerReward Reward;

    public CashPayment(String tenKhachHang, String payId, Employee employee, CustomerReward Reward) {
        this.payId = payId;
        this.employee = employee;
        this.tenKhachHang = tenKhachHang;
        this.Reward = Reward;
    }

    @Override
    public void pay(double So_Tien) {
        double T_So_Tien=So_Tien+Reward.calculateReward(0,So_Tien);
        System.out.println("[Thanh toan tien mat] cho khach hang:"
                + T_So_Tien +" "+ tenKhachHang + " " + payId +"\n"+ "NV -> " + employee.getThongTin());
    }
    @Override
    public String getPayment() {
        return " Ten khach hang: "+ tenKhachHang + "Ma giao dich: " + payId +"\n"+ employee.getThongT();
    }
}
