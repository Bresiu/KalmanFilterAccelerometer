import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class KalmanSensor {
    private static final double VARIANCE = 0.05;    // Noise variance estimation in percent

    private List<Double> noiseVariances;       // Noise variances
    private List<Double> predictedVariances;   // Predicted variances
    private List<Double> predictedValues;      // Predicted values

    private boolean isInitialised;

    private Exporter exporter;

    public KalmanSensor() {
        isInitialised = false;
        noiseVariances = new ArrayList<Double>();
        predictedVariances = new ArrayList<Double>();
        predictedValues = new ArrayList<Double>();
        exporter = new Exporter();
        registerBus();
    }

    private void registerBus() {
        BusProvider.getInstance().register(this);
    }

    @Subscribe
    public void onSensorUpdate(SensorSingleData singleData) {
        passData(singleData);
    }

    private void passData(SensorSingleData singleData) {
        List<Double> values = new ArrayList<Double>();
        values.add(singleData.getAccX());
        values.add(singleData.getAccY());
        values.add(singleData.getAccZ());
        values.add(singleData.getAccV());
        process(singleData.getTimestamp(), values);
    }

    public void init(long timestamp, List<Double> initValues) {
        for (int i = 0; i < initValues.size(); i++) {
            noiseVariances.add(i, VARIANCE);
            predictedVariances.add(i, noiseVariances.get(i));
            predictedValues.add(i, initValues.get(i));
        }
        isInitialised = true;

        exportNewSensorData(timestamp, initValues);
    }

    public void process(long timestamp, List<Double> measurementValues) {
        if (isInitialised) {
            List<Double> correctedValues = new ArrayList<Double>();
            for (int i = 0; i < measurementValues.size(); i++) {
                // compute the Kalman gain
                double kalmanGain = predictedVariances.get(i) / (predictedVariances.get(i) + noiseVariances.get(i));

                // update the sensor prediction with the measurement
                double correctedValue = Constants.FILTER_GAIN * predictedValues.get(i) + (1.0 - Constants.FILTER_GAIN) *
                        measurementValues.get(i) + kalmanGain * (measurementValues.get(i) - predictedValues.get(i));

                // predict next variance and value
                predictedVariances.add(i, predictedVariances.get(i) * (1.0 - kalmanGain));
                predictedValues.add(i, correctedValue);

                correctedValues.add(i, correctedValue);
            }
            exportNewSensorData(timestamp, correctedValues);
        } else {
            init(timestamp, measurementValues);
        }
    }

    private void exportNewSensorData(long timestamp, List<Double> correctedValues) {
        SensorSingleData newSensorData = new SensorSingleData(timestamp, correctedValues.get(0),
                correctedValues.get(1), correctedValues.get(2), correctedValues.get(3));
        exporter.writeData(newSensorData.toString());
    }
}