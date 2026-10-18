package queriesJava;

import enums.*;
import model.*;
import payment.*;
import manager.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class QueriesJava {
    private static final DecimalFormat df = new DecimalFormat("#,### VNĐ");

    // =========================================================================
    // BỘ 1: CÁC TRUY VẤN VỀ ĐƠN HÀNG, THANH TOÁN & KHO BÃI (Q1 - Q10)
    // =========================================================================

    // 1. Lấy danh sách tất cả các phiếu thu mua và in báo cáo chi tiết
    public static void q1_getAllOrdersDetailed(List<PurchaseOrder> orders) {
        System.out.println("=== 1. DANH SÁCH TOÀN BỘ PHIẾU THU MUA ===");
        safeList(orders).forEach(o -> {
            System.out.println("Mã đơn: " + o.getOrderId() +
                    " | Khách: " + (o.getCustomer() != null ? o.getCustomer().getName() : "Vãng lai") +
                    " | Số lượng mục: " + o.getItems().size() +
                    " | Tổng TL: " + o.getTotalWeight() + " kg" +
                    " | Trạng thái: " + o.getStatus() +
                    " | Tổng tiền: " + df.format(o.calTotal()));
        });
    }

    // 2. Tìm kiếm chi tiết phiếu thu mua dựa trên mã phiếu
    public static void q2_findOrderByIdDetailed(List<PurchaseOrder> orders, String orderId) {
        System.out.println("\n=== 2. TÌM KIẾM CHI TIẾT ĐƠN HÀNG: " + orderId + " ===");
        safeList(orders).stream()
                .filter(o -> o.getOrderId().equalsIgnoreCase(orderId))
                .findFirst()
                .ifPresentOrElse(
                        o -> {
                            System.out.println("Mã đơn: " + o.getOrderId() + " - Trạng thái: " + o.getStatus());
                            System.out.println("Khách hàng: " + o.getCustomer().getName() + " (" + o.getCustomer().getPhone() + ")");
                            System.out.println("Chi tiết các mặt hàng:");
                            o.getItems().forEach(item -> System.out.println("   + " + item.getItem().getName() +
                                    " | Khối lượng: " + item.getWeightKg() + " kg" +
                                    " | Đơn giá: " + df.format(item.getPricePerKgNow()) + "/kg" +
                                    " | Thành tiền: " + df.format(item.calPrice())));
                            System.out.println("TỔNG GIÁ TRỊ: " + df.format(o.calTotal()));
                        },
                        () -> System.out.println("Không tìm thấy phiếu thu mua có mã: " + orderId)
                );
    }

    // 3. Lọc danh sách các phiếu thu mua đang ở trạng thái chờ xử lý (PENDING)
    public static List<PurchaseOrder> q3_getPendingOrdersDetailed(List<PurchaseOrder> orders) {
        System.out.println("\n=== 3. DANH SÁCH PHIẾU CHỜ XỬ LÝ (PENDING) ===");
        List<PurchaseOrder> pendingList = safeList(orders).stream()
                .filter(Objects::nonNull)
                .filter(o -> o.getStatus() == OrderStatus.PENDING)
                .toList();
        if (pendingList.isEmpty()) {
            System.out.println("Không có đơn hàng nào đang ở trạng thái chờ xử lý (PENDING).");
        } else {
            pendingList.forEach(o -> System.out.println("Mã đơn: " + o.getOrderId() +
                    " | Khách hàng: " + (o.getCustomer() != null ? o.getCustomer().getName() : "Vãng lai") +
                    " | Số lượng mục: " + (o.getItems() != null ? o.getItems().size() : 0) +
                    " | Tổng tiền: " + df.format(o.calTotal()) +
                    " | Ngày lập: " + LocalDate.now()));
        }
        return pendingList;
    }

    // 4. Lấy danh sách phế phẩm kim loại trong các đơn đã hoàn tất (COMPLETED)
    public static void q4_getCompletedMetalItemsDetailed(List<PurchaseOrder> orders) {
        System.out.println("\n=== 4. PHẾ LIỆU KIM LOẠI TRONG CÁC ĐƠN HOÀN TẤT ===");
        safeList(orders).stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .flatMap(o -> o.getItems().stream())
                .filter(item -> item.getItem() instanceof MetalScrap)
                .forEach(item -> {
                    MetalScrap metal = (MetalScrap) item.getItem();
                    System.out.println("Tên phế liệu: " + metal.getName() +
                            " | Khối lượng: " + item.getWeightKg() + " kg" +
                            " | Đơn giá chốt: " + df.format(item.getPricePerKgNow()) +
                            " | Thành tiền: " + df.format(item.calPrice()));
                });
    }

    // 5. Hiển thị danh sách chi tiết các mặt hàng nằm trong một phiếu thu mua cụ thể
    public static void q5_showItemsOfOrder(List<PurchaseOrder> orders, String orderId) {
        System.out.println("\n=== 5. CHI TIẾT MẶT HÀNG TRONG ĐƠN " + orderId + " ===");
        safeList(orders).stream()
                .filter(o -> o.getOrderId().equalsIgnoreCase(orderId))
                .flatMap(o -> o.getItems().stream())
                .forEach(i -> System.out.println("- " + i.getItem().getName() +
                        " | Cân nặng: " + i.getWeightKg() + " kg" +
                        " | Đơn giá: " + df.format(i.getPricePerKgNow()) +
                        " | Thành tiền: " + df.format(i.calPrice())));
    }

    // 6. Tìm phiếu thu mua có tổng tiền lớn nhất
    public static void q6_getHighestValueOrderDetailed(List<PurchaseOrder> orders) {
        System.out.println("\n=== 6. PHIẾU THU MUA CÓ GIÁ TRỊ LỚN NHẤT ===");
        safeList(orders).stream()
                .max(Comparator.comparingDouble(PurchaseOrder::calTotal))
                .ifPresent(o -> {
                    System.out.println("Mã đơn cao nhất: " + o.getOrderId());
                    System.out.println("Khách hàng: " + o.getCustomer().getName());
                    System.out.println("Tổng khối lượng: " + o.getTotalWeight() + " kg");
                    System.out.println("TỔNG TIỀN: " + df.format(o.calTotal()));
                });
    }

    // 7. Lọc các hóa đơn được thanh toán qua Chuyển khoản ngân hàng (BankPayment)
    public static void q7_getBankPaymentInvoicesDetailed(List<Invoice> invoices) {
        System.out.println("\n=== 7. CÁC HÓA ĐƠN THANH TOÁN CHUYỂN KHOẢN ===");
        safeList(invoices).stream()
                .filter(inv -> inv.getPaymentMethod() instanceof BankPayment)
                .forEach(inv -> {
                    System.out.println("Mã hóa đơn: " + inv.getInvoiceId() +
                            " | Mã đơn mua: " + inv.getPurchaseOrder().getOrderId() +
                            " | Thuộc tính thanh toán: " + inv.getPaymentMethod().getPayment());
                });
    }

    // 8. Lấy danh sách kho chứa hơn 70% sức chứa tối đa
    public static void q8_getOver70PercentStockDetailed(List<InventoryManager> inventoryList) {
        System.out.println("\n=== 8. CÁC KHO CHỨA VƯỢT NGUỠNG 70% SỨC CHỨA ===");
        safeList(inventoryList).stream()
                .filter(inv -> (inv.checkStock() / inv.getSucChuaToiDa()) > 0.70)
                .forEach(inv -> {
                    double percentage = (inv.checkStock() / inv.getSucChuaToiDa()) * 100;
                    System.out.printf("Mã kho: %-15s | Hiện tại: %.2f kg / %.2f kg | Tỉ lệ lấp đầy: %.1f%%\n",
                            inv.getIdItem(), inv.checkStock(), inv.getSucChuaToiDa(), percentage);
                });
    }

    // 9. Lọc phiếu thu mua được tạo trong ngày hôm nay nhưng bị hủy (CANCELLED)
    public static void q9_getTodayCancelledOrdersDetailed(List<PurchaseOrder> orders) {
        System.out.println("\n=== 9. CÁC ĐƠN BỊ HỦY TRONG NGÀY HÔM NAY ===");
        LocalDate today = LocalDate.now();
        safeList(orders).stream()
                .filter(o -> o.getStatus() == OrderStatus.CANCELLED)
                .forEach(o -> System.out.println("Đơn hủy: " + o.getOrderId() +
                        " | Khách hàng: " + o.getCustomer().getName() +
                        " | Ngày xử lý: " + today));
    }

    // 10. Tìm các phiếu thu mua có tổng giá trị đơn hàng vượt quá 2.000.000 VNĐ
    public static void q10_getOrdersOver2MillionDetailed(List<PurchaseOrder> orders) {
        System.out.println("\n=== 10. ĐƠN HÀNG CÓ GIÁ TRỊ VƯỢT QUÁ 2.000.000 VNĐ ===");
        safeList(orders).stream()
                .filter(o -> o.calTotal() > 2_000_000)
                .forEach(o -> System.out.println("Mã đơn: " + o.getOrderId() +
                        " | Khách hàng: " + o.getCustomer().getName() +
                        " | Tổng tiền: " + df.format(o.calTotal())));
    }

    // =========================================================================
    // BỘ 2: CÁC TRUY VẤN VỀ MẶT HÀNG PHẾ LIỆU & TỒN KHO (Q11 - Q20)
    // =========================================================================

    // 11. Lấy danh sách tất cả các loại phế liệu cửa hàng đang nhận thu mua
    public static void q11_getAllScrapItems(List<ScrapItem> items) {
        System.out.println("\n=== 11. DANH SÁCH TẤT CẢ LOẠI PHẾ LIỆU ĐANG THU MUA ===");
        safeList(items).forEach(item ->
                System.out.println("Mã ID: " + item.getId() +
                        " | Tên phế liệu: " + item.getName() +
                        " | Đơn giá: " + df.format(item.getPricePerKg()) + "/kg")
        );
    }

    // 12. Lọc danh sách các phế liệu có Giá thu mua bằng 0 VNĐ (Hàng miễn phí / Hàng tặng)
    public static void q12_getZeroPriceItems(List<ScrapItem> items) {
        System.out.println("\n=== 12. DANH SÁCH PHẾ LIỆU CÓ GIÁ THU MUA = 0 VNĐ ===");
        safeList(items).stream()
                .filter(item -> item.getPricePerKg() == 0)
                .forEach(item -> System.out.println("Mã ID: " + item.getId() +
                        " | Tên: " + item.getName() +
                        " | Khối lượng: " + item.getWeight() + " kg"));
    }

    // 13. Tìm các loại phế liệu nhựa có đơn giá thu mua lớn hơn 10.000 VNĐ
    public static void q13_getHighPricePlasticItems(List<ScrapItem> items) {
        System.out.println("\n=== 13. PHẾ LIỆU NHỰA CÓ ĐƠN GIÁ > 10.000 VNĐ ===");
        safeList(items).stream()
                .filter(item -> item instanceof PlasticScrap)
                .filter(item -> item.getPricePerKg() > 10000)
                .forEach(item -> System.out.println("Tên: " + item.getName() +
                        " | Đơn giá: " + df.format(item.getPricePerKg()) + "/kg"));
    }

    // 14. Tính tổng giá trị tiền mặt của toàn bộ phế liệu mới thêm trong kho kim loại
    public static void q14_getTotalValueMetalItems(List<ScrapItem> items) {
        System.out.println("\n=== 14. TỔNG GIÁ TRỊ TIỀN MẶT PHẾ LIỆU KHO KIM LOẠI ===");
        double totalValue = safeList(items).stream()
                .filter(item -> item instanceof MetalScrap)
                .mapToDouble(item -> item.getWeight() * item.getPricePerKg())
                .sum();
        System.out.println("TỔNG GIÁ TRỊ KIM LOẠI TỒN KHO: " + df.format(totalValue));
    }

    // 15. Lấy danh sách các phế liệu điện tử có nguồn gốc trong kho (CLEANORIGIN)
    public static void q15_getCleanOriginEWasteItems(List<ScrapItem> items) {
        System.out.println("\n=== 15. PHẾ LIỆU ĐIỆN TỬ CÓ NGUỒN GỐC RÕ RÀNG (CLEANORIGIN) ===");
        safeList(items).stream()
                .filter(item -> item instanceof EWasteScrap)
                .map(item -> (EWasteScrap) item)
                .filter(item -> item.getType() == EWasteType.CLEANORIGIN)
                .forEach(item -> System.out.println("Mã ID: " + item.getId() +
                        " | Tên: " + item.getName() +
                        " | Phân loại nguồn gốc: " + item.getType()));
    }

    // 16. Tìm kiếm loại phế liệu theo mã định danh trong kho
    public static void q16_findScrapItemById(List<ScrapItem> items, String itemId) {
        System.out.println("\n=== 16. TÌM PHẾ LIỆU THEO MÃ ĐỊNH DANH: " + itemId + " ===");
        safeList(items).stream()
                .filter(item -> item.getId().equalsIgnoreCase(itemId))
                .findFirst()
                .ifPresentOrElse(
                        item -> System.out.println("Mã ID: " + item.getId() +
                                " | Tên phế liệu: " + item.getName() +
                                " | Đơn giá: " + df.format(item.getPricePerKg()) + "/kg" +
                                " | Khối lượng: " + item.getWeight() + " kg"),
                        () -> System.out.println("Không tìm thấy phế liệu có mã: " + itemId)
                );
    }

    // 17. Lấy danh sách các mặt hàng phế liệu nhựa được thu mua có cân nặng trên 3kg
    public static void q17_getPlasticItemsOver3Kg(List<ScrapItem> items) {
        System.out.println("\n=== 17. PHẾ LIỆU NHỰA CÓ CÂN NẶNG > 3KG ===");
        safeList(items).stream()
                .filter(item -> item instanceof PlasticScrap)
                .filter(item -> item.getWeight() > 3.0)
                .forEach(item -> System.out.println("Tên: " + item.getName() +
                        " | Cân nặng: " + item.getWeight() + " kg"));
    }

    // 18. Lọc các phế liệu có đơn giá thu mua vượt mức đơn giá trung bình của toàn bộ cửa hàng
    public static void q18_getItemsAboveAveragePrice(List<ScrapItem> items) {
        System.out.println("\n=== 18. PHẾ LIỆU CÓ ĐƠN GIÁ THU MUA CAO HƠN MỨC TRUNG BÌNH CỬA HÀNG ===");

        // Tính đơn giá trung bình của tất cả mặt hàng phế liệu
        double avgPrice = safeList(items).stream()
                .filter(Objects::nonNull)
                .mapToDouble(ScrapItem::getPricePerKg)
                .average()
                .orElse(0.0);

        System.out.printf("   [Mặt bằng chung] Đơn giá trung bình: %s/kg\n", df.format(avgPrice));
        System.out.println("   [Danh sách vượt chuẩn]:");

        // Lọc phế liệu có đơn giá > avgPrice
        safeList(items).stream()
                .filter(Objects::nonNull)
                .filter(item -> item.getPricePerKg() > avgPrice)
                .forEach(item -> System.out.printf("   + [%s] %-25s | Đơn giá: %s/kg\n",
                        item.getId(), item.getName(), df.format(item.getPricePerKg())));
    }

    // 19. Lọc 3 phế liệu có đơn giá thu mua thấp nhất
    public static void q19_getTop3LowestPriceItems(List<ScrapItem> items) {
        System.out.println("\n=== 19. TOP 3 PHẾ LIỆU CÓ ĐƠN GIÁ THU MUA THẤP NHẤT ===");
        safeList(items).stream()
                .sorted(Comparator.comparingDouble(ScrapItem::getPricePerKg))
                .limit(3)
                .forEach(item -> System.out.println("Tên phế liệu: " + item.getName() +
                        " | Đơn giá: " + df.format(item.getPricePerKg()) + "/kg"));
    }

    // 20. Trích xuất tỷ lệ phần trăm khối lượng của 1 phế liệu theo mã ID so với tổng khối lượng của cùng nhóm loại (Nhựa/Kim loại/Giấy/Điện tử)
    public static void q20_extractScrapItemDetail(List<ScrapItem> items, String itemId) {
        System.out.println("\n=== 20. TỈ LỆ PHẦN TRĂM KHỐI LƯỢNG THEO NHÓM CỦA PHẾ LIỆU: " + itemId + " ===");

        // Bước 1: Tìm phế liệu theo mã ID
        Optional<ScrapItem> targetOpt = safeList(items).stream()
                .filter(Objects::nonNull)
                .filter(item -> item.getId().equalsIgnoreCase(itemId))
                .findFirst();

        if (targetOpt.isEmpty()) {
            System.out.println("Không tìm thấy phế liệu có mã: " + itemId);
            return;
        }

        ScrapItem targetItem = targetOpt.get();

        // Bước 2: Lọc ra tất cả các phế liệu cùng nhóm loại (cùng Class kế thừa)
        List<ScrapItem> sameCategoryItems = safeList(items).stream()
                .filter(Objects::nonNull)
                .filter(item -> item.getClass().equals(targetItem.getClass()))
                .toList();

        // Bước 3: Tính tổng khối lượng của riêng nhóm sản phẩm đó
        double totalCategoryWeight = sameCategoryItems.stream()
                .mapToDouble(ScrapItem::getWeight)
                .sum();

        if (totalCategoryWeight <= 0) {
            System.out.println("Tổng khối lượng của nhóm sản phẩm này bằng 0.");
            return;
        }

        // Bước 4: Tính tỷ lệ % của phế liệu được chọn trên tổng khối lượng nhóm
        double percentage = (targetItem.getWeight() / totalCategoryWeight) * 100;
        String categoryName = targetItem.getClass().getSimpleName(); // MetalScrap, PlasticScrap, PaperScrap, EWasteScrap

        System.out.println("   Mã ID phế liệu      : " + targetItem.getId());
        System.out.println("   Tên mặt hàng        : " + targetItem.getName());
        System.out.println("   Nhóm chủng loại     : " + categoryName);
        System.out.printf("   Khối lượng phế liệu : %.2f kg\n", targetItem.getWeight());
        System.out.printf("   Tổng khối lượng nhóm : %.2f kg\n", totalCategoryWeight);
        System.out.printf("   ***TỶ LỆ ĐÓNG GÓP   : %.2f%% (trên tổng nhóm %s)\n", percentage, categoryName);
    }

    // =========================================================================
    // BỘ 3: CÁC TRUY VẤN VỀ KHÁCH HÀNG & PHÂN PHẠM KHÁCH HÀNG (Q21 - Q30)
    // =========================================================================

    // 21. Tìm khách hàng theo mã khách hàng
    public static void q21_findCustomerById(List<Customer> customers, String customerId) {
        System.out.println("\n=== 21. TÌM KHÁCH HÀNG THEO MÃ: " + customerId + " ===");
        safeList(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> customerId != null && c.getCustomerId().equalsIgnoreCase(customerId.trim()))
                .findFirst()
                .ifPresentOrElse(
                        Customer::displayInfo,
                        () -> System.out.println("Không tìm thấy khách hàng có mã: " + customerId)
                );
    }

    // 22. Tìm khách hàng theo số điện thoại đã đăng ký
    public static void q22_findCustomersByPhone(List<Customer> customers, String phone) {
        System.out.println("\n=== 22. TÌM KHÁCH HÀNG THEO SĐT: " + phone + " ===");
        safeList(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> phone != null && c.getPhone().equals(phone.trim()))
                .forEach(Customer::displayInfo);
    }

    // 23. Tìm khách hàng theo tên, không phân biệt hoa thường và hỗ trợ tên gần đúng
    public static void q23_findCustomersByName(List<Customer> customers, String name) {
        System.out.println("\n=== 23. TÌM KHÁCH HÀNG THEO TÊN: " + name + " ===");
        safeList(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> name != null && c.getName().toLowerCase().contains(name.trim().toLowerCase()))
                .forEach(Customer::displayInfo);
    }

    // 24. Lấy toàn bộ danh sách khách hàng
    public static void q24_getAllCustomers(List<Customer> customers) {
        System.out.println("\n=== 24. DANH SÁCH TOÀN BỘ KHÁCH HÀNG ===");
        safeList(customers).stream()
                .filter(Objects::nonNull)
                .forEach(Customer::displayInfo);
    }

    // 25. Lọc khách hàng theo loại (CustomerType)
    public static void q25_findCustomersByType(List<Customer> customers, CustomerType type) {
        System.out.println("\n=== 25. TÌM KHÁCH HÀNG THEO LOẠI: " + type + " ===");
        safeList(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> type != null && c.getCustomerType() == type)
                .forEach(Customer::displayInfo);
    }

    // 26. Tìm khách hàng có tổng giá trị các giao dịch thu mua đã hoàn tất lớn hơn 2.000.000 VNĐ
    public static void q26_findCustomersWithPurchasesOver2Million(List<Customer> customers, List<PurchaseOrder> orders) {
        System.out.println("\n=== 26. KHÁCH HÀNG CÓ TỔNG GIAO DỊCH > 2.000.000 VNĐ ===");
        Map<String, Double> totalByCustomer = safeList(orders).stream()
                .filter(Objects::nonNull)
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .filter(o -> o.getCustomer() != null)
                .collect(Collectors.groupingBy(
                        o -> o.getCustomer().getCustomerId(),
                        Collectors.summingDouble(PurchaseOrder::calTotal)
                ));

        List<String> customerIds = totalByCustomer.entrySet().stream()
                .filter(entry -> entry.getValue() > 2_000_000)
                .map(Map.Entry::getKey)
                .toList();

        safeList(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> customerIds.contains(c.getCustomerId()))
                .forEach(Customer::displayInfo);
    }

    // 27. Lọc khách hàng thiếu địa chỉ hoặc số điện thoại
    public static void q27_findCustomersWithMissingContact(List<Customer> customers) {
        System.out.println("\n=== 27. KHÁCH HÀNG THIẾU SĐT HOẶC ĐỊA CHỈ ===");
        safeList(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> isBlank(c.getAddress()) || isBlank(c.getPhone()))
                .forEach(Customer::displayInfo);
    }

    // 28. Lọc khách hàng có số điện thoại bắt đầu bằng đầu số cho trước
    public static void q28_findCustomersByPhonePrefix(List<Customer> customers, String prefix) {
        System.out.println("\n=== 28. KHÁCH HÀNG CÓ ĐẦU SỐ ĐIỆN THOẠI: " + prefix + " ===");
        safeList(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> prefix != null && !prefix.isBlank() && c.getPhone().startsWith(prefix.trim()))
                .forEach(Customer::displayInfo);
    }

    // 29. Lấy thông tin các khách hàng VIP
    public static void q29_getVipCustomers(List<Customer> customers) {
        System.out.println("\n=== 29. DANH SÁCH KHÁCH HÀNG VIP ===");
        safeList(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> c.getCustomerType() == CustomerType.VIP)
                .forEach(Customer::displayInfo);
    }

    // 30. Tính tổng khối lượng phế liệu theo từng loại
    public static void q30_totalWeightByScrapType(List<ScrapItem> items) {
        System.out.println("\n=== 30. TỔNG KHỐI LƯỢNG THEO TỪNG LOẠI PHẾ LIỆU ===");
        Map<String, Double> totals = safeList(items).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        item -> item.getType().toString(),
                        Collectors.summingDouble(ScrapItem::getWeight)
                ));

        totals.forEach((type, weight) ->
                System.out.printf("Loại phế liệu: %-15s | Tổng khối lượng: %.2f kg%n", type, weight));
    }

    // Helper kiểm tra danh sách an toàn không lo NullPointerException
    private static <T> List<T> safeList(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
