package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import be.demmel.fun.jgwcaconstants.bll.Region;
import be.demmel.fun.jgwcaconstants.bll.SkillType;
import java.io.IOException;

public class RemainingCommands {
    //TODO: unclaimed items bag operations
    private final GWCAOperations gwcaOperations;

    public RemainingCommands(GWCAOperations gwcaOperations) {
        this.gwcaOperations = gwcaOperations;
    }
    
    /**
     * FOR DEBUGGING ONLY
     * @return the pointers of the agent and the target
     */
    public int[] getAgentAndTargetPointer() throws IOException {
        return this.gwcaOperations.getAgentAndTargetPointer();
    }
    
    /**
     * @return the build number of your client
     */
    public int getBuildNumber() throws IOException {
        return this.gwcaOperations.getBuildNumber();
    }
    
    /**
     * Changes the max zoom. The default value is 750. For observer mode, the default is 1400.
     * @param zoom: the new mex zoom
     * @throws IOException 
     */
    public void setMaxZoom(int zoom) throws IOException {
        this.gwcaOperations.setMaxZoom(zoom);
    }
    
    /**
     * @return the ID of the last dialog opened
     */
    public int getLastDialogId() throws IOException {
        return this.gwcaOperations.getLastDialogId();
    }
    
    /**
     * Disables/enables graphics rendering, lowering the CPU usage to 0 - 1% if disabled
     * @param disableRendering
     * @throws IOException 
     */
    public void setEngineHook(boolean disableRendering) throws IOException {
        this.gwcaOperations.setEngineHook(disableRendering);
    }
    
    /**
     * @return 0 when the map is loaded, 1 when ???? and 2 when it's loading
     */
    public int getMapLoading() throws IOException {
        return this.gwcaOperations.getMapLoading();
    }
    
    /**
     * @return the ID of the map
     */
    public int getMapId() throws IOException {
        return this.gwcaOperations.getMapId();
    }
    
    /**
     * @return your ping
     */
    public int getPing() throws IOException {
        return this.gwcaOperations.getPing();
    }
    
    /**
     * @return whether you're logged in or not
     */
    public boolean isLoggedIn() throws IOException {
        return this.gwcaOperations.isLoggedIn();
    }
    
    
    /**
     * @return your connection status.  2 = logged into character, 1/0 = not logged into character.
     */
    public int getConnection() throws IOException {
        return this.gwcaOperations.getConnection();
    }
    
    /**
     * @returninteger of the match status. 
     * For RA, when in a match, this value is 1 before it starts, 2 when started, and 3 when the match is over (check if you're dead at this point to see if you won the match). 
     * Values may vary in other PvP types!
     */
    public int getMatchStatus() throws IOException {
        return this.gwcaOperations.getMatchStatus();
    }
    
    /**
     * @return the size of the agent array divided by 4 (because the agent array consists of Agent pointers (pointer = 4 bytes)).
     * @throws IOException 
     */
    public int getMaxId() throws IOException {
        return this.gwcaOperations.getMaxId();
    }
    
    /**
     * @param playerNumber player/model number
     * @return  the first (id-wise) agent with that number.
     * @throws IOException 
     */
    public int getFirstAgentByPlayerNumber(int playerNumber) throws IOException {
        return this.gwcaOperations.getFirstAgentByPlayerNumber(playerNumber);
    }
    
    /**
     * @param playerNumber
     * @return Same as CA_GetFirstAgentByPlayerNumberexcept it returns the nearest agent to you.
     * @throws IOException 
     */
    public int getNearestAgentByPlayerNumber(int playerNumber) throws IOException {
        return this.gwcaOperations.getNearestAgentByPlayerNumber(playerNumber);
    }
    
    /**
     * @param x
     * @param y
     * @return  the nearest agent to the specified coords.
     * @throws IOException 
     */
    public int getNearestAgentToCoords(float x, float y) throws IOException {
        return this.gwcaOperations.getNearestAgentToCoords(x, y);
    }
    
