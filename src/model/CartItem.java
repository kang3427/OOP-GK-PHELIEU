package model;

/**
 * Một dòng trong giỏ hàng.
 * Đơn giá và khối lượng được chụp lại tại thời điểm thêm vào giỏ, tránh việc đổi dữ liệu
 * của ScrapItem làm thay đổi ngược số tiền của giao dịch đang lập.
 */
public class CartItem {
    private final ScrapItem item;
    private final double pricePerKgNow;
    private final double weightKg;

    public CartItem(ScrapItem item) {
        this(requireItem(item), item.getPricePerKg(), item.getWeight());
    }

    public CartItem(ScrapItem item, double pricePerKgNow) {
        this(requireItem(item), pricePerKgNow, item.getWeight());
    }

    public CartItem(ScrapItem item, double pricePerKgNow, double weightKg) {
        if (item == null) {
            throw new IllegalArgumentException("Mặt hàng trong giỏ không được null.");
        }
        if (!Double.isFinite(pricePerKgNow) || pricePerKgNow <= 0) {
            throw new IllegalArgumentException("Đơn giá tại thời điểm thu mua phải lớn hơn 0.");
        }
        if (!Double.isFinite(weightKg) || weightKg <= 0) {
            throw new IllegalArgumentException("Khối lượng của dòng giỏ hàng phải lớn hơn 0.");
        }
        this.item = item;
        this.pricePerKgNow = pricePerKgNow;
        this.weightKg = weightKg;
    }

    private static ScrapItem requireItem(ScrapItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Mặt hàng trong giỏ không được null.");
        }
        return item;
    }

    public ScrapItem getItem() { return item; }
    public double getPricePerKgNow() { return pricePerKgNow; }
    public double getWeightKg() { return weightKg; }

    public double calPrice() {
        return pricePerKgNow * weightKg;
    }
}
