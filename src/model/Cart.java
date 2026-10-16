package model;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp đại diện cho Giỏ hàng / Phiếu cân gom tạm thời.
 * Lưu giữ danh sách các mặt hàng (CartItem) đang được tiếp nhận và cân đếm trước khi tiến hành chốt đơn mua chính thức.
 */

public class Cart {
    private String cartId;
    private List<CartItem> items= new ArrayList<>(); //Tạo mảng lưu các phế liệu
    public Cart(String cartId) {
        // 1. Kiểm tra Mã giỏ hàng (cartId/cartId) không được null hoặc rỗng
        if (cartId == null || cartId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã giỏ hàng (cartId) không được để trống!");
        }
        this.cartId = cartId.trim();
        // Danh sách items đã được khởi tạo sẵn (new ArrayList<>()) ở phần khai báo thuộc tính
    }

    // Bổ sung Constructor không tham số (nếu có sử dụng khởi tạo giỏ hàng trống mặc định)
    public Cart() {
        this.cartId = "CART-" + System.currentTimeMillis();
    }
    // Thêm phế liệu theo giá niêm yết mặc định, tự nhập mã chi tiết giỏ hàng
    public void addItem(String cartItemId, ScrapItem item) {
        items.add(new CartItem(cartItemId, item));
    }

    // Thêm phế liệu với giá thỏa thuận riêng, tự nhập mã chi tiết giỏ hàng
    public void addItem(String cartItemId, ScrapItem item, double pricePerKgNow) {
        items.add(new CartItem(cartItemId, item, pricePerKgNow));
    }

    // Giữ các cách gọi cũ để không ảnh hưởng phần code khác.
    public void addItem(ScrapItem item) {
        items.add(new CartItem(item));
    }

    public void addItem(ScrapItem item, double pricePerKgNow) {
        items.add(new CartItem(item, pricePerKgNow));
    }
    // Xóa 1 phẩn tử khỏi cart
    public void removeItem(String id) {
        items.removeIf(cartItem -> cartItem.getItem().getId().equals(id));
    }
    //tinh tiền trong cart
    public double calTotal(){
        double tong=0;
        for (CartItem x: items){
            tong+=x.calPrice();
        }
        return tong;
    }
    //Xem cart
    public void showCart(){
        System.out.println("====== DANH SÁCH GIỎ HÀNG ======");
        if (items.isEmpty()){
            System.out.println("Giỏ hàng không có gì");
        }
        else{
            for (CartItem x: items){
                System.out.println("Mã ID: " + x.getItem().getId() +
                        " | Tên phế liệu: " + x.getItem().getName() +
                        " | Đơn giá chốt: " + x.getPricePerKgNow() + " VNĐ/kg" +
                        " | Khối lượng: " + x.getItem().getWeight() + " kg" +
                        " | Thành tiền: " + x.calPrice() + " VNĐ");
            }
            System.out.println("------------------------------------------------------------------");
            System.out.println("TỔNG TIỀN GIỎ HÀNG: " + calTotal() + " VNĐ");
        }
    }

    public List<CartItem> getCart(){ return items; }
    public String getCartId() {return cartId;}
}
