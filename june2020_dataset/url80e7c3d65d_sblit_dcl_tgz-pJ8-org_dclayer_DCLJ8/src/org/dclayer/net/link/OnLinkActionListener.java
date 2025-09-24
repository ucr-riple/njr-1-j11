package org.dclayer.net.link;

import org.dclayer.crypto.key.KeyPair;
import org.dclayer.net.link.channel.data.DataChannel;

/**
 * interface used by {@link Link} instances to call specific methods upon events
 */
public interface OnLinkActionListener<T> {
	/**
	 * called when a new {@link DataChannel} should be opened
	 * @param referenceObject the reference object passed to the {@link Link} that the channel should be created on
	 * @param channelId the channel id of the new channel
	 * @param protocol the protocol identifier of the protocol to use on the channel
	 * @return the new {@link DataChannel}
	 */
	public DataChannel onOpenChannelRequest(T referenceObject, long channelId, String protocol);
	
	public void onLinkStatusChange(T referenceObject, Link.Status oldStatus, Link.Status newStatus);
	
	public KeyPair getLinkCryptoInitializationKeyPair();
}
