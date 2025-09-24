package org.dclayer.net.component;


import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.buf.DataByteBuf;
import org.dclayer.net.componentinterface.DataComponentI;

/**
 * a {@link PacketComponent} containing data
 */
public class DataComponent extends PacketComponent implements DataComponentI {
	/**
	 * the contained data
	 */
	private Data ownData;
	private Data data;
	
	/**
	 * the length of the data
	 */
	private FlexNum dataLength;
	
	private DataByteBuf dataByteBuf = null;
	
	/**
	 * creates an empty {@link DataComponent} that must first be read into before it can be written from
	 */
	public DataComponent() {
		this.dataLength = new FlexNum();
	}
	
	private void verifyLength(int curLength) {
		if(this.dataLength.getNum() == curLength) return;
		this.dataLength.setNum(curLength);
	}
	
	private void verifyLength() {
		int curLength = data == null ? 0 : data.length();
		verifyLength(curLength);
	}
    
    /**
     * returns the {@link Data} holding the data contained in this {@link DataComponent}
     * @return the {@link Data} holding the data contained in this {@link DataComponent}
     */
	@Override
    public Data getData() {
    	return data;
    }
	
    /**
     * sets the Data that is contained in this {@link DataComponent}
     * @param data the Data that should be contained in this {@link DataComponent}
     */
    @Override
	public void setData(Data data) {
		this.data = data;
		verifyLength();
	}

	@Override
	public void setData(PacketComponentI packetComponent) throws BufException {
		
		if(ownData == null) ownData = new Data();
		if(dataByteBuf == null) dataByteBuf = new DataByteBuf();
		
		data = ownData;
		data.prepare(packetComponent.length());
		dataByteBuf.setData(data);
		
		packetComponent.write(dataByteBuf);
		
		verifyLength();
		
	}

	@Override
	public void getData(PacketComponentI packetComponent) throws BufException, ParseException {
		
		if(dataByteBuf == null) dataByteBuf = new DataByteBuf();
		dataByteBuf.setData(data);
		packetComponent.read(dataByteBuf);
		
	}

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		dataLength.read(byteBuf);

		if(ownData == null) {
			ownData = new Data((int) dataLength.getNum());
		} else {
			ownData.prepare((int) dataLength.getNum());
		}
		byteBuf.read(ownData);
		data = ownData;
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		verifyLength();
		dataLength.write(byteBuf);
		if(data != null) byteBuf.write(data);
	}

	@Override
	public int length() {
		verifyLength();
		return dataLength.length() + (int) dataLength.getNum();
	}

	@Override
	public int lengthForDataLength(int dataLength) {
		verifyLength(dataLength);
		return this.dataLength.length() + (int) this.dataLength.getNum();
	}

	@Override
	public String toString() {
		verifyLength();
		return String.format("DataComponent(len=%s): %s", (int) dataLength.getNum(), data);
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}
	
}
