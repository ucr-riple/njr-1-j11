package org.dclayer.net.link.bmcp.message;
import org.dclayer.net.component.ArrayPacketComponent;
import org.dclayer.net.link.bmcp.crypto.init.component.CryptoInitializerIdentifierComponent;
import org.dclayer.net.packetcomponent.Child;
import org.dclayer.net.packetcomponent.ParentPacketComponent;

public class ConnectRequestMessage extends ParentPacketComponent {
	
	@Child(index = 0, create = false) public ArrayPacketComponent<CryptoInitializerIdentifierComponent> cryptoInitializerIdentifierComponents = new ArrayPacketComponent<CryptoInitializerIdentifierComponent>() {
		@Override
		protected CryptoInitializerIdentifierComponent newElementPacketComponent() {
			return new CryptoInitializerIdentifierComponent();
		}
	};
	
}
