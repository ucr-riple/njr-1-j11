package org.washcom.cardgames.doors;

import org.washcom.cardgames.core.Denomination;

/**
 * Same as a denomination value policy except that the denomination of royalty is the number of letters in their name.
 * 
 * @author Joe
 */
public class RoyaltyAsSpelledPolicy extends DenominationValuePolicy {

    @Override
    public int getValue(Denomination denom) {
        return denom.isRoyalty() ? denom.toString().length() : super.getValue(denom);
    }
    
}
