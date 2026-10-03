package manager;
import model.*;
import java.util.List;
import java.util.function.Predicate;// Predicate là một Functional Interface trả về True False

/**
 * Lớp xử lý bộ lọc phế liệu (Scrap Filtering Component).
 * Cung cấp các công cụ lọc và tìm kiếm danh sách phế liệu linh hoạt:
 * - Lọc phế liệu theo đơn giá, khối lượng tồn kho, hoặc tên chủng loại.
 * - Hỗ trợ kết hợp biểu thức Lambda (Predicate) để thực hiện các truy vấn dữ liệu nhanh chóng.
 */

public class BoLocPheLieu { // lớp lọc phế phẩm
    public List<PheLieu> loc(// filter trả về một ds Scrap
                             List<PheLieu> danhsach, Predicate<PheLieu> dieukien) {
        // truyền vào hai biến ds item và dieukien(ddeief kiện lọc)
        return danhsach.stream()           // Chuyển List thành Stream để xử lý
                .filter(dieukien)      // Áp dụng điều kiện lọc
                .toList();              // Thu thập kết quả trả về một List mới
    }
}