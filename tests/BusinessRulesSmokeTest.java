import enums.CustomerType;
import enums.MetalType;
import enums.OrderStatus;
import event.EventManager;
import event.EventListener;
import event.ShopEvent;
import manager.InventoryManager;
import model.Cart;
import model.Employee;
import model.Customer;
import model.Invoice;
import model.MetalScrap;
import model.PurchaseOrder;
import model.ScrapItem;
import payment.CashPayment;
import payment.Payment;
import reward.CustomerReward;
import reward.SupplierBenefitPolicy;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Kiểm thử khói không phụ thuộc JUnit.
 *
 * Chạy từ thư mục gốc:
 *   javac -d out $(find src tests -name "*.java")
 *   java -cp out BusinessRulesSmokeTest
 */
public final class BusinessRulesSmokeTest {
    private BusinessRulesSmokeTest() {}

    public static void main(String[] args) {
        testCartAndOrderSnapshot();
        testPaymentLifecycle();
        testCustomerAndRewardValidation();
        testEmployeePrintableDetails();
        testSupplierBenefitPriorityAndSnapshot();
        testInventoryDoesNotGoNegative();
        testEventSubscription();
        System.out.println("PASS: tất cả kiểm thử nghiệp vụ đều thành công.");
    }

    private static void testCartAndOrderSnapshot() {
        ScrapItem scrap = new MetalScrap("T-01", "Sắt thử nghiệm",
                10_000, 0.0, MetalType.KIM_LOAI_DEN);
        Cart cart = new Cart();
        cart.addItem(scrap, 2.5);
        assertEquals(25_000.0, cart.calTotal(), "Tổng tiền giỏ hàng");

        // Thay đổi dữ liệu danh mục không được sửa ngược số tiền đã chốt trong giỏ/phiếu.
        scrap.setPricePerKg(30_000);
        scrap.setWeight(99.0);
        assertEquals(25_000.0, cart.calTotal(), "Giỏ hàng phải giữ giá/khối lượng đã chụp");

        PurchaseOrder order = new PurchaseOrder("PO-TEST", "Khách thử", cart);
        scrap.setPricePerKg(50_000);
        scrap.setWeight(120.0);
        assertEquals(25_000.0, order.calTotal(), "Phiếu thu mua phải giữ giá/khối lượng đã chốt");
    }

    private static void testPaymentLifecycle() {
        Cart cart = new Cart();
        cart.addItem(new MetalScrap("T-02", "Nhôm thử nghiệm",
                20_000, 0.0, MetalType.KIM_LOAI_MAU), 2.0);
        PurchaseOrder order = new PurchaseOrder("PO-PAY", "Khách thử", cart);
        Payment payment = new CashPayment("Khách thử", "TX-TEST");
        Invoice invoice = new Invoice("INV-TEST", order, payment);

        invoice.processPayment();
        assertEquals(OrderStatus.COMPLETED, order.getStatus(), "Trạng thái sau thanh toán");
        expectThrows(IllegalStateException.class, invoice::processPayment,
                "Không cho phép thanh toán phiếu hai lần");
        expectThrows(IllegalArgumentException.class, () -> payment.pay(-1),
                "Không chấp nhận số tiền âm");
    }

    private static void testCustomerAndRewardValidation() {
        Customer customer = new Customer("C-TEST", "Khách thử", "", "", CustomerType.VIP);
        customer.addRewardPoints(10);
        assertEquals(10, customer.getRewardPoints(), "Điểm thưởng");
        assertEquals(CustomerType.VIP, customer.getCustomerType(), "Loại khách hàng");
        expectThrows(IllegalArgumentException.class, () -> customer.addRewardPoints(-1),
                "Không chấp nhận điểm thưởng âm");
        expectThrows(IllegalArgumentException.class, () -> new CustomerReward(1.5),
                "Tỷ lệ thưởng không được vượt quá 100%");
    }

    private static void testEmployeePrintableDetails() {
        Employee employee = new Employee(
                "id111", "nguyen hoang luan", "0325728429",
                java.util.List.of("xom 1", "van tuong"));

        assertEquals("id111", employee.getIdNhanVien(), "Mã nhân viên");
        String details = employee.getThongTin();
        if (!details.equals("Id: id111 Ten nhan vien: nguyen hoang luan "
                + "So dien thoai: 0325728429 Dia chi: xom 1; van tuong;")) {
            throw new AssertionError("Định dạng thông tin nhân viên không đúng: " + details);
        }
    }

