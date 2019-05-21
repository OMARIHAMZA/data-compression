package omari.hamza.utils.compression;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CompressionUtils {

    public static enum CompressionAlgorithms {

        RLE , HUFFMAN, ADAPTIVE_HUFFMAN;


        @Override
        public String toString() {
            switch (this){
                case RLE:{
                    return "RLE";
                }

                case ADAPTIVE_HUFFMAN:{
                    return "Adaptive Huffman";
                }

                case HUFFMAN:{
                    return "Huffman";
                }
            }

            return "";
        }
    }

    public static void compress(String inputFilePath, String outputFilePath, String compressionAlgorithm)
            throws FileNotFoundException {

        switch (compressionAlgorithm) {

            case "RLE": {
                System.out.println(outputFilePath);
                RLE.compress(new FileInputStream(inputFilePath), new FileOutputStream(outputFilePath));
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

        switch (compressionAlgorithm) {

            case "RLE": {
                RLE.decompress(new FileInputStream(inputFilePath), new FileOutputStream(outputFilePath));
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
}
