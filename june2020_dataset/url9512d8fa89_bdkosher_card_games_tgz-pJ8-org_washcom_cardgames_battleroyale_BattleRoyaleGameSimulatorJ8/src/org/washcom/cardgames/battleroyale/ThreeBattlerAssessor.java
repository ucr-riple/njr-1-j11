package org.washcom.cardgames.battleroyale;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import static org.washcom.cardgames.battleroyale.Rules.*;
import static org.washcom.cardgames.core.Denomination.ACE;
import static org.washcom.cardgames.core.Denomination.TWO;

/**
 * A BattleAssessor for when there's exactly three battlers in the battle. If
 * not, the methods will throw IllegalArgumentExceptions.
 *
 * @author Joe
 */
public class ThreeBattlerAssessor implements BattleAssessor {

    private static final Logger log = Logger.getLogger(ThreeBattlerAssessor.class.toString());

    @Override
    public BattleCard pickWinner(Battle battle) {
        checkBattlers(battle.getBattlers());
        List<BattleCard> cards = battle.getBattleCards();
        BattleCard one = cards.get(0);
        BattleCard two = cards.get(1);
        BattleCard three = cards.get(2);

        /* J8 combo means that the players immediately swap decks. Multiple
         * swaps can occur. XXX - verify the correctness of this
         */
        if (isJackEightCombo(one, two)) {
            battle.getGame().swapHands(one.getPlayedBy(), two.getPlayedBy());
        }
        if (isJackEightCombo(one, three)) {
            battle.getGame().swapHands(one.getPlayedBy(), three.getPlayedBy());
        }
        if (isJackEightCombo(two, three)) {
            battle.getGame().swapHands(two.getPlayedBy(), three.getPlayedBy());
        }

        /*
         * In a Battle Royale scenario, no one wins
         */
        if (isBattleRoyale(one, two) || isBattleRoyale(one, three) || isBattleRoyale(two, three)) {
            return null;
        }

        /*
         * Determine value difference between three players. If any combination is
         * under the battle threshold, there is no winner.
         */
        int oneTwoDiff = one.computeValueDifference(two);
        int oneThreeDiff = one.computeValueDifference(three);
        int twoThreeDiff = two.computeValueDifference(three);
        if (isDifferentialUnderBattleThreshold(oneTwoDiff)
                || isDifferentialUnderBattleThreshold(oneThreeDiff)
                || isDifferentialUnderBattleThreshold(twoThreeDiff)) {
            return null;
        }
        BattleCard highCard = highCard(one, two, three);
        /*
         * If an Ace is the high card, check for a two. In an Ace-Two combo, the Two wins.
         * It's one of those quirky rules.
         */
        if (highCard.getCard().getDenomination() == ACE) {
            if (one.getCard().getDenomination() == TWO) {
                return one;
            } else if (two.getCard().getDenomination() == TWO) {
                return two;
            } else if (three.getCard().getDenomination() == TWO) {
                return three;
            }
        }
        return highCard;
    }

    /* FIXME */
    @Override
    public Map<Player, Integer> determineFees(Battle battle) {
        checkBattlers(battle.getBattlers());
        List<BattleCard> cards = battle.getBattleCards();
        BattleCard one = cards.get(0);
        BattleCard two = cards.get(1);
        BattleCard three = cards.get(2);

        Map<Player, Integer> result = new HashMap<>();
        if (isBattleRoyale(one, two) || isBattleRoyale(one, three) || isBattleRoyale(two, three)) {
            if (one.isBattleRoyaleEligible()) {
                result.put(one.getPlayedBy(), battleRoyaleFee(one));
            }
            if (two.isBattleRoyaleEligible()) {
                result.put(two.getPlayedBy(), battleRoyaleFee(two));
            }
            if (three.isBattleRoyaleEligible()) {
                result.put(three.getPlayedBy(), battleRoyaleFee(three));
            }
            return result;
        }

        int oneTwoFee = MAX_VALUE_DIFF_FEE - Math.abs(one.computeValueDifference(two));
        int oneThreeFee = MAX_VALUE_DIFF_FEE - Math.abs(one.computeValueDifference(three));
        int twoThreeFee = MAX_VALUE_DIFF_FEE - Math.abs(two.computeValueDifference(three));
        if (oneTwoFee > 0 || oneThreeFee > 0) {
            result.put(one.getPlayedBy(), Math.max(oneTwoFee, oneThreeFee));
        }
        if (oneTwoFee > 0 || twoThreeFee > 0) {
            result.put(two.getPlayedBy(), Math.max(oneTwoFee, twoThreeFee));
        }
        if (oneThreeFee > 0 || twoThreeFee > 0) {
            result.put(three.getPlayedBy(), Math.max(oneThreeFee, twoThreeFee));
        }
        return result;
    }

    private static void checkBattlers(List<Player> battlers) {
        Preconditions.checkState(battlers.size() == 3);
    }
}
