package manager;
import Event.*;

/**
 * Lớp quản lý kho phế liệu (Inventory Management Unit).
 * Chịu trách nhiệm theo dõi và điều hành toàn bộ lượng hàng phế liệu trong kho vựa:
 * - Quản lý danh sách tồn kho các mặt hàng phế liệu (ScrapItem).
 * - Cập nhật tăng/giảm khối lượng khả dụng (weight) khi nhập hoặc bán phế liệu.
 * - Kiểm tra ngưỡng tồn kho, tổng giá trị kho hàng và tích hợp phát sự kiện thông báo.
 */

public class TonKhoPheLieu {
    private String maMatHang;
    private double khoiLuongHienTai;
    private double sucChuaToiDa;
    public TonKhoPheLieu(String maMatHang, double khoiLuong, double sucChuToiDa) {
        this.maMatHang = maMatHang;
        this.khoiLuongHienTai = khoiLuong;
        this.sucChuaToiDa = sucChuToiDa;
    }

    //nhap them phe lieu khi thu mua tu khach hang(tang ton kho)
    public void NhapKho(double khoiLuongNhap, QuanLiSuKien quanLiSuKien) {
        if (khoiLuongNhap <= 0) {
            throw new IllegalArgumentException("Khối lượng nhập kho phải lớn hơn 0");
        }

        double tongKhoiLuongMoi = this.khoiLuongHienTai + khoiLuongNhap;

        // Kiểm tra quy tắc cảnh báo vượt sức chứa
        if (tongKhoiLuongMoi > this.sucChuaToiDa) {
            String thongBao = String.format("CẢNH BÁO: Mặt hàng [%s] vượt sức chứa bãi! (Hiện có + Mới: %.2f kg / Tối đa: %.2f kg)",
                    maMatHang, tongKhoiLuongMoi, sucChuaToiDa);
            if (quanLiSuKien != null) {
                quanLiSuKien.publish(new Sukien("VƯỢT SỨC CHỨA", thongBao));
            } else {
                System.out.println("[WARNING] " + thongBao);
            }
        }

        this.khoiLuongHienTai = tongKhoiLuongMoi;
    }

    public void NhapKho(double khoiLuongNhap) {
        NhapKho(khoiLuongNhap, null);
    }

    // Giảm tồn kho khi xuất bán cho nhà máy tái chế
    public void XuatKho(double khoiLuongXuat) {
        if (khoiLuongXuat <= 0) {
            throw new IllegalArgumentException("Khối lượng xuất kho phải lớn hơn 0");
        }

        // Quy tắc: Không cho xuất quá số lượng tồn
        if (khoiLuongXuat > this.khoiLuongHienTai) {
            throw new IllegalArgumentException(String.format(
                    "Xuất kho thất bại: [%s] không đủ hàng trong kho! (Tồn: %.2f kg, Yêu cầu xuất: %.2f kg)",
                    maMatHang, khoiLuongHienTai, khoiLuongXuat));
        }

        this.khoiLuongHienTai -= khoiLuongXuat;
    }

    // Cập nhật lại khối lượng tồn kho
    public void CapNhatTonKho(double khoiLuongMoi) {
        if (khoiLuongMoi < 0) {
            throw new IllegalArgumentException("Khối lượng tồn kho không được âm");
        }
        this.khoiLuongHienTai = khoiLuongMoi;
    }

    // Kiểm tra số lượng tồn kho hiện tại
    public double kiemTraTonKho() {
        return this.khoiLuongHienTai;
    }

    // Getters & Setters
    public String getMaMatHang() { return maMatHang; }
    public double getKhoiLuongHienTai() { return khoiLuongHienTai; }
    public double getSucChuaToiDa() { return sucChuaToiDa; }
}
