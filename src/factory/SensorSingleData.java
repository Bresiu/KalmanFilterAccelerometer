package factory;

public class SensorSingleData {
    private long number;
    private long dt;

    private double accX;
    private double accY;
    private double accZ;
    private double accV;

    public SensorSingleData() {
    }

    public SensorSingleData(long number, long dt, double accX, double accY, double accZ) {
        this.number = number;
        this.dt = dt;
        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
        this.accV = setV();
    }

    private double setV() {
        return Math.sqrt(Math.pow(this.accX, 2) + Math.pow(this.accY, 2) + Math.pow(this.accZ, 2));
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public double getAccX() {
        return accX;
    }

    public void setAccX(double accX) {
        this.accX = accX;
    }

    public double getAccY() {
        return accY;
    }

    public void setAccY(double accY) {
        this.accY = accY;
    }

    public double getAccZ() {
        return accZ;
    }

    public void setAccZ(double accZ) {
        this.accZ = accZ;
    }

    public double getAccV() {
        return accV;
    }

    public void setAccV(double accV) {
        this.accV = accV;
    }

    @Override
    public String toString() {
        return number + " " + dt + " " + accX + " " + accY + " " + accZ + " " + accV;
    }


}
