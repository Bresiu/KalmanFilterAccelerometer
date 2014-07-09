package filters;

import factory.SensorSingleData;

public class BandPassFilter {

    SensorSingleData sensorSingleData;

    public BandPassFilter(SensorSingleData sensorSingleData) {
        LowPassFilter lowPassFilter = new LowPassFilter();
        this.sensorSingleData = lowPassFilter.addNewSensorData(sensorSingleData);

        HighPassFilter highPassFilter = new HighPassFilter();
        this.sensorSingleData = highPassFilter.addNewSensorData(this.sensorSingleData);
    }

    public SensorSingleData makeFilteredData() {
        return this.sensorSingleData;
    }
}
