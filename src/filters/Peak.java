package filters;

import containers.SensorSingleData;

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
    }

    public SensorSingleData filter(SensorSingleData sensorSingleData) {

        double x = sensorSingleData.getAccX();
        double y = sensorSingleData.getAccY();
        double z = sensorSingleData.getAccZ();

        // peakX = checkForPeak(isUpPeakX, x, lastX);
        // peakY = checkForPeak(isUpPeakY, y, lastY);
        // peakZ = checkForPeak(isUpPeakZ, z, lastZ);


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

        return sensorSingleData.setAccX(peakX).setAccY(peakY).setAccZ(peakZ);
    }

    private double checkForPeak(boolean isUpPeak, double value, double lastValue) {

        if (isUpPeak) {
            if (value < lastValue) {
                isUpPeak = false;
                return lastValue;
            }
        } else {
            if (value > lastValue) {
                isUpPeak = true;
                return lastValue;
            }
        }
        return lastValue;
    }


}
