import containers.AccMagn;
import containers.GPSData;
import factory.AccDataFactory;
import factory.GpsDataFactory;
import search.AccFinder;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        AccDataFactory accDataFactory = new AccDataFactory();
        GpsDataFactory gpsDataFactory = new GpsDataFactory();

        List<AccMagn> magns = accDataFactory.getAccMagnsFromFile();
        List<GPSData> gpss = gpsDataFactory.getGpsDataFromFile();

        AccFinder accFinder = new AccFinder();
        accFinder.getAccIter(gpss, magns);

        /*
        Double accV = accFinder.getAccBinary(2, magns);
        if (accV != null) {
            System.out.println("Acc: " + accV);
        } else {
            System.out.println("ACC == null");
        }
        */


        // new LinearAcceleration();
        // new SensorDataFactory();
    }
}