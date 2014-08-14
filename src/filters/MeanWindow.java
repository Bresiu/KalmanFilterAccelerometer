package filters;

import containers.SensorSingleData;

import java.util.LinkedList;

public class MeanWindow {

	private int windowLength;

	private LinkedList<Double> accXList;
	private LinkedList<Double> accYList;
	private LinkedList<Double> accZList;

	private int counter;

	public MeanWindow(int windowLength) {
		this.windowLength = windowLength;

		initVariables();
	}

	private void initVariables() {
		accXList = new LinkedList<Double>();
		accYList = new LinkedList<Double>();
		accZList = new LinkedList<Double>();

		counter = 0;
	}

	public SensorSingleData filter(SensorSingleData sensorSingleData) {
		accXList.addFirst(sensorSingleData.getAccX());
		accYList.addFirst(sensorSingleData.getAccY());
		accZList.addFirst(sensorSingleData.getAccZ());

		counter++;

		return getMean(sensorSingleData);
	}

	private SensorSingleData getMean(SensorSingleData sensorSingleData) {
		double x = computeMean(accXList);
		double y = computeMean(accYList);
		double z = computeMean(accZList);

		if (counter == windowLength) {

			accXList.removeLast();
			accYList.removeLast();
			accZList.removeLast();

			counter--;
		}

		return sensorSingleData.setAccX(x).setAccY(y).setAccZ(z);
	}

	private double computeMean(LinkedList<Double> accList) {
		Double result = 0.0;

		for (Double value : accList) {
			result += value;
		}

		return result / counter;
	}
}
