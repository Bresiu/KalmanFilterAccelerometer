package containers;

public class PedometerData {
    private long number;
    private long timestamp;

    private double accX;
    private double accY;
    private double accZ;

    private Double bottomXupZ;
    private Double upXbottomZ;
    private Double upXupZ;
    private Double upY;
    private Double bottomY;
    private Double step;

    public PedometerData() {
    }

    public PedometerData(long number, long timestamp, double accX, double accY, double accZ) {
        this.number = number;
        this.timestamp = timestamp;

        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
    }

    public long getNumber() {
        return number;
    }

    public PedometerData setNumber(long number) {
        this.number = number;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public PedometerData setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public double getAccX() {
        return accX;
    }

    public PedometerData setAccX(double accX) {
        this.accX = accX;
        return this;
    }

    public double getAccY() {
        return accY;
    }

    public PedometerData setAccY(double accY) {
        this.accY = accY;
        return this;
    }

    public double getAccZ() {
        return accZ;
    }

    public PedometerData setAccZ(double accZ) {
        this.accZ = accZ;
        return this;
    }

    public double getBottomXupZ() {
        return bottomXupZ;
    }

    public PedometerData setBottomXupZ(Double bottomXupZ) {
        this.bottomXupZ = bottomXupZ;
        return this;
    }

    public double getUpXbottomZ() {
        return upXbottomZ;
    }

    public PedometerData setUpXbottomZ(Double upXbottomZ) {
        this.upXbottomZ = upXbottomZ;
        return this;
    }

    public double getUpXupZ() {
        return upXupZ;
    }

    public PedometerData setUpXupZ(Double upXupZ) {
        this.upXupZ = upXupZ;
        return this;
    }

    public Double getUpY() {
        return upY;
    }

    public PedometerData setUpY(Double upY) {
        this.upY = upY;
        return this;
    }

    public Double getBottomY() {
        return bottomY;
    }

    public PedometerData setBottomY(Double bottomY) {
        this.bottomY = bottomY;
        return this;
    }

    public Double getStep() {
        return step;
    }

    public PedometerData setStep(Double step) {
        this.step = step;
        return this;
    }

    @Override
    public String toString() {
        return number + " " +
                timestamp + " " +
                accX + " " +
                accY + " " +
                accZ + " " +
                bottomXupZ + " " +
                upXbottomZ + " " +
                upXupZ + " " +
                upY + " " +
                bottomY + " " +
                step;
    }
}
