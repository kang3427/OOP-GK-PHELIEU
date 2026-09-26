package manager;
import model.ScrapItem;
import java.util.List;
import java.util.function.Predicate;// Predicate là một Functional Interface trả về True False

/**
 * Lớp xử lý bộ lọc phế liệu (Scrap Filtering Component).
 * Cung cấp các công cụ lọc và tìm kiếm danh sách phế liệu linh hoạt:
 * - Lọc phế liệu theo đơn giá, khối lượng tồn kho, hoặc tên chủng loại.
 * - Hỗ trợ kết hợp biểu thức Lambda (Predicate) để thực hiện các truy vấn dữ liệu nhanh chóng.
 */

public class ScrapFilter { // lớp lọc phế phẩm
    public List<ScrapItem> filter(// filter trả về một ds Scrap
                                  List<ScrapItem> items, Predicate<ScrapItem> condition) {
        // truyền vào hai biến ds item và condition(ddeief kiện lọc)
        return items.stream()           // Chuyển List thành Stream để xử lý
                .filter(condition)      // Áp dụng điều kiện lọc
                .toList();              // Thu thập kết quả trả về một List mới
    }
    public double TotalWeight(List<ScrapItem> items) {// tính tổng khối lượng phế liệu có trong kho
        return items.stream()
                .mapToDouble(i -> i.getWeight())
                .sum();
    }
}