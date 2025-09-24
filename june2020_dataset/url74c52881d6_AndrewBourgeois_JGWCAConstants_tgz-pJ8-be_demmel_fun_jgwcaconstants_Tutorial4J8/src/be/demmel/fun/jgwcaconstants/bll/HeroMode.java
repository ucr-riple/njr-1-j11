package be.demmel.fun.jgwcaconstants.bll;

public enum HeroMode {
    FIGHT((byte)0), GUARD((byte)1), AVOID((byte)2);
    
    private final byte value;

    private HeroMode(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
