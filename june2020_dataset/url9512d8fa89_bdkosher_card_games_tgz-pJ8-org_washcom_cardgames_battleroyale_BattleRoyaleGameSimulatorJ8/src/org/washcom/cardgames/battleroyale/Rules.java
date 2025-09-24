package org.washcom.cardgames.battleroyale;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import org.washcom.cardgames.core.Denomination;
import static org.washcom.cardgames.core.Denomination.*;

/**
 * Utility class for handling some of the more mundane aspects of dealing with Battle Royale rules.
 * 
 * @author Joe
 */
public class Rules {

    public static int MAX_VALUE_DIFF_FEE = 3;
    /**
     * The maximum threshold at which the difference between card values results in a battle.
     */
    public static int VALUE_DIFF_BATTLE_THRESHOLD = 2;
    
    public static final Map<Denomination, Integer> battleRoyaleFees = new HashMap<Denomination, Integer>() {
        {
            put(Denomination.ACE, 4);
            put(Denomination.KING, 3);
            put(Denomination.QUEEN, 2);
            put(Denomination.JACK, 1);
        }
    };
    
    /**
     * Returns the battle royale fee for a given card.
     * @return null if the card is not a battle royale-eligible card.
     */
    public static Integer battleRoyaleFee(BattleCard card) {
        Preconditions.checkNotNull(card);
        return battleRoyaleFees.get(card.getCard().getDenomination());
    }
    
    /**
     * Returns true if the difference provided is under the battle threshold.
     * That is, if true is returned, more battling is necessary.
     */
    public static boolean isDifferentialUnderBattleThreshold(int diff) {
        return Math.abs(diff) <= VALUE_DIFF_BATTLE_THRESHOLD;
    }

    /**
     * Returns true if he two cards are denominated such that they are engaged
     * in a battle royale. That is, both cards are royalty.
     */
    public static boolean isBattleRoyale(BattleCard one, BattleCard two) {
        Preconditions.checkNotNull(one);
        Preconditions.checkNotNull(two);
        return one.isBattleRoyaleEligible() && two.isBattleRoyaleEligible();
    }

    /*
     * Checks if the two cards are of the given two denominations with no respect for order. Thus, the order of the
     * final two arguments makes no difference to the result of the method.
     */
    private static boolean isDenominationCombo(BattleCard one, BattleCard two, Denomination denom, Denomination otherDenom) {
        Preconditions.checkNotNull(one);
        Preconditions.checkNotNull(two);
        Preconditions.checkNotNull(denom);
        Preconditions.checkNotNull(otherDenom);
        return (one.getCard().getDenomination() == denom && two.getCard().getDenomination() == otherDenom)
                || (two.getCard().getDenomination() == denom && one.getCard().getDenomination() == otherDenom);
    }

    /**
     * Returns true if the two cards are Ace and Two.
     */
    public static boolean isAceTwoCombo(BattleCard one, BattleCard two) {
        return isDenominationCombo(one, two, ACE, TWO);
    }

    /**
     * Returns true if the two cards are Jack and Eight.
     */
    public static boolean isJackEightCombo(BattleCard one, BattleCard two) {
        return isDenominationCombo(one, two, JACK, EIGHT);
    }
    
    public static boolean isLosingCardAThree(BattleCard one, BattleCard two) {
        return one.getCard().getDenomination() != two.getCard().getDenomination()
                && ((one.getCard().getDenomination() == Denomination.THREE && two.getCard().getDenomination() != Denomination.TWO)
                || (two.getCard().getDenomination() == Denomination.THREE && one.getCard().getDenomination() != Denomination.TWO));
    }
    
    public static BattleCard highCard(BattleCard one, BattleCard two) {
        int diff = one.computeValueDifference(two);
        return diff > 0 ? one : two;
    }
    
    public static BattleCard highCard(BattleCard one, BattleCard two, BattleCard three) {
        return highCard(highCard(one, two), three);
    }
    
}
