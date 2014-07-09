package filters;

import constants.Constants;
import factory.SensorSingleData;

import java.util.ArrayList;
import java.util.List;

public class KalmanFilter {

    private List<Double> noiseVariances;       // Noise variances
    private List<Double> predictedVariances;   // Predicted variances
    private List<Double> predictedValues;      // Predicted values
    private boolean isInitialised;

    public KalmanFilter() {
        noiseVariances = new ArrayList<Double>();
        predictedVariances = new ArrayList<Double>();
        predictedValues = new ArrayList<Double>();
        isInitialised = false;
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

        // TODO: add return here
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
}
