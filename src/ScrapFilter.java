import java.util.List;
import java.util.function.Predicate;

public class ScrapFilter {
    // Phương thức sử dụng Stream và Predicate để lọc danh sách
    public List<ScrapItem> filter(
            List<ScrapItem> items,
            Predicate<ScrapItem> condition) {
        return items.stream()           // Chuyển List thành Stream để xử lý tuần tự
                .filter(condition)      // Áp dụng điều kiện lọc (condition)
                .toList();              // Thu thập kết quả trả về một List mới
    }
}