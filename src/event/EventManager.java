package event;

import java.util.ArrayList;
import java.util.List;

/**
 * Quản lý đăng ký và phát sự kiện theo mô hình Observer.
 * publish() duyệt bản sao danh sách để listener có thể đăng ký/hủy trong lúc nhận sự kiện
 * mà không gây ConcurrentModificationException.
 */
public class EventManager {
    private final List<EventListener> listeners = new ArrayList<>();

    public synchronized void subscribe(EventListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("EventListener không được null.");
        }
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public synchronized void unsubscribe(EventListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    public void publish(ShopEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Sự kiện không được null.");
        }
        List<EventListener> snapshot;
        synchronized (this) {
            snapshot = new ArrayList<>(listeners);
        }
        for (EventListener listener : snapshot) {
            listener.onEvent(event);
        }
    }
}
