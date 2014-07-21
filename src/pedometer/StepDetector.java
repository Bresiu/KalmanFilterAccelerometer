package pedometer;

public class StepDetector {

    private int peakYcounter;
    private int bottomYcounter;

    private boolean peakOk;
    private boolean bottomOk;

    private boolean step1;
    private boolean step2;
    private boolean step3;
    private boolean step4;
    private boolean step5;

    private int clockPhase;

    // TODO move this to constants
    private static final int WINDOW_SIZE = 10;

    public StepDetector() {
        initVariables();
    }

    private void initVariables() {
        // step1 = false;
        // step2 = false;
        // step3 = false;
        // step4 = false;
        // step5 = false;

        // clockPhase = 0;

        peakOk = false;
        bottomOk = false;
        peakYcounter = 0;
        bottomYcounter = 0;
    }

    public boolean process(boolean peakX, boolean peakY, boolean peakZ) {
        if (peakY) {
            System.out.println(true);
            if (bottomYcounter < WINDOW_SIZE) {
                System.out.println("bottom zero");
                bottomYcounter = 0;
                bottomOk = false;
            }
            peakYcounter++;
            System.out.println("up counter: " + peakYcounter);
            if (peakYcounter == WINDOW_SIZE) {
                peakOk = true;
            }
        } else {
            System.out.println(false);
            if(peakYcounter < WINDOW_SIZE) {
                System.out.println("up zero");
                peakYcounter = 0;
                peakOk = false;
            }
            bottomYcounter++;
            System.out.println("bottom counter: " + bottomYcounter);
            if (bottomYcounter == WINDOW_SIZE) {
                bottomOk = true;
            }
        }

        if (peakOk && bottomOk) {
            initVariables();
            System.out.println("OK OK");
            return true;
        }

        return false;
    }
}
