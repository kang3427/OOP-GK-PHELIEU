package payment;

import model.Employee;

/** Mô phỏng chi trả tiền mặt cho khách bán phế liệu. */
public class CashPayment implements Payment {
    private final String payId;
    private final Employee employee;
    private final String tenKhachHang;

    public CashPayment(String tenKhachHang, String payId, Employee employee) {
        if (tenKhachHang == null || tenKhachHang.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống.");
        }
        if (payId == null || payId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã giao dịch không được để trống.");
        }
        this.payId = payId.trim();
        this.employee = employee;
        this.tenKhachHang = tenKhachHang.trim();
    }

    public CashPayment(String tenKhachHang, String payId) {
        this(tenKhachHang, payId, null);
    }

    @Override
    public void pay(double soTien) {
        Payment.validateAmount(soTien);
        System.out.println("[Thanh toan tien mat] cho khach hang:"
                + soTien + " " + tenKhachHang + " " + payId + employeeInfo());
    }

    @Override
    public String getPayment() {
        return " Ten khach hang: " + tenKhachHang
                + " Ma giao dich: " + payId + employeeInfo();
    }

    private String employeeInfo() {
        return employee == null ? "" : " nv: " + employee.getThongTin();
    }
}
