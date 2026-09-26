package GiuaKi;

public class PaperScrap extends ScrapItem{
    private PaperType type;

    protected PaperScrap(String id, String name, double pricePerKg, double weight, PaperType type) {
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
