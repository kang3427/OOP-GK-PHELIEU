package model;
import enums.*;

/**
 * Lớp đại diện cho phế liệu điện tử (bo mạch, điện thoại, linh kiện máy tính...), kế thừa từ ScrapItem.
 * Quản lý thông tin kiểm định nguồn gốc (EWasteType) để xác định tính hợp lệ pháp lý và điều chỉnh giá thực thu mua.
 */

public class PheLieuDienTu extends PheLieu {
    private DienTU loaiDienTu;
    public PheLieuDienTu(String ma, String ten, double donGia, double khoiLuong, DienTU loaiDienTu) {
        super(ma, ten, donGia, khoiLuong);
        this.loaiDienTu = loaiDienTu;
    }
    public double TinhTien() {
        return getDonGia() * getKhoiLuong();
    }

    @Override
    public void HienThiThongTin() {
        System.out.println("Ten " + getTen() + " Gia " + getDonGia() +
                " Khoi luong " + getKhoiLuong() + " Loai " + loaiDienTu);
    }
    public DienTU getLoaiDienTu() {
        return loaiDienTu;
    }
}
