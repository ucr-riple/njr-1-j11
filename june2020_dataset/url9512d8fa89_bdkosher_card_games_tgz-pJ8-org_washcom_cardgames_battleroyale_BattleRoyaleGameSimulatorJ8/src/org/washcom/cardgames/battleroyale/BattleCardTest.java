package org.washcom.cardgames.battleroyale;

import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import org.washcom.cardgames.core.Card;
import org.washcom.cardgames.core.Denomination;
import static org.washcom.cardgames.core.Denomination.*;
import org.washcom.cardgames.core.Suit;

/**
 *
 * @author Joe
 */
public class BattleCardTest {

    Player player(int playerId) {
        return new Player("Player " + playerId);
    }

    BattleCard bc(Denomination denom) {
        return bc(denom, 1);
    }

    BattleCard bc(Denomination denom, int playerId) {
        return new BattleCard(new Card(denom, Suit.values()[new Random().nextInt(Suit.values().length)]),
                player(playerId));
    }

    @Test
    public void testEqualCardsHaveZeroDifference() {
        for (Denomination denom : Denomination.values()) {
            assertEquals(0, bc(denom).computeValueDifference(bc(denom)));
        }
    }
    
    @Test
    public void testOneOffCards() {
        assertEquals(1, bc(ACE).computeValueDifference(bc(KING)));
        assertEquals(1, bc(KING).computeValueDifference(bc(QUEEN)));
        assertEquals(1, bc(QUEEN).computeValueDifference(bc(JACK)));
        assertEquals(1, bc(JACK).computeValueDifference(bc(TEN)));
        assertEquals(1, bc(TEN).computeValueDifference(bc(NINE)));
        assertEquals(1, bc(NINE).computeValueDifference(bc(EIGHT)));
        assertEquals(1, bc(EIGHT).computeValueDifference(bc(SEVEN)));
        assertEquals(1, bc(SEVEN).computeValueDifference(bc(SIX)));
        assertEquals(1, bc(SIX).computeValueDifference(bc(FIVE)));
        assertEquals(1, bc(FIVE).computeValueDifference(bc(FOUR)));
        assertEquals(1, bc(FOUR).computeValueDifference(bc(THREE)));
        assertEquals(1, bc(THREE).computeValueDifference(bc(TWO)));
    }
    
    @Test
    public void testNegativeOneOffCards() {
        assertEquals(-1, bc(KING).computeValueDifference(bc(ACE)));
        assertEquals(-1, bc(QUEEN).computeValueDifference(bc(KING)));
        assertEquals(-1, bc(JACK).computeValueDifference(bc(QUEEN)));
        assertEquals(-1, bc(TEN).computeValueDifference(bc(JACK)));
        assertEquals(-1, bc(NINE).computeValueDifference(bc(TEN)));
        assertEquals(-1, bc(EIGHT).computeValueDifference(bc(NINE)));
        assertEquals(-1, bc(SEVEN).computeValueDifference(bc(EIGHT)));
        assertEquals(-1, bc(SIX).computeValueDifference(bc(SEVEN)));
        assertEquals(-1, bc(FIVE).computeValueDifference(bc(SIX)));
        assertEquals(-1, bc(FOUR).computeValueDifference(bc(FIVE)));
        assertEquals(-1, bc(THREE).computeValueDifference(bc(FOUR)));
        assertEquals(-1, bc(TWO).computeValueDifference(bc(THREE)));
    }
    
    @Test
    public void testTwoOffCards() {
        assertEquals(2, bc(ACE).computeValueDifference(bc(QUEEN)));
        assertEquals(2, bc(KING).computeValueDifference(bc(JACK)));
        assertEquals(2, bc(QUEEN).computeValueDifference(bc(TEN)));
        assertEquals(2, bc(JACK).computeValueDifference(bc(NINE)));
        assertEquals(2, bc(TEN).computeValueDifference(bc(EIGHT)));
        assertEquals(2, bc(NINE).computeValueDifference(bc(SEVEN)));
        assertEquals(2, bc(EIGHT).computeValueDifference(bc(SIX)));
        assertEquals(2, bc(SEVEN).computeValueDifference(bc(FIVE)));
        assertEquals(2, bc(SIX).computeValueDifference(bc(FOUR)));
        assertEquals(2, bc(FIVE).computeValueDifference(bc(THREE)));
        assertEquals(2, bc(FOUR).computeValueDifference(bc(TWO)));
    }
    
    @Test
    public void testNegativeTwoOffCards() {
        assertEquals(-2, bc(QUEEN).computeValueDifference(bc(ACE)));
        assertEquals(-2, bc(JACK).computeValueDifference(bc(KING)));
        assertEquals(-2, bc(TEN).computeValueDifference(bc(QUEEN)));
        assertEquals(-2, bc(NINE).computeValueDifference(bc(JACK)));
        assertEquals(-2, bc(EIGHT).computeValueDifference(bc(TEN)));
        assertEquals(-2, bc(SEVEN).computeValueDifference(bc(NINE)));
        assertEquals(-2, bc(SIX).computeValueDifference(bc(EIGHT)));
        assertEquals(-2, bc(FIVE).computeValueDifference(bc(SEVEN)));
        assertEquals(-2, bc(FOUR).computeValueDifference(bc(SIX)));
        assertEquals(-2, bc(THREE).computeValueDifference(bc(FIVE)));
        assertEquals(-2, bc(TWO).computeValueDifference(bc(FOUR)));
    }
    
