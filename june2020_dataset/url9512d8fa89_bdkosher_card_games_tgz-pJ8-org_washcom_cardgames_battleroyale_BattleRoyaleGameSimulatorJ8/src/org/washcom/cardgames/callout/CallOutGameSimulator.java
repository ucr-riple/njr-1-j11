package org.washcom.cardgames.callout;

import java.math.BigDecimal;
import org.washcom.cardgames.core.Deck;
import org.washcom.cardgames.core.DeckBuilder;
import static org.washcom.cardgames.core.SolitaireGameResult.WIN;

/**
 * Win ratios (using 1,000,000 games): 0.016219, 0.016143, 0.016068
 * @author Joe
 */
public class CallOutGameSimulator {
    
    private int gamesPlayed = 0;
    private int gamesWon = 0;

    public CallOutGameSimulator(int totalGamesToPlay) {
        for (int i = 0; i < totalGamesToPlay; ++i) {
            Deck deck = DeckBuilder.build52CardDeck();
            deck.shuffle();
            CallOutGame game = new CallOutGame(deck);
            gamesWon = gamesWon + (game.playGame() == WIN ? 1 : 0);
            ++gamesPlayed;
        }
    }
    
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }
    
    public static void main(String[] args) {
        CallOutGameSimulator sim = new CallOutGameSimulator(1000000);
        System.out.println("Won " + sim.getGamesWon() + " out of " + sim.getGamesPlayed() + " games.");
        System.out.println("Empirical win ratio: " + new BigDecimal(sim.getGamesWon()).divide(new BigDecimal(sim.getGamesPlayed())).floatValue());
    }
    
}
