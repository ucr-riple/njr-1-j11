package org.washcom.util;

import java.util.*;

/**
 * Represents a deck of cards.
 * 
 * TODO: add a cut method?
 * 
 * @author Joe
 */
public class Deck<T> {

    private final Deque<T> internal = new ArrayDeque<>();
    
    private final Random random = new Random();

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
    public Deck(Collection<T> initial) {
        if (initial == null) {
            throw new NullPointerException("initial arg cannot be null.");
        }
        internal.addAll(initial);
    }

    /**
     * Shuffles the deck according to a uniform random distribution.
     */
    public void shuffle() {
        List<T> copy = new ArrayList<>();
        copy.addAll(internal);
        internal.clear();
        while (!copy.isEmpty()) {
            int idx = random.nextInt(copy.size());
            internal.push(copy.remove(idx));
        }
    }

    /**
     * Remove the top card from the deck. 
     * 
     * @return
     * @throws DeckExhaustedException - if the deck is empty
     */
    public T draw() throws DeckExhaustedException {
        return draw(true);
    }

    /**
     * Draw the given number of cards from the deck. The order is preserved in the returned list:
     * the first card is the top card of the deck and so on.
     * 
     * @param nbrOfTs - must be greater than 0
     * @return
     * @throws DeckExhaustedException - if there aren't enough cards in the deck which can be drawn; the
     *      thrown exception will contain the partial list of drawn cards.
     */
    public List<T> draw(int nbrOfTs) throws DeckExhaustedException {
        return draw(nbrOfTs, true);
    }

    /**
     * Remove the bottom card from the deck. 
     * 
     * @return
     * @throws DeckExhaustedException - if the deck is empty
     */
    public T drawFromBottom() throws DeckExhaustedException {
        return draw(false);
    }

    /**
     * Draw the given number of cards from the bottom of the deck. The order is preserved in the returned list:
     * the first card is the bottom card of the deck and so on.
     * 
     * @param nbrOfTs - must be greater than 0
     * @return
     * @throws DeckExhaustedException - if there aren't enough cards in the deck which can be drawn; the
     *      thrown exception will contain the partial list of drawn cards.
     */
    public List<T> drawFromBottom(int nbrOfTs) throws DeckExhaustedException {
        return draw(nbrOfTs, false);
    }

    /**
     * Remove a card from the deck. If {@code top} is true, it's the top card; otherwise, it's the bottom card.
     *
     * @param top - true if to be drawn from the top; false if to be drawn from the bottom 
     * @return
     * @throws DeckExhaustedException - if the deck is empty
     */
    private T draw(boolean top) throws DeckExhaustedException {
        if (internal.isEmpty()) {
            throw new DeckExhaustedException();
        }
        return top ? internal.removeFirst() : internal.removeLast();
    }

    /**
     * Draw the given number of cards from the top of the deck (if {@code top} is true) or the bottom of the deck
     * (if {@code top} is false). The order is preserved in the returned list.
     * 
     * @param nbrOfTs - must be non-negative
     * @param top - true if to be drawn from the top; false if to be drawn from the bottom
     * @return
     * @throws DeckExhaustedException - if there aren't enough cards in the deck which can be drawn; the
     *      thrown exception will contain the partial list of drawn cards.
     */
    private List<T> draw(int nbrOfTs, boolean top) throws DeckExhaustedException {
        if (nbrOfTs < 0) {
            throw new IllegalArgumentException("Integer arg must be 1 or more.");
        }
        if (nbrOfTs == 0) {
            return Collections.emptyList();
        }
        List<T> hand = new ArrayList<>();
        try {
            for (int i = nbrOfTs; i > 0; --i) {
                hand.add(top ? internal.removeFirst() : internal.removeLast());
            }
        } catch (NoSuchElementException e) {
            throw new DeckExhaustedException(hand);
        }
        return hand;
    }

    /**
     * Draws all cards from the deck.
     * 
     * @return 
     */
    public List<T> drawAll() {
        try {
            return draw(internal.size());
        } catch (DeckExhaustedException e) {
            throw new IllegalStateException("This should never happen.", e);
        }
    }
    
    /**
     * Draws a card at random.
     * 
     * @return
     * @throws DeckExhaustedException - if there are no cards in the deck to draw
     */
    public T drawAtRandom() throws DeckExhaustedException {
        if (internal.isEmpty()) {
            throw new DeckExhaustedException();
        }
        Iterator<T> cards = internal.iterator();
        T current = cards.next();
        for (int i = 0; i < new Random().nextInt(size()); ++i) {
            current = cards.next();
        }
        cards.remove();
        return current;
    }

    /**
     * Returns the given card to the bottom of the deck.
     * 
     * @param card - cannot be null
     */
    public void putOnBottom(T card) {
        put(card, false);
    }

    /**
     * Returns the given cards to the bottom of the deck in the order provided. The last card in the
     * list becomes the last card in the deck.
     * 
     * @param cards - cannot be null
     */
    public void putOnBottom(List<T> cards) {
        put(cards, false);
    }

    /**
     * Puts the given card on the top of the deck.
     * 
     * @param card - cannot be null
     */
    public void put(T card) {
        put(card, true);
    }

    /**
     * Puts the given cards on the top of the deck in the order provided. The last card in the
     * list becomes the top card of the deck.
     * 
     * @param cards - cannot be null
     */
    public void put(List<T> cards) {
        put(cards, true);
    }

    /**
     * Returns the given card to the top of the deck if {@code top} is true; otherwise returns the card
     * to the bottom of the deck.
     * 
     * @param top
     * @param card - cannot be null
     */
    private void put(T card, boolean top) {
        if (card == null) {
            throw new NullPointerException("T cannot be null.");
        }
        if (top) {
            internal.addFirst(card);
        } else {
            internal.addLast(card);
        }
    }

    /**
     * Returns the given cards to the deck in the order provided. If {@code top} is true, they're returned to
     * the top and the last card in the argument list becomes the top card of the deck; otherwise, they're
     * returned to the bottom of the deck and the last card in the argument list becomes the bottom card.
     * 
     * @param top
     * @param cards - cannot be null
     */
    private void put(List<T> cards, boolean top) {
        if (cards == null) {
            throw new NullPointerException("Ts collected cannot be null.");
        }
        if (top) {
            for (T card : cards) {
                internal.addFirst(card);
            }
        } else {
            for (T card : cards) {
                internal.addLast(card);
            }
        }
    }

    /**
     * Returns the number of cards presently in the deck.
     * 
     * @return 
     */
    public int size() {
        return internal.size();
    }

    /**
     * Returns true if there are no cards left in the deck.
     * 
     * @return 
     */
    public boolean isEmpty() {
        return internal.isEmpty();
    }

}
