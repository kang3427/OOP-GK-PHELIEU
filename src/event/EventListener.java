package event;

@FunctionalInterface
public interface EventListener {
    void onEvent(ShopEvent event);
}