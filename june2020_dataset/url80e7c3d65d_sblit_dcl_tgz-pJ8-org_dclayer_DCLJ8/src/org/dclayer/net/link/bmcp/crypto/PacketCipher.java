package org.dclayer.net.link.bmcp.crypto;

import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.buf.BufNoReadException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;

public abstract class PacketCipher<T extends ByteBuf> {
	
	protected final T writeByteBuf = makeWriteByteBuf();
	
	@SuppressWarnings("unchecked")
	protected T makeWriteByteBuf() {
		return (T) new ByteBuf() {
			
			@Override
			public void write(byte[] buf, int offset, int length) throws BufException {
				process(buf, offset, length);
			}
			
			@Override
			public void write(byte b) throws BufException {
				process(b);
			}
			
			@Override
			public void read(byte[] buf, int offset, int length) throws BufException {
				throw new BufNoReadException();
			}
			
			@Override
			public byte read() throws BufException {
				throw new BufNoReadException();
			}
			
		};
	}
	
	protected void resetWriteByteBuf() {
		
	}
	
	//
	
	/**
	 * encrypts the given {@link PacketComponentI} object
	 * @param outData the {@link Data} the encrypted bytes should be stored in
	 * @param prefixData a {@link Data} object containing bytes that should be copied to the start of outData, or null if outData should not be prefixed
	 * @param packetComponents the {@link PacketComponentI} object(s) to encrypt
	 */
	public final Data encrypt(Data outData, Data prefixData, PacketComponentI... packetComponents) throws CryptoException {
		
		resetWriteByteBuf();
		
		int length = 0;
		for(PacketComponentI packetComponent : packetComponents) {
			length += packetComponent.length();
		}
		
		initEncryption(outData, prefixData, length);
		
		try {
			for(PacketComponentI packetComponent : packetComponents) {
				writeByteBuf.write(packetComponent);
			}
		} catch (BufException e) {
			throw new CryptoException(e);
		}
		
		return finishEncryption();
		
	}
	
	/**
	 * finishes encryption
	 * @return {@link Data} object containing the encrypted bytes
	 * @throws CryptoException
	 */
	protected abstract Data finishEncryption() throws CryptoException;
	
	/**
	 * decrypts the given encrypted data
	 * @param encryptedData {@link Data} containing the encrypted data
	 * @param outData the {@link Data} the decrypted bytes should be stored in
	 * @return {@link Data} object containing the plain body bytes
	 */
	public abstract Data decrypt(Data encryptedData, Data outData) throws CryptoException;
	
	//
	
	/**
	 * prepares for encryption
	 * @param outData the {@link Data} the encrypted bytes should be stored in
	 * @param prefixData a {@link Data} object containing bytes that should be copied to the start of outData, or null if outData should not be prefixed
	 * @param inDataLength the amount of bytes to be encrypted, if known. zero otherwise.
	 */
	protected abstract void initEncryption(Data outData, Data prefixData, int inDataLength) throws CryptoException;
	
	/**
	 * processes the given bytes
	 * @param buf
	 * @param offset
	 * @param length
	 */
	protected abstract void process(byte[] buf, int offset, int length);
	
	/**
	 * processes the given byte
	 * @param b
	 */
	protected abstract void process(byte b);
	
}
