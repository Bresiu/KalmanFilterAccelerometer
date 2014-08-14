package filters;

import containers.SensorSingleData;

public class BandPass {

	SensorSingleData sensorSingleData;

	LowPass lowPass;
	HighPass highPass;

	public BandPass() {
		initFilters();
	}

	private void initFilters() {
		highPass = new HighPass();
		lowPass = new LowPass();
	}

	public SensorSingleData filter(SensorSingleData sensorSingleData) {
		this.sensorSingleData = sensorSingleData;
		this.sensorSingleData = highPass.filter(this.sensorSingleData);
		this.sensorSingleData = lowPass.filter(this.sensorSingleData);
		return this.sensorSingleData;
	}
}
