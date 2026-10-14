package model;
import java.util.List;
import java.util.ArrayList;

public class Employee {
    private String tenNhanVien;
    private String idNhanVien;
    private String sdt;
    private List<String> diaChi=new ArrayList<>();

    public Employee(String idNhanVien, String tenNhanVien, String sdt, List<String> diaChi) {
        // 1. Kiểm tra ID Nhân viên
        if (idNhanVien == null || idNhanVien.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống!");
        }

        // 2. Kiểm tra Tên nhân viên
        if (tenNhanVien == null || tenNhanVien.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên nhân viên không được để trống!");
        }

        // 3. Kiểm tra Số điện thoại (Không null/rỗng )
        if (sdt == null || sdt.trim().isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống!");
        }


        // 4. Kiểm tra Danh sách địa chỉ (Không null và không rỗng)
        if (diaChi == null || diaChi.isEmpty()) {
            throw new IllegalArgumentException("Danh sách địa chỉ không được để trống!");
        }

        this.idNhanVien = idNhanVien.trim();
        this.tenNhanVien = tenNhanVien.trim();
        this.sdt = sdt.trim();
        this.diaChi = diaChi;
    }
    // Phương thức lấy thông tin hiển thị cả tên và danh sách địa chỉ
    public String getThongT() {
        String kq = "Id: " + idNhanVien + " Ten nhan vien: " + tenNhanVien + " So dien thoai: " + sdt + " Dia chi: ";
        for (String dc : diaChi) {
            kq += dc + "; ";
        }
        return kq;
    }
    public String getThongTin() {
        return idNhanVien + " | " + tenNhanVien +" | "+ sdt;
    }

    //getter
    public  List<String> getDiaChi() {
        return diaChi;
    }
    public String  getTenNhanVien() {
        return tenNhanVien;
    }
    public String getSdt(){
        return sdt;
    }
}
