package manager.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Networking.Request;
import Networking.StreamWriter;

/**
 * A common listener for buttons whose sole role is to send request to server. 
 * 
 * @author Peter Zhang
 */
public class NetworkingButtonListener implements ActionListener {
	
	private Request req;
	private StreamWriter writer;
	
	/**
	 * Setting NetworkingButtonListener as the ActionListener of a button will send the specified Request to the server.
	 * @param req
	 * @param writer
	 */
	public NetworkingButtonListener(Request req, StreamWriter writer) {
		this.req = req;
		this.writer = writer;
	}
	
	/**
	 * Setting NetworkingButtonListener as the ActionListener of a button will send the specified Request to the server.
	 * Calling constructor with target and command but not a data parameter will generate a Request with null data.
	 */
	public NetworkingButtonListener(String command, String target, StreamWriter writer) {
		this(new Request(command, target, null), writer);
	}
	
	/**
	 * Setting NetworkingButtonListener as the ActionListener of a button will send the specified Request to the server.
	 * Calling constructor with target and command and data parameter will generate a Request based on them to send. 
	 */
	public NetworkingButtonListener(String command, String target, Object data, StreamWriter writer) {
		this(new Request(command, target, data), writer);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		writer.sendData(req);
	}

}
