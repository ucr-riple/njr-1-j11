package org.dclayer.net.link.bmcp.crypto;

import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.net.Data;
import org.dclayer.net.buf.DataByteBuf;

public class PlainPacketCipher extends PacketCipher<DataByteBuf> {
	
	@Override
	protected DataByteBuf makeWriteByteBuf() {
		return new DataByteBuf();
	}

	@Override
	protected Data finishEncryption() throws CryptoException {
		return this.writeByteBuf.getData();
	}

	@Override
	protected void initEncryption(Data outData, Data prefixData, int inDataLength) throws CryptoException {
		
		this.writeByteBuf.setData(outData);
		
		if(prefixData != null) {
			
			this.writeByteBuf.prepare(prefixData.length() + inDataLength);
			
			try {
				this.writeByteBuf.write(prefixData);
			} catch (BufException e) {
				throw new CryptoException(e);
			}
			
		} else {
			
			this.writeByteBuf.prepare(inDataLength);
			
		}
	}
	
	@Override
	protected void process(byte[] buf, int offset, int length) {
		
	}
	
	@Override
	protected void process(byte b) {
		
	}

	@Override
	public Data decrypt(Data encryptedData, Data outData) throws CryptoException {
		return encryptedData;
	}
	
}
