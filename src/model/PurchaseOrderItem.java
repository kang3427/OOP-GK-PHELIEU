package model;

public class PurchaseOrderItem {
    private ScrapItem item;
    private double pricePerKgNow;
    public PurchaseOrderItem(ScrapItem item, double pricePerKgNow){
        this.item=item;
        this.pricePerKgNow=pricePerKgNow;
    }

    public PurchaseOrderItem(ScrapItem item){
        this.item=item;
    }

    public ScrapItem getItem(){ return item; }
    public double getPricePerKgNow() { return pricePerKgNow; }

    public double calPrice() {
        return item.getPricePerKg() * item.getWeight();
    }

}
