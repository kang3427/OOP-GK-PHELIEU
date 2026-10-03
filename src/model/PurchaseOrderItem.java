package model;

/**
 * Lớp đại diện cho từng dòng chi tiết phế liệu trong Phiếu thu mua chính thức (PurchaseOrder).
 * Chốt cố định giá trị phế liệu, khối lượng thực cân và thành tiền tại thời điểm hoàn tất giao dịch.
 */

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
    public double getWeightKg() {
        return item.getWeight();
    }

}
