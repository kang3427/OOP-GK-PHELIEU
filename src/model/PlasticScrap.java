package model;
import enums.PlasticType;

/**
 * Lớp đại diện cho phế liệu nhựa (PET, HDPE, PVC, PP...), kế thừa từ ScrapItem.
 * Quản lý loại nhựa (PlasticType) theo mã tái chế quốc tế để xác định giá trị tái chế và đơn giá thu mua.
 */

public class PlasticScrap extends ScrapItem{
    private PlasticType type;

    protected PlasticScrap(String id, String name, double pricePerKg, double weight, PlasticType type) {
        super(id, name, pricePerKg, weight);
        this.type=type;
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
