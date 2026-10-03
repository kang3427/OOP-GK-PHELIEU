package reward;

/**
 * Lớp triển khai chính sách tính thưởng / chiết khấu thực tế cho khách hàng (Customer Reward Module).
 * Triển khai interface Rewardable để tính toán tiền thưởng dựa trên tỷ lệ phần trăm (rewardRate)
 * hoặc các ưu đãi dành cho khách hàng bán phế liệu.
 */

public class ThuongKhachHang implements TinhThuong {
    private double tyLeThuong; // Tỷ lệ thưởng (ví dụ: 0.02 = 2%)

    public ThuongKhachHang(double tyLeThuong) {
        this.tyLeThuong = tyLeThuong;
    }

    @Override
    public double tinhTienThuong(double khoiLuong, double giaGoc) {
        if (giaGoc <= 0) return 0;
        return giaGoc * tyLeThuong;
    }
}
