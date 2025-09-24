package Networking;

import java.io.ObjectInputStream;

/**
 * Variant of StreamReader, used by clients. 
 *  
 * @author Peter Zhang
 */
public class ServerReader extends StreamReader {

	private Client client;
	
	public ServerReader(ObjectInputStream ois, Client c) {
		super(ois);
		client = c;
	}

	@Override
	public void receiveData(Request req) {
		client.receiveData(req);
	}
	
}
