package io;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import constants.Constants;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Importer {
    List<String> sensorDataLines;

    public List<String> readData() {
        try {
            sensorDataLines = Files.readLines(new File(Constants.SENSOR_FILE_IMPORT), Charsets.UTF_8,
                    new LineProcessor<List<String>>() {
                        List<String> result = Lists.newArrayList();

                        public boolean processLine(String line) {
                            result.add(line.trim());
                            return true;
                        }

                        public List<String> getResult() {
                            return result;
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sensorDataLines;
    }
}

