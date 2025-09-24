package org.dclayer.net.link.component;


import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.FlexNum;

/**
 * a link packet header
 */
public class LinkPacketHeader extends PacketComponent {
	
	/**
	 * {@link FlexNum} instance for reading the data id into
	 */
	private final FlexNum ownDataId = new FlexNum();
	/**
	 * {@link FlexNum} instance for writing the data id from
	 */
	private FlexNum dataId = ownDataId;
	
	/**
	 * {@link FlexNum} instance for reading the channel id into
	 */
	private final FlexNum ownChannelId = new FlexNum();
	/**
	 * {@link FlexNum} instance for writing the channel id from
	 */
	private FlexNum channelId = ownChannelId;
	
	/**
	 * {@link FlexNum} instance for reading the channel data length into
	 */
	private final FlexNum ownChannelDataLength = new FlexNum(0, Integer.MAX_VALUE);
	/**
	 * {@link FlexNum} instance for writing the channel data length from
	 */
	private FlexNum channelDataLength = ownChannelDataLength;
	
	public LinkPacketHeader() {
		
	}
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		(dataId = ownDataId).read(byteBuf);
		(channelId = ownChannelId).read(byteBuf);
		(channelDataLength = ownChannelDataLength).read(byteBuf);
	}
	
	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		dataId.write(byteBuf);
		channelId.write(byteBuf);
		channelDataLength.write(byteBuf);
	}

	@Override
	public int length() {
		return dataId.length() + channelId.length() + channelDataLength.length();
	}

	@Override
	public String toString() {
		return String.format("LinkPacket(dataId=%d, channelId=%d, channelDataLength=%d)", dataId.getNum(), channelId.getNum(), channelDataLength.getNum());
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}
	
	/**
	 * returns the read/written data id {@link FlexNum}
	 * @return the read/written data id {@link FlexNum}
	 */
	public FlexNum getDataId() {
		return dataId;
	}
	
	/**
	 * returns the read/written channel id {@link FlexNum}
	 * @return the read/written channel id {@link FlexNum}
	 */
	public FlexNum getChannelId() {
		return channelId;
	}
	
	/**
	 * returns the read/written channel data length {@link FlexNum}
	 * @return the read/written channel data length {@link FlexNum}
	 */
	public FlexNum getChannelDataLength() {
		return ownChannelDataLength;
	}
	
	/**
	 * sets the data id that will be written
	 * @param dataId the data id that will be written
	 */
	public void setDataId(long dataId) {
		(this.dataId = this.ownDataId).setNum(dataId);
	}
	
	/**
	 * sets the channel id that will be written
	 * @param channelId the channel id that will be written
	 */
	public void setChannelId(long channelId) {
		(this.channelId = this.ownChannelId).setNum(channelId);
	}
	
	/**
	 * sets the channel data length that will be written
	 * @param channelDataLength the channel data length that will be written
	 */
	public void setChannelDataLength(int channelDataLength) {
		(this.channelDataLength = this.ownChannelDataLength).setNum(channelDataLength);
	}
	
}