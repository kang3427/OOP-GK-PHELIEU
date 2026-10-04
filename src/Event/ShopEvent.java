package Event;

/**
 * Lớp đại diện cho một sự kiện diễn ra trong vựa phế liệu (Event Object).
 * Chứa thông tin chi tiết về sự kiện phát ra như: loại sự kiện, dữ liệu kèm theo và thời điểm xảy ra.
 * (Ví dụ: Sự kiện "Nhập phế liệu vào kho", "Thanh toán phiếu mua", "Cảnh báo hết dung lượng kho").
 */

public class ShopEvent {
    private String eventType;
    private String message;

    public ShopEvent(String eventType, String message) {
        this.eventType = eventType;
        this.message = message;
    }

    public String getEventType() {
        return eventType;
    }

    public String getMessage() {
        return message;
    }
}
