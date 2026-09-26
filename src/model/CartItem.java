package model;

public class CartItem {
    private ScrapItem item;
    private double pricePerKgNow;

    public CartItem(ScrapItem item) {
        this.item = item;
    }

    public CartItem(ScrapItem item, double pricePerKgNow) {
        this.item = item;
        item.setPricePerKg(pricePerKgNow);
    }

    public ScrapItem getItem() {
        return item;
    }

    public double calPrice() {
        return item.getPricePerKg() * item.getWeight();
    }
}
