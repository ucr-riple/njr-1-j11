package util;

public class Debug {
	public static boolean debug = false;
	public static void debug(Object s) {
		if (debug) {
			System.out.println(s);
		}
	}
}
