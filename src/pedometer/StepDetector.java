package pedometer;

public class StepDetector {

	private int peakYcounter;
	private int bottomYcounter;

	private Double xValue;

	private boolean peakOk;
	private boolean bottomOk;
	private boolean previousX;
	private boolean previousY;

	private int phase = 0;


	// TODO move this to constants
	private static final int WINDOW_SIZE = 10;

	public StepDetector() {
		initVariables();
	}

	private void initVariables() {
		previousX = false;
		previousY = false;
		peakOk = false;
		bottomOk = false;
		peakYcounter = 0;
		bottomYcounter = 0;
	}

	public boolean process(boolean peakX, boolean peakY, boolean peakZ, Double xValue) {

        /*
		if (peakY) {
            if (bottomYcounter < WINDOW_SIZE) {
                bottomYcounter = 0;
                bottomOk = false;
            } else {
                bottomOk = true;
            }
            peakYcounter++;
        } else {
            if (peakYcounter < WINDOW_SIZE) {
                peakYcounter = 0;
                peakOk = false;
            } else {
                peakOk = true;
            }
            bottomYcounter++;
        }
*/
		if (bottomOk && peakY && !peakX && peakZ) {

		}
		if (peakX != previousX && peakY != previousY) {
			return true;
		}
		previousX = peakX;
		previousY = peakY;
        /*
            phase = 1;
        } else {
            if (phase == 1 && !peakX) {
                phase = 2;
                return true;
            }
        }
        */
		return false;
	}
}
