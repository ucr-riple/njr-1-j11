package be.demmel.fun.jgwcaconstants.bll;

import java.util.HashMap;
import java.util.Map;

public enum WeaponType {

    BOW((short) 1), AXE((short) 2), HAMMER((short) 3), DAGGERS((short) 4), SCYTHE((short) 5), SPEAR((short) 6), SWORD((short) 7), WAND((short) 10);
        
    private static final Map<Short, WeaponType> SHORT_TO_WEAPON_TYPE_MAP = new HashMap<Short, WeaponType>();

    static {
        for (WeaponType operation : WeaponType.values()) {
            SHORT_TO_WEAPON_TYPE_MAP.put(operation.skillTypeId, operation);
        }
    }
    private final short skillTypeId;

    private WeaponType(short skillTypeId) {
        this.skillTypeId = skillTypeId;
    }

    public short getSkillTypeId() {
        return skillTypeId;
    }

    public static WeaponType toEnum(short value) {
        return SHORT_TO_WEAPON_TYPE_MAP.get(value);
    }
}
