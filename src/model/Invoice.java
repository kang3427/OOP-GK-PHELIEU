package model;

import enums.OrderStatus;
import payment.*;
import java.text.DecimalFormat;

/**
 * Hóa đơn/biên nhận của một phiếu thu mua.
 * In hóa đơn chỉ hiển thị nội dung; processPayment() mới thực hiện chi trả và
 * chuyển phiếu sang COMPLETED sau khi phương thức thanh toán chạy thành công.
 */
public class Invoice {
    private String invoiceId;
    private PurchaseOrder purchaseOrder;
    private Payment paymentMethod;

    public Invoice(String invoiceId, PurchaseOrder purchaseOrder, Payment paymentMethod) {
        setInvoiceId(invoiceId);
        setPurchaseOrder(purchaseOrder);
        setPaymentMethod(paymentMethod);
    }

    public String getInvoiceId() { return invoiceId; }

    public void setInvoiceId(String invoiceId) {
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống.");
        }
        this.invoiceId = invoiceId.trim();
    }

    public PurchaseOrder getPurchaseOrder() { return purchaseOrder; }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        if (purchaseOrder == null) {
            throw new IllegalArgumentException("Phiếu thu mua của hóa đơn không được null.");
        }
        this.purchaseOrder = purchaseOrder;
    }

    public Payment getPaymentMethod() { return paymentMethod; }

    public void setPaymentMethod(Payment paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Phương thức thanh toán không được null.");
        }
        this.paymentMethod = paymentMethod;
    }

    /**
     * Chi trả một lần. Phiếu rỗng, đã hủy hoặc đã hoàn tất sẽ bị từ chối để hạn chế thanh toán lặp.
     * Vì Payment hiện tại ghi kết quả qua console, đây là luồng mô phỏng chứ chưa kết nối cổng thanh toán thật.
     */
    public void processPayment() {
        OrderStatus status = purchaseOrder.getStatus();
        if (status == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Phiếu này đã được thanh toán.");
        }
        if (status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Không thể thanh toán phiếu đã hủy.");
        }
        if (purchaseOrder.getItems().isEmpty()) {
            throw new IllegalStateException("Không thể thanh toán phiếu không có mặt hàng.");
        }

        paymentMethod.pay(purchaseOrder.getPayableTotal());
        purchaseOrder.complete();
    }

    public void printInvoice() {
        DecimalFormat df = new DecimalFormat("#,##0.##");

        System.out.println("=============================");
        System.out.println("         PHIẾU THU MUA PHẾ LIỆU");
        System.out.println("=============================");
        System.out.println("Mã hóa đơn: " + invoiceId);
        System.out.println("Mã phiếu: " + purchaseOrder.getOrderId());
        System.out.println("Nhà cung cấp: " + purchaseOrder.getCustomerName());
        if (purchaseOrder.getCustomerEntity() != null) {
            System.out.println("Loại nhà cung cấp: "
                    + purchaseOrder.getCustomerEntity().getCustomerType());
            System.out.println("Mã nhà cung cấp: "
                    + purchaseOrder.getCustomerEntity().getCustomerId());
        } else {
            System.out.println("Loại nhà cung cấp: KHÁCH VÃNG LAI");
        }

        System.out.println("------------------ CHI TIẾT PHẾ LIỆU ------------------");
        for (PurchaseOrderItem line : purchaseOrder.getItems()) {
            System.out.printf("%-16s x %.2f kg | %.2f VNĐ/kg | Thành tiền: %s VNĐ%n",
                    line.getItem().getName(),
                    line.getWeightKg(),
                    line.getPricePerKgNow(),
                    df.format(line.calPrice()));
        }

        System.out.println("Tổng tiền phế liệu: " + df.format(purchaseOrder.calTotal()) + " VND");
        System.out.println("------------------ ƯU ĐÃI NHÀ CUNG CẤP ------------------");
        if (purchaseOrder.isSupplierBenefitApplied()) {
            System.out.println("Chính sách: " + purchaseOrder.getSupplierBenefitName());
            System.out.println("Nguồn áp dụng: " + purchaseOrder.getSupplierBenefitSource());
            System.out.println("Cách tính: " + purchaseOrder.getSupplierBenefitCalculation());
        } else {
            System.out.println("Chính sách: Chưa áp dụng chính sách ưu đãi.");
            System.out.println("Ghi chú: Cần gọi PurchaseOrder.applySupplierBenefit() trước khi thanh toán.");
        }
        System.out.println("Tiền ưu đãi/thưởng: +" + df.format(purchaseOrder.getSupplierBenefitAmount()) + " VND");
        System.out.println("TỔNG TIỀN PHẢI TRẢ: " + df.format(purchaseOrder.getPayableTotal()) + " VND");
        System.out.println("Trạng thái phiếu: " + purchaseOrder.getStatus());
        System.out.println("Hình thức: " + getPaymentMethodName());
        System.out.println("=============================");
    }

    private String getPaymentMethodName() {
        if (paymentMethod instanceof CashPayment) return "Tiền mặt";
        if (paymentMethod instanceof BankPayment) return "Chuyển khoản";
        if (paymentMethod instanceof EWalletPayment) return "Ví điện tử";
        return paymentMethod.getClass().getSimpleName();
    }
}
