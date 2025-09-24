package org.dclayer.crypto.key;

import java.math.BigInteger;

import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.dclayer.exception.crypto.InsufficientKeySizeException;

public class RSAPrivateKey extends RSAKey {

	public RSAPrivateKey(BigInteger modulus, BigInteger exponent) throws InsufficientKeySizeException {
		super(true, modulus, exponent);
	}
	
	public RSAPrivateKey(RSAKeyParameters rsaKeyParameters) throws InsufficientKeySizeException {
		super(rsaKeyParameters);
	}

	@Override
	public String toString() {
		return "RSA private key";
	}

}
