public class Constants {
    // Gravity Force
    public static final double EARTH_GRAVITY = 9.80665;

    // Low-Pass Filter
    public static final boolean ALPHA_STATIC = false;
    public static final float TIME_CONSTANT = 0.18f;
    public static final float ALPHA = 0.9f;

    // Kalman Filter
    public static final double VARIANCE = 0.05;
    public static final double FILTER_GAIN = 0.8;
    public static final double DELTA_ERROR = 1.0;

    // Mean Filter
    public static final int MEAN_FILTER_WINDOW = 10;

    // Filenames
    public static final String MEAN_SENSOR_FILE_EXPORT = "mean.dat";
    public static final String SENSOR_FILE_IMPORT = "log.dat";
    public static final String SENSOR_FILE_EXPORT = "new_log.dat";
}
