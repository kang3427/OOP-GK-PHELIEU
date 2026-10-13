package model;
import payment.*;
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

    public Invoice(String invoiceId, PurchaseOrder purchaseOrder, Payment paymentMethod) {
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

        this.invoiceId = invoiceId.trim();
        this.purchaseOrder = purchaseOrder;
        this.paymentMethod = paymentMethod;
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


    // Phương thức in phiếu chi trả ra màn hình theo định dạng
    public void printInvoice() {
        DecimalFormat df = new DecimalFormat("#,###");

        System.out.println("=============================");
        System.out.println("         PHIẾU THU MUA PHẾ LIỆU");
        System.out.println("=============================");
        System.out.println("Mã phiếu: " + invoiceId);

        // Lấy thông tin khách hàng từ PurchaseOrder
        if (purchaseOrder != null && purchaseOrder.getCustomer() != null) {
            System.out.println("Khách: " + purchaseOrder.getCustomer());
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
            System.out.println("Tổng chi trả: " + df.format(purchaseOrder.calTotal()) + " VND");
        }

        // Hiển thị hình thức thanh toán
        System.out.println("Hình thức: " + getPaymentMethodName());
        System.out.println("=============================");
    }

    /**
     * Phương thức phụ trợ để chuyển đổi đối tượng Payment thành chuỗi tên hình thức thanh toán
     */
    private String getPaymentMethodName() {
        if (paymentMethod == null) return "Chưa xác định";

        String className = paymentMethod.getClass().getSimpleName();
        switch (className) {
            case "CashPayment":
                return "Tiền mặt";
            case "BankPayment":
                return "Chuyển khoản";
            case "EWalletPayment":
                return "Ví điện tử";
            default:
                return "Khác";
        }
    }
}
