package filters;

public class SecondOrderLowPass {
	public float iQ, fc;
	public float K, iD;
	public float a0, a1;
	public float b1, b2;

	// Butterworth filter because the chosen value of Q
	// Fc = filter corner frequency
	// Q = fs
	public SecondOrderLowPass(float Q, float fc) {
		// coefficients
		this.iQ = 1.0f / Q;
		this.fc = fc;
	}

	public void setSamplingPeriod(double dT) {
		// K = tan(pi * Fc/Fs)
		K = (float) Math.tan(Math.PI * fc * dT);
		// frequency-warped // "s" from 2nd order LPF
		iD = 1.0f / (K * K + K * iQ + 1);
		// a0 -> (k^2) / (K * K + (K / Q) + 1)
		a0 = K * K * iD;
		// a1 -> 2 * a0
		a1 = 2.0f * a0;
		// b1 -> (2 * (K^2 - 1)) / k^2 + (K / Q) + 1)
		b1 = 2.0f * (K * K - 1) * iD;
		// b2
		b2 = (K * K - K * iQ + 1) * iD;
	}
}
