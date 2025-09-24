package org.dclayer.net.link.channel.component;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;

/**
 * a simple channel data component containing a {@link String}
 */
public class StringChannelDataComponent extends ChannelDataComponent {
	
	/**
	 * the contained string
	 */
	private String string;

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		string = byteBuf.readString();
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		byteBuf.writeString(string);
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}

	@Override
	public int length() {
		return string.length() + 1;
	}

	@Override
	public String toString() {
		return String.format("StringChannelDataComponent(string=%s)", string);
	}
	
	/**
	 * @return the contained string
	 */
	public String getString() {
		return string;
	}
	
	/**
	 * sets the contained string
	 * @param string the contained string
	 */
	public void setString(String string) {
		this.string = string;
	}
	
}
