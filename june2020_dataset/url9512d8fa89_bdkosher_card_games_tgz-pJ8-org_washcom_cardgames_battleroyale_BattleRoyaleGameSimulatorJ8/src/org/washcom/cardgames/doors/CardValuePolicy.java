package org.washcom.cardgames.doors;

import org.washcom.cardgames.core.Card;

/**
 * In the doors game, you have to turn over a number of cards from the deck which corresponds to the value of the top card
 * shown; the value of the top card shown is determined by this interface.
 * 
 * @author Joe
 */
public interface CardValuePolicy {
    
    int getValue(Card card);
    
}
