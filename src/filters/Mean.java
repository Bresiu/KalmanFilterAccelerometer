package filters;

import constants.Constants;
import factory.SensorSingleData;

public class Mean {

    private long counter;

    private int meanCounter;

    private double meanAccX;
    private double meanAccY;
    private double meanAccZ;

    private double meanGyroX;
    private double meanGyroY;
    private double meanGyroZ;

    private double meanMagnX;
    private double meanMagnY;
    private double meanMagnZ;

    public Mean() {
        counter = 0;
        initObjects();
    }

    private void initObjects() {
        meanCounter = 0;

        meanAccX = 0.0;
        meanAccY = 0.0;
        meanAccZ = 0.0;

        meanGyroX = 0.0;
        meanGyroY = 0.0;
        meanGyroZ = 0.0;

        meanMagnX = 0.0;
        meanMagnY = 0.0;
        meanMagnZ = 0.0;
    }

    public SensorSingleData filter(SensorSingleData sensorSingleData) {
        meanAccX += sensorSingleData.getAccX();
        meanAccY += sensorSingleData.getAccY();
        meanAccZ += sensorSingleData.getAccZ();

        meanGyroX += sensorSingleData.getGyroX();
        meanGyroY += sensorSingleData.getGyroY();
        meanGyroZ += sensorSingleData.getGyroZ();

        meanMagnX += sensorSingleData.getMagnX();
        meanMagnY += sensorSingleData.getMagnY();
        meanMagnZ += sensorSingleData.getMagnZ();

        meanCounter++;

        return checkFilterWindow(sensorSingleData);
    }

    private SensorSingleData checkFilterWindow(SensorSingleData sensorSingleData) {
        if (meanCounter == Constants.MEAN_FILTER_WINDOW) {

            meanAccX /= Constants.MEAN_FILTER_WINDOW;
            meanAccY /= Constants.MEAN_FILTER_WINDOW;
            meanAccZ /= Constants.MEAN_FILTER_WINDOW;

            meanGyroX /= Constants.MEAN_FILTER_WINDOW;
            meanGyroY /= Constants.MEAN_FILTER_WINDOW;
            meanGyroZ /= Constants.MEAN_FILTER_WINDOW;

            meanMagnX /= Constants.MEAN_FILTER_WINDOW;
            meanMagnY /= Constants.MEAN_FILTER_WINDOW;
            meanMagnZ /= Constants.MEAN_FILTER_WINDOW;

            sensorSingleData.setAccX(meanAccX);
            sensorSingleData.setAccY(meanAccY);
            sensorSingleData.setAccZ(meanAccZ);

            sensorSingleData.setGyroX(meanGyroX);
            sensorSingleData.setGyroY(meanGyroY);
            sensorSingleData.setGyroZ(meanGyroZ);

            sensorSingleData.setMagnX(meanMagnX);
            sensorSingleData.setMagnY(meanMagnY);
            sensorSingleData.setMagnZ(meanMagnZ);

            sensorSingleData.setNumber(counter);
            counter++;

            initObjects();

            return sensorSingleData;
        }
        return null;
    }
}