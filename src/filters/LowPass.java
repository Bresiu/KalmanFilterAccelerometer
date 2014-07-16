package filters;

import constants.Constants;
import factory.SensorSingleData;

public class LowPass {

    SensorSingleData sensorSingleData;

    double filteredValueX;
    double filteredValueY;
    double filteredValueZ;

    public LowPass() {
        initObjects();
    }

    private void initObjects() {
        filteredValueX = 0.0;
        filteredValueY = 0.0;
        filteredValueZ = 0.0;
    }

    public SensorSingleData filter(SensorSingleData sensorSingleData) {
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
        return sensorSingleData
                .setAccX(filteredValueX)
                .setAccY(filteredValueY)
                .setAccZ(filteredValueZ);
    }
}