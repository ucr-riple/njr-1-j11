package org.dclayer.net.link.bmcp.crypto.init.rsaaesgcm.component;

import org.dclayer.net.component.KeyComponent;
import org.dclayer.net.packetcomponent.Child;
import org.dclayer.net.packetcomponent.ParentPacketComponent;

public class RsaAesGcmPubkeyMessage extends ParentPacketComponent {
	
	@Child(index = 0) public KeyComponent pubkeyComponent;
	
}