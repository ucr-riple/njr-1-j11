package Networking;

/**
 * Literally does nothing. All writers are to be initialized to a DummyWriter, so that in cases where some clients did
 * not connect to the Server before a Request was to be sent, no null pointer exceptions will appear. Instead, a message
 * is printed.
 * 
 * @author Peter Zhang
 */
public class DummyWriter implements AbstractWriter {

	@Override
	public void sendData(Request req) {
		// System.out.println("StreamWriter: Manager has not started. Cannot send request");
	}
}
