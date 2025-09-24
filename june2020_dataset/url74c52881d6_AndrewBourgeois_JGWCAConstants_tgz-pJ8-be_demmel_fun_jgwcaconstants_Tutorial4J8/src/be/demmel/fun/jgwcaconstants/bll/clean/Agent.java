package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import be.demmel.fun.jgwcaconstants.bll.WeaponType;
import java.io.IOException;

public class Agent {
    private final GWCAOperations gwcaOperations;
    private int id;

    public Agent(GWCAOperations gwcaOperations, int id) {
        this.gwcaOperations = gwcaOperations;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @param equipmentIndex ??
     * @return the model ID of the equipment
     * @throws IOException 
     */
    public int[] getEquipmentModelId(int equipmentIndex) throws IOException {
        return this.gwcaOperations.getEquipmentModelId(id, equipmentIndex);
    }
    
    /**
     * 
     * @param equipmentIndex ??
     * @return the dye ID and shinyness of the equipment piece
     * @throws IOException 
     */
    public int[] getEquipmentDyeInfo(int equipmentIndex) throws IOException {        
        return this.gwcaOperations.getEquipmentDyeInfo(id, equipmentIndex);
    }
    
    /**
     * @return whether the agent with the given ID exists
     * @throws IOException 
     */
    public boolean getAgentExists() throws IOException {
        return this.gwcaOperations.getAgentExists(id);
    }
    
    /**
     * @return  integer of primary and integer of secondary profession for the agent with the given ID
     * @throws IOException 
     */
    public int[] getProfessions() throws IOException {
        return this.gwcaOperations.getProfessions(id);
    }
    
    /**
     * @return  integer of player number or, if the agent is an NPC/monster, the 'Model number'.
     * @throws IOException 
     */
    public int getPlayerNumber() throws IOException {
        return this.gwcaOperations.getPlayerNumber(id);
    }
    
    /**
     * @return float between 0 and 1 with the health in percent.
     * @throws IOException 
     */
    public float getHp() throws IOException {
        return this.gwcaOperations.getHp(id);
    }
    
    /**
     * @return float of rotation in degrees.
     * @throws IOException 
     */
    public float getRotation() throws IOException {
        return this.gwcaOperations.getRotation(id);
    }
    
    /**
     * @return  integer of current skill being used by agent.
     * @throws IOException 
     */
    public int getSkill() throws IOException {
        return this.gwcaOperations.getSkill(id);
    }
    
    /**
     * @return  float of X and float of Y coordinate, packed of course.
     * @throws IOException 
     */
    public float[] getCoords() throws IOException {
        return this.gwcaOperations.getCoords(id);
    }
    
    /**
     * @return
     * @throws IOException 
     */
    public float[] getWeaponSpeeds() throws IOException {
        return this.gwcaOperations.getWeaponSpeeds(id);
    }
    
    /**
     * @return integer of team id. 1 = blue, 2 = red, 3 = yellow, ...
     * @throws IOException 
     */
    public int getTeamId() throws IOException {
        return this.gwcaOperations.getTeamId(id);
    }
    
    /**
     * @return  integer of how many pips the target has.
     * @throws IOException 
     */
    public int getHpPips() throws IOException {
        return this.gwcaOperations.getHpPips(id);
    }
    
    /**
     * @return  If 0, target is not hexed. If 1, target is hexed without degen and if 2, target is hexed with degen.
     * @throws IOException 
     */
    public int getHex() throws IOException {
        return this.gwcaOperations.getHex(id);
    }
    
    /**
     * @return The model animation number depends on profession and sex, but can be used to determine emotes, etc.
     * @throws IOException 
     */
    public int getModelAnimation() throws IOException {
        return this.gwcaOperations.getModelAnimation(id);
    }
    
    /**
     * @return 0xDB = players/npc's/monsters (living stuff), 0x200 = signposts/chests/solid objects, 0x400 = items that can be picked up.
     * @throws IOException 
     */
    public int getType() throws IOException {
        return this.gwcaOperations.getType(id);
    }
    
    /**
     * @return level of the agent
     * @throws IOException 
     */
    public int getLevel() throws IOException {
        return this.gwcaOperations.getLevel(id);
    }
    
    /**
     * @return the distance
     * @throws IOException 
     */
    public float getMyDistanceToAgent() throws IOException {
        return this.gwcaOperations.getMyDistanceToAgent(id);
    }
    
    /**
     * @return  id of the nearest agent to the specified agent.
     * @throws IOException 
     */
    public int getNearestAgent() throws IOException {
        return this.gwcaOperations.getNearestAgentToAgent(id);
    }
    
    /**
     * @param otherAgentId
     * @return  the distance between the two agents as a float.
     * @throws IOException 
     */
    public float getDistanceFromAgentToAgent(int otherAgentId) throws IOException {
        return this.gwcaOperations.getDistanceFromAgentToAgent(id, otherAgentId);
    }
    
    /**
     * @return same as CA_GetNearestAgentToAgent, but also returns float with distance between them.
     * @throws IOException 
     */
    public float[] getNearestAgentToAgentEx() throws IOException {
        return this.gwcaOperations.getNearestAgentToAgentEx(id);
    }
    
    /**
     * @return whether the agent is attacking
     * @throws IOException 
     */
    public boolean isAttacking() throws IOException {
        return this.gwcaOperations.isAttacking(id);
    }
    
    /**
     * @return whether the agent is knocked down
     * @throws IOException 
     */
    public boolean isKnockedDown() throws IOException {
        return this.gwcaOperations.isKnockedDown(id);
    }
    
    /**
     * @return whether the agent is moving
     * @throws IOException 
     */
    public boolean isMoving() throws IOException {
        return this.gwcaOperations.isMoving(id);
    }
    
    /**
     * @return whether the agent is dead
     * @throws IOException 
     */
    public boolean isDead() throws IOException {
        return this.gwcaOperations.isDead(id);
    }
    
    /**
     * @return  the allegiance of agent. 0x100 = ally/non-attackable, 0x300 = enemy, 0x400 = spirit/pet, 0x500 = minion, 0x600 = npc/minipet.
     * @throws IOException 
     */
    public int getAllegiance() throws IOException {
        return this.gwcaOperations.getAllegiance(id);
    }
    
    /**
     * @return  id of nearest enemy to agent by team id (so, only for PvP) and float of distance.
     * @throws IOException 
     */
    public float[] getNearestEnemyToAgentEx() throws IOException {
        return this.gwcaOperations.getNearestEnemyToAgentEx(id);
    }
    
    /**
     * @return agent id of item and distance as float.
     * @throws IOException 
     */
    public float[] getNearestItemToAgentEx() throws IOException {
        return this.gwcaOperations.getNearestItemToAgentEx(id);
    }
    
    /**
     * @return  current speed of agent (how far the agent moves per second in GW units).
     * @throws IOException 
     */
    public float getSpeed() throws IOException {
        return this.gwcaOperations.getSpeed(id);
    }
    
    /**
     * @return  id of enemy and distance as float. Only works for yourself or your allies.
     * @throws IOException 
     */
    public float[] getNearestEnemyToAgentByAllegiance() throws IOException {
        return this.gwcaOperations.getNearestEnemyToAgentByAllegiance(id);
    }
    
    /**
     * @return  id of enemy and distance as float. Only works for yourself or your allies.s
     * @throws IOException 
     */
    public float[] getNearestAliveEnemyToAgent() throws IOException {
        return this.gwcaOperations.getNearestAliveEnemyToAgent(id);
    }
    
    /**
     * @return  weapon type. 1=bow, 2=axe, 3=hammer, 4=daggers, 5=scythe, 6=spear, 7=sword, 10=wand.
     * @throws IOException 
     */
    public WeaponType getWeaponType() throws IOException {
        return this.gwcaOperations.getWeaponType(id);
    }
    
    /**
     * @return id of the nearest signpost/chest.
     * @throws IOException 
     */
    public int getNearestSignpostToAgent() throws IOException {
        return this.gwcaOperations.getNearestSignpostToAgent(id);
    }
    
    /**
     * @return id of the nearest NPC.
     * @throws IOException 
     */
    public int getNearestNpcToAgentByAllegiance() throws IOException {
        return this.gwcaOperations.getNearestNpcToAgentByAllegiance(id);
    }
    
    /**
     * @return the unique login number in instance of player. If used on non-player it will return 0.
     * @throws IOException 
     */
    public int getLoginNumber() throws IOException {
        return this.gwcaOperations.getLoginNumber(id);
    }
    
    /**
     * @return  the currently logged target of the specified agent.
     * @throws IOException 
     */
    public int getTarget() throws IOException {
        return this.gwcaOperations.getTarget(id);
    }
    
    /**
     * @return the number of foes to the agent which are currently targetting it.
     * @throws IOException 
     */
    public int getAgentDanger() throws IOException {
        return this.gwcaOperations.getAgentDanger(id);
    }
    
    /**
     * @return  the bitmap integer used to determine things such as boss (0xC00), spirit (0x40000) and player (0x400000). Remember to use BitAND() like with CA_GetEffects.
     * @throws IOException 
     */
    public int getTypeMap() throws IOException {
        return this.gwcaOperations.getTypeMap(id);
    }
    
    /**
     * @return the item id('s) of the agent's weapon(s). Can be used to acquire a bit more detailed info about weapons than CA_GetWeaponType.
     * @throws IOException 
     */
    public int[] getAgentWeapons() throws IOException {
        return this.gwcaOperations.getAgentWeapons(id);
    }
    
    /**
     * @return the agent id of the next valid agent. For looping through all agents, use this command with the returned agent id again.
     * @throws IOException 
     */
    public int getNextAgent() throws IOException {
        return this.gwcaOperations.getNextAgent(id);
    }
    
    /**
     * @return the agent id of the next valid ally. For looping through all allies, use this command with the returned agent id again.
     * @throws IOException 
     */
    public int getNextAlly() throws IOException {
        return this.gwcaOperations.getNextAlly(id);
    }
    
    /**
     * @return the agent id of the next valid foe. For looping through all foes, use this command with the returned agent id again.
     * @throws IOException 
     */
    public int getNextFoe() throws IOException {
        return this.gwcaOperations.getNextFoe(id);
    }
    
    /**
     * @return  the extra type of the agent as integer. This number can be used for distinguishing between chests and signposts for example.
     * @throws IOException 
     */
    public int getExtraType() throws IOException {
        return this.gwcaOperations.getExtraType(id);
    }
    
    /**
     * @param radius
     * @return the number of foes counted
     * @throws IOException 
     */
    public int getNumberOfFoesInRangeOfAgent(float radius) throws IOException {
        return this.gwcaOperations.getNumberOfFoesInRangeOfAgent(id, radius);
    }
    
    /**
     * @param radius
     * @return the number of allies counted
     * @throws IOException 
     */
    public int getNumberOfAlliesInRangeOfAgent(float radius) throws IOException {
        return this.gwcaOperations.getNumberOfAlliesInRangeOfAgent(id, radius);
    }
    
    /**
     * @param radius
     * @return the number of items counted
     * @throws IOException 
     */
    public int getNumberOfItemsInRangeOfAgent(float radius) throws IOException {
        return this.gwcaOperations.getNumberOfItemsInRangeOfAgent(id, radius);
    }
    
    /**
     * @return the agent id of the next valid alive foe. For looping through all alive foes, use this command with the returned agent id again.
     * @throws IOException 
     */
    public int getNextAliveFoe() throws IOException {
        return this.gwcaOperations.getNextAliveFoe(id);
    }
    
    /**
     * @return item id and model id of the specified agent, if it's an item.
     * @throws IOException 
     */
    public int[] getItemIdByAgent() throws IOException {
        return this.gwcaOperations.getItemIdByAgent(id);
    }
    
    /**
     * @return item rarity and quantity of the specified agent, if it's an item.
     * @throws IOException 
     */
    public int[] getItemInfoByAgent() throws IOException {
        return this.gwcaOperations.getItemInfoByAgent(id);
    }
    
    /**
     * @return the agent id of the next item. For looping through all items, use this command with the returned agent id as parameter after taking care of that item.
     * @throws IOException 
     */
    public int getNextItem() throws IOException {
        return this.gwcaOperations.getNextItem(id);
    }
    
    /**
     * @return  the extra id of the item - used to determine dye color etc.
     * @throws IOException 
     */
    public int getItemExtraIdByAgent() throws IOException {
        return this.gwcaOperations.getItemExtraIdByAgent(id);
    }
    
    /**
     * @return
     * @throws IOException 
     */
    public int getItemReqByAgent() throws IOException {
        return this.gwcaOperations.getItemReqByAgent(id);
    }
    
    /**
     * Only works for offhands/shields!
     * @return  the damage mod of the item.
     * @throws IOException 
     */
    public int getItemDmgModByAgent() throws IOException {
        return this.gwcaOperations.getItemDmgModByAgent(id);
    }
    
    /**
     * @return
     * @throws IOException 
     */
    public int[] getItemLastModifierByAgent() throws IOException {
        return this.gwcaOperations.getItemLastModifierByAgent(id);
    }
}
