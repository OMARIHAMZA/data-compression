package omari.hamza.utils.compression.lzw;

public class LZW {
    public static void compress(String source, String destination) {
        Lzipping.beginLzipping(source, destination);

    }

    public static void decompress(String source, String destination) {
        Lunzipping.beginLunzipping(source, destination);
    }
}