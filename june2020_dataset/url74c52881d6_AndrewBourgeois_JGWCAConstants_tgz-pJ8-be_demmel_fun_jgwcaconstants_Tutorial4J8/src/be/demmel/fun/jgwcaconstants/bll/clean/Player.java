package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import java.io.IOException;

public class Player extends Agent {
    private final GWCAOperations gwcaOperations;

    public Player(GWCAOperations gwcaOperations) {
        // -2 because that's how GWCA knows it should use the player
        super(gwcaOperations, -2);
        this.gwcaOperations = gwcaOperations;
    }
    
    /**
     * @return the id of your current target
     */
    public int getCurrentTarget() throws IOException {
        return this.gwcaOperations.getCurrentTarget();
    }
    
    /**
     * @return the id of your own agent
     */
    public int getMyId() throws IOException {
        return this.gwcaOperations.getMyId();
    }
    
    /**
     * @return whether you're casting or not
     */
    public boolean isCasting() throws IOException {
        return this.gwcaOperations.isCasting();
    }
    
    /**
     * @return your max HP and your current HP
     */
    public int[] getMyMaxHp() throws IOException {
        return this.gwcaOperations.getMyMaxHp();
    }
    
    /**
     * @return your max energy and your current energy
     */
    public int[] getMyMaxEnergy() throws IOException {
        return this.gwcaOperations.getMyMaxEnergy();
    }
    
    /**
     * @return whether you're dead or not
     */
    public boolean isDead() throws IOException {
        return this.gwcaOperations.isDead();
    }
    
    /**
     * @param skillId
     * @return
     * @throws IOException 
     */
    public boolean playerHasBuff(int skillId) throws IOException {
        return this.gwcaOperations.playerHasBuff(skillId);
    }
    
    /**
     * @return id of your nearest agent.
     * @throws IOException 
     */
    public int getMyNearestAgent() throws IOException {
        return this.gwcaOperations.getMyNearestAgent();
    }
    
    /**
     * @param x
     * @param y
     * @throws IOException 
     */
    public void move(float x, float y) throws IOException {
       this.gwcaOperations.move(x, y);
    }
    
    /**
     * @param x
     * @param y
     * @param random
     * @throws IOException 
     */
    public void moveEx(float x, float y, int random) throws IOException {
        this.gwcaOperations.moveEx(x, y, random);
    }
    
    /**
     * Like pressing the "Trade" button next to a player's name.
     * @param agentId or -1 for the current target, -2 for yourself
     * @return a trade panel that can be used for the trade operations
     * @throws IOException 
     */
    public TradePanel tradePlayer(int agentId) throws IOException {
       this.gwcaOperations.tradePlayer(agentId);
       return new TradePanel(gwcaOperations);
    }
    
    /**
     * attacks the specified target with weapon.
     * @param targetId
     * @throws IOException 
     */
    public void attack(int targetId) throws IOException {
       this.gwcaOperations.attack(targetId);
    }
    
    /**
     * goes to specified player (for use in outposts/cities).
     * @param playerId
     * @throws IOException 
     */
    public void goPlayer(int playerId) throws IOException {
       this.gwcaOperations.goPlayer(playerId);
    }
    
    /**
     * goes to specified NPC.
     * @param npcId
     * @throws IOException 
     */
    public void goNpc(int npcId) throws IOException {
       this.gwcaOperations.goNpc(npcId);
    }
    
    /**
     * goes to specified signpost/chest (yellow name).
     * @param signpostOrChestId
     * @throws IOException 
     */
    public void goSignpost(int signpostOrChestId) throws IOException {
       this.gwcaOperations.goSignpost(signpostOrChestId);
    }
    
    /**
     * picks up the specified item
     * @param agentId
     * @throws IOException 
     */
    public void pickupItem(int agentId) throws IOException {
        this.gwcaOperations.pickupItem(agentId);
    }
    
    /**
     * opens the nearest chest if you're within 'close range'.
     * @throws IOException 
     */
    public void openChest() throws IOException {
        this.gwcaOperations.openChest();
    }
        
    /**
     * experimental function, requires knowledge about the NPC's dialog options. Can be found by using CA_GetLastDialogId
     * @param dialogOptionId
     * @throws IOException 
     */
    public void dialog(int dialogOptionId) throws IOException {
        this.gwcaOperations.dialog(dialogOptionId);
    }
    
    /**
     * @param agentId
     * @throws IOException 
     */
    public void changeTarget(int agentId) throws IOException {
        this.gwcaOperations.changeTarget(agentId);
    }
    
    /**
     *  targets the nearest foe
     * @throws IOException 
     */
    public void targetNearestFoe() throws IOException {
        this.gwcaOperations.targetNearestFoe();
    }
    
    /**
     *  targets the nearest ally
     * @throws IOException 
     */
    public void targetNearestAlly() throws IOException {
        this.gwcaOperations.targetNearestAlly();
    }
    
    /**
     * targets the nearest item/signpost/chest
     * @throws IOException 
     */
    public void targetNearestItem() throws IOException {
        this.gwcaOperations.targetNearestItem();
    }
    
    /**
     * targets the currently called target of the party
     * @throws IOException 
     */
    public void targetCalledTarget() throws IOException {
        this.gwcaOperations.targetCalledTarget();
    }
    
    /**
     * cancels your current action. Just like pressing ESC.
     * @throws IOException 
     */
    public void cancelAction() throws IOException {
        this.gwcaOperations.cancelAction();
    }
    
    /**
     * Goes to the specified target. Same as follow. Will not open NPC dialog or attack enemy.
     * @param agentId
     * @throws IOException 
     */
    public void goAgent(int agentId) throws IOException {
        this.gwcaOperations.goAgent(agentId);
    }
    
    /**
     * Functions like the GW Control: Targetting - Party Member: Next
     * @throws IOException 
     */
    public void targetNextPartyMember() throws IOException {
        this.gwcaOperations.targetNextPartyMember();
    }
    
    /**
     * Functions like the GW Control: Targetting - Next Foe
     * @throws IOException 
     */
    public void targetNextFoe() throws IOException {
        this.gwcaOperations.targetNextFoe();
    }
    
    /**
     * @param numberOfItems
     * @param maxDistance
     * @throws IOException 
     */
    public void pickupItems(int numberOfItems, float maxDistance) throws IOException {
        this.gwcaOperations.pickupItems(numberOfItems, maxDistance);
    }
    
    /**
     * @param x
     * @param y
     * @param random
     * @throws IOException 
     */
    public void moveToEx(float x, float y, int random) throws IOException {
        this.gwcaOperations.moveToEx(x, y, random);
    }
}
