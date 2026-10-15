package queriesJava;
import enums.*;
import model.*;
import payment.*;
import manager.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

public class QueriesJava {
    private static final DecimalFormat df = new DecimalFormat("#,### VNĐ");

    // 1. Lấy danh sách tất cả các phiếu thu mua và in báo cáo chi tiết
    public static void q1_getAllOrdersDetailed(List<PurchaseOrder> orders) {
        System.out.println("=== 1. DANH SÁCH TOÀN BỘ PHIẾU THU MUA ===");
        orders.stream().forEach(o -> {
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
        orders.stream()
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
        List<PurchaseOrder> pendingList = orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.PENDING)
                .toList();

        pendingList.forEach(o -> System.out.println("Mã đơn: " + o.getOrderId() +
                " | Khách hàng: " + o.getCustomer().getName() +
                " | Ngày lập: " + LocalDate.now()));
        return pendingList;
    }

    // 4. Lấy danh sách phế phẩm kim loại trong các đơn đã hoàn tất (COMPLETED)
    public static void q4_getCompletedMetalItemsDetailed(List<PurchaseOrder> orders) {
        System.out.println("\n=== 4. PHẾ LIỆU KIM LOẠI TRONG CÁC ĐƠN HOÀN TẤT ===");
        orders.stream()
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
        orders.stream()
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
        orders.stream()
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
        invoices.stream()
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
        inventoryList.stream()
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
        orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.CANCELLED)
                .forEach(o -> System.out.println("Đơn hủy: " + o.getOrderId() +
                        " | Khách hàng: " + o.getCustomer().getName() +
                        " | Ngày xử lý: " + today));
    }

    // 10. Tìm các phiếu thu mua có tổng giá trị đơn hàng vượt quá 2.000.000 VNĐ
    public static void q10_getOrdersOver2MillionDetailed(List<PurchaseOrder> orders) {
        System.out.println("\n=== 10. ĐƠN HÀNG CÓ GIÁ TRỊ VƯỢT QUÁ 2.000.000 VNĐ ===");
        orders.stream()
                .filter(o -> o.calTotal() > 2_000_000)
                .forEach(o -> System.out.println("Mã đơn: " + o.getOrderId() +
                        " | Khách hàng: " + o.getCustomer().getName() +
                        " | Tổng tiền: " + df.format(o.calTotal())));
    }

    // CÁC TRUY VẤN BỔ SUNG NÂNG CAO (11 - 20)

    // 11. Lấy danh sách tất cả các loại phế liệu cửa hàng đang nhận thu mua
    public static void q11_getAllScrapItems(List<ScrapItem> items) {
        System.out.println("\n=== 11. DANH SÁCH TẤT CẢ LOẠI PHẾ LIỆU ĐANG THU MUA ===");
        items.stream().forEach(item ->
                System.out.println("Mã ID: " + item.getId() +
                        " | Tên phế liệu: " + item.getName() +
                        " | Đơn giá: " + df.format(item.getPricePerKg()) + "/kg")
        );
    }

    // 12. Lọc danh sách các phế liệu có Giá thu mua bằng 0 VNĐ (Hàng miễn phí / Hàng tặng)
    public static void q12_getZeroPriceItems(List<ScrapItem> items) {
        System.out.println("\n=== 12. DANH SÁCH PHẾ LIỆU CÓ GIÁ THU MUA = 0 VNĐ ===");
        items.stream()
                .filter(item -> item.getPricePerKg() == 0)
                .forEach(item -> System.out.println("Mã ID: " + item.getId() +
                        " | Tên: " + item.getName() +
                        " | Khối lượng: " + item.getWeight() + " kg"));
    }

    // 13. Tìm các loại phế liệu nhựa có đơn giá thu mua lớn hơn 10.000 VNĐ
    public static void q13_getHighPricePlasticItems(List<ScrapItem> items) {
        System.out.println("\n=== 13. PHẾ LIỆU NHỰA CÓ ĐƠN GIÁ > 10.000 VNĐ ===");
        items.stream()
                .filter(item -> item instanceof PlasticScrap)
                .filter(item -> item.getPricePerKg() > 10000)
                .forEach(item -> System.out.println("Tên: " + item.getName() +
                        " | Đơn giá: " + df.format(item.getPricePerKg()) + "/kg"));
    }

