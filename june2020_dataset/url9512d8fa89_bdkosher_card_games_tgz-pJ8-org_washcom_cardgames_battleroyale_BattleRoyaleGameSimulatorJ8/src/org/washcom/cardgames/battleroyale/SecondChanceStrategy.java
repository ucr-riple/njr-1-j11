package org.washcom.cardgames.battleroyale;

import java.util.List;

public interface SecondChanceStrategy {
    
    public boolean shouldTryForAnotherCard(BattleCard three, List<BattleCard> opponents);

}
