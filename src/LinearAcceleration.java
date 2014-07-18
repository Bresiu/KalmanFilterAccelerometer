import bus.BusProvider;
import com.google.common.eventbus.Subscribe;
import constants.Constants;
import containers.AccMagn;
import containers.helpers.AccMagnCalculator;
import containers.SensorSingleData;
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
        mean = new Mean(Constants.MEAN_FILTER_WINDOW);
        finalMean = new Mean(Constants.FINAL_MEAN_FILTER_WINDOW);
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
        // sensorSingleData = mean.filter(sensorSingleData);
        // sensorSingleData = bandPass.filter(sensorSingleData);
        // sensorSingleData = lowPass.filter(sensorSingleData);
        // sensorSingleData = highPass.filter(sensorSingleData);
        // sensorSingleData = wikipedia.filter(sensorSingleData);
        // sensorSingleData = noise.filter(sensorSingleData);


        SensorSingleData meanSingleData = mean.filter(sensorSingleData);
        if (meanSingleData != null) {
            sensorSingleData = bandPass.filter(meanSingleData);
            computeFinalMeanAndExport(sensorSingleData);
        }

    }

    private void computeFinalMeanAndExport(SensorSingleData sensorSingleData) {
        SensorSingleData meanSingleData = finalMean.filter(sensorSingleData);
        if (meanSingleData != null) {

            AccMagnCalculator accVector = new AccMagnCalculator(meanSingleData);
            AccMagn accMagn = accVector.getLength();

            exportNewData(Constants.LINEAR_ACCELERATION, meanSingleData.toString());
            exportNewData(Constants.MAGNITUDE_ACCELERATION, accMagn.toString());
        }
    }

    private void registerBus() {
        BusProvider.getInstance().register(this);
    }

    private void exportNewData(String filename, String line) {
        exporter.writeData(filename, line);
    }
}