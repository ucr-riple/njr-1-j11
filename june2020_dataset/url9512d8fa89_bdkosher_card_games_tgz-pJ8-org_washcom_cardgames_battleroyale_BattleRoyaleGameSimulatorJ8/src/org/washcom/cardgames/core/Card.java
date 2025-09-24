package org.washcom.cardgames.core;

/**
 * A playing card.
 * 
 * @author Joe
 */
public class Card {
    
    private final Suit suit;
    
    private final Denomination denomination;

    /**
     * Creates a new card of the given suit and denomination.
     * TODO what about Jokers?
     * 
     * @param denomination - cannot be null
     * @param suit - cannot be null
     */
    public Card(Denomination denomination, Suit suit) {
        if (suit == null) {
            throw new NullPointerException("Suit arg cannot be null.");
        }
        if (denomination == null) {
            throw new NullPointerException("Denomination arg cannot be null.");
        }
        this.suit = suit;
        this.denomination = denomination;
    }

    /**
     * Returns the Card's suit.
     * 
     * @return 
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns the Card's denomination.
     * 
     * @return 
     */
    public Denomination getDenomination() {
        return denomination;
    }
    
    /**
     * Returns the full name of the card according to its denomination and suit.
     * 
     * @return 
     */
    @Override
    public String toString() {
        return denomination + " of " + suit;
    }

    /**
     * IDE-generated. Based off the suit and denomination.
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
        final Card other = (Card) obj;
        if (this.suit != other.suit) {
            return false;
        }
        if (this.denomination != other.denomination) {
            return false;
        }
        return true;
    }

    /**
     * IDE-generated. Based off the suit and denomination.
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.suit != null ? this.suit.hashCode() : 0);
        hash = 89 * hash + (this.denomination != null ? this.denomination.hashCode() : 0);
        return hash;
    }
    
}
