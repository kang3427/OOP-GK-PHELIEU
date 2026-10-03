package event;
import java.util.ArrayList;
import java.util.List;


/**
 * Lớp quản lý và điều hướng sự kiện tập trung (Publisher / Subject).
 * Nắm giữ danh sách các bộ lắng nghe (EventListener), cung cấp các chức năng:
 * - Đăng ký lắng nghe sự kiện (subscribe)
 * - Hủy đăng ký lắng nghe (unsubscribe)
 * - Phát thông báo sự kiện (notify/publish) đến tất cả Listener đang lắng nghe khi có thay đổi
 */

public class QuanLiSuKien {
    private List<NhanSuKien> danhSachNhan = new ArrayList<>();

    // Hàm subscribe() dùng để đăng ký nhận thông báo
    public void subscribe(NhanSuKien nguoiNhan) {
        danhSachNhan.add(nguoiNhan);
    }

    // Hàm unsubscribe() dùng để hủy đăng ký
    public void unsubscribe(NhanSuKien nguoiNhan) {
        danhSachNhan.remove(nguoiNhan);
    }

    // Hàm publish() dùng để phát sự kiện cho các listener
    public void publish(Sukien suKien) {
        for (NhanSuKien nguoiNhan : danhSachNhan) {
            nguoiNhan.coSukien(suKien);
        }
    }
}