    private static void testSupplierBenefitPriorityAndSnapshot() {
        Customer vip = new Customer("C-VIP", "Nhà cung cấp VIP", "", "", CustomerType.VIP);
        ScrapItem scrap = new MetalScrap("T-BENEFIT", "Sắt", 10_000, 0.0,
                MetalType.KIM_LOAI_DEN);
        Cart cart = new Cart();
        cart.addItem(scrap, 2.0);

        SupplierBenefitPolicy policy = new SupplierBenefitPolicy();
        policy.setTypePercentagePolicy(CustomerType.VIP, "Ưu đãi nhóm VIP", 0.03);
        policy.setTypePerKilogramPolicy(CustomerType.AGENCY, "Thưởng đại lý theo kg", 500.0);
        policy.setCustomerPercentagePolicy("C-VIP", "Ưu đãi riêng C-VIP", 0.05);

        PurchaseOrder order = PurchaseOrder.forCustomer("PO-BENEFIT", vip, cart);
        order.applySupplierBenefit(policy);
        assertEquals(20_000.0, order.calTotal(), "Tiền phế liệu trước ưu đãi");
        assertEquals(1_000.0, order.getSupplierBenefitAmount(),
                "Ưu đãi riêng 5% phải ưu tiên hơn ưu đãi nhóm VIP 3%");
        assertEquals(21_000.0, order.getPayableTotal(), "Tổng tiền sau ưu đãi");
        assertEquals("Ưu đãi riêng C-VIP", order.getSupplierBenefitName(),
                "Tên chính sách riêng được ghi nhận");
        if (!order.getSupplierBenefitSource().contains("C-VIP")) {
            throw new AssertionError("Nguồn ưu đãi phải xác nhận chính sách theo mã khách hàng.");
        }

        // Đổi cấu hình sau khi áp dụng không được thay đổi ưu đãi đã chốt trên phiếu.
        policy.setCustomerPercentagePolicy("C-VIP", "Ưu đãi mới", 0.10);
        assertEquals(1_000.0, order.getSupplierBenefitAmount(),
                "Ưu đãi của phiếu phải được chụp tại thời điểm áp dụng");

        // Khi sửa dòng phiếu, kết quả ưu đãi phải bị xóa để không giữ số tiền cũ.
        order.addItem(new MetalScrap("T-BENEFIT-2", "Nhôm", 20_000, 0.0,
                MetalType.KIM_LOAI_MAU), 2_000.0, 1.0);
        assertEquals(0.0, order.getSupplierBenefitAmount(),
                "Thay đổi phiếu phải xóa kết quả ưu đãi cũ");
        assertEquals(false, order.isSupplierBenefitApplied(),
                "Sau khi sửa phiếu, ưu đãi phải được tính lại");
        order.applySupplierBenefit(policy);
        assertEquals(2_200.0, order.getSupplierBenefitAmount(),
                "Ưu đãi riêng 10% phải được tính lại trên tổng mới");

        Customer agency = new Customer("C-AGENCY", "Đại lý", "", "", CustomerType.AGENCY);
        SupplierBenefitPolicy.BenefitResult agencyResult =
                policy.evaluate(agency, 3.0, 30_000.0);
        assertEquals(1_500.0, agencyResult.getBonusAmount(),
                "Ưu đãi đại lý phải tính theo khối lượng");
    }

    private static void testInventoryDoesNotGoNegative() {
        InventoryManager inventory = new InventoryManager("T-03", 5.0, 10.0);
        expectThrows(IllegalArgumentException.class, () -> inventory.removeStock(6.0),
                "Không cho xuất vượt tồn kho");
        assertEquals(5.0, inventory.checkStock(), "Tồn kho không đổi khi xuất thất bại");
        inventory.addStock(2.0);
        assertEquals(7.0, inventory.checkStock(), "Tồn kho sau khi nhập");
    }

    private static void testEventSubscription() {
        EventManager manager = new EventManager();
        AtomicInteger received = new AtomicInteger();
        EventListener listener = event -> received.incrementAndGet();

        manager.subscribe(listener);
        manager.publish(new ShopEvent("TEST_EVENT", "Thông báo thử"));
        assertEquals(1, received.get(), "Listener phải nhận sự kiện");
        manager.unsubscribe(listener);
        manager.publish(new ShopEvent("TEST_EVENT", "Thông báo sau hủy đăng ký"));
        assertEquals(1, received.get(), "Listener đã hủy đăng ký không nhận sự kiện");
    }

    private static void assertEquals(double expected, double actual, String label) {
        if (Math.abs(expected - actual) > 0.000001) {
            throw new AssertionError(label + ": mong đợi " + expected + ", thực tế " + actual);
        }
    }

    private static void assertEquals(Object expected, Object actual, String label) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(label + ": mong đợi " + expected + ", thực tế " + actual);
        }
    }

    private static void expectThrows(Class<? extends Throwable> expectedType,
                                     Runnable action, String label) {
        try {
            action.run();
        } catch (Throwable actual) {
            if (expectedType.isInstance(actual)) {
                return;
            }
            throw new AssertionError(label + ": loại lỗi không đúng, nhận được "
                    + actual.getClass().getSimpleName(), actual);
        }
        throw new AssertionError(label + ": lẽ ra phải phát sinh "
                + expectedType.getSimpleName());
    }
}
