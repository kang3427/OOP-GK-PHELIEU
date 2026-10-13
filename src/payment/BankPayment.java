package payment;

import model.Employee;

/** Mô phỏng chi trả qua chuyển khoản ngân hàng. */
public class BankPayment implements Payment {
    private final String nganHang;
    private final String soTaiKhoan;
    private final String tenTaiKhoan;
    private final String payId;
    private final Employee employee;

    public BankPayment(String nganHang, String soTaiKhoan, String tenTaiKhoan,
                       String payId, Employee employee) {
        this.nganHang = requireText(nganHang, "Tên ngân hàng");
        this.soTaiKhoan = requireText(soTaiKhoan, "Số tài khoản");
        this.tenTaiKhoan = requireText(tenTaiKhoan, "Tên chủ tài khoản");
        this.payId = requireText(payId, "Mã giao dịch");
        this.employee = employee;
    }

    public BankPayment(String nganHang, String soTaiKhoan, String tenTaiKhoan, String payId) {
        this(nganHang, soTaiKhoan, tenTaiKhoan, payId, null);
    }

    private static String requireText(String value, String label) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(label + " không được để trống.");
        }
        return value.trim();
    }

    @Override
    public void pay(double soTien) {
        Payment.validateAmount(soTien);
        System.out.println("[Chuyen khoan ngan hang] cho khach hang:"
                + soTien + " " + nganHang + " " + soTaiKhoan
                + " " + tenTaiKhoan + " " + payId + employeeInfo());
    }

    @Override
    public String getPayment() {
        return "Ngan hang: " + nganHang
                + " So tai khoan: " + soTaiKhoan
                + " Ma giao dich: " + payId + employeeInfo();
    }

    private String employeeInfo() {
        return employee == null ? "" : " nv: " + employee.getThongTin();
    }
}
