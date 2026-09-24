PROJECT – HỆ THỐNG QUẢN LÝ CỬA HÀNG PHẾ LIỆU
(Scrap Yard Management System)
Chủ đề
Encapsulation + Composition + Inheritance + Polymorphism + Abstraction + Interface + Generic + Lambda + Stream + Event
Bối cảnh
Xây dựng hệ thống quản lý cửa hàng thu mua – bán phế liệu, cho phép quản lý phế liệu (mặt hàng thu mua), khách hàng bán phế liệu, giỏ thu mua, phiếu thu mua, kho (tồn phế liệu), thanh toán cho khách, thưởng/chiết khấu và các sự kiện phát sinh trong quá trình thu mua.
Hệ thống quản lý
●Customer
●ScrapItem
●MetalScrap
●PlasticScrap
●PaperScrap
●EWasteScrap
●Cart
●CartItem
●PurchaseOrder
●PurchaseOrderItem
●InventoryManager
●Payment
●CashPayment
●BankPayment
●EWalletPayment
●Rewardable
●Manager<T>
●ScrapFilter
●Invoice
●ShopEvent
●EventListener
●EventManager
Phần A – Encapsulation
Lớp ScrapItem
●private id
●private name
●private pricePerKg
●private weightAvailable
Yêu cầu validation:
●pricePerKg > 0
●weightAvailable >= 0
Chức năng:
●getPricePerKg()
●getWeightAvailable()
●displayInfo()
Phần B – ScrapItem và các loại phế liệu
Thiết kế lớp trừu tượng:
ScrapItem
   ▲
   ├── MetalScrap
   ├── PlasticScrap
   ├── PaperScrap
   └── EWasteScrap
ScrapItem là abstract class. Các lớp con kế thừa ScrapItem và override displayInfo().
●MetalScrap: quản lý kim loại (phân biệt kim loại đen/màu, đơn giá theo kg khác nhau).
●PlasticScrap: quản lý nhựa (phân loại theo mã nhựa, độ sạch).
●PaperScrap: quản lý giấy/carton (phân biệt giấy báo, carton, giấy văn phòng).
●EWasteScrap: quản lý thiết bị điện tử cũ (yêu cầu kiểm định nguồn gốc).
Tính chất OOP: Abstraction, Inheritance, Polymorphism, Encapsulation.
Phần C – Customer, Cart và CartItem
Customer
●customerId
●name
●phone
●address
Chức năng: lưu và hiển thị thông tin khách hàng.
Cart
●List<CartItem>
●addItem()
●removeItem()
●updateWeight()
●calculateTotal()
●showCart()
CartItem
●ScrapItem item
●double weightKg
Cart chứa nhiều CartItem. CartItem đại diện cho một loại phế liệu và khối lượng (kg) khách mang đến bán.
Tính chất OOP: Encapsulation + Composition/Aggregation.
Phần D – PurchaseOrder và PurchaseOrderItem
PurchaseOrder
●orderId
●customer
●List<PurchaseOrderItem> items
●OrderStatus status
Chức năng:
●addItem()
●removeItem()
●calculateTotal()
●showOrder()
PurchaseOrderItem
●ScrapItem item
●double weightKg
●double pricePerKg
PurchaseOrder quản lý danh sách PurchaseOrderItem. PurchaseOrderItem lưu đơn giá tại thời điểm cân/thu mua để không bị ảnh hưởng khi giá thị trường phế liệu thay đổi.
Tính chất OOP: Encapsulation + Composition.
Phần E – Payment và Polymorphism
Payment
   ▲
   ├── CashPayment
   ├── BankPayment
   └── EWalletPayment
Có thể thiết kế Payment là abstract class hoặc interface. Với phiên bản project hiện tại, Payment được dùng như interface để thể hiện abstraction và polymorphism (cửa hàng trả tiền cho khách bán phế liệu).
Payment p1 = new CashPayment(...);
Payment p2 = new BankPayment(...);
Payment p3 = new EWalletPayment(...);
p1.pay(...);
p2.pay(...);
p3.pay(...);
Cùng kiểu Payment nhưng hành vi thanh toán thực tế khác nhau. Không cần dùng chuỗi paymentType và if/else dài.
Tính chất OOP: Abstraction + Polymorphism + Interface.
Phần F – InventoryManager
InventoryManager quản lý khối lượng tồn kho phế liệu (tăng khi thu mua, giảm khi xuất bán cho nhà máy tái chế).
●addStock() – tăng tồn khi thu mua từ khách
●removeStock() – giảm tồn khi xuất bán cho nhà máy tái chế
●checkStock()
●updateStock()
Quy tắc: không cho xuất quá số lượng tồn; cảnh báo khi tồn kho một loại phế liệu vượt sức chứa của bãi.
Tính chất: Encapsulation; có thể kết hợp Lambda/Stream để tìm và lọc phế liệu tồn kho.
Phần G – Rewardable và xử lý thưởng/chiết khấu
Rewardable
     |
     └── calculateReward(weight, basePrice)
