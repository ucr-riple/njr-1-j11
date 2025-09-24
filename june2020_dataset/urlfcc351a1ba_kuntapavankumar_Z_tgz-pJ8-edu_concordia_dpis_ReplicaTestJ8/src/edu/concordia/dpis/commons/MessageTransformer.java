package edu.concordia.dpis.commons;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// A Java serializer and deserialier
public class MessageTransformer {

	// serializes using java serialization mechanism
	public static byte[] serializeMessage(Message msg) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bos);
		out.writeObject(msg);
		out.close();
		return bos.toByteArray();
	}

	// deserializes using java deserialization mechanism
	public static Message deserializeMessage(byte[] msg) throws IOException {
		ObjectInputStream reader = new ObjectInputStream(
				new ByteArrayInputStream(msg));
		try {
			Message request = (ReliableMessage) reader.readObject();
			return request;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
