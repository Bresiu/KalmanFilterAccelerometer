package factory;

import helpers.FixEnum;

public class GPSData {
    private long timestamp;
    private long longitude;
    private long latitude;
    private long altitude;
    private int HDOP;
    private double speed;
    private double angle;
    private
    FixEnum fixEnum;

    public GPSData() {
    }

    public GPSData(int HDOP, long longitude, long latitude, FixEnum fixEnum) {
        this.HDOP = HDOP;
        this.longitude = longitude;
        this.latitude = latitude;
        this.fixEnum = fixEnum;
    }

    public String getFixStatus() {
        return fixEnum.getStatus();
    }

    public int getFixKalmanAlphaCoefficient() {
        return fixEnum.getKalmanAlphaCoefficient();
    }



}
