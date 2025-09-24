package org.dclayer.crypto.key;

import java.math.BigInteger;

import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.dclayer.exception.crypto.InsufficientKeySizeException;

public class RSAPublicKey extends RSAKey {

	public RSAPublicKey(BigInteger modulus, BigInteger exponent) throws InsufficientKeySizeException {
		super(false, modulus, exponent);
	}
	
	public RSAPublicKey(RSAKeyParameters rsaKeyParameters) throws InsufficientKeySizeException {
		super(rsaKeyParameters);
	}

	@Override
	public String toString() {
		return "RSA public key";
	}

}
