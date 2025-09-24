package logfilegen.allgenerators.record;

import java.util.Random;

import logfilegen.allmodels.record.Ip;

public class IpGen {
	Random random = new Random();
	public Ip generate(){
		short num0 = (short)random.nextInt(256);
		short num1 = (short)random.nextInt(256);
		short num2 = (short)random.nextInt(256);
		short num3 = (short)random.nextInt(256);
		
		
		return new Ip(num0, num1, num2, num3);
	}
}
