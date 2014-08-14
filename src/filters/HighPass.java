package filters;

import constants.Constants;
import containers.SensorSingleData;

public class HighPass extends Filter {

	private SensorSingleData sensorSingleData;

	private double preTimestamp;

	private double gravityHighPassX;
	private double gravityHighPassY;
	private double gravityHighPassZ;

	public HighPass() {
		initObjects();
	}

	private void initObjects() {
		preTimestamp = 0.0;

		gravityHighPassX = 0.0;
		gravityHighPassY = 0.0;
		gravityHighPassZ = 0.0;
	}

	public SensorSingleData filter(SensorSingleData sensorSingleData) {
		this.sensorSingleData = sensorSingleData;
		computeGravity();
		return extractGravity();
	}

	private void computeGravity() {
		if (preTimestamp == 0.0) {
			preTimestamp = sensorSingleData.getTimestamp();
		}
		double dt = (sensorSingleData.getTimestamp() - preTimestamp);
		double alpha = dt / (Constants.HIGH_PASS_RC + dt);
		preTimestamp = sensorSingleData.getTimestamp();

		gravityHighPassX = alpha * gravityHighPassX + (1.0f - alpha) * sensorSingleData.getAccX();
		gravityHighPassY = alpha * gravityHighPassY + (1.0f - alpha) * sensorSingleData.getAccY();
		gravityHighPassZ = alpha * gravityHighPassZ + (1.0f - alpha) * sensorSingleData.getAccZ();
	}

	private SensorSingleData extractGravity() {
		return super.extractGravity(sensorSingleData, gravityHighPassX, gravityHighPassY, gravityHighPassZ);
	}
}