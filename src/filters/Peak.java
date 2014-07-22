package filters;

import constants.Constants;
import containers.PedometerData;
import containers.SensorSingleData;
import pedometer.StepDetector;

public class Peak {

    private double peakX;
    private double peakY;
    private double peakZ;

    private double lastX;
    private double lastY;
    private double lastZ;

    private boolean isUpPeakX;
    private boolean isUpPeakY;
    private boolean isUpPeakZ;

    private StepDetector stepDetector;

    public Peak() {
        peakX = 0.0;
        peakY = 0.0;
        peakZ = 0.0;

        lastX = 0.0;
        lastY = 0.0;
        lastZ = 0.0;

        isUpPeakX = false;
        isUpPeakY = false;
        isUpPeakZ = false;

        stepDetector = new StepDetector();
    }

    public PedometerData filter(SensorSingleData sensorSingleData) {

        double x = sensorSingleData.getAccX();
        double y = sensorSingleData.getAccY();
        double z = sensorSingleData.getAccZ();

        checkForPeakX(x);
        checkForPeakY(y);
        checkForPeakZ(z);

        PedometerData pedometerData = new PedometerData(
                sensorSingleData.getNumber(),
                sensorSingleData.getTimestamp(),
                peakX,
                peakY,
                peakZ);

        pedometerData.setBottomXupZ((!isUpPeakX && isUpPeakZ) ? Constants.BOTTOM_X_UP_Z : null);
        pedometerData.setUpXbottomZ((isUpPeakX && !isUpPeakZ) ? Constants.UP_X_BOTTOM_Z : null);
        pedometerData.setUpXupZ((isUpPeakX && isUpPeakZ) ? Constants.UP_X_UP_Z : null);
        pedometerData.setUpY(isUpPeakY ? Constants.PEAK_Y : null);
        pedometerData.setBottomY(!isUpPeakY ? Constants.PEAK_Y : null);

        pedometerData.setStep(stepDetector.process(isUpPeakX, isUpPeakY, isUpPeakZ, x) ? Constants.STEP : null);

        return pedometerData;
    }

    private void checkForPeakX(double x) {
        if (isUpPeakX) {
            if (x < lastX) {
                peakX = lastX;
                isUpPeakX = false;
            }
        } else {
            if (x > lastX) {
                peakX = lastX;
                isUpPeakX = true;
            }
        }

        lastX = x;
    }

    private void checkForPeakY(double y) {
        if (isUpPeakY) {
            if (y < lastY) {
                peakY = lastY;
                isUpPeakY = false;
            }
        } else {
            if (y > lastY) {
                peakY = lastY;
                isUpPeakY = true;
            }
        }

        lastY = y;
    }

    private void checkForPeakZ(double z) {
        if (isUpPeakZ) {
            if (z < lastZ) {
                peakZ = lastZ;
                isUpPeakZ = false;
            }
        } else {
            if (z > lastZ) {
                peakZ = lastZ;
                isUpPeakZ = true;
            }
        }

        lastZ = z;
    }

}
