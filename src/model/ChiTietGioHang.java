package model;

/**
 * Lớp đại diện cho một dòng sản phẩm chi tiết nằm trong giỏ hàng.
 * Liên kết một đối tượng phế liệu (ScrapItem) với khối lượng thu mua cụ thể trong đợt cân đó.
 */

public class ChiTietGioHang {
    private PheLieu item;
    private double pricePerKgNow;

    public ChiTietGioHang(PheLieu item) {
        this.item = item;
    }

    public ChiTietGioHang(PheLieu item, double pricePerKgNow) {
        this.item = item;
        item.setPricePerKg(pricePerKgNow);
    }

    public PheLieu getItem() {
        return item;
    }

    public double calPrice() {
        return item.getDonGia() * item.getKhoiLuong();
    }
}
