package org.dclayer.application.applicationchannel;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.dclayer.application.applicationchannel.slot.ApplicationChannelSlot;
import org.dclayer.callback.VoidCallback;
import org.dclayer.crypto.Crypto;
import org.dclayer.crypto.key.Key;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.net.Data;
import org.dclayer.net.applicationchannel.ApplicationChannelTarget;
import org.dclayer.net.buf.AsyncPipeByteBuf;
import org.dclayer.net.buf.ByteBufInputStream;

public abstract class AbsApplicationChannel implements ApplicationChannel {
	
	private ApplicationChannelSlot applicationChannelSlot;
	private ApplicationChannelActionListener applicationChannelActionListener;
	
	private boolean locallyInitiated;
	
	private Data remotePublicKeyFingerprint;
	private ApplicationChannelTarget applicationChannelTarget;
	
	private ByteBufInputStream byteBufInputStream;
	private BufferedOutputStream bufferedOutputStream;
	
	public AbsApplicationChannel(ApplicationChannelTarget applicationChannelTarget, ApplicationChannelActionListener applicationChannelActionListener, boolean locallyInitiated) {
		this.applicationChannelTarget = applicationChannelTarget;
		this.applicationChannelActionListener = applicationChannelActionListener;
		this.locallyInitiated = locallyInitiated;
		this.remotePublicKeyFingerprint = Crypto.sha1(getRemotePublicKey().toData());
	}
	
	public void setApplicationChannelSlot(ApplicationChannelSlot applicationChannelSlot) {
		this.applicationChannelSlot = applicationChannelSlot;
	}
	
	public ApplicationChannelSlot getApplicationChannelSlot() {
		return applicationChannelSlot;
	}
	
	public void setApplicationChannelActionListener(ApplicationChannelActionListener applicationChannelActionListener) {
		this.applicationChannelActionListener = applicationChannelActionListener;
	}
	
	public ApplicationChannelActionListener getApplicationChannelActionListener() {
		return applicationChannelActionListener;
	}
	
	@Override
	public boolean wasInitiatedLocally() {
		return locallyInitiated;
	}

	@Override
	public ApplicationChannelTarget getApplicationChannelTarget() {
		return applicationChannelTarget;
	}

	@Override
	public Key getRemotePublicKey() {
		return applicationChannelTarget.getRemoteAddress().getKeyPair().getPublicKey();
	}

	@Override
	public String getActionIdentifier() {
		return applicationChannelTarget.getActionIdentifier();
	}
	
	@Override
	public final String toString() {
		return String.format("%s (actionIdentifier=%s, remote public key fingerprint %s)", getName(), getActionIdentifier(), remotePublicKeyFingerprint);
	}
	
	//
	
	public final void makePipes(final VoidCallback<Data> dataCallback) {
		
		this.bufferedOutputStream = new BufferedOutputStream(new OutputStream() {
			
			Data bufData = new Data();
			Data singleByteData = new Data(1);
			
			@Override
			public void write(int b) throws IOException {
				singleByteData.setByte(0, (byte)b);
				dataCallback.callback(singleByteData);
			}
			
			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				bufData.reset(b, off, len);
				dataCallback.callback(bufData);
			}
			
		});
		
		final AsyncPipeByteBuf asyncPipeByteBuf = new AsyncPipeByteBuf(512);
		this.byteBufInputStream = new ByteBufInputStream(asyncPipeByteBuf);
		
	}
	
	public final void pushData(Data data) {
		try {
			byteBufInputStream.getByteBuf().write(data);
		} catch (BufException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public InputStream getInputStream() {
		return byteBufInputStream;
	}
	
	@Override
	public BufferedOutputStream getOutputStream() {
		return bufferedOutputStream;
	}
	
	//
	
	protected abstract String getName();

}
