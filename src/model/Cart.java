package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Giỏ cân tạm thời trước khi chốt PurchaseOrder.
 * Mỗi CartItem lưu riêng giá và khối lượng của dòng giao dịch.
 */
public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    /** Giữ cách gọi cũ: sử dụng khối lượng hiện tại của ScrapItem. */
    public void addItem(ScrapItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Không thể thêm mặt hàng null vào giỏ.");
        }
        addItem(item, item.getWeight());
    }

    /** Thêm mặt hàng với khối lượng cân cụ thể và chụp lại đơn giá hiện tại. */
    public void addItem(ScrapItem item, double weightKg) {
        if (item == null) {
            throw new IllegalArgumentException("Không thể thêm mặt hàng null vào giỏ.");
        }
        items.add(new CartItem(item, item.getPricePerKg(), weightKg));
    }

    /** Thêm dòng với đơn giá và khối lượng được truyền tường minh. */
    public void addItem(ScrapItem item, double pricePerKg, double weightKg) {
        items.add(new CartItem(item, pricePerKg, weightKg));
    }

    /** Xóa tất cả dòng có cùng mã mặt hàng. */
    public void removeItem(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã mặt hàng cần xóa không được để trống.");
        }
        items.removeIf(line -> line.getItem().getId().equals(id));
    }

    public double calTotal() {
        double total = 0;
        for (CartItem line : items) {
            total += line.calPrice();
        }
        return total;
    }

    public void showCart() {
        System.out.println("====== DANH SÁCH GIỎ HÀNG ======");
        if (items.isEmpty()) {
            System.out.println("Giỏ hàng không có gì");
            return;
        }
        for (CartItem line : items) {
            System.out.println("Mã ID: " + line.getItem().getId()
                    + " | Tên phế liệu: " + line.getItem().getName()
                    + " | Đơn giá: " + line.getPricePerKgNow() + " VNĐ/kg"
                    + " | Khối lượng: " + line.getWeightKg() + " kg"
                    + " | Thành tiền: " + line.calPrice() + " VNĐ");
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("TỔNG TIỀN GIỎ HÀNG: " + calTotal() + " VNĐ");
    }

    /** Trả về bản sao chỉ đọc, ngăn bên ngoài sửa danh sách nội bộ. */
    public List<CartItem> getCart() {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
