package com.shn.sid.benchmark;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Random;

public class RamBenchmark {
	
	final static int G = 1024*1024*1024;
	final static int LENGTH = 1*G;
	final static String LOCATION = "/media/vitali/Intel/benchmark.bin";
	final static int ITERATIONS = 100 * 1000;
	final static int READ_LEGTH = 10 * 14;

	public static void main(String[] args) throws IOException {
		
		byte[] memBuf = new byte[(int)G];
		
		Random rand = new Random();
		int[] locations = new int[ITERATIONS];
		for(int i=0; i<ITERATIONS; i++) {
			int l = rand.nextInt();
			int location = (l>0 ? l%(LENGTH-READ_LEGTH) : -l%(LENGTH-READ_LEGTH));
			locations[i] = location;
		}
		
		byte[] dst = new byte[READ_LEGTH];
		
		long start = System.nanoTime();
		
		for(int i=0; i<ITERATIONS; i++) {
			byte b = memBuf[locations[i]];
//			if (memBuf[locations[i]] != (byte)0) {
//				System.out.println("Data does not match. Expected: " + (byte)0 + ", found: " + (int)memBuf[locations[i]]);
//				System.out.println("Location: " + locations[i]);
//				System.exit(-1);
//			}
		}
		
		long end = System.nanoTime();
		
		System.out.println("Start: " + start + " End: " + end);
		System.out.println(((end-start)/1000000000d) / ITERATIONS + " secs");
	}

}