    @Test
    public void testMoreThanTwoOffCards() {
        assertEquals(3, bc(ACE).computeValueDifference(bc(JACK)));
        assertEquals(3, bc(KING).computeValueDifference(bc(TEN)));
        assertEquals(3, bc(QUEEN).computeValueDifference(bc(NINE)));
        assertEquals(3, bc(JACK).computeValueDifference(bc(EIGHT)));
        assertEquals(3, bc(TEN).computeValueDifference(bc(SEVEN)));
        assertEquals(3, bc(NINE).computeValueDifference(bc(SIX)));
        assertEquals(3, bc(EIGHT).computeValueDifference(bc(FIVE)));
        assertEquals(3, bc(SEVEN).computeValueDifference(bc(FOUR)));
        assertEquals(3, bc(SIX).computeValueDifference(bc(THREE)));
        assertEquals(3, bc(FIVE).computeValueDifference(bc(TWO)));
        
        assertEquals(4, bc(ACE).computeValueDifference(bc(TEN)));
        assertEquals(4, bc(KING).computeValueDifference(bc(NINE)));
        assertEquals(4, bc(QUEEN).computeValueDifference(bc(EIGHT)));
        assertEquals(4, bc(JACK).computeValueDifference(bc(SEVEN)));
        assertEquals(4, bc(TEN).computeValueDifference(bc(SIX)));
        assertEquals(4, bc(NINE).computeValueDifference(bc(FIVE)));
        assertEquals(4, bc(EIGHT).computeValueDifference(bc(FOUR)));
        assertEquals(4, bc(SEVEN).computeValueDifference(bc(THREE)));
        assertEquals(4, bc(SIX).computeValueDifference(bc(TWO)));
        
        assertEquals(12, bc(ACE).computeValueDifference(bc(TWO)));
    }
    
    @Test
    public void testLessThanNegativeTwoOffCards() {
        assertEquals(-3, bc(JACK).computeValueDifference(bc(ACE)));
        assertEquals(-3, bc(TEN).computeValueDifference(bc(KING)));
        assertEquals(-3, bc(NINE).computeValueDifference(bc(QUEEN)));
        assertEquals(-3, bc(EIGHT).computeValueDifference(bc(JACK)));
        assertEquals(-3, bc(SEVEN).computeValueDifference(bc(TEN)));
        assertEquals(-3, bc(SIX).computeValueDifference(bc(NINE)));
        assertEquals(-3, bc(FIVE).computeValueDifference(bc(EIGHT)));
        assertEquals(-3, bc(FOUR).computeValueDifference(bc(SEVEN)));
        assertEquals(-3, bc(THREE).computeValueDifference(bc(SIX)));
        assertEquals(-3, bc(TWO).computeValueDifference(bc(FIVE)));
        
        assertEquals(-4, bc(TEN).computeValueDifference(bc(ACE)));
        assertEquals(-4, bc(NINE).computeValueDifference(bc(KING)));
        assertEquals(-4, bc(EIGHT).computeValueDifference(bc(QUEEN)));
        assertEquals(-4, bc(SEVEN).computeValueDifference(bc(JACK)));
        assertEquals(-4, bc(SIX).computeValueDifference(bc(TEN)));
        assertEquals(-4, bc(FIVE).computeValueDifference(bc(NINE)));
        assertEquals(-4, bc(FOUR).computeValueDifference(bc(EIGHT)));
        assertEquals(-4, bc(THREE).computeValueDifference(bc(SEVEN)));
        assertEquals(-4, bc(TWO).computeValueDifference(bc(SIX)));
        
        assertEquals(-12, bc(TWO).computeValueDifference(bc(ACE)));
    }
    
    @Test
    public void testBattleRoyaleEligible() {
        assertTrue(bc(ACE).isBattleRoyaleEligible());
        assertTrue(bc(KING).isBattleRoyaleEligible());
        assertTrue(bc(QUEEN).isBattleRoyaleEligible());
        assertTrue(bc(JACK).isBattleRoyaleEligible());
        assertFalse(bc(TEN).isBattleRoyaleEligible());
        assertFalse(bc(NINE).isBattleRoyaleEligible());
        assertFalse(bc(EIGHT).isBattleRoyaleEligible());
        assertFalse(bc(SEVEN).isBattleRoyaleEligible());
        assertFalse(bc(SIX).isBattleRoyaleEligible());
        assertFalse(bc(FIVE).isBattleRoyaleEligible());
        assertFalse(bc(FOUR).isBattleRoyaleEligible());
        assertFalse(bc(THREE).isBattleRoyaleEligible());
        assertFalse(bc(TWO).isBattleRoyaleEligible());
    }
}
