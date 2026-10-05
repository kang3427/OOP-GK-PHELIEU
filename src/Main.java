import enums.*;
import event.*;
import manager.*;
import model.*;
import payment.*;
import reward.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ==========================================
        // PHẦN 1: KIỂM THỬ TÍNH NĂNG LỌC PHẾ LIỆU (LAMBDA & STREAM)
        // ==========================================
        System.out.println("========== KIỂM THỬ LỌC PHẾ LIỆU ==========");

        List<ScrapItem> items = new ArrayList<>();
        items.add(new MetalScrap("M01", "Sắt vụn", 4000, 15.5, MetalType.KIM_LOAI_DEN));
        items.add(new MetalScrap("M02", "Nhôm khối", 15000, 0, MetalType.KIM_LOAI_MAU));
        items.add(new MetalScrap("M03", "Vỏ lon nhôm", 12000, 5.0, MetalType.KIM_LOAI_MAU));
        items.add(new PaperScrap("P01", "Giấy báo", 3000, 20.0, PaperType.NEWSPAPER));

        ScrapFilter filter = new ScrapFilter();

        System.out.println("--- 1. Lọc phế liệu có giá > 5000 ---");
        List<ScrapItem> highPriceScraps = filter.filter(items, i -> i.getPricePerKg() > 5000);
        highPriceScraps.forEach(System.out::println);

        System.out.println("\n--- 2. Lọc phế liệu có khối lượng > 0 (Còn hàng) ---");
        List<ScrapItem> availableScraps = filter.filter(items, i -> i.getWeight() > 0);
        availableScraps.forEach(System.out::println);

        System.out.println("\n--- 3. Lọc phế liệu có tên chứa chữ 'nhôm' ---");
        List<ScrapItem> nhomScraps = filter.filter(items, i -> i.getName().toLowerCase().contains("nhôm"));
        nhomScraps.forEach(System.out::println);


        // ==========================================
        // PHẦN 2: KIỂM THỬ TÍNH NĂNG THANH TOÁN (POLYMORPHISM)
        // ==========================================
        System.out.println("\n========== KIỂM THỬ THANH TOÁN ==========");

        Payment p1 = new CashPayment("Nguyen Hoang Luan","8972964179",new Employee("nguyen van a","07782642552",List.of("xom 1","van tuong")));
        Payment p2 = new BankPayment("Vietcombank", "1012345678", "NGUYEN VAN A","9872461857",new Employee("nguyen van a","07782642552",List.of("xom 1","van tuong")));
        Payment p3 = new EWalletPayment("MoMo", "0909123456","nguyen the khang","7493384957",new Employee("nguyen van a","07782642552",List.of("xom 1","van tuong")));

        p1.pay(300000);
        p2.pay(300000);
        p3.pay(300000);


        // ==========================================
        // PHẦN 3: KIỂM THỬ HỆ THỐNG SỰ KIỆN (EVENT)
        // ==========================================
        System.out.println("\n========== HỆ THỐNG SỰ KIỆN CỬA HÀNG ==========");

        EventManager eventManager = new EventManager();

        eventManager.subscribe(
                event -> System.out.println("[THÔNG BÁO] " + event.getMessage())
        );

        System.out.println("--- Giao dịch thu mua bắt đầu ---");

        eventManager.publish(
                new ShopEvent("ORDER_CREATED", "Phiếu thu mua PL001 đã được tạo!")
        );

        eventManager.publish(
                new ShopEvent("PAYMENT_SUCCESS", "Đã thanh toán 350.000 VND cho khách.")
        );


        // ==========================================
        // PHẦN 4: KIỂM THỬ QUẢN LÝ TỒN KHO (PHẦN F - INVENTORY MANAGER)
        // ==========================================
        System.out.println("\n========== KIỂM THỬ TỒN KHO PHẾ LIỆU ==========");

        // Khởi tạo quản lý kho cho mặt hàng "M01" với số lượng tồn 50kg, sức chứa tối đa 100kg
        InventoryManager inventoryM01 = new InventoryManager("M01", 50.0, 100.0);

        System.out.println("Tồn kho hiện tại M01: " + inventoryM01.checkStock() + " kg");

        // Nhập thêm 20kg vào kho
        inventoryM01.addStock(20.0, eventManager);
        System.out.println("Tồn kho sau khi nhập: " + inventoryM01.checkStock() + " kg");

        // Nhập vượt sức chứa để kiểm tra cảnh báo event (70 + 40 = 110kg > 100kg)
        inventoryM01.addStock(40.0, eventManager);

        // Xuất kho 30kg
        inventoryM01.removeStock(30.0);
        System.out.println("Tồn kho sau khi xuất tái chế: " + inventoryM01.checkStock() + " kg");


        // ==========================================
        // PHẦN 5: KIỂM THỬ CHÍNH SÁCH THƯỞNG (PHẦN G - REWARDABLE)
        // ==========================================
        System.out.println("\n========== KIỂM THỬ THƯỞNG & CHIẾT KHẤU ==========");

        // Cách 1: Sử dụng class CustomerReward (Tỷ lệ thưởng 5%)
        Rewardable rewardPolicy = new CustomerReward(0.05);
        double bonusMoney = rewardPolicy.calculateReward(50.0, 350000.0);
        System.out.println("Tiền thưởng cho khách (5%): " + bonusMoney + " VNĐ");

        // Cách 2: Sử dụng biểu thức Lambda trực tiếp (Thưởng 1.000 VNĐ cho mỗi kg nếu bán trên 20kg)
        Rewardable bulkReward = (weight, basePrice) -> (weight >= 20.0) ? weight * 1000 : 0;
        System.out.println("Tiền thưởng lô hàng lớn (>20kg): " + bulkReward.calculateReward(25.0, 350000.0) + " VNĐ");


        // ==========================================
        // PHẦN 6: KIỂM THỬ DẠNG GENERIC (PHẦN H - MANAGER<T>)
        // ==========================================
        System.out.println("\n========== KIỂM THỬ GENERIC MANAGER<T> ==========");

        Manager<ScrapItem> scrapManager = new Manager<>();
        scrapManager.add(new PlasticScrap("PL01", "Chai nhựa PET", 8000, 12.0, PlasticType.PET));
        scrapManager.add(new EWasteScrap("E01", "Bo mạch cũ", 50000, 2.5, EWasteType.CLEANORIGIN));

        System.out.println("Tổng số mặt hàng trong ScrapManager: " + scrapManager.size());

        // Tìm kiếm các mặt hàng có giá lớn hơn 10.000 VNĐ dùng Predicate & Stream
        List<ScrapItem> expensiveItems = scrapManager.find(item -> item.getPricePerKg() > 10000);
        System.out.println("Các mặt hàng giá > 10.000 VNĐ:");
        expensiveItems.forEach(System.out::println);


        // ==========================================
        // PHẦN 7: KIỂM THỬ HÓA ĐƠN (PHẦN K - INVOICE)
        // ==========================================
        System.out.println("\n========== KIỂM THỬ HÓA ĐƠN (INVOICE) ==========");

        ScrapItem sat = new MetalScrap("M01", "Sắt vụn", 10000, 25.0, MetalType.KIM_LOAI_DEN);
        ScrapItem nhom = new MetalScrap("M02", "Nhôm", 20000, 5.0, MetalType.KIM_LOAI_MAU);

        Cart cart = new Cart();
        cart.addItem(sat);
        cart.addItem(nhom);

        PurchaseOrder order = new PurchaseOrder("PL001", "Nguyễn Văn Thoáng", cart);
        Payment paymentMethod = new CashPayment("nguyen van thoang", "8320487362",new Employee("nguyen van a","07782642552",List.of("xom 1","van tuong")));

        Invoice invoice = new Invoice("INV-999", order, paymentMethod);
        invoice.printInvoice();
    }
}