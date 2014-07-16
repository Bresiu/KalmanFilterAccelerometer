package helpers;

public enum  FixEnum {
    FIX_NO("NO FIX", 0),
    FIX_ES("ES FIX", 1),
    FIX_2D("2D FIX", 2),
    FIX_3D("3D FIX", 3);

    private String status;
    private int kalmanAlphaCoefficient;

    private FixEnum(String status, int kalmanAlphaCoefficient){
        this.status = status;
        this.kalmanAlphaCoefficient = kalmanAlphaCoefficient;
    }

    public String getStatus() {
        return status;
    }

    public int getKalmanAlphaCoefficient() {
        return kalmanAlphaCoefficient;
    }
}