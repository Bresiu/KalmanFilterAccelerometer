package containers;

public class SensorSingleData {
    private long number;
    private long timestamp;

    private double accX;
    private double accY;
    private double accZ;

    private double gyroX;
    private double gyroY;
    private double gyroZ;

    private double magnX;
    private double magnY;
    private double magnZ;

    public SensorSingleData() {
    }

    public SensorSingleData(long number, long timestamp, double accX, double accY, double accZ, double gyroX,
                            double gyroY, double gyroZ, double magnX, double magnY, double magnZ) {
        this.number = number;
        this.timestamp = timestamp;
        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
        this.gyroX = gyroX;
        this.gyroY = gyroY;
        this.gyroZ = gyroZ;
        this.magnX = magnX;
        this.magnY = magnY;
        this.magnZ = magnZ;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getAccX() {
        return accX;
    }

    public SensorSingleData setAccX(double accX) {
        this.accX = accX;
        return this;
    }

    public double getAccY() {
        return accY;
    }

    public SensorSingleData setAccY(double accY) {
        this.accY = accY;
        return this;
    }

    public double getAccZ() {
        return accZ;
    }

    public SensorSingleData setAccZ(double accZ) {
        this.accZ = accZ;
        return this;
    }

    public double getGyroX() {
        return gyroX;
    }

    public SensorSingleData setGyroX(double gyroX) {
        this.gyroX = gyroX;
        return this;
    }

    public double getGyroY() {
        return gyroY;
    }

    public SensorSingleData setGyroY(double gyroY) {
        this.gyroY = gyroY;
        return this;
    }

    public double getGyroZ() {
        return gyroZ;
    }

    public SensorSingleData setGyroZ(double gyroZ) {
        this.gyroZ = gyroZ;
        return this;
    }

    public double getMagnX() {
        return magnX;
    }

    public SensorSingleData setMagnX(double magnX) {
        this.magnX = magnX;
        return this;
    }

    public double getMagnY() {
        return magnY;
    }

    public SensorSingleData setMagnY(double magnY) {
        this.magnY = magnY;
        return this;
    }

    public double getMagnZ() {
        return magnZ;
    }

    public SensorSingleData setMagnZ(double magnZ) {
        this.magnZ = magnZ;
        return this;
    }

    @Override
    public String toString() {
        return number + " " + timestamp + " " + accX + " " + accY + " " + accZ + " " + gyroX + " " + gyroY + " " +
                gyroZ + " " + magnX + " " + magnY + " " + magnZ;
    }
}
