package be.demmel.fun.jgwcaconstants.bll;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is like the AutoIt GWCAConstants, except that every operation has its function, plus:
 * - the API enforces you to send the right parameters with the right types
 * - the API encapsulated some fixed list of values (like Rarity, Dye, Hero,...) into enums to enforce a correct value
 * - the API gives you the correct return
 * - you get to see the documentation straight into the IDE
 */
public class GWCAOperations {

    private static final Logger LOG = LoggerFactory.getLogger(GWCAOperations.class);
    private static final int ZERO = 0;
    private GWCAConnection gwcaConnection;

    public GWCAOperations(GWCAConnection gwcaConnection) {
        this.gwcaConnection = gwcaConnection;
    }

    private byte[] floatToByteArray(float f) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putFloat(f);
        return bb.array();
    }

    private int byteArrayToInt(byte[] b) {
        ByteBuffer bb = ByteBuffer.wrap(b);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getInt();
    }

    private byte[] intToByteArray(int i) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(i);
        return bb.array();
    }

    private float computeDistanceEx(float x1, float y1, float x2, float y2) {
        double y = Math.pow(y2 - y1, 2);
        double x = Math.pow(x2 - x1, 2);

        return (float) Math.sqrt(y + x);
    }

    private void sendPacket(GWCAOperation operation, int wParam, int lParam) throws IOException {
        GWCAPacket gwcaPacket = new GWCAPacket(operation, intToByteArray(wParam), intToByteArray(lParam));
        this.gwcaConnection.sendPacket(gwcaPacket);
    }

    // for the operations that only take 1 param
    private void sendPacket(GWCAOperation operation, int wParam) throws IOException {
        sendPacket(operation, wParam, ZERO);
    }

    // for the operations that take no params
    private void sendPacket(GWCAOperation operation) throws IOException {
        this.sendPacket(operation, ZERO, ZERO);
    }

    private GWCAPacket sendAndReceivePacket(GWCAOperation operation, int wParam, int lParam) throws IOException {
        this.sendPacket(operation, wParam, lParam);
        return this.gwcaConnection.receivePacket();
    }

    // for the operations that only take 1 param
    private GWCAPacket sendAndReceivePacket(GWCAOperation operation, int wParam) throws IOException {
        return sendAndReceivePacket(operation, wParam, ZERO);
    }

    // for the operations that take no params
    private GWCAPacket sendAndReceivePacket(GWCAOperation operation) throws IOException {
        return sendAndReceivePacket(operation, ZERO, ZERO);
    }

    // START float setion
    private GWCAPacket sendAndReceivePacket(GWCAOperation operation, float wParam, float lParam) throws IOException {
        this.sendPacket(operation, wParam, lParam);
        return this.gwcaConnection.receivePacket();
    }

    // for the operations that only take 1 param
    private GWCAPacket sendAndReceivePacket(GWCAOperation operation, float wParam) throws IOException {
        return sendAndReceivePacket(operation, wParam, ZERO);
    }

    private void sendPacket(GWCAOperation operation, float wParam, float lParam) throws IOException {
        GWCAPacket gwcaPacket = new GWCAPacket(operation, floatToByteArray(wParam), floatToByteArray(lParam));
        this.gwcaConnection.sendPacket(gwcaPacket);
    }

    // for the operations that only take 1 param
    private void sendPacket(GWCAOperation operation, float wParam) throws IOException {
        sendPacket(operation, wParam, ZERO);
    }
    // END float section

    // Start of the GWCA operations....
    /**
     * @return the id of your current target
     */
    public int getCurrentTarget() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_CURRENT_TARGET);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return the id of your own agent
     */
    public int getMyId() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_MY_ID);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return whether you're casting or not
     */
    public boolean isCasting() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.CASTING);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @param skillSlot: the SkillSlot to check
     * @return: the timestamp at which the skill will be recharged, or 0 if the skill is recharged
     * @throws IOException 
     */
    public int getSkillRecharge(SkillBarSkillSlot skillSlot) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.SKILL_RECHARGE, skillSlot.getValue());
        //TODO: verify how this timestamp is formatted
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param skillSlot: the SkillSlot to check
     * @return: the amount of points the given skill slot has
     * @throws IOException 
     */
    public int getSkillAdrenaline(SkillBarSkillSlot skillSlot) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.SKILL_ADRENALINE, skillSlot.getValue());
        //TODO: verify how this timestamp is formatted
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * FOR DEBUGGING ONLY
     * @return the pointers of the agent and the target
     */
    public int[] getAgentAndTargetPointer() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_AGENT_AND_TARGET_POINTERS);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * OBSOLETE
     * @param skillSlot: the SkillSlot to check
     * @return: the skill ID slotted in the given skill slot
     * @throws IOException 
     */
    public int getSkillbarSkillId(SkillBarSkillSlot skillSlot) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_SKILLBAR_SKILL_ID, skillSlot.getValue());
        //TODO: verify how this timestamp is formatted
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return your max HP and your current HP
     */
    public int[] getMyMaxHp() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_MY_MAX_HP);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @return your max energy and your current energy
     */
    public int[] getMyMaxEnergy() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_MY_MAX_ENERGY);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @return the build number of your client
     */
    public int getBuildNumber() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_BUILD_NUMBER);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * Changes the max zoom. The default value is 750. For observer mode, the default is 1400.
     * @param zoom: the new mex zoom
     * @throws IOException 
     */
    public void setMaxZoom(int zoom) throws IOException {
        sendPacket(GWCAOperation.CHANGE_MAX_ZOOM, zoom);
    }

    /**
     * @return the ID of the last dialog opened
     */
    public int getLastDialogId() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_LAST_DIALOG_ID);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * Disables/enables graphics rendering, lowering the CPU usage to 0 - 1% if disabled
     * @param disableRendering
     * @throws IOException 
     */
    public void setEngineHook(boolean disableRendering) throws IOException {
        sendPacket(GWCAOperation.SET_ENGINE_HOOK, disableRendering == true ? 1 : 0);
    }

    /**
     * 
     * @param heroSlot the slot of the hero to check
     * @return whether the hero at the given slot is casting
     * @throws IOException 
     */
    public boolean isHeroCasting(HeroSlot heroSlot) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_HERO_CASTING, heroSlot.getValue());
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @param skillSlot: the SkillSlot to check
     * @param heroSlot : the HeroSlot to check
     * @return: the timestamp at which the skill will be recharged, or 0 if the skill is recharged
     * @throws IOException 
     */
    public int getHeroSkillRecharge(HeroSlot heroSlot, SkillBarSkillSlot skillSlot) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_HERO_SKILL_RECHARGE, heroSlot.getValue(), skillSlot.getValue());
        //TODO: verify how this timestamp is formatted
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param skillSlot: the SkillSlot to check
     * @param heroSlot : the HeroSlot to check
     * @return: the timestamp at which the skill will be recharged, or 0 if the skill is recharged
     * @throws IOException 
     */
    public int getHeroSkillAdrenaline(HeroSlot heroSlot, SkillBarSkillSlot skillSlot) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_HERO_SKILL_ADRENALINE, heroSlot.getValue(), skillSlot.getValue());
        //TODO: verify how this timestamp is formatted
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param skillSlot: the SkillSlot to check
     * @param heroSlot : the HeroSlot to check
     * @return: the hero's skill ID at the given slots
     * @throws IOException 
     */
    public int getHeroSkillId(HeroSlot heroSlot, SkillBarSkillSlot skillSlot) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_HERO_SKILL_ID, heroSlot.getValue(), skillSlot.getValue());
        //TODO: verify how this timestamp is formatted
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param skillSlot: the SkillSlot to check
     * @param heroSlot : the HeroSlot to check
     * @return: the hero's agent ID at the given slots
     * @throws IOException 
     */
    public int getHeroAgentId(HeroSlot heroSlot, SkillBarSkillSlot skillSlot) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_HERO_AGENT_ID, heroSlot.getValue(), skillSlot.getValue());
        //TODO: verify how this timestamp is formatted
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return 0 when the map is loaded, 1 when ???? and 2 when it's loading
     */
    public int getMapLoading() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_MAP_LOADING);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return the ID of the map
     */
    public int getMapId() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_MAP_ID);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return your ping
     */
    public int getPing() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_PING);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return whether you're logged in or not
     */
    public boolean isLoggedIn() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_LOGGED_IN);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @return whether you're dead or not
     */
    public boolean isDead() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_DEAD);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @return your character's Balthazar faction
     */
    public int getBalthazarFaction() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_BALTHAZAR_FACTION);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return your character's Kurzick faction
     */
    public int getKurzickFaction() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_KURZICK_FACTION);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return your character's Luxon faction
     */
    public int getLuxonFaction() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_LUXON_FACTION);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many treasure title points that you have
     */
    public int getTitleTreasure() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_TREASURE);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many lucky title points that you have
     */
    public int getTitleLucky() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_LUCKY);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many unlucky title points that you have
     */
    public int getTitleUnlucky() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_UNLUCKY);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many wisdom title points that you have
     */
    public int getTitleWisdom() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_UNLUCKY);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many gamer title points that you have
     */
    public int getTitleGamer() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_GAMER);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many experience points that you have
     */
    public int getExperience() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_EXPERIENCE);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return your connection status.  2 = logged into character, 1/0 = not logged into character.
     */
    public int getConnection() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_CONNECTION);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @returninteger of the match status. 
     * For RA, when in a match, this value is 1 before it starts, 2 when started, and 3 when the match is over (check if you're dead at this point to see if you won the match). 
     * Values may vary in other PvP types!
     */
    public int getMatchStatus() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_MATCH_STATUS);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many sunspear title points that you have
     */
    public int getTitleSunspear() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_SUNSPEAR);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many lightbringer title points that you have
     */
    public int getTitleLightbringer() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_LIGHTBRINGER);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many vanguard title points that you have
     */
    public int getTitleVanguard() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_VANGUARD);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many norn title points that you have
     */
    public int getTitleNorn() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_NORN);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many asura title points that you have
     */
    public int getTitleAsura() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_ASURA);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many deldrimor title points that you have
     */
    public int getTitleDeldrimor() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_DELDRIMOR);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many north mastery title points that you have
     */
    public int getTitleNorthMastery() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_NORTH_MASTERY);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many drunkard title points that you have
     */
    public int getTitleDrunkard() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_DRUNKARD);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many sweet title points that you have
     */
    public int getTitleSweet() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_SWEET);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many party title points that you have
     */
    public int getTitleParty() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_PARTY);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return how many commander title points that you have
     */
    public int getTitleCommander() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TITLE_COMMANDER);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * 
     * @param agentId or -1 for the current target, -2 for yourself
     * @param equipmentIndex ??
     * @return the model ID of the equipment
     * @throws IOException 
     */
    public int[] getEquipmentModelId(int agentId, int equipmentIndex) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_EQUIPMENT_MODEL_ID, agentId, equipmentIndex);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * 
     * @param agentId or -1 for the current target, -2 for yourself
     * @param equipmentIndex ??
     * @return the dye ID and shinyness of the equipment piece
     * @throws IOException 
     */
    public int[] getEquipmentDyeInfo(int agentId, int equipmentIndex) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_EQUIPMENT_DYE_INFO, agentId, equipmentIndex);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @return the number of effects currently on you
     */
    public int getEffectCount() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_EFFECT_COUNT);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * 
     * @param skillId
     * @return If the effect exists on you, the id of the effect is returned.
     * @throws IOException 
     */
    public int getEffect(int skillId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_EFFECT, skillId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * 
     * @param effectIndex (�-based)
     * @return the skill ID of the specified effect
     * @throws IOException 
     */
    public int getEffectByIndex(int effectIndex) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_EFFECT_BY_INDEX, effectIndex);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * 
     * @param skillId
     * @return  If the effect exists on you, the total duration and the current time left of the effect is returned as floats in milliseconds.
     * @throws IOException 
     */
    public int[] getEffectDuration(int skillId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_EFFECT_DURATION, skillId);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param agentId
     * @return whether the agent with the given ID exists
     * @throws IOException 
     */
    public boolean getAgentExists(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_AGENT_EXIST, agentId);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @param agentId
     * @return  integer of primary and integer of secondary profession for the agent with the given ID
     * @throws IOException 
     */
    public int[] getProfessions(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_PROFESSIONS, agentId);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param agentId
     * @return  integer of player number or, if the agent is an NPC/monster, the 'Model number'.
     * @throws IOException 
     */
    public int getPlayerNumber(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_PLAYER_NUMBER, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return float between 0 and 1 with the health in percent.
     * @throws IOException 
     */
    public float getHp(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_HP, agentId);
        return receivedGwcaPacket.getWparamAsFloat();
    }

    /**
     * @param agentId
     * @return float of rotation in degrees.
     * @throws IOException 
     */
    public float getRotation(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ROTATION, agentId);
        return receivedGwcaPacket.getWparamAsFloat();
    }

    /**
     * @param agentId
     * @return  integer of current skill being used by agent.
     * @throws IOException 
     */
    public int getSkill(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_SKILL, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return  float of X and float of Y coordinate, packed of course.
     * @throws IOException 
     */
    public float[] getCoords(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_COORDS, agentId);
        return receivedGwcaPacket.getParamsAsFloatArray();
    }

    /**
     * @param agentId
     * @return
     * @throws IOException 
     */
    public float[] getWeaponSpeeds(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_WEAPON_SPEEDS, agentId);
        return receivedGwcaPacket.getParamsAsFloatArray();
    }

    /**
     * @param agentId
     * @return integer of team id. 1 = blue, 2 = red, 3 = yellow, ...
     * @throws IOException 
     */
    public int getTeamId(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TEAM_ID, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return  integer of how many pips the target has.
     * @throws IOException 
     */
    public int getHpPips(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_HP_PIPS, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return  If 0, target is not hexed. If 1, target is hexed without degen and if 2, target is hexed with degen.
     * @throws IOException 
     */
    public int getHex(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_HEX, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return The model animation number depends on profession and sex, but can be used to determine emotes, etc.
     * @throws IOException 
     */
    public int getModelAnimation(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_MODEL_ANIMATION, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return 0xDB = players/npc's/monsters (living stuff), 0x200 = signposts/chests/solid objects, 0x400 = items that can be picked up.
     * @throws IOException 
     */
    public int getType(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TYPE, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return level of the agent
     * @throws IOException 
     */
    public int getLevel(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_LEVEL, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return the size of the agent array divided by 4 (because the agent array consists of Agent pointers (pointer = 4 bytes)).
     * @throws IOException 
     */
    public int getMaxId() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_MAX_ID);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return id of your nearest agent.
     * @throws IOException 
     */
    public int getMyNearestAgent() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_MY_NEAREST_AGENT);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return the distance
     * @throws IOException 
     */
    public float getMyDistanceToAgent(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_MY_DISTANCE_TO_AGENT, agentId);
        return receivedGwcaPacket.getWparamAsFloat();
    }

    /**
     * @param agentId
     * @return  id of the nearest agent to the specified agent.
     * @throws IOException 
     */
    public int getNearestAgentToAgent(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_AGENT_TO_AGENT, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @param otherAgentId
     * @return  the distance between the two agents as a float.
     * @throws IOException 
     */
    public float getDistanceFromAgentToAgent(int agentId, int otherAgentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_DISTANCE_FROM_AGENT_TO_AGENT, agentId, otherAgentId);
        return receivedGwcaPacket.getWparamAsFloat();
    }

    /**
     * @param agentId
     * @return same as CA_GetNearestAgentToAgent, but also returns float with distance between them.
     * @throws IOException 
     */
    public float[] getNearestAgentToAgentEx(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_AGENT_TO_AGENT_EX, agentId);
        return receivedGwcaPacket.getParamsAsIntFloatArray();
    }

    /**
     * @return whether the agent is attacking
     * @throws IOException 
     */
    public boolean isAttacking(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_IS_ATTACKING, agentId);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @param agentId
     * @return whether the agent is knocked down
     * @throws IOException 
     */
    public boolean isKnockedDown(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_IS_KNOCKED_DOWN, agentId);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @param agentId
     * @return whether the agent is moving
     * @throws IOException 
     */
    public boolean isMoving(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_IS_MOVING, agentId);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @param agentId
     * @return whether the agent is dead
     * @throws IOException 
     */
    public boolean isDead(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_IS_DEAD, agentId);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @param playerNumber player/model number
     * @return  the first (id-wise) agent with that number.
     * @throws IOException 
     */
    public int getFirstAgentByPlayerNumber(int playerNumber) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_FIRST_AGENT_BY_PLAYER_NUMBER, playerNumber);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return  the allegiance of agent. 0x100 = ally/non-attackable, 0x300 = enemy, 0x400 = spirit/pet, 0x500 = minion, 0x600 = npc/minipet.
     * @throws IOException 
     */
    public int getAllegiance(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ALLEGIANCE, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return  id of nearest enemy to agent by team id (so, only for PvP) and float of distance.
     * @throws IOException 
     */
    public float[] getNearestEnemyToAgentEx(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_ENEMY_TO_AGENT_EX, agentId);
        return receivedGwcaPacket.getParamsAsIntFloatArray();
    }

    /**
     * @param agentId
     * @return agent id of item and distance as float.
     * @throws IOException 
     */
    public float[] getNearestItemToAgentEx(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_ITEM_TO_AGENT_EX, agentId);
        return receivedGwcaPacket.getParamsAsIntFloatArray();
    }

    /**
     * @param playerNumber
     * @return Same as CA_GetFirstAgentByPlayerNumber except it returns the nearest agent to you.
     * @throws IOException 
     */
    public int getNearestAgentByPlayerNumber(int playerNumber) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_AGENT_BY_PLAYER_NUMBER, playerNumber);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return  current speed of agent (how far the agent moves per second in GW units).
     * @throws IOException 
     */
    public float getSpeed(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_SPEED, agentId);
        return receivedGwcaPacket.getWparamAsFloat();
    }

    /**
     * @param agentId
     * @return  id of enemy and distance as float. Only works for yourself or your allies.
     * @throws IOException 
     */
    public float[] getNearestEnemyToAgentByAllegiance(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_ENEMY_TO_AGENT_BY_ALLEGIANCE, agentId);
        return receivedGwcaPacket.getParamsAsIntFloatArray();
    }

    /**
     * @param agentId
     * @return  id of enemy and distance as float. Only works for yourself or your allies.s
     * @throws IOException 
     */
    public float[] getNearestAliveEnemyToAgent(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_ALIVE_ENEMY_TO_AGENT, agentId);
        return receivedGwcaPacket.getParamsAsIntFloatArray();
    }

    /**
     * @param agentId
     * @return  weapon type. 1=bow, 2=axe, 3=hammer, 4=daggers, 5=scythe, 6=spear, 7=sword, 10=wand.
     * @throws IOException 
     */
    public WeaponType getWeaponType(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_WEAPON_TYPE, agentId);
        byte[] lParam = receivedGwcaPacket.getWparam();
        return WeaponType.toEnum((short) byteArrayToInt(lParam));
    }

    /**
     * @param agentId
     * @return id of the nearest signpost/chest.
     * @throws IOException 
     */
    public int getNearestSignpostToAgent(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_SIGNPOST_TO_AGENT, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return id of the nearest NPC.
     * @throws IOException 
     */
    public int getNearestNpcToAgentByAllegiance(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_NPC_TO_AGENT_BY_ALLEGIANCE, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param x
     * @param y
     * @return  the nearest agent to the specified coords.
     * @throws IOException 
     */
    public int getNearestAgentToCoords(float x, float y) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_AGENT_TO_COORDS, x, y);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param x
     * @param y
     * @return the nearest NPC to the specified coords.
     * @throws IOException 
     */
    public int getNearestNpcToCoords(float x, float y) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_NPC_TO_COORDS, x, y);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return the unique login number in instance of player. If used on non-player it will return 0.
     * @throws IOException 
     */
    public int getLoginNumber(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_LOGIN_NUMBER, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param playerNumber
     * @return  the number of alive agents within compass range with specified id.
     * @throws IOException 
     */
    public int getNumberOfAgentsByPlayerNumber(int playerNumber) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NUMBER_OF_AGENTS_BY_PLAYER_NUMBER, playerNumber);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @return  the number of alive and visible enemy agents in total.
     * @throws IOException 
     */
    public int getNumberOfAliveEnemyAgents() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NUMBER_OF_ALIVE_ENEMY_AGENTS);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param startingAgentId
     * @return the agent id of the next item. For looping through all items, use this command with the returned agent id as parameter after taking care of that item.
     * @throws IOException 
     */
    public int getNextItem(int startingAgentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEXT_ITEM, startingAgentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return  the currently logged target of the specified agent.
     * @throws IOException 
     */
    public int getTarget(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TARGET, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     *  Remember to use CA_ResetAttributes before starting to set attributes in scripts! See the $ATTRIB constants in GWCAConstants.au3 or http://wiki.guildwars.com/wiki/Template_format for attribute id's.
     * @param attribute
     * @param value
     * @throws IOException 
     */
    public void setAttribute(Attribute attribute, int value) throws IOException {
        sendPacket(GWCAOperation.SET_ATTRIBUTE, attribute.getValue(), value);
    }

    /**
     * Resets the saved attributes, does NOT set your attributes to 0! To be used before a series of CA_SetAttribute.
     * @throws IOException 
     */
    public void resetAttributes() throws IOException {
        sendPacket(GWCAOperation.RESET_ATTRIBUTES);
    }

    /**
     * @param skillId
     * @return
     * @throws IOException 
     */
    public boolean playerHasBuff(int skillId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.PLAYER_HAS_BUFF);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @param skillId
     * @return
     * @throws IOException 
     */
    public boolean hero1HasBuff(int skillId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.HERO1_HAS_BUFF);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @param skillId
     * @return
     * @throws IOException 
     */
    public boolean hero2HasBuff(int skillId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.HERO2_HAS_BUFF);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @param skillId
     * @return
     * @throws IOException 
     */
    public boolean hero3HasBuff(int skillId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.HERO3_HAS_BUFF);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @param agentId
     * @return the number of foes to the agent which are currently targetting it.
     * @throws IOException 
     */
    public int getAgentDanger(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_AGENT_DANGER, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return  the bitmap integer used to determine things such as boss (0xC00), spirit (0x40000) and player (0x400000). Remember to use BitAND() like with CA_GetEffects.
     * @throws IOException 
     */
    //TODO: understand the return
    public int getTypeMap(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TYPE_MAP, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return the item id('s) of the agent's weapon(s). Can be used to acquire a bit more detailed info about weapons than CA_GetWeaponType.
     * @throws IOException 
     */
    public int[] getAgentWeapons(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_AGENT_WEAPONS, agentId);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param agentId
     * @return the agent id of the next valid agent. For looping through all agents, use this command with the returned agent id again.
     * @throws IOException 
     */
    public int getNextAgent(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEXT_AGENT, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return the agent id of the next valid ally. For looping through all allies, use this command with the returned agent id again.
     * @throws IOException 
     */
    public int getNextAlly(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEXT_ALLY, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return the agent id of the next valid foe. For looping through all foes, use this command with the returned agent id again.
     * @throws IOException 
     */
    public int getNextFoe(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEXT_FOE, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId
     * @return  the extra type of the agent as integer. This number can be used for distinguishing between chests and signposts for example.
     * @throws IOException 
     */
    public int getExtraType(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_EXTRA_TYPE, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * Sets up the CA_GetNearestPlayerNumberToCoords command. But please use the wrapper function GetNearestPlayerNumberToCoords() in GWCAConstants.au3 instead!
     * @param playerNumber
     * @return
     * @throws IOException 
     */
    public void prepareNearestPlayerNumberToCoords(int playerNumber) throws IOException {
        sendPacket(GWCAOperation.PREPARE_NEAREST_PLAYER_NUMBER_TO_COORDS, playerNumber);
    }

    /**
     * Requires previous call to CA_PrepareNearestPlayerNumberToCoords. But please use the wrapper function GetNearestPlayerNumberToCoords() in GWCAConstants.au3 instead!
     * @param x
     * @param y
     * @return
     * @throws IOException 
     */
    public int getNearestPlayerNumberToCoords(float x, float y) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_PLAYER_NUMBER_TO_COORDS, x, y);
        return receivedGwcaPacket.getWparamAsInt();
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
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_FIRST_AGENT_BY_PLAYER_NUMBER_BY_TEAM, playerNumber, teamId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param x
     * @param y
     * @return the nearest alive enemy to the specified coordinates and the distance to the enemy.
     * @throws IOException 
     */
    public float[] getNearestAliveEnemyToCoords(float x, float y) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_ALIVE_ENEMY_TO_COORDS, x, y);
        return receivedGwcaPacket.getParamsAsIntFloatArray();
    }

    /**
     * @param playerNumber
     * @throws IOException 
     */
    public void prepareNearestPlayerNumberToCoordsByPlayerNumber(int playerNumber) throws IOException {
        sendPacket(GWCAOperation.PREPARE_NEAREST_PLAYER_NUMBER_TO_COORDS_BY_PLAYER_NUMBER);
    }

    /**
     * @param x
     * @param y
     * @return
     * @throws IOException 
     */
    public float[] getNearestAliveEnemyToCoordsByPlayerNumber(float x, float y) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_ALIVE_ENEMY_TO_COORDS_BY_PLAYER_NUMBER, x, y);
        return receivedGwcaPacket.getParamsAsIntFloatArray();
    }

    /**
     * @param playerNumber
     * @param x
     * @param y
     * @return
     * @throws IOException 
     */
    public float[] getNearestAliveEnemyToCoordsByPlayerNumber(int playerNumber, float x, float y) throws IOException {
        prepareNearestPlayerNumberToCoordsByPlayerNumber(playerNumber);
        return this.getNearestAliveEnemyToCoordsByPlayerNumber(x, y);
    }

    /**
     * @param agentId
     * @return the agent id of the next valid alive foe. For looping through all alive foes, use this command with the returned agent id again.
     * @throws IOException 
     */
    public int getNextAliveFoe(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEXT_ALIVE_FOE, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param playerNumber
     * @return the nearest alive agent with specified player/model number and your distance to it.
     * @throws IOException 
     */
    public float[] getNearestAliveAgentByPlayerNumber(int playerNumber) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_ALIVE_AGENT_BY_PLAYER_NUMBER, playerNumber);
        return receivedGwcaPacket.getParamsAsIntFloatArray();
    }

    /**
     * @return gold on your character and gold in your storage.
     * @throws IOException 
     */
    public int[] getGold() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_GOLD);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param bag the bag you want the size of (+ how many slots that are taken)
     * @return the number of slots and the number of items currently in the bag.
     * @throws IOException 
     */
    public int[] getBagSize(BagLocation bag) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_BAG_SIZE, bag.getValue());
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param bag the bag to work with
     * @throws IOException 
     */
    public void setBag(BagLocation bag) throws IOException {
        sendPacket(GWCAOperation.SET_BAG, bag.getValue());
        //GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.SET_BAG, bag.getValue());
    }

    /**
     * @param itemSlot
     * @return  item id and item model id.
     * @throws IOException 
     */
    public int[] getItemId(int itemSlot) throws IOException {
        return this.getItemId(itemSlot, null);
    }

    /**
     * @param itemSlot
     * @param bag
     * @return item id and item model id.
     * @throws IOException 
     */
    public int[] getItemId(int itemSlot, BagLocation bag) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_ID, itemSlot, bag != null ? bag.getValue() : ZERO);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @return item id of the first ID kit it finds. If return is non-zero then you have an ID kit in your inventory.
     * @throws IOException 
     */
    public int getIdKit() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ID_KIT);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param bag
     * @param itemSlot
     * @throws IOException 
     */
    public void identifyItem(BagLocation bag, int itemSlot) throws IOException {
        sendPacket(GWCAOperation.IDENTIFY_ITEM, bag.getValue(), itemSlot);
    }

    /**
     *  If you've returned the item id from one of the prior commands you can use this command instead of CA_IdentifyItem.
     * @param itemId
     * @throws IOException 
     */
    public void identifyItemById(int itemId) throws IOException {
        sendPacket(GWCAOperation.IDENTIFY_ITEM_BY_ID, itemId);
    }

    /**
     * Can be used anywhere in the outpost.
     * @param amount Specify -1 if you want to deposit the maximum amount. 
     * @throws IOException 
     */
    public void depositGold(int amount) throws IOException {
        sendPacket(GWCAOperation.DEPOSIT_GOLD, amount);
    }

    /**
     * Can be used anywhere in the outpost.
     * @param amount Specify -1 if you want to withdraw the maximum amount.
     * @throws IOException 
     */
    public void withdrawGold(int amount) throws IOException {
        sendPacket(GWCAOperation.WITHDRAW_GOLD, amount);
    }

    /**
     *  Requires an open dialog with the Merchant of the outpost!
     * @param bag
     * @param itemSlot
     * @throws IOException 
     */
    public void sellItem(BagLocation bag, int itemSlot) throws IOException {
        sendPacket(GWCAOperation.SELL_ITEM, bag.getValue(), itemSlot);
    }

    /**
     * Requires an open dialog with the Merchant of the outpost!
     * @param itemId
     * @throws IOException 
     */
    public void sellItemById(int itemId) throws IOException {
        sendPacket(GWCAOperation.SELL_ITEM_BY_ID, itemId);
    }

    /**
     *  Requires an open dialog with the Merchant of the outpost!
     * @throws IOException 
     */
    public void buyIdKit() throws IOException {
        sendPacket(GWCAOperation.BUY_ID_KIT);
    }

    /**
     * Requires an open dialog with the Merchant of the outpost!
     * @throws IOException 
     */
    public void buySuperiorIdKit() throws IOException {
        sendPacket(GWCAOperation.BUY_SUPERIOR_ID_KIT);
    }

    /**
     * @param itemSlot
     * @param bag
     * @return item rarity and quantity.
     * @throws IOException 
     */
    //TODO: return rarity enum
    public int[] getItemInfo(int itemSlot, BagLocation bag) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_INFO, itemSlot, bag != null ? bag.getValue() : ZERO);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param itemSlot
     * @return item rarity and quantity.
     * @throws IOException 
     */
    public int[] getItemInfo(int itemSlot) throws IOException {
        return this.getItemInfo(itemSlot, null);
    }

    /**
     * Uses the item specified.
     * @param bag
     * @param itemSlot
     * @throws IOException 
     */
    public void useItem(BagLocation bag, int itemSlot) throws IOException {
        sendPacket(GWCAOperation.USE_ITEM, bag.getValue(), itemSlot);
    }

    /**
     * Uses the item specified.
     * @param itemId
     * @throws IOException 
     */
    public void useItemById(int itemId) throws IOException {
        sendPacket(GWCAOperation.USE_ITEM_BY_ID, itemId);
    }

    /**
     * Drops the specified item. If the quantity is above 1 then this command will drop it all.
     * @param bag
     * @param itemSlot
     * @throws IOException 
     */
    public void dropItem(BagLocation bag, int itemSlot) throws IOException {
        sendPacket(GWCAOperation.DROP_ITEM, bag.getValue(), itemSlot);
    }

    /**
     * Drops the specified item by the specified amount. Setting amount to -1 will drop it all.
     * @param itemId
     * @throws IOException 
     */
    public void dropItemById(int itemId) throws IOException {
        sendPacket(GWCAOperation.DROP_ITEM_BY_ID, itemId);
    }

    /**
     * Accepts all unclaimed items (from the unclaimed items window that may appear). Doesn't check if there isn't room for all the items, this simply accepts them!
     * @throws IOException 
     */
    public void acceptAllItems() throws IOException {
        sendPacket(GWCAOperation.ACCEPT_ALL_ITEMS);
    }

    /**
     * @param itemModelId
     * @return the first occurence of the specified model id and returns integer with the item id or 0 if item wasn't found.
     * @throws IOException 
     */
    public int findItemByModelId(int itemModelId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.FIND_ITEM_BY_MODEL_ID, itemModelId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param bag
     * @return bag index and item slot
     * @throws IOException 
     */
    //TODO: return bag enum as the first return element of the array
    public int[] findEmptySlot(BagLocation bag) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.FIND_EMPTY_SLOT, bag.getValue());
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * Searches from the backpack until (and including) the bag index supplied.
     * @param bag  last bag to search in
     * @return  item id of the gold item found and the item's model id. If return value is 0 then no gold items were found.
     * @throws IOException 
     */
    public int[] findGoldItem(BagLocation bag) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.FIND_GOLD_ITEM, bag == null ? ZERO : bag.getValue());
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * search all bags including storage
     * @return
     * @throws IOException 
     */
    public int[] findGoldItem() throws IOException {
        return this.findGoldItem(null);
    }

    /**
     * @param itemId
     * @return the position of the item; bag and slot-wise. If any of the returns are 0, the item was not found.
     * @throws IOException 
     */
    //TODO: return bag enum for the first param (for multivalue returns, separate methods could be made... or just a composite return class)
    public int[] getItemPositionByItemId(int itemId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_POSITION_BY_ITEM_ID, itemId);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param modelId
     * @return the position of the item; bag and slot-wise. If any of the returns are 0, the item was not found.
     * @throws IOException 
     */
    //TODO: return bag enum for the first param
    public int[] getItemPositionByModelId(int modelId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_POSITION_BY_MODEL_ID, modelId);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param rarity
     * @return the position of the item; bag and slot-wise. If any of the returns are 0, the item was not found.
     * @throws IOException 
     */
    //TODO: return bag enum for the first param
    public int[] getItemPositionByRarity(Rarity rarity) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_POSITION_BY_RARITY, rarity.getValue());
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param itemId
     * @return the model id of the specified item.
     * @throws IOException 
     */
    public int getItemModelIdById(int itemId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_MODEL_ID_BY_ID, itemId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param itemId
     * @return the rarity and quantity of the specified item.
     * @throws IOException 
     */
    //TODO: return rarity enum for the first param
    public int[] getItemInfoById(int itemId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_INFO_BY_ID, itemId);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param bag
     * @param itemSlot
     * @throws IOException 
     */
    public void equipItem(BagLocation bag, int itemSlot) throws IOException {
        sendPacket(GWCAOperation.EQUIP_ITEM, bag.getValue(), itemSlot);
    }

    /**
     * @param itemId
     * @throws IOException 
     */
    public void equipItemById(int itemId) throws IOException {
        sendPacket(GWCAOperation.EQUIP_ITEM_BY_ID, itemId);
    }

    /**
     * @return the item id if it finds one 
     * @throws IOException 
     */
    public int getSalvageKit() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_SALVAGE_KIT);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * Buys the specified item if you used the right gold cost.
     * @param merchantItemIndex (starting at 1)
     * @param goldCost
     * @throws IOException 
     */
    public void buyItem(int merchantItemIndex, int goldCost) throws IOException {
        sendPacket(GWCAOperation.BUY_ITEM, merchantItemIndex, goldCost);
    }

    /**
     * 
     * @param agentId or -1 for the current target, -2 for yourself
     * @return item id and model id of the specified agent, if it's an item.
     * @throws IOException 
     */
    public int[] getItemIdByAgent(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_ID_BY_AGENT, agentId);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param agentId or -1 for the current target, -2 for yourself
     * @return item rarity and quantity of the specified agent, if it's an item.
     * @throws IOException 
     */
    //TODO: rarity enum return
    public int[] getItemInfoByAgent(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_INFO_BY_AGENT, agentId);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param itemModelId
     * @return the agent id and the distance between you and the item as a float.
     * @throws IOException 
     */
    public float[] getNearestItemByModelId(int itemModelId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_ITEM_BY_MODEL_ID, itemModelId);
        return receivedGwcaPacket.getParamsAsFloatArray();
    }

    /**
     * @param bag
     * @param itemSlot
     * @return the extra id of the item - used to determine dye color etc.
     * @throws IOException 
     */
    public int getItemExtraId(BagLocation bag, int itemSlot) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_EXTRA_ID, itemSlot, bag == null ? ZERO : bag.getValue());
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param itemSlot
     * @return the extra id of the item - used to determine dye color etc.
     * @throws IOException 
     */
    public int getItemExtraId(int itemSlot) throws IOException {
        return getItemExtraId(null, itemSlot);
    }

    /**
     * @param itemId
     * @return the extra id of the item - used to determine dye color etc.
     * @throws IOException 
     */
    public int getItemExtraIdById(int itemId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_EXTRA_ID_BY_ID, itemId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId or -1 for the current target, -2 for yourself
     * @return  the extra id of the item - used to determine dye color etc.
     * @throws IOException 
     */
    public int getItemExtraIdByAgent(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_EXTRA_ID_BY_AGENT, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param bag
     * @param itemSlot
     * @return  the attribute requirement of the item.
     * @throws IOException 
     */
    public int getItemReq(BagLocation bag, int itemSlot) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_REQ, itemSlot, bag == null ? ZERO : bag.getValue());
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param itemSlot
     * @return  the attribute requirement of the item.
     * @throws IOException 
     */
    public int getItemReq(int itemSlot) throws IOException {
        return getItemReq(null, itemSlot);
    }

    /**
     * @param itemId
     * @return  the attribute requirement of the item.
     * @throws IOException 
     */
    public int getItemReqById(int itemId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_REQ_BY_ID, itemId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * 
     * @param agentId or -1 for the current target, -2 for yourself
     * @return
     * @throws IOException 
     */
    public int getItemReqByAgent(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_REQ_BY_AGENT, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param dye
     * @param startBag
     * @return the position, if found, of the specified dye as bag,slot.
     * @throws IOException 
     */
    public int getDyePositionByColor(Dye dye, BagLocation startBag) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_DYE_POSITION_BY_COLOR, dye.getValue(),
                startBag == null ? ZERO : startBag.getValue());
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param dye
     * @return the position, if found, of the specified dye as bag,slot.
     * @throws IOException 
     */
    public int getDyePositionByColor(Dye dye) throws IOException {
        return getDyePositionByColor(dye, null);
    }

    /**
     * @param itemSlot
     * @param startBag
     * @return the damage mod of the item.
     * @throws IOException 
     */
    public int getItemDmgMod(int itemSlot, BagLocation startBag) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_DMG_MOD, itemSlot,
                startBag == null ? ZERO : startBag.getValue());
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param itemSlot
     * @return the damage mod of the item.
     * @throws IOException 
     */
    public int getItemDmgMod(int itemSlot) throws IOException {
        return getItemDmgMod(itemSlot, null);
    }

    /**
     * Only works for offhands/shields!
     * @param itemId
     * @return  the damage mod of the item
     * @throws IOException 
     */
    public int getItemDmgModById(int itemId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_DMG_MOD_BY_ID, itemId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * Only works for offhands/shields!
     * @param agentId or -1 for the current target, -2 for yourself
     * @return  the damage mod of the item.
     * @throws IOException 
     */
    public int getItemDmgModByAgent(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_DMG_MOD_BY_AGENT, agentId);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * Like pressing the "Submit Offer" button, but also including the amount of gold offered.
     * @param goldAmount
     * @throws IOException 
     */
    public void submitOffer(int goldAmount) throws IOException {
        sendPacket(GWCAOperation.SUBMIT_OFFER, goldAmount);
    }

    /**
     * Like pressing the "Change Offer" button.
     * @throws IOException 
     */
    public void changeOffer() throws IOException {
        sendPacket(GWCAOperation.CHANGE_OFFER);
    }

    /**
     * Offers the specified item in the trade window.
     * @param bag
     * @param itemSlot
     * @throws IOException 
     */
    public void offerItem(BagLocation bag, int itemSlot) throws IOException {
        sendPacket(GWCAOperation.OFFER_ITEM, bag.getValue(), itemSlot);
    }

    /**
     * Offers the specified item in the trade window.
     * @param itemId
     * @throws IOException 
     */
    public void offerItem(int itemId) throws IOException {
        sendPacket(GWCAOperation.OFFER_ITEM, itemId);
    }

    /**
     * Like pressing the "Cancel" button in a trade.
     * @throws IOException 
     */
    public void cancelTrade() throws IOException {
        sendPacket(GWCAOperation.CANCEL_TRADE);
    }

    /**
     * Like pressing the "Accept" button in a trade. Can only be used after both players have submitted their offer.
     * @throws IOException 
     */
    public void acceptTrade() throws IOException {
        sendPacket(GWCAOperation.ACCEPT_TRADE);
    }

    /**
     * attacks the specified target with weapon.
     * @param targetId
     * @throws IOException 
     */
    public void attack(int targetId) throws IOException {
        sendPacket(GWCAOperation.ATTACK, targetId);
    }

    /**
     * @param x
     * @param y
     * @throws IOException 
     */
    public void move(float x, float y) throws IOException {
        LOG.debug("Moving to: {} {}", x, y);
        sendPacket(GWCAOperation.MOVE, x, y);
    }

    /**
     * @param x
     * @param y
     * @param random
     * @throws IOException 
     */
    public void moveEx(float x, float y, int random) throws IOException {
        x += -random + new Random().nextInt(random * 2);
        y += -random + new Random().nextInt(random * 2);
        this.move(x, y);
    }

    /**
     * Works on all skills including attack skills. Remember that some skills that target yourself may require that specified
     * @param skillSlot
     * @param targetId or -1 for the current target, -2 for yourself
     * @throws IOException 
     */
    public void useSkill(SkillBarSkillSlot skillSlot, int targetId) throws IOException {
        sendPacket(GWCAOperation.USE_SKILL, skillSlot.getValue(), targetId);
    }

    /**
     * @param weaponSlot
     * @throws IOException 
     */
    public void changeWeaponSet(int weaponSlot) throws IOException {
        sendPacket(GWCAOperation.CHANGE_WEAPON_SET, weaponSlot);
    }

    /**
     * zones to the outpost/city specified and if district number is provided also attempts to enter that district. Always uses current region and language
     * @param zoneId
     * @param districtNumber
     * @throws IOException 
     */
    public void zoneMap(int zoneId, int districtNumber) throws IOException {
        sendPacket(GWCAOperation.ZONE_MAP, zoneId, districtNumber);
    }

    /**
     * @param zoneId
     * @param districtNumber
     * @throws IOException 
     */
    public void travelTo(int zoneId, int districtNumber) throws IOException {
        long start = System.currentTimeMillis();
        this.initMapLoad();
        this.zoneMap(zoneId, districtNumber);

        int status = -1;
        boolean secondStatusCheck = false;

        do {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                // do nothing
            }
            status = this.getMapLoading();
            long elapsedTime = System.currentTimeMillis() - start;

            // if 15 seconds passed and the status is still "loading", execute the command again
            if (elapsedTime > 15000 && status != 2) {
                this.zoneMap(zoneId, districtNumber);
            }

            secondStatusCheck = this.isMapLoaded();
        } while (status != 0 && !secondStatusCheck);
    }

    /**
     * drops the gold on the ground
     * @param goldAmount
     * @throws IOException 
     */
    public void dropGold(int goldAmount) throws IOException {
        sendPacket(GWCAOperation.DROP_GOLD, goldAmount);
    }

    /**
     * goes to specified NPC.
     * @param npcId
     * @throws IOException 
     */
    public void goNpc(int npcId) throws IOException {
        sendPacket(GWCAOperation.GO_NPC, npcId);
    }

    /**
     * goes to specified player (for use in outposts/cities).
     * @param playerId
     * @throws IOException 
     */
    public void goPlayer(int playerId) throws IOException {
        sendPacket(GWCAOperation.GO_PLAYER, playerId);
    }

    /**
     * goes to specified signpost/chest (yellow name).
     * @param signpostOrChestId
     * @throws IOException 
     */
    public void goSignpost(int signpostOrChestId) throws IOException {
        sendPacket(GWCAOperation.GO_SIGN_POST, signpostOrChestId);
    }

    /**
     * enters the challenge if you're in a Challenge Mission outpost.
     * @throws IOException 
     */
    public void enterChallenge() throws IOException {
        sendPacket(GWCAOperation.ENTER_CHALLENGE);
    }

    /**
     * used after CA_EnterChallenge, it allows you to skip past the foreign character window.
     * @throws IOException 
     */
    public void enterChallengeForeign() throws IOException {
        sendPacket(GWCAOperation.ENTER_CHALLENGE_FOREIGN);
    }

    /**
     * opens the nearest chest if you're within 'close range'.
     * @throws IOException 
     */
    public void openChest() throws IOException {
        sendPacket(GWCAOperation.OPEN_CHEST);
    }

    /**
     * picks up the specified item
     * @param agentId
     * @throws IOException 
     */
    public void pickupItem(int agentId) throws IOException {
        sendPacket(GWCAOperation.PICKUP_ITEM, agentId);
    }

    /**
     * experimental function, requires knowledge about the NPC's dialog options. Can be found by using CA_GetLastDialogId
     * @param dialogOptionId
     * @throws IOException 
     */
    public void dialog(int dialogOptionId) throws IOException {
        sendPacket(GWCAOperation.DIALOG, dialogOptionId);
    }

    /**
     * @param agentId
     * @throws IOException 
     */
    public void changeTarget(int agentId) throws IOException {
        sendPacket(GWCAOperation.CHANGE_TARGET, agentId);
    }

    /**
     *  targets the nearest foe
     * @throws IOException 
     */
    public void targetNearestFoe() throws IOException {
        sendPacket(GWCAOperation.TARGET_NEAREST_FOE);
    }

    /**
     *  targets the nearest ally
     * @throws IOException 
     */
    public void targetNearestAlly() throws IOException {
        sendPacket(GWCAOperation.TARGET_NEAREST_ALLY);
    }

    /**
     * targets the nearest item/signpost/chest
     * @throws IOException 
     */
    public void targetNearestItem() throws IOException {
        sendPacket(GWCAOperation.TARGET_NEAREST_ITEM);
    }

    /**
     * targets the currently called target of the party
     * @throws IOException 
     */
    public void targetCalledTarget() throws IOException {
        sendPacket(GWCAOperation.TARGET_CALLED_TARGET);
    }

    /**
     * @param skillSlot
     * @param agentId
     * @throws IOException 
     */
    public void useHero1Skill(SkillBarSkillSlot skillSlot, int agentId) throws IOException {
        sendPacket(GWCAOperation.USE_HERO1_SKILL, skillSlot.getValue(), agentId);
    }

    /**
     * @param skillSlot
     * @param agentId
     * @throws IOException 
     */
    public void useHero2Skill(SkillBarSkillSlot skillSlot, int agentId) throws IOException {
        sendPacket(GWCAOperation.USE_HERO2_SKILL, skillSlot.getValue(), agentId);
    }

    /**
     * @param skillSlot
     * @param agentId
     * @throws IOException 
     */
    public void useHero3Skill(SkillBarSkillSlot skillSlot, int agentId) throws IOException {
        sendPacket(GWCAOperation.USE_HERO3_SKILL, skillSlot.getValue(), agentId);
    }

    /**
     * cancels your current action. Just like pressing ESC.
     * @throws IOException 
     */
    public void cancelAction() throws IOException {
        sendPacket(GWCAOperation.CANCEL_ACTION);
    }

    /**
     * To unflag, set X and Y to 0x7F800000 (flag reset)
     * @param x
     * @param y
     * @throws IOException 
     */
    public void commandHero1(float x, float y) throws IOException {
        sendPacket(GWCAOperation.COMMAND_HERO1, x, y);
    }

    /**
     * To unflag, set X and Y to 0x7F800000 (flag reset)
     * @param x
     * @param y
     * @throws IOException 
     */
    public void commandHero2(float x, float y) throws IOException {
        sendPacket(GWCAOperation.COMMAND_HERO2, x, y);
    }

    /**
     * To unflag, set X and Y to 0x7F800000 (flag reset)
     * @param x
     * @param y
     * @throws IOException 
     */
    public void commandHero3(float x, float y) throws IOException {
        sendPacket(GWCAOperation.COMMAND_HERO3, x, y);
    }

    /**
     * To unflag, set X and Y to 0x7F800000 (flag reset)
     * @param x
     * @param y
     * @throws IOException 
     */
    public void commandAll(float x, float y) throws IOException {
        sendPacket(GWCAOperation.COMMAND_ALL, x, y);
    }

    /**
     * @param region
     * @param language experiment around with this!
     * @throws IOException 
     */
    public void changeDistrict(Region region, byte language) throws IOException {
        sendPacket(GWCAOperation.CHANGE_DISTRICT, region.getValue(), language);
    }

    /**
     * @throws IOException 
     */
    public void resign() throws IOException {
        LOG.debug("Resign");
        sendPacket(GWCAOperation.RESIGN);
    }

    /**
     * Abuses the fact that the client actually knows where you are, even though it may not display the correct position.
     * @param agentId or -1 for the current target, -2 for yourself
     * @throws IOException 
     */
    public void updateAgentPosition(int agentId) throws IOException {
        sendPacket(GWCAOperation.UPDATE_AGENT_POSITION, agentId);
    }

    /**
     * "Presses" the Return To Outpost button after resigning.
     * @throws IOException 
     */
    public void returnToOutpost() throws IOException {
        LOG.debug("Returning to outpost");
        sendPacket(GWCAOperation.RETURN_TO_OUTPOST);
    }

    /**
     * Goes to the specified target. Same as follow. Will not open NPC dialog or attack enemy.
     * @param agentId
     * @throws IOException 
     */
    public void goAgent(int agentId) throws IOException {
        sendPacket(GWCAOperation.GO_AGENT, agentId);
    }

    /**
     * Donates 5000 faction towards your faction. Not recently tested, but it should require open dialog with Faction Npc.
     * @throws IOException 
     */
    public void donateKurzickFaction() throws IOException {
        sendPacket(GWCAOperation.DONATE_FACTION, 0);
    }

    /**
     * Donates 5000 faction towards your faction. Not recently tested, but it should require open dialog with Faction Npc.
     * @throws IOException 
     */
    public void donateLuxonFaction() throws IOException {
        sendPacket(GWCAOperation.DONATE_FACTION, 1);
    }

    /**
     * @param skillSlot
     * @param skillId
     * @throws IOException 
     */
    public void setSkillbarSkill(SkillBarSkillSlot skillSlot, int skillId) throws IOException {
        sendPacket(GWCAOperation.SET_SKILLBAR_SKILL, skillSlot.getValue(), skillId);
    }

    /**
     * @param profession
     * @throws IOException 
     */
    public void changeSecondProfession(Profession profession) throws IOException {
        sendPacket(GWCAOperation.CHANGE_SECOND_PROFESSION, profession.getValue());
    }

    /**
     * Functions like the GW Control: Targetting - Party Member: Next
     * @throws IOException 
     */
    public void targetNextPartyMember() throws IOException {
        sendPacket(GWCAOperation.TARGET_NEXT_PARTY_MEMBER);
    }

    /**
     * Functions like the GW Control: Targetting - Next Foe
     * @throws IOException 
     */
    public void targetNextFoe() throws IOException {
        sendPacket(GWCAOperation.TARGET_NEXT_FOE);
    }

    /**
     * Skips the current cinematic/cutscene.
     * @throws IOException 
     */
    // Broken, also not working in AutoIt
    public void skipCinematic() throws IOException {
        sendPacket(GWCAOperation.SKIP_CINEMATIC);
    }

    /**
     * 
     * @param heroIndex (from 0 = self to 3 = Hero3)
     * @param buffIndex index of the buff in order it was casted.
     * @throws IOException 
     */
    public void dismissBuff(int heroIndex, int buffIndex) throws IOException {
        sendPacket(GWCAOperation.DISMISS_BUFF, heroIndex, buffIndex);
    }

    /**
     * Experimental function which visually opens your storage. Note: only works after opening your storage once! Else GW will crash!
     * @throws IOException 
     */
    public void openStorage() throws IOException {
        sendPacket(GWCAOperation.OPEN_STORAGE);
    }

    /**
     * Internal command. Please use ZoneMapEx() function instead of this.
     * @param mapId
     * @throws IOException 
     */
    public void prepareZoneMapEx(int mapId) throws IOException {
        sendPacket(GWCAOperation.PREPARE_ZONE_MAP_EX, mapId);
    }

    /**
     * Internal command. Requires previous call to CA_PrepareZoneMapEx, so please use ZoneMapEx() function instead of this.
     * @param region
     * @param language
     * @throws IOException 
     */
    public void zoneMapEx(Region region, int language) throws IOException {
        sendPacket(GWCAOperation.ZONE_MAP_EX, region.getValue(), language);
    }

    /**
     * Adds hero to the party.
     * @param hero
     * @throws IOException 
     */
    public void addHero(Hero hero) throws IOException {
        sendPacket(GWCAOperation.ADD_HERO, hero.getValue());
    }

    /**
     * Kicks hero from the party.
     * @param hero
     * @throws IOException 
     */
    public void kickHero(Hero hero) throws IOException {
        sendPacket(GWCAOperation.KICK_HERO, hero.getValue());
    }

    /**
     * @throws IOException 
     */
    public void toggleHardMode() throws IOException {
        sendPacket(GWCAOperation.SWITCH_MODE, 1);
    }

    /**
     * @throws IOException 
     */
    public void toggleNormalMode() throws IOException {
        sendPacket(GWCAOperation.SWITCH_MODE, 0);
    }

    /**
     * Adds henchman to the party.
     * @param henchmenId according to Party Search list
     * @throws IOException 
     */
    public void addNpc(int henchmenId) throws IOException {
        sendPacket(GWCAOperation.ADD_NPC, henchmenId);
    }

    /**
     * Kicks henchman from the party.
     * @param henchmenId
     * @throws IOException 
     */
    public void kickNpc(int henchmenId) throws IOException {
        sendPacket(GWCAOperation.KICK_NPC, henchmenId);
    }

    /**
     * @throws IOException 
     */
    public void travelGH() throws IOException {
        sendPacket(GWCAOperation.TRAVEL_GH);
    }

    /**
     * @throws IOException 
     */
    public void leaveGH() throws IOException {
        sendPacket(GWCAOperation.LEAVE_GH);
    }

    /**
     * Inits the CA_MapIsLoaded command.
     * @throws IOException 
     */
    public void initMapLoad() throws IOException {
        sendPacket(GWCAOperation.INIT_MAP_LOAD);
    }

    /**
     * Use CA_InitMapLoad before !!!
     * @return
     * @throws IOException 
     */
    public boolean isMapLoaded() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.MAP_IS_LOADED);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @param mapOverlayId  (get this with CA_GetNearestMapOverlayToCoords)
     * @return  the x,y coords of the specified map overlay.
     * @throws IOException 
     */
    public float[] getMapOverlayCoords(int mapOverlayId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_MAP_OVERLAY_COORDS, mapOverlayId);
        return receivedGwcaPacket.getParamsAsFloatArray();
    }

    /**
     * @param mapOverlayId
     * @return  the option variable (usually color) and the icon model id of the specified map overlay.
     * @throws IOException 
     */
    public int[] getMapOverlayInfo(int mapOverlayId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_MAP_OVERLAY_INFO, mapOverlayId);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param x
     * @param y
     * @return the nearest Map Overlay to the specified coords.
     * @throws IOException 
     */
    public int getNearestMapOverlayToCoords(float x, float y) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NEAREST_MAP_OVERLAY_TO_COORDS, x, y);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param heroIndex
     * @param heroMode
     * @throws IOException 
     */
    public void setHeroMode(int heroIndex, HeroMode heroMode) throws IOException {
        sendPacket(GWCAOperation.SET_HERO_MODE, heroIndex, heroMode.getValue());
    }

    /**
     * @param questId  (or -1 for active quest)
     * @return  the quest id and the current log state
     * @throws IOException 
     */
    public int[] questCheck(int questId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.QUEST_CHECK, questId);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param questId (or -1 for active quest)
     * @return the X,Y coordinates of the marker
     * @throws IOException 
     */
    public float[] questCoords(int questId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.QUEST_COORDS, questId);
        return receivedGwcaPacket.getParamsAsFloatArray();
    }

    /**
     * @return the quest id of the currently active quest
     * @throws IOException 
     */
    public int questActive() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.QUEST_ACTIVE);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * Abandons the specified quest.
     * @param questId
     * @throws IOException 
     */
    public void questAbandon(int questId) throws IOException {
        sendPacket(GWCAOperation.QUEST_ABANDON, questId);
    }

    /**
     * For use with CA_GetPartyInfo. Set the team size to be evaluated using the CA_GetPartyInfo command.
     * @param teamSize
     * @throws IOException 
     */
    public void setTeamSize(int teamSize) throws IOException {
        sendPacket(GWCAOperation.SET_TEAM_SIZE, teamSize);
    }

    /**
     * @return your current region and language.
     * @throws IOException 
     */
    public int[] getRegionAndLanguage() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_REGION_AND_LANGUAGE);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * For the Rune trader experiment around. Use this to setup CA_TraderBuy.
     * @param itemIndex the number on the list at the trader.
     * @throws IOException 
     */
    public void traderRequest(int itemIndex) throws IOException {
        sendPacket(GWCAOperation.TRADER_REQUEST, itemIndex);
    }

    /**
     * Loop this until the item id or cost is non-zero to verify that the info has been received before calling CA_TraderBuy.
     * @return the trader item id and it's cost that is currently active.
     * @throws IOException 
     */
    public int[] traderCheck() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.TRADER_CHECK);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * Requires a CA_TraderRequest and waiting until item id and cost has been received before it works!
     * @return whether the packet was sent or not as boolean.
     * @throws IOException 
     */
    public boolean traderBuy() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.TRADER_BUY);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * Sends a Request Quote on the specified item (remember to use the right trader for the item). Wait using CA_TraderCheck until the info is received.
     * @param bag
     * @param itemSlot
     * @throws IOException 
     */
    public void traderRequestSell(BagLocation bag, int itemSlot) throws IOException {
        sendPacket(GWCAOperation.TRADER_REQUEST_SELL, bag.getValue(), itemSlot);
    }

    /**
     * Same as CA_TraderRequestSell, but instead, simply takes an item id as input.
     * @param itemId
     * @throws IOException 
     */
    //FIXME: you must be on the sell tab to see something happen. Can you sell without being on it? if not, how to get there?
    public void traderRequestSellById(int itemId) throws IOException {
        sendPacket(GWCAOperation.TRADER_REQUEST_SELL_BY_ID, itemId);
    }

    /**
     * Requires a CA_TraderRequestSell(ById) and waiting until item id and cost has been received before it works!
     * @return whether the packet was sent or not as boolean.
     * @throws IOException 
     */
    public boolean traderSell() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.TRADER_SELL);
        return receivedGwcaPacket.getWparamAsBoolean();
    }

    /**
     * @param agentId or -1 for the current target, -2 for yourself
     * @param radius
     * @return the number of foes counted
     * @throws IOException 
     */
    public int getNumberOfFoesInRangeOfAgent(int agentId, float radius) throws IOException {
        //FIXME: calling float float will cause problems??
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NUMBER_OF_FOES_IN_RANGE_OF_AGENT, agentId, radius);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId or -1 for the current target, -2 for yourself
     * @param radius
     * @return the number of allies counted
     * @throws IOException 
     */
    public int getNumberOfAlliesInRangeOfAgent(int agentId, float radius) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NUMBER_OF_ALLIES_IN_RANGE_OF_AGENT, agentId, radius);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * @param agentId or -1 for the current target, -2 for yourself
     * @param radius
     * @return the number of items counted
     * @throws IOException 
     */
    public int getNumberOfItemsInRangeOfAgent(int agentId, float radius) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_NUMBER_OF_ITEMS_IN_RANGE_OF_AGENT, agentId, radius);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * Like pressing the "Trade" button next to a player's name.
     * @param agentId or -1 for the current target, -2 for yourself
     * @throws IOException 
     */
    public void tradePlayer(int agentId) throws IOException {
        sendPacket(GWCAOperation.TRADE_PLAYER, agentId);
    }

    /**
     * Used in skill recharges, effects durations and other things.
     * @return the current internal time stamp.
     * @throws IOException 
     */
    public int getTimeStamp() throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_TIME_STAMP);
        return receivedGwcaPacket.getWparamAsInt();
    }

    /**
     * Set to 0 to un-lock the target of the hero.
     * @param heroIndex
     * @param targetAgentId or -1 for the current target, -2 for yourself
     * @throws IOException 
     */
    public void lockHero(int heroIndex, int targetAgentId) throws IOException {
        sendPacket(GWCAOperation.LOCK_HERO, heroIndex, targetAgentId);
    }

    /**
     * Cancels the specified self-targetting or in other way targetting you maintained enchantment.
     * @param skillId
     * @throws IOException 
     */
    public void cancelMaintainedEnchantment(int skillId) throws IOException {
        sendPacket(GWCAOperation.CANCEL_MAINTAINED_ENCHANTMENT, skillId);
    }

    /**
     * @param skillId
     * @return the type of the specified skill
     * @throws IOException 
     */
    public SkillType getSkillType(int skillId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_SKILL_TYPE, skillId);
        byte[] lParam = receivedGwcaPacket.getWparam();
        return SkillType.toEnum((short) byteArrayToInt(lParam));
    }

    /**
     * @param bag
     * @param itemSlot
     * @throws IOException 
     */
    public void prepareMoveItem(BagLocation bag, int itemSlot) throws IOException {
        sendPacket(GWCAOperation.PREPARE_MOVE_ITEM, bag.getValue(), itemSlot);
    }

    /**
     * @param itemId
     * @throws IOException 
     */
    public void prepareMoveItem(int itemId) throws IOException {
        sendPacket(GWCAOperation.MOVE_ITEM, itemId);
    }

    /**
     * @param bag
     * @param itemSlot
     * @throws IOException 
     */
    public void moveItem(BagLocation bag, int itemSlot) throws IOException {
        sendPacket(GWCAOperation.MOVE_ITEM, bag.getValue(), itemSlot);
    }

    /**
     * Moves an item from one location to another
     * @param currentBag
     * @param currentItemSlot
     * @param targetBag
     * @param targetItemSlot
     * @throws IOException 
     */
    public void moveItem(BagLocation currentBag, int currentItemSlot, BagLocation targetBag, int targetItemSlot) throws IOException {
        this.prepareMoveItem(currentBag, currentItemSlot);
        try {
            //TODO: explain why this sleep is needed
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            // ignore
        }

        this.moveItem(targetBag, targetItemSlot);
    }

    /**
     * Moves an item from one location to another
     * @param itemId
     * @param targetBag
     * @param targetItemSlot
     * @throws IOException 
     */
    public void moveItemById(int itemId, BagLocation targetBag, int targetItemSlot) throws IOException {
        this.prepareMoveItem(itemId);
        try {
            //TODO: explain why this sleep is needed
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            // ignore
        }

        this.moveItem(targetBag, targetItemSlot);
    }

    /**
     * @param numberOfItems
     * @param maxDistance
     * @throws IOException 
     */
    public void pickupItems(int numberOfItems, float maxDistance) throws IOException {
        int itemsPicked = 0;

        long startDeadlockCheck = System.currentTimeMillis();

        do {
            float[] itemAgentIdAndDistance = this.getNearestItemToAgentEx(-2);

            long currentlyElapsedTime = System.currentTimeMillis() - startDeadlockCheck;
            if (itemAgentIdAndDistance[0] == 0 || itemAgentIdAndDistance[1] > maxDistance || currentlyElapsedTime > 30000) {
                break;
            }

            boolean itemStillExists = true;
            do {
                //FIXME: why pickup item twice??
                this.pickupItem((int) itemAgentIdAndDistance[0]);
                try {
                    //FIXMR: why is there a sleep needed?
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    // ignore
                }
                itemStillExists = this.getAgentExists((int) itemAgentIdAndDistance[0]);

                long startDeadlockCheck2 = System.currentTimeMillis();
                long currentlyElapsedTime2 = System.currentTimeMillis() - startDeadlockCheck2;

                if (currentlyElapsedTime2 > 5000) {
                    // try the next item
                    break;
                }

            } while (itemStillExists);

            itemsPicked++;
        } while (itemsPicked != numberOfItems);
    }

    /**
     * @param itemSlot
     * @param bag
     * @return the last modifier of the item and wchar_t* with customize text. Last modifier can be used to check which dye (model id 146) it is. If customize text = 0 then item is not customized.
     * @throws IOException 
     */
    public int[] getItemLastModifier(int itemSlot, BagLocation bag) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_LAST_MODIFIER, itemSlot, bag != null ? bag.getValue() : ZERO);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param itemId
     * @return the last modifier of the item and wchar_t* with customize text. Last modifier can be used to check which dye (model id 146) it is. If customize text = 0 then item is not customized.
     * @throws IOException 
     */
    public int[] getItemLastModifierById(int itemId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_LAST_MODIFIER_BY_ID, itemId);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param agentId
     * @return
     * @throws IOException 
     */
    public int[] getItemLastModifierByAgent(int agentId) throws IOException {
        GWCAPacket receivedGwcaPacket = sendAndReceivePacket(GWCAOperation.GET_ITEM_LAST_MODIFIER_BY_AGENT, agentId);
        return receivedGwcaPacket.getParamsAsIntArray();
    }

    /**
     * @param mapId
     * @param region
     * @param language
     * @throws IOException 
     */
    //TODO: enum for language
    public void zoneMapEx(int mapId, Region region, int language) throws IOException {
        this.prepareZoneMapEx(mapId);
        this.zoneMapEx(region, language);
    }

    /**
     * @param x
     * @param y
     * @param random
     * @throws IOException 
     */
    public void moveToEx(float x, float y, int random) throws IOException {
        LOG.debug("Moving to {} {} (and waiting until we reach it, until we're blocked or dead)");
        int blocked = 0;

        int status = this.getMapLoading();
        this.moveEx(x, y, random);
        pingSleep(0);

        float distanceToTarget = 0;
        do {
            try {
                Thread.sleep(150);
            } catch (InterruptedException ex) {
                // ignore
            }
            boolean isDead = this.isDead();
            if (isDead) {
                LOG.debug("The player is dead");
                break;
            }

            int oldStatus = status;
            status = this.getMapLoading();

            if (oldStatus != status) {
                break;
            }

            boolean isMoving = this.isMoving(-2);

            if (!isMoving) {
                LOG.debug("Blocked");
                blocked++;
                this.moveEx(x, y, random);
            }

            float[] newCoords = this.getCoords(-2);

            distanceToTarget = this.computeDistanceEx(newCoords[0], newCoords[1], x, y);
        } while (distanceToTarget > 220 && blocked < 14);
        
        LOG.debug("Moved");
    }

    /**
     * @param extra
     * @throws IOException
     * @throws InterruptedException 
     */
    public void pingSleep(long extra) throws IOException {
        int f = this.getPing();
        long timeToSleep = f + extra;
        LOG.debug("Time to sleep: {}", timeToSleep);
        try {
            Thread.sleep(timeToSleep);
        } catch (InterruptedException ex) {
            // ignore
        }
        LOG.debug("Finished sleeping");
    }

    /**-
     * @param skillSlot
     * @param targetId
     * @param delay
     * @throws IOException 
     */
    public void useSkillEx(SkillBarSkillSlot skillSlot, int targetId, int delay) throws IOException {
        LOG.debug("Using skill in slot {} on target {}", skillSlot, targetId);
        //FIXME: why isn't the delay used????
        if (this.isDead(-2)) {
            LOG.debug("The player is dead");
            return;
        }

        long startDeadlockCheck = System.currentTimeMillis();
        this.useSkill(skillSlot, targetId);

        int skillRecharge = 0;
        long elapsedTime = 0;
        do {
            if (this.isDead()) {
                LOG.debug("The player is dead");
                break;
            }

            int playerSkill = this.getSkill(-2);
            elapsedTime = System.currentTimeMillis() - startDeadlockCheck;
            if (playerSkill == 0 && elapsedTime > 1000) {
                break;
            }
            
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                // ignore
            }

            skillRecharge = this.getSkillRecharge(skillSlot);
        } while (skillRecharge == 0 || elapsedTime < 8000);
        try {
            Thread.sleep(250);
        } catch (InterruptedException ex) {
            // ignore
        }
        LOG.debug("Used skill on target");
    }

    /**
     * @param skillSlot
     * @return the amount of milliseconds before the skill will be recharged
     * @throws IOException 
     */
    public int getSkillRechargeTimeLeft(SkillBarSkillSlot skillSlot) throws IOException {
        int skillRecharge = this.getSkillRecharge(skillSlot);

        if (skillRecharge == 0) {
            return 0;
        }

        int timeStamp = this.getTimeStamp();

        return skillRecharge - timeStamp;
    }

    /**
     * @param heroSlot
     * @param skillSlot
     * @return the amount of milliseconds before the skill will be recharged
     * @throws IOException 
     */
    public int getHeroSkillRechargeTimeLeft(HeroSlot heroSlot, SkillBarSkillSlot skillSlot) throws IOException {
        int skillRecharge = this.getHeroSkillRecharge(heroSlot, skillSlot);

        if (skillRecharge == 0) {
            return 0;
        }

        int timeStamp = this.getTimeStamp();

        return skillRecharge - timeStamp;
    }
}
