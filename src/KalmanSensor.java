import com.google.common.eventbus.Subscribe;

public class KalmanSensor {
    private static final double VARIANCE = 0.05;    // Noise variance estimation in percent
    private static final double FILTER_GAIN = 0.9;  // Filter gain

    private double noiseVariance;       // Noise variance array
    private double predictedVariance;   // Predicted variance
    private double predictedValue;      // Predicted values

    private boolean isInitialised;

    private Exporter exporter;

    public KalmanSensor() {
        isInitialised = false;
        exporter = new Exporter();
        registerBus();
    }

    private void registerBus() {
        BusProvider.getInstance().register(this);
    }

    @Subscribe
    public void onSensorUpdate(SensorSingleData singleData) {
        process(singleData.getTimestamp(), singleData.getAccX());
    }

    public void init(long timestamp, double initValue) {
        noiseVariance = VARIANCE;
        predictedVariance = noiseVariance;
        predictedValue = initValue;

        isInitialised = true;

        exportNewSensorData(timestamp, initValue);
    }

    public void process(long timestamp, double measurementValue) {
        if (isInitialised) {
            // compute the Kalman gain for each dimension
            double kalmanGain = predictedVariance / (predictedVariance + noiseVariance);

            // update the sensor prediction with the measurement
            double correctedValue = FILTER_GAIN * predictedValue + (1.0 - FILTER_GAIN) * measurementValue + kalmanGain *
                    (measurementValue - predictedValue);

            // predict next variances and values
            predictedVariance = predictedVariance * (1.0 - kalmanGain);
            predictedValue = correctedValue;

            exportNewSensorData(timestamp, correctedValue);
        } else {
            init(timestamp, measurementValue);
        }
    }

    private void exportNewSensorData(long timestamp, double correctedValue) {
        SensorSingleData newSensorData = new SensorSingleData(timestamp, correctedValue);
        exporter.writeData(newSensorData.toString());
    }
}

