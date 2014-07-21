package constants;

public class Constants {
    // Import delay in ms
    public static final long SLEEP_TIME = 10;

    // Complementary Filter
    public static final float FILTER_COEFFICIENT = 0.98f;

    // public static final float FILTER_COEFFICIENT = 0.5f;
    public static final float EPSILON = 0.000000001f;

    // Nanosecond to second
    public static final double NS2S = 1.0 / 1000000000.0;

    // Butterworth / Low Pass - Q
    public static final float Q = 0.7071f;

    // Gravity Force
    public static final double EARTH_GRAVITY = 9.80665;

    // Low-Pass Filter
    // lower value is -> output has smaller amplitude
    // lower value is -> output is smoother, but picks are less visible (eliminate noise)
    // public static final float LOW_PASS_ALPHA = 0.7f; // CAR -> 0.1f // walk -> 2f
    public static final float LOW_PASS_ALPHA = 0.1f; // CAR -> 0.1f // walk -> 2f

    // High-Pass Filter
    // higher value is -> long duration changes have smaller impact (values more likely to oscillate around "zero")
    public static final double HIGH_PASS_RC = 0.3f; // CAR -> 0.01f // walk -> 0.9f

    // Wikipedia filter
    public static final float WIKIPEDIA_ALPHA = 0.15f;

    // Mean Filter
    public static final int MEAN_FILTER_WINDOW = 10;
    public static final int FINAL_MEAN_FILTER_WINDOW = 10;

    // Noise delta error
    public static final double NOISE_DELTA_ERROR = 0.2;

    // Kalman Filter
    public static final double VARIANCE = 0.05;
    public static final double FILTER_GAIN = 0.8;

    // Peak Filter
    public static final double BOTTOM_X_UP_Z = 2.5;
    public static final double UP_X_BOTTOM_Z = 2.55;
    public static final double UP_X_UP_Z = 2.6;
    public static final double PEAK_Y = 0;
    public static final double STEP = -0.3;

    // Filenames
    // public static final String SENSOR_FILE = "log.dat";
    public static final String SENSOR_FILE = "accGravCar.dat";
    // public static final String SENSOR_FILE = "accGrav.dat";
    public static final String STEPS = "accGrav2.dat";
    public static final String PEDOMETER = "pdm.dat";
    public static final String PEAKS = "peaks.dat";
    public static final String LINEAR_ACCELERATION = "linear_acceleration.dat";
    public static final String MAGNITUDE_ACCELERATION = "magnitude_acceleration.dat";
    public static final String GPS_DATA = "gps_data.dat";
    public static final String NEW_GPS_DATA = "new_gps_data.dat";
}