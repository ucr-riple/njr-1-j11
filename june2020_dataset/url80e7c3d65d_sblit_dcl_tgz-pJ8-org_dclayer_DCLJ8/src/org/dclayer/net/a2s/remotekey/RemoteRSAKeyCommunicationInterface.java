package org.dclayer.net.a2s.remotekey;

import org.dclayer.net.Data;

public interface RemoteRSAKeyCommunicationInterface {

	public void setRemoteRSAKeyCommunicationListener(RemoteRSAKeyCommunicationListener remoteRSAKeyCommunicationListener);
	
	public void sendEncryptMessage(Data plainData);
	public void sendDecryptMessage(Data cipherData);
	public void sendMaxEncryptionBlockNumBytesRequestMessage();
	
}
