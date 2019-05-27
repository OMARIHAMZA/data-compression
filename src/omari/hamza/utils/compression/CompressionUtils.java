package omari.hamza.utils.compression;

import omari.hamza.utils.FileUtils;
import omari.hamza.utils.compression.adaptive.huffman.AdaptiveHuffmanDecoder;
import omari.hamza.utils.compression.adaptive.huffman.AdaptiveHuffmanEncoder;
import omari.hamza.utils.compression.aithmetic_coding.*;
import omari.hamza.utils.compression.huffman.Huffman;
import omari.hamza.utils.compression.lzw.LZW;

import java.io.*;
import java.util.ArrayList;

import static omari.hamza.utils.compression.aithmetic_coding.ArithmeticCompress.getFrequencies;
import static omari.hamza.utils.compression.aithmetic_coding.ArithmeticCompress.writeFrequencies;
import static omari.hamza.utils.compression.aithmetic_coding.ArithmeticDecompress.readFrequencies;

public class CompressionUtils {

    public static void compress(String inputFilePath, String outputFilePath, String compressionAlgorithm)
            throws IOException {

        ArrayList<File> files = getFiles(inputFilePath);

        switch (compressionAlgorithm) {

            case "RLE": {
                if (files.size() == 1) {

                    RLE.compress(new FileInputStream(inputFilePath),
                            new FileOutputStream(outputFilePath));

                    calculateCompressionRatio(inputFilePath, outputFilePath);

                    break;

                } else {
                    for (File file : files) {
                        new File(file.getParent() + "/compressed").mkdirs();
                        String outputFileLocation = file.getParent() + "/compressed/" + file.getName();
                        RLE.compress(new FileInputStream(file), new FileOutputStream(outputFileLocation));
                        calculateCompressionRatio(file.getPath(), outputFileLocation);
                    }

                }
                break;
            }

            case "Huffman": {
                if (files.size() == 1) {
                    Huffman.compress(inputFilePath, outputFilePath);
                } else {
                    for (File file : files) {
                        new File(file.getParent() + "/decompressed").mkdirs();
                        Huffman.compress(file.getPath(), file.getParent() + "/decompressed/" + file.getName());
                    }
                }
                break;
            }

            case "Adaptive Huffman": {
                adaptiveEncode(inputFilePath, outputFilePath, "8");
                break;
            }

            case "Arithmetic Coding": {

                if (files.size() == 1) {

                    arithmeticCodingCompress(inputFilePath, outputFilePath);

                    calculateCompressionRatio(inputFilePath, outputFilePath);

                    break;

                } else {
                    for (File file : files) {
                        new File(file.getParent() + "/compressed").mkdirs();
                        String outputFileLocation = file.getParent() + "/compressed/" + file.getName();
                        arithmeticCodingCompress(file.getPath(), outputFileLocation);
                        calculateCompressionRatio(file.getPath(), outputFileLocation);
                    }

                }
                break;
            }

            case "Adaptive Arithmetic Coding": {

                if (files.size() == 1) {

                    adaptiveArithmeticCodingCompress(inputFilePath, outputFilePath);
                    calculateCompressionRatio(inputFilePath, outputFilePath);
                    break;

                } else {
                    for (File file : files) {
                        new File(file.getParent() + "/compressed").mkdirs();
                        String outputFileLocation = file.getParent() + "/compressed/" + file.getName();
                        adaptiveArithmeticCodingCompress(file.getPath(), outputFileLocation);
                        calculateCompressionRatio(file.getPath(), outputFileLocation);
                    }

                }
                break;
            }

            case "LZW": {
                if (files.size() == 1) {
                    LZW.compress(inputFilePath, outputFilePath);
                } else {
                    for (File file : files) {
                        new File(file.getParent() + "/decompressed").mkdirs();
                        LZW.compress(file.getPath(), file.getParent() + "/decompressed/" + file.getName());
                    }
                }
            }
        }
    }

    public static void decompress(String inputFilePath, String outputFilePath, String compressionAlgorithm)
            throws IOException {

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
                if (files.size() == 1) {
                    Huffman.decompress(inputFilePath, outputFilePath);
                } else {
                    for (File file : files) {
                        new File(file.getParent() + "/decompressed").mkdirs();
                        Huffman.decompress(file.getPath(), file.getParent() + "/decompressed/" + file.getName());
                    }
                }
                break;
            }

            case "Adaptive Huffman": {
                adaptiveDecode(inputFilePath, outputFilePath);
                break;
            }

