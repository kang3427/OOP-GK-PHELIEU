package model;

/**
 * Lớp đại diện cho từng dòng chi tiết phế liệu trong Phiếu thu mua chính thức (PurchaseOrder).
 * Chốt cố định giá trị phế liệu, khối lượng thực cân và thành tiền tại thời điểm hoàn tất giao dịch.
 */

public class PurchaseOrderItem {
    private ScrapItem item;
    private double pricePerKgNow;
    public PurchaseOrderItem(ScrapItem item, double pricePerKgNow) {
        // 1. Kiểm tra đối tượng ScrapItem không được null
        if (item == null) {
            throw new IllegalArgumentException("Mặt hàng phế liệu (item) không được để trống!");
        }

        // 2. Kiểm tra đơn giá chốt tại thời điểm giao dịch phải lớn hơn 0
        if (pricePerKgNow <= 0) {
            throw new IllegalArgumentException("Đơn giá chốt (pricePerKgNow) phải lớn hơn 0 VNĐ/kg!");
        }

        this.item = item;
        this.pricePerKgNow = pricePerKgNow;
    }

    // Bổ sung ràng buộc cho Constructor 1 tham số (nếu có sử dụng trong class)
    public PurchaseOrderItem(ScrapItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Mặt hàng phế liệu (item) không được để trống!");
        }
        this.item = item;
        this.pricePerKgNow = item.getPricePerKg();
    }

    public ScrapItem getItem(){ return item; }
    public double getPricePerKgNow() { return pricePerKgNow; }
    public double calPrice() {
        return item.getPricePerKg() * item.getWeight();
    }
    public double getWeightKg() {
        return item.getWeight();
    }

}
