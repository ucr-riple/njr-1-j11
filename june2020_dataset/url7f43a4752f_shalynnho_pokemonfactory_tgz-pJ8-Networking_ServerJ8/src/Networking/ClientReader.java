package Networking;

import java.io.ObjectInputStream;

/**
 * Variant of StreamReader, used by the Server. 
 *  
 * @author Peter Zhang
 */
public class ClientReader extends StreamReader {

	private Server server;
	
	public ClientReader(ObjectInputStream ois, Server s) {
		super(ois);
		server = s;
	}

	@Override
	public void receiveData(Request req) {
		server.receiveData(req);
	}
	
}
