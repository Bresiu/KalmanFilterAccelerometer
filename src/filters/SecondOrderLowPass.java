package filters;

public class SecondOrderLowPass {
    public float iQ, fc;
    public float K, iD;
    public float a0, a1;
    public float b1, b2;

    public SecondOrderLowPass(float Q, float fc) {
        this.iQ = 1.0f / Q;
        this.fc = fc;
    }

    public void setSamplingPeriod(double dT) {
        K = (float) Math.tan(Math.PI * fc * dT);
        iD = 1.0f / (K * K + K * iQ + 1);
        a0 = K * K * iD;
        a1 = 2.0f * a0;
        b1 = 2.0f * (K * K - 1) * iD;
        b2 = (K * K - K * iQ + 1) * iD;
    }
}
