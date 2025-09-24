package org.dclayer.net.a2s.remotekey;

import java.util.concurrent.locks.ReentrantLock;

import org.dclayer.crypto.key.RemoteRSAKeyInterface;
import org.dclayer.net.Data;

public class ApplicationConnectionRemoteRSAKeyInterface implements RemoteRSAKeyCommunicationListener, RemoteRSAKeyInterface {
	
	private RemoteRSAKeyCommunicationInterface remoteRSAKeyCommunicationInterface;
	private int numBits;
	private int responseNum;
	
	private Data responseData = null;
	
	private ReentrantLock actionLock = new ReentrantLock();
	
	public ApplicationConnectionRemoteRSAKeyInterface(int numBits, RemoteRSAKeyCommunicationInterface remoteRSAKeyCommunicationInterface) {
		this.numBits = numBits;
		this.remoteRSAKeyCommunicationInterface = remoteRSAKeyCommunicationInterface;
		
		remoteRSAKeyCommunicationInterface.setRemoteRSAKeyCommunicationListener(this);
	}

	@Override
	public int getNumBits() {
		return numBits;
	}

	@Override
	public Data encrypt(Data plainData) {
		
		actionLock.lock();
		
		remoteRSAKeyCommunicationInterface.sendEncryptMessage(plainData);
		try {
			synchronized(this) { wait(); }
		} catch (InterruptedException e) {
			return null;
		} finally {
			actionLock.unlock();
		}
		
		// TODO validate
		return responseData;
		
	}

	@Override
	public Data decrypt(Data cipherData) {
		
		actionLock.lock();
		
		remoteRSAKeyCommunicationInterface.sendDecryptMessage(cipherData);
		try {
			synchronized(this) { wait(); }
		} catch (InterruptedException e) {
			return null;
		} finally {
			actionLock.unlock();
		}
		
		// TODO validate
		return responseData;
		
	}

	@Override
	public int queryMaxEncryptionBlockNumBytes() {
		
		actionLock.lock();
		
		remoteRSAKeyCommunicationInterface.sendMaxEncryptionBlockNumBytesRequestMessage();
		try {
			synchronized(this) { wait(); }
		} catch (InterruptedException e) {
			return 0;
		} finally {
			actionLock.unlock();
		}
		
		return responseNum;
		
	}

	@Override
	public synchronized void onResponseDataMessage(Data responseData) {
		this.responseData = responseData;
		notify();
	}
	
	@Override
	public synchronized void onResponseNumMessage(int responseNum) {
		this.responseNum = responseNum;
		notify();
	}

}
