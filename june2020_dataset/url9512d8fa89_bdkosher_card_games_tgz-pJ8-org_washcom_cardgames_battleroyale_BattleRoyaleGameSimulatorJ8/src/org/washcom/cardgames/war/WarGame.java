package org.washcom.cardgames.war;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.washcom.cardgames.core.*;

/**
 *
 * @author Joe
 */
public class WarGame {

    /**
     * The number of cards which each player plays in the event their played cards match.
     */
    public static final int WAR_FEE = 3;
    private final Player player1;
    private final Player player2;
    private final Comparator<Card> chooser = new AcesHighCardComparator();
    private boolean gameOver = false;

    public WarGame() {
        this(DeckBuilder.buildShuffled52CardDeck());
    }

    public WarGame(Deck deck) {
        this(new Player("1"), new Player("2"), deck);
    }

    public WarGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public WarGame(Player player1, Player player2, Deck deckToDistribute) {
        this.player1 = player1;
        this.player2 = player2;
        DeckDealer.dealEntirely(deckToDistribute, player1, player2);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    int battle() {
        return battle(new Deck(), 0, 0);
    }

    /**
     * Perform a battle.
     *
     * @param pot
     * @param fee
     * @param wars - the number of wars that have occurred so far in this recursive battle
     * @return
     */
    private int battle(Deck pot, int fee, int wars) {
        int cardsNeededToBattle = fee + 1;
        if (!player1.handHasAtLeast(cardsNeededToBattle)) {
            pot.put(player1.getHand().drawAll());
            gameOver = true;
        }
        if (!player2.handHasAtLeast(cardsNeededToBattle)) {
            pot.put(player2.getHand().drawAll());
            gameOver = true;
        }

        if (gameOver) {
            return wars;
        }

        pot.put(player1.getHand().draw(fee));
        pot.put(player2.getHand().draw(fee));

        Card card1 = player1.getHand().draw();
        Card card2 = player2.getHand().draw();
        /*
         * Some randomness is indeed necessary for some games to terminate.
         */
        if (Math.random() < 0.5d) {
            pot.put(card1);
            pot.put(card2);
        } else {
            pot.put(card2);
            pot.put(card1);
        }
        int result = chooser.compare(card1, card2);
        if (result < 0) {
            player2.getHand().putOnBottom(pot.drawAll());
            return wars;
        } else if (result > 0) {
            player1.getHand().putOnBottom(pot.drawAll());
            return wars;
        } else {
            return battle(pot, WAR_FEE, wars + 1);
        }
    }
    
    public static class Result {
        Player winner;
        int totalBattles = 0;
        /**
         * Maps the number of wars which occurred during a battle (the key) to the number of battles
         * which occurred during the game with that number of wars. More often than not, there is no
         * war, which means that key=0 should have the highest value of the Map. The sum of all Map
         * values equals the total number of battles.
         */
        final Map<Integer, Integer> totalWars = new HashMap<>();
        private void recordBattle(int nbrOfWarsInBattle) {
            ++totalBattles;
            Integer wars = totalWars.get(nbrOfWarsInBattle);
            totalWars.put(nbrOfWarsInBattle, (wars == null ? 0 : wars) + 1);
        }
    }

    public Result play() {
        Result result = new Result();
        while (!isGameOver()) {
            int nbrOfWarsInBattle = battle();
            result.recordBattle(nbrOfWarsInBattle);
        }
        if (player1.getHand().isEmpty()) {
            if (!player2.getHand().isEmpty()) {
                result.winner = player2;
            }
        } else if (player2.getHand().isEmpty()) {
            result.winner = player1;
        }
        return result;
    }
}
