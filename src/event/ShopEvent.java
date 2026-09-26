package Event;


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
