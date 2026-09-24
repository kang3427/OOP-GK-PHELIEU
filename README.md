# PROJECT – HỆ THỐNG QUẢN LÝ CỬA HÀNG PHẾ LIỆU
(Scrap Yard Management System)

## Chủ đề
Encapsulation + Composition + Inheritance + Polymorphism + Abstraction + Interface + Generic + Lambda + Stream + Event

## Bối cảnh
Xây dựng hệ thống quản lý cửa hàng thu mua – bán phế liệu, cho phép quản lý phế liệu (mặt hàng thu mua), khách hàng bán phế liệu, giỏ thu mua, phiếu thu mua, ...

## Hệ thống quản lý
- Customer
- ScrapItem
- MetalScrap
- PlasticScrap
- PaperScrap
- EWasteScrap
- Cart
- CartItem
- PurchaseOrder
- PurchaseOrderItem
- InventoryManager
- Payment
- CashPayment
- BankPayment
- EWalletPayment
- Rewardable
- Manager<T>
- ScrapFilter
- Invoice
- ShopEvent
- EventListener
- EventManager

## Phần A – Encapsulation

### Lớp ScrapItem
- private id
- private name
- private pricePerKg
- private weightAvailable

### Yêu cầu validation
- pricePerKg > 0
- weightAvailable >= 0

### Chức năng
- getPricePerKg()
- getWeightAvailable()
- displayInfo()

## Phần B – ScrapItem và các loại phế liệu

### Thiết kế lớp trừu tượng
ScrapItem là abstract class. Các lớp con kế thừa ScrapItem và override displayInfo().

- MetalScrap: quản lý kim loại (phân biệt kim loại đen/màu, đơn giá theo kg khác nhau)
- PlasticScrap: quản lý nhựa (phân loại theo mã nhựa, độ sạch)
- PaperScrap: quản lý giấy/carton (phân biệt giấy báo, carton, giấy văn phòng)
- EWasteScrap: quản lý thiết bị điện tử cũ (yêu cầu kiểm định nguồn gốc)

Tính chất OOP: Abstraction, Inheritance, Polymorphism, Encapsulation.

## Phần C – Customer, Cart và CartItem

### Customer
- customerId
- name
- phone
- address

### Chức năng
Lưu và hiển thị thông tin khách hàng.

### Cart
- List<CartItem>
- addItem()
- removeItem()
- updateWeight()
- calculateTotal()
- showCart()

### CartItem
- ScrapItem item
- double weightKg

Cart chứa nhiều CartItem. CartItem đại diện cho một loại phế liệu và khối lượng (kg) khách mang đến bán.

Tính chất OOP: Encapsulation + Composition/Aggregation.

## Phần D – PurchaseOrder và PurchaseOrderItem

### PurchaseOrder
- orderId
- customer
- List<PurchaseOrderItem> items
- OrderStatus status

### Chức năng
- addItem()
- removeItem()
- calculateTotal()
- showOrder()

### PurchaseOrderItem
- ScrapItem item
- double weightKg
- double pricePerKg

PurchaseOrder quản lý danh sách PurchaseOrderItem. PurchaseOrderItem lưu đơn giá tại thời điểm cân/thu mua để không bị ảnh hưởng khi giá thị trường phế liệu thay đổi.

Tính chất OOP: Encapsulation + Composition.

## Phần E – Payment và Polymorphism

```java
Payment p1 = new CashPayment(...);
Payment p2 = new BankPayment(...);
Payment p3 = new EWalletPayment(...);

p1.pay(...);
p2.pay(...);
p3.pay(...);
