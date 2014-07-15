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

    LowPass lowPass;
    HighPass highPass;
    Wikipedia wikipedia;
    BandPass bandPass;
    Noise noise;
    Mean mean;
    Kalman kalman;
    ButterWorth butterWorth;
    Complementary complementary;

    public LinearAcceleration() {
        initObjects();
        registerBus();
    }

    private void initObjects() {
        exporter = new Exporter();
        lowPass = new LowPass();
        highPass = new HighPass();
        wikipedia = new Wikipedia();
        bandPass = new BandPass();
        noise = new Noise();
        mean = new Mean();
        kalman = new Kalman();
        butterWorth = new ButterWorth();
        complementary = new Complementary();
    }

    @Subscribe
    public void onSensorUpdate(SensorSingleData sensorSingleData) {
        filter(sensorSingleData);
    }

    private void filter(SensorSingleData sensorSingleData) {
        // sensorSingleData = kalman.filter(sensorSingleData);
        // sensorSingleData = bandPass.filter(sensorSingleData);
        // sensorSingleData = lowPass.filter(sensorSingleData);
        // sensorSingleData = highPass.filter(sensorSingleData);
        // sensorSingleData = wikipedia.filter(sensorSingleData);
        // sensorSingleData = noise.filter(sensorSingleData);

        /*
        SensorSingleData meanSingleData = mean.filter(sensorSingleData);
        if (meanSingleData != null) {
            meanSingleData = lowPass.filter(meanSingleData);
            exportNewSensorData(Constants.LINEAR_ACCELERATION_FILE_EXPORT, meanSingleData);
        }
        */
        // sensorSingleData = butterWorth.filter(sensorSingleData);

        complementary.startProcess(sensorSingleData);

        exportNewSensorData(Constants.LINEAR_ACCELERATION_FILE_EXPORT, sensorSingleData);
    }

    private void registerBus() {
        BusProvider.getInstance().register(this);
    }

    private void exportNewSensorData(String name, SensorSingleData sensorSingleData) {
        exporter.writeData(name, sensorSingleData.toString());
    }
}