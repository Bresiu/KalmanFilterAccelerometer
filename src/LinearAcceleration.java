import bus.BusProvider;
import com.google.common.eventbus.Subscribe;
import constants.Constants;
import factory.SensorSingleData;
import io.Exporter;

import java.util.ArrayList;
import java.util.List;

public class LinearAcceleration {

    public LinearAcceleration() {

        exporter = new Exporter();
        meanCounter = 0;
        registerBus();
    }

    private void registerBus() {
        BusProvider.getInstance().register(this);
    }

    // TODO: change void to factory.SensorSingleData
    // TODO: move each filter to new class
    // TODO: alpha in Low-Pass filter as: t / (t + dT), where t, the low-pass filter's time-constant
    // TODO: and dT, the event delivery rate
    // TODO: http://seattlesensor.wordpress.com/2013/01/01/accelerometer-sensor-data-processing/

    @Subscribe
    public void onSensorUpdate(SensorSingleData singleData) {
        // bandPassFilter(singleData);
        // highPassFilter(singleData);
        // passToFilter(singleData);
        // meanFilter(singleData, constants.Constants.KALMAN_DELTA_ERROR);
        // passData(singleData);
    }

    // KALMAN
    private void passData(SensorSingleData singleData) {
        List<Double> values = new ArrayList<Double>();
        values.add(singleData.getAccX());
        values.add(singleData.getAccY());
        values.add(singleData.getAccZ());
        process(values);
    }

    // EXPORT DATA
    private void exportNewSensorData(String name, SensorSingleData sensorSingleData) {
        exporter.writeData(name, sensorSingleData.toString());
    }
}