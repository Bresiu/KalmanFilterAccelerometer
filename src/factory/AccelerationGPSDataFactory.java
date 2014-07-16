package factory;

import bus.BusProvider;
import com.google.common.eventbus.EventBus;
import constants.Constants;
import io.Importer;

import java.util.List;

public class AccelerationGPSDataFactory {

    private List<String> AccDataLines;
    private List<String> GPSDataLines;
    private EventBus bus;

    public AccelerationGPSDataFactory() {
        Importer importer = new Importer();
        AccDataLines = importer.readData(Constants.MAGNITUDE_ACCELERATION);
        GPSDataLines = importer.readData(Constants.GPS_DATA);
        registerBus();
        startFactory();
    }

    private void registerBus() {
        bus = BusProvider.getInstance();
    }

    private void startFactory() {
        Thread thread = new Thread() {
            public void run() {
                for (String string : AccDataLines) {
                    SensorSingleData sensorSingleData = proccessLine(string);
                    bus.post(sensorSingleData);

                    // Simulate GPS intervals
                    // pauseThread(Constants.SLEEP_TIME);
                }
            }
        };
        thread.start();
    }

    private SensorSingleData proccessLine(String sensorLine) {
        String[] sensorParts = sensorLine.split(" ");

        SensorSingleData sensorSingleData = new SensorSingleData();

        sensorSingleData.setNumber(Long.valueOf(sensorParts[0]));
        sensorSingleData.setTimestamp(Long.valueOf(sensorParts[1]));

        sensorSingleData.setAccX(Double.valueOf(sensorParts[2]));
        sensorSingleData.setAccY(Double.valueOf(sensorParts[3]));
        sensorSingleData.setAccZ(Double.valueOf(sensorParts[4]));

        sensorSingleData.setGyroX(Double.valueOf(sensorParts[5]));
        sensorSingleData.setGyroY(Double.valueOf(sensorParts[6]));
        sensorSingleData.setGyroZ(Double.valueOf(sensorParts[7]));

        sensorSingleData.setMagnX(Double.valueOf(sensorParts[8]));
        sensorSingleData.setMagnY(Double.valueOf(sensorParts[9]));
        sensorSingleData.setMagnZ(Double.valueOf(sensorParts[10]));

        return sensorSingleData;
    }

    private void pauseThread(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
