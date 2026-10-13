package model;

/**
 * Dòng chi tiết của phiếu thu mua.
 * Giá và khối lượng là dữ liệu lịch sử của giao dịch; không lấy lại từ ScrapItem
 * khi tính tiền vì giá danh mục hoặc khối lượng đối tượng có thể thay đổi về sau.
 */
public class PurchaseOrderItem {
    private final ScrapItem item;
    private final double pricePerKgNow;
    private final double weightKg;

    public PurchaseOrderItem(ScrapItem item) {
        this(requireItem(item), item.getPricePerKg(), item.getWeight());
    }

    public PurchaseOrderItem(ScrapItem item, double pricePerKgNow) {
        this(requireItem(item), pricePerKgNow, item.getWeight());
    }

    public PurchaseOrderItem(ScrapItem item, double pricePerKgNow, double weightKg) {
        if (item == null) {
            throw new IllegalArgumentException("Mặt hàng trong chi tiết phiếu không được null.");
        }
        if (!Double.isFinite(pricePerKgNow) || pricePerKgNow <= 0) {
            throw new IllegalArgumentException("Đơn giá tại thời điểm thu mua phải lớn hơn 0.");
        }
        if (!Double.isFinite(weightKg) || weightKg <= 0) {
            throw new IllegalArgumentException("Khối lượng thu mua phải lớn hơn 0.");
        }
        this.item = item;
        this.pricePerKgNow = pricePerKgNow;
        this.weightKg = weightKg;
    }

    private static ScrapItem requireItem(ScrapItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Mặt hàng trong chi tiết phiếu không được null.");
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
