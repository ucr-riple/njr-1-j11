package org.dclayer.net.link.control.packetbackup;


public class PacketBackupCollectionProperties extends Properties {
	
	private UnreliablePacketBackupCollection removeFromUnreliablePacketBackupCollection;
	
	public PacketBackupCollectionProperties(PacketBackup packetBackup) {
		super(packetBackup);
	}
	
	public void reset() {
		removeFromUnreliablePacketBackupCollection = null;
	}
	
	public void onSent() {
		if(removeFromUnreliablePacketBackupCollection != null) {
			removeFromUnreliablePacketBackupCollection.clear(getPacketBackup());
		}
	}
	
}
