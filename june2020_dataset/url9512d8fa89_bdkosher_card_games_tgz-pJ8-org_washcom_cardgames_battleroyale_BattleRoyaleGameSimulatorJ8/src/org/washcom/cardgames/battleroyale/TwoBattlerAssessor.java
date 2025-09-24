package org.washcom.cardgames.battleroyale;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import static org.washcom.cardgames.battleroyale.Rules.*;
import org.washcom.cardgames.core.Denomination;
import static org.washcom.cardgames.core.Denomination.*;

/**
 * A BattleAssessor for when there's exactly two battlers in the battle. If not, the methods will throw
 * IllegalArgumentExceptions.
 * 
 * @author Joe
 */
public class TwoBattlerAssessor implements BattleAssessor {
    
    private static final Logger log = Logger.getLogger(TwoBattlerAssessor.class.toString());
    
    public static final Map<Denomination, Integer> battleRoyaleFees = new HashMap<Denomination, Integer>() {
        {
            put(Denomination.ACE, 4);
            put(Denomination.KING, 3);
            put(Denomination.QUEEN, 2);
            put(Denomination.JACK, 1);
        }
    };

    @Override
    public BattleCard pickWinner(Battle battle) {
        checkBattlers(battle.getBattlers());
        List<BattleCard> cards = battle.getBattleCards();
        BattleCard one = cards.get(0);
        BattleCard two = cards.get(1);
        
        /* J8 combo means that the players immediately swap decks. */
        if (isJackEightCombo(one, two)) {
            battle.getGame().swapHands(one.getPlayedBy(), two.getPlayedBy());
        }
        
        /*
         * In a Battle Royale scenario, no one wins
         */
        if (isBattleRoyale(one, two)) {
            return null;
        }
        /*
         * In an Ace-Two combo, the Two wins.
         */
        if (isAceTwoCombo(one, two)) {
            return one.getCard().getDenomination() == ACE ? two : one;
        }
        
        /*
         * If the value difference is equal or under the max value difference, no winner can be declared. 
         */
        int diff = one.computeValueDifference(two);
        if (isDifferentialUnderBattleThreshold(diff)) {
            return null;
        }
        
        return diff > 0 ? one : two;
    }

    @Override
    public Map<Player, Integer> determineFees(Battle battle) {
        checkBattlers(battle.getBattlers());
        List<BattleCard> cards = battle.getBattleCards();
        BattleCard one = cards.get(0);
        BattleCard two = cards.get(1);

        Map<Player, Integer> result = new HashMap<>();
        if (isBattleRoyale(one, two)) {
            result.put(one.getPlayedBy(), battleRoyaleFee(one));
            result.put(two.getPlayedBy(), battleRoyaleFee(two));
            return result;
        }
        
        int fee = MAX_VALUE_DIFF_FEE - Math.abs(one.computeValueDifference(two));
        result.put(one.getPlayedBy(), fee);
        result.put(two.getPlayedBy(), fee);
        return result;
    }
    
    private static void checkBattlers(List<Player> battlers) {
        Preconditions.checkState(battlers.size() == 2);
    }
}
