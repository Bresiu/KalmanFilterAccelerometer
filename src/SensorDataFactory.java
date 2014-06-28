import com.google.common.eventbus.EventBus;

import java.util.List;

public class SensorDataFactory {

    private List<String> sensorDataLines;
    private EventBus bus;

    private final long SLEEP_TIME = 1000;

    public SensorDataFactory() {
        Importer importer = new Importer();
        sensorDataLines = importer.readData();
        registerBus();
        startFactory();
    }

    private void registerBus() {
        bus = BusProvider.getInstance();
    }

    private void startFactory() {
        Thread thread = new Thread() {
            public void run() {
                for (String string : sensorDataLines) {
                    SensorSingleData sensorSingleData = proccessLine(string);
                    bus.post(sensorSingleData);

                    // Simulate GPS intervals
                    // pauseThread(SLEEP_TIME);
                }
            }
        };
        thread.start();
    }

    private SensorSingleData proccessLine(String sensorLine) {
        String[] sensorParts = sensorLine.split(" ");
        return new SensorSingleData(Long.valueOf(sensorParts[0]), Double.valueOf(sensorParts[1]));
    }

    private void pauseThread(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
