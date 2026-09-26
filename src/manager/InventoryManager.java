package manager;

/**
 * Lớp quản lý kho phế liệu (Inventory Management Unit).
 * Chịu trách nhiệm theo dõi và điều hành toàn bộ lượng hàng phế liệu trong kho vựa:
 * - Quản lý danh sách tồn kho các mặt hàng phế liệu (ScrapItem).
 * - Cập nhật tăng/giảm khối lượng khả dụng (weight) khi nhập hoặc bán phế liệu.
 * - Kiểm tra ngưỡng tồn kho, tổng giá trị kho hàng và tích hợp phát sự kiện thông báo.
 */

public class InventoryManager {
    private String idItem;
    private double khoiLuong;
    private double sucChuaToiDa;
    public InventoryManager(String idItem, double khoiLuong, double sucChuaToiDa) {
        this.idItem = idItem;
        this.khoiLuong = khoiLuong;
        this.sucChuaToiDa = sucChuaToiDa;
    }

    //nhap them phe lieu khi thu mua tu khach hang(tang ton kho)
    public void addStock(String idItem, double khoiLuong){
        if (khoiLuong<0){
            throw new IllegalArgumentException("Phe lieu nhap vao co khoi luong khong duoc am");
        }

    }
}
