package Networking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.concurrent.Semaphore;

/**
 * Contains connection to server. Sends data to the server at request.
 * 
 * @author Peter Zhang
 */
public class StreamWriter implements AbstractWriter {
	private final ObjectOutputStream oos;
	private final Semaphore lock = new Semaphore(1, true);

	public StreamWriter(ObjectOutputStream o) throws IOException {
		oos = o;
		System.out.println("StreamWriter: got stream");

		// sanity check
		oos.flush();
	}

	/**
	 * Sends the Request variable to the Server. For example:
	 * <code>writer.sendData(new Request("receiveBin", "feeder1", null));</code>
	 * 
	 * @param req
	 *            Request variable to be sent.
	 */
	@Override
	public void sendData(Request req) {
		try {
			// only allow one call of the method at one time.
			// System.out.println("StreamWriter: (acq) requesting for \"" + req + "\"");
			lock.acquire();
			// System.out.println(toString() + ":  requesting for \"" + req + "\"");

			oos.writeObject(req);
			oos.flush();
			oos.reset();

			// allow other people to call now
			lock.release();
			// System.out.println("StreamWriter: (rel) requesting for \"" + req + "\"");
		} catch (SocketException e) {
			System.out.println("StreamWriter: Connection lost. Other terminal has disconnected.");
		} catch (Exception e) {
			System.out.println(toString() + ": request fail");
			e.printStackTrace();
		}
	}
}
