package com.shn.sid.benchmark;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class BenchmarkNonRandomDataWriter {
	final static long G = 1024L*1024*1024;
	final static long LENGTH = 128L*G;
	final static String LOCATION = "/media/vitali/Intel/benchmark.bin";
	final static int ITERATIONS = 100 * 1024;
	final static int READ_LEGTH = 10 * 14;

	public static void main(String[] args) throws IOException {
		
		String fileName = args[0];
		RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		MappedByteBuffer[] bb = new MappedByteBuffer[64];
		FileChannel channel = file.getChannel();
		for (int i=0; i<64; i++) {
			bb[i] = channel.map(FileChannel.MapMode.READ_WRITE, i*G, G);
		}
		
		for (int i=0; i<64; i++) {
			System.out.println("Buffer " + i + " is direct: " + bb[i].isDirect());
			System.out.println("Buffer " + i + " is loaded: " + bb[i].isLoaded());
		}
		
		byte[] block = new byte[(int)G];		
		
		for(int i=0; i<64; i++) {
			Arrays.fill(block, (byte)i);
			bb[i].put(block);
			bb[i].force();
			System.out.println("Wrote block " + i);
		}
		
		channel.close();
		file.close();
		
		System.out.println("File created.");
	}
}
