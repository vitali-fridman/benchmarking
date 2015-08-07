package com.shn.sid.benchmark;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

public class BenchmarkRAF {
	final static long G = 1024L*1024*1024;
	final static long LENGTH = 128L*G;
	// final static long LENGTH = 4L*G;
	final static String LOCATION = "/media/vitali/Intel/benchmark.bin";
	// final static String LOCATION = "/home/vitali/bechmark.bin";
	final static int ITERATIONS = 100*1024;
	final static int READ_LEGTH = 10 * 14;

	public static void main(String[] args) throws IOException {
		
//		RandomAccessFile file = null;
//		try {
//            file = new RandomAccessFile(LOCATION, "rw");
//            file.setLength(LENGTH);
//       } catch (Exception e) {
//            System.err.println(e);
//       }
		
		String fileName = args[0];
		RandomAccessFile file = new RandomAccessFile(fileName, "r");
		
		Random rand = new Random();
		
		byte[] dst = new byte[READ_LEGTH];
		
		long start = System.nanoTime();
		
		for(int i=0; i<ITERATIONS; i++) {
			long l = rand.nextLong();
			long location = (l>0 ? l%(LENGTH-READ_LEGTH) : -l%(LENGTH-READ_LEGTH));
			// System.out.println("Location: " + location / (double)G);
			file.seek(location);
			file.readFully(dst);
		}
		
		long end = System.nanoTime();
		
		System.out.println(((end-start)/1000000000d) / ITERATIONS + " secs");
	}
}
