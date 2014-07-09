package filters;

import factory.SensorSingleData;

public class Filter {
    public SensorSingleData extractGravity(SensorSingleData sensorSingleData, double gravityX,
                                           double gravityY, double gravityZ) {

        sensorSingleData.setAccX(sensorSingleData.getAccX() - gravityX);
        sensorSingleData.setAccY(sensorSingleData.getAccY() - gravityY);
        sensorSingleData.setAccZ(sensorSingleData.getAccZ() - gravityZ);

        return sensorSingleData;
    }
}
