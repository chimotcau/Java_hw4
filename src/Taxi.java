public interface Taxi {
    void placeOrder(String orderId);
    boolean available();
    void setDispatcher(Dispatcher dispatcher);
}
