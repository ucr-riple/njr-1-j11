package org.dclayer;

import java.net.InetAddress;
import java.util.Random;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.Data;

public class PreLinkCommunicationManager implements HierarchicalLevel {
	
	public static final int RANDOM_DATA_LENGTH = 8;
	public static final int IGNORE_DATA_LENGTH = 8;
	
	public static final long RANDOM_DATA_RENEW_INTERVAL = 60000000000L;
	public static final long IGNORE_DATA_RENEW_INTERVAL = 60000000000L;
	
	public static class Result {
		public boolean done = false;
		public Data echoData = null;
		public Data firstLinkPacketPrefixData = null;
	}
	
	//
	
	// TODO probably renew this periodically
	private Data connectData = new Data(RANDOM_DATA_LENGTH); {
		new Random().nextBytes(connectData.getData());
	}
	
	private Digest digest = new SHA1Digest();
	private final int digestSize = digest.getDigestSize();
	
	private Data hashData = new Data();
	private Data hashedData = new Data(digestSize);
	
	private Data randomData;
	private long lastRandomDataRenew;
	
	private Data ignoreData;
	private long lastIgnoreDataRenew;
	
	private Data echoData = new Data();
	
	private Result result = new Result();
	
	private HierarchicalLevel parentHierarchicalLevel;
	
	public PreLinkCommunicationManager(HierarchicalLevel parentHierarchicalLevel) {
		this.parentHierarchicalLevel = parentHierarchicalLevel;
	}
	
	public Data getCurrentIgnoreData() {
		
		long now = System.nanoTime();
		long timePassed = (now - lastIgnoreDataRenew);
		long timeLeft = (IGNORE_DATA_RENEW_INTERVAL - timePassed);
		
		if(ignoreData == null || timeLeft < 1000000000L) {
			
			ignoreData = new Data(IGNORE_DATA_LENGTH);
			new Random().nextBytes(ignoreData.getData());
			lastIgnoreDataRenew = now;
			
		} else if(timeLeft < (IGNORE_DATA_RENEW_INTERVAL/5)) {
			
			lastIgnoreDataRenew = now - 4*IGNORE_DATA_RENEW_INTERVAL/5;
			
		}
		
		return ignoreData;
		
	}

	public Result permit(InetAddress inetAddress, int port, Data receivedData) {
		
		long now = System.nanoTime();
		
		if(randomData == null || (now - lastRandomDataRenew) > RANDOM_DATA_RENEW_INTERVAL) {
			lastRandomDataRenew = now;
			if(randomData == null) randomData = new Data(RANDOM_DATA_LENGTH);
			new Random().nextBytes(randomData.getData());
			Log.debug(this, "renewed random data: %s", randomData);
		}
		
		if(ignoreData != null) {
			
			if((now - lastIgnoreDataRenew) > IGNORE_DATA_RENEW_INTERVAL) {
				ignoreData = null;
				Log.debug(this, "removed ignore data");
			} else if(receivedData.equals(0, ignoreData, 0, ignoreData.length())) {
				
				Log.debug(this, "ignoring data that starts with ignoreData (%s): %s", ignoreData, receivedData);
				return null;
				
			}
		}
		
		byte[] address = inetAddress.getAddress();
		
		hashData.prepare(address.length + 2 + RANDOM_DATA_LENGTH);
		hashData.setBytes(0, address, 0, address.length);
		hashData.setByte(address.length, (byte)((port >> 8) & 0xFF));
		hashData.setByte(address.length+1, (byte)(port & 0xFF));
		hashData.setBytes(address.length+2, randomData.getData(), randomData.offset(), randomData.length());

		digest.update(hashData.getData(), hashData.offset(), hashData.length());
		digest.doFinal(hashedData.getData(), 0);
		
		Log.debug(this, "hashData=%s hashedData=%s receivedData=%s", hashData, hashedData, receivedData);
		
		if(receivedData.length() >= digestSize && hashedData.equals(0, receivedData, 0, digestSize)) {
			
			receivedData.relativeReset(digestSize, receivedData.length() - digestSize);
			Data firstLinkPacketPrefixData = new Data(RANDOM_DATA_LENGTH);
			new Random().nextBytes(firstLinkPacketPrefixData.getData());
			echoData.prepare(receivedData.length() + firstLinkPacketPrefixData.length());
			echoData.setBytes(0, receivedData.getData(), receivedData.offset(), receivedData.length());
			echoData.setBytes(receivedData.length(), firstLinkPacketPrefixData.getData(), firstLinkPacketPrefixData.offset(), firstLinkPacketPrefixData.length());
			
			Log.debug(this, "receivedData is valid, echoing the part of it which exceeds hashedData (%s) and appending expected first link prefix data (%s): %s", receivedData, firstLinkPacketPrefixData, echoData);
			
			result.done = true;
			result.echoData = echoData;
			result.firstLinkPacketPrefixData = firstLinkPacketPrefixData;
			
		} else {
		
			Log.debug(this, "receivedData is invalid, replying with hashedData: %s", hashedData);
			result.done = false;
			result.echoData = hashedData;
			
		}
		
		return result;
		
	}
	
	public Result echo(Data remoteData) {
		
		Log.debug(this, "received data from remote we're trying to connect to: %s", remoteData);
		
		if(connectData.equals(0, remoteData, 0, connectData.length())) {
			
			// done
			remoteData.relativeReset(connectData.length(), remoteData.length() - connectData.length());
			Data firstLinkPacketPrefixData = remoteData.copy();
			Log.debug(this, "first part of received data matches connectData, pre-link communication completed, first link packet prefix data: %s", firstLinkPacketPrefixData);
			result.done = true;
			result.firstLinkPacketPrefixData = firstLinkPacketPrefixData;
			
		} else {
		
			echoData.prepare(remoteData.length() + connectData.length());
			echoData.setBytes(0, remoteData.getData(), remoteData.offset(), remoteData.length());
			echoData.setBytes(remoteData.length(), connectData.getData(), connectData.offset(), connectData.length());
			
			Log.debug(this, "echoing received data with appended connectData, sending: %s", echoData);
			
			result.done = false;
			result.echoData = echoData;
			
		}
		
		return result;
		
	}

	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return parentHierarchicalLevel;
	}
	
}
