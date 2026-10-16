import enums.*;
import event.*;
import manager.*;
import model.*;
import payment.*;
import reward.*;
import queriesJava.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    // Hàm trợ giúp tìm đúng Kho tương ứng cho từng món phế liệu
    private static InventoryManager layKhoTheoPheLieu(ScrapItem pheLieu, InventoryManager khoKimLoai, InventoryManager khoGiay, InventoryManager khoNhua, InventoryManager khoDienTu) {
        if (pheLieu instanceof MetalScrap) return khoKimLoai;
        if (pheLieu instanceof PaperScrap) return khoGiay;
        if (pheLieu instanceof PlasticScrap) return khoNhua;
        if (pheLieu instanceof EWasteScrap) return khoDienTu;
        return null;
    }

    // Hàm hỗ trợ thêm phế liệu an toàn tránh dừng chương trình khi dữ liệu lỗi
    private static void themPheLieuAnToan(List<ScrapItem> danhSach, ScrapItem pheLieu) {
        if (pheLieu != null) {
            danhSach.add(pheLieu);
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("   QUẢN LÝ KHO & XỬ LÝ 4 ĐƠN HÀNG THU MUA (PURCHASEORDER) ");
        System.out.println("==================================================================\n");

        // 1. KHỞI TẠO 4 KHO BAN ĐẦU
        InventoryManager khoKimLoai = new InventoryManager("KHO_KIM_LOAI", 100.0, 5000.0);
        InventoryManager khoGiay    = new InventoryManager("KHO_GIAY",     30.0,  100.0);
        InventoryManager khoNhua    = new InventoryManager("KHO_NHUA",     10.0,  30.0);
        InventoryManager khoDienTu  = new InventoryManager("KHO_DIEN_TU",  20.0,  1000.0);

        System.out.println("1. TỒN KHO BAN ĐẦU:");
        System.out.println("   + Kim loại: " + khoKimLoai.checkStock() + " / " + khoKimLoai.getSucChuaToiDa() + " kg (Trống " + (khoKimLoai.getSucChuaToiDa() - khoKimLoai.checkStock()) + "kg)");
        System.out.println("   + Giấy    : " + khoGiay.checkStock()    + " / " + khoGiay.getSucChuaToiDa()    + " kg (Trống " + (khoGiay.getSucChuaToiDa() - khoGiay.checkStock()) + "kg)");
        System.out.println("   + Nhựa    : " + khoNhua.checkStock()    + " / " + khoNhua.getSucChuaToiDa()    + " kg (Trống " + (khoNhua.getSucChuaToiDa() - khoNhua.checkStock()) + "kg)");
        System.out.println("   + Điện tử : " + khoDienTu.checkStock()  + " / " + khoDienTu.getSucChuaToiDa()  + " kg (Trống " + (khoDienTu.getSucChuaToiDa() - khoDienTu.checkStock()) + "kg)\n");

        // 2. KHỞI TẠO DỮ LIỆU PHẾ LIỆU
        List<ScrapItem> items = new ArrayList<>();

        // A. Kim loại
        try { themPheLieuAnToan(items, new MetalScrap("M01", "Sắt vụn công trình", 4000, 15.5, MetalType.KIM_LOAI_DEN)); } catch (Exception e) { System.out.println("[BO QUA M01]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new MetalScrap("M02", "Nhôm khối định hình", 15000, 0.0, MetalType.KIM_LOAI_MAU)); } catch (Exception e) { System.out.println("[BO QUA M02]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new MetalScrap("M03", "Vỏ lon nhôm bia", 12000, 5.0, MetalType.KIM_LOAI_MAU)); } catch (Exception e) { System.out.println("[BO QUA M03]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new MetalScrap("M04", "Sắt gỉ hỏng nặng", -5000, 10.0, MetalType.KIM_LOAI_DEN)); } catch (Exception e) { System.out.println("[BO QUA M04]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new MetalScrap("M05", "Đồng cáp điện cao thế", 120000, 8.2, MetalType.KIM_LOAI_MAU)); } catch (Exception e) { System.out.println("[BO QUA M05]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new MetalScrap("M06", "Inox 304 tấm phế liệu", 35000, 12.0, MetalType.KIM_LOAI_MAU)); } catch (Exception e) { System.out.println("[BO QUA M06]: " + e.getMessage()); }

        // B. Giấy
        try { themPheLieuAnToan(items, new PaperScrap("P01", "Giấy báo cũ", 3000, 20.0, PaperType.NEWSPAPER)); } catch (Exception e) { System.out.println("[BO QUA P01]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new PaperScrap("P02", "Thùng carton 3 lớp", 4500, 45.0, PaperType.CARTON)); } catch (Exception e) { System.out.println("[BO QUA P02]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new PaperScrap("P03", "Bìa carton ẩm ướt", 0, 12.0, PaperType.CARTON)); } catch (Exception e) { System.out.println("[BO QUA P03]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new PaperScrap("P04", "Giấy văn phòng A4", 5000, -5.0, PaperType.OFFICE_PAPER)); } catch (Exception e) { System.out.println("[BO QUA P04]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new PaperScrap("P05", "Tạp chí cũ", 2800, 8.5, PaperType.NEWSPAPER)); } catch (Exception e) { System.out.println("[BO QUA P05]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new PaperScrap("P06", "Sách giáo khoa cũ", 3500, 14.0, PaperType.OFFICE_PAPER)); } catch (Exception e) { System.out.println("[BO QUA P06]: " + e.getMessage()); }

        // C. Nhựa
        try { themPheLieuAnToan(items, new PlasticScrap("PL01", "Chai nhựa PET trong suốt", 8000, 12.0, PlasticType.PET)); } catch (Exception e) { System.out.println("[BO QUA PL01]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new PlasticScrap("PL02", "Can nhựa HDPE dầy", 11000, 65.0, PlasticType.HDPE)); } catch (Exception e) { System.out.println("[BO QUA PL02]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new PlasticScrap("PL03", "Ống nhựa PVC cháy xém", -2000, 0.0, PlasticType.PVC)); } catch (Exception e) { System.out.println("[BO QUA PL03]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new PlasticScrap("PL04", "Hộp nhựa thực phẩm PP", 9500, 4.0, PlasticType.PP)); } catch (Exception e) { System.out.println("[BO QUA PL04]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new PlasticScrap("PL05", "Chai nước giặt HDPE", 10500, 9.0, PlasticType.HDPE)); } catch (Exception e) { System.out.println("[BO QUA PL05]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new PlasticScrap("PL06", "Tấm nhựa PE phế thải", 6000, 15.0, PlasticType.PP)); } catch (Exception e) { System.out.println("[BO QUA PL06]: " + e.getMessage()); }

        // D. Điện tử
        try { themPheLieuAnToan(items, new EWasteScrap("E01", "Bo mạch máy tính cũ", 50000, 2.5, EWasteType.CLEANORIGIN)); } catch (Exception e) { System.out.println("[BO QUA E01]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new EWasteScrap("E02", "Linh kiện điện thoại hỏng", 80000, 1.2, EWasteType.CLEANORIGIN)); } catch (Exception e) { System.out.println("[BO QUA E02]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new EWasteScrap("E03", "Thân máy vi tính vỡ nát", 0, -2.0, EWasteType.NOORIGIN)); } catch (Exception e) { System.out.println("[BO QUA E03]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new EWasteScrap("E04", "Ổ cứng HDD cũ", 35000, 4.0, EWasteType.CLEANORIGIN)); } catch (Exception e) { System.out.println("[BO QUA E04]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new EWasteScrap("E05", "Bo mạch tivi trôi nổi", 18000, 5.5, EWasteType.NOORIGIN)); } catch (Exception e) { System.out.println("[BO QUA E05]: " + e.getMessage()); }
        try { themPheLieuAnToan(items, new EWasteScrap("E06", "Pin laptop hỏng", 45000, 3.0, EWasteType.CLEANORIGIN)); } catch (Exception e) { System.out.println("[BO QUA E06]: " + e.getMessage()); }

        System.out.println("\n2. Đã khởi tạo thành công " + items.size() + " phế liệu hợp lệ.\n");

        // 3. KHỞI TẠO DANH SÁCH NHÂN VIÊN & KHÁCH HÀNG
        List<Employee> danhSachNhanVien = new ArrayList<>();
        danhSachNhanVien.add(new Employee("NV01", "Nguyễn Văn Hùng", "0901234567", List.of("123 Lê Lợi, Q1")));
        danhSachNhanVien.add(new Employee("NV02", "Trần Thị Mai", "0912345678", List.of("456 Nguyễn Huệ, Q1")));
        danhSachNhanVien.add(new Employee("NV03", "Lê Hoàng Nam", "0987654321", List.of("789 CMT8, Q3")));

        List<Customer> danhSachKhachHang = new ArrayList<>();
        danhSachKhachHang.add(new Customer("CUST01", "Phạm Văn Minh", "0933111222", "12 Nguyễn Trãi, Q5", CustomerType.REGULAR));
        danhSachKhachHang.add(new Customer("CUST02", "Công ty Xây dựng An Phát", "0944333444", "45 Xô Viết Nghệ Tĩnh", CustomerType.AGENCY));
        danhSachKhachHang.add(new Customer("CUST03", "Bà Hoàng Thị Lan", "0955666777", "88 Võ Văn Tần, Q3", CustomerType.REGULAR));
        danhSachKhachHang.add(new Customer("CUST04", "Nguyễn Gia Bảo", "0977888999", "22 Điện Biên Phủ, Q Bình Thạnh", CustomerType.VIP));
        danhSachKhachHang.add(new Customer("CUST05", "Lê Minh Khang", "", "15 Lý Thường Kiệt, Q Tân Bình", CustomerType.REGULAR));

        System.out.println("3. Đã tạo List " + danhSachNhanVien.size() + " nhân viên và List " + danhSachKhachHang.size() + " khách hàng.\n");

        // 4. XỬ LÝ 4 ĐƠN HÀNG THU MUA
        EventManager eventManager = new EventManager();

        Cart cart1 = new Cart("CART01");
        cart1.addItem(items.get(0)); // M01
        cart1.addItem(items.get(1)); // M03
        cart1.addItem(items.get(4)); // P01
        cart1.addItem(items.get(6)); // P03
        PurchaseOrder order1 = new PurchaseOrder("ORD01", danhSachKhachHang.get(0), cart1);
        xuLyDonHang(order1, danhSachKhachHang.get(0), danhSachNhanVien.get(0), khoKimLoai, khoGiay, khoNhua, khoDienTu, eventManager, new CashPayment("PAY01", danhSachKhachHang.get(0).getName()));

        Cart cart2 = new Cart("CART02");
        cart2.addItem(items.get(3));  // M05
        cart2.addItem(items.get(4));  // M06
        cart2.addItem(items.get(7));  // P05
        cart2.addItem(items.get(14)); // E01
        PurchaseOrder order2 = new PurchaseOrder("ORD02", danhSachKhachHang.get(1), cart2);
        xuLyDonHang(order2, danhSachKhachHang.get(1), danhSachNhanVien.get(1), khoKimLoai, khoGiay, khoNhua, khoDienTu, eventManager, new BankPayment("PAY02", "Techcombank", "190312345678", danhSachKhachHang.get(1).getName()));

        Cart cart3 = new Cart("CART03");
        cart3.addItem(items.get(10)); // PL02 (Tràn kho Nhựa)
        cart3.addItem(items.get(17)); // E06
        PurchaseOrder order3 = new PurchaseOrder("ORD03", danhSachKhachHang.get(2), cart3);
        xuLyDonHang(order3, danhSachKhachHang.get(2), danhSachNhanVien.get(2), khoKimLoai, khoGiay, khoNhua, khoDienTu, eventManager, new CashPayment("PAY03", danhSachKhachHang.get(2).getName()));

        Cart cart4 = new Cart("CART04");
        cart4.addItem(items.get(11)); // PL04
        cart4.addItem(items.get(12)); // PL05
        cart4.addItem(items.get(15)); // E04
        cart4.addItem(items.get(16)); // E02
        PurchaseOrder order4 = new PurchaseOrder("ORD04", danhSachKhachHang.get(0), cart4);
        xuLyDonHang(order4, danhSachKhachHang.get(0), danhSachNhanVien.get(0), khoKimLoai, khoGiay, khoNhua, khoDienTu, eventManager, new CashPayment("PAY04", danhSachKhachHang.get(0).getName()));

        // 5. BÁO CÁO TỒN KHO CUỐI CÙNG
        System.out.println("\n========== TỒN KHO CUỐI CÙNG SAU KHI XỬ LÝ 4 ĐƠN HÀNG ==========");
        System.out.println("   + KHO KIM LOẠI: " + khoKimLoai.checkStock() + " / " + khoKimLoai.getSucChuaToiDa() + " kg");
        System.out.println("   + KHO GIẤY    : " + khoGiay.checkStock()    + " / " + khoGiay.getSucChuaToiDa()    + " kg");
        System.out.println("   + KHO NHỰA    : " + khoNhua.checkStock()    + " / " + khoNhua.getSucChuaToiDa()    + " kg");
        System.out.println("   + KHO ĐIỆN TỬ : " + khoDienTu.checkStock()  + " / " + khoDienTu.getSucChuaToiDa()  + " kg");

        // Đơn giả lập kiểm thử riêng cho truy vấn giá trị cao (Q26)
        Cart cartTestGiaTriCao = new Cart("CART_TEST_26");
        cartTestGiaTriCao.addItem(items.get(3), 300000.0);
        PurchaseOrder orderTestGiaTriCao = new PurchaseOrder("ORD_TEST_26", danhSachKhachHang.get(3), cartTestGiaTriCao);
        orderTestGiaTriCao.setStatus(OrderStatus.COMPLETED);

        // Đơn hàng chờ xử lý (Q3)
        Cart cartPending = new Cart("CART_PENDING");
        cartPending.addItem(items.get(0));
        PurchaseOrder orderPending = new PurchaseOrder("ORD05_PENDING", danhSachKhachHang.get(3), cartPending);
        orderPending.setStatus(OrderStatus.PENDING);

        List<PurchaseOrder> allOrders = List.of(order1, order2, order3, order4, orderTestGiaTriCao, orderPending);
        List<InventoryManager> allWarehouses = List.of(khoKimLoai, khoGiay, khoNhua, khoDienTu);

        List<Invoice> allInvoices = new ArrayList<>();
        CustomerReward reward = new CustomerReward(danhSachKhachHang.get(1));
        // Dùng hóa đơn đã tạo khi xử lý ORD02, không tạo hóa đơn thứ hai cho cùng đơn.
        if (order2.getInvoice() != null) {
            allInvoices.add(order2.getInvoice());
        }

        // =========================================================================
        // THỰC THI TOÀN BỘ 30 TRUY VẤN STREAM API (TỪ QUERIESJAVA)
        // =========================================================================

        System.out.println("\n------------------------------------------------------------------");
        System.out.println("        BÁO CÁO THỰC THI BỘ 10 TRUY VẤN ĐƠN HÀNG (Q1 - Q10)       ");
        System.out.println("------------------------------------------------------------------");
        QueriesJava.q1_getAllOrdersDetailed(allOrders);
        QueriesJava.q2_findOrderByIdDetailed(allOrders, "ORD01");
        QueriesJava.q3_getPendingOrdersDetailed(allOrders);
        QueriesJava.q4_getCompletedMetalItemsDetailed(allOrders);
        QueriesJava.q5_showItemsOfOrder(allOrders, "ORD02");
        QueriesJava.q6_getHighestValueOrderDetailed(allOrders);
        QueriesJava.q7_getBankPaymentInvoicesDetailed(allInvoices);
        QueriesJava.q8_getOver70PercentStockDetailed(allWarehouses);
        QueriesJava.q9_getTodayCancelledOrdersDetailed(allOrders);
        QueriesJava.q10_getOrdersOver2MillionDetailed(allOrders);

        System.out.println("\n------------------------------------------------------------------");
        System.out.println("        BÁO CÁO THỰC THI BỘ 10 TRUY VẤN PHẾ LIỆU & KHO (Q11 - Q20) ");
        System.out.println("------------------------------------------------------------------");
        QueriesJava.q11_getAllScrapItems(items);
        QueriesJava.q12_getZeroPriceItems(items);
        QueriesJava.q13_getHighPricePlasticItems(items);
        QueriesJava.q14_getTotalValueMetalItems(items);
        QueriesJava.q15_getCleanOriginEWasteItems(items);
        QueriesJava.q16_findScrapItemById(items, "M01");
        QueriesJava.q17_getPlasticItemsOver3Kg(items);
        QueriesJava.q18_getItemsAboveAveragePrice(items);
        QueriesJava.q19_getTop3LowestPriceItems(items);
        QueriesJava.q20_extractScrapItemDetail(items, "P01");

        System.out.println("\n------------------------------------------------------------------");
        System.out.println("        BÁO CÁO THỰC THI BỘ 10 TRUY VẤN KHÁCH HÀNG (Q21 - Q30)     ");
        System.out.println("------------------------------------------------------------------");
        QueriesJava.q21_findCustomerById(danhSachKhachHang, "CUST01");
        QueriesJava.q22_findCustomersByPhone(danhSachKhachHang, "0944333444");
        QueriesJava.q23_findCustomersByName(danhSachKhachHang, "An Phát");
        QueriesJava.q24_getAllCustomers(danhSachKhachHang);
        QueriesJava.q25_findCustomersByType(danhSachKhachHang, CustomerType.REGULAR);
        QueriesJava.q26_findCustomersWithPurchasesOver2Million(danhSachKhachHang, allOrders);
        QueriesJava.q27_findCustomersWithMissingContact(danhSachKhachHang);
        QueriesJava.q28_findCustomersByPhonePrefix(danhSachKhachHang, "093");
        QueriesJava.q29_getVipCustomers(danhSachKhachHang);
        QueriesJava.q30_totalWeightByScrapType(items);

        System.out.println("\n================ KẾT THÚC BÁO CÁO THỰC THI 30 TRUY VẤN ================\n");
    }

    // Hàm quy trình xử lý đơn hàng
    private static void xuLyDonHang(PurchaseOrder order, Customer customer, Employee employee, InventoryManager khoKimLoai, InventoryManager khoGiay, InventoryManager khoNhua, InventoryManager khoDienTu, EventManager eventManager, Payment payment) {
        System.out.println("------------------------------------------------------------------");
        System.out.println("--> XỬ LÝ " + order.getOrderId() + " (Khách: " + customer.getName() + " | NV Lập: " + employee.getTenNhanVien() + ")");
        System.out.println(" [Trạng thái ban đầu]: " + order.getStatus());

        order.setStatus(OrderStatus.PROCESSING);
        System.out.println(" [Đang xử lý]: Chuyển đơn sang " + order.getStatus());

        boolean hasOverflow = false;
        for (PurchaseOrderItem orderItem : order.getItems()) {
            ScrapItem pheLieu = orderItem.getItem();
            double weight = orderItem.getWeightKg();

            InventoryManager khoCanTim = layKhoTheoPheLieu(pheLieu, khoKimLoai, khoGiay, khoNhua, khoDienTu);

            if (khoCanTim != null && (khoCanTim.checkStock() + weight) > khoCanTim.getSucChuaToiDa()) {
                System.out.println(" [CẢNH BÁO TRÀN KHO]: " + pheLieu.getName() + " (" + weight + " kg) vượt quá sức chứa " + khoCanTim.getIdItem());
                hasOverflow = true;
                break;
            }
        }

        if (hasOverflow) {
            order.setStatus(OrderStatus.CANCELLED);
            System.out.println(" [KẾT QUẢ]: Đơn " + order.getOrderId() + " chuyển sang " + order.getStatus() + " (Hủy do tràn kho)");
        } else {
            for (PurchaseOrderItem orderItem : order.getItems()) {
                InventoryManager khoCanTim = layKhoTheoPheLieu(orderItem.getItem(), khoKimLoai, khoGiay, khoNhua, khoDienTu);
                if (khoCanTim != null) khoCanTim.addStock(orderItem.getWeightKg(), eventManager);
            }
            order.setStatus(OrderStatus.COMPLETED);
            System.out.println(" [THANH CONG]: Nhập kho hoàn tất! Đơn chuyển sang " + order.getStatus());

            CustomerReward reward = new CustomerReward(customer.getCustomerType() == CustomerType.AGENCY ? 0.05 : 0.01);
            Invoice invoice = new Invoice("INV_" + order.getOrderId(), order, payment, employee, reward);
            invoice.printInvoice();
        }
    }
}