package filters;

import factory.SensorSingleData;

public class Filter {
    public SensorSingleData extractGravity(SensorSingleData sensorSingleData, double gravityX,
                                           double gravityY, double gravityZ) {

        return sensorSingleData
                .setAccX(sensorSingleData.getAccX() - gravityX)
                .setAccY(sensorSingleData.getAccY() - gravityY)
                .setAccZ(sensorSingleData.getAccZ() - gravityZ);
    }
}
