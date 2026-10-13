package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Bộ quản lý danh sách tổng quát.
 * Không nhận phần tử null; getAll() trả về bản sao để mã gọi không sửa trực tiếp
 * danh sách đang được quản lý.
 */
public class Manager<T> {
    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Không thể thêm phần tử null vào Manager.");
        }
        items.add(item);
    }

    public boolean remove(T item) {
        if (item == null) {
            return false;
        }
        return items.remove(item);
    }

    public List<T> getAll() {
        return new ArrayList<>(items);
    }

    /** Tìm kiếm/lọc danh sách bằng Predicate và Stream. */
    public List<T> find(Predicate<T> condition) {
        if (condition == null) {
            throw new IllegalArgumentException("Điều kiện tìm kiếm không được null.");
        }
        return items.stream().filter(condition).toList();
    }

    public int size() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }
}
