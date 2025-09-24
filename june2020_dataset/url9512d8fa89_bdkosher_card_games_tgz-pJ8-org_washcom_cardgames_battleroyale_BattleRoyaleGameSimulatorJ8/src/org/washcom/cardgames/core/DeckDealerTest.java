package org.washcom.cardgames.core;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.washcom.cardgames.core.Suit.*;
import static org.washcom.cardgames.core.Denomination.*;

/**
 *
 * @author Joe
 */
public class DeckDealerTest {

    private Deck emptyDeck;
    private Deck singleCardDeck;
    private Deck threeCardDeck;
    private Deck hundredCardDeck;
    private Player[] noPlayers;
    private Player singlePlayer;
    private Player[] twoPlayers;
    private Player[] threePlayers;
    private Player[] fourPlayers;

    @Before
    public void setUp() {
        emptyDeck = new Deck();
        singleCardDeck = new Deck(Collections.singletonList(new Card(TEN, CLUBS)));
        threeCardDeck = new Deck(Arrays.asList(new Card[]{
                    new Card(TEN, CLUBS),
                    new Card(THREE, HEARTS),
                    new Card(FIVE, DIAMONDS),}));
        hundredCardDeck = new Deck();
        for (int i = 0; i < 100; ++i) {
            hundredCardDeck.put(new Card(Denomination.values()[i % Denomination.values().length], 
                    Suit.values()[i % Suit.values().length]));
        }

        noPlayers = new Player[0];
        singlePlayer = new Player("1");
        twoPlayers = new Player[]{new Player("1"), new Player("2")};
        threePlayers = new Player[]{new Player("1"), new Player("2"), new Player("3")};
        fourPlayers = new Player[]{new Player("1"), new Player("2"), new Player("3"), new Player("4")};
    }

    @Test(expected = RuntimeException.class)
    public void testDealEntirelyNullPlayerArray() {
        DeckDealer.dealEntirely(threeCardDeck, (Player[]) null);
    }

    @Test(expected = RuntimeException.class)
    public void testDealEntirelyEmptyPlayerArray() {
        DeckDealer.dealEntirely(threeCardDeck, noPlayers);
    }

    @Test(expected = RuntimeException.class)
    public void testDealEntirelySingleItemArrayNullPlayer() {
        DeckDealer.dealEntirely(threeCardDeck, (Player) null);
    }

    @Test
    public void testDealEntirelyEmptyDeck() {
        DeckDealer.dealEntirely(emptyDeck, threePlayers);
        assertTrue(threePlayers[0].getHand().isEmpty());
        assertTrue(threePlayers[1].getHand().isEmpty());
        assertTrue(threePlayers[2].getHand().isEmpty());
    }

    @Test
    public void testDealEntirelyToSinglePlayer() {
        int deckSize = threeCardDeck.size();
        DeckDealer.dealEntirely(threeCardDeck, singlePlayer);
        assertEquals(deckSize, singlePlayer.getHand().size());
        assertTrue(threeCardDeck.isEmpty());
    }

    @Test
    public void testDealEntirelyToPerfectNbrOfPlayers() {
        DeckDealer.dealEntirely(threeCardDeck, threePlayers);
        assertTrue(threeCardDeck.isEmpty());
        assertEquals(1, threePlayers[0].getHand().size());
        assertEquals(1, threePlayers[1].getHand().size());
        assertEquals(1, threePlayers[2].getHand().size());
    }

    @Test
    public void testDealEntirelyToFewerNbrOfPlayers() {
        DeckDealer.dealEntirely(threeCardDeck, twoPlayers);
        assertTrue(threeCardDeck.isEmpty());
        assertEquals(2, twoPlayers[0].getHand().size());
        assertEquals(1, twoPlayers[1].getHand().size());
    }

    @Test
    public void testDealEntirelyToGreaterNbrOfPlayers() {
        DeckDealer.dealEntirely(threeCardDeck, fourPlayers);
        assertTrue(threeCardDeck.isEmpty());
        assertEquals(1, fourPlayers[0].getHand().size());
        assertEquals(1, fourPlayers[1].getHand().size());
        assertEquals(1, fourPlayers[2].getHand().size());
        assertEquals(0, fourPlayers[3].getHand().size());
    }

    @Test(expected = RuntimeException.class)
    public void testDealFairlyNullPlayerArray() {
        DeckDealer.dealFairly(threeCardDeck, (Player[]) null);
    }

    @Test
    public void testDealFairlyEmptyPlayerArray() {
        DeckDealer.dealFairly(hundredCardDeck, noPlayers);
        assertEquals(100, hundredCardDeck.size());
    }

    @Test(expected = RuntimeException.class)
    public void testDeaFairlySingleItemArrayNullPlayer() {
        DeckDealer.dealFairly(threeCardDeck, (Player) null);
    }

    @Test
    public void testDealFairlyEmptyDeck() {
        DeckDealer.dealFairly(emptyDeck, threePlayers);
        assertTrue(threePlayers[0].getHand().isEmpty());
        assertTrue(threePlayers[1].getHand().isEmpty());
        assertTrue(threePlayers[2].getHand().isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void testDealFairlySmallerDeckThanPlayers() {
        DeckDealer.dealFairly(singleCardDeck, twoPlayers);
    }

    @Test
    public void testDealFairlyToSinglePlayer() {
        DeckDealer.dealFairly(threeCardDeck, singlePlayer);
        assertTrue(threeCardDeck.isEmpty());
        assertEquals(3, singlePlayer.getHand().size());
    }

    public void testDealFairlyToPerfectNbrOfPlayers() {
        DeckDealer.dealFairly(threeCardDeck, threePlayers);
        assertTrue(threeCardDeck.isEmpty());
        assertEquals(1, threePlayers[0].getHand().size());
        assertEquals(1, threePlayers[1].getHand().size());
        assertEquals(1, threePlayers[2].getHand().size());
    }

    public void testDealFairlyToGreaterNbrOfPlayersWithLeftovers() {
        DeckDealer.dealFairly(hundredCardDeck, threePlayers);
        assertEquals(1, hundredCardDeck.size());
        assertEquals(33, threePlayers[0].getHand().size());
        assertEquals(33, threePlayers[1].getHand().size());
        assertEquals(33, threePlayers[2].getHand().size());
    }

    public void testDealFairlyToGreaterNbrOfPlayersWithNoLeftovers() {
        DeckDealer.dealFairly(hundredCardDeck, fourPlayers);
        assertTrue(hundredCardDeck.isEmpty());
        assertEquals(25, fourPlayers[0].getHand().size());
        assertEquals(25, fourPlayers[1].getHand().size());
        assertEquals(25, fourPlayers[2].getHand().size());
        assertEquals(25, fourPlayers[3].getHand().size());
    }
}
