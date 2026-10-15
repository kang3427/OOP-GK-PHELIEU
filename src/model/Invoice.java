package model;
import payment.*;
import reward.*;
import java.text.DecimalFormat;
/**
 * Lớp đại diện cho Hóa đơn / Biên nhận thanh toán hoàn xuất cho khách hàng.
 * Khởi tạo từ PurchaseOrder để ghi nhận thông tin thanh toán, hình thức trả tiền và xác nhận hoàn tất thu mua.
 */

public class Invoice {
    // Thể hiện tính chất Encapsulation thông qua các thuộc tính private
    private String invoiceId;
    private PurchaseOrder purchaseOrder;
    private Payment paymentMethod;
    private Employee  employee;
    private CustomerReward reward;

    public Invoice(String invoiceId, PurchaseOrder purchaseOrder, Payment paymentMethod, Employee employee, CustomerReward reward) {
        // 1. Kiểm tra Mã hóa đơn (invoiceId) không được null hoặc rỗng
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn (invoiceId) không được để trống!");
        }

        // 2. Kiểm tra Đơn thu mua (purchaseOrder) không được null
        if (purchaseOrder == null) {
            throw new IllegalArgumentException("Đơn thu mua (purchaseOrder) không được để trống!");
        }

        // 3. Kiểm tra Phương thức thanh toán (paymentMethod) không được null
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Phương thức thanh toán (paymentMethod) không được để trống!");
        }

        // 4. Kiểm tra Thông tin nhân viên (employee) không được null
        if (employee == null) {
            throw new IllegalArgumentException("Thông tin nhân viên (employee) không được để trống!");
        }

        // 5. Kiểm tra Thưởng (reward) không được null
        if (reward == null) {
            throw new IllegalArgumentException("Thưởng (reward) không được để trống");
        }

        this.invoiceId = invoiceId.trim();
        this.purchaseOrder = purchaseOrder;
        this.paymentMethod = paymentMethod;
        this.employee = employee;
        this.reward = reward;
    }

    // Getters & Setters
    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Payment getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Payment paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Employee getEmployee() {return employee; }

    public void setEmployee(Employee employee) {this.employee = employee; }


    // Phương thức in phiếu chi trả ra màn hình theo định dạng
    public void printInvoice() {
        DecimalFormat df = new DecimalFormat("#,###");

        System.out.println("=============================");
        System.out.println("         PHIẾU THU MUA PHẾ LIỆU");
        System.out.println("=============================");
        System.out.println("Mã phiếu: " + invoiceId);
        // Hiển thị thông tin nhân viên
        System.out.println("Nhân Viên: "+employee.getThongTin());
        // Lấy thông tin khách hàng từ PurchaseOrder
        if (purchaseOrder != null && purchaseOrder.getCustomer() != null) {
            System.out.println("Khách: " + purchaseOrder.getCustomer().getName());
        } else {
            System.out.println("Khách: Khách vãng lai");
        }

        // Liệt kê chi tiết các mặt hàng phế liệu thu mua
        if (purchaseOrder != null && purchaseOrder.getItems() != null) {
            for (PurchaseOrderItem item : purchaseOrder.getItems()) {
                // Định dạng hiển thị tên phế liệu và khối lượng (ví dụ: Sắt vụn x 25 kg)
                System.out.printf("%-12s x %.0f kg\n", item.getItem().getName(), item.getWeightKg());
            }
            // Hiển thị tổng chi trả
            double baseTotal = purchaseOrder.calTotal();
            double totalWeight = purchaseOrder.getTotalWeight();
            double rewardAmount = reward.calculateReward(totalWeight, baseTotal);
            double finalTotal = baseTotal + rewardAmount;
            System.out.println("Tiền hàng:    " + df.format(baseTotal) + " VNĐ");
            System.out.println("Tiền thưởng:  " + df.format(rewardAmount) + " VNĐ");
            System.out.println("TỔNG CHI TRẢ: " + df.format(finalTotal) + " VNĐ");
            paymentMethod.pay(finalTotal);
        }
        System.out.println("=============================");
    }
}
