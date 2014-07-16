package filters;

import constants.Constants;
import factory.XYZVector;

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

        // Butterworth filter because the chosen value of Q
        // When calculating the coefficients, Q is the filter Q value
        // (for a Butterworth lowpass, where the passband is flat with no corner peaking,
        // use 0.7071, which is the reciprocal of the square root of two)
        //(float) (1f / Math.sqrt(2f)), 1f);
        mLowPass = new SecondOrderLowPass(Constants.Q, 1f);

        mX = new Biquad(mLowPass);
        mY = new Biquad(mLowPass);
        mZ = new Biquad(mLowPass);
    }

    public XYZVector filter(XYZVector vector, long timestamp) {
        float accX = (float) vector.getVectX();
        float accY = (float) vector.getVectY();
        float accZ = (float) vector.getVectZ();

        float x;
        float y;
        float z;

        double now = timestamp * Constants.NS2S;
        if (mAccTime == 0) {
            System.out.println("ACC = 0");
            x = mX.init(accX);
            y = mY.init(accY);
            z = mZ.init(accZ);
        } else {
            double dT = now - mAccTime;
            // System.out.println("dT: " + dT);
            mLowPass.setSamplingPeriod(dT);
            x = mX.filter(accX);
            y = mY.filter(accY);
            z = mZ.filter(accZ);
        }

        mAccTime = now;

        return new XYZVector(x, y, z);
    }
}