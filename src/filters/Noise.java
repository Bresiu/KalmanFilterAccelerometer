package filters;

import constants.Constants;
import containers.SensorSingleData;

public class Noise {

	public Noise() {
	}

	public SensorSingleData filter(SensorSingleData sensorSingleData) {
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

		return sensorSingleData;
	}
}
