package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Thông tin nhân viên thực hiện giao dịch.
 * getThongTin() tạo đúng chuỗi thông tin nhân viên để hiển thị cùng kết quả thanh toán.
 */
public class Employee {
    private final String idNhanVien;
    private final String tenNhanVien;
    private final String sdt;
    private final List<String> diaChi = new ArrayList<>();

    public Employee(String idNhanVien, String tenNhanVien, String sdt, List<String> diaChi) {
        if (idNhanVien == null || idNhanVien.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống.");
        }
        if (tenNhanVien == null || tenNhanVien.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên nhân viên không được để trống.");
        }

        this.idNhanVien = idNhanVien.trim();
        this.tenNhanVien = tenNhanVien.trim();
        this.sdt = sdt == null ? "" : sdt.trim();

        if (diaChi != null) {
            for (String dc : diaChi) {
                if (dc != null && !dc.trim().isEmpty()) {
                    this.diaChi.add(dc.trim());
                }
            }
        }
    }

    /**
     * Constructor tương thích với code cũ chưa truyền mã nhân viên.
     * Luồng mới nên truyền idNhanVien để thông tin in trên biên nhận có mã chính xác.
     */
    public Employee(String tenNhanVien, String sdt, List<String> diaChi) {
        this("NV-CHUA-CAP-NHAT", tenNhanVien, sdt, diaChi);
    }

    public String getThongTin() {
        StringBuilder result = new StringBuilder("Id: ")
                .append(idNhanVien)
                .append(" Ten nhan vien: ")
                .append(tenNhanVien)
                .append(" So dien thoai: ")
                .append(sdt)
                .append(" Dia chi: ");

        if (diaChi.isEmpty()) {
            result.append("Chua cap nhat");
        } else {
            result.append(String.join("; ", diaChi)).append(";");
        }
        return result.toString();
    }

    public String getIdNhanVien() {
        return idNhanVien;
    }

    public List<String> getDiaChi() {
        return Collections.unmodifiableList(new ArrayList<>(diaChi));
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public String getSdt() {
        return sdt;
    }
}
