package model;

/**
 * Lớp đại diện cho một dòng sản phẩm chi tiết nằm trong giỏ hàng.
 * Liên kết một đối tượng phế liệu (ScrapItem) với khối lượng thu mua cụ thể trong đợt cân đó.
 */

public class CartItem {
    private ScrapItem item;
    private double pricePerKgNow;

    public CartItem(ScrapItem item) {
        this.item = item;
    }

    public CartItem(ScrapItem item, double pricePerKgNow) {
        this.item = item;
        item.setPricePerKg(pricePerKgNow);
    }

    public ScrapItem getItem() {
        return item;
    }

    public double calPrice() {
        return item.getPricePerKg() * item.getWeight();
    }
}
