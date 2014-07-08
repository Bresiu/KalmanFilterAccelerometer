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

    // Gravity Low-Pass filter
    private double gravityLowPassX = 0.0;
    private double gravityLowPassY = 0.0;
    private double gravityLowPassZ = 0.0;

    // Filtered values Low-Pass filter
    private double filteredValueX = 0.0;
    private double filteredValueY = 0.0;
    private double filteredValueZ = 0.0;

    // Gravity Wikipedia filter
    private double gravityWikipediaX = 0.0;
    private double gravityWikipediaY = 0.0;
    private double gravityWikipediaZ = 0.0;

    // High-Pass filter
    private double gravityHighPassX = 0.0;
    private double gravityHighPassY = 0.0;
    private double gravityHighPassZ = 0.0;

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

        // lowPassFilter(newSingleData);
        highPassFilter(newSingleData);
        // wikiFilter(newSingleData);
    }

    // Low-Pass filter + High-Pass filter
    private void bandPassFilter(SensorSingleData singleData) {
        lowPassFilter(singleData);
    }

    private void highPassFilter(SensorSingleData singleData) {
        gravityHighPassX = Constants.HIGH_PASS_ALPHA * gravityHighPassX + (1.0f - Constants.HIGH_PASS_ALPHA) * singleData
                .getAccX();
        gravityHighPassY = Constants.HIGH_PASS_ALPHA * gravityHighPassY + (1.0f - Constants.HIGH_PASS_ALPHA) * singleData
                .getAccY();
        gravityHighPassZ = Constants.HIGH_PASS_ALPHA * gravityHighPassZ + (1.0f - Constants.HIGH_PASS_ALPHA) * singleData
                .getAccZ();

        // TODO move this to another method (merge with wikiFilter)
        SensorSingleData linearAcceleration = new SensorSingleData();

        linearAcceleration.setNumber(singleData.getNumber());
        linearAcceleration.setAccX(singleData.getAccX() - gravityHighPassX);
        linearAcceleration.setAccY(singleData.getAccY() - gravityHighPassY);
        linearAcceleration.setAccZ(singleData.getAccZ() - gravityHighPassZ);

        // removeNoise(Constants.LINEAR_ACCELERATION_FILE_EXPORT, linearAcceleration, Constants.HIGH_PASS_DELTA_ERROR);
        exportNewSensorData(Constants.LINEAR_ACCELERATION_FILE_EXPORT, linearAcceleration);
    }

    private void wikiFilter(SensorSingleData singleData) {
        // y[i] = y[i] + alpha * (x[i] - y[i])
        gravityWikipediaX = gravityWikipediaX + Constants.WIKIPEDIA_ALPHA * (singleData.getAccX() - gravityWikipediaX);
        gravityWikipediaY = gravityWikipediaY + Constants.WIKIPEDIA_ALPHA * (singleData.getAccY() - gravityWikipediaY);
        gravityWikipediaZ = gravityWikipediaZ + Constants.WIKIPEDIA_ALPHA * (singleData.getAccZ() - gravityWikipediaZ);

        SensorSingleData linearAcceleration = new SensorSingleData();

        linearAcceleration.setNumber(singleData.getNumber());
        linearAcceleration.setAccX(singleData.getAccX() - gravityWikipediaX);
        linearAcceleration.setAccY(singleData.getAccY() - gravityWikipediaY);
        linearAcceleration.setAccZ(singleData.getAccZ() - gravityWikipediaZ);

        removeNoise(Constants.LINEAR_ACCELERATION_FILE_EXPORT_2, linearAcceleration, Constants.LOW_PASS_DELTA_ERROR);
    }


    // TODO: refactor this to return singleData;
    private void lowPassFilter(SensorSingleData singleData) {
        filteredValueX = singleData.getAccX() * Constants.LOW_PASS_ALPHA + filteredValueX * (1.0f - Constants
                .LOW_PASS_ALPHA);
        filteredValueY = singleData.getAccY() * Constants.LOW_PASS_ALPHA + filteredValueY * (1.0f - Constants
                .LOW_PASS_ALPHA);
        filteredValueZ = singleData.getAccZ() * Constants.LOW_PASS_ALPHA + filteredValueZ * (1.0f - Constants
                .LOW_PASS_ALPHA);


        SensorSingleData filteredValues = new SensorSingleData();
        filteredValues.setNumber(singleData.getNumber());
        filteredValues.setAccX(filteredValueX);
        filteredValues.setAccY(filteredValueY);
        filteredValues.setAccZ(filteredValueZ);

        highPassFilter(filteredValues);
    }

    private void removeNoise(String fileName, SensorSingleData singleData, Double deltaError) {
        double x = singleData.getAccX();
        double y = singleData.getAccY();
        double z = singleData.getAccZ();

        if (x < deltaError && x > -deltaError) {
            x = 0;
        }
        if (y < deltaError && y > -deltaError) {
            y = 0;
        }
        if (z < deltaError && z > -deltaError) {
            z = 0;
        }

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