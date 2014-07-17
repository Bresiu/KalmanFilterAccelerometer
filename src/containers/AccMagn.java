package containers;

public class AccMagn {
    private long number;
    private long timestamp;
    private double vectorLength;

    public AccMagn() {
    }

    public AccMagn(long number, long timestamp, double vectorLength) {
        this.number = number;
        this.timestamp = timestamp;
        this.vectorLength = vectorLength;
    }

    public long getNumber() {
        return number;
    }

    public AccMagn setNumber(long number) {
        this.number = number;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public AccMagn setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public double getVectorLength() {
        return vectorLength;
    }

    public AccMagn setVectorLength(double vectorLength) {
        this.vectorLength = vectorLength;
        return this;
    }

    @Override
    public String toString() {
        return number + " " + timestamp + " " + vectorLength;
    }
}
