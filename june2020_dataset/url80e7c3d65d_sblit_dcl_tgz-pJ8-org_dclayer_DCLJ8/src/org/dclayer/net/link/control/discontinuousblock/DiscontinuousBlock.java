package org.dclayer.net.link.control.discontinuousblock;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.net.Data;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.link.control.packetbackup.PacketBackup;

/**
 * this is the received counterpart to a {@link PacketBackup}.<br />
 * stores one received packet.
 */
public class DiscontinuousBlock {
	
	/**
	 * true if this {@link DiscontinuousBlock} object is currently occupied, false otherwise
	 */
	public boolean used = false;
	
	/**
	 * the data id of the packet this stores
	 */
	private long dataId;
	/**
	 * the packet data
	 */
	private Data data;
	
	/**
	 * a {@link PacketBackup} that was sent as reply to this
	 */
	private PacketBackup replyPacketBackup;
	
	/**
	 * stores <i>length</i> bytes from <i>byteBuf</i> along with the given data id in this {@link DiscontinuousBlock} object
	 * @param dataId the data id of the packet
	 * @param byteBuf the {@link ByteBuf} holding the packet data
	 * @param length the packet data length
	 * @throws BufException
	 */
	public void store(long dataId, ByteBuf byteBuf, int length) throws BufException {
		this.dataId = dataId;
		if(data == null) data = new Data(length);
		else data.prepare(length);
		byteBuf.read(data);
	}
	
	/**
	 * copies the given data and stores it along with the given data id in this {@link DiscontinuousBlock} object
	 * @param dataId the data id of the packet
	 * @param data the data to copy
	 * @throws BufException
	 */
	public void store(long dataId, Data data) throws BufException {
		this.dataId = dataId;
		if(this.data == null) {
			this.data = data.copy();
		} else {
			this.data.prepare(data.length());
			this.data.setBytes(0, data);
		}
	}
	
	/**
	 * @return the packet data
	 */
	public Data getData() {
		return data;
	}
	
	/**
	 * sets the {@link PacketBackup} that was sent in response to this {@link DiscontinuousBlock}
	 * @param replyPacketBackup the {@link PacketBackup} that was sent in response to this {@link DiscontinuousBlock}
	 */
	public void setReplyPacketBackup(PacketBackup replyPacketBackup) {
		this.replyPacketBackup = replyPacketBackup;
	}
	
	/**
	 * @return the {@link PacketBackup} that was sent in response to this {@link DiscontinuousBlock}
	 */
	public PacketBackup getReplyPacketBackup() {
		return replyPacketBackup;
	}
	
	@Override
	public String toString() {
		return String.format("DiscontinuousBlock(dataId=%s)", dataId);
	}
	
}
