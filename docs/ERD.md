# ERD – Hệ thống quản lý cửa hàng phế liệu

Tài liệu này ghi nhận mô hình dữ liệu khái niệm dựa trên các lớp hiện có trong mã nguồn. Đây là tài liệu mô tả mô hình, không phải schema cơ sở dữ liệu đã triển khai.

## Thực thể và thuộc tính chính

- **Customer**: `customerId` (PK khái niệm), `name`, `phone`, `address`, `customerType`.
- **Employee**: `idNhanVien` (PK khái niệm), `tenNhanVien`, `sdt`, `diaChi`.
- **ScrapItem**: `id` (PK khái niệm), `name`, `pricePerKg`, `weight`. Các lớp con: `MetalScrap`, `PaperScrap`, `PlasticScrap`, `EWasteScrap`.
- **Cart**: `cartId` (PK), chứa các `CartItem`.
- **CartItem**: `cartItemId` (mã dòng), `pricePerKgNow`, tham chiếu tới `ScrapItem`.
- **PurchaseOrder**: `orderId` (PK), `status`, tham chiếu tới `Customer`.
- **PurchaseOrderItem**: `purchaseOrderItemId` (mã dòng), `pricePerKgNow`, `weightKgNow`, tham chiếu tới `PurchaseOrder` và `ScrapItem`.
- **Invoice**: `invoiceId` (PK), tham chiếu tới `PurchaseOrder`, `Employee`, `Payment` và `CustomerReward`.
- **InventoryManager**: `idItem` (mã kho/định danh kho trong chương trình), `khoiLuong`, `sucChuaToiDa`. Trong `Main`, hệ thống tạo bốn đối tượng kho theo nhóm kim loại, giấy, nhựa và điện tử.

## Quan hệ và lực lượng

1. **Customer 1 — 0..N PurchaseOrder**: mỗi đơn hàng có một khách hàng; một khách hàng có thể có nhiều đơn hàng.
2. **Cart 1 — 0..N CartItem**: một giỏ có thể có nhiều dòng hàng; mỗi dòng hàng thuộc một giỏ.
3. **ScrapItem 1 — 0..N CartItem**: mỗi dòng giỏ tham chiếu một mặt hàng; một mặt hàng có thể xuất hiện ở nhiều dòng.
4. **PurchaseOrder 1 — 1..N PurchaseOrderItem** khi đơn có hàng; mỗi dòng chi tiết thuộc một đơn hàng và tham chiếu một mặt hàng `ScrapItem`.
5. **ScrapItem 1 — 0..N PurchaseOrderItem**: một mặt hàng có thể xuất hiện trong nhiều đơn/dòng chi tiết.
6. **PurchaseOrder 1 — 0..1 Invoice**: một đơn hàng có thể chưa có hóa đơn hoặc có tối đa một hóa đơn; mỗi hóa đơn tham chiếu đúng một đơn hàng. Lớp `PurchaseOrder` kiểm tra và từ chối việc gắn thêm hóa đơn thứ hai.
7. **Employee 1 — 0..N Invoice**: mỗi hóa đơn có một nhân viên; một nhân viên có thể lập nhiều hóa đơn.
8. **InventoryManager** được mô hình như một thực thể quản lý tồn kho tổng hợp theo nhóm phế liệu. **Không vẽ khóa ngoại trực tiếp tới ScrapItem**, vì lớp hiện tại chỉ lưu mã kho, khối lượng và sức chứa, không giữ tham chiếu `ScrapItem` hoặc thuộc tính loại kho riêng.

## Ràng buộc và lưu ý

- `Customer.customerId`, `Employee.idNhanVien`, `ScrapItem.id`, `Cart.cartId`, `PurchaseOrder.orderId` và `Invoice.invoiceId` là các mã định danh dùng làm khóa chính khái niệm.
- `CartItem.cartItemId` và `PurchaseOrderItem.purchaseOrderItemId` được bổ sung làm mã dòng duy nhất trong mỗi đối tượng khi chạy chương trình, tránh phải dùng riêng mã mặt hàng để định danh dòng.
- Quan hệ `PurchaseOrder`–`Invoice` được giới hạn ở mức tối đa một hóa đơn cho mỗi đơn hàng trong code.
- `Payment` là interface, được triển khai bởi `CashPayment`, `BankPayment`, `EWalletPayment`; nếu vẽ ERD thuần túy thì thể hiện đây là nhóm thông tin/phương thức thanh toán thay vì quan hệ kế thừa UML.
- `CustomerReward` là đối tượng chính sách thưởng, không có mã định danh riêng trong code hiện tại; vì vậy cần mô tả như thông tin liên quan đến hóa đơn, không tự đặt khóa ngoại hay bảng độc lập nếu chưa thiết kế lưu trữ.
- Mã dòng UUID chỉ là định danh trong mô hình đối tượng hiện tại; nếu triển khai cơ sở dữ liệu, có thể dùng khóa thay thế tương đương theo quy ước của cơ sở dữ liệu.

