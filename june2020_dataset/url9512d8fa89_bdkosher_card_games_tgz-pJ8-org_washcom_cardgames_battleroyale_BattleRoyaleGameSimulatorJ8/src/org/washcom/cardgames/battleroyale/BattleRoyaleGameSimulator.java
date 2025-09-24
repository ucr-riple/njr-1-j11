package org.washcom.cardgames.battleroyale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.washcom.cardgames.core.DeckBuilder;

/**
 *
 * @author Joe
 */
public class BattleRoyaleGameSimulator {

    private static final Logger log = Logger.getLogger(BattleRoyaleGameSimulator.class.getName());
    public static final Player player1 = new Player("Joe");
    public static final Player player2 = new Player("Patrick");
    public static final Player player3 = new Player("Nolan");
    private static final Player noWinner = new Player("NO WINNER");
    private final Map<Player, Integer> victoriesByPlayer = new HashMap<>();
    private final List<Integer> gameLengths = new ArrayList<>();
    private final List<Integer> deckSwapsPerGame = new ArrayList<>();
    private final List<Integer> unresolvedBattlesPerGame = new ArrayList<>();

    public BattleRoyaleGameSimulator(int nbrOfGames) {
        player2.setSecondChanceStrategy(new OptimalSecondChanceStrategy());
        for (int i = 0; i < nbrOfGames; ++i) {
            BattleRoyaleGame game = new BattleRoyaleGame();
            game.play(DeckBuilder.buildShuffled52CardDeck(), player1, player2, player3);
            Player winner = game.getWinner();
            if (winner == null) {
                winner = noWinner;
            }
            Integer victories = victoriesByPlayer.get(winner);
            victoriesByPlayer.put(winner, victories == null ? 1 : victories + 1);
            gameLengths.add(game.getRoundsPlayed());
            deckSwapsPerGame.add(game.getSwappedHandsCount());
        }
    }

    public int getTotalGamesPlayed() {
        return gameLengths.size();
    }

    public int getLongestGameLength() {
        return Collections.max(gameLengths);
    }

    public int getShortestGameLength() {
        return Collections.min(gameLengths);
    }

    public double getAverageGameLength() {
        int total = 0;
        for (Integer gameLength : gameLengths) {
            total += gameLength;
        }
        return (double) total / (double) gameLengths.size();
    }

    public double getGameLengthVariance() {
        double avg = getAverageGameLength();
        double diffSqsSum = 0.0d;
        for (Integer length : gameLengths) {
            double diff = length - avg;
            double diffSq = diff * diff;
            diffSqsSum += diffSq;
        }
        return new BigDecimal(diffSqsSum).divide(new BigDecimal(gameLengths.size())).doubleValue();
    }

    public double getGameLengthStdDev() {
        return Math.sqrt(getGameLengthVariance());
    }
    
    public int getNumberOfGamesWonByPlayer(Player player) {
        Integer victories = victoriesByPlayer.get(player);
        return victories == null ? 0 : victories;
    }
    
    public double getPercentageOfGamesWonByPlayer(Player player) {
        return new BigDecimal(getNumberOfGamesWonByPlayer(player)).divide(new BigDecimal(getTotalGamesPlayed())).doubleValue() * 100d;
    }
    
    public int getNumberOfUnresolvedGames() {
        Integer victories = victoriesByPlayer.get(noWinner);
        return victories == null ? 0 : victories;
    }
    
    public double getPercentageOfUnresolvedGames() {
        return new BigDecimal(getNumberOfUnresolvedGames()).divide(new BigDecimal(getTotalGamesPlayed())).doubleValue() * 100d;
    }
    
    public List<Integer> getDeckSwapsPerGame() {
        return deckSwapsPerGame;
    }
    
    public int getMostDeckSwapsPerGame() {
        return Collections.max(deckSwapsPerGame);
    }

    public int getFewestDeckSwapsPerGame() {
        return Collections.min(deckSwapsPerGame);
    }
    
    public double getAverageDeckSwapsPerGame() {
        int total = 0;
        for (Integer deckSwaps : deckSwapsPerGame) {
            total += deckSwaps;
        }
        return (double) total / (double) deckSwapsPerGame.size();
    }
    
    public double getDeckSwapsPerGameVariance() {
        double avg = getAverageDeckSwapsPerGame();
        double diffSqsSum = 0.0d;
        for (Integer swaps : deckSwapsPerGame) {
            double diff = swaps - avg;
            double diffSq = diff * diff;
            diffSqsSum += diffSq;
        }
        return new BigDecimal(diffSqsSum).divide(new BigDecimal(deckSwapsPerGame.size())).doubleValue();
    }  

    public List<Integer> getUnresolvedBattlesPerGame() {
        return unresolvedBattlesPerGame;
    }
    
     public int getMostUnresolvedBattlesPerGame() {
        return Collections.max(unresolvedBattlesPerGame);
    }

    public int getFewestUnresolvedBattlesPerGame() {
        return Collections.min(unresolvedBattlesPerGame);
    }
    
    public double getAverageUnresolvedBattlesPerGame() {
        int total = 0;
        for (Integer unresolvedBattles : unresolvedBattlesPerGame) {
            total += unresolvedBattles;
        }
        return (double) total / (double) unresolvedBattlesPerGame.size();
    }
    
    public double getUnresolvedBattlesPerGameVariance() {
        double avg = getAverageUnresolvedBattlesPerGame();
        double diffSqsSum = 0.0d;
        for (Integer unresolvedBattles : unresolvedBattlesPerGame) {
            double diff = unresolvedBattles - avg;
            double diffSq = diff * diff;
            diffSqsSum += diffSq;
        }
        return new BigDecimal(diffSqsSum).divide(new BigDecimal(unresolvedBattlesPerGame.size())).doubleValue();
    }
    
    public static void main(String[] args) {
        int numGames = 10;
        if (args.length > 0) {
            numGames = Integer.parseInt(args[0]);
        }
        BattleRoyaleGameSimulator simulator = new BattleRoyaleGameSimulator(numGames);
        log.info("Total games played: " + simulator.getTotalGamesPlayed());
        log.info("Shortest game length: " + simulator.getShortestGameLength());
        log.info("Longest game length: " + simulator.getLongestGameLength());
        log.info("Average game length: " + simulator.getAverageGameLength());
        for (Player player : new Player[] { player1, player2, player3 }) {
            log.info("Number of victories by " + player + ": " + simulator.getNumberOfGamesWonByPlayer(player));
            log.info("Percentage of victories by " + player + ": " + simulator.getPercentageOfGamesWonByPlayer(player));
        }
        log.info("Number of unresolved games: " + simulator.getNumberOfUnresolvedGames());
        log.info("Percentage of unresolved games: " + simulator.getPercentageOfUnresolvedGames());
    }
}
