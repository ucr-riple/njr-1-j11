package org.washcom.cardgames.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.washcom.util.LoopingIterator;

/**
 * A utility class with methods for dealing decks to players.
 * 
 * @author Joe
 */
public class DeckDealer {
    
    /**
     * Deals the entire deck to all of the given players. Depending on the number of cards/players, some players may
     * have more cards than others.
     * 
     * @param deck
     * @param players - cannot be null or empty
     * @throws IllegalArgumentException if there are no players to deal to
     */
    public static <P extends Player> void dealEntirely(Deck deck, P... players) {
        /* 
         * It's OK in Java 7 to just check the array for null, no need to check for a (Player[])-typed null, e.g.:
         * (players == null || (players.length == 1 && players[0] == null))
         */
        dealEntirely(deck, players == null ? null : Arrays.asList(players));
    }
    
    /**
     * Deals the entire deck to all of the given players. Depending on the number of cards/players, some players may
     * have more cards than others.
     * 
     * @param deck
     * @param players - cannot be null or empty
     * @throws IllegalArgumentException if there are no players to deal to
     */
    public static <P extends Player> void dealEntirely(Deck deck, Collection<P> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("Must have at least one player to deal to.");
        }
        Iterator<P> playerIterator = new LoopingIterator<>(players);
        while (deck.hasCards()) {
            playerIterator.next().getHand().put(deck.draw());
        }
    }
    
    /**
     * Deals the entire deck to all of the given players so that each player has an equal number of cards. 
     * 
     * @param deck
     * @param players - cannot be null, but may be empty
     * @throws IllegalArgumentException if there are more cards than players
     */
    public static <P extends Player> void dealFairly(Deck deck, P... players) {
        dealFairly(deck, players == null ? null : Arrays.asList(players));
    }
    
    /**
     * Deals the entire deck to all of the given players so that each player has an equal number of cards.
     * 
     * @param deck - may be empty, but if non-empty, cannot contain fewer cards than players
     * @param players - cannot be null, but may be empty
     * @throws IllegalArgumentException if there are more cards than players
     */
    public static <P extends Player> void dealFairly(Deck deck, Collection<P> players) {
        if (players == null) {
            throw new NullPointerException("Players cannot be null.");
        }
        final int deckSize = deck.size();
        final int nbrOfPlayers = players.size();
        if (deckSize == 0 || nbrOfPlayers == 0) {
            return;
        } else if (deckSize < nbrOfPlayers) {
            throw new IllegalArgumentException("Deck not large enough for number of players.");
        }
        Iterator<P> playerIterator = new LoopingIterator<>(players);
        for (int i = 0; i < deckSize - (deckSize % nbrOfPlayers); ++i) {
            playerIterator.next().getHand().put(deck.draw());
        }
    }
    
}
