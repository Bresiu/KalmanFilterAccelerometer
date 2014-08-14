import factory.SensorDataFactory;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		// new AccToGpsFactory().produce();
		new LinearAcceleration();
		new SensorDataFactory();
	}
}