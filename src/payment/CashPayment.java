package payment;
import model.Employee;

/**
 * Triển khai phương thức thanh toán bằng Tiền mặt (Cash Payment).
 * - Nghiệp vụ: Dùng cho các giao dịch trực tiếp tại cân vựa phế liệu,
 *   chi trả tiền mặt tức thì cho khách vãng lai hoặc hộ gom nhỏ lẻ.
 */

public class CashPayment implements Payment{
    private String payId;
    private Employee employee;
    private String tenKhachHang;

    public CashPayment(String tenKhachHang, String payId, Employee employee) {
        this.payId = payId;
        this.employee = employee;
        this.tenKhachHang = tenKhachHang;
    }
    public CashPayment(String tenKhachHang, String payId) {
        this.payId = payId;
        this.tenKhachHang = tenKhachHang;
    }

    @Override
    public void pay(double So_Tien) {
        System.out.println("[Thanh toan tien mat] cho khach hang:"
                + So_Tien +" "+ tenKhachHang + " " + payId);
    }
    @Override
    public String getPayment() {
        String thongTinNv="";
        if (employee!=null)
        {
            thongTinNv=employee.getThongTin();
        }
        return " Ten khach hang: "+ tenKhachHang + "Ma giao dich: " + payId +"\n"+ thongTinNv;
    }
}