    // 14. Tính tổng giá trị tiền mặt của toàn bộ phế liệu mới thêm trong kho kim loại
    public static void q14_getTotalValueMetalItems(List<ScrapItem> items) {
        System.out.println("\n=== 14. TỔNG GIÁ TRỊ TIỀN MẶT PHẾ LIỆU KHO KIM LOẠI ===");
        double totalValue = items.stream()
                .filter(item -> item instanceof MetalScrap)
                .mapToDouble(item -> item.getWeight() * item.getPricePerKg())
                .sum();
        System.out.println("TỔNG GIÁ TRỊ KIM LOẠI TỒN KHO: " + df.format(totalValue));
    }

    // 15. Lấy danh sách các phế liệu điện tử có nguồn gốc trong kho (CLEANORIGIN)
    public static void q15_getCleanOriginEWasteItems(List<ScrapItem> items) {
        System.out.println("\n=== 15. PHẾ LIỆU ĐIỆN TỬ CÓ NGUỒN GỐC RÕ RÀNG (CLEANORIGIN) ===");
        items.stream()
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
        items.stream()
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
        items.stream()
                .filter(item -> item instanceof PlasticScrap)
                .filter(item -> item.getWeight() > 3.0)
                .forEach(item -> System.out.println("Tên: " + item.getName() +
                        " | Cân nặng: " + item.getWeight() + " kg"));
    }

    // 18. Lọc các loại phế liệu đang có khối lượng tồn kho đạt sức chứa của bãi
    public static void q18_getFullStockInventories(List<InventoryManager> inventoryList) {
        System.out.println("\n=== 18. CÁC KHO ĐẠT/VƯỢT SỨC CHỨA TỐI ĐA BÃI ===");
        inventoryList.stream()
                .filter(inv -> inv.checkStock() >= inv.getSucChuaToiDa())
                .forEach(inv -> System.out.printf("CẢNH BÁO TRÀN: Kho %-15s | Hiện tại: %.2f kg / Max: %.2f kg\n",
                        inv.getIdItem(), inv.checkStock(), inv.getSucChuaToiDa()));
    }

    // 19. Lọc 3 phế liệu có đơn giá thu mua thấp nhất
    public static void q19_getTop3LowestPriceItems(List<ScrapItem> items) {
        System.out.println("\n=== 19. TOP 3 PHẾ LIỆU CÓ ĐƠN GIÁ THU MUA THẤP NHẤT ===");
        items.stream()
                .sorted(Comparator.comparingDouble(ScrapItem::getPricePerKg))
                .limit(3)
                .forEach(item -> System.out.println("Tên phế liệu: " + item.getName() +
                        " | Đơn giá: " + df.format(item.getPricePerKg()) + "/kg"));
    }

    // 20. Trích xuất thông tin chi tiết của một loại phế liệu để xem đơn giá và khối lượng tồn kho hiện tại
    public static void q20_extractScrapItemDetail(List<ScrapItem> items, String itemId) {
        System.out.println("\n=== 20. TRÍCH XUẤT THÔNG TIN CHI TIẾT PHẾ LIỆU: " + itemId + " ===");
        items.stream()
                .filter(item -> item.getId().equalsIgnoreCase(itemId))
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            System.out.println("Mã định danh      : " + item.getId());
                            System.out.println("Tên mặt hàng      : " + item.getName());
                            System.out.println("Đơn giá thu mua   : " + df.format(item.getPricePerKg()) + "/kg");
                            System.out.println("Tồn kho hiện tại  : " + item.getWeight() + " kg");
                        },
                        () -> System.out.println("Không tìm thấy thông tin phế liệu mã: " + itemId)
                );
    }
}