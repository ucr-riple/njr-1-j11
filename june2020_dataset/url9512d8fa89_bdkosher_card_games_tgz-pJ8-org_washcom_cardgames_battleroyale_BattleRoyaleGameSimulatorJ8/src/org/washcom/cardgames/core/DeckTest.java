package org.washcom.cardgames.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import static org.washcom.cardgames.core.Denomination.*;
import static org.washcom.cardgames.core.Suit.*;
import org.washcom.util.DeckExhaustedException;

/**
 *
 * @author Joe
 */
public class DeckTest {

    private Deck emptyDeck;
    private Deck singleCardDeck;
    private Deck twoCardDeck;

    @Before
    public void setUp() {
        emptyDeck = new Deck();
        singleCardDeck = new Deck(Collections.singleton(new Card(ACE, CLUBS)));
        twoCardDeck = new Deck(Arrays.asList(new Card(ACE, CLUBS), new Card(TWO, CLUBS)));
    }

    @Test
    public void testDrawZeroFromEmptyDeck() {
        assertTrue(emptyDeck.draw(0).isEmpty());
    }

    @Test
    public void testDrawZeroFromNonEmptyDeck() {
        assertTrue(singleCardDeck.draw(0).isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void testDrawLessThanZeroFromEmptyDeck() {
        emptyDeck.draw(-1);
    }

    @Test(expected = RuntimeException.class)
    public void testDrawLessThanZeroFromNonEmptyDeck() {
        emptyDeck.draw(-1);
    }

    @Test(expected = DeckExhaustedException.class)
    public void testDrawFromEmptyDeckThrowsExhaustedException() {
        emptyDeck.draw();
    }
    
    @Test
    public void testDrawFromSingleCardDeck() {
        assertEquals(new Card(ACE, CLUBS), singleCardDeck.draw());
        assertEquals(0, singleCardDeck.size());
    }
    
    @Test
    public void testDrawFromMultiCardDeck() {
        assertEquals(new Card(ACE, CLUBS), twoCardDeck.draw());
        assertEquals(1, twoCardDeck.size());
        assertEquals(new Card(TWO, CLUBS), twoCardDeck.draw());
        assertEquals(0, twoCardDeck.size()); 
    }
    
    @Test
    public void testBottomDrawFromMultiCardDeck() {
        assertEquals(new Card(TWO, CLUBS), twoCardDeck.drawFromBottom());
        assertEquals(1, twoCardDeck.size());
        assertEquals(new Card(ACE, CLUBS), twoCardDeck.drawFromBottom());
        assertEquals(0, twoCardDeck.size());
    }
    
    @Test(expected=RuntimeException.class)
    public void testPutNullCard() {
        singleCardDeck.put((Card)null);
    }
    
    @Test(expected=RuntimeException.class)
    public void testPutNullCardList() {
        singleCardDeck.put((List<Card>)null);
    }
    
    @Test
    public void testPutEmptyCardList() {
        emptyDeck.put(Collections.<Card>emptyList());
        assertEquals(0, emptyDeck.size());
    }
    
    @Test
    public void testPutCardOnEmptyDeck() {
        Card card = new Card(QUEEN, HEARTS);
        emptyDeck.put(card);
        assertEquals(1, emptyDeck.size());
        assertEquals(card, emptyDeck.draw());
    }
    
    @Test
    public void testPutCardOnBottomOfEmptyDeck() {
        Card card = new Card(QUEEN, HEARTS);
        emptyDeck.putOnBottom(card);
        assertEquals(1, emptyDeck.size());
        assertEquals(card, emptyDeck.draw());
    }
    
    @Test
    public void testPutCardOnNonEmptyDeck() {
        Card card = new Card(QUEEN, HEARTS);
        singleCardDeck.put(card);
        assertEquals(2, singleCardDeck.size());
        assertEquals(card, singleCardDeck.draw());
    }
    
    @Test
    public void testPutCardOnBottomOfNonEmptyDeck() {
        Card card = new Card(QUEEN, HEARTS);
        singleCardDeck.putOnBottom(card);
        assertEquals(2, singleCardDeck.size());
        assertEquals(card, singleCardDeck.drawFromBottom());
    }
    
    @Test
    public void testPutCardsOn() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(QUEEN, HEARTS));
        cards.add(new Card(ACE, SPADES)); 
        emptyDeck.put(cards);
        assertEquals(2, emptyDeck.size());
        assertEquals(new Card(ACE, SPADES), emptyDeck.draw());
        assertEquals(new Card(QUEEN, HEARTS), emptyDeck.draw());
    }
    
    @Test
    public void testPutCardsOnBottom() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(QUEEN, HEARTS));
        cards.add(new Card(ACE, SPADES)); 
        emptyDeck.putOnBottom(cards);
        assertEquals(2, emptyDeck.size());
        assertEquals(new Card(ACE, SPADES), emptyDeck.drawFromBottom());
        assertEquals(new Card(QUEEN, HEARTS), emptyDeck.drawFromBottom());
    }
    
    @Test
    public void testSize() {
        assertEquals(0, emptyDeck.size());
        assertEquals(1, singleCardDeck.size());
        assertEquals(2, twoCardDeck.size());
    }
}
