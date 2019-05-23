package omari.hamza.utils.compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class CompressionUtils {

    public static enum CompressionAlgorithms {

        RLE, HUFFMAN, ADAPTIVE_HUFFMAN, LZ77;


        @Override
        public String toString() {
            switch (this) {
                case RLE: {
                    return "RLE";
                }

                case ADAPTIVE_HUFFMAN: {
                    return "Adaptive Huffman";
                }

                case HUFFMAN: {
                    return "Huffman";
                }
            }

            return "";
        }
    }

    public static void compress(String inputFilePath, String outputFilePath, String compressionAlgorithm)
            throws FileNotFoundException {

        ArrayList<File> files = getFiles(inputFilePath);

        switch (compressionAlgorithm) {

            case "RLE": {
                if (files.size() == 1) {

                    RLE.compress(new FileInputStream(inputFilePath),
                            new FileOutputStream(outputFilePath));

                    break;

                } else {
                    for (File file : files) {
                        new File(file.getParent() + "/compressed").mkdirs();
                        RLE.compress(new FileInputStream(file), new FileOutputStream(file.getParent() + "/compressed/" + file.getName()));
                    }

                }
                break;
            }

            case "Huffman": {

                break;
            }

            case "Adaptive Huffman": {

                break;
            }
        }
    }


    public static void decompress(String inputFilePath, String outputFilePath, String compressionAlgorithm)
            throws FileNotFoundException {

        ArrayList<File> files = getFiles(inputFilePath);

        switch (compressionAlgorithm) {

            case "RLE": {
                if (files.size() == 1) {
                    RLE.decompress(new FileInputStream(inputFilePath),
                            new FileOutputStream(outputFilePath));
                    break;
                }
                for (File file : files) {
                    new File(files.get(0).getParent() + "/decompressed").mkdirs();
                    RLE.decompress(new FileInputStream(file), new FileOutputStream(file.getParent() + "/decompressed/" + file.getName()));
                }
                break;
            }

            case "Huffman": {

                break;
            }

            case "Adaptive Huffman": {

                break;
            }
        }
    }

    private static ArrayList<File> getFiles(String path) {
        File file = new File(path);
        ArrayList<File> files = new ArrayList<>();
        if (file.exists()) {
            if (file.isFile()) {
                files.add(new File(path));
            } else {
                flattenFolder(path, files);
                return files;
            }
        }
        return files;
    }

    private static void flattenFolder(String directoryPath, ArrayList<File> files) {
        File directory = new File(directoryPath);
        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    flattenFolder(file.getAbsolutePath(), files);
                }
            }
    }
}
