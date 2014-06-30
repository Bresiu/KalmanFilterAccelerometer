public class SensorSingleData {
    private long timestamp;
    private double accX;
    private double accY;
    private double accZ;
    private double accV;

    public SensorSingleData(long timestamp, double accX, double accY, double accZ, double accV) {
        this.timestamp = timestamp;
        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
        this.accV = accV;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getAccX() {
        return accX;
    }

    public double getAccY() {
        return accY;
    }

    public double getAccZ() {
        return accZ;
    }

    public double getAccV() {
        return accV;
    }

    @Override
    public String toString() {
        return timestamp + " " + accX + " " + accY + " " + accZ + " " + accV;
    }
}
