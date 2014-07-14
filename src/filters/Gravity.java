package filters;

import constants.Constants;

public class Gravity {
    double mAccTime;
    // SecondOrderLowPassFilter mLowPass;
    // BiquadFilter mX, mY, mZ;

    // SecondOrderLowPassFilter
    float iQ, fc;
    float K, iD;
    float a0, a1;
    float b1, b2;

    // BiquadFilter
    float x1, x2;
    float y1, y2;


    public Gravity() {
    }

    public float[] filter(float[] accelerometer, long timestamp) {
        float x, y, z;
        double now = timestamp * Constants.NS2S;
        if (mAccTime == 0) {
            x = biquadInit(accelerometer[0]);
            y = biquadInit(accelerometer[1]);
            z = biquadInit(accelerometer[2]);
        } else {
            double dT = now - mAccTime;
            setSamplingPeriod(dT);
            x = 0; // mX(accelerometer[0]);
            y = 0; // mY(accelerometer[1]);
            z = 0; // mZ(accelerometer[2]);
        }
        mAccTime = now;

        return new float[]{x, y, z};
    }

    /**
     * SECOND ORDER LOW PASS FILTER
     */
    public void secondOrderLowPassFilter(float Q, float fc) {

    }

    public void setSamplingPeriod(double dT) {
        K = (float) Math.tanh(Math.PI * fc * dT);
        iD = 1.0f / (K * K + K * iQ + 1);
        a0 = K * K * iD;
        a1 = 2.0f * a0;
        b1 = 2.0f * (K * K - 1) * iD;
        b2 = (K * K - K * iQ + 1) * iD;
    }

    /**
     * BIQUAD FILTER
     */

    public float biquadInit(float x) {
        x1 = x2 = x;
        y1 = y2 = x;
        return x;
    }


    public float biquadOperator(float x) {
        float y = (x + x2) * a0 + x1 * a1 - y1 * b1 - y2 * b2;
        x2 = x1;
        y2 = y1;
        x1 = x;
        y1 = y;
        return y;
    }
}