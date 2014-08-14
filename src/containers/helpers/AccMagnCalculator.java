package containers.helpers;

import containers.AccMagn;
import containers.SensorSingleData;

public class AccMagnCalculator {
	private long number;
	private long timestamp;

	private double accX;
	private double accY;
	private double accZ;


	public AccMagnCalculator(SensorSingleData sensorSingleData) {
		this.number = sensorSingleData.getNumber();
		this.timestamp = sensorSingleData.getTimestamp();

		this.accX = sensorSingleData.getAccX();
		this.accY = sensorSingleData.getAccY();
		this.accZ = sensorSingleData.getAccZ();
	}

	public AccMagn getLength() {
		double vectorLength = Math.sqrt(Math.pow(accX, 2) + Math.pow(accY, 2) + Math.pow(accZ, 2));
		return new AccMagn(number, timestamp, vectorLength);
	}
}