Rewardable là interface quy định đối tượng có khả năng tính thưởng thêm cho khách khi bán phế liệu.
Ví dụ: thưởng theo phần trăm khối lượng, chương trình thu gom định kỳ hoặc khách đại lý.
Tính chất OOP: Abstraction + Polymorphism.
Phần H – Generic với Manager<T>
Manager<T>
   ├── Manager<ScrapItem>
   ├── Manager<Customer>
   └── Manager<PurchaseOrder>
Manager<T> là generic class dùng chung để quản lý nhiều loại đối tượng.
●add(T item)
●remove(T item)
●getAll()
●find(Predicate<T> condition)
●size()
Manager<ScrapItem> scrapManager = new Manager<>();
Manager<Customer> customerManager = new Manager<>();
Manager<PurchaseOrder> orderManager = new Manager<>();
Tính chất: Generic + Encapsulation. Method find() kết hợp Predicate, Lambda và Stream.
Phần I – Lambda và Stream
Tạo class riêng ScrapFilter để thể hiện rõ việc sử dụng Lambda.
public class ScrapFilter {
    public List<ScrapItem> filter(
            List<ScrapItem> items,
            Predicate<ScrapItem> condition) {
        return items.stream()
                .filter(condition)
                .toList();
    }
}
Ví dụ:
ScrapFilter filter = new ScrapFilter();
filter.filter(items,
    i -> i.getPricePerKg() > 5000);
filter.filter(items,
    i -> i.getWeightAvailable() > 0);
filter.filter(items,
    i -> i.getName().toLowerCase().contains("nhôm"));
Lambda có dạng i -> điều_kiện; Predicate trả về true/false; Stream dùng để xử lý danh sách.
Tính chất/kỹ thuật: Lambda + Functional Interface (Predicate) + Stream.
Phần J – Event
Event dùng để thông báo rằng một hành động trong cửa hàng phế liệu vừa xảy ra.
Các class:
●ShopEvent
●EventListener
●EventManager
ShopEvent
●- eventType
●- message
EventListener
●- onEvent(ShopEvent event)
EventManager
●- subscribe()
●- unsubscribe()
●- publish()
Ví dụ event:
●ORDER_CREATED
●PAYMENT_SUCCESS
●SCRAP_ADDED_TO_CART
●OUT_OF_CAPACITY
EventListener là Functional Interface nên có thể tạo bằng Lambda:
eventManager.subscribe(
    event -> System.out.println(
        "[THÔNG BÁO] " + event.getMessage()
    ));
Khi PurchaseOrder tạo phiếu:
eventManager.publish(
    new ShopEvent(
        "ORDER_CREATED",
        "Phiếu thu mua PL001 đã được tạo!"
    ));
Tính chất/kỹ thuật: Interface + Abstraction + Lambda + Event handling.
Phần K – Invoice
Invoice dùng để tạo và hiển thị phiếu chi trả dựa trên PurchaseOrder.
=============================
         PHIẾU THU MUA PHẾ LIỆU
=============================
Mã phiếu: PL001
Khách: Nguyễn Văn A
Sắt vụn      x 25 kg
Nhôm         x 5 kg
Tổng chi trả: 350.000 VND
Hình thức: Tiền mặt
=============================
Tính chất: Encapsulation; quan hệ sử dụng với PurchaseOrder.
Phần L – Cấu trúc file đề xuất
src/
├── model/
│   ├── ScrapItem.java
│   ├── MetalScrap.java
│   ├── PlasticScrap.java
│   ├── PaperScrap.java
│   ├── EWasteScrap.java
│   ├── Customer.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── PurchaseOrder.java
│   ├── PurchaseOrderItem.java
│   └── Invoice.java
│
├── payment/
│   ├── Payment.java
│   ├── CashPayment.java
│   ├── BankPayment.java
│   └── EWalletPayment.java
│
├── reward/
│   └── Rewardable.java
│
├── manager/
│   ├── Manager.java
│   ├── InventoryManager.java
│   └── ScrapFilter.java
│
├── event/
│   ├── ShopEvent.java
│   ├── EventListener.java
│   └── EventManager.java
│
├── enums/
│   ├── ScrapCategory.java
│   ├── CustomerType.java
│   └── OrderStatus.java
│
└── Main.java
