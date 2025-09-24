package networking;

/**
 * code based on oscP5 examples by andreas schlegel
 * http://www.sojamo.de/oscP5
 */

import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;

public class OSCServer {

	public NetAddress myRemoteLocation;
	public String theMessage;
	
	public OSCServer(String s, int p) {

		int port = p;
		myRemoteLocation = new NetAddress("127.0.0.1", port);
		theMessage = s;
		this.send();
		
	}

	void send() {

		// create a new osc message object 
		OscMessage myMessage = new OscMessage("list");

		myMessage.add(this.theMessage); // add a string to the osc message 

		// send the message 
		OscP5.flush(myMessage, myRemoteLocation);
	}

	public static void main(String[] args) {
		new OSCServer("Welcome",59999);
	}
}
