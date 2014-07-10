import bus.BusProvider;
import com.google.common.eventbus.Subscribe;
import constants.Constants;
import factory.SensorSingleData;
import filters.*;
import io.Exporter;

// TODO: alpha in Low-Pass filter as: t / (t + dT), where t, the low-pass filter's time-constant
// TODO: and dT, the event delivery rate

public class LinearAcceleration {
    Exporter exporter;

    Filter filter;
    LowPass lowPassFilter;
    HighPass highPassFilter;
    WikipediaFilter wikipediaFilter;
    BandPass bandPassFilter;
    NoiseFilter noiseFilter;
    MeanFilter meanFilter;
    Kalman kalmanFilter;

    public LinearAcceleration() {
        initObjects();
        registerBus();
    }

    private void initObjects() {
        exporter = new Exporter();
        bandPassFilter = new BandPass();
        noiseFilter = new NoiseFilter();
        meanFilter = new MeanFilter();
    }

    @Subscribe
    public void onSensorUpdate(SensorSingleData sensorSingleData) {
        filter(sensorSingleData);
    }

    private void filter(SensorSingleData sensorSingleData) {
        sensorSingleData = bandPassFilter.filter(sensorSingleData);
        sensorSingleData = noiseFilter.filter(sensorSingleData);

        SensorSingleData meanSingleData = meanFilter.filter(sensorSingleData);
        if (meanSingleData != null) {
            sensorSingleData = meanSingleData;
        }

        exportNewSensorData(Constants.LINEAR_ACCELERATION_FILE_EXPORT, sensorSingleData);
    }

    private void registerBus() {
        BusProvider.getInstance().register(this);
    }

    private void exportNewSensorData(String name, SensorSingleData sensorSingleData) {
        exporter.writeData(name, sensorSingleData.toString());
    }
}