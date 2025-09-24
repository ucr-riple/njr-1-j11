package org.washcom.cardgames.core;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import static org.washcom.cardgames.core.Denomination.ACE;
import static org.washcom.cardgames.core.Denomination.KING;

/**
 *
 * @author Joe
 */
public class AcesHighCardComparator implements Comparator<Card> {

    private static final Map<Denomination, Integer> battleRanks = new EnumMap<Denomination, Integer>(Denomination.class) {

        {
            for (Denomination denom : Denomination.values()) {
                /* ACE is worth 1 more than a King */
                put(denom, denom == ACE ? KING.getValue() + 1 : denom.getValue());
            }
        }
    };

    @Override
    public int compare(Card card1, Card card2) {
        return battleRanks.get(card1.getDenomination()).compareTo(battleRanks.get(card2.getDenomination()));
    }
}
