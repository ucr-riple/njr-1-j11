package org.washcom.cardgames.doors;

import java.util.List;
import org.washcom.cardgames.core.Card;
import org.washcom.cardgames.core.Deck;
import org.washcom.cardgames.core.SolitaireGameResult;
import static org.washcom.cardgames.core.SolitaireGameResult.LOSE;
import static org.washcom.cardgames.core.SolitaireGameResult.WIN;
import org.washcom.util.DeckExhaustedException;

/**
 *
 * @author Joe
 */
public class DoorsGame {

    private final Deck deck;
    private final CardValuePolicy valuePolicy;
    /**
     * Round integer tracks state: 0 = unstarted, greater than 0 = in progress
     */
    private int round = 0;
    private Card topCard = null;
    private SolitaireGameResult result = null;

    public DoorsGame(Deck deck, CardValuePolicy valuePolicy) {
        if (deck == null) {
            throw new NullPointerException("Deck arg cannot be null.");
        }
        if (valuePolicy == null) {
            throw new NullPointerException("CardValuePolicy arg cannot be null.");
        }
        if (deck.isEmpty()) {
            throw new IllegalArgumentException("Cannot play with an empty deck.");
        }

        this.deck = deck;
        this.valuePolicy = valuePolicy;
    }

    /**
     *
     * @return true - if the game can be continued, false if the game has ended.
     */
    public boolean playRound() {
        if (result != null) {
            return false;
        }
        round++;
        try {
            /*
             * if it's the first round, turn over the top card; otherwise, turn over the number of cards shown by the top
             * turned-over card
             */
            if (round == 1) {
                topCard = deck.draw();
            } else {
                List<Card> drawn = deck.draw(getTopCardValue());
                topCard = drawn.get(drawn.size() - 1);
            }

        } catch (DeckExhaustedException e) {
            result = LOSE;
            return false;
        }
        if (deck.isEmpty()) {
            result = WIN;
            return false;
        }
        return true;
    }

    public Card getTopCard() {
        return topCard;
    }

    public int getTopCardValue() {
        return valuePolicy.getValue(topCard);
    }

    public int getRound() {
        return round;
    }

    public SolitaireGameResult getResult() {
        return result;
    }

    public Deck getDeck() {
        return deck;
    }

    public CardValuePolicy getValuePolicy() {
        return valuePolicy;
    }
}
