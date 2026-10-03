package event;

/**
 * Lớp đại diện cho một sự kiện diễn ra trong vựa phế liệu (Event Object).
 * Chứa thông tin chi tiết về sự kiện phát ra như: loại sự kiện, dữ liệu kèm theo và thời điểm xảy ra.
 * (Ví dụ: Sự kiện "Nhập phế liệu vào kho", "Thanh toán phiếu mua", "Cảnh báo hết dung lượng kho").
 */

public class Sukien {
    private String loaisukien;
    private String noidung;

    public Sukien(String loaisukien, String noidung) {
        this.loaisukien = loaisukien;
        this.noidung = noidung;
    }

    public String getLoaisukien() {
        return loaisukien;
    }

    public String getNoidung() {
        return noidung;
    }
}
