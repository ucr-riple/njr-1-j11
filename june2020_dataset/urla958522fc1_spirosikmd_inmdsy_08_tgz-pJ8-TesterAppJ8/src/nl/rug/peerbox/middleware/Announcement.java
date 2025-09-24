package nl.rug.peerbox.middleware;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;

/**
 * Uses a CRC32 checksum check for integrity
 * 
 * @author cm
 * 
 */
final class Announcement {

	static final byte MESSAGE = 1;
	static final byte ACK = 2;
	static final byte NACK = 4;
	static final byte HEARTBEAT = 8;

	static final int HEADER_SIZE = 19;
	static final int MAX_MESSAGE_SIZE = 32768; //2^15
	static final int MAX_PAYLOAD_SIZE = MAX_MESSAGE_SIZE - HEADER_SIZE;
	

	private byte command;
	private int peerID;
	private int messageID;
	private long checksum;
	private short length;
	private byte[] payload = new byte[0];

	private Announcement() {
	}

	static Announcement send(int source, int s_piggyback, byte[] payload) {
		Announcement m = new Announcement();
		m.command = MESSAGE;
		m.payload = payload;
		m.length = (short) m.payload.length;
		m.checksum = m.calculateChecksum(m.payload);
		m.peerID = source;
		m.messageID = s_piggyback;
		return m;
	}

	static Announcement ack(int destination, int messageID, int acknowledger) {
		Announcement m = new Announcement();
		m.command = ACK;
		m.peerID = destination;
		m.messageID = messageID;
		m.payload = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE)
				.putInt(acknowledger).array();
		m.length = (short) m.payload.length;
		m.checksum = m.calculateChecksum(m.payload);
		return m;

	}
	
	static Announcement heartbeat(int source, int s_piggyback, byte[] payload) {
		Announcement m = send(source, s_piggyback, payload);
		m.command = HEARTBEAT;
		return m;
	}

	static Announcement nack(int destination, int messageID) {
		Announcement m = new Announcement();
		m.command = NACK;
		m.peerID = destination;
		m.messageID = messageID;
		m.length = (short) m.payload.length;
		m.checksum = m.calculateChecksum(m.payload);
		return m;
	}

	static Announcement fromByte(byte[] bytes) throws ChecksumFailedException {

		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		Announcement tmp = new Announcement();
		tmp.command = buffer.get();
		tmp.peerID = buffer.getInt();
		tmp.messageID = buffer.getInt();
		tmp.checksum = buffer.getLong();
		tmp.length = buffer.getShort();
		tmp.payload = (byte[]) Arrays.copyOfRange(buffer.array(), HEADER_SIZE,
				HEADER_SIZE + tmp.length);

		long cchecksum = tmp.calculateChecksum(tmp.payload);
		if (cchecksum != tmp.checksum) {
			throw new ChecksumFailedException();
		}

		return tmp;
	}

	byte[] toByte() {
		ByteBuffer buffer = ByteBuffer.allocate(HEADER_SIZE + length);
		buffer.put(command);
		buffer.putInt(peerID);
		buffer.putInt(messageID);
		buffer.putLong(checksum);
		buffer.putShort(length);
		buffer.put(payload);
		return buffer.array();
	}

	byte getCommand() {
		return command;
	}

	int getPeerID() {
		return peerID;
	}

	int getMessageID() {
		return messageID;
	}

	long getChecksum() {
		return checksum;
	}

	short getLength() {
		return length;
	}

	byte[] getPayload() {
		return payload;
	}

	private long calculateChecksum(byte[] data, int offset, int length) {
		CRC32 crc32 = new CRC32();
		crc32.update(data, offset, length);
		return crc32.getValue();
	}

	private long calculateChecksum(byte[] data) {
		return calculateChecksum(data, 0, data.length);
	}

	@Override
	public String toString() {
		return cmdToText(command) + " " + peerID + "(" + messageID + ")  => "
				+ PrettyPrinter.print(this);
	}


	private String cmdToText(byte command) {
		switch (command) {
		case ACK:
			return "ACK";
		case NACK:
			return "MISS";
		case MESSAGE:
			return "MESSAGE";
		case HEARTBEAT:
			return "HEARTBEAT";
		default:
			return "STRANGE";
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Announcement)) {
			return false;
		}

		Announcement other = (Announcement) obj;
		return (command == other.command && peerID == other.peerID
				&& messageID == other.messageID && checksum == other.checksum
				&& length == other.length && Arrays.equals(payload,
				other.payload));

	}
}