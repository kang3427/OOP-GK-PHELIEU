package model;

/**
 * Lớp đại diện cho từng dòng chi tiết phế liệu trong Phiếu thu mua chính thức (PurchaseOrder).
 * Chốt cố định giá trị phế liệu, khối lượng thực cân và thành tiền tại thời điểm hoàn tất giao dịch.
 */
public class PurchaseOrderItem {
    private String purchaseOrderItemId;
    private ScrapItem item;
    private double pricePerKgNow;
    private double weightKgNow;

    public PurchaseOrderItem(String purchaseOrderItemId, ScrapItem item, double pricePerKgNow, double weightKgNow) {
        if (purchaseOrderItemId == null || purchaseOrderItemId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã chi tiết đơn hàng không được để trống!");
        }
        if (item == null) {
            throw new IllegalArgumentException("Mặt hàng phế liệu (item) không được để trống!");
        }
        if (pricePerKgNow < 0) {
            throw new IllegalArgumentException("Đơn giá chốt (pricePerKgNow) phải lớn hơn 0 VNĐ/kg!");
        }
        if (weightKgNow <= 0) {
            throw new IllegalArgumentException("Khối lượng chốt (weightKgNow) phải lớn hơn 0 kg!");
        }

        this.purchaseOrderItemId = purchaseOrderItemId.trim();
        this.item = item;
        this.pricePerKgNow = pricePerKgNow;
        this.weightKgNow = weightKgNow;
    }

        // 1. Kiểm tra đối tượng ScrapItem không được null
    public PurchaseOrderItem(String purchaseOrderItemId, ScrapItem item, double pricePerKgNow) {
        if (purchaseOrderItemId == null || purchaseOrderItemId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã chi tiết đơn hàng không được để trống!");
        }
        if (item == null) {
            throw new IllegalArgumentException("Mặt hàng phế liệu (item) không được để trống!");
        }

        // 2. Kiểm tra đơn giá chốt tại thời điểm giao dịch phải lớn hơn 0
        if (pricePerKgNow < 0) {
            throw new IllegalArgumentException("Đơn giá chốt (pricePerKgNow) phải lớn hơn 0 VNĐ/kg!");
        }

        this.purchaseOrderItemId = purchaseOrderItemId.trim();
        this.item = item;
        this.pricePerKgNow = pricePerKgNow;
        this.weightKgNow = item.getWeight();
    }

    // Bổ sung ràng buộc cho Constructor 1 tham số (nếu có sử dụng trong class)
    public PurchaseOrderItem(ScrapItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Mặt hàng phế liệu (item) không được để trống!");
        }
        this.purchaseOrderItemId = "POI_" + item.getId();
        this.item = item;
        this.pricePerKgNow = item.getPricePerKg();
        this.weightKgNow = item.getWeight();
    }

    public String getPurchaseOrderItemId() { return purchaseOrderItemId; }

    public ScrapItem getItem() { return item; }
    public double getPricePerKgNow() { return pricePerKgNow; }
    public double calPrice() { return pricePerKgNow * weightKgNow; }
    public double getWeightKg() { return weightKgNow; }
}
