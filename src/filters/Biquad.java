package filters;

public class Biquad {
    public SecondOrderLowPass s;

    private float x1, x2;
    private float y1, y2;

    public Biquad(SecondOrderLowPass s) {
        this.s = s;
    }

    public float init(float x) {
        x1 = x2 = x;
        y1 = y2 = x;
        return x;
    }

    public float filter(float x) {
        float y = (x + x2) * s.a0 + x1 * s.a1 - y1 * s.b1 - y2 * s.b2;
        x2 = x1;
        y2 = y1;
        x1 = x;
        y1 = y;
        return y;
    }
}
