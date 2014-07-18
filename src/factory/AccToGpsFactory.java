package factory;

import constants.Constants;
import containers.AccMagn;
import containers.GPSData;
import io.Exporter;
import search.AccFinder;

import java.util.List;

public class AccToGpsFactory {

    Exporter exporter;

    public AccToGpsFactory() {
        exporter = new Exporter();
    }

    public void produce() {
        AccDataFactory accDataFactory = new AccDataFactory();
        GpsDataFactory gpsDataFactory = new GpsDataFactory();

        List<AccMagn> magns = accDataFactory.getAccMagnsFromFile();
        List<GPSData> gpss = gpsDataFactory.getGpsDataFromFile();

        AccFinder accFinder = new AccFinder();
        gpss = accFinder.getAccIter(gpss, magns);

        // TODO: add check for null here
        for (GPSData gpsData : gpss) {
            exportNewData(Constants.NEW_GPS_DATA, gpsData.toString());
        }
    }

    private void exportNewData(String filename, String line) {
        exporter.writeData(filename, line);
    }
}
