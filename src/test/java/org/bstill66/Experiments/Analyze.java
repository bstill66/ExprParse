package org.bstill66.Experiments;


import org.bstill66.Expression.PrvBaseListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Analyze {

    public class TestAnalyzer extends PrvBaseListener {

    }

    public static String getResourceFileAsString(String fileName) {
        // The class loader will look at the root of the classpath
        InputStream inputStream = Analyze.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }

        // Use try-with-resources to ensure the stream is closed
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {
            throw new RuntimeException("Error reading resource file: " + e.getMessage(), e);
        }
    }

    public static void main(String[] argv) {

        String fname = argv[0];
        String program = getResourceFileAsString(fname);



    }
}