    /**
     * @param x
     * @param y
     * @return the nearest NPC to the specified coords.
     * @throws IOException 
     */
    public int getNearestNpcToCoords(float x, float y) throws IOException {
        return this.gwcaOperations.getNearestNpcToCoords(x, y);
    }
    
    /**
     * @param playerNumber
     * @return  the number of alive agents within compass range with specified id.
     * @throws IOException 
     */
    public int getNumberOfAgentsByPlayerNumber(int playerNumber) throws IOException {
        return this.gwcaOperations.getNumberOfAgentsByPlayerNumber(playerNumber);
    }
    
    /**
     * @return  the number of alive and visible enemy agents in total.
     * @throws IOException 
     */
    public int getNumberOfAliveEnemyAgents() throws IOException {
        return this.gwcaOperations.getNumberOfAliveEnemyAgents();
    }
    
    /**
     * Sets up the CA_GetNearestPlayerNumberToCoords command. But please use the wrapper function GetNearestPlayerNumberToCoords() in GWCAConstants.au3 instead!
     * @param playerNumber
     * @return
     * @throws IOException 
     */
    public void prepareNearestPlayerNumberToCoords(int playerNumber) throws IOException {
        this.gwcaOperations.prepareNearestPlayerNumberToCoords(playerNumber);
    }
    
    /**
     * Requires previous call to CA_PrepareNearestPlayerNumberToCoords. But please use the wrapper function GetNearestPlayerNumberToCoords() in GWCAConstants.au3 instead!
     * @param x
     * @param y
     * @return
     * @throws IOException 
     */
    public int getNearestPlayerNumberToCoords(float x, float y) throws IOException {
        return this.gwcaOperations.getNearestPlayerNumberToCoords(x, y);
    }
    
    /**
     * @param playerNumber
     * @param x
     * @param y
     * @return
     * @throws IOException 
     */
    public int getNearestPlayerNumberToCoords(int playerNumber, float x, float y) throws IOException {
        this.prepareNearestPlayerNumberToCoords(playerNumber);
        return this.getNearestPlayerNumberToCoords(x, y);
    }
    
    /**
     * @param playerNumber
     * @param teamId
     * @return the first agent (iteration-wise) with the specified player/model number and team id making you able to easily identify your Ghostly Hero etc. in PvP.
     * @throws IOException 
     */
    public int getFirstAgentByPlayerNumberByTeam(int playerNumber, int teamId) throws IOException {
        return this.gwcaOperations.getFirstAgentByPlayerNumberByTeam(playerNumber, teamId);
    }
    
    /**
     * @param x
     * @param y
     * @return the nearest alive enemy to the specified coordinates and the distance to the enemy.
     * @throws IOException 
     */
    public float[] getNearestAliveEnemyToCoords(float x, float y) throws IOException {
        return this.gwcaOperations.getNearestAliveEnemyToCoords(x, y);
    }
    
    /**
     * @param playerNumber
     * @throws IOException 
     */
    public void prepareNearestPlayerNumberToCoordsByPlayerNumber(int playerNumber) throws IOException {
        this.gwcaOperations.prepareNearestPlayerNumberToCoordsByPlayerNumber(playerNumber);
    }
    
    /**
     * @param playerNumber
     * @param x
     * @param y
     * @return
     * @throws IOException 
     */
    public float[] getNearestAliveEnemyToCoordsByPlayerNumber(int playerNumber, float x, float y) throws IOException {
        return this.gwcaOperations.getNearestAliveEnemyToCoordsByPlayerNumber(playerNumber, x, y);
    }
    
    /**
     * @param x
     * @param y
     * @return
     * @throws IOException 
     */
    public float[] getNearestAliveEnemyToCoordsByPlayerNumber(float x, float y) throws IOException {
        return this.gwcaOperations.getNearestAliveEnemyToCoordsByPlayerNumber(x, y);
    }
    
    /**
     * @param playerNumber
     * @return the nearest alive agent with specified player/model number and your distance to it.
     * @throws IOException 
     */
    public float[] getNearestAliveAgentByPlayerNumber(int playerNumber) throws IOException {
        return this.gwcaOperations.getNearestAliveAgentByPlayerNumber(playerNumber);
    }
    
