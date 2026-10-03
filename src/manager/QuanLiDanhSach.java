package manager;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Lớp quản lý chung / Lớp cơ sở (Base Manager Class).
 * Định nghĩa khung làm việc chung cho các bộ quản lý trong hệ thống:
 * - Cung cấp các thao tác quản lý dữ liệu nền tảng (thêm, sửa, xóa, tìm kiếm).
 * - Đảm bảo tính nhất quán trong cấu trúc điều hành các thực thể của vựa phế liệu.
 */

public class QuanLiDanhSach<T> {
    private List<T> danhsach = new ArrayList<>();

    public void them(T phantu) {
        if (phantu != null) {
            danhsach.add(phantu);
        }
    }

    public boolean xoa(T phantu) {
        return danhsach.remove(phantu);
    }

    public List<T> layTatCa() {
        return new ArrayList<>(danhsach);
    }

    // Tìm kiếm và lọc bằng Predicate + Stream
    public List<T> timKiem(Predicate<T> dieukien) {
        return danhsach.stream()
                .filter(dieukien)
                .toList();
    }

    public int laySoLuong() {
        return danhsach.size();
    }

    public void lamSach() {
        danhsach.clear();
    }
}
