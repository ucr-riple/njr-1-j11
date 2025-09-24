package org.washcom.cardgames.doors;

import org.washcom.cardgames.core.Card;
import org.washcom.cardgames.core.Denomination;

/**
 * In a fixed-royalty card value policy, the value of royalty is fixed (10 by default), and the value of all other cards is
 * their face value.
 * 
 * @author Joe
 */
public class FixedRoyaltyPolicy extends DenominationValuePolicy {
    
    public static final int DEFAULT_ROYALTY_VALUE = 10;
    
    private int royaltyValue;

    public FixedRoyaltyPolicy() {
        this(DEFAULT_ROYALTY_VALUE);
    }
    
    public FixedRoyaltyPolicy(int royaltyValue) {
        this.royaltyValue = royaltyValue;
    }
    
    @Override
    public int getValue(Denomination denom) {
        return denom.isRoyalty() ? royaltyValue : super.getValue(denom);
    }
    
}