    /**
     * Accepts all unclaimed items (from the unclaimed items window that may appear). Doesn't check if there isn't room for all the items, this simply accepts them!
     * @throws IOException 
     */
    public void acceptAllItems() throws IOException {
        this.gwcaOperations.acceptAllItems();
    }
    
    /**
     * @param itemModelId
     * @return the agent id and the distance between you and the item as a float.
     * @throws IOException 
     */
    public float[] getNearestItemByModelId(int itemModelId) throws IOException {
        return this.gwcaOperations.getNearestItemByModelId(itemModelId);
    }
    
    /**
     * @param weaponSlot
     * @throws IOException 
     */
    public void changeWeaponSet(int weaponSlot) throws IOException {
       this.gwcaOperations.changeWeaponSet(weaponSlot);
    }
    
    /**
     * zones to the outpost/city specified and if district number is provided also attempts to enter that district. Always uses current region and language
     * @param zoneId
     * @param districtNumber
     * @throws IOException 
     */
    public void zoneMap(int zoneId, int districtNumber) throws IOException {
       this.gwcaOperations.zoneMap(zoneId, districtNumber);
    }
    
    /**
     * @param zoneId
     * @param districtNumber
     * @throws IOException 
     */
    public void travelTo(int zoneId, int districtNumber) throws IOException {
        this.gwcaOperations.travelTo(zoneId, districtNumber);
    }
    
    /**
     * enters the challenge if you're in a Challenge Mission outpost.
     * @throws IOException 
     */
    public void enterChallenge() throws IOException {
        this.gwcaOperations.enterChallenge();
    }
    
    /**
     * used after CA_EnterChallenge, it allows you to skip past the foreign character window.
     * @throws IOException 
     */
    public void enterChallengeForeign() throws IOException {
        this.gwcaOperations.enterChallengeForeign();
    }
    
    /**
     * @param region
     * @param language experiment around with this!
     * @throws IOException 
     */
    public void changeDistrict(Region region, byte language) throws IOException {        
        this.gwcaOperations.changeDistrict(region, language);
    }
    
    /**
     * @throws IOException 
     */
    public void resign() throws IOException {
        this.gwcaOperations.resign();
    }
        
    /**
     * Abuses the fact that the client actually knows where you are, even though it may not display the correct position.
     * @param agentId or -1 for the current target, -2 for yourself
     * @throws IOException 
     */
    public void updateAgentPosition(int agentId) throws IOException {
        this.gwcaOperations.updateAgentPosition(agentId);
    }
    
    /**
     * "Presses" the Return To Outpost button after resigning.
     * @throws IOException 
     */
    public void returnToOutpost() throws IOException {
        this.gwcaOperations.returnToOutpost();
    }
    
    /**
     * Donates 5000 faction towards your faction. Not recently tested, but it should require open dialog with Faction Npc.
     * @throws IOException 
     */
    public void donateKurzickFaction() throws IOException {
        this.gwcaOperations.donateKurzickFaction();
    }
    
    /**
     * Donates 5000 faction towards your faction. Not recently tested, but it should require open dialog with Faction Npc.
     * @throws IOException 
     */
    public void donateLuxonFaction() throws IOException {
        this.gwcaOperations.donateLuxonFaction();
    }
    
    /**
     * Skips the current cinematic/cutscene.
     * @throws IOException 
     */
    // Broken, also not working in AutoIt
    public void skipCinematic() throws IOException {
        this.gwcaOperations.skipCinematic();
    }
    
    /**
     * 
     * @param heroIndex (from 0 = self to 3 = Hero3)
     * @param buffIndex index of the buff in order it was casted.
     * @throws IOException 
     */
    public void dismissBuff(int heroIndex, int buffIndex) throws IOException {
       this.gwcaOperations.dismissBuff(heroIndex, buffIndex);
    }
    
