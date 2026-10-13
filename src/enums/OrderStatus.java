package enums;

//trạng thái phiếu mua hàng
public enum OrderStatus {
    PENDING,//chờ xử lý
    PROCESSING,//đang xử lý(kiểm tra phế liệu)
    COMPLETED,//đã thanh toán
    CANCELLED //đã bị hủy
}
