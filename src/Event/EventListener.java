package Event;

/**
 * Interface định nghĩa bộ lắng nghe sự kiện (Subscriber / Observer).
 * Các lớp muốn nhận thông báo tự động khi có sự kiện xảy ra trong vựa phế liệu
 * (như bộ phận Ghi log, bộ phận Cảnh báo kho, bộ phận Tích điểm khách hàng) sẽ triển khai interface này.
 */

public interface EventListener {
    void onEvent(ShopEvent event);
}