package factory;

import constants.Constants;
import containers.AccMagn;
import io.Importer;

import java.util.ArrayList;
import java.util.List;

public class AccDataFactory {

	private Importer importer;
	private List<AccMagn> accMagns;


	public AccDataFactory() {
		importer = new Importer();
		accMagns = new ArrayList<AccMagn>();
	}

	public List<AccMagn> getAccMagnsFromFile() {
		List<String> accDataLines = importer.readData(Constants.MAGNITUDE_ACCELERATION);

		for (String sensorLine : accDataLines) {
			accMagns.add(proccessLine(sensorLine));
		}
		return accMagns;
	}

	private AccMagn proccessLine(String sensorLine) {
		String[] sensorParts = sensorLine.split(" ");

		return new AccMagn(
				Long.valueOf(sensorParts[0]),
				Long.valueOf(sensorParts[1]),
				Double.valueOf(sensorParts[2]));
	}
}
