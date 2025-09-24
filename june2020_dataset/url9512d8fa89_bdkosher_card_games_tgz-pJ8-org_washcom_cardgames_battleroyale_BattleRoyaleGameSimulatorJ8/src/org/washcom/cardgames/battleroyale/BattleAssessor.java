package org.washcom.cardgames.battleroyale;

import java.util.Map;

/**
 * Manages the rules for assessing which cards win a battle and the fees associated with playing certain card combinations.
 * 
 * TODO these could probably be combined into a single method.
 * 
 * @author Joe
 */
public interface BattleAssessor {

    /**
     * Picks the winning battle card (a pointer to the winner and the card played by the winner). If there is no decisive winner,
     * null is returned. 
     * 
     * @param battle - cannot be null
     */
    BattleCard pickWinner(Battle battle);

    /**
     * Returns a mapping of Players to the number of cards they must pay as fees in order to continue the battle. Battlers who
     * are not eligible to continue battle should not be present in the map.
     * 
     * Implementations may presume that the battle has no definitive winner and need not check that there is a winner first.
     * 
     * Implementations need not check to see if the player is able to pay the fee.
     * 
     * @param battle - cannot be null
     */
    Map<Player, Integer> determineFees(Battle battle);
}
