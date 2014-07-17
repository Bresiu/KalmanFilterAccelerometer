package search;

import containers.AccMagn;
import containers.GPSData;

import java.util.List;

public class AccFinder {

    public AccFinder() {
    }

    public List<GPSData> getAccIter(List<GPSData> gpss, List<AccMagn> magns) {

        // TODO: add boundary conditions (if arrays.size() < 2...)


        long accMagnPreTimestamp = magns.get(0).getTimestamp();
        int k = 0;

        for (int i = 0; i < gpss.size(); i++) {
            GPSData gpsData = gpss.get(i);
            long gpsTimestamp = gpsData.getTimestamp();

            if (k != magns.size() - 1) {
                for (int j = k; j < magns.size(); j++) {
                    AccMagn accMagnCurr = magns.get(j);
                    long accMagnCurrTimestamp = accMagnCurr.getTimestamp();

                    long diffPre = Math.abs(gpsTimestamp - accMagnPreTimestamp);
                    long diffCurr = Math.abs(accMagnCurrTimestamp - gpsTimestamp);

                    if (diffPre < diffCurr) {
                        gpsData.setAcc(magns.get(j - 1).getVectorLength());
                        System.out.println("i = " + i + " " + gpsData.toString());
                        k = j;
                        break;
                    } else {
                        accMagnPreTimestamp = accMagnCurrTimestamp;
                    }
                }
            } else {
                gpsData.setAcc(magns.get(magns.size() - 1).getVectorLength());
                System.out.println("i = " + i + " " + gpsData.toString());
            }
        }
        return gpss;
    }

    // fast search for one point
    public Double getAccBinary(long timestamp, List<AccMagn> magns) {
        long minTimestamp = magns.get(0).getTimestamp();
        long maxTimestamp = magns.get(magns.size() - 1).getTimestamp();
        int low = 0;
        int high = magns.size() - 1;

        if ((magns.size() == 0) || (timestamp < minTimestamp || timestamp > maxTimestamp)) {
            return null;
        }

        while (low < high) {
            int mid = (low + high) / 2;
            assert (mid < high);
            long d1 = Math.abs(magns.get(mid).getTimestamp() - timestamp);
            long d2 = Math.abs(magns.get(mid + 1).getTimestamp() - timestamp);
            if (d2 <= d1) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return magns.get(high).getVectorLength();
    }
}
