package model;

/**
 * Lớp đại diện cho từng dòng chi tiết phế liệu trong Phiếu thu mua chính thức (PurchaseOrder).
 * Chốt cố định giá trị phế liệu, khối lượng thực cân và thành tiền tại thời điểm hoàn tất giao dịch.
 */

public class ChiTietDonThuMua {
    private PheLieu pheLieu;
    private double donGiaHienTai;
    public ChiTietDonThuMua(PheLieu pheLieu, double donGiaHienTai){
        this.pheLieu =pheLieu;
        this.donGiaHienTai =donGiaHienTai;
    }

    public ChiTietDonThuMua(PheLieu pheLieu){
        this.pheLieu =pheLieu;
    }

    public PheLieu getPheLieu(){ return pheLieu; }
    public double getDonGiaHienTai() { return donGiaHienTai; }
    public double tinhTien() {
        return pheLieu.getDonGia() * pheLieu.getKhoiLuong();
    }
    public double getKhoiLuong() {
        return pheLieu.getKhoiLuong();
    }

}
