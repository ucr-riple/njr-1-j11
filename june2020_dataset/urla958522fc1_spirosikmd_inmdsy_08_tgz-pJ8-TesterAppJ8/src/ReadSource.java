import java.io.*;
import java.util.*;

public class ReadSource implements Runnable {

	private BufferedReader input;
	private PrintStream output;
	private Thread readSourceListener;
	private List<ReadEventListener> listeners = new ArrayList<ReadEventListener>();

	public ReadSource() {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = System.out;
		readSourceListener = new Thread(this);
		readSourceListener.start();
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				byte[] utf = input.readLine().getBytes("UTF8");
				fireEvent(utf);
			}
		} catch (IOException ex) {
			output.println(ex);
		}
	}

	public synchronized void addEventListener(ReadEventListener listener) {
		listeners.add(listener);
	}

	public synchronized void removeEventListener(ReadEventListener listener) {
		listeners.remove(listener);
	}

	private synchronized void fireEvent(byte[] utf) {
		ReadEvent event = new ReadEvent(this, utf);
		Iterator i = listeners.iterator();
		while(i.hasNext()) {
			((ReadEventListener)i.next()).handleReadEvent(event);
		}
	}
}