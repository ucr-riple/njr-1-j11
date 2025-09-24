package org.washcom.cardgames.core;

/**
 *
 * @author Joe
 */
public enum Denomination {

    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    JACK(11, true), QUEEN(12, true), KING(13, true), ACE(1);
    private final Integer value;
    private final boolean royalty;

    /**
     * Creates a non-royalty denomination of the given ordinal value.
     * 
     * @param value 
     */
    Denomination(int value) {
        this(value, false);
    }

    /**
     * Creates a denomination of the given ordinal value and royalty.
     * 
     * @param value
     * @param royalty 
     */
    Denomination(Integer value, boolean royalty) {
        this.value = value;
        this.royalty = royalty;
    }

    /**
     * Returns true if this denomination is royalty.
     * 
     * @return 
     */
    public boolean isRoyalty() {
        return royalty;
    }

    /**
     * Returns a numeric value indicating the ranking of the denomination. Ace = 1.
     * 
     * @return 
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
