package model;

/**
 * Lớp trừu tượng đại diện cho một loại/mặt hàng phế liệu trong danh mục.
 * pricePerKg là đơn giá hiện tại. weight được giữ để tương thích với các lời gọi cũ
 * dùng trực tiếp khối lượng trên ScrapItem; trong nghiệp vụ mới, số kg của từng giao dịch
 * phải truyền rõ qua Cart.addItem(item, weightKg) và được lưu riêng ở CartItem/PurchaseOrderItem.
 * Tồn kho cửa hàng hiện được theo dõi riêng bởi InventoryManager.
 */
public abstract class ScrapItem {
    private final String id;
    private final String name;
    private double pricePerKg;
    private double weight;

    public ScrapItem(String id, String name, double pricePerKg, double weight) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phế liệu không được để trống.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên phế liệu không được để trống.");
        }
        validatePrice(pricePerKg);
        validateWeight(weight);

        this.id = id.trim();
        this.name = name.trim();
        this.pricePerKg = pricePerKg;
        this.weight = weight;
    }

    private static void validatePrice(double pricePerKg) {
        if (!Double.isFinite(pricePerKg) || pricePerKg <= 0) {
            throw new IllegalArgumentException("Đơn giá phải là số hữu hạn lớn hơn 0.");
        }
    }

    private static void validateWeight(double weight) {
        if (!Double.isFinite(weight) || weight < 0) {
            throw new IllegalArgumentException("Khối lượng phải là số hữu hạn không âm.");
        }
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPricePerKg() { return pricePerKg; }
    public double getWeight() { return weight; }

    public void setPricePerKg(double pricePerKg) {
        validatePrice(pricePerKg);
        this.pricePerKg = pricePerKg;
    }

    public void setWeight(double weight) {
        validateWeight(weight);
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Phế liệu: " + name
                + " | Giá: " + pricePerKg
                + " VNĐ/kg | Khối lượng: " + weight + " kg";
    }

    public abstract void displayInfo();
}
