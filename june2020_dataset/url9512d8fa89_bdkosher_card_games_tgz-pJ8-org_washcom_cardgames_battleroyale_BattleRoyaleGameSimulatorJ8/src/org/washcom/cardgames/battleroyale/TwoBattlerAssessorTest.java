package org.washcom.cardgames.battleroyale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Test;
import org.washcom.cardgames.core.Card;
import org.washcom.cardgames.core.Denomination;
import static org.washcom.cardgames.core.Denomination.*;
import org.washcom.cardgames.core.Suit;

/**
 *
 * @author Joe
 */
public class TwoBattlerAssessorTest {
    
    TwoBattlerAssessor assessor = new TwoBattlerAssessor();
    
    Denomination pickWinner(Denomination one, Denomination two) {
        BattleCard battleCard = assessor.pickWinner(battle(one, two));
        return battleCard == null ? null : battleCard.getCard().getDenomination();
    }

    public Map<Player, Integer> determineFees(Battle battle) {
        return assessor.determineFees(battle);
    }
    
    Player player(int playerId) {
        return new Player("Player " + playerId);
    }
    
    BattleCard bc(Denomination denom, int playerId) {
        return new BattleCard(new Card(denom, Suit.values()[new Random().nextInt(Suit.values().length)]), 
                player(playerId));
    }
    
    Battle battle(final Denomination denom1, final Denomination denom2) {
        return new Battle(1, new BattleRoyaleGame(assessor)) {

            @Override
            public List<BattleCard> getBattleCards() {
                List<BattleCard> cards = new ArrayList<>();
                cards.add(bc(denom1, 1));
                cards.add(bc(denom2, 2));
                return cards;
            }

            @Override
            public List<Player> getBattlers() {
                return Arrays.<Player>asList(player(1), player(2));
            }
            
        };
    }
    
    @Test
    public void testTwoWinsOverAce() {
        assertEquals(TWO, pickWinner(TWO, ACE));
    }
    
    @Test
    public void testAceBeatsAllButRoyaltyAndTwo() {
        assertEquals(ACE, pickWinner(THREE, ACE));
        assertEquals(ACE, pickWinner(FOUR, ACE));
        assertEquals(ACE, pickWinner(FIVE, ACE));
        assertEquals(ACE, pickWinner(SIX, ACE));
        assertEquals(ACE, pickWinner(SEVEN, ACE));
        assertEquals(ACE, pickWinner(EIGHT, ACE));
        assertEquals(ACE, pickWinner(NINE, ACE));
        assertEquals(ACE, pickWinner(TEN, ACE));
        assertNull(pickWinner(JACK, ACE));
        assertNull(pickWinner(QUEEN, ACE));
        assertNull(pickWinner(KING, ACE));
    }
    
    
    @Test
    public void testTwoBeatsAce() {
        assertEquals(TWO, pickWinner(TWO, ACE));
        assertEquals(TWO, pickWinner(ACE, TWO));
    }
    
    @Test
    public void testBattleRoyaleCombosResultInNoWinner() {
        assertNull(pickWinner(JACK, JACK));
        assertNull(pickWinner(QUEEN, JACK));
        assertNull(pickWinner(KING, JACK));
        assertNull(pickWinner(JACK, QUEEN));
        assertNull(pickWinner(QUEEN, QUEEN));
        assertNull(pickWinner(KING, QUEEN));
        assertNull(pickWinner(JACK, KING));
        assertNull(pickWinner(QUEEN, KING));
        assertNull(pickWinner(KING, KING));
    }
    
    @Test
    public void testIdenticalCardsResultInNoWinner() {
        for (Denomination denom : Denomination.values()) {
            assertNull(pickWinner(denom, denom));
        }
    }
    
    @Test
    public void testOneOffCardsResultInNoWinner() {
        assertNull(pickWinner(TWO, THREE));
        assertNull(pickWinner(THREE, FOUR));
        assertNull(pickWinner(FOUR, FIVE));
        assertNull(pickWinner(FIVE, SIX));
        assertNull(pickWinner(SIX, SEVEN));
        assertNull(pickWinner(SEVEN, EIGHT));
        assertNull(pickWinner(EIGHT, NINE));
        assertNull(pickWinner(NINE, TEN));
        assertNull(pickWinner(TEN, JACK));
        
        assertNull(pickWinner(THREE, TWO));
        assertNull(pickWinner(FOUR, THREE));
        assertNull(pickWinner(FIVE, FOUR));
        assertNull(pickWinner(SIX, FIVE));
        assertNull(pickWinner(SEVEN, SIX));
        assertNull(pickWinner(EIGHT, SEVEN));
        assertNull(pickWinner(NINE, EIGHT));
        assertNull(pickWinner(TEN, NINE));
        assertNull(pickWinner(JACK, TEN));
    }
    
