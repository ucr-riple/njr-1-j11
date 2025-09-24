package org.washcom.cardgames.core;

import static org.washcom.cardgames.core.Color.BLACK;
import static org.washcom.cardgames.core.Color.RED;

/**
 * The suits.
 * 
 * @author Joe
 */
public enum Suit {

    HEARTS(RED), DIAMONDS(RED), SPADES(BLACK), CLUBS(BLACK);
    final Color color;

    Suit(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
    
}
