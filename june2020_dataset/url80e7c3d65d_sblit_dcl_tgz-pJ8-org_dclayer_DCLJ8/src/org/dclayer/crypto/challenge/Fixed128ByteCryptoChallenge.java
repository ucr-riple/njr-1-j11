package org.dclayer.crypto.challenge;

import java.util.Random;

import org.dclayer.crypto.key.Key;
import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.net.Data;

public class Fixed128ByteCryptoChallenge extends CryptoChallenge {
	
	// TODO this is completely MITM-vulnerable
	
	// leave space for both random padding of plain text to encrypt
	// and for OAEP padding
	public static final int CHALLENGE_DATA_MAXNUMBYTES = 64;
	public static final int PLAINDATA_PADDING = 64;
	
	//
	
	private Key key;
	private Data challengeData;
	
	public Fixed128ByteCryptoChallenge(Key key) {
		this.key = key;
	}
	
	public Key getKey() {
		return key;
	}

	// crypto challenge resolution
	
	public Data solveChallengeData(Data challengeData) throws CryptoException {
		Data cryptData = new Data(challengeData.length() + PLAINDATA_PADDING);
		byte[] padding = new byte[PLAINDATA_PADDING];
		(new Random()).nextBytes(padding); // TODO is a simple random padding sufficient?
		cryptData.setBytes(0, padding, 0, padding.length);
		cryptData.setBytes(padding.length, challengeData.getData(), challengeData.offset(), challengeData.length());
		return key.encrypt(cryptData);
	}

	// crypto challenge verification

	public Data makeChallengeData() {
		byte[] bytes = new byte[CHALLENGE_DATA_MAXNUMBYTES];
		(new Random()).nextBytes(bytes);
		this.challengeData = new Data(bytes);
		return challengeData;
	}

	public boolean verifySolvedData(Data solvedData) throws CryptoException {
		Data plainData = key.decrypt(solvedData);
		return (plainData.length() == (CHALLENGE_DATA_MAXNUMBYTES+PLAINDATA_PADDING))
				&& this.challengeData.equals(0, plainData, PLAINDATA_PADDING, CHALLENGE_DATA_MAXNUMBYTES);
	}
	
}
