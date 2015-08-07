package com.shn.sid.benchmark;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRafworker implements Runnable{
	
	final static long G = 1024L*1024*1024;
	final static int ITERATIONS = 50 * 1000;
	final static int READ_LEGTH = 10 * 14;

	private String fileName;
	private int index;
	private long sectorLength;
	private int size;
	private long length;
	
	public BenchmarkRafworker(String fileName, int index, int size) {
		
		this.fileName = fileName;
		this.index = index;
		this.size = size;
		this.length = size * G;
		this.sectorLength = this.length / bechmarkRafMultithreaded.getNumthread();
	}
	
	@Override
	public void run() {
		System.out.println("Worker started : index " + this.index);
		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile(this.fileName, "r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Random rand = new Random();
		
		byte[] dst = new byte[READ_LEGTH];
		long[] rl = new long[ITERATIONS];
		for(int i=0; i<ITERATIONS; i++) {
			rl[i] = rand.nextLong();
		}
			
		long start = System.nanoTime();
		
		for(int i=0; i<ITERATIONS; i++) {
			long l = rl[i];
			long location = (l>0 ? l%(length-READ_LEGTH) : -l%(length-READ_LEGTH));
			long sectorLocation = (location - this.sectorLength * index) % this.sectorLength;
			if (sectorLocation < 0) {
				sectorLocation = -sectorLocation;
			}
//			System.out.println("Location: " + location/ (double) G);
//			System.out.println("Sector location: " + sectorLocation / (double)G + " Sector: " + index);
//			System.out.println("Seek location: " + (index * SECTOR + sectorLocation)/(double)G);
			try {
				file.seek(index * this.sectorLength + sectorLocation);
				file.readFully(dst);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		long end = System.nanoTime();
		long threadId = Thread.currentThread().getId();
		System.out.println("Worker " + this.index + " Performance: " + ((end-start)/1000000000d) / ITERATIONS + " secs");
	}

}