package filters;

import factory.SensorSingleData;

public class BandPass {

    SensorSingleData sensorSingleData;

    LowPass lowPass;
    HighPass highPass;

    public BandPass() {
        initFilters();
    }

    private void initFilters() {
        lowPass = new LowPass();
        highPass = new HighPass();
    }

    public SensorSingleData filter(SensorSingleData sensorSingleData) {
        this.sensorSingleData = lowPass.filter(sensorSingleData);
        this.sensorSingleData = highPass.filter(this.sensorSingleData);
        return this.sensorSingleData;
    }
}
