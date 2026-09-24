package GiuaKi;

public class CartItem {
    private ScrapItem item;

    public CartItem(ScrapItem item) {
        this.item = item;
    }

    public ScrapItem getItem() {
        return item;
    }

    public double calPrice() {
        return item.getPricePerKg() * item.getWeight();
    }
}
