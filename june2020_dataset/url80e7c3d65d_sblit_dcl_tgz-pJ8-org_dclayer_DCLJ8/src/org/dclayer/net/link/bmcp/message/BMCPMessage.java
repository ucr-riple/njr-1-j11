package org.dclayer.net.link.bmcp.message;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.packetcomponent.Child;
import org.dclayer.net.packetcomponent.SwitchPacketComponent;

public class BMCPMessage extends SwitchPacketComponent<PacketComponentI> {
	
	public BMCPMessage() {
		
	}
	
	public BMCPMessage(Object onReceiveObject) {
		super(onReceiveObject);
	}
	
	@Child(index = 0) public ConnectRequestMessage connectRequest;
	@Child(index = 1) public ConnectReplyMessage connectReplyMessage;
	
	@Child(index = 2) public DisconnectMessage disconnect;
	@Child(index = 3) public KillMessage kill;
	
	@Child(index = 4) public CryptoInitMessage cryptoInit;
	
	@Child(index = 5) public AckMessage ack;
	
	@Child(index = 6) public ChangeProtocolRequestMessage changeProtocolRequest;
	
	@Child(index = 7) public ChannelBlockStatusRequestMessage channelBlockStatusRequest;
	@Child(index = 8) public ChannelBlockStatusReportMessage channelBlockStatusReport;
	
	@Child(index = 9) public OpenChannelRequestMessage openChannelRequest;
	@Child(index = 10) public ThrottleMessage throttle;
	
}
