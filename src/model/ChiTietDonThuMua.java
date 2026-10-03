package model;

/**
 * Lớp đại diện cho từng dòng chi tiết phế liệu trong Phiếu thu mua chính thức (PurchaseOrder).
 * Chốt cố định giá trị phế liệu, khối lượng thực cân và thành tiền tại thời điểm hoàn tất giao dịch.
 */

public class ChiTietDonThuMua {
    private PheLieu item;
    private double pricePerKgNow;
    public ChiTietDonThuMua(PheLieu item, double pricePerKgNow){
        this.item=item;
        this.pricePerKgNow=pricePerKgNow;
    }

    public ChiTietDonThuMua(PheLieu item){
        this.item=item;
    }

    public PheLieu getPheLieu(){ return item; }
    public double getPricePerKgNow() { return pricePerKgNow; }
    public double calPrice() {
        return item.getDonGia() * item.getKhoiLuong();
    }
    public double getKhoiLuong() {
        return item.getKhoiLuong();
    }

}
