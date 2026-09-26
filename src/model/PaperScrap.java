package model;
import enums.*;

/**
 * Lớp đại diện cho phế liệu giấy (carton, giấy báo, giấy văn phòng...), kế thừa từ ScrapItem.
 * Quản lý loại giấy (PaperType) nhằm áp dụng bảng giá thu gom và quy trình đóng kiện riêng.
 */

public class PaperScrap extends ScrapItem{
    private PaperType type;

    public PaperScrap(String id, String name, double pricePerKg, double weight, PaperType type) {
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
