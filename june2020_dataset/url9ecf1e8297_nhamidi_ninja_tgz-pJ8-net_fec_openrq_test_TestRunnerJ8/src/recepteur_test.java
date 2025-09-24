import java.net.InetAddress;
import java.net.UnknownHostException;


public class recepteur_test {

	public static void main(String[] args) throws Exception {
		if (args.length != 6) {
			StringBuilder s = new StringBuilder();
			s.append("Usage: \n");
			s.append("    java -jar Receiver.jar pathToFile fileSize overhead port\n");
			s.append("\n        - pathToFile  : path to store the received file.");
			s.append("\n        - overhead    : the necessary symbol overhead needed for decoding (usually between 0 and 2).");
			s.append("\n        - destIP      : the IP address for the multicast.");
			s.append("\n        - portNumber  : the port the receiver will be listening on.");
			s.append("\n        - canalloss   : the loss due to the canal.");
			s.append("\n        - nb_receiver   : the number of receiver.");
			System.out.println(s.toString());
			System.exit(1);
		}

		String fileName = args[0];

		// check fileSize
		long fileSize = 0;

		// check overhead
		int overhead = -1;

		try {
			overhead = Integer.valueOf(args[1]);
		} catch (NumberFormatException e) {
			System.err
					.println("Invalid overhead value. (must be a positive integer)");
			System.exit(-1);
		}

		if (overhead < 0) {
			System.err
					.println("Invalid overhead value. (must be a positive integer)");
			System.exit(-1);
		}

		// get IP and transform to InetAddress
		InetAddress sendIP = null;

		try {
			sendIP = InetAddress.getByName(args[2]);
		} catch (UnknownHostException e2) {
			e2.printStackTrace();
			System.err.println("invalid IP");
			System.exit(1);
		}

		// check source port
		int srcPort = -1;

		try {
			srcPort = Integer.valueOf(args[3]);
		} catch (NumberFormatException e) {
			System.err.println("Invalid port. (must be above 1024)");
			System.exit(-1);
		}

		if (srcPort < 1024 || srcPort >= 65535) {
			System.err.println("Invalid port. (must be above 1024)");
			System.exit(-1);
		}

		float canalloss = -1;

		try {
			canalloss = Integer.valueOf(args[4]);
		} catch (NumberFormatException e) {
			System.err
					.println("Invalid network loss percentage. (must be a float in [0.0, 100.0[)");
			System.exit(-1);
		}

		if (canalloss < 0 || canalloss >= 100) {
			System.err
					.println("Invalid network loss percentage. (must be a float in [0.0, 100.0[)");
			System.exit(-1);
		}
		
		
		
		
		
		
		
		
	}
	
	
	
	
}
