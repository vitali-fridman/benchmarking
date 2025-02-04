package com.shn.sid.benchmark;

import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSFileStore;

public class ashi {
	
	public static void main(String[] args) {
		SystemInfo si = new SystemInfo();
		HardwareAbstractionLayer hal = si.getHardware();
		OSFileStore[] fstores = hal.getFileStores();
		Memory mem = hal.getMemory();
		Processor[] pr = hal.getProcessors();
		
		System.out.println("Total mem: " + mem.getTotal());
		System.out.println("Available mem: " + mem.getAvailable());
		
		System.out.println("Processors: " + pr.length);
		
		System.out.println("Num file systems " + fstores.length);
		
		for (OSFileStore fs : fstores) {
			System.out.println("File system: " + fs.getDescription() + " " + fs.getName() + " " +
					fs.getTotalSpace() + " " + fs.getUsableSpace());
		}
	}

}
