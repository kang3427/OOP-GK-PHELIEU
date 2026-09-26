package manager;

import model.ScrapItem;

import java.util.List;
import java.util.function.Predicate;// Predicate là một Functional Interface trả về True False

public class ScrapFilter { // lớp lọc phế phẩm
    public List<ScrapItem> filter(// filter trả về một ds Scrap
                                  List<ScrapItem> items, Predicate<ScrapItem> condition) {
        // truyền vào hai biến ds item và condition(ddeief kiện lọc)
        return items.stream()           // Chuyển List thành Stream để xử lý
                .filter(condition)      // Áp dụng điều kiện lọc
                .toList();              // Thu thập kết quả trả về một List mới
    }
}