package event;
import java.util.ArrayList;
import java.util.List;


/**
 * Lớp quản lý và điều hướng sự kiện tập trung (Publisher / Subject).
 * Nắm giữ danh sách các bộ lắng nghe (EventListener), cung cấp các chức năng:
 * - Đăng ký lắng nghe sự kiện (subscribe)
 * - Hủy đăng ký lắng nghe (unsubscribe)
 * - Phát thông báo sự kiện (notify/publish) đến tất cả Listener đang lắng nghe khi có thay đổi
 */

public class EventManager {
    private List<EventListener> listeners = new ArrayList<>();
    private event.ShopEvent event;

    // Hàm subscribe() dùng để đăng ký nhận thông báo
    public void subscribe(EventListener listener) {
        listeners.add(listener);
    }

    // Hàm unsubscribe() dùng để hủy đăng ký
    public void unsubscribe(EventListener listener) {
        listeners.remove(listener);
    }

    // Hàm publish() dùng để phát sự kiện cho các listener
    public void publish(event.ShopEvent event) {
        this.event = event;
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}