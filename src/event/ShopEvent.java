package event;

/** Dữ liệu bất biến của một sự kiện trong hệ thống cửa hàng phế liệu. */
public class ShopEvent {
    private final String eventType;
    private final String message;

    public ShopEvent(String eventType, String message) {
        if (eventType == null || eventType.trim().isEmpty()) {
            throw new IllegalArgumentException("Loại sự kiện không được để trống.");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Nội dung sự kiện không được để trống.");
        }
        this.eventType = eventType.trim();
        this.message = message.trim();
    }

    public String getEventType() { return eventType; }
    public String getMessage() { return message; }
}
