package logfilegen.allgenerators.record;

import java.util.Random;

import logfilegen.allmodels.record.Size;

public class SizeGen {
	Random random = new Random();
	public Size generate(){
		Long size = Math.abs(random.nextLong()) % 100000;
		return new Size(size);				
	}
}
