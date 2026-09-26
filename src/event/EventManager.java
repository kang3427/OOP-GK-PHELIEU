package event;
import java.util.ArrayList;
import java.util.List;
package Event;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<EventListener> listeners = new ArrayList<>();

    // Hàm subscribe() dùng để đăng ký nhận thông báo
    public void subscribe(EventListener listener) {
        listeners.add(listener);
    }

    // Hàm unsubscribe() dùng để hủy đăng ký
    public void unsubscribe(EventListener listener) {
        listeners.remove(listener);
    }

    // Hàm publish() dùng để phát sự kiện cho các listener
    public void publish(ShopEvent event) {
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
