package utility.security;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.security.MessageDigest;

import timer.Timer;

public class Hasher {

	private static MessageDigest HASHER = null;

	private static byte[] lock = new byte[0];

	static {
		init();
	}

	public static void main(String[] args) {
		
		int times = 1000000;
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		long start = bean.getCurrentThreadCpuTime();
//		timer.reset();
		String testStr = "13010177899260974698268335948222812229032559169368248982715056251741919|14180373046968447110771776474120209297778825660630606807910598702665019|5887452964664100737530293136278805430831466489207678051240397325075669|10376367661798113032690241680154132210665619298112842979765174675441004|13010177899260974698268335948222812229032559169368248982715056251741919|14180373046968447110771776474120209297778825660630606807910598702665019|5887452964664100737530293136278805430831466489207678051240397325075669|10376367661798113032690241680154132210665619298112842979765174675441004|";
		for (int i = 0; i < times; i ++) {
//			for (int j = 0; j < 64; j ++) {
				testStr = hashString(testStr);
//			}
			//System.out.println(result.length());
		}
		long end = bean.getCurrentThreadCpuTime();
//		timer.stop();
		System.out.println("Time consume: " + (end - start) / 1000000.0 / times + "ms");
	}

	private static void init() {
		try {
			HASHER = MessageDigest.getInstance("SHA-1");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * 
	 * @param str
	 * @return HEX string
	 */
	public static String hashString(String str) {
		try {
			//System.err.println(str);
			byte[] data = str.getBytes("ISO-8859-1");
			synchronized (lock) {
				HASHER.update(data);
				data = HASHER.digest();
			}
			return new String(data, "ISO-8859-1");
			//return EncodeConverter.byteToHexString(data);
		} catch (Exception e) {
			System.err.println("hashString fatal error. str:" + str);
			e.printStackTrace();
			System.exit(-1);
			return null;
		}
	}

}
