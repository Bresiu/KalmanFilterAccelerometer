package constants;

public class Constants {
    // Gravity Force
    public static final double EARTH_GRAVITY = 9.80665;

    // Low-Pass Filter
    public static final float LOW_PASS_ALPHA = 0.2f;

    // High-Pass Filter
    public static final float HIGH_PASS_ALPHA = 0.8f;

    // Wikipedia filter
    public static final float WIKIPEDIA_ALPHA = 0.15f;

    // Kalman Filter
    public static final double VARIANCE = 0.05;
    public static final double FILTER_GAIN = 0.8;
    public static final double KALMAN_DELTA_ERROR = 1.0;

    // Mean Filter
    public static final int MEAN_FILTER_WINDOW = 5;

    // Noise delta error
    public static final double NOISE_DELTA_ERROR = 0.04;

    // Filenames
    public static final String LINEAR_ACCELERATION_FILE_EXPORT = "linear_acceleration.dat";
    // public static final String SENSOR_FILE_IMPORT = "log.dat";
    public static final String SENSOR_FILE_IMPORT = "accGrav2.dat";
    public static final String SENSOR_FILE_EXPORT = "new_log.dat";
}
