package pedometer;

import containers.SensorSingleData;

public class stepDetector {
    private int stepCounter;

    public stepDetector() {
        initVariables();
    }

    private void initVariables() {
        stepCounter = 0;
    }

    public void process(SensorSingleData sensorSingleData) {

    }

    private boolean isPick(double previous, double current) {
        if (previous < current) {
        }
        return true;
    }
}
