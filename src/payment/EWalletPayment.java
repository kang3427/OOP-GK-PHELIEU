package payment;

import model.Employee;

/** Mô phỏng chi trả qua ví điện tử. */
public class EWalletPayment implements Payment {
    private final String viDienTu;
    private final String soTaiKhoan;
    private final String payId;
    private final Employee employee;
    private final String tenTaiKhoan;

    public EWalletPayment(String viDienTu, String soTaiKhoan, String tenTaiKhoan,
                          String payId, Employee employee) {
        this.viDienTu = requireText(viDienTu, "Tên ví điện tử");
        this.soTaiKhoan = requireText(soTaiKhoan, "Số tài khoản/số điện thoại");
        this.tenTaiKhoan = requireText(tenTaiKhoan, "Tên chủ tài khoản");
        this.payId = requireText(payId, "Mã giao dịch");
        this.employee = employee;
    }

    public EWalletPayment(String viDienTu, String soTaiKhoan, String tenTaiKhoan, String payId) {
        this(viDienTu, soTaiKhoan, tenTaiKhoan, payId, null);
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
        System.out.println("[Thanh toan vi dien tu] cho khach hang:"
                + soTien + " " + viDienTu + " " + soTaiKhoan
                + " " + tenTaiKhoan + " " + payId + employeeInfo());
    }

    @Override
    public String getPayment() {
        return "Vi dien tu: " + viDienTu
                + " So tai khoan: " + soTaiKhoan
                + " Ma giao dich: " + payId
                + " Ten tai khoan: " + tenTaiKhoan + employeeInfo();
    }

    private String employeeInfo() {
        return employee == null ? "" : " nv: " + employee.getThongTin();
    }
}
