package org.washcom.cardgames.battleroyale;

import java.util.List;

import org.washcom.cardgames.core.Deck;

/**
 *
 * @author Joe
 */
public class Player extends org.washcom.cardgames.core.Player {

    private boolean participatingInBattle = true;
    private int nbrOfBattlesFought = 0;
    private int nbrOfBattlesWon = 0;
    private SecondChanceStrategy strategy = null;
    
    public Player(String name) {
        super(name);
    }
    
    public SecondChanceStrategy getSecondChanceStrategy() {
        if (strategy == null) {
            strategy = new SecondChanceStrategy() {
                
                @Override
                public boolean shouldTryForAnotherCard(BattleCard three, List<BattleCard> opponents) {
                    return false;
                }
            };
        }
        return strategy;
    }
    
    public void setSecondChanceStrategy(SecondChanceStrategy strat) {
        this.strategy = strat;
    }

    public boolean isParticipatingInBattle() {
        return participatingInBattle;
    }

    public void setParticipatingInBattle(boolean participatingInBattle) {
        this.participatingInBattle = participatingInBattle;
    }

    public int getNbrOfBattlesFought() {
        return nbrOfBattlesFought;
    }

    public void incrementNbrOfBattlesFought() {
        ++nbrOfBattlesFought;
    }

    public int getNbrOfBattlesWon() {
        return nbrOfBattlesWon;
    }

    public void incrementNbrOfBattlesWon() {
        ++nbrOfBattlesWon;
    }
    
    /**
     * This player swaps hands with given player.
     * 
     * @param other 
     */
    public void swapHands(Player other) {
        if (other == null) {
            throw new IllegalArgumentException("Other player cannot be null.");
        }
        if (this == other) return;
        Deck swap = new Deck(getHand().drawAll());
        getHand().put(other.getHand().drawAll());
        other.getHand().put(swap.drawAll());
    }
}
