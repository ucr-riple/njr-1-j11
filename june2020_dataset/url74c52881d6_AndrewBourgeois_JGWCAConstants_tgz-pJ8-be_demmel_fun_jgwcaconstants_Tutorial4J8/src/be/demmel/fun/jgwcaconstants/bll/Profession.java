package be.demmel.fun.jgwcaconstants.bll;

public enum Profession {
    NONE((byte)0), WARRIOR((byte)1), RANGER((byte)2), MONK((byte)3), NECROMANCER((byte)4),
    MESMER((byte)5), ELEMENTALIST((byte)6), ASSASSIN((byte)7), RITUALIST((byte)8),
    PARAGON((byte)9), DERVISH((byte)10);
    private final byte value;

    private Profession(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
