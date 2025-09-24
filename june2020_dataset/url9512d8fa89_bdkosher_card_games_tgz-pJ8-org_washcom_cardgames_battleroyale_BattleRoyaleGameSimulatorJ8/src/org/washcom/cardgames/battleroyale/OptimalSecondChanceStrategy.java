package org.washcom.cardgames.battleroyale;

import java.util.List;

import org.washcom.cardgames.core.Denomination;

public class OptimalSecondChanceStrategy implements SecondChanceStrategy {

    @Override
    public boolean shouldTryForAnotherCard(BattleCard three, List<BattleCard> opponents) {
        //If opponent(s) only have twos, don't bother redrawing
        if (containsOnlyTwos(opponents)) {
            return false;
        }
        
        for (BattleCard card : opponents) {
            if (card.getCard().getDenomination() == Denomination.ACE ||
                    card.getCard().getDenomination().getValue() > 8) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean containsOnlyTwos(List<BattleCard> opponents) {
        for (BattleCard card : opponents) {
            if (card.getCard().getDenomination() != Denomination.TWO) {
                return false;
            }
        }
        return true;
    }

}
