package containers;

public class XYZVector {
    private double vectX;
    private double vectY;
    private double vectZ;

    public XYZVector() {
    }

    public XYZVector(SensorSingleData sensorSingleData) {
        this.vectX = sensorSingleData.getAccX();
        this.vectY = sensorSingleData.getAccY();
        this.vectZ = sensorSingleData.getAccZ();
    }

    public XYZVector(double vectX, double vectY, double vectZ) {
        this.vectX = vectX;
        this.vectY = vectY;
        this.vectZ = vectZ;
    }

    public double getVectX() {
        return vectX;
    }

    public void setVectX(double vectX) {
        this.vectX = vectX;
    }

    public double getVectY() {
        return vectY;
    }

    public void setVectY(double vectY) {
        this.vectY = vectY;
    }

    public double getVectZ() {
        return vectZ;
    }

    public void setVectZ(double vectZ) {
        this.vectZ = vectZ;
    }
}
