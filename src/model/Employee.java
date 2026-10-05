package model;
import java.util.List;
import java.util.ArrayList;

public class Employee {
    private String tenNhanVien;
    private String sdt;
    private List<String> diaChi=new ArrayList<>();

    public Employee(String tenNhanVien, String sdt, List<String> diaChi) {
        this.tenNhanVien = tenNhanVien;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }
    // Phương thức lấy thông tin hiển thị cả tên và danh sách địa chỉ
    public String getThongTin() {
        String kq = "Ten nhan vien: " + tenNhanVien + " So dien thoai: " + sdt + " Dia chi: ";
        for (String dc : diaChi) {
            kq += dc + "; ";
        }
        return kq;
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
