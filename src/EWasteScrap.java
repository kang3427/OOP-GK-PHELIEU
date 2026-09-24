package GiuaKi;

public abstract class EWasteScrap extends ScrapItem {
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