    /**
     * Internal command. Please use ZoneMapEx() function instead of this.
     * @param mapId
     * @throws IOException 
     */
    public void prepareZoneMapEx(int mapId) throws IOException {
        this.gwcaOperations.prepareZoneMapEx(mapId);
    }
    
    /**
     * Internal command. Requires previous call to CA_PrepareZoneMapEx, so please use ZoneMapEx() function instead of this.
     * @param region
     * @param language
     * @throws IOException 
     */
    public void zoneMapEx(Region region, int language) throws IOException {
        this.gwcaOperations.zoneMapEx(region, language);
    }
    
    /**
     * @throws IOException 
     */
    public void travelGH() throws IOException {
        this.gwcaOperations.travelGH();
    }
    
    /**
     * @throws IOException 
     */
    public void leaveGH() throws IOException {
        this.gwcaOperations.leaveGH();
    }
    
    /**
     * Inits the CA_MapIsLoaded command.
     * @throws IOException 
     */
    public void initMapLoad() throws IOException {
        this.gwcaOperations.initMapLoad();
    }
    
    /**
     * Use CA_InitMapLoad before !!!
     * @return
     * @throws IOException 
     */
    public boolean isMapLoaded() throws IOException {
        return this.gwcaOperations.isMapLoaded();
    }
    
    /**
     * @param mapOverlayId  (get this with CA_GetNearestMapOverlayToCoords)
     * @return  the x,y coords of the specified map overlay.
     * @throws IOException 
     */
    public float[] getMapOverlayCoords(int mapOverlayId) throws IOException {
        return this.gwcaOperations.getMapOverlayCoords(mapOverlayId);
    }
    
    /**
     * @param mapOverlayId
     * @return  the option variable (usually color) and the icon model id of the specified map overlay.
     * @throws IOException 
     */
    public int[] getMapOverlayInfo(int mapOverlayId) throws IOException {
        return this.gwcaOperations.getMapOverlayInfo(mapOverlayId);
    }
    
    /**
     * @param x
     * @param y
     * @return the nearest Map Overlay to the specified coords.
     * @throws IOException 
     */
    public int getNearestMapOverlayToCoords(float x, float y) throws IOException {
        return this.gwcaOperations.getNearestMapOverlayToCoords(x, y);
    }
    
    /**
     * For use with CA_GetPartyInfo. Set the team size to be evaluated using the CA_GetPartyInfo command.
     * @param teamSize
     * @throws IOException 
     */
    public void setTeamSize(int teamSize) throws IOException {
       this.gwcaOperations.setTeamSize(teamSize);
    }
    
    /**
     * @return your current region and language.
     * @throws IOException 
     */
    public int[] getRegionAndLanguage() throws IOException {
        return this.gwcaOperations.getRegionAndLanguage();
    }
    
    /**
     * Used in skill recharges, effects durations and other things.
     * @return the current internal time stamp.
     * @throws IOException 
     */
    public int getTimeStamp() throws IOException {
        return this.gwcaOperations.getTimeStamp();
    }
    
    /**
     * Cancels the specified self-targetting or in other way targetting you maintained enchantment.
     * @param skillId
     * @throws IOException 
     */
    public void cancelMaintainedEnchantment(int skillId) throws IOException {
       this.gwcaOperations.cancelMaintainedEnchantment(skillId);
    }
    
    /**
     * @param skillId
     * @return the type of the specified skill
     * @throws IOException 
     */
    public SkillType getSkillType(int skillId) throws IOException {
        return this.gwcaOperations.getSkillType(skillId);
    }
    
    /**
     * @param mapId
     * @param region
     * @param language
     * @throws IOException 
     */
    //TODO: enum for language
    public void zoneMapEx(int mapId, Region region, int language) throws IOException {
        this.gwcaOperations.zoneMapEx(mapId, region, language);
    }
    
    /**
     * @param extra
     * @throws IOException
     * @throws InterruptedException 
     */
    public void pingSleep(long extra) throws IOException {
        this.gwcaOperations.pingSleep(extra);
    }
}
