package omari.hamza.utils.compression.aithmetic_coding;/*
 * Reference arithmetic coding
 * Copyright (c) Project Nayuki
 * 
 * https://www.nayuki.io/page/reference-arithmetic-coding
 * https://github.com/nayuki/Reference-arithmetic-coding
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Compression application using static arithmetic coding.
 * <p>Usage: java ArithmeticCompress InputFile OutputFile</p>
 * <p>Then use the corresponding "ArithmeticDecompress" application to recreate the original input file.</p>
 * <p>Note that the application uses an alphabet of 257 symbols - 256 symbols for the byte
 * values and 1 symbol for the EOF marker. The compressed file format starts with a list
 * of 256 symbol frequencies, and then followed by the arithmetic-coded data.</p>
 */
public class ArithmeticCompress {
	
	public static void main(String[] args) throws IOException {
		// Handle command line arguments
		if (args.length != 2) {
			System.err.println("Usage: java ArithmeticCompress InputFile OutputFile");
			System.exit(1);
			return;
		}

	}
	
	
	// Returns a frequency table based on the bytes in the given file.
	// Also contains an extra entry for symbol 256, whose frequency is set to 0.
	public static FrequencyTable getFrequencies(File file) throws IOException {
		FrequencyTable freqs = new SimpleFrequencyTable(new int[257]);
		try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
			while (true) {
				int b = input.read();
				if (b == -1)
					break;
				freqs.increment(b);
			}
		}
		return freqs;
	}
	
	
	// To allow unit testing, this method is package-private instead of private.
	public static void writeFrequencies(BitOutputStream out, FrequencyTable freqs) throws IOException {
		for (int i = 0; i < 256; i++)
			writeInt(out, 32, freqs.get(i));
	}
	
	
	// To allow unit testing, this method is package-private instead of private.
	public static void compress(FrequencyTable freqs, InputStream in, BitOutputStream out) throws IOException {
		ArithmeticEncoder enc = new ArithmeticEncoder(32, out);
		while (true) {
			int symbol = in.read();
			if (symbol == -1)
				break;
			enc.write(freqs, symbol);
		}
		enc.write(freqs, 256);  // EOF
		enc.finish();  // Flush remaining code bits
	}
	
	
	// Writes an unsigned integer of the given bit width to the given stream.
	public static void writeInt(BitOutputStream out, int numBits, int value) throws IOException {
		if (numBits < 0 || numBits > 32)
			throw new IllegalArgumentException();
		
		for (int i = numBits - 1; i >= 0; i--)
			out.write((value >>> i) & 1);  // Big endian
	}
	
}
