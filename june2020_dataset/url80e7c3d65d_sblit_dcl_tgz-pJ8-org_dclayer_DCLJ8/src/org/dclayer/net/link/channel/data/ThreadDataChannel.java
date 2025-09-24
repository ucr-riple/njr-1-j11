package org.dclayer.net.link.channel.data;

import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.link.Link;

public abstract class ThreadDataChannel extends DataChannel implements Runnable {

	/**
	 * Thread that is calling the abstract {@link DataChannel#readConstantly(ByteBuf)} function
	 */
	private Thread thread = new Thread(this);

	public ThreadDataChannel(Link link, long channelId, String channelName) {
		super(link, channelId, channelName);
	}
	
	@Override
	public void onOpen(boolean initiator) {
		thread.start();
		onOpenChannel(initiator);
	}
	
	/**
	 * executed in Thread {@link DataChannel#thread}
	 */
	@Override
	public void run() {
		readConstantly(getReadByteBuf());
	}
	
	@Override
	public final void onClose() {
		endReadByteBuf();
		onCloseChannel();
	}
	
	/**
	 * called when this channel is actually opened (either requested by the
	 * remote and accepted or requested locally and confirmed by the remote)
	 * @param initiator true if we requested this channel, false if the remote requested it
	 */
	protected abstract void onOpenChannel(boolean initiator);
	/**
	 * called when this channel is closed
	 */
	protected abstract void onCloseChannel();
	/**
	 * called by this {@link DataChannel}'s Thread {@link DataChannel#thread}.<br />
	 * only return from this method if this channel should be closed.
	 * @param byteBuf
	 */
	public abstract void readConstantly(ByteBuf byteBuf);
	
}
