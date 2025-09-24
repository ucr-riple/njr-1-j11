import nl.rug.peerbox.middleware.Multicast;
import nl.rug.peerbox.middleware.PrettyPrinter;
import nl.rug.peerbox.middleware.ReliableMulticast;

import org.apache.log4j.BasicConfigurator;


public class TesterApp {
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		String address = "239.1.2.4";
		int port = 1567;
		Multicast group = ReliableMulticast.createPeer(address, port);
		PrettyPrinter.registerPrinter(new MessagePrinter());	
	}

}
