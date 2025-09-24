package org.dclayer.application.applicationchannel;

import java.io.BufferedOutputStream;
import java.io.InputStream;

import org.dclayer.crypto.key.Key;
import org.dclayer.net.applicationchannel.ApplicationChannelTarget;

public interface ApplicationChannel {
	
	public boolean wasInitiatedLocally();
	
	public ApplicationChannelTarget getApplicationChannelTarget();
	
	public Key getRemotePublicKey();
	public String getActionIdentifier();
	
	public InputStream getInputStream();
	public BufferedOutputStream getOutputStream();

}
