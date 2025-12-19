import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaxiDispatcher implements Dispatcher, Runnable {
    private final List<Taxi> taxis = new ArrayList<>();
    private volatile boolean dispatch = false;
    private final Thread dispatcher;
    private final Random random = new Random();
    private int oderCount = 0;
    public TaxiDispatcher() {
        this.dispatcher = new Thread(this, "TaxiDispatcher");
    }

    @Override
    public void addTaxi(Taxi taxi) {
        taxis.add(taxi);
        taxi.setDispatcher(this);
    }

    @Override
    public void startDispatching() {
        if(!dispatch) {
            dispatch = true;
            dispatcher.start();
        }
    }

    @Override
    public void run() {
        System.out.println("Dispatcher is working");

        while (dispatch && !Thread.currentThread().isInterrupted()) {
            try {
                String orderId = "Order " + (++oderCount);
                System.out.println(" Dispatcher is processing " + orderId);

                boolean orderPlaced = false;

                synchronized (taxis) {
                    for (Taxi taxi : taxis) {
                        if (taxi.available()) {
                            taxi.placeOrder(orderId);
                            orderPlaced = true;
                            break;
                        }
                    }
                }
                if(!orderPlaced) {
                    System.out.println(orderId + " has no taxi");
                }

                int wait = 1000 + random.nextInt(2000);
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                    break;
                }
            }

            System.out.println("Dispatcher stops");
    }
}