    @Test
    public void testTwoOffCardsResultInNoWinner() {
        assertNull(pickWinner(TWO, FOUR));
        assertNull(pickWinner(THREE, FIVE));
        assertNull(pickWinner(FOUR, SIX));
        assertNull(pickWinner(FIVE, SEVEN));
        assertNull(pickWinner(SIX, EIGHT));
        assertNull(pickWinner(SEVEN, NINE));
        assertNull(pickWinner(EIGHT, TEN));
        assertNull(pickWinner(NINE, JACK));
        assertNull(pickWinner(TEN, QUEEN));
        
        assertNull(pickWinner(FOUR, TWO));
        assertNull(pickWinner(FIVE, THREE));
        assertNull(pickWinner(SIX, FOUR));
        assertNull(pickWinner(SEVEN, FIVE));
        assertNull(pickWinner(EIGHT, SIX));
        assertNull(pickWinner(NINE, SEVEN));
        assertNull(pickWinner(TEN, EIGHT));
        assertNull(pickWinner(JACK, NINE));
        assertNull(pickWinner(QUEEN, TEN));
    }
    
    @Test
    public void testNoWrappingAroundForDiffsOfTwo() {
        assertEquals(KING, pickWinner(KING, TWO));
        assertEquals(KING, pickWinner(TWO, KING));
        assertEquals(ACE, pickWinner(ACE, THREE));
        assertEquals(ACE, pickWinner(THREE, ACE));
    }
    
    @Test
    public void testMoreThanTwoDiffMeansAWinner() {
        assertEquals(FIVE, pickWinner(FIVE, TWO));
        assertEquals(SIX, pickWinner(SIX, THREE));
        assertEquals(SIX, pickWinner(SIX, TWO));
        assertEquals(SEVEN, pickWinner(SEVEN, FOUR));
        assertEquals(SEVEN, pickWinner(SEVEN, THREE));
        assertEquals(SEVEN, pickWinner(SEVEN, TWO));
        assertEquals(EIGHT, pickWinner(EIGHT, FIVE));
        assertEquals(EIGHT, pickWinner(EIGHT, FOUR));
        assertEquals(EIGHT, pickWinner(EIGHT, THREE));
        assertEquals(EIGHT, pickWinner(EIGHT, TWO));
        assertEquals(NINE, pickWinner(NINE, SIX));
        assertEquals(NINE, pickWinner(NINE, FIVE));
        assertEquals(NINE, pickWinner(NINE, FOUR));
        assertEquals(NINE, pickWinner(NINE, THREE));
        assertEquals(NINE, pickWinner(NINE, TWO));
        assertEquals(TEN, pickWinner(TEN, SEVEN));
        assertEquals(TEN, pickWinner(TEN, SIX));
        assertEquals(TEN, pickWinner(TEN, FIVE));
        assertEquals(TEN, pickWinner(TEN, FOUR));
        assertEquals(TEN, pickWinner(TEN, THREE));
        assertEquals(TEN, pickWinner(TEN, TWO));
        assertEquals(JACK, pickWinner(JACK, EIGHT));
        assertEquals(JACK, pickWinner(JACK, SEVEN));
        assertEquals(JACK, pickWinner(JACK, SIX));
        assertEquals(JACK, pickWinner(JACK, FIVE));
        assertEquals(JACK, pickWinner(JACK, FOUR));
        assertEquals(JACK, pickWinner(JACK, THREE));
        assertEquals(JACK, pickWinner(JACK, TWO));
        assertEquals(QUEEN, pickWinner(QUEEN, NINE));
        assertEquals(QUEEN, pickWinner(QUEEN, EIGHT));
        assertEquals(QUEEN, pickWinner(QUEEN, SEVEN));
        assertEquals(QUEEN, pickWinner(QUEEN, SIX));
        assertEquals(QUEEN, pickWinner(QUEEN, FIVE));
        assertEquals(QUEEN, pickWinner(QUEEN, FOUR));
        assertEquals(QUEEN, pickWinner(QUEEN, THREE));
        assertEquals(QUEEN, pickWinner(QUEEN, TWO));
        assertEquals(KING, pickWinner(KING, TEN));
        assertEquals(KING, pickWinner(KING, NINE));
        assertEquals(KING, pickWinner(KING, EIGHT));
        assertEquals(KING, pickWinner(KING, SEVEN));
        assertEquals(KING, pickWinner(KING, SIX));
        assertEquals(KING, pickWinner(KING, FIVE));
        assertEquals(KING, pickWinner(KING, FOUR));
        assertEquals(KING, pickWinner(KING, THREE));
        assertEquals(KING, pickWinner(KING, TWO));
    }
}
