package filters;

import constants.Constants;
import factory.SensorSingleData;

public class NoiseFilter {

    SensorSingleData sensorSingleData;

    public NoiseFilter(SensorSingleData sensorSingleData) {
        this.sensorSingleData = sensorSingleData;
        extractNoise();
    }

    private void extractNoise() {
        if (sensorSingleData.getAccX() < Constants.NOISE_DELTA_ERROR &&
                sensorSingleData.getAccX() > -Constants.NOISE_DELTA_ERROR) {
            sensorSingleData.setAccX(0);
        }

        if (sensorSingleData.getAccY() < Constants.NOISE_DELTA_ERROR &&
                sensorSingleData.getAccY() > -Constants.NOISE_DELTA_ERROR) {
            sensorSingleData.setAccY(0);
        }

        if (sensorSingleData.getAccZ() < Constants.NOISE_DELTA_ERROR &&
                sensorSingleData.getAccZ() > -Constants.NOISE_DELTA_ERROR) {
            sensorSingleData.setAccZ(0);
        }
    }

    public SensorSingleData makeFilteredData() {
        return sensorSingleData;
    }
}
