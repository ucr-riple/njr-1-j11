package org.dclayer.net.interservice.component;

import java.util.ArrayList;
import java.util.List;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.lla.LLA;

/**
 * a {@link PacketComponent} containing a list of {@link LowerLevelAddressComponent}s
 */
public class LowerLevelAddressListComponent extends PacketComponent {
	
	private FlexNum count = new FlexNum(0, Integer.MAX_VALUE);
	/**
	 * the list of {@link LowerLevelAddressComponent}s contained in this {@link PacketComponent}
	 */
	private List<LLA> addresses;
	
	/**
	 * the length in bytes of this {@link PacketComponent}
	 */
	private int length;
	
	/**
	 * calculates and updates the length of this {@link PacketComponent}
	 */
	private void updateLength() {
		int length = count.length();
		for(LLA lla : addresses) {
			length += lla.length();
		}
		this.length = length;
	}
    
	@Override
	public String toString() {
		return String.format("LowerLevelAddressListComponent(numaddresses=%d)", addresses.size());
	}

	@Override
	public PacketComponent[] getChildren() {
		return addresses.toArray(new PacketComponent[addresses.size()]);
	}

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		this.count.read(byteBuf);
		final int count = (int) this.count.getNum();
		this.addresses = new ArrayList<LLA>(count);
		for(int i = 0; i < count; i++) {
			addresses.add(LLA.fromByteBuf(byteBuf));
		}
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		count.write(byteBuf);
		for(LLA lla : addresses) {
			lla.write(byteBuf);
		}
	}

	@Override
	public int length() {
		return length;
	}

	/**
	 * @return the newly created list of {@link LLA}s contained in this {@link PacketComponent}.
	 * the returned object has been newly created while this {@link PacketComponent} was read and can be used without restrictions.
	 */
    public List<LLA> getNewAddresses() {
    	return addresses;
    }
    
    /**
     * sets the list of {@link LLA}s contained in this {@link PacketComponent}
     * @param addresses the list of {@link LLA}s to use
     */
    public void setAddresses(List<LLA> addresses) {
    	this.addresses = addresses;
    	this.count.setNum(addresses.size());
    	updateLength();
    }
	
}
