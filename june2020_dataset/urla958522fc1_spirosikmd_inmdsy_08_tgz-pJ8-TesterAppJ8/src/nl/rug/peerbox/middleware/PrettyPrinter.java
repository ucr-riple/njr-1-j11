package nl.rug.peerbox.middleware;

import java.util.LinkedList;
import java.util.List;

public abstract class PrettyPrinter {

	public abstract String printPayload(byte[] payload);

	static List<PrettyPrinter> printers = new LinkedList<PrettyPrinter>();

	public static void registerPrinter(PrettyPrinter p) {
		printers.add(p);
	}

	static String print(Announcement a) {
		for (PrettyPrinter p : printers) {
			String output = p.printPayload(a.getPayload());
			if (output != null) {
				return output;
			}
		}
		return firstXChars(40, byteToText(a.getPayload()));
	}

	private static String firstXChars(int x, String text) {
		return text.substring(0, text.length() < x ? text.length() : x);
	}

	private static String byteToText(byte[] bytes) {
		StringBuffer bf = new StringBuffer();
		for (byte b : bytes) {
			bf.append((char) b);
		}
		return bf.toString();
	}

}
