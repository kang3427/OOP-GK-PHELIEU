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
    private String maDon;
    private String khachHang;
    private List<ChiTietDonThuMua> danhSachMatHang = new ArrayList<>();
    private TrangThaiDonHang trangThai = TrangThaiDonHang.CHO_XULI;

    public void themMatHang(PheLieu pheLieu) {
        danhSachMatHang.add(new ChiTietDonThuMua(pheLieu));
    }

    public void xoaMatHang(String ma) {
        danhSachMatHang.removeIf(chiTiet -> chiTiet.getPheLieu().getMa().equals(ma));
    }

    public DonThuMua(String maDon, String khachHang, GioHang gioHang) {
        this.maDon = maDon;
        this.khachHang = khachHang;
        this.trangThai = TrangThaiDonHang.DANG_XULI;

        for (ChiTietGioHang x: gioHang.layDanhSach()){
            PheLieu i = x.getPheLieu();
            this.themMatHang(i);
        }
    }

    public double tinhTongTien(){
        double tong=0;
        for (ChiTietDonThuMua x: danhSachMatHang){
            tong+=x.tinhTien();
        }
        return tong;
    }

    public String getKhachHang() {
        return this.khachHang;
    }
    public List<ChiTietDonThuMua> layDanhSachMatHang() {
        return this.danhSachMatHang;
    }

    public void showOrder(){
        System.out.println("====== Hóa đơn của bạn là ======");
        if (danhSachMatHang.isEmpty()){
            System.out.println("Hóa đơn không có gì");
        }
        else{
            System.out.println("Mã ID: " + maDon);
            System.out.println("Tên khách hàng : " + khachHang);
            System.out.println("Trạng thái : " + trangThai);
            for (ChiTietDonThuMua x: danhSachMatHang){
                System.out.println(x.getPheLieu().getTen()+ " "+x.getPheLieu().getKhoiLuong()+" thành tiền: "+x.tinhTien());
            }
            System.out.println("------------------------------------------------------------------");
            System.out.println("TỔNG TIỀN: " + tinhTongTien() + " VNĐ");
        }
    }
}
