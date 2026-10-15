package queriesJava;

import enums.CustomerType;
import enums.OrderStatus;
import model.Customer;
import model.PlasticScrap;
import model.PurchaseOrder;
import model.PurchaseOrderItem;
import model.ScrapItem;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Thoáng: 10 truy vấn khách hàng sử dụng Stream API và Lambda.
 */
public class CustomerQueries {

    // 1. Tìm khách hàng theo mã khách hàng.
    public static void q1_findCustomerById(List<Customer> customers, String customerId) {
        safeCustomers(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> customerId != null && c.getCustomerId().equalsIgnoreCase(customerId.trim()))
                .findFirst()
                .ifPresentOrElse(
                        Customer::displayInfo,
                        () -> System.out.println("Không tìm thấy khách hàng có mã: " + customerId)
                );
    }

    // 2. Tìm khách hàng theo số điện thoại đã đăng ký.
    public static void q2_findCustomersByPhone(List<Customer> customers, String phone) {
        safeCustomers(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> phone != null && c.getPhone().equals(phone.trim()))
                .forEach(Customer::displayInfo);
    }

    // 3. Tìm khách hàng theo tên, không phân biệt hoa thường và hỗ trợ tên gần đúng.
    public static void q3_findCustomersByName(List<Customer> customers, String name) {
        safeCustomers(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> name != null && c.getName().toLowerCase().contains(name.trim().toLowerCase()))
                .forEach(Customer::displayInfo);
    }

    // 4. Lấy toàn bộ khách hàng.
    public static void q4_getAllCustomers(List<Customer> customers) {
        safeCustomers(customers).stream()
                .filter(Objects::nonNull)
                .forEach(Customer::displayInfo);
    }

    // 5. Lọc khách hàng theo loại.
    public static void q5_findCustomersByType(List<Customer> customers, CustomerType type) {
        safeCustomers(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> type != null && c.getCustomerType() == type)
                .forEach(Customer::displayInfo);
    }

    // 6. Tìm khách hàng có bán ít nhất một mặt hàng nhựa nặng trên 3 kg trong các đơn thu mua.
    public static void q6_findCustomersSellingPlasticOver3Kg(List<Customer> customers, List<PurchaseOrder> orders) {
        List<String> customerIds = orders == null ? Collections.emptyList() : orders.stream()
                .filter(Objects::nonNull)
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .filter(o -> o.getCustomer() != null)
                .filter(o -> o.getItems() != null && o.getItems().stream()
                        .filter(Objects::nonNull)
                        .anyMatch(i -> i.getItem() instanceof PlasticScrap && i.getWeightKg() > 3.0))
                .map(o -> o.getCustomer().getCustomerId())
                .distinct()
                .toList();

        safeCustomers(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> customerIds.contains(c.getCustomerId()))
                .forEach(Customer::displayInfo);
    }

    // 7. Lọc khách hàng thiếu địa chỉ hoặc số điện thoại.
    // Constructor Customer hiện tại không cho phép lưu các trường này rỗng,
    // nhưng truy vấn vẫn hữu ích khi dữ liệu được nạp từ nguồn khác.
    public static void q7_findCustomersWithMissingContact(List<Customer> customers) {
        safeCustomers(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> isBlank(c.getAddress()) || isBlank(c.getPhone()))
                .forEach(Customer::displayInfo);
    }

    // 8. Lọc khách hàng có số điện thoại bắt đầu bằng đầu số cho trước.
    public static void q8_findCustomersByPhonePrefix(List<Customer> customers, String prefix) {
        safeCustomers(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> prefix != null && !prefix.isBlank() && c.getPhone().startsWith(prefix.trim()))
                .forEach(Customer::displayInfo);
    }

    // 9. Lấy thông tin khách hàng VIP.
    public static void q9_getVipCustomers(List<Customer> customers) {
        safeCustomers(customers).stream()
                .filter(Objects::nonNull)
                .filter(c -> c.getCustomerType() == CustomerType.VIP)
                .forEach(Customer::displayInfo);
    }

    // 10. Tính tổng khối lượng theo từng loại phế liệu.
    public static void q10_totalWeightByScrapType(List<ScrapItem> items) {
        Map<String, Double> totals = (items == null ? Collections.<ScrapItem>emptyList() : items).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        item -> item.getType().toString(),
                        Collectors.summingDouble(ScrapItem::getWeight)
                ));

        totals.forEach((type, weight) ->
                System.out.printf("Loại phế liệu: %s | Tổng khối lượng: %.2f kg%n", type, weight));
    }

    private static List<Customer> safeCustomers(List<Customer> customers) {
        return customers == null ? Collections.emptyList() : customers;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
