package Networking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import DeviceGraphicsDisplay.DeviceGraphicsDisplay;
import Utils.Constants;

/**
 * Abstract class that all managers extend from.
 * 
 * @author Peter Zhang
 */
public abstract class Client extends JPanel {

	private Socket socket;
	protected ServerReader reader;
	protected StreamWriter writer;

	protected int offset;

	public static Font font;
	public static Font pokeFont;

	/**
	 * To identify client with server.
	 */
	protected String clientName;

	/**
	 * To store devices based on device target.
	 */
	protected Map<String, DeviceGraphicsDisplay> devices = Collections
			.synchronizedMap(new LinkedHashMap<String, DeviceGraphicsDisplay>(100, .75f, false));

	protected Client() {
		setLayout(new BorderLayout());
		font = new Font("Arial", font.PLAIN, 14);
		try {
			pokeFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/pkmndp.ttf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		pokeFont = pokeFont.deriveFont(19f);
		Map<TextAttribute, Object> fontAttributes = new HashMap<TextAttribute, Object>();
		fontAttributes.put(TextAttribute.KERNING, Integer.valueOf(5));

		pokeFont = pokeFont.deriveFont(fontAttributes);
	}

	/**
	 * This is called by ServerReaders' receiveData(Object), taking in a Request variable casted from ObjectInput. Must
	 * be implemented by the Manager subclasses so to parse the Request variable accordingly.
	 */
	public abstract void receiveData(Request req);

	public void initStreams() {
		try {
			socket = new Socket("localhost", Constants.SERVER_PORT);
			System.out.println("Client: connected to the server");

			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			writer = new StreamWriter(oos);
			reader = new ServerReader(ois, this);
			new Thread(reader).start();
			System.out.println("Client: streams ready");
		} catch (Exception e) {
			System.out.println("Client: got an exception initing streams" + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

		// establish client identity with server
		writer.sendData(new Request(Constants.IDENTIFY_COMMAND, Constants.SERVER_TARGET, clientName));
	}

	public static void setUpJFrame(JFrame frame, int width, int height, String name) {
		frame.setBackground(Color.BLACK);
		frame.setTitle(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	public void addDevice(String target, DeviceGraphicsDisplay device) {
		// target.clone();
		synchronized (devices) {
			// Map<String, DeviceGraphicsDisplay> ClonedMap = new LinkedHashMap<String,DeviceGraphicsDisplay>(devices);
			// ClonedMap.put(target, device);
			devices.put(target, device);
		}

	}

	public void removeDevice(String target) {
		synchronized (devices) {
			for (Iterator<String> iter = devices.keySet().iterator(); iter.hasNext();) {
				String s = iter.next();
				if (s.equals(target)) {
					iter.remove();
				}
			}
		}
	}

	public void sendData(Request req) {
		writer.sendData(req);
	}

	public int getOffset() {
		return offset;
	}

	public void startMusic() {
	}

	public void startPokeflute() {
	}

	public void startRecovery() {
	}

	public void startMessageTone() {
	}
}
