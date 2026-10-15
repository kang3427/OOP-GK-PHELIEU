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

        // 1. KHỞI TẠO 4 KHO (CÓ ĐỦ SỨC CHỨA ĐỂ TEST CẢ ĐƠN THÀNH CÔNG LẪN TRÀN KHO)

        InventoryManager khoKimLoai = new InventoryManager("KHO_KIM_LOAI", 100.0, 5000.0); // Trống 4900kg
        InventoryManager khoGiay    = new InventoryManager("KHO_GIAY",     30.0,  100.0);  // Trống 70kg
        InventoryManager khoNhua    = new InventoryManager("KHO_NHUA",     10.0,  30.0);   // Trống 20kg (Nhỏ để test tràn kho)
        InventoryManager khoDienTu  = new InventoryManager("KHO_DIEN_TU",  20.0,  1000.0); // Trống 980kg

        System.out.println("1. TỒN KHO BAN ĐẦU:");
        System.out.println("   + Kim loại: " + khoKimLoai.checkStock() + " / " + khoKimLoai.getSucChuaToiDa() + " kg (Trống " + (khoKimLoai.getSucChuaToiDa() - khoKimLoai.checkStock()) + "kg)");
        System.out.println("   + Giấy    : " + khoGiay.checkStock()    + " / " + khoGiay.getSucChuaToiDa()    + " kg (Trống " + (khoGiay.getSucChuaToiDa() - khoGiay.checkStock()) + "kg)");
        System.out.println("   + Nhựa    : " + khoNhua.checkStock()    + " / " + khoNhua.getSucChuaToiDa()    + " kg (Trống " + (khoNhua.getSucChuaToiDa() - khoNhua.checkStock()) + "kg)");
        System.out.println("   + Điện tử : " + khoDienTu.checkStock()  + " / " + khoDienTu.getSucChuaToiDa()  + " kg (Trống " + (khoDienTu.getSucChuaToiDa() - khoDienTu.checkStock()) + "kg)\n");

        // 2. KHỞI TẠO DỮ LIỆU PHẾ LIỆU (DÙNG TRY-CATCH XỬ LÝ DỮ LIỆU KHÔNG HỢP LỆ)

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
        try { themPheLieuAnToan(items, new PaperScrap("P03", "Bìa carton ẩm ướt", 0, 12.0, PaperType.CARTON)); } catch (Exception e) { System.out.println("[BO QUA P03]: " + e.getMessage()); } // Thành công!
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

        // 3. KHỞI TẠO LIST NHÂN VIÊN & LIST KHÁCH HÀNG

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

        // 4. XỬ LÝ 4 ĐƠN HÀNG THU MUA (DÙNG THEO TRỰC TIẾP LẤY ITEMS.GET(INDEX))

        EventManager eventManager = new EventManager();

        // ĐƠN 1 (ORD01): M01 (15.5kg), M03 (5kg), P01 (20kg), P03 (12kg giá 0đ) -> THÀNH CÔNG (COMPLETED)
        Cart cart1 = new Cart("CART01");
        cart1.addItem(items.get(0)); // M01: Sắt 15.5kg
        cart1.addItem(items.get(1)); // M03: Vỏ lon 5.0kg
        cart1.addItem(items.get(4)); // P01: Giấy báo 20.0kg
        cart1.addItem(items.get(6)); // P03: Bìa carton 12.0kg (Giá 0 đồng - Khởi tạo thành công)
        PurchaseOrder order1 = new PurchaseOrder("ORD01", danhSachKhachHang.get(0), cart1);
        xuLyDonHang(order1, danhSachKhachHang.get(0), danhSachNhanVien.get(0), khoKimLoai, khoGiay, khoNhua, khoDienTu, eventManager, new CashPayment("PAY01", danhSachKhachHang.get(0).getName()));

        // ĐƠN 2 (ORD02): M05 (8.2kg), M06 (12kg), P05 (8.5kg), E01 (2.5kg) -> THÀNH CÔNG (COMPLETED)
        Cart cart2 = new Cart("CART02");
        cart2.addItem(items.get(3));  // M05: Đồng 8.2kg
        cart2.addItem(items.get(4));  // M06: Inox 12.0kg
        cart2.addItem(items.get(7));  // P05: Tạp chí 8.5kg
        cart2.addItem(items.get(14)); // E01: Bo mạch 2.5kg
        PurchaseOrder order2 = new PurchaseOrder("ORD02", danhSachKhachHang.get(1), cart2);
        xuLyDonHang(order2, danhSachKhachHang.get(1), danhSachNhanVien.get(1), khoKimLoai, khoGiay, khoNhua, khoDienTu, eventManager, new BankPayment("PAY02", "Techcombank", "190312345678", danhSachKhachHang.get(1).getName()));

        // ĐƠN 3 (ORD03): PL02 (Can nhựa 65kg > sức chứa 20kg kho Nhựa) -> TRÀN KHO -> CANCELLED
        Cart cart3 = new Cart("CART03");
        cart3.addItem(items.get(10)); // PL02: Can nhựa HDPE 65.0kg (Trống 20kg -> Bị tràn kho)
        cart3.addItem(items.get(17)); // E06: Pin laptop 3.0kg
        PurchaseOrder order3 = new PurchaseOrder("ORD03", danhSachKhachHang.get(2), cart3);
        xuLyDonHang(order3, danhSachKhachHang.get(2), danhSachNhanVien.get(2), khoKimLoai, khoGiay, khoNhua, khoDienTu, eventManager, new CashPayment("PAY03", danhSachKhachHang.get(2).getName()));

        // ĐƠN 4 (ORD04): PL04 (4kg), PL05 (9kg), E04 (4kg), E02 (1.2kg) -> THÀNH CÔNG (COMPLETED)
        Cart cart4 = new Cart("CART04");
        cart4.addItem(items.get(11)); // PL04: Hộp PP 4.0kg
        cart4.addItem(items.get(12)); // PL05: Chai HDPE 9.0kg
        cart4.addItem(items.get(15)); // E04: Ổ cứng 4.0kg
        cart4.addItem(items.get(16)); // E02: Linh kiện 1.2kg
        PurchaseOrder order4 = new PurchaseOrder("ORD04", danhSachKhachHang.get(0), cart4);
        xuLyDonHang(order4, danhSachKhachHang.get(0), danhSachNhanVien.get(0), khoKimLoai, khoGiay, khoNhua, khoDienTu, eventManager, new CashPayment("PAY04", danhSachKhachHang.get(0).getName()));


        // 5. BÁO CÁO TỒN KHO CUỐI CÙNG

        System.out.println("\n========== TỒN KHO CUỐI CÙNG SAU KHI XỬ LÝ 4 ĐƠN HÀNG ==========");
        System.out.println("   + KHO KIM LOẠI: " + khoKimLoai.checkStock() + " / " + khoKimLoai.getSucChuaToiDa() + " kg");
        System.out.println("   + KHO GIẤY    : " + khoGiay.checkStock()    + " / " + khoGiay.getSucChuaToiDa()    + " kg");
        System.out.println("   + KHO NHỰA    : " + khoNhua.checkStock()    + " / " + khoNhua.getSucChuaToiDa()    + " kg");
        System.out.println("   + KHO ĐIỆN TỬ : " + khoDienTu.checkStock()  + " / " + khoDienTu.getSucChuaToiDa()  + " kg");


        // Thoáng: Phần chạy kiểm thử 10 truy vấn chính theo dữ liệu mẫu ở Main.
        // ==================== CHẠY 10 CÂU TRUY VẤN CHI TIẾT ====================
        // Đơn kiểm thử riêng cho truy vấn 26; không chạy qua quy trình nhập kho thực tế.
        Cart cartTestGiaTriCao = new Cart("CART_TEST_26");
        cartTestGiaTriCao.addItem(items.get(3), 300000.0);
        PurchaseOrder orderTestGiaTriCao = new PurchaseOrder("ORD_TEST_26", danhSachKhachHang.get(3), cartTestGiaTriCao);
        orderTestGiaTriCao.setStatus(OrderStatus.COMPLETED);

        List<PurchaseOrder> allOrders = List.of(order1, order2, order3, order4, orderTestGiaTriCao);
        List<InventoryManager> allWarehouses = List.of(khoKimLoai, khoGiay, khoNhua, khoDienTu);

        // Tạo danh sách hóa đơn mẫu để test Query 7
        List<Invoice> allInvoices = new ArrayList<>();
        CustomerReward reward = new CustomerReward(danhSachKhachHang.get(1));
        allInvoices.add(new Invoice("INV_ORD02", order2,
                new BankPayment("PAY02", "Techcombank", "190312345678", danhSachKhachHang.get(1).getName()),
                danhSachNhanVien.get(1), reward));

        System.out.println("\n------------------------------------------------------------------");
        System.out.println("              BÁO CÁO THỰC THI 10 CÂU TRUY VẤN STREAM             ");
        System.out.println("------------------------------------------------------------------");

        System.out.println("\n-------------------- TRUY VẤN 1 --------------------");
        QueriesJava.q1_getAllOrdersDetailed(allOrders);

        System.out.println("\n-------------------- TRUY VẤN 2 --------------------");
        QueriesJava.q2_findOrderByIdDetailed(allOrders, "ORD01");

        System.out.println("\n-------------------- TRUY VẤN 3 --------------------");
        QueriesJava.q3_getPendingOrdersDetailed(allOrders);

        System.out.println("\n-------------------- TRUY VẤN 4 --------------------");
        QueriesJava.q4_getCompletedMetalItemsDetailed(allOrders);

        System.out.println("\n-------------------- TRUY VẤN 5 --------------------");
        QueriesJava.q5_showItemsOfOrder(allOrders, "ORD02");

        System.out.println("\n-------------------- TRUY VẤN 6 --------------------");
        QueriesJava.q6_getHighestValueOrderDetailed(allOrders);

        System.out.println("\n-------------------- TRUY VẤN 7 --------------------");
        QueriesJava.q7_getBankPaymentInvoicesDetailed(allInvoices);

        System.out.println("\n-------------------- TRUY VẤN 8 --------------------");
        QueriesJava.q8_getOver70PercentStockDetailed(allWarehouses);

        System.out.println("\n-------------------- TRUY VẤN 9 --------------------");
        QueriesJava.q9_getCancelledOrdersDetailed(allOrders);

        System.out.println("\n-------------------- TRUY VẤN 10 --------------------");
        QueriesJava.q10_getOrdersOver2MillionDetailed(allOrders);

        // ==================== BÁO CÁO THỰC THI 10 CÂU TRUY VẤN MỚI (Q11 - Q20) ====================
        System.out.println("\n------------------------------------------------------------------");
        System.out.println("        BÁO CÁO THỰC THI BỘ 10 TRUY VẤN MỚI BỔ SUNG (Q11 - Q20)   ");
        System.out.println("------------------------------------------------------------------");

        System.out.println("\n-------------------- TRUY VẤN 11 --------------------");
        QueriesJava.q11_getAllScrapItems(items);

        System.out.println("\n-------------------- TRUY VẤN 12 --------------------");
        QueriesJava.q12_getZeroPriceItems(items);

        System.out.println("\n-------------------- TRUY VẤN 13 --------------------");
        QueriesJava.q13_getHighPricePlasticItems(items);

        System.out.println("\n-------------------- TRUY VẤN 14 --------------------");
        QueriesJava.q14_getTotalValueMetalItems(items);

        System.out.println("\n-------------------- TRUY VẤN 15 --------------------");
        QueriesJava.q15_getCleanOriginEWasteItems(items);

        System.out.println("\n-------------------- TRUY VẤN 16 --------------------");
        QueriesJava.q16_findScrapItemById(items, "M01");

        System.out.println("\n-------------------- TRUY VẤN 17 --------------------");
        QueriesJava.q17_getPlasticItemsOver3Kg(items);

        System.out.println("\n-------------------- TRUY VẤN 18 --------------------");
        QueriesJava.q18_getFullStockInventories(allWarehouses);

        System.out.println("\n-------------------- TRUY VẤN 19 --------------------");
        QueriesJava.q19_getTop3LowestPriceItems(items);

        System.out.println("\n-------------------- TRUY VẤN 20 --------------------");
        QueriesJava.q20_extractScrapItemDetail(items, "P01");

        // Thoáng: Bổ sung kiểm thử đúng 10 truy vấn khách hàng, giữ nguyên toàn bộ phần Main hiện có.
        System.out.println("\n------------------------------------------------------------------");
        System.out.println("              KIỂM THỬ TRUY VẤN 21 - 30                    ");
        System.out.println("------------------------------------------------------------------");

        System.out.println("\n[TRUY VẤN 21] Tìm khách hàng theo mã");
        CustomerQueries.q1_findCustomerById(danhSachKhachHang, "CUST01");

        System.out.println("\n------------------------------------------------------------");
        System.out.println("[TRUY VẤN 22] Tìm khách hàng theo số điện thoại");
        CustomerQueries.q2_findCustomersByPhone(danhSachKhachHang, "0944333444");

        System.out.println("\n------------------------------------------------------------");
        System.out.println("[TRUY VẤN 23] Tìm khách hàng theo tên");
        CustomerQueries.q3_findCustomersByName(danhSachKhachHang, "An Phát");

        System.out.println("\n------------------------------------------------------------");
        System.out.println("[TRUY VẤN 24] Hiển thị toàn bộ khách hàng");
        CustomerQueries.q4_getAllCustomers(danhSachKhachHang);

        System.out.println("\n------------------------------------------------------------");
        System.out.println("[TRUY VẤN 25] Lọc khách hàng theo loại");
        CustomerQueries.q5_findCustomersByType(danhSachKhachHang, CustomerType.REGULAR);

        System.out.println("\n------------------------------------------------------------");
        System.out.println("[TRUY VẤN 26] Tìm khách hàng có tổng giá trị thu mua trên 2.000.000 VNĐ");
        CustomerQueries.q6_findCustomersWithPurchasesOver2Million(danhSachKhachHang, allOrders);

        System.out.println("\n------------------------------------------------------------");
        System.out.println("[TRUY VẤN 27] Tìm khách hàng thiếu địa chỉ hoặc số điện thoại");
        CustomerQueries.q7_findCustomersWithMissingContact(danhSachKhachHang);

        System.out.println("\n------------------------------------------------------------");
        System.out.println("[TRUY VẤN 28] Tìm khách hàng theo đầu số điện thoại");
        CustomerQueries.q8_findCustomersByPhonePrefix(danhSachKhachHang, "093");

        System.out.println("\n------------------------------------------------------------");
        System.out.println("[TRUY VẤN 29] Hiển thị khách hàng VIP");
        CustomerQueries.q9_getVipCustomers(danhSachKhachHang);

        System.out.println("\n------------------------------------------------------------");
        System.out.println("[TRUY VẤN 30] Tính tổng khối lượng theo từng loại phế liệu");
        CustomerQueries.q10_totalWeightByScrapType(items);

        System.out.println("\n================ KẾT THÚC TRUY VẤN 21 - 30 ================\n");

    }

    // Hàm quy trình xử lý đơn hàng (Sử dụng đúng getOrderId() và getTenNhanVien() theo code gốc)
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