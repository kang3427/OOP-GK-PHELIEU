package model;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp đại diện cho Giỏ hàng / Phiếu cân gom tạm thời.
 * Lưu giữ danh sách các mặt hàng (CartItem) đang được tiếp nhận và cân đếm trước khi tiến hành chốt đơn mua chính thức.
 */

public class GioHang {
    private List<ChiTietGioHang> danhSachMatHang = new ArrayList<>(); //Tạo mảng lưu các phế liệu
    // Thêm vào cart
    public void themMatHang(PheLieu item) {
        danhSachMatHang.add(new ChiTietGioHang(item));
    }
    // Xóa 1 phẩn tử khỏi cart
    public void xoaMatHang(String id) {
        danhSachMatHang.removeIf(cartItem -> cartItem.getPheLieu().getMa().equals(id));
    }
    //tinh tiền trong cart
    public double tinhTongTien(){
        double tongTien=0;
        for (ChiTietGioHang x: danhSachMatHang){
            tongTien+=x.tinhTien();
        }
        return tongTien;
    }
    //Xem cart
    public void HienThi(){
        System.out.println("====== DANH SÁCH GIỎ HÀNG ======");
        if (danhSachMatHang.isEmpty()){
            System.out.println("Giỏ hàng không có gì");
        }
        else{
            for (ChiTietGioHang x: danhSachMatHang){
                System.out.println("Mã ID: " + x.getPheLieu().getMa() +
                        " | Tên phế liệu: " + x.getPheLieu().getTen() +
                        " | Đơn giá: " + x.getPheLieu().getDonGia() + " VNĐ/kg" +
                        " | Khối lượng: " + x.getPheLieu().getKhoiLuong() + " kg" +
                        " | Thành tiền: " + x.tinhTien() + " VNĐ");
            }
            System.out.println("------------------------------------------------------------------");
            System.out.println("TỔNG TIỀN GIỎ HÀNG: " + tinhTongTien() + " VNĐ");
        }
    }

    public List<ChiTietGioHang> layDanhSach(){ return danhSachMatHang; }
}
