package constants;

public class Constants {
    // Import delay in ms
    public static final long SLEEP_TIME = 10;

    // Complementary Filter
    public static final float FILTER_COEFFICIENT = 0.98f;

    // public static final float FILTER_COEFFICIENT = 0.5f;
    public static final float EPSILON = 0.000000001f;

    // Nanosecond to second
    public static final float NS2S = 1.0f / 1000000000.0f;

    // Gravity Force
    public static final double EARTH_GRAVITY = 9.80665;

    // Low-Pass Filter
    public static final float LOW_PASS_ALPHA = 0.1f;

    // High-Pass Filter
    public static final float HIGH_PASS_ALPHA = 0.9f;

    // Wikipedia filter
    public static final float WIKIPEDIA_ALPHA = 0.15f;

    // Mean Filter
    public static final int MEAN_FILTER_WINDOW = 10;

    // Noise delta error
    public static final double NOISE_DELTA_ERROR = 0.2;

    // Kalman Filter
    public static final double VARIANCE = 0.05;
    public static final double FILTER_GAIN = 0.8;
    public static final double KALMAN_DELTA_ERROR = 1.0;

    // Filenames
    // public static final String SENSOR_FILE_IMPORT = "log.dat";
    // public static final String SENSOR_FILE_IMPORT = "accGravCar.dat";
    public static final String SENSOR_FILE_IMPORT = "accGrav.dat";
    public static final String LINEAR_ACCELERATION_FILE_EXPORT = "linear_acceleration.dat";
}