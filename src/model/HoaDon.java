package model;
import payment.*;
import java.text.DecimalFormat;
/**
 * Lớp đại diện cho Hóa đơn / Biên nhận thanh toán hoàn xuất cho khách hàng.
 * Khởi tạo từ PurchaseOrder để ghi nhận thông tin thanh toán, hình thức trả tiền và xác nhận hoàn tất thu mua.
 */

public class HoaDon {
    // Thể hiện tính chất Encapsulation thông qua các thuộc tính private
    private String maHoaDon;
    private DonThuMua donThuMua;
    private ThanhToan phuongThucThanhToan;

    public HoaDon(String maHoaDon, DonThuMua donThuMua, ThanhToan phuongThucThanhToan) {
        this.maHoaDon = maHoaDon;
        this.donThuMua = donThuMua;
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    // Getters & Setters
    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public DonThuMua getDonThuMua() {
        return donThuMua;
    }

    public void setDonThuMua(DonThuMua donThuMua) {
        this.donThuMua = donThuMua;
    }

    public ThanhToan getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(ThanhToan phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }


    // Phương thức in phiếu chi trả ra màn hình theo định dạng
    public void InHoaDon() {
        DecimalFormat df = new DecimalFormat("#,###");

        System.out.println("=============================");
        System.out.println("         PHIẾU THU MUA PHẾ LIỆU");
        System.out.println("=============================");
        System.out.println("Mã phiếu: " + maHoaDon);

        // Lấy thông tin khách hàng từ PurchaseOrder
        if (donThuMua != null && donThuMua.getCustomer() != null) {
            System.out.println("Khách: " + donThuMua.getCustomer());
        } else {
            System.out.println("Khách: Khách vãng lai");
        }

        // Liệt kê chi tiết các mặt hàng phế liệu thu mua
        if (donThuMua != null && donThuMua.layDanhSachMatHang() != null) {
            for (ChiTietDonThuMua matHang : donThuMua.layDanhSachMatHang()) {
                // Định dạng hiển thị tên phế liệu và khối lượng (ví dụ: Sắt vụn x 25 kg)
                System.out.printf("%-12s x %.0f kg\n", matHang.getPheLieu().getTen(), matHang.getKhoiLuong());
            }
            // Hiển thị tổng chi trả
            System.out.println("Tổng chi trả: " + df.format(donThuMua.tinhTongTien()) + " VND");
        }

        // Hiển thị hình thức thanh toán
        System.out.println("Hình thức: " + tenHinhThuc());
        System.out.println("=============================");
    }

    /**
     * Phương thức phụ trợ để chuyển đổi đối tượng Payment thành chuỗi tên hình thức thanh toán
     */
    private String tenHinhThuc() {
        if (phuongThucThanhToan == null) return "Chưa xác định";

        String className = phuongThucThanhToan.getClass().getSimpleName();
        switch (className) {
            case "TienMat":
                return "Tiền mặt";
            case "ChuyenKhoan":
                return "Chuyển khoản";
            case "ViDienTu":
                return "Ví điện tử";
            default:
                return "Khác";
        }
    }
}