            case "Arithmetic Coding": {

                if (files.size() == 1) {

                    arithmeticCodingDecompress(inputFilePath, outputFilePath);

                    break;

                } else {
                    for (File file : files) {
                        new File(file.getParent() + "/decompressed").mkdirs();
                        arithmeticCodingDecompress(file.getPath(), file.getParent() + "/decompressed/" + file.getName());
                    }

                }
                break;
            }

            case "Adaptive Arithmetic Coding": {
                if (files.size() == 1) {

                    adaptiveArithmeticCodingDecompress(inputFilePath, outputFilePath);

                    break;

                } else {
                    for (File file : files) {
                        new File(file.getParent() + "/decompressed").mkdirs();
                        adaptiveArithmeticCodingDecompress(file.getPath(), file.getParent() + "/decompressed/" + file.getName());
                    }

                }
                break;
            }

            case "LZW": {
                if (files.size() == 1) {
                    LZW.decompress(inputFilePath, outputFilePath);
                } else {
                    for (File file : files) {
                        new File(file.getParent() + "/decompressed").mkdirs();
                        LZW.decompress(file.getPath(), file.getParent() + "/decompressed/" + file.getName());
                    }
                }
                break;
            }
        }
    }

    private static void adaptiveArithmeticCodingCompress(String inputFilePath, String outputFilePath) throws IOException {
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);
        // Perform file compression
        try (InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
             BitOutputStream out = new BitOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)))) {
            AdaptiveArithmeticCompress.compress(in, out);
        }
    }

    private static void adaptiveArithmeticCodingDecompress(String inputFilePath, String outputFilePath) throws IOException {
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);

        // Perform file decompression
        try (BitInputStream in = new BitInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            AdaptiveArithmeticDecompress.decompress(in, out);
        }
    }

    private static void arithmeticCodingCompress(String inputFilePath, String outputFilePath) throws IOException {
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);

        // Read input file once to compute symbol frequencies
        FrequencyTable freqs = getFrequencies(inputFile);
        freqs.increment(256);  // EOF symbol gets a frequency of 1

        // Read input file again, compress with arithmetic coding, and write output file
        try (InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
             BitOutputStream out = new BitOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)))) {
            writeFrequencies(out, freqs);
            ArithmeticCompress.compress(freqs, in, out);
        }
    }

    private static void arithmeticCodingDecompress(String inputFilePath, String outputFilePath) throws IOException {
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);

        // Perform file decompression
        try (BitInputStream in = new BitInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            FrequencyTable freqs = readFrequencies(in);
            ArithmeticDecompress.decompress(freqs, in, out);
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

    private static void calculateCompressionRatio(String inputFileLocation, String outputFileLocation) {
        File inputFile = new File(inputFileLocation);
        File outputFile = new File(outputFileLocation);


        double inputFileSize = (double) (inputFile.length()) / (1024 * 1024);


        double outputFileSize = (double) (outputFile.length()) / (1024 * 1024);

        String logMessage = "-----\n";
        logMessage += "Input File:\nName: " + inputFile.getName() + " Size: " + String.format("%.02f", inputFileSize) + " MB";
        logMessage += "\nCompressed File:\nName: " + outputFile.getName() + " Size: " + String.format("%.02f", outputFileSize) + " MB";
        logMessage += "\nCompression Ration = " + String.format("%.02f", (outputFileSize / inputFileSize)) + "%";
        logMessage += "\n-----\n";
        FileUtils.writeToLogFile(logMessage);

    }

    public static void adaptiveEncode(String readFilename, String outputFilePath, String encodingSize) {
        AdaptiveHuffmanEncoder encoder = new AdaptiveHuffmanEncoder(Integer.parseInt(encodingSize));
        outputFilePath = outputFilePath.replace('/', '\\');
        encoder.encode(readFilename, outputFilePath);
    }

    public static void adaptiveDecode(String fileName, String outputFilePath) {
        AdaptiveHuffmanDecoder decoder = new AdaptiveHuffmanDecoder();
        outputFilePath = outputFilePath.replace('/', '\\');
        decoder.decode(fileName, outputFilePath);
    }

    public enum CompressionAlgorithms {

        RLE, HUFFMAN, ADAPTIVE_HUFFMAN, LZ77, ARITHMETIC_CODING, ADAPTIVE_ARITHMETIC_CODING, LZW;


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
                case LZ77: {
                    return "LZ77";
                }
                case ARITHMETIC_CODING: {
                    return "Arithmetic Coding";
                }
                case ADAPTIVE_ARITHMETIC_CODING: {
                    return "Adaptive Arithmetic Coding";
                }
                case LZW: {
                    return "LZW";
                }
            }
            return "";
        }
    }
}
