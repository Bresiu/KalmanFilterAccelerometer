package io;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

public class Exporter {
    public void writeData(String name, String line) {
        line += "\n";
        try {
            Files.append(line, new File(name), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}