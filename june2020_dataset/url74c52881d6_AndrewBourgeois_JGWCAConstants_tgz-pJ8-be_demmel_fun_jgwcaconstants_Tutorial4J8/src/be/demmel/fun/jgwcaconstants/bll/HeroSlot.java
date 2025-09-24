package be.demmel.fun.jgwcaconstants.bll;

public enum HeroSlot {
    ONE((byte)1), TWO((byte)2), THREE((byte)3), FOUR((byte)4), FIVE((byte)5), SIX((byte)6), SEVEN((byte)7);
    
    private final byte value;

    private HeroSlot(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
