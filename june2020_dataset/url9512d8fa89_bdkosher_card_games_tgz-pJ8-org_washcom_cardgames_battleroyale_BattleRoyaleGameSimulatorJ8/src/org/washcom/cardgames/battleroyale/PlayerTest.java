package org.washcom.cardgames.battleroyale;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

import org.washcom.cardgames.core.Card;
import static org.washcom.cardgames.core.Suit.*;
import static org.washcom.cardgames.core.Denomination.*;

/**
 *
 * @author Joe
 */
public class PlayerTest {
    
    private List<Card> allQueens = Arrays.asList(new Card[] {
        new Card(QUEEN, HEARTS),
        new Card(QUEEN, DIAMONDS),
        new Card(QUEEN, SPADES),
        new Card(QUEEN, CLUBS),
    });
    
    private List<Card> allKings = Arrays.asList(new Card[] {
        new Card(KING, HEARTS),
        new Card(KING, DIAMONDS),
        new Card(KING, SPADES),
        new Card(KING, CLUBS)
    });
    
    @Test
    public void testSwapHandsWithSelf() {
        Player self = new Player("me");
        self.getHand().put(allKings);
        self.swapHands(self);
        assertEquals(4, self.getHand().size());
        for (Card card : self.getHand().drawAll()) {
            assertEquals(KING, card.getDenomination());
        }
    }
    
    @Test
    public void testSwapHandsTwoEmpty() {
        Player one = new Player("one");
        Player two = new Player("two");
        one.swapHands(two);
        assertTrue(one.getHand().isEmpty());
        assertTrue(two.getHand().isEmpty());
    }
    
    @Test
    public void testSwapHandsTwoEmptySymmetric() {
        Player one = new Player("one");
        Player two = new Player("two");
        two.swapHands(one);
        assertTrue(one.getHand().isEmpty());
        assertTrue(two.getHand().isEmpty());
    }
    
    @Test
    public void testSwapHandsWithEmptyHandedPlayer() {
        Player one = new Player("one");
        one.getHand().put(new Card(QUEEN, HEARTS));
        Player two = new Player("two");
        one.swapHands(two);
        assertTrue(one.getHand().isEmpty());
        assertEquals(1, two.getHand().size());
        assertEquals(new Card(QUEEN, HEARTS), two.getHand().draw());
    }
    
    @Test
    public void testSwapHandsWithEmptyHandedPlayerSymmetric() {
        Player one = new Player("one");
        one.getHand().put(new Card(QUEEN, HEARTS));
        Player two = new Player("two");
        two.swapHands(one);
        assertTrue(one.getHand().isEmpty());
        assertEquals(1, two.getHand().size());
        assertEquals(new Card(QUEEN, HEARTS), two.getHand().draw());
    }
    
    @Test
    public void testSwapNonEmpty() {
        Player one = new Player("one");
        one.getHand().put(allKings);
        Player two = new Player("two");
        two.getHand().put(allQueens);
        one.swapHands(two);
        assertEquals(4, one.getHand().size());
        assertEquals(4, two.getHand().size());
        for (Card card : one.getHand().drawAll()) {
            assertEquals(QUEEN, card.getDenomination());
        }
        for (Card card : two.getHand().drawAll()) {
            assertEquals(KING, card.getDenomination());
        }
    }
    
    @Test
    public void testSwapNonEmptySymmetric() {
        Player one = new Player("one");
        one.getHand().put(allKings);
        Player two = new Player("two");
        two.getHand().put(allQueens);
        two.swapHands(one);
        assertEquals(4, one.getHand().size());
        assertEquals(4, two.getHand().size());
        for (Card card : one.getHand().drawAll()) {
            assertEquals(QUEEN, card.getDenomination());
        }
        for (Card card : two.getHand().drawAll()) {
            assertEquals(KING, card.getDenomination());
        }
    }
    
}
