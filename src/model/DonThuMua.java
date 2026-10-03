package model;
import enums.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp đại diện cho Phiếu thu mua phế liệu chính thức.
 * Lưu trữ thông tin toàn bộ giao dịch: khách hàng bán, danh sách chi tiết hàng (PurchaseOrderItem),
 * tổng tiền phải thanh toán, trạng thái phiếu (OrderStatus) và thời gian lập phiếu.
 */

public class DonThuMua {
    private String orderId;
    private String customer;
    private List<ChiTietDonThuMua> order = new ArrayList<>();
    private TrangThaiDonHang status= TrangThaiDonHang.CHO_XULI;

    public void addItem(PheLieu item) {
        order.add(new ChiTietDonThuMua(item));
    }

    public void removeItem(String id) {
        order.removeIf(OrderItem -> OrderItem.getPheLieu().getId().equals(id));
    }

    public DonThuMua(String orderId, String customer, GioHang cart) {
        this.orderId = orderId;
        this.customer = customer;
        this.status = TrangThaiDonHang.DANG_XULI;

        for (ChiTietGioHang x: cart.layDanhSach()){
            PheLieu i = x.getItem();
            this.addItem(i);
        }
    }

    public double tinhTongTien(){
        double tong=0;
        for (ChiTietDonThuMua x: order){
            tong+=x.calPrice();
        }
        return tong;
    }

    public String getCustomer() {
        return this.customer;
    }
    public List<ChiTietDonThuMua> layDanhSachMatHang() {
        return this.order;
    }

    public void showOrder(){
        System.out.println("====== Hóa đơn của bạn là ======");
        if (order.isEmpty()){
            System.out.println("Hóa đơn không có gì");
        }
        else{
            System.out.println("Mã ID: " + orderId);
            System.out.println("Tên khách hàng : " + customer);
            System.out.println("Trạng thái : " + status);
            for (ChiTietDonThuMua x: order){
                System.out.println(x.getPheLieu().getTen()+ " "+x.getPheLieu().getKhoiLuong()+" thành tiền: "+x.calPrice());
            }
            System.out.println("------------------------------------------------------------------");
            System.out.println("TỔNG TIỀN: " + tinhTongTien() + " VNĐ");
        }
    }
}
