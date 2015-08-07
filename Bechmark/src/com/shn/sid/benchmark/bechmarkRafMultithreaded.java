package com.shn.sid.benchmark;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class bechmarkRafMultithreaded {
	
	public static int numthreads;

	public static void main(String[] args) throws IOException {
		
		String fileName = args[0];
		numthreads = new Integer(args[1]).intValue();
		int size = new Integer(args[2]).intValue();
		
		ExecutorService executor = Executors.newFixedThreadPool(numthreads);
		for (int i = 0; i < numthreads; i++) {
			Runnable worker = new BenchmarkRafworker(fileName, i, size);
		    executor.execute(worker);
		}
		long start = System.nanoTime();
		executor.shutdown();
		while (!executor.isTerminated()) {
			
		}
		long end = System.nanoTime();
		System.out.println("Total Performance: " + ((end-start)/1000000000d) / (BenchmarkRafworker.ITERATIONS * numthreads) + " secs");
		
		System.out.println("Finished all threads");

	}
	
	public static int getNumthread() {
		return numthreads;
	}
}