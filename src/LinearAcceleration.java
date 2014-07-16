import bus.BusProvider;
import com.google.common.eventbus.Subscribe;
import constants.Constants;
import factory.ResultantVector;
import factory.SensorSingleData;
import filters.*;
import io.Exporter;

public class LinearAcceleration {
    Exporter exporter;

    LowPass lowPass;
    HighPass highPass;
    Wikipedia wikipedia;
    BandPass bandPass;
    Noise noise;
    Mean mean;
    Mean finalMean;
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
        finalMean = new Mean();
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

        SensorSingleData meanSingleData = mean.filter(sensorSingleData);
        if (meanSingleData != null) {
            sensorSingleData = complementary.startProcess(meanSingleData);
            computeFinalMeanAndExport(sensorSingleData);
        }
    }

    private void computeFinalMeanAndExport(SensorSingleData sensorSingleData) {
        SensorSingleData meanSingleData = finalMean.filter(sensorSingleData);
        if (meanSingleData != null) {
            ResultantVector resultantVector = new ResultantVector(meanSingleData);
            exportNewData(Constants.MAGNITUDE_ACCELERATION_FILE_EXPORT, resultantVector.toString());
            exportNewData(Constants.LINEAR_ACCELERATION_FILE_EXPORT, meanSingleData.toString());
        }
    }

    private void registerBus() {
        BusProvider.getInstance().register(this);
    }

    private void exportNewData(String filename, String line) {
        exporter.writeData(filename, line);
    }
}