package org.dclayer.net.link.control.packetbackup;

public class Properties {
	
	private final PacketBackup packetBackup;
	
	public Properties(PacketBackup packetBackup) {
		this.packetBackup = packetBackup;
	}
	
	public PacketBackup getPacketBackup() {
		return packetBackup;
	}
	
}
