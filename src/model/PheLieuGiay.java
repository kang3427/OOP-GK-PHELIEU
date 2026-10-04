package model;
import enums.*;

/**
 * Lớp đại diện cho phế liệu giấy (carton, giấy báo, giấy văn phòng...), kế thừa từ ScrapItem.
 * Quản lý loại giấy (PaperType) nhằm áp dụng bảng giá thu gom và quy trình đóng kiện riêng.
 */

public class PheLieuGiay extends PheLieu {
    private Giay loaiGiay;

    public PheLieuGiay(String ma, String ten, double donGia, double khoiLuong, Giay loaiGiay) {
        super(ma, ten, donGia, khoiLuong);
        this.loaiGiay = loaiGiay;
    }

    public double TinhTien() {
        return getDonGia() * getKhoiLuong();
    }

    @Override
    public void HienThiThongTin() {
        System.out.println("Ten " + getTen() + " Gia " + getDonGia() +
                " Khoi luong " + getKhoiLuong() + " Loai " + loaiGiay);
    }
    public Giay getLoaiGiay() {
        return loaiGiay;
    }
}
