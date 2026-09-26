
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
class Main {
    public static void main(String[] args) {
        // Tạo danh sách phế liệu mẫu
        List<ScrapItem> items = new ArrayList<>();// tạo ds item rổng
        items.add(new MetalScrap("M01", "Sắt vụn", 4000, 15.5));
        items.add(new MetalScrap("M02", "Nhôm khối", 15000, 0));
        items.add(new MetalScrap("M03", "Vỏ lon nhôm", 12000, 5.0));
        items.add(new MetalScrap("P01", "Giấy báo", 3000, 20.0));

        ScrapFilter filter = new ScrapFilter();//tạo bộ lọc

        System.out.println("--- 1. Lọc phế liệu có giá > 5000 ---");
        List<ScrapItem> highPriceScraps = filter.filter(items, i -> i.getPricePerKg() > 5000);
        // lọc theo đk trả ds về biến hightPriceScrap
        highPriceScraps.forEach(System.out::println);
        //forEach duyệt qua từng phần tử trong hight.. rồi in ra
        System.out.println("\n--- 2. Lọc phế liệu có khối lượng > 0 (Còn hàng) ---");
        List<ScrapItem> availableScraps = filter.filter(items, i -> i.getWeight() > 0);
        availableScraps.forEach(System.out::println);

        System.out.println("\n--- 3. Lọc phế liệu có tên chứa chữ 'nhôm' ---");
        List<ScrapItem> nhomScraps = filter.filter(items, i -> i.getName().toLowerCase().contains("nhôm"));
        nhomScraps.forEach(System.out::println);
    }
}

import payment.*;
public class Main {
    public static void main(String[] args) {
        Payment p1 = new CashPayment("Nguyen Hoang Luan");
        Payment p2 = new BankPayment("Vietcombank", "1012345678", "NGUYEN VAN A");
        Payment p3 = new EWalletPayment("MoMo", "0909123456");
        p1.pay(300000);
        p2.pay(300000);
        p3.pay(300000);
    }
}

