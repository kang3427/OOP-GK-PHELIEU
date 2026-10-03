package model;

/**
 * Lớp đại diện cho thông tin Khách hàng / Đối tác giao dịch với vựa phế liệu.
 * Quản lý thông tin cá nhân, nhóm khách hàng (CustomerType) và số điểm thưởng tích lũy (rewardPoints).
 */

public abstract class KhachHang {
    private final String maKhachHang;
    private final String ten;
    private final String soDienThoai;
    private final String diaChi ;

    public KhachHang(String maKhachHang, String ten, String soDienThoai, String diaChi) {
        this.maKhachHang = maKhachHang;
        this.ten = ten;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
    }
    public String getMaKhachHang() {
        return maKhachHang;
    }
    public String getTen() {
        return ten;
    }
    public String getSoDienThoai() {
        return soDienThoai;
    }
    public String getDiaChi() {
        return diaChi;
    }

    public abstract void HienThiThongTin();
}
