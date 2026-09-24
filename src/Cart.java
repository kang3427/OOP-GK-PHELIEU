package GiuaKi;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

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
}
