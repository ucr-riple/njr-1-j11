package org.washcom.cardgames.battleroyale;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.washcom.cardgames.core.Card;
import org.washcom.cardgames.core.Denomination;

/**
 * A battle is ambiguously both a) the name for a round and b) the name for a
 * round that has no decisive winner after the first set of cards are played.
 *
 * Battles can extend for up to three continuations. On the final continuation,
 * no additional fees will be played. If the final continuation results in no
 * clear winner, the player NOT participating the battle gets the cards--this is
 * analagous to two armies duking it out so brutally that they kill each other,
 * leaving a non-participating army to swoop in and collect all of the loot.
 *
 * It's theoretically possible for a three-way battle to extend up through the
 * final continuation and result in no clear winner. I've never witnessed such
 * an event, so it must be rare and is thusly implemented by throwing an
 * Exception, which the Game class will catch and deal with appropriately.
 *
 * @author Joe
 */
public class Battle {

    private static final Logger log = Logger.getLogger(Battle.class.toString());
    public static final int MAXIMUM_BATTLE_CONTINUATIONS = 3;
    private final int number;
    private final BattleRoyaleGame game;
    private final List<BattleCard> battleCards = new ArrayList<>();
    private List<Player> battlers;
    private int continuations = 0;

    /**
     * Creates a new battle.
     *
     * @param number - the round number
     * @param game
     */
    public Battle(int number, BattleRoyaleGame game) {
        Preconditions.checkNotNull(game);
        Preconditions.checkArgument(number > 0);
        this.number = number;
        this.game = game;
    }

    public List<Player> getBattlers() {
        return battlers;
    }

    /**
     * Returns the round number.
     *
     * @return
     */
    public int getNumber() {
        return number;
    }

    public BattleRoyaleGame getGame() {
        return game;
    }

    public List<BattleCard> getBattleCards() {
        return battleCards;
    }

    /**
     * Returns the number of times the battle has continued beyond the initial
     * playing of battle cards.
     *
     * @return
     */
    public int getContinuations() {
        return continuations;
    }

    /**
     * Does a skirmish: collects a single card from each active player. Returns
     * the winning player or null if there is no winning player.
     *
     * @return
     */
    public void fight(BattleAssessor assessor) {
        this.battlers = game.getActivePlayers();
        log.info("There are " + battlers.size() + " players active to fight battle " + number);
        Player nonBattler = null;
        BattleCard winner = null;
        Map<Player, Integer> fees = Collections.emptyMap();
        /* Battle continuations are modeled so that the playing of the battle cards
         * is the last phase of each continuation, which means for the start of the 
         * continuation there is an effective no-op of collecting the played battle 
         * cards, collecting fees, and eliminating cardless battlers in between.
         */
        for (; continuations <= MAXIMUM_BATTLE_CONTINUATIONS; ++continuations) {
            if (continuations > 0) {
                log.info("Executing continuation " + continuations + " of battle " + number);
            } else {
                log.info("Fighting battle " + number);
            }

            if (winner != null) {
                spoilsToTheVictor(winner.getPlayedBy());
                game.incrementNbrOfBattlesByLength(continuations);
                return;
            }
            
            addBattleCardsToGamePot();
            eliminateCardlessBattlers();
            if (battlers.size() == 1) {
                spoilsToTheVictor(battlers.get(0));
                game.incrementNbrOfBattlesByLength(continuations);
                return;
            }
            
            addFeeCardsToGamePot(fees);

            eliminateCardlessBattlers();
            if (battlers.size() == 1) {
                spoilsToTheVictor(battlers.get(0));
                game.incrementNbrOfBattlesByLength(continuations);
                return;
            }
            
            nonBattler = findNonBattler();
            playBattleCards();
            winner = assessor.pickWinner(this);
            fees = assessor.determineFees(this);
        }
        // unresolved battle, so inactive player gets the spoils
        if (nonBattler == null) {
            throw new UnresolvedThreeWayBattleException();
        } else {
            log.info("Unresolved battle, spoils go to the non-battling battler, " + nonBattler);
            spoilsToTheVictor(nonBattler);
            game.incrementUnresolvedBattleCount();
        }
    }

    /**
     * The non-battler is eligible to receive all the spoils in the event of an
     * unresolvable battle. If all players are battling, or if there is more
     * than one non-battler, this method returns null.
     *
     * @return
     */
    private Player findNonBattler() {
        List<Player> allPlayers = new ArrayList<>(game.getPlayers());
        allPlayers.removeAll(battlers);
        return allPlayers.size() == 1 ? allPlayers.get(0) : null;
    }

    private void playBattleCards() {
        log.info("Playing battle cards...");
        for (Player battler : battlers) {
            Card card = battler.getHand().draw();
            battleCards.add(new BattleCard(card, battler));
            log.info("\t" + battler + " played a " + card);
        }
        
        boolean tookChance = checkForSecondChances();
        while (tookChance) {    //could run more times if person keeps plays another three
            tookChance = checkForSecondChances();
        }
    }
    
    private boolean checkForSecondChances() {
        boolean tookSecondChance = false;
        //Look for 3s, which gives the player an opportunity for a second chance
        for (int i = 0; i < battleCards.size(); i++) {
            BattleCard card = battleCards.get(i);
            if (card.getCard().getDenomination() == Denomination.THREE) {
                if (card.getPlayedBy().handHasAtLeast(1)) { //make sure they have a card left to burn
                    List<BattleCard> subList = new ArrayList<>(battleCards);
                    subList.remove(card);
                    if (card.getPlayedBy().getSecondChanceStrategy().shouldTryForAnotherCard(card, subList)) {
                        BattleCard newCard = this.getGame().burnAThreeAndPlayAnother(card);
                        tookSecondChance = true;
                    }
                }
            }
        }
        return tookSecondChance;
    }
    
    private void spoilsToTheVictor(Player victor) {
        log.info("Giving spoils of " + game.getGameCards().size() + " cards to " + victor);
        /* shuffling necessary in war to prevent endless fighting, perhaps necessary here, too? */
        game.getGameCards().shuffle();
        victor.getHand().putOnBottom(game.getGameCards().drawAll());
    }

    private void addBattleCardsToGamePot() {
        log.info("Adding " + battleCards.size() + " battle cards to game pot.");
        for (BattleCard battleCard : battleCards) {
            game.getGameCards().put(battleCard.getCard());
        }
        battleCards.clear();
    }

    private void eliminateCardlessBattlers() {
        for (Iterator<Player> it = battlers.iterator(); it.hasNext();) {
            Player player = it.next();
            if (player.getHand().isEmpty()) {
                it.remove();
                log.info(player + " has run out of cards.");
            }
        }
    }

    /**
     * Collects fees from the battle
     */
    private void addFeeCardsToGamePot(Map<Player, Integer> fees) {
        log.info("Adding fee cards to battle pot...");
        for (Map.Entry<Player, Integer> entry : fees.entrySet()) {
            Player battler = entry.getKey();
            int fee = entry.getValue();
            log.info("\t" + battler + " pays fee of " + fee);
            game.getGameCards().put(battler.getHand().drawUpTo(fee));
        }
    }
}
