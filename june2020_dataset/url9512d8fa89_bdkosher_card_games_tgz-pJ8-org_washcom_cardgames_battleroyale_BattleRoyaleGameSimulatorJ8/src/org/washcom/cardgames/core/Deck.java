package org.washcom.cardgames.core;

import java.util.Collection;
import java.util.List;

/**
 * Represents a deck of cards.
 * 
 * TODO: add a cut method?
 * 
 * @author Joe
 */
public class Deck extends org.washcom.util.Deck<Card> {

    /**
     * Initializes the deck with no cards in it.
     */
    public Deck() {
    }

    /**
     * Initializes the deck with the given collection of cards. The Collection type is not used
     * by the deck, but the cards within the collection are.
     * 
     * The deck is not shuffled on initialization.
     * 
     * @param cards - cannot be null
     */
    public Deck(Collection<Card> cards) {
        super(cards);
    }

    /**
     * Draws up to the maximum number of cards given. If the number of cards left in the
     * deck is less than the maximum, then all remaining cards in the deck will be drawn.
     * If the deck is empty, an empty collection will be returned.
     * 
     * @param max - cannot be negative
     * @return 
     */
    public List<Card> drawUpTo(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("Maximum must be non-negative.");
        }
        return draw(Math.min(max, size()));
    }
    
    /**
     * Draws up to the maximum number of cards given from the bottom of the deck. If the 
     * number of cards left is less than the maximum, then all remaining cards in the 
     * deck will be drawn. If the deck is empty, an empty collection will be returned.
     * 
     * @param max - cannot be negative
     * @return 
     */
    public List<Card> drawFromBottomUpTo(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("Maximum must be non-negative.");
        }
        return drawFromBottom(Math.min(max, size()));
    }

    /**
     * Returns true if there is at lease one card left in the deck. 
     * 
     * This is always true {@code assert isEmpty() != hasCards()}
     * 
     * @return 
     */
    public boolean hasCards() {
        return !isEmpty();
    }
}
