package filters;

import constants.Constants;
import factory.SensorSingleData;

public class MeanFilter {

    private int meanCounter;
    private double meanX;
    private double meanY;
    private double meanZ;

    public MeanFilter() {
        initObjects();
    }

    private void initObjects() {
        meanCounter = 0;
        meanX = 0.0;
        meanY = 0.0;
        meanZ = 0.0;
    }

    public SensorSingleData filter(SensorSingleData sensorSingleData) {

        meanX += sensorSingleData.getAccX();
        meanY += sensorSingleData.getAccY();
        meanZ += sensorSingleData.getAccZ();

        meanCounter++;

        return checkFilterWindow(sensorSingleData);
    }

    private SensorSingleData checkFilterWindow(SensorSingleData sensorSingleData) {
        if (meanCounter == Constants.MEAN_FILTER_WINDOW) {

            meanX /= Constants.MEAN_FILTER_WINDOW;
            meanY /= Constants.MEAN_FILTER_WINDOW;
            meanZ /= Constants.MEAN_FILTER_WINDOW;

            sensorSingleData.setAccX(meanX);
            sensorSingleData.setAccY(meanY);
            sensorSingleData.setAccZ(meanZ);

            meanCounter = 0;
            meanX = 0;
            meanY = 0;
            meanZ = 0;

            return sensorSingleData;
        }
        return null;
    }
}
