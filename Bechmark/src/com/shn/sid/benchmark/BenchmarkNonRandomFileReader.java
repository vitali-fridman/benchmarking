package com.shn.sid.benchmark;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkNonRandomFileReader {
	
	final static long G = 1024L*1024*1024;
	final static long LENGTH = 64L*G;
	final static String LOCATION = "/media/vitali/Intel/benchmark.bin";
	final static int ITERATIONS = 100 * 1024;
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
		MappedByteBuffer[] bb = new MappedByteBuffer[64];
		FileChannel channel = file.getChannel();
		for (int i=0; i<64; i++) {
			bb[i] = channel.map(FileChannel.MapMode.READ_ONLY, i*G, G);
			// bb[i].load();
		}
		
		for (int i=0; i<64; i++) {
			System.out.println("Buffer " + i + " is direct: " + bb[i].isDirect());
			System.out.println("Buffer " + i + " is loaded: " + bb[i].isLoaded());
		}
		
		// MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, LENGTH);
		
		// buffer.load();
		// System.out.println("Buffer is direct: " + buffer.isDirect());
		
		Random rand = new Random();
		
		byte[] dst = new byte[READ_LEGTH];
		
		long start = System.nanoTime();
		
		for(int i=0; i<ITERATIONS; i++) {
			// int location = rand.nextInt(LENGTH-READ_LEGTH);
			long l = rand.nextLong();
			long location = (l>0 ? l%(LENGTH-READ_LEGTH) : -l%(LENGTH-READ_LEGTH));
			int bi = (int)(location / G);
			channel.position(location);
			bb[bi].get(dst, 0, READ_LEGTH);
			// System.out.println("Location: " + location + " Buffer: " + bi);
			// System.out.println("Content: " + Arrays.toString(dst));
			if (bi != (int)dst[0]) {
				System.out.println("Data does not match. Expected: " + bi + ", found: " + (int)dst[0]);
				System.out.println("Location: " + location + " Buffer: " + bi);
				System.out.println("Content: " + Arrays.toString(dst));
				System.exit(-1);
			}
		}
		
		long end = System.nanoTime();
		
		System.out.println("Start: " + start + " End: " + end);
		System.out.println(((end-start)/1000000000d) / ITERATIONS + " secs");
	}

}
