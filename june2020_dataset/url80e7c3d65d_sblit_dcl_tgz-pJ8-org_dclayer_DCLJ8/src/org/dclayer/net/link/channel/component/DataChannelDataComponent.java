package org.dclayer.net.link.channel.component;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.DataComponent;

/**
 * a simple channel data component containing a {@link DataComponent}
 */
public class DataChannelDataComponent extends ChannelDataComponent {
	
	/**
	 * the contained {@link DataComponent}
	 */
	private DataComponent dataComponent = new DataComponent();

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		dataComponent.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		dataComponent.write(byteBuf);
	}

	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { dataComponent };
	}

	@Override
	public int length() {
		return dataComponent.length();
	}

	@Override
	public String toString() {
		return "DataChannelDataComponent";
	}
	
	/**
	 * @return the contained {@link DataComponent}
	 */
	public DataComponent getDataComponent() {
		return dataComponent;
	}
	
	/**
	 * sets the {@link Data} to use in the contained {@link DataComponent}
	 * @param data the {@link Data} to use in the contained {@link DataComponent}
	 */
	public void setData(Data data) {
		dataComponent.setData(data);
	}
	
}
