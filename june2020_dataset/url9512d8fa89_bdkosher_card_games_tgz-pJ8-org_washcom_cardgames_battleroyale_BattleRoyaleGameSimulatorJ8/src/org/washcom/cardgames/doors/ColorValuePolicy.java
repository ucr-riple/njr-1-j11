package org.washcom.cardgames.doors;

import org.washcom.cardgames.core.Card;
import org.washcom.cardgames.core.Color;

/**
 * The value of a card is determined by its color alone.
 * @author Joe
 */
public class ColorValuePolicy implements CardValuePolicy {
    
    public static final int DEFAULT_BLACK_VALUE = 2;
    
    public static final int DEFAULT_RED_VALUE = 1;
    
    private final int blackValue;
    
    private final int redValue;
    
    public ColorValuePolicy() {
        this(DEFAULT_BLACK_VALUE, DEFAULT_RED_VALUE);
    }

    public ColorValuePolicy(int blackValue, int redValue) {
        this.blackValue = blackValue;
        this.redValue = redValue;
    }
    
    @Override
    public int getValue(Card card) {
        return getValue(card.getSuit().getColor());
    }
    
    public int getValue(Color color) {
        return color == Color.BLACK ? blackValue : redValue;
    }
}
