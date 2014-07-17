package filters;

import containers.SensorSingleData;

public class Mean {

    private int windowLength;

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

    public Mean(int windowLength) {
        this.windowLength = windowLength;
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

        if (checkFilterWindow()) {
            return createSensorSingleData(sensorSingleData);
        }
        return null;

    }

    private boolean checkFilterWindow() {
        return meanCounter == windowLength;
    }

    private SensorSingleData createSensorSingleData(SensorSingleData sensorSingleData) {

        meanAccX /= windowLength;
        meanAccY /= windowLength;
        meanAccZ /= windowLength;

        meanGyroX /= windowLength;
        meanGyroY /= windowLength;
        meanGyroZ /= windowLength;

        meanMagnX /= windowLength;
        meanMagnY /= windowLength;
        meanMagnZ /= windowLength;

        sensorSingleData
                .setAccX(meanAccX).setAccY(meanAccY).setAccZ(meanAccZ)
                .setGyroX(meanGyroX).setGyroY(meanGyroY).setGyroZ(meanGyroZ)
                .setMagnX(meanMagnX).setMagnY(meanMagnY).setMagnZ(meanMagnZ)
                .setNumber(counter);

        counter++;

        initObjects();

        return sensorSingleData;

    }
}