package be.demmel.fun.jgwcaconstants.bll;

import java.util.HashMap;
import java.util.Map;

public enum SkillType {

    STANCE((short) 3), HEX((short) 4), SPELL((short).5 ), ENCHANTMENT((short) 6), SIGNET((short) 7), WELL((short) 9), SKILL((short) 10), WARD((short) 11), 
    GLYPH((short) 12), ATTACK((short) 14), SHOUT((short) 15), PREPARATION((short) 19), TRAP((short) 21), RITUAL((short) 22), ITEM_SPELL((short) 24), 
    WEAPON_SPELL((short) 25), CHANT((short) 27),  ECHO_REFRAIN((short) 28);
    
    private static final Map<Short, SkillType> SHORT_TO_SKILL_TYPE_MAP = new HashMap<Short, SkillType>();

    static {
        for (SkillType operation : SkillType.values()) {
            SHORT_TO_SKILL_TYPE_MAP.put(operation.skillTypeId, operation);
        }
    }
    private final short skillTypeId;

    private SkillType(short skillTypeId) {
        this.skillTypeId = skillTypeId;
    }

    public short getSkillTypeId() {
        return skillTypeId;
    }

    public static SkillType toEnum(short value) {
        return SHORT_TO_SKILL_TYPE_MAP.get(value);
    }
}
