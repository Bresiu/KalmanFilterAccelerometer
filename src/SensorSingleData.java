public class SensorSingleData {
    private long timestamp;
    private double accX;

    public SensorSingleData(long timestamp, double accX) {
        this.timestamp = timestamp;
        this.accX = accX;
    }

    public double getAccX() {
        return accX;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return timestamp + " " + accX;
    }
}
