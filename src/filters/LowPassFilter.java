package filters;

import constants.Constants;
import factory.SensorSingleData;

public class LowPassFilter {

    SensorSingleData sensorSingleData;

    double filteredValueX;
    double filteredValueY;
    double filteredValueZ;

    public LowPassFilter() {
        filteredValueX = 0.0;
        filteredValueY = 0.0;
        filteredValueZ = 0.0;
    }

    public SensorSingleData addNewSensorData(SensorSingleData sensorSingleData) {
        this.sensorSingleData = sensorSingleData;
        filterValues();
        return makeFilteredData();
    }

    private void filterValues() {
        filteredValueX = sensorSingleData.getAccX() * Constants.LOW_PASS_ALPHA + filteredValueX * (1.0f - Constants
                .LOW_PASS_ALPHA);
        filteredValueY = sensorSingleData.getAccY() * Constants.LOW_PASS_ALPHA + filteredValueY * (1.0f - Constants
                .LOW_PASS_ALPHA);
        filteredValueZ = sensorSingleData.getAccZ() * Constants.LOW_PASS_ALPHA + filteredValueZ * (1.0f - Constants
                .LOW_PASS_ALPHA);
    }

    private SensorSingleData makeFilteredData() {
        sensorSingleData.setAccX(filteredValueX);
        sensorSingleData.setAccY(filteredValueY);
        sensorSingleData.setAccZ(filteredValueZ);
        return sensorSingleData;
    }
}