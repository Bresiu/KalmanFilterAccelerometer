import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class KalmanSensor {
    private List<Double> noiseVariances;       // Noise variances
    private List<Double> predictedVariances;   // Predicted variances
    private List<Double> predictedValues;      // Predicted values

    private boolean isInitialised;

    private Exporter exporter;
    private long count = 0;

    private double gravityX = 0.0;
    private double gravityY = 0.0;
    private double gravityZ = 0.0;

    private int meanCounter;
    private List<Double> meanList;
    private double meanX = 0;
    private double meanY = 0;
    private double meanZ = 0;

    public KalmanSensor() {
        isInitialised = false;
        noiseVariances = new ArrayList<Double>();
        predictedVariances = new ArrayList<Double>();
        predictedValues = new ArrayList<Double>();
        exporter = new Exporter();
        meanCounter = 0;
        meanList = new ArrayList<Double>();
        registerBus();
    }

    private void registerBus() {
        BusProvider.getInstance().register(this);
    }

    @Subscribe
    public void onSensorUpdate(SensorSingleData singleData) {
        passToFilter(singleData);
        // meanFilter(singleData);
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
        float alpha;

        if (!Constants.ALPHA_STATIC) {
            // Find the sample period (between updates).
            // Convert from nanoseconds to seconds
            float dt = 1 / (count / (singleData.getDt() / 1000000000.0f));

            alpha = Constants.TIME_CONSTANT / (Constants.TIME_CONSTANT + dt);
        } else {
            alpha = Constants.ALPHA;
        }

        count++;

        if (count > 5) {
            gravityX = alpha * gravityX + (1 - alpha) * singleData.getAccX();
            gravityY = alpha * gravityY + (1 - alpha) * singleData.getAccY();
            gravityZ = alpha * gravityZ + (1 - alpha) * singleData.getAccZ();

            SensorSingleData linearAcceleration = new SensorSingleData();

            linearAcceleration.setNumber(singleData.getNumber());
            linearAcceleration.setAccX(singleData.getAccX() - gravityX);
            linearAcceleration.setAccY(singleData.getAccX() - gravityY);
            linearAcceleration.setAccZ(singleData.getAccX() - gravityZ);

            exportNewSensorData("linear_acceleration.dat", linearAcceleration);
        }

    }

    private void wikiFilter(SensorSingleData singleData) {

    }

    private void meanFilter(SensorSingleData singleData) {
        // TODO
        meanCounter++;
        double x = singleData.getAccX();
        double y = singleData.getAccY();
        double z = singleData.getAccZ();
        if (x < Constants.DELTA_ERROR && x > Constants.DELTA_ERROR) {
            x = 0;
        }
        if (y < Constants.DELTA_ERROR && y > Constants.DELTA_ERROR) {
            y = 0;
        }
        if (z < Constants.DELTA_ERROR && z > Constants.DELTA_ERROR) {
            z = 0;
        }
        meanX += x;
        meanY += y;
        meanZ += z;

        if (meanCounter == Constants.MEAN_FILTER_WINDOW) {
            meanList.add(meanX / Constants.MEAN_FILTER_WINDOW);
            meanList.add(meanY / Constants.MEAN_FILTER_WINDOW);
            meanList.add(meanZ / Constants.MEAN_FILTER_WINDOW);

            SensorSingleData sensorSingleData = new SensorSingleData();
            sensorSingleData.setAccX(meanList.get(0));
            sensorSingleData.setAccY(meanList.get(1));
            sensorSingleData.setAccZ(meanList.get(2));

            exportNewSensorData(Constants.MEAN_SENSOR_FILE_EXPORT, sensorSingleData);

            meanList.clear();
            meanCounter = 0;
            meanX = 0;
            meanY = 0;
            meanZ = 0;
        }
    }

    private void passData(SensorSingleData singleData) {
        List<Double> values = new ArrayList<Double>();
        values.add(singleData.getAccX());
        values.add(singleData.getAccY());
        values.add(singleData.getAccZ());
        process(singleData.getDt(), values);
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

            SensorSingleData sensorSingleData = new SensorSingleData();
            sensorSingleData.setAccX(correctedValues.get(0));
            sensorSingleData.setAccY(correctedValues.get(1));
            sensorSingleData.setAccZ(correctedValues.get(2));

            exportNewSensorData(Constants.SENSOR_FILE_EXPORT, sensorSingleData);
        } else {
            init(measurementValues);
        }
    }

    private void exportNewSensorData(String name, SensorSingleData sensorSingleData) {
        exporter.writeData(name, sensorSingleData.toString());
    }
}