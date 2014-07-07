import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class LinearAcceleration {

    // Kalman
    private List<Double> noiseVariances;       // Noise variances
    private List<Double> predictedVariances;   // Predicted variances
    private List<Double> predictedValues;      // Predicted values
    private boolean isInitialised;

    private Exporter exporter;

    // Low-Pass filter
    private double gravityX = 0.0;
    private double gravityY = 0.0;
    private double gravityZ = 0.0;

    // Mean filter
    private int meanCounter;
    private double meanX = 0;
    private double meanY = 0;
    private double meanZ = 0;

    public LinearAcceleration() {
        isInitialised = false;
        noiseVariances = new ArrayList<Double>();
        predictedVariances = new ArrayList<Double>();
        predictedValues = new ArrayList<Double>();
        exporter = new Exporter();
        meanCounter = 0;
        registerBus();
    }

    private void registerBus() {
        BusProvider.getInstance().register(this);
    }

    // TODO: change void to SensorSingleData
    // TODO: move each filter to new class
    // TODO: alpha in Low-Pass filter as: t / (t + dT), where t, the low-pass filter's time-constant
    // TODO: and dT, the event delivery rate
    // TODO: http://seattlesensor.wordpress.com/2013/01/01/accelerometer-sensor-data-processing/

    @Subscribe
    public void onSensorUpdate(SensorSingleData singleData) {
        passToFilter(singleData);
        // meanFilter(singleData, Constants.KALMAN_DELTA_ERROR);
        // passData(singleData);
    }

    private void passToFilter(SensorSingleData singleData) {
        SensorSingleData newSingleData = new SensorSingleData();

        newSingleData.setNumber(singleData.getNumber());
        newSingleData.setAccX(singleData.getAccX() / Constants.EARTH_GRAVITY);
        newSingleData.setAccY(singleData.getAccY() / Constants.EARTH_GRAVITY);
        newSingleData.setAccZ(singleData.getAccZ() / Constants.EARTH_GRAVITY);

        lowPassfilter(newSingleData);
        wikiFilter(newSingleData);
    }

    private void lowPassfilter(SensorSingleData singleData) {
        gravityX = Constants.LOW_PASS_ALPHA * gravityX + (1 - Constants.LOW_PASS_ALPHA) * singleData.getAccX();
        gravityY = Constants.LOW_PASS_ALPHA * gravityY + (1 - Constants.LOW_PASS_ALPHA) * singleData.getAccY();
        gravityZ = Constants.LOW_PASS_ALPHA * gravityZ + (1 - Constants.LOW_PASS_ALPHA) * singleData.getAccZ();

        // TODO move this to another method (merge with wikiFilter)
        SensorSingleData linearAcceleration = new SensorSingleData();

        linearAcceleration.setNumber(singleData.getNumber());
        linearAcceleration.setAccX(singleData.getAccX() - gravityX);
        linearAcceleration.setAccY(singleData.getAccY() - gravityY);
        linearAcceleration.setAccZ(singleData.getAccZ() - gravityZ);

        removeNoise(Constants.LINEAR_ACCELERATION_FILE_EXPORT, linearAcceleration, Constants.LOW_PASS_DELTA_ERROR);
        // exportNewSensorData(Constants.LINEAR_ACCELERATION_FILE_EXPORT,, linearAcceleration);
    }

    private void wikiFilter(SensorSingleData singleData) {
        // y[i] = y[i] + alpha * (x[i] - y[i])
        gravityX = gravityX + Constants.WIKIPEDIA_ALPHA * (singleData.getAccX() - gravityX);
        gravityY = gravityY + Constants.WIKIPEDIA_ALPHA * (singleData.getAccY() - gravityY);
        gravityZ = gravityZ + Constants.WIKIPEDIA_ALPHA * (singleData.getAccZ() - gravityZ);

        SensorSingleData linearAcceleration = new SensorSingleData();

        linearAcceleration.setNumber(singleData.getNumber());
        linearAcceleration.setAccX(singleData.getAccX() - gravityX);
        linearAcceleration.setAccY(singleData.getAccY() - gravityY);
        linearAcceleration.setAccZ(singleData.getAccZ() - gravityZ);

        removeNoise(Constants.LINEAR_ACCELERATION_FILE_EXPORT_2, linearAcceleration, Constants.LOW_PASS_DELTA_ERROR);
    }

    private void removeNoise(String fileName, SensorSingleData singleData, Double deltaError) {
        double x = singleData.getAccX();
        double y = singleData.getAccY();
        double z = singleData.getAccZ();

        if (x < deltaError && x > -deltaError) { x = 0; }
        if (y < deltaError && y > -deltaError) { y = 0; }
        if (z < deltaError && z > -deltaError) { z = 0; }

        SensorSingleData sensorSingleData = new SensorSingleData();
        sensorSingleData.setNumber(singleData.getNumber());
        sensorSingleData.setAccX(x);
        sensorSingleData.setAccY(y);
        sensorSingleData.setAccZ(z);

        exportNewSensorData(fileName, sensorSingleData);
    }

    private void meanFilter(String fileName, SensorSingleData singleData) {
        meanCounter++;

        double x = singleData.getAccX();
        double y = singleData.getAccY();
        double z = singleData.getAccZ();

        meanX += x;
        meanY += y;
        meanZ += z;

        if (meanCounter == Constants.MEAN_FILTER_WINDOW) {

            meanX /= meanX / Constants.MEAN_FILTER_WINDOW;
            meanY /= meanY / Constants.MEAN_FILTER_WINDOW;
            meanZ /= meanZ / Constants.MEAN_FILTER_WINDOW;

            SensorSingleData sensorSingleData = new SensorSingleData();
            sensorSingleData.setNumber(singleData.getNumber());
            sensorSingleData.setAccX(meanX);
            sensorSingleData.setAccY(meanY);
            sensorSingleData.setAccZ(meanZ);

            exportNewSensorData(fileName, sensorSingleData);

            meanCounter = 0;
            meanX = 0;
            meanY = 0;
            meanZ = 0;
        }
    }

    // KALMAN
    private void passData(SensorSingleData singleData) {
        List<Double> values = new ArrayList<Double>();
        values.add(singleData.getAccX());
        values.add(singleData.getAccY());
        values.add(singleData.getAccZ());
        process(values);
    }

    public void init(List<Double> initValues) {
        for (int i = 0; i < initValues.size(); i++) {
            noiseVariances.add(i, Constants.VARIANCE);
            predictedVariances.add(i, noiseVariances.get(i));
            predictedValues.add(i, initValues.get(i));
        }
        isInitialised = true;

        SensorSingleData sensorSingleData = new SensorSingleData();
        sensorSingleData.setAccX(initValues.get(0));
        sensorSingleData.setAccY(initValues.get(1));
        sensorSingleData.setAccZ(initValues.get(2));

        exportNewSensorData(Constants.SENSOR_FILE_EXPORT, sensorSingleData);
    }

    public void process(List<Double> measurementValues) {
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

            SensorSingleData sensorSingleData = new SensorSingleData();
            sensorSingleData.setAccX(correctedValues.get(0));
            sensorSingleData.setAccY(correctedValues.get(1));
            sensorSingleData.setAccZ(correctedValues.get(2));

            exportNewSensorData(Constants.SENSOR_FILE_EXPORT, sensorSingleData);
        } else {
            init(measurementValues);
        }
    }

    // EXPORT DATA
    private void exportNewSensorData(String name, SensorSingleData sensorSingleData) {
        exporter.writeData(name, sensorSingleData.toString());
    }
}