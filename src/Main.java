import factory.SensorDataFactory;
import filters.Complementary;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //new LinearAcceleration();
        new Complementary();
        new SensorDataFactory();
    }
}