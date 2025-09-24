package patterns.gof.structural.facade;

import java.util.Arrays;

public class Memory {
	public void load(long position, byte[] data) {
		FacadeClient.addOutput("loading memory at position " + 
				position + " with data: " + Arrays.toString(data));
	}
}