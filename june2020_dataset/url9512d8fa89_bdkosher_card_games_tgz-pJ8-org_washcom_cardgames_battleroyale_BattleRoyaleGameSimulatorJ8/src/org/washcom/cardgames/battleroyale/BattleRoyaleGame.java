package org.washcom.cardgames.battleroyale;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.washcom.cardgames.core.Deck;
import org.washcom.cardgames.core.DeckDealer;

/**
 * The game class maintains the state of the game. - the players - the current
 * round - game cards (not belonging to any particular player)
 *
 * The game class also contains methods to move state, namely, to start a new
 * round.
 *
 * @author Joe
 */
public class BattleRoyaleGame {

    private static final Logger log = Logger.getLogger(BattleRoyaleGame.class.toString());
    private static final int MINIMUM_NUMBER_OF_PLAYERS = 2;
    private static final boolean LEFTOVER_CARDS_FROM_DEAL_GO_INTO_GAMEPOT = true;
    private final BattleAssessor assessor;
    private List<Player> players;
    private Deck gameCards;
    private int currentRoundNumber = 0;
    private Battle currentBattle;
    private Player winner = null;
    private boolean swapHandsRuleEnabled = true;
    //private boolean threeCardBattleRuleEnabled = true;
    private int swappedHandsCount = 0;
    private int unresolvedBattleCount = 0;
    
    /**
     * Maps a number of continuations (0 = decided on the first playing of battle
     * cards, through 3 = the last continuation) to the number of battles that
     * concluded with that many continuations. Unresolved battles are captured
     * in a separate variable, {@code unresolvedBattleCount}
     */
    private Map<Integer, Integer> battlesByNbrOfContinuations = new HashMap<>();

    /**
     * Initializes a new game. The game is not started until {@code start} is
     * called. The provided listener is responsible for driving the state of the
     * game.
     */
    public BattleRoyaleGame() {
        this(new DefaultBattleAccessor());
    }

    /**
     * Initializes a new game. The game is not started until {@code start} is
     * called. The provided listener is responsible for driving the state of the
     * game.
     */
    public BattleRoyaleGame(BattleAssessor assessor) {
        if (assessor == null) {
            throw new IllegalArgumentException("BattleAssessor arg cannot be null.");
        }
        this.assessor = assessor;
    }

    private void initializeGame(Deck deck, Player... players) {
        Preconditions.checkNotNull(deck);
        Preconditions.checkArgument(players != null && players.length >= MINIMUM_NUMBER_OF_PLAYERS);
        Preconditions.checkArgument(deck.size() >= MINIMUM_NUMBER_OF_PLAYERS);
        this.players = Arrays.asList(players);
        this.gameCards = deck;
        if (LEFTOVER_CARDS_FROM_DEAL_GO_INTO_GAMEPOT) {
            DeckDealer.dealFairly(this.gameCards, players);
        } else {
            DeckDealer.dealEntirely(this.gameCards, players);
        }

    }

    /**
     * Starts a new game of Battle Royale.
     *
     * @param deck - the cards to be used in the game
     * @param players - the players who will be playing the game
     */
    public void play(Deck deck, Player... players) {
        initializeGame(deck, players);
        /*
         * Battle as long as there are two or more players in the game.
         */
        while (getActivePlayers().size() > 1) {
            try {
                battle();
            } catch (UnresolvedThreeWayBattleException e) {
                log.info("Uh-oh, no winner: " + e.getLocalizedMessage());
            }
        }

        /*
         * If there's one player left standing, declare him the winner. It is possible given few enough cards and high enough max
         * battle continuations that all active players are engaged in a battle that never concludes, thus ending the game without
         * a declared winner.
         */
        if (getActivePlayers().size() == 1) {
            winner = getActivePlayers().get(0);
        }
    }

    void battle() {
        currentBattle = new Battle(++currentRoundNumber, this);
        currentBattle.fight(assessor);
    }

    /**
     * Swaps hands between players, provided the swap hands rule is enabled.
     */
    void swapHands(Player one, Player other) {
        if (swapHandsRuleEnabled) {
            one.swapHands(other);
            log.info(one + " swapped hands with " + other);
            ++swappedHandsCount;
        }
    }
    
    BattleCard burnAThreeAndPlayAnother(BattleCard toBurn) {
        //burn
        getGameCards().put(toBurn.getCard());
        
        //redraw another card
        BattleCard newCard = new BattleCard(toBurn.getPlayedBy().getHand().draw(), toBurn.getPlayedBy());
        getCurrentBattle().getBattleCards().set(getCurrentBattle().getBattleCards().indexOf(toBurn), newCard);
        log.info(newCard.getPlayedBy() + " is burning a 3 and drawing a "+ newCard.getCard());
        return newCard;
    }

    /**
     * Returns the current battle.
     */
    public Battle getCurrentBattle() {
        return currentBattle;
    }

    /**
     * Returns the players playing this game.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the cards which do not belong to any player in particular.
     */
    public Deck getGameCards() {
        return gameCards;
    }

    /**
     * Returns all players who are participating in this game.
     */
    public List<Player> getActivePlayers() {
        List<Player> active = new ArrayList<>(players.size());
        for (Player player : players) {
            if (!player.getHand().isEmpty()) {
                active.add(player);
            }
        }
        return active;
    }

    /**
     * Returns the winner of the game. If the game hasn't finished, this method
     * will return null.
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Returns the number of rounds played so far.
     */
    public int getRoundsPlayed() {
        return currentRoundNumber;
    }

    /**
     * Returns the number of times hands were swapped.
     */
    public int getSwappedHandsCount() {
        return swappedHandsCount;
    }
    
    /**
     * Returns a count of battles according to their length, 0 meaning they were
     * won decisively upon the first play, 3 meaning a winner was not decided
     * until the bitter end.
     * @param continations - a number between 0 and 3, inclusive
     */
    public int getNbrOfBattlesByLength(int continuations) {
        Preconditions.checkArgument(continuations >= 0 && continuations <= Battle.MAXIMUM_BATTLE_CONTINUATIONS);
        return battlesByNbrOfContinuations.get(continuations);
    }
    
    void incrementNbrOfBattlesByLength(int continuations) {
        Integer count = battlesByNbrOfContinuations.get(continuations);
        battlesByNbrOfContinuations.put(continuations, count == null ? 1 : count + 1);
    }

    /**
     * Returns the number of battles that had no winner after the max number
     * of continuations had occurred.
     */
    public int getUnresolvedBattleCount() {
        return unresolvedBattleCount;
    }
    
    void incrementUnresolvedBattleCount() {
        ++unresolvedBattleCount;
    }

}
