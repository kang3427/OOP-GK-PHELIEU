package manager;
import event.*;
import model.ScrapItem;
import java.util.List;

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
    public void addStock(double weight, EventManager eventManager) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Khối lượng nhập kho phải lớn hơn 0");
        }

        double newTotal = this.khoiLuong + weight;

        // Kiểm tra quy tắc cảnh báo vượt sức chứa
        if (newTotal > this.sucChuaToiDa) {
            String alert = String.format("CẢNH BÁO: Mặt hàng [%s] vượt sức chứa bãi! (Hiện có + Mới: %.2f kg / Tối đa: %.2f kg)",
                    idItem, newTotal, sucChuaToiDa);
            if (eventManager != null) {
                eventManager.publish(new ShopEvent("OUT_OF_CAPACITY", alert));
            } else {
                System.out.println("[WARNING] " + alert);
            }
        }

        this.khoiLuong = newTotal;
    }

    public void addStock(double weight) {
        addStock(weight, null);
    }

    // Giảm tồn kho khi xuất bán cho nhà máy tái chế
    public void removeStock(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Khối lượng xuất kho phải lớn hơn 0");
        }

        // Quy tắc: Không cho xuất quá số lượng tồn
        if (weight > this.khoiLuong) {
            throw new IllegalArgumentException(String.format(
                    "Xuất kho thất bại: [%s] không đủ hàng trong kho! (Tồn: %.2f kg, Yêu cầu xuất: %.2f kg)",
                    idItem, khoiLuong, weight));
        }

        this.khoiLuong -= weight;
    }

    // Cập nhật lại khối lượng tồn kho
    public void updateStock(double newWeight) {
        if (newWeight < 0) {
            throw new IllegalArgumentException("Khối lượng tồn kho không được âm");
        }
        this.khoiLuong = newWeight;
    }

    // Kiểm tra số lượng tồn kho hiện tại
    public double checkStock() {
        return this.khoiLuong;
    }

    // Getters & Setters
    public String getIdItem() { return idItem; }
    public double getKhoiLuong() { return khoiLuong; }
    public double getSucChuaToiDa() { return sucChuaToiDa; }
}
