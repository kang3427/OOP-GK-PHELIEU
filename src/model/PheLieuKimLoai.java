package model;
import enums.*;

/**
 * Lớp đại diện cho phế liệu kim loại (sắt, thép, đồng, nhôm...), kế thừa từ ScrapItem.
 * Quản lý thêm chủng loại kim loại (MetalType - Kim loại đen / Kim loại màu)
 * để tính toán đơn giá chuẩn và phân loại lưu kho.
 */

public class PheLieuKimLoai extends PheLieu {
    private KimLoai loaiKimLoai;

    public PheLieuKimLoai(String ma, String ten, double donGia, double khoiLuong, KimLoai loaiKimLoai) {
        super(ma, ten, donGia, khoiLuong);
        this.loaiKimLoai = loaiKimLoai;
    }

    public double TinhTien () {
        return getDonGia() * getKhoiLuong();
    }

    @Override
    public void HienThiThongTin() {
        System.out.println("Đây là phế liệu Kim loại: " + getTen());
        System.out.println("Ten " + getTen() + " Gia " + getDonGia() +
                " Khoi luong " + getKhoiLuong() + " Loai " + loaiKimLoai);
    }
    public KimLoai getLoaiKimLoai() {
        return loaiKimLoai;
    }
}