package patterns.gof.structural.facade;

import java.util.Random;

public class HardDrive {
	public byte[] read(long lba, int size) {
		byte[] data = new byte[size];
		(new Random()).nextBytes(data);
		FacadeClient.addOutput("recieved array of bytes at lba " + lba);
		return data;
	}
}