package omari.hamza.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static void writeToFile(String filePath, String data) {
        File file = new File(filePath);
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String readFile(String filePath) {
        byte[] encoded;
        try {
            encoded = Files.readAllBytes(Paths.get(filePath));
            return new String(encoded);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
