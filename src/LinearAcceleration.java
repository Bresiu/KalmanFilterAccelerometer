import bus.BusProvider;
import com.google.common.eventbus.Subscribe;
import constants.Constants;
import containers.AccMagn;
import containers.PedometerData;
import containers.SensorSingleData;
import containers.helpers.AccMagnCalculator;
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
    MeanWindow meanWindow;
    MeanWindow finalMeanWindow;
    Kalman kalman;
    ButterWorth butterWorth;
    Complementary complementary;
    Peak peak;

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
        meanWindow = new MeanWindow(Constants.MEAN_FILTER_WINDOW);
        finalMeanWindow = new MeanWindow(Constants.FINAL_MEAN_FILTER_WINDOW);
        kalman = new Kalman();
        butterWorth = new ButterWorth();
        complementary = new Complementary();
        peak = new Peak();
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
        
        sensorSingleData = meanWindow.filter(sensorSingleData);
        sensorSingleData = highPass.filter(sensorSingleData);
        sensorSingleData = lowPass.filter(sensorSingleData);
        sensorSingleData = finalMeanWindow.filter(sensorSingleData);

        PedometerData pedometerData = peak.filter(sensorSingleData);

        exportNewData(Constants.PEDOMETER, pedometerData.toString());

        /**
         * DONT TOUCH THIS!
         */
        /*
        sensorSingleData = highPass.filter(sensorSingleData);
        sensorSingleData = lowPass.filter(sensorSingleData);

        // exportNewData(Constants.LINEAR_ACCELERATION, sensorSingleData.toString());


        SensorSingleData meanSingleData = mean.filter(sensorSingleData);
        if (meanSingleData != null) {
            AccMagnCalculator accVector = new AccMagnCalculator(meanSingleData);
            AccMagn accMagn = accVector.getLength();

            exportNewData(Constants.MAGNITUDE_ACCELERATION, accMagn.toString());
            exportNewData(Constants.LINEAR_ACCELERATION, meanSingleData.toString());
            // computeFinalMeanAndExport(meanSingleData);
        }
        */
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