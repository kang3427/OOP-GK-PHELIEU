package GiuaKi;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrder {
    private String orderId;
    private String customer;
    private List<PurchaseOrderItem> order = new ArrayList<>();
    private orderStatus status= orderStatus.PENDING;

    public void addItem(ScrapItem item) {
        order.add(new PurchaseOrderItem(item));
    }

    public void removeItem(String id) {
        order.removeIf(OrderItem -> OrderItem.getItem().getId().equals(id));
    }

    public PurchaseOrder(String orderId, String customer, Cart cart) {
        this.orderId = orderId;
        this.customer = customer;
        this.status = orderStatus.PROCESSING;

        for (CartItem x: cart.getCart()){
            ScrapItem i = x.getItem();
            this.addItem(i);
        }
    }

    public double calTotal(){
        double tong=0;
        for (PurchaseOrderItem x: order){
            tong+=x.calPrice();
        }
        return tong;
    }

}
