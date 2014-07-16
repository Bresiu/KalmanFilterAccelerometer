package factory;

public class ResultantVector {
    private long number;
    private long timestamp;

    private double accX;
    private double accY;
    private double accZ;

    private double vectorLength;

    public ResultantVector(SensorSingleData sensorSingleData) {
        this.number = sensorSingleData.getNumber();
        this.timestamp = sensorSingleData.getTimestamp();

        this.accX = sensorSingleData.getAccX();
        this.accY = sensorSingleData.getAccY();
        this.accZ = sensorSingleData.getAccZ();
    }

    private void computeLength() {
        this.vectorLength = Math.sqrt(Math.pow(accX, 2) + Math.pow(accY, 2) + Math.pow(accZ, 2));
    }

    @Override
    public String toString() {
        computeLength();
        return number + " " + timestamp + " " + vectorLength;
    }
}
