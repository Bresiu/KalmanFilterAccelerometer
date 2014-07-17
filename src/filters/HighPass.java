package filters;

import constants.Constants;
import containers.SensorSingleData;

public class HighPass extends Filter {

    SensorSingleData sensorSingleData;

    double gravityHighPassX;
    double gravityHighPassY;
    double gravityHighPassZ;

    public HighPass() {
        initObjects();
    }

    private void initObjects() {
        gravityHighPassX = 0.0;
        gravityHighPassY = 0.0;
        gravityHighPassZ = 0.0;
    }

    public SensorSingleData filter(SensorSingleData sensorSingleData) {
        this.sensorSingleData = sensorSingleData;
        computeGravity();
        return extractGravity();
    }

    private void computeGravity() {
        gravityHighPassX = Constants.HIGH_PASS_ALPHA * gravityHighPassX + (1.0f - Constants.HIGH_PASS_ALPHA)
                * sensorSingleData.getAccX();
        gravityHighPassY = Constants.HIGH_PASS_ALPHA * gravityHighPassY + (1.0f - Constants.HIGH_PASS_ALPHA)
                * sensorSingleData.getAccY();
        gravityHighPassZ = Constants.HIGH_PASS_ALPHA * gravityHighPassZ + (1.0f - Constants.HIGH_PASS_ALPHA)
                * sensorSingleData.getAccZ();
    }

    private SensorSingleData extractGravity() {
        return super.extractGravity(sensorSingleData, gravityHighPassX, gravityHighPassY, gravityHighPassZ);
    }
}
