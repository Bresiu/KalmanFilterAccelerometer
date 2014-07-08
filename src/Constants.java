public class Constants {
    // Gravity Force
    public static final double EARTH_GRAVITY = 9.80665;

    // Low-Pass Filter
    public static final float LOW_PASS_ALPHA = 0.8f;
    public static final double LOW_PASS_DELTA_ERROR = 0.02;

    // Low-Pass Filter
    public static final float HIGH_PASS_ALPHA = 0.8f;
    public static final double HIGH_PASS_DELTA_ERROR = 0.02;

    // Wikipedia filter
    public static final float WIKIPEDIA_ALPHA = 0.15f;

    // Kalman Filter
    public static final double VARIANCE = 0.05;
    public static final double FILTER_GAIN = 0.8;
    public static final double KALMAN_DELTA_ERROR = 1.0;

    // Mean Filter
    public static final int MEAN_FILTER_WINDOW = 10;

    // Filenames
    public static final String LINEAR_ACCELERATION_FILE_EXPORT = "linear_acceleration.dat";
    public static final String LINEAR_ACCELERATION_FILE_EXPORT_2 = "linear_acceleration2.dat";
    public static final String MEAN_SENSOR_FILE_EXPORT = "mean.dat";
    public static final String SENSOR_FILE_IMPORT = "log.dat";
    public static final String SENSOR_FILE_EXPORT = "new_log.dat";
}
