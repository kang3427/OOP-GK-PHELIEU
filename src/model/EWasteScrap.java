package model;
import enums.EWasteType;

/**
 * Lớp đại diện cho phế liệu điện tử (bo mạch, điện thoại, linh kiện máy tính...), kế thừa từ ScrapItem.
 * Quản lý thông tin kiểm định nguồn gốc (EWasteType) để xác định tính hợp lệ pháp lý và điều chỉnh giá thực thu mua.
 */

public class EWasteScrap extends ScrapItem {
    private EWasteType type;
    protected EWasteScrap(String id, String name, double pricePerKg, double weight, EWasteType type) {
        super(id, name, pricePerKg, weight);
        this.type = type;
    }

    public double calPrice() {
        return getPricePerKg() * getWeight();
    }

    @Override
    public void displayInfo() {
        System.out.println("Ten " + getName() + " Gia " + getPricePerKg() +
                " Khoi luong " + getWeight() + " Loai " + type);
    }
}
