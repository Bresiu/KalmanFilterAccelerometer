package filters;

import constants.Constants;
import factory.SensorSingleData;

public class Wikipedia extends Filter {

    SensorSingleData sensorSingleData;

    double gravityWikipediaX;
    double gravityWikipediaY;
    double gravityWikipediaZ;

    public Wikipedia() {
        initObjects();
    }

    private void initObjects() {
        gravityWikipediaX = 0.0;
        gravityWikipediaY = 0.0;
        gravityWikipediaZ = 0.0;
    }

    public SensorSingleData filter(SensorSingleData sensorSingleData) {
        this.sensorSingleData = sensorSingleData;
        computeGravity();
        return extractGravity();
    }

    private void computeGravity() {
        // y[i] = y[i] + alpha * (x[i] - y[i])
        gravityWikipediaX = gravityWikipediaX + Constants.WIKIPEDIA_ALPHA * (sensorSingleData
                .getAccX() - gravityWikipediaX);
        gravityWikipediaY = gravityWikipediaY + Constants.WIKIPEDIA_ALPHA * (sensorSingleData
                .getAccY() - gravityWikipediaY);
        gravityWikipediaZ = gravityWikipediaZ + Constants.WIKIPEDIA_ALPHA * (sensorSingleData
                .getAccZ() - gravityWikipediaZ);
    }

    private SensorSingleData extractGravity() {
        return super.extractGravity(sensorSingleData, gravityWikipediaX, gravityWikipediaY, gravityWikipediaZ);
    }
}