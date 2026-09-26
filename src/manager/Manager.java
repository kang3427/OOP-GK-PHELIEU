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

public class Manager<T> {
    private List<T> items = new ArrayList<>();

    public void add(T item) {
        if (item != null) {
            items.add(item);
        }
    }

    public boolean remove(T item) {
        return items.remove(item);
    }

    public List<T> getAll() {
        return new ArrayList<>(items);
    }

    // Tìm kiếm và lọc bằng Predicate + Stream
    public List<T> find(Predicate<T> condition) {
        return items.stream()
                .filter(condition)
                .toList();
    }

    public int size() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }
}
