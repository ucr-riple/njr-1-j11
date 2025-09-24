package nl.rug.peerbox.logic.messaging;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import nl.rug.peerbox.middleware.PrettyPrinter;

import org.apache.log4j.Logger;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Object NULLOBJ = new Object();
	private static final Logger logger = Logger.getLogger(Message.class);
	private final Map<Key, Object> dictionary = new HashMap<Key, Object>();

	public static enum Key {
		Command, Files, Peer, File, FileId
	}

	public static enum Command {
			Created, Deleted, Modified, ByeBye, List, Join, Refresh;
	}

	public void put(Key key, Object obj) {
		dictionary.put(key, obj);
	}

	public Object get(Key key) {
		Object result = NULLOBJ;
		if (dictionary.containsKey(key)) {
			result = dictionary.get(key);
		}
		return result;
	}

	public byte[] serialize() {
		byte[] data = new byte[0];
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(baos);
			os.writeObject(this);
			data = baos.toByteArray();
		} catch (IOException e) {
			logger.error(e);
		}
		return data;
	}

	public static Message deserialize(byte[] data) {
		Message message = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ObjectInputStream is = new ObjectInputStream(bais);
			Object o = is.readObject();
			if (o instanceof Message) {
				message = (Message) o;
			}
		} catch (IOException | ClassNotFoundException e) {
			logger.error(e);
		}
		return message;
	}
	
	public static class MessagePrinter extends PrettyPrinter {

		@Override
		public String printPayload(byte[] payload) {
			Message message = null;
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(payload);
				ObjectInputStream is = new ObjectInputStream(bais);
				Object o = is.readObject();
				if (o instanceof Message) {
					message = (Message) o;
				}
			} catch (Exception e) {
			}
			
			if (message != null) {
				return "" + message.get(Key.Command);
			}
			return null;
		}
		
	}

}
