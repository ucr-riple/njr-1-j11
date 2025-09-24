package org.dclayer.crypto.challenge;

import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.net.Data;

public abstract class CryptoChallenge {

	public abstract Data makeChallengeData();
	public abstract boolean verifySolvedData(Data solvedData) throws CryptoException;
	
	public abstract Data solveChallengeData(Data challengeData) throws CryptoException;
	
}
