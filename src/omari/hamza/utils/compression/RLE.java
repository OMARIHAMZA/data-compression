package omari.hamza.utils.compression;

import java.io.*;

public class RLE {

    /**
     * default constructor
     **/
    public RLE() {
    }

    /**
     * Reads a series of bytes from an inputstream and
     * executes a compression algorithm over those bytes, writing
     * the compressed data to the specified outputStream.
     *
     * @param in  the InputStream for the data
     * @param out the outputStream for writing the compressed data
     * @return a String reporting information to be logged.
     **/
    public String compress(InputStream in, OutputStream out) {
        try {
            //Uncomment the following line to use the write buffer
            //WriteBuffer b = new WriteBuffer(out);
            StringBuilder log = new StringBuilder();
            int bite = in.read();
            while (bite != -1) {
                int count = 1;
                int next = in.read();
                while (next == bite && count < 255) {
                    count++;
                    next = in.read();
                }
                out.write(count);
                out.write(bite);
                //uncomment the following lines
                //to use the write buffer
                //b.write(8, count);
                //b.write(8, bite);
                log.append("Wrote ").append(count).append(" ").append(bite).append("'s\n");
                bite = next;
            }
            return log.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return ("Fail!");
        }
    }


    /**
     * Reads a series of bytes from compressed data and
     * executes a decompression algorithm over those bytes, writing
     * the decompressed data to the specified outputStream.
     *
     * @param in  the InputStream for the compressed data
     * @param out the outputStream for writing the decompressed data
     * @return a String reporting information to be logged.
     **/
    public String decompress(InputStream in, OutputStream out) {
        try {
            StringBuffer log = new StringBuffer("");
            int count = in.read();
            while (count != -1) {
                int bite = in.read();
                for (int i = 0; i < count; i++)
                    out.write(bite);
                log.append("Read " + count + " " + bite + "'s\n");
                count = in.read();
            }
            return log.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return ("Fail!");
        }
    }

    /**
     * Main method to compress/decompress from command line
     * options are c to compress, d to decompress, v for verbose
     * followed by input file, then output file.
     * eg java RLE -vc file.txt file.z
     * will create a compressed file file.z corresponding to file.txt
     * and give verbose output.
     */
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fin = new FileInputStream("C:\\Users\\ASUS\\Desktop\\test_result.pdf");
        FileOutputStream fout = new FileOutputStream("C:\\Users\\ASUS\\Desktop\\test_xx.pdf");
        RLE rle = new RLE();
        System.out.println(rle.decompress(fin, fout));
       /* try {
            FileInputStream fin = new FileInputStream(args[1]);
            FileOutputStream fout = new FileOutputStream(args[2]);
            RLE rle = new RLE();
            if (compress) {
                if (verbose)
                    System.out.println(rle.compress(fin, fout));
                else rle.compress(fin, fout);
            } else if (decompress) {
                if (verbose)
                    System.out.println(rle.decompress(fin, fout));
                else rle.decompress(fin, fout);
            } else usage();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Fail");
            e.printStackTrace();
        }*/
    }

    private static void usage() {
        System.out.println("The RLE class will compress/decompress from command line");
        System.out.println("The options are:");
        System.out.println("\t c to compress");
        System.out.println("\t d to decompress");
        System.out.println("\t v for verbose output");
        System.out.println("followed by input file, then output file.");
        System.out.println("For example:");
        System.out.println("java RLE -vc file.txt file.z");
        System.out.println("will create a compressed file, file.z corresponding to file.txt and give verbose output.");
        System.out.println("java RLE -d file.z file.txt");
        System.out.println("will create a decompress the file, file.z, to give the original file.txt and give verbose output.");
    }

    /**
     * A simple class for writing bitstreams to bytestreams
     **/
    class WriteBuffer {

        int pos;//the number of bits in the buffer
        long buffer;
        OutputStream out;

        WriteBuffer(OutputStream out) {
            this.out = out;
        }

        /**
         * Writes the n least significant bits of m to the buffer
         **/
        public void write(int n, int m) throws IOException {
            buffer = (buffer << n) + m % (1 << n);
            pos = pos + n;
            while (pos >= 8) {
                out.write((int) (buffer >> (pos - 8)));
                pos = pos - 8;
            }
        }

        /**
         * pads the final output with 0's and writes to
         * the output stream
         **/
        public void flush() throws IOException {
            write((8 - pos % 8) % 8, 0);
        }
    }
}

