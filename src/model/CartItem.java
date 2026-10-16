package model;

/**
 * Lớp đại diện cho một dòng sản phẩm chi tiết nằm trong giỏ hàng.
 * Liên kết một đối tượng phế liệu (ScrapItem) với đơn giá của dòng hàng.
 */
public class CartItem {
    private String cartItemId;
    private ScrapItem item;
    private double pricePerKgNow;

    public CartItem(String cartItemId, ScrapItem item) {
        this(cartItemId, item, item != null ? item.getPricePerKg() : 1.0);
    }

    public CartItem(String cartItemId, ScrapItem item, double pricePerKgNow) {
        if (cartItemId == null || cartItemId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã chi tiết giỏ hàng không được để trống!");
        }
        if (item == null) {
            throw new IllegalArgumentException("Mặt hàng phế liệu (item) không được để trống!");
        }

        // 2. Kiểm tra đơn giá chốt tại thời điểm mua phải lớn hơn 0
        if (pricePerKgNow < 0) {
            throw new IllegalArgumentException("Đơn giá mới (pricePerKgNow) phải lớn hơn 0 VNĐ/kg!");
        }

        this.cartItemId = cartItemId.trim();
        this.item = item;
        this.pricePerKgNow = pricePerKgNow;
    }

    // Giữ constructor cũ để các đoạn code hiện có vẫn tương thích.
    public CartItem(ScrapItem item) {
        this("CI_" + (item != null ? item.getId() : "UNKNOWN"), item);
    }

    public CartItem(ScrapItem item, double pricePerKgNow) {
        this("CI_" + (item != null ? item.getId() : "UNKNOWN"), item, pricePerKgNow);
    }

    public String getCartItemId() { return cartItemId; }

    public double getPricePerKgNow() { return pricePerKgNow; }

    public ScrapItem getItem() {
        return item;
    }

    public double calPrice() {
        return pricePerKgNow * item.getWeight();
    }
}
