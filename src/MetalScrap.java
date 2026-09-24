package GiuaKi;

public abstract class MetalScrap extends ScrapItem {
    private MetalType type;

    protected MetalScrap(String id, String name, double pricePerKg, double weight, MetalType type) {
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