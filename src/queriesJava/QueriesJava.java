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
}