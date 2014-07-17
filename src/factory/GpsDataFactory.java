package factory;

import constants.Constants;
import containers.GPSData;
import io.Importer;

import java.util.ArrayList;
import java.util.List;

public class GpsDataFactory {
    private Importer importer;
    private List<GPSData> gpsDatas;


    public GpsDataFactory() {
        importer = new Importer();
        gpsDatas = new ArrayList<GPSData>();
    }

    public List<GPSData> getGpsDataFromFile() {
        List<String> gpsDataLines = importer.readData(Constants.GPS_DATA);

        for (String line : gpsDataLines) {
            gpsDatas.add(new GPSData(Long.valueOf(line)));
        }
        return gpsDatas;
    }

    /*
    private GPSData proccessLine(String sensorLine) {
        String[] sensorParts = sensorLine.split(" ");

        return new GPSData(Long.valueOf(sensorParts[0]));
    }
    */
}
