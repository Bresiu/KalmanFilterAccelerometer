package filters;

import containers.SensorSingleData;

public class Filter {
	// TODO: change gravity variables to XYZVector.class object
	public SensorSingleData extractGravity(SensorSingleData sensorSingleData, double gravityX,
										   double gravityY, double gravityZ) {

		return sensorSingleData
				.setAccX(sensorSingleData.getAccX() - gravityX)
				.setAccY(sensorSingleData.getAccY() - gravityY)
				.setAccZ(sensorSingleData.getAccZ() - gravityZ);
	}
}
