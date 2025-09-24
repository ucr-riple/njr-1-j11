package org.dclayer.net.link.channel.component;


import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;

/**
 * a {@link PacketComponent} containing just plain data without a length field
 */
public class DataChannelPlainDataComponent extends ChannelDataComponent {
	/**
	 * the contained data
	 */
	private Data ownData;
	private Data data;
	
	/**
	 * creates a new {@link DataChannelPlainDataComponent} using the given {@link Data} for reading into
	 */
	public DataChannelPlainDataComponent(Data ownData) {
		this.data = this.ownData = ownData;
	}
    
    /**
     * returns the {@link Data} holding the data contained in this {@link DataChannelPlainDataComponent}
     * @return the {@link Data} holding the data contained in this {@link DataChannelPlainDataComponent}
     */
    public Data getData() {
    	return data;
    }
	
    /**
     * sets the Data that is contained in this {@link DataChannelPlainDataComponent}
     * @param data the Data that should be contained in this {@link DataChannelPlainDataComponent}
     */
	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		byteBuf.read(ownData);
		data = ownData;
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		byteBuf.write(data);
	}

	@Override
	public int length() {
		return data.length();
	}

	@Override
	public String toString() {
		return String.format("DataChannelPlainDataComponent(len=%d)", data.length());
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}
}
