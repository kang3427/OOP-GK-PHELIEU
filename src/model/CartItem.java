package model;

/**
 * Lớp đại diện cho một dòng sản phẩm chi tiết nằm trong giỏ hàng.
 * Liên kết một đối tượng phế liệu (ScrapItem) với khối lượng thu mua cụ thể trong đợt cân đó.
 */

public class CartItem {
    private ScrapItem item;
    private double pricePerKgNow;

    public CartItem(ScrapItem item) {
        this(item, item != null ? item.getPricePerKg() : 1.0);
    }

    public CartItem(ScrapItem item, double pricePerKgNow) {
        // 1. Kiểm tra mặt hàng phế liệu không được null
        if (item == null) {
            throw new IllegalArgumentException("Mặt hàng phế liệu (item) không được để trống!");
        }

        // 2. Kiểm tra đơn giá chốt tại thời điểm mua phải lớn hơn 0
        if (pricePerKgNow <= 0) {
            throw new IllegalArgumentException("Đơn giá mới (pricePerKgNow) phải lớn hơn 0 VNĐ/kg!");
        }

        this.item = item;
        this.pricePerKgNow = pricePerKgNow;
    }

    public double  getPricePerKgNow() {return pricePerKgNow;}
    public ScrapItem getItem() {
        return item;
    }

    public double calPrice() {
        return pricePerKgNow * item.getWeight();
    }
}
