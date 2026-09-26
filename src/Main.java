import java.util.ArrayList;
import java.util.List;
import manager.ScrapFilter;
import payment.*;
import model.*;
import enums.*;
import event.*;

public class Main {
    public static void main(String[] args) {
        //update
        // ==========================================
        // PHẦN 1: KIỂM THỬ TÍNH NĂNG LỌC PHẾ LIỆU (LAMBDA & STREAM)
        // ==========================================
        System.out.println("========== KIỂM THỬ LỌC PHẾ LIỆU ==========");

        List<ScrapItem> items = new ArrayList<>();
        // Giả sử MetalScrap đã được định nghĩa đúng hàm khởi tạo
        items.add(new MetalScrap("M01", "Sắt vụn", 4000, 15.5, MetalType.KIM_LOAI_DEN));
        items.add(new MetalScrap("M02", "Nhôm khối", 15000, 0, MetalType.KIM_LOAI_MAU));
        items.add(new MetalScrap("M03", "Vỏ lon nhôm", 12000, 5.0, MetalType.KIM_LOAI_DEN));
        // Đổi MetalScrap thành PaperScrap nếu P01 là giấy báo
        // items.add(new PaperScrap("P01", "Giấy báo", 3000, 20.0));
        items.add(new PaperScrap("P01", "Giấy báo", 3000, 20.0,PaperType.NEWSPAPER)); // Tạm giữ theo code của bạn

        ScrapFilter filter = new ScrapFilter();

        System.out.println("--- 1. Lọc phế liệu có giá > 5000 ---");
        List<ScrapItem> highPriceScraps = filter.filter(items, i -> i.getPricePerKg() > 5000);
        highPriceScraps.forEach(System.out::println);

        System.out.println("\n--- 2. Lọc phế liệu có khối lượng > 0 (Còn hàng) ---");
        // Kiểm tra xem hàm lấy khối lượng của bạn tên là getWeight hay getWeightAvailable
        List<ScrapItem> availableScraps = filter.filter(items, i -> i.getWeight() > 0);
        availableScraps.forEach(System.out::println);

        System.out.println("\n--- 3. Lọc phế liệu có tên chứa chữ 'nhôm' ---");
        List<ScrapItem> nhomScraps = filter.filter(items, i -> i.getName().toLowerCase().contains("nhôm"));
        nhomScraps.forEach(System.out::println);


        // ==========================================
        // PHẦN 2: KIỂM THỬ TÍNH NĂNG THANH TOÁN (POLYMORPHISM)
        // ==========================================
        System.out.println("\n========== KIỂM THỬ THANH TOÁN ==========");

        Payment p1 = new CashPayment("Nguyen Hoang Luan");
        Payment p2 = new BankPayment("Vietcombank", "1012345678", "NGUYEN VAN A");
        Payment p3 = new EWalletPayment("MoMo", "0909123456");

        p1.pay(300000);
        p2.pay(300000);
        p3.pay(300000);


        // ==========================================
        // PHẦN 3: KIỂM THỬ HỆ THỐNG SỰ KIỆN (EVENT)
        // ==========================================
        System.out.println("\n========== HỆ THỐNG SỰ KIỆN CỬA HÀNG ==========");

        EventManager eventManager = new EventManager();

        // 1. Cài đặt EventListener bằng Lambda như tài liệu yêu cầu[cite: 4]
        eventManager.subscribe(
                event -> System.out.println(
                        "[THÔNG BÁO] " + event.getMessage()
                )
        );

        System.out.println("--- Giao dịch thu mua bắt đầu ---");

        // 2. Kích hoạt sự kiện khi PurchaseOrder tạo phiếu[cite: 4]
        // Ở thực tế sau này, lệnh này sẽ nằm trong file PurchaseOrder.java
        eventManager.publish(
                new ShopEvent(
                        "ORDER_CREATED",
                        "Phiếu thu mua PL001 đã được tạo!"
                )
        );

        // Kích hoạt thêm một sự kiện thanh toán khác để test[cite: 4]
        eventManager.publish(
                new ShopEvent(
                        "PAYMENT_SUCCESS",
                        "Đã thanh toán 350.000 VND cho khách."
                )
        );


        // ==========================================
        // PHẦN 4: KIỂM THỬ HÓA ĐƠN (PHẦN K - INVOICE)
        // ==========================================
        System.out.println("\n========== KIỂM THỬ HÓA ĐƠN (INVOICE) ==========");

        // 1. Tạo các món phế liệu khách mang tới bán (Thiết lập giá và khối lượng)
        // Sắt vụn: 25kg x 10.000đ = 250.000đ
        ScrapItem sat = new MetalScrap("M01", "Sắt vụn", 10000, 25.0, MetalType.KIM_LOAI_DEN);
        // Nhôm: 5kg x 20.000đ = 100.000đ
        ScrapItem nhom = new MetalScrap("M02", "Nhôm", 20000, 5.0, MetalType.KIM_LOAI_MAU);

        // 2. Tạo giỏ hàng (Cart) chứa đồ khách mang tới
        // (Nếu file Cart.java của ông dùng cấu trúc khác thì điều chỉnh nhẹ chỗ này nhé)
        Cart cart = new Cart();
        cart.getCart().add(new CartItem(sat));
        cart.getCart().add(new CartItem(nhom));

        // 3. Lập phiếu thu mua (PurchaseOrder) cho khách hàng
        PurchaseOrder order = new PurchaseOrder("PL001", "Nguyễn Văn Thoáng", cart);

        // 4. Chọn hình thức thanh toán cho khách
        Payment paymentMethod = new CashPayment("Kế toán trưởng");

        // 5. Khởi tạo Hóa đơn (Invoice) và in ra màn hình
        Invoice invoice = new Invoice("INV-999", order, paymentMethod);
        invoice.printInvoice();
    }
}


