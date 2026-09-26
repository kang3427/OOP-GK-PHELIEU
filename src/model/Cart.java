package model;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp đại diện cho Giỏ hàng / Phiếu cân gom tạm thời.
 * Lưu giữ danh sách các mặt hàng (CartItem) đang được tiếp nhận và cân đếm trước khi tiến hành chốt đơn mua chính thức.
 */

public class Cart {
    private List<CartItem> items= new ArrayList<>();

    public void addItem(ScrapItem item) {
        items.add(new CartItem(item));
    }

    public void removeItem(String id) {
        items.removeIf(cartItem -> cartItem.getItem().getId().equals(id));
    }

    public double calTotal(){
        double tong=0;
        for (CartItem x: items){
            tong+=x.calPrice();
        }
        return tong;
    }

    public void showCart(){
        System.out.println("====== DANH SÁCH GIỎ HÀNG ======");
        if (items.isEmpty()){
            System.out.println("Giỏ hàng không có gì");
        }
        else{
            for (CartItem x: items){
                System.out.println("Mã ID: " + x.getItem().getId() +
                        " | Tên phế liệu: " + x.getItem().getName() +
                        " | Đơn giá: " + x.getItem().getPricePerKg() + " VNĐ/kg" +
                        " | Khối lượng: " + x.getItem().getWeight() + " kg" +
                        " | Thành tiền: " + x.calPrice() + " VNĐ");
            }
            System.out.println("------------------------------------------------------------------");
            System.out.println("TỔNG TIỀN GIỎ HÀNG: " + calTotal() + " VNĐ");
        }
    }

    public List<CartItem> getCart(){ return items; }
}
