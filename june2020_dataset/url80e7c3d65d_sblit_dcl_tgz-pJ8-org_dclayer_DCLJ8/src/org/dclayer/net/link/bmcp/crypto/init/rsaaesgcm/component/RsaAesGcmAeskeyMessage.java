package org.dclayer.net.link.bmcp.crypto.init.rsaaesgcm.component;

import org.dclayer.net.component.DataComponent;
import org.dclayer.net.packetcomponent.Child;
import org.dclayer.net.packetcomponent.ParentPacketComponent;

public class RsaAesGcmAeskeyMessage extends ParentPacketComponent {
	
	@Child(index = 0) public DataComponent encryptedAesKey;
	
}