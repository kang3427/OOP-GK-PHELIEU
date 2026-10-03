package model;

/**
 * Lớp đại diện cho một dòng sản phẩm chi tiết nằm trong giỏ hàng.
 * Liên kết một đối tượng phế liệu (ScrapItem) với khối lượng thu mua cụ thể trong đợt cân đó.
 */

public class ChiTietGioHang {
    private PheLieu pheLieu;
    private double pricePerKgNow;

    public ChiTietGioHang(PheLieu pheLieu) {
        this.pheLieu = pheLieu;
    }

    public ChiTietGioHang(PheLieu pheLieu, double donGiaHienTai) {
        this.pheLieu = pheLieu;
        pheLieu.setPricePerKg(donGiaHienTai);
    }

    public PheLieu getPheLieu() {
        return pheLieu;
    }

    public double tinhTien() {
        return pheLieu.getDonGia() * pheLieu.getKhoiLuong();
    }
}
