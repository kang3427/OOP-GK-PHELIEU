package payment;

/**
 * Interface định nghĩa hợp đồng chung cho các phương thức thanh toán (Strategy Pattern).
 * Giúp hệ thống xử lý giao dịch linh hoạt bằng nhiều hình thức khác nhau
 * mà không bị phụ thuộc chặt chẽ vào một cách thanh toán cụ thể.
 */

public interface ThanhToan {
    //in ra so tien tra cho khach hang
    void Thanhtoan(double So_Tien);
    //lay phuong thuc thanh toan
    String layTenPhuongThuc();
}
