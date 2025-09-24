package org.washcom.cardgames.core;

import java.util.Objects;

/**
 *
 * @author Joe
 */
public class Player {

    private final String name;
    private final Deck hand = new Deck();

    public Player(String name) {
        if (name == null) {
            throw new NullPointerException("Player name cannot be null.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Use mutators on the returned Deck to manipulate the player's hand.
     * 
     * @return 
     */
    public Deck getHand() {
        return hand;
    }
    
    /**
     * Returns true if the size of this Player's hand has{@code nbrOfCards} or more.
     * 
     * @param nbrOfCards
     * @return 
     */
    public boolean handHasAtLeast(int nbrOfCards) {
        return hand.size() >= nbrOfCards;
    }
    
    /**
     * Players are considered equal when they have the same name.
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    /**
     * HashCode determined solely by the player name.
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return name;
    }
}
