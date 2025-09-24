package org.washcom.cardgames.doors;

import org.washcom.cardgames.core.Card;
import org.washcom.cardgames.core.Denomination;

/**
 * The value of a card is determined by its denomination alone.
 * 
 * @author Joe
 */
public class DenominationValuePolicy implements CardValuePolicy {

    @Override
    public int getValue(Card card) {
        return getValue(card.getDenomination());
    }
    
    public int getValue(Denomination denom) {
        return denom.getValue();
    }
    
}
