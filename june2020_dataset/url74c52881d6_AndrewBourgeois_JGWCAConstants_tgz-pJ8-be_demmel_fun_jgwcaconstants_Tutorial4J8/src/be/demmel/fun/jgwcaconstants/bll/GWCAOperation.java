package be.demmel.fun.jgwcaconstants.bll;

import java.util.HashMap;
import java.util.Map;

public enum GWCAOperation {
    //TODO later: 
    // CA_SetLogAndHwnd, CA_SetEquipmentAgent, CA_SetEquipmentModelId, CA_SetEquipmentDye, CA_SetEquipmentShinyness, CA_GetAgentPtr
    // CA_GetAgentMovementPtr, CA_GetEffects
    // CA_GetName, CA_SendChat, CA_WriteWhisper, CA_GetPartyInfo, CA_ClearPacketQueue, CA_AllocMem, CA_FreeMem
    // CA_GetMapBoundariesPtr

    // Obsolete: CA_GetSpiritRange, CA_GetCombatMode, CA_GetModelMode, CA_GetEnergy,
    // CA_GetNameProperties, CA_GetModelState, CA_GetIsCasting, CA_GetIsAttackedMelee, CA_SalvageItem
    // CA_MoveOld, CA_SetEventSkillMode
    MOVE((short) 265), GET_PING((short) 810), PREPARE_ZONE_MAP_EX((short) 355), ZONE_MAP_EX((short) 356), GET_MAP_ID((short) 807),
    GET_REGION_AND_LANGUAGE((short) 809), GET_CURRENT_TARGET((short) 770), GET_MY_ID((short) 771), CASTING((short) 776), SKILL_RECHARGE((short) 777), SKILL_ADRENALINE((short) 778),
    GET_AGENT_AND_TARGET_POINTERS((short) 781), GET_SKILLBAR_SKILL_ID((short) 779), GET_MY_MAX_HP((short) 772), GET_MY_MAX_ENERGY((short) 773),
    GET_BUILD_NUMBER((short) 782), CHANGE_MAX_ZOOM((short) 783), GET_LAST_DIALOG_ID((short) 784), SET_ENGINE_HOOK((short) 785),
    GET_HERO_CASTING((short) 935), GET_HERO_SKILL_RECHARGE((short) 936), GET_HERO_SKILL_ADRENALINE((short) 937), GET_HERO_SKILL_ID((short) 938),
    GET_HERO_AGENT_ID((short) 939), GET_MAP_LOADING((short) 806), GET_LOGGED_IN((short) 811), GET_DEAD((short) 812), GET_BALTHAZAR_FACTION((short) 813),
    GET_KURZICK_FACTION((short) 814), GET_LUXON_FACTION((short) 815), GET_TITLE_TREASURE((short) 816), GET_TITLE_LUCKY((short) 817), GET_TITLE_UNLUCKY((short) 818),
    GET_TITLE_GAMER((short) 820), GET_EXPERIENCE((short) 821), GET_CONNECTION((short) 900), GET_MATCH_STATUS((short) 919), GET_TITLE_SUNSPEAR((short) 822),
    GET_TITLE_LIGHTBRINGER((short) 823), GET_TITLE_VANGUARD((short) 824), GET_TITLE_NORN((short) 825), GET_TITLE_ASURA((short) 826), GET_TITLE_DELDRIMOR((short) 827),
    GET_TITLE_NORTH_MASTERY((short) 828), GET_TITLE_DRUNKARD((short) 829), GET_TITLE_SWEET((short) 830), GET_TITLE_PARTY((short) 831), GET_TITLE_COMMANDER((short) 832),
    GET_EQUIPMENT_MODEL_ID((short) 926), GET_EQUIPMENT_DYE_INFO((short) 927), GET_EFFECT_COUNT((short) 911), GET_EFFECT((short) 912), GET_EFFECT_BY_INDEX((short) 913),
    GET_EFFECT_DURATION((short) 914), GET_AGENT_EXIST((short) 839), GET_PROFESSIONS((short) 840), GET_PLAYER_NUMBER((short) 841), GET_HP((short) 843), GET_ROTATION((short) 844),
    GET_SKILL((short) 845), GET_COORDS((short) 846), GET_WEAPON_SPEEDS((short) 847), GET_TEAM_ID((short) 849), GET_HP_PIPS((short) 852), GET_HEX((short) 854),
    GET_MODEL_ANIMATION((short) 855), GET_TYPE((short) 858), GET_LEVEL((short) 859), GET_MAX_ID((short) 861), GET_MY_NEAREST_AGENT((short) 774), GET_MY_DISTANCE_TO_AGENT((short) 775),
    GET_NEAREST_AGENT_TO_AGENT((short) 877), GET_DISTANCE_FROM_AGENT_TO_AGENT((short) 878), GET_NEAREST_AGENT_TO_AGENT_EX((short) 879), GET_IS_ATTACKING((short) 866),
    GET_IS_KNOCKED_DOWN((short) 867), GET_IS_MOVING((short) 868), GET_IS_DEAD((short) 869), GET_FIRST_AGENT_BY_PLAYER_NUMBER((short) 876), GET_ALLEGIANCE((short) 863),
    GET_NEAREST_ENEMY_TO_AGENT_EX((short) 880), GET_NEAREST_ITEM_TO_AGENT_EX((short) 881), GET_NEAREST_AGENT_BY_PLAYER_NUMBER((short) 882), GET_SPEED((short) 862),
    GET_NEAREST_ENEMY_TO_AGENT_BY_ALLEGIANCE((short) 883), GET_NEAREST_ALIVE_ENEMY_TO_AGENT((short) 884), GET_WEAPON_TYPE((short) 864), GET_NEAREST_SIGNPOST_TO_AGENT((short) 885),
    GET_NEAREST_NPC_TO_AGENT_BY_ALLEGIANCE((short) 886), GET_NEAREST_AGENT_TO_COORDS((short) 887), GET_NEAREST_NPC_TO_COORDS((short) 888), GET_LOGIN_NUMBER((short) 889),
    GET_NUMBER_OF_AGENTS_BY_PLAYER_NUMBER((short) 890), GET_NUMBER_OF_ALIVE_ENEMY_AGENTS((short) 891), GET_NEXT_ITEM((short) 892), GET_TARGET((short) 780), SET_ATTRIBUTE((short) 273),
    RESET_ATTRIBUTES((short) 346), PLAYER_HAS_BUFF((short) 872), HERO1_HAS_BUFF((short) 873), HERO2_HAS_BUFF((short) 874), HERO3_HAS_BUFF((short) 875), GET_AGENT_DANGER((short) 916),
    GET_TYPE_MAP((short) 917), GET_AGENT_WEAPONS((short) 918), GET_NEXT_AGENT((short) 920), GET_NEXT_ALLY((short) 921), GET_NEXT_FOE((short) 922), GET_EXTRA_TYPE((short) 928),
    PREPARE_NEAREST_PLAYER_NUMBER_TO_COORDS((short) 929), GET_NEAREST_PLAYER_NUMBER_TO_COORDS((short) 930), GET_FIRST_AGENT_BY_PLAYER_NUMBER_BY_TEAM((short) 932),
    GET_NEAREST_ALIVE_ENEMY_TO_COORDS((short) 933), GET_NEXT_ALIVE_FOE((short) 934), GET_NEAREST_ALIVE_AGENT_BY_PLAYER_NUMBER((short) 942), GET_GOLD((short) 786),
    GET_BAG_SIZE((short) 787), SET_BAG((short) 316), GET_ITEM_ID((short) 788), GET_ID_KIT((short) 789), IDENTIFY_ITEM((short) 319), IDENTIFY_ITEM_BY_ID((short) 320),
    DEPOSIT_GOLD((short) 281), WITHDRAW_GOLD((short) 282), SELL_ITEM((short) 322), SELL_ITEM_BY_ID((short) 323), BUY_ID_KIT((short) 324), BUY_SUPERIOR_ID_KIT((short) 325),
    GET_ITEM_INFO((short) 791), USE_ITEM((short) 278), USE_ITEM_BY_ID((short) 279), DROP_ITEM((short) 335), DROP_ITEM_BY_ID((short) 336), ACCEPT_ALL_ITEMS((short) 333),
    FIND_ITEM_BY_MODEL_ID((short) 794), FIND_EMPTY_SLOT((short) 795), FIND_GOLD_ITEM((short) 796), GET_ITEM_POSITION_BY_ITEM_ID((short) 797), GET_ITEM_POSITION_BY_MODEL_ID((short) 798),
    GET_ITEM_POSITION_BY_RARITY((short) 799), GET_ITEM_MODEL_ID_BY_ID((short) 800), GET_ITEM_INFO_BY_ID((short) 801), EQUIP_ITEM((short) 276), EQUIP_ITEM_BY_ID((short) 277),
    GET_SALVAGE_KIT((short) 790), BUY_ITEM((short) 326), GET_ITEM_ID_BY_AGENT((short) 802), GET_ITEM_INFO_BY_AGENT((short) 803), GET_NEAREST_ITEM_BY_MODEL_ID((short) 805),
    GET_ITEM_EXTRA_ID((short) 898), GET_ITEM_EXTRA_ID_BY_ID((short) 899), GET_ITEM_EXTRA_ID_BY_AGENT((short) 901), GET_ITEM_REQ((short) 902), GET_ITEM_REQ_BY_ID((short) 903),
    GET_ITEM_REQ_BY_AGENT((short) 904), GET_DYE_POSITION_BY_COLOR((short) 905), GET_ITEM_DMG_MOD((short) 923), GET_ITEM_DMG_MOD_BY_ID((short) 924), GET_ITEM_DMG_MOD_BY_AGENT((short) 925),
    SUBMIT_OFFER((short) 341), CHANGE_OFFER((short) 342), OFFER_ITEM((short) 343), CANCEL_TRADE((short) 344), ACCEPT_TRADE((short) 345), ATTACK((short) 258), USE_SKILL((short) 270),
    CHANGE_WEAPON_SET((short) 275), ZONE_MAP((short) 303), DROP_GOLD((short) 280), GO_NPC((short) 266), GO_PLAYER((short) 267), GO_SIGN_POST((short) 268), ENTER_CHALLENGE((short) 313),
    ENTER_CHALLENGE_FOREIGN((short) 354), OPEN_CHEST((short) 332), PICKUP_ITEM((short) 334), DIALOG((short) 259), CHANGE_TARGET((short) 283), TARGET_NEAREST_FOE((short) 284),
    TARGET_NEAREST_ALLY((short) 285), TARGET_NEAREST_ITEM((short) 288), TARGET_CALLED_TARGET((short) 289), USE_HERO1_SKILL((short) 290), USE_HERO2_SKILL((short) 291),
    USE_HERO3_SKILL((short) 292), CANCEL_ACTION((short) 260), COMMAND_HERO1((short) 293), COMMAND_HERO2((short) 294), COMMAND_HERO3((short) 295), COMMAND_ALL((short) 296),
    CHANGE_DISTRICT((short) 302), RESIGN((short) 311), UPDATE_AGENT_POSITION((short) 338), RETURN_TO_OUTPOST((short) 312), GO_AGENT((short) 269), DONATE_FACTION((short) 263),
    SET_SKILLBAR_SKILL((short) 272), CHANGE_SECOND_PROFESSION((short) 274), TARGET_NEXT_PARTY_MEMBER((short) 286), TARGET_NEXT_FOE((short) 284), SKIP_CINEMATIC((short) 306),
    DISMISS_BUFF((short) 307), ADD_HERO((short) 298), KICK_HERO((short) 299), SWITCH_MODE((short) 304), ADD_NPC((short) 300), KICK_NPC((short) 301),
    TRAVEL_GH((short) 314), LEAVE_GH((short) 315), INIT_MAP_LOAD((short) 305), MAP_IS_LOADED((short) 808), GET_MAP_OVERLAY_COORDS((short) 835), GET_MAP_OVERLAY_INFO((short) 836),
    GET_NEAREST_MAP_OVERLAY_TO_COORDS((short) 837), SET_HERO_MODE((short) 297), QUEST_CHECK((short) 893), QUEST_COORDS((short) 894), QUEST_ACTIVE((short) 895),
    QUEST_ABANDON((short) 262), SET_TEAM_SIZE((short) 309), TRADER_REQUEST((short) 327), TRADER_CHECK((short) 897), TRADER_BUY((short) 330),
    TRADER_REQUEST_SELL((short) 328), TRADER_REQUEST_SELL_BY_ID((short) 329), TRADER_SELL((short) 331), GET_NUMBER_OF_FOES_IN_RANGE_OF_AGENT((short) 906),
    GET_NUMBER_OF_ALLIES_IN_RANGE_OF_AGENT((short) 907), GET_NUMBER_OF_ITEMS_IN_RANGE_OF_AGENT((short) 908), TRADE_PLAYER((short) 340), GET_TIME_STAMP((short) 915),
    LOCK_HERO((short) 352), CANCEL_MAINTAINED_ENCHANTMENT((short) 353), GET_SKILL_TYPE((short) 931), OPEN_STORAGE((short) 337), GET_NEAREST_ALIVE_ENEMY_TO_COORDS_BY_PLAYER_NUMBER((short) 941),
    PREPARE_NEAREST_PLAYER_NUMBER_TO_COORDS_BY_PLAYER_NUMBER((short) 940), PREPARE_MOVE_ITEM((short) 317), MOVE_ITEM((short) 318), GET_ITEM_LAST_MODIFIER((short) 792),
    GET_ITEM_LAST_MODIFIER_BY_ID((short) 793), GET_ITEM_LAST_MODIFIER_BY_AGENT((short) 804);
    private static final Map<Short, GWCAOperation> SHORT_TO_OPERATION_MAP = new HashMap<Short, GWCAOperation>();

    static {
        for (GWCAOperation operation : GWCAOperation.values()) {
            SHORT_TO_OPERATION_MAP.put(operation.operationId, operation);
        }
    }
    private final short operationId;

    private GWCAOperation(short operationId) {
        this.operationId = operationId;
    }

    public short getOperationId() {
        return operationId;
    }

    public static GWCAOperation toEnum(short value) {
        return SHORT_TO_OPERATION_MAP.get(value);
    }
}
