package model;
import enums.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp đại diện cho Phiếu thu mua phế liệu chính thức.
 * Lưu trữ thông tin toàn bộ giao dịch: khách hàng bán, danh sách chi tiết hàng (PurchaseOrderItem),
 * tổng tiền phải thanh toán, trạng thái phiếu (OrderStatus) và thời gian lập phiếu.
 */

public class PurchaseOrder {
    private String orderId;
    private Customer customer;
    private List<PurchaseOrderItem> order = new ArrayList<>();
    private OrderStatus status= OrderStatus.PENDING;

    public void addItem(ScrapItem item) {
        order.add(new PurchaseOrderItem(item));
    }

    public void removeItem(String id) {
        order.removeIf(OrderItem -> OrderItem.getItem().getId().equals(id));
    }

    public PurchaseOrder(String orderId, Customer customer, Cart cart) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã đơn hàng không được để trống!");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Khách hàng không được để trống!");
        }
        this.orderId = orderId;
        this.customer = customer;
        this.status = OrderStatus.PROCESSING;

        if (cart != null && cart.getCart() != null) {
            for (CartItem x : cart.getCart()) {
                // SỬA DÒNG NÀY: Truyền cả item và pricePerKgNow đã chốt từ giỏ hàng
                this.order.add(new PurchaseOrderItem(x.getItem(), x.getPricePerKgNow()));
            }
        }
    }

    public double calTotal(){
        double tong=0;
        for (PurchaseOrderItem x: order){
            tong+=x.calPrice();
        }
        return tong;
    }

    public double getTotalWeight() {
        double totalWeight = 0;
        for (PurchaseOrderItem x : order) {
            totalWeight += x.getWeightKg();
        }
        return totalWeight;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public Customer getCustomer() {
        return this.customer;
    }
    public String getOrderId() {
        return this.orderId;
    }
    public List<PurchaseOrderItem> getItems() {
        return this.order;
    }
    public OrderStatus getStatus() { return this.status; }

    public void showOrder(){
        System.out.println("====== Hóa đơn của bạn là ======");
        if (order.isEmpty()){
            System.out.println("Hóa đơn không có gì");
        }
        else{
            System.out.println("Mã ID: " + orderId);
            System.out.println("Tên khách hàng : " + (customer != null ? customer.getName() : "Khách vãng lai"));
            System.out.println("Trạng thái : " + status);
            for (PurchaseOrderItem x : order) {
                System.out.println(x.getItem().getName() + " " + x.getItem().getWeight() + " kg | Thành tiền: " + x.calPrice() + " VNĐ");
            }
            System.out.println("------------------------------------------------------------------");
            System.out.println("TỔNG TIỀN: " + calTotal() + " VNĐ");
        }
    }
}
