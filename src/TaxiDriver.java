import java.util.Random;

public class TaxiDriver implements Taxi, Runnable {
    private final String taxiId;
    private Dispatcher dispatcher;
    private volatile boolean working = false;
    private final Random random = new Random();
    private final Thread worker;

    public TaxiDriver(String taxiID) {
        this.taxiId = taxiID;
        this.worker = new Thread(this, "taxi " + taxiID);
    }

    @Override
    public void run() {
        System.out.println(" Taxi " + taxiId + " is working");

        while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("Taxi" + taxiId + "finished");
    }

    @Override
    public synchronized void placeOrder(String orderId) {
        if(working) {
            return;
        }

        working = true;
        System.out.println("Taxi " + taxiId + " received " + orderId);

        new Thread(() -> processOrder(orderId)).start();
    }

    private void processOrder(String orderId) {
        try {
            int processTime = 1000 + random.nextInt(2000);
            System.out.println("Taxi " + taxiId + " is processing " + orderId + " will take " + processTime);

            Thread.sleep(processTime);

            System.out.println("Taxi " + taxiId + " finished " + orderId);

        } catch (InterruptedException e) {
        } finally {
            working = false;
        }
    }

    @Override
    public synchronized boolean available() {
        return !working;
    }

    @Override
    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void startWorking() {
        worker.start();
    }
}