package model;
import enums.*;

/**
 * Lớp đại diện cho phế liệu nhựa (PET, HDPE, PVC, PP...), kế thừa từ ScrapItem.
 * Quản lý loại nhựa (PlasticType) theo mã tái chế quốc tế để xác định giá trị tái chế và đơn giá thu mua.
 */

public class PheLieuNhua extends PheLieu {
    private Nhua loaiNhua;

    public PheLieuNhua(String ma, String ten, double donGia, double khoiLuong, Nhua loaiNhua) {
        super(ma, ten, donGia, khoiLuong);
        this.loaiNhua =loaiNhua;
    }

    public double TinhTien() {
        return getDonGia() * getKhoiLuong();
    }

    @Override
    public void HienThiThongTin() {
        System.out.println("Ten " + getTen() + " Gia " + getDonGia() +
                " Khoi luong " + getKhoiLuong() + " Loai " + loaiNhua);
    }
    public Nhua getLoaiNhua() {
        return loaiNhua;
    }
}
