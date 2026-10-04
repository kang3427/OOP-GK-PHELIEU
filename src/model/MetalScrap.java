package model;
import enums.*;

/**
 * Lớp đại diện cho phế liệu kim loại (sắt, thép, đồng, nhôm...), kế thừa từ ScrapItem.
 * Quản lý thêm chủng loại kim loại (MetalType - Kim loại đen / Kim loại màu)
 * để tính toán đơn giá chuẩn và phân loại lưu kho.
 */

public class MetalScrap extends ScrapItem {
    private MetalType type;

    public MetalScrap(String id, String name, double pricePerKg, double weight, MetalType type) {
        super(id, name, pricePerKg, weight);
        this.type = type;
    }

    public double calPrice() {
        return getPricePerKg() * getWeight();
    }

    @Override
    public void displayInfo() {
        System.out.println("Đây là phế liệu Kim loại: " + getName());
        System.out.println("Ten " + getName() + " Gia " + getPricePerKg() +
                " Khoi luong " + getWeight() + " Loai " + type);
    }
}