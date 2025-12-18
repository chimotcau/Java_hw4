import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        TaxiDispatcher taxiDispatcher = new TaxiDispatcher();

        int numTaxis = 5;
        List<TaxiDriver> taxiDrivers = new ArrayList<>();

        for(int i = 1; i<= numTaxis; i++){
            TaxiDriver taxi = new TaxiDriver("Id" + i);
            taxiDispatcher.addTaxi(taxi);
            taxiDrivers.add(taxi);
        }

        for(TaxiDriver taxi : taxiDrivers) {
            taxi.startWorking();
        }

        taxiDispatcher.startDispatching();
    };
}
