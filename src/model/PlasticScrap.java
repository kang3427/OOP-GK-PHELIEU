package model;
import enums.PlasticType;

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
