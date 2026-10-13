package model;

import enums.OrderStatus;
import reward.SupplierBenefitPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Phiếu thu mua chính thức.
 * Giữ constructor cũ nhận tên khách để tương thích; dùng forCustomer() khi có Customer
 * để tránh mơ hồ với constructor nhận String khi truyền null.
 */
public class PurchaseOrder {
    private final String orderId;
    private final Customer customer;
    private final String guestCustomerName;
    private final List<PurchaseOrderItem> order = new ArrayList<>();
    private OrderStatus status = OrderStatus.PROCESSING;

    // Kết quả ưu đãi được chụp lại theo phiếu, không tính lại khi in hóa đơn.
    private String supplierBenefitName = "Chưa áp dụng";
    private String supplierBenefitSource = "Chưa áp dụng";
    private String supplierBenefitCalculation = "Chưa áp dụng";
    private double supplierBenefitAmount;
    private boolean supplierBenefitApplied;

    /** Constructor tương thích với code cũ: tên khách được ghi nhận dạng khách vãng lai. */
    public PurchaseOrder(String orderId, String customerName, Cart cart) {
        this(orderId, null, normalizeGuestName(customerName), cart);
    }

    /** Tạo phiếu cho khách có hồ sơ mà không làm mơ hồ constructor cũ khi customerName là null. */
    public static PurchaseOrder forCustomer(String orderId, Customer customer, Cart cart) {
        return new PurchaseOrder(orderId, requireCustomer(customer), null, cart);
    }

    private PurchaseOrder(String orderId, Customer customer, String guestCustomerName, Cart cart) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu thu mua không được để trống.");
        }
        if (cart == null) {
            throw new IllegalArgumentException("Giỏ hàng không được null.");
        }
        this.orderId = orderId.trim();
        this.customer = customer;
        this.guestCustomerName = guestCustomerName;
        for (CartItem line : cart.getCart()) {
            addItem(line);
        }
    }

    private static Customer requireCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer không được null.");
        }
        return customer;
    }

    private static String normalizeGuestName(String customerName) {
        return customerName == null || customerName.trim().isEmpty()
                ? "Khách vãng lai" : customerName.trim();
    }

    private void ensureEditable() {
        if (status == OrderStatus.COMPLETED || status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Không thể sửa phiếu đã hoàn tất hoặc đã hủy.");
        }
    }

    /** Thêm theo giá và khối lượng hiện tại của ScrapItem (tương thích với code cũ). */
    public void addItem(ScrapItem item) {
        ensureEditable();
        order.add(new PurchaseOrderItem(item));
        clearSupplierBenefit();
    }

    /** Sao chép đúng giá và khối lượng đã được chốt trong CartItem. */
    public void addItem(CartItem item) {
        ensureEditable();
        if (item == null) {
            throw new IllegalArgumentException("Dòng giỏ hàng không được null.");
        }
        order.add(new PurchaseOrderItem(
                item.getItem(), item.getPricePerKgNow(), item.getWeightKg()));
        clearSupplierBenefit();
    }

    /** Thêm trực tiếp một dòng phiếu với đơn giá và khối lượng đã cân tường minh. */
    public void addItem(ScrapItem item, double pricePerKg, double weightKg) {
        ensureEditable();
        order.add(new PurchaseOrderItem(item, pricePerKg, weightKg));
        clearSupplierBenefit();
    }

    public void removeItem(String id) {
        ensureEditable();
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã mặt hàng cần xóa không được để trống.");
        }
        order.removeIf(line -> line.getItem().getId().equals(id));
        clearSupplierBenefit();
    }

    /**
     * Tính và chốt ưu đãi cho nhà cung cấp. Ưu đãi riêng theo customerId được
     * ưu tiên hơn chính sách theo CustomerType. Cần gọi sau khi đã sửa xong các dòng phiếu.
     */
    public void applySupplierBenefit(SupplierBenefitPolicy policy) {
        ensureEditable();
        if (policy == null) {
            throw new IllegalArgumentException("Chính sách ưu đãi không được null.");
        }
        SupplierBenefitPolicy.BenefitResult result =
                policy.evaluate(customer, getTotalWeightKg(), calTotal());

        this.supplierBenefitName = result.getPolicyName();
        this.supplierBenefitSource = result.getPolicySource();
        this.supplierBenefitCalculation = result.getCalculationDescription();
        this.supplierBenefitAmount = result.getBonusAmount();
        this.supplierBenefitApplied = true;
    }

    private void clearSupplierBenefit() {
        supplierBenefitName = "Chưa áp dụng";
        supplierBenefitSource = "Chưa áp dụng";
        supplierBenefitCalculation = "Chưa áp dụng";
        supplierBenefitAmount = 0;
        supplierBenefitApplied = false;
    }

    /** Tổng khối lượng các dòng trên phiếu, tính từ số cân đã chốt. */
    public double getTotalWeightKg() {
        double totalWeight = 0;
        for (PurchaseOrderItem line : order) {
            totalWeight += line.getWeightKg();
        }
        return totalWeight;
    }

    public double getSupplierBenefitAmount() { return supplierBenefitAmount; }
    public String getSupplierBenefitName() { return supplierBenefitName; }
    public String getSupplierBenefitSource() { return supplierBenefitSource; }
    public String getSupplierBenefitCalculation() { return supplierBenefitCalculation; }
    public boolean isSupplierBenefitApplied() { return supplierBenefitApplied; }

    /** Tổng khoản cửa hàng trả nhà cung cấp = tiền phế liệu + thưởng/ưu đãi. */
    public double getPayableTotal() {
        return calTotal() + supplierBenefitAmount;
    }

    public double calTotal() {
        double total = 0;
        for (PurchaseOrderItem line : order) {
            total += line.calPrice();
        }
        return total;
    }

    public String getOrderId() { return orderId; }

    /** Trả về tên khách để giữ tương thích với Invoice và Main hiện tại. */
    public String getCustomer() { return getCustomerName(); }

    public String getCustomerName() {
        if (customer != null) {
            return customer.getName();
        }
        return guestCustomerName == null ? "Khách vãng lai" : guestCustomerName;
    }

    public Customer getCustomerEntity() { return customer; }
    public OrderStatus getStatus() { return status; }

    /** Danh sách trả về chỉ đọc, tránh sửa phiếu mà bỏ qua kiểm soát trạng thái. */
    public List<PurchaseOrderItem> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(order));
    }

    public void complete() {
        if (status == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Phiếu này đã được hoàn tất trước đó.");
        }
        if (status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Không thể hoàn tất phiếu đã hủy.");
        }
        if (order.isEmpty()) {
            throw new IllegalStateException("Không thể hoàn tất phiếu không có mặt hàng.");
        }
        status = OrderStatus.COMPLETED;
    }

    public void cancel() {
        if (status == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Không thể hủy phiếu đã hoàn tất thanh toán.");
        }
        status = OrderStatus.CANCELLED;
    }

    public void showOrder() {
        System.out.println("====== PHIẾU THU MUA ======");
        System.out.println("Mã phiếu: " + orderId);
        System.out.println("Tên khách hàng: " + getCustomerName());
        System.out.println("Trạng thái: " + status);
        if (order.isEmpty()) {
            System.out.println("Phiếu chưa có mặt hàng.");
            return;
        }
        for (PurchaseOrderItem line : order) {
            System.out.println(line.getItem().getName()
                    + " | " + line.getWeightKg() + " kg"
                    + " x " + line.getPricePerKgNow() + " VNĐ/kg"
                    + " = " + line.calPrice() + " VNĐ");
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("TỔNG TIỀN: " + calTotal() + " VNĐ");
    }
}
