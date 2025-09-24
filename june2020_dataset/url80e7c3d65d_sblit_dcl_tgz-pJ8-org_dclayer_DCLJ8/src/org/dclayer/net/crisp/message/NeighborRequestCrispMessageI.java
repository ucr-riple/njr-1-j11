package org.dclayer.net.crisp.message;

import org.dclayer.net.componentinterface.DataComponentI;
import org.dclayer.net.componentinterface.KeyEncryptedPacketComponentI;
import org.dclayer.net.lla.LLA;

public interface NeighborRequestCrispMessageI extends KeyEncryptedPacketComponentI {

	public String getActionIdentifier();
	public void setActionIdentifier(String actionIdentifier);
	
	public LLA getSenderLLA();
	public void setSenderLLA(LLA senderLLA);
	
	public boolean isResponse();
	public void setResponse(boolean response);
	
	public DataComponentI getIgnoreDataComponent();
	
}
