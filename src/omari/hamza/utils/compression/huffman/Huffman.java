package omari.hamza.utils.compression.huffman;

public class Huffman {
    public static void compress(String source, String destination) {
        Hzipping.beginHzipping(source);

    }

    public static void decompress(String source, String destination) {
        Hunzipping.beginHunzipping(source);
    }
}
