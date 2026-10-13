package manager;

import event.*;

/**
 * Quản lý tồn kho của một mã mặt hàng.
 * Khi nhập vượt sức chứa, lớp phát cảnh báo nhưng vẫn ghi nhận khối lượng thực tế đã nhập.
 * Khi xuất kho, không cho phép xuất quá tồn hiện có.
 */
public class InventoryManager {
    private final String idItem;
    private double khoiLuong;
    private final double sucChuaToiDa;

    public InventoryManager(String idItem, double khoiLuong, double sucChuaToiDa) {
        if (idItem == null || idItem.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã mặt hàng trong kho không được để trống.");
        }
        if (!Double.isFinite(khoiLuong) || khoiLuong < 0) {
            throw new IllegalArgumentException("Khối lượng tồn ban đầu phải là số hữu hạn không âm.");
        }
        if (!Double.isFinite(sucChuaToiDa) || sucChuaToiDa <= 0) {
            throw new IllegalArgumentException("Sức chứa tối đa phải là số hữu hạn lớn hơn 0.");
        }
        this.idItem = idItem.trim();
        this.khoiLuong = khoiLuong;
        this.sucChuaToiDa = sucChuaToiDa;
    }

    public void addStock(double weight, EventManager eventManager) {
        validatePositiveWeight(weight, "Khối lượng nhập kho");
        double newTotal = this.khoiLuong + weight;
        if (!Double.isFinite(newTotal)) {
            throw new IllegalArgumentException("Khối lượng tồn sau khi nhập vượt giới hạn số.");
        }

        // Cập nhật tồn trước khi phát cảnh báo để listener nhìn thấy số lượng mới nhất.
        this.khoiLuong = newTotal;
        if (newTotal > this.sucChuaToiDa) {
            String alert = String.format(
                    "CẢNH BÁO: Mặt hàng [%s] vượt sức chứa bãi! (Tồn sau nhập: %.2f kg / Tối đa: %.2f kg)",
                    idItem, newTotal, sucChuaToiDa);
            if (eventManager != null) {
                eventManager.publish(new ShopEvent("OUT_OF_CAPACITY", alert));
            } else {
                System.out.println("[WARNING] " + alert);
            }
        }
    }

    public void addStock(double weight) {
        addStock(weight, null);
    }

    public void removeStock(double weight) {
        validatePositiveWeight(weight, "Khối lượng xuất kho");
        if (weight > this.khoiLuong) {
            throw new IllegalArgumentException(String.format(
                    "Xuất kho thất bại: [%s] không đủ hàng trong kho! (Tồn: %.2f kg, Yêu cầu xuất: %.2f kg)",
                    idItem, khoiLuong, weight));
        }
        this.khoiLuong -= weight;
    }

    public void updateStock(double newWeight) {
        if (!Double.isFinite(newWeight) || newWeight < 0) {
            throw new IllegalArgumentException("Khối lượng tồn kho phải là số hữu hạn không âm.");
        }
        this.khoiLuong = newWeight;
        if (newWeight > sucChuaToiDa) {
            System.out.printf("[WARNING] Mặt hàng [%s] đang vượt sức chứa: %.2f / %.2f kg%n",
                    idItem, newWeight, sucChuaToiDa);
        }
    }

    private static void validatePositiveWeight(double weight, String label) {
        if (!Double.isFinite(weight) || weight <= 0) {
            throw new IllegalArgumentException(label + " phải là số hữu hạn lớn hơn 0.");
        }
    }

    public double checkStock() { return this.khoiLuong; }
    public String getIdItem() { return idItem; }
    public double getKhoiLuong() { return khoiLuong; }
    public double getSucChuaToiDa() { return sucChuaToiDa; }
}
