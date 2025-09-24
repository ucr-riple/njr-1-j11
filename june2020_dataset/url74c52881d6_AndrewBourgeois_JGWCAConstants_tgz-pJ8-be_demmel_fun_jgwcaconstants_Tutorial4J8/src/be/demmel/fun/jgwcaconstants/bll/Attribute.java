package be.demmel.fun.jgwcaconstants.bll;

public enum Attribute {
    FAST_CASTING((byte)0), ILLUSION_MAGIC((byte)1), DOMINATION_MAGIC((byte)2), INSPIRATION_MAGIC((byte)3),
    BLOOD_MAGIC((byte)4), DEATH_MAGIC((byte)5), SOUL_REAPING((byte)6), CURSES((byte)7),
    AIR_MAGIC((byte)8), EARTH_MAGIC((byte)9), FIRE_MAGIC((byte)10), WATER_MAGIC((byte)11),
    ENERGY_STORAGE((byte)12), HEALING_PRAYERS((byte)13), SMITING_PRAYERS((byte)14), PROTECTION_PRAYERS((byte)15),
    DIVINE_FAVOR((byte)16), STRENGTH((byte)17), AXE_MASTERY((byte)18), HAMMER_MASTERY((byte)19),
    SWORDSMANSHIP((byte)20), TACTICS((byte)21), BEAST_MASTERY((byte)22), EXPERTISE((byte)23),
    WILDERNESS_SURVIVAL((byte)24), MARKSMANSHIP((byte)25), DAGGER_MASTERY((byte)29), DEADLY_ARTS((byte)30),
    SHADOW_ARTS((byte)31), COMMUNING((byte)32),
    RESTORATION_MAGIC((byte)33), CHANNELING_MAGIC((byte)34),
    CRITICAL_STRIKES((byte)35), SPAWNING_POWER((byte)36),
    SPEAR_MASTERY((byte)37), COMMAND((byte)38),
    MOTIVATION((byte)39), LEADERSHIP((byte)40),
    SCYTHE_MASTERY((byte)41), WIND_PRAYERS((byte)42),
    EARTH_PRAYERS((byte)43), MYSTICISM((byte)44),;
  
    private final byte value;

    private Attribute(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
