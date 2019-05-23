package omari.hamza.utils.compression.adaptive.huffman;


public class AdaptiveHuffmanCoding {

    public static void main(String[] args) {
        encode("C:\\Users\\m-n-3\\IdeaProjects\\testHuffman\\SWE2-1.pdf", "8");
        decode("C:\\Users\\m-n-3\\IdeaProjects\\testHuffman\\SWE2-1.compressed8.pdf");
    }

    public static void encode(String readFilename, String encodingSize) {
        AdaptiveHuffmanEncoder encoder = new AdaptiveHuffmanEncoder(Integer.parseInt(encodingSize));
        System.out.println("Starting encoding " + readFilename + " with " + encodingSize + " bit tree node representation.");
        encoder.encode(readFilename);
        System.out.println("Finished encoding");
    }

    public static void decode(String fileName) {
        AdaptiveHuffmanDecoder decoder = new AdaptiveHuffmanDecoder();
        System.out.println("Starting decoding " + fileName);
        decoder.decode(fileName);
        System.out.println("Finished decoding");
    }


}