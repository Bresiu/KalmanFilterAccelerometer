package filters;

import constants.Constants;
import factory.SensorSingleData;

public class ButterWorth {

    private double mAccTime;
    private SecondOrderLowPass mLowPass;
    private Biquad mX;
    private Biquad mY;
    private Biquad mZ;

    public ButterWorth() {
        initObjects();
    }

    private void initObjects() {
        mAccTime = 0;

        mLowPass = new SecondOrderLowPass((float) (1f / Math.sqrt(2f)), 1f);
        mX = new Biquad(mLowPass);
        mY = new Biquad(mLowPass);
        mZ = new Biquad(mLowPass);
    }

    public SensorSingleData filter(SensorSingleData sensorSingleData) {
        float accX = (float) sensorSingleData.getAccX();
        float accY = (float) sensorSingleData.getAccY();
        float accZ = (float) sensorSingleData.getAccZ();
        long timestamp = sensorSingleData.getTimestamp();

        float x;
        float y;
        float z;

        final double now = timestamp * Constants.NS2S;
        if (mAccTime == 0) {
            x = mX.init(accX);
            y = mY.init(accY);
            z = mZ.init(accZ);
        } else {
            double dT = now - mAccTime;
            mLowPass.setSamplingPeriod(dT);
            x = mX.filter(accX);
            y = mY.filter(accY);
            z = mZ.filter(accZ);
        }
        mAccTime = now;

        sensorSingleData.setAccX(x);
        sensorSingleData.setAccY(y);
        sensorSingleData.setAccZ(z);

        return sensorSingleData;
    }
}