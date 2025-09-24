package org.dclayer.net.component;


import org.dclayer.crypto.key.Key;
import org.dclayer.crypto.key.RSAKey;
import org.dclayer.crypto.key.RSAPublicKey;
import org.dclayer.exception.crypto.InsufficientKeySizeException;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.componentinterface.RSAKeyComponentI;

/**
 * a {@link PacketComponent} containing data for the RSA crypto method
 */
public class RSAKeyComponent extends AbsKeyComponent implements RSAKeyComponentI {
	
	/**
	 * the modulus
	 */
	private BigIntComponent modulusBigIntComponent = new BigIntComponent();
	
	/**
	 * the exponent
	 */
	private BigIntComponent exponentBigIntComponent = new BigIntComponent();

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		modulusBigIntComponent.read(byteBuf);
		exponentBigIntComponent.read(byteBuf);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		modulusBigIntComponent.write(byteBuf);
		exponentBigIntComponent.write(byteBuf);
	}

	@Override
	public int length() {
		return modulusBigIntComponent.length() + exponentBigIntComponent.length();
	}

	@Override
	public String toString() {
		return "RSAKeyComponent";
	}

	@Override
	public PacketComponent[] getChildren() {
		return new PacketComponent[] { modulusBigIntComponent, exponentBigIntComponent };
	}

	@Override
	public byte getTypeId() {
		return Key.RSA;
	}

	@Override
	public Key getKey() throws InsufficientKeySizeException {
		return new RSAPublicKey(modulusBigIntComponent.getBigInteger(), exponentBigIntComponent.getBigInteger());
	}
	
	@Override
	public void setKey(RSAKey rsaKey) {
		this.modulusBigIntComponent.setBigInteger(rsaKey.getModulus());
		this.exponentBigIntComponent.setBigInteger(rsaKey.getExponent());
	}
	
}
