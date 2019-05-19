package omari.hamza.utils.compression;

import omari.hamza.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Scanner;

public class RunLengthEncoding {

   /* public static String encodeString(String data) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            int counter = 1;
            while (i < data.length() - 1 && (data.charAt(i) == data.charAt(i + 1))) {
                counter++;
                i++;
            }
            result.append(data.charAt(i));
            result.append(counter);
        }
        return result.toString();
    }

    public static String decodeString(String data) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            result.append(String.valueOf(data.charAt(i)).repeat(Integer.valueOf(data.charAt(i + 1) + "")));
            i += 1;
        }
        return result.toString();
    }

    public static void compressFile(String filePath){
        try {
            File file = new File(filePath);
            byte[] encodedBytes = Base64.getEncoder().encode(Files.readAllBytes(Path.of(file.toURI())));
            String base64String = FileUtils.readFile(filePath);
            //base64String -> RLEString
            String encodedString = encodeString(base64String);
            FileUtils.writeToFile(file.getParent() + "/compressed.txt", encodedString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
