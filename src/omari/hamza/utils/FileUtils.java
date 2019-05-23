package omari.hamza.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    public static void writeToLogFile(String log){
        File file = new File(System.getProperty("user.home") + "/Desktop/log.txt");
        if (!file.exists()) {
            try {
                //noinspection all
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Files.write(Paths.get(file.toURI()), log.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
