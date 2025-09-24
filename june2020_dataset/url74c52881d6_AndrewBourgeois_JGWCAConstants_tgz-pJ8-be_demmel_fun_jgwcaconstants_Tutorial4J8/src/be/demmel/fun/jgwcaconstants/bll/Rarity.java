package be.demmel.fun.jgwcaconstants.bll;

public enum Rarity {
    WHITE((byte)0x3D), BLUE((byte)0x3F), PURPLE((byte)0x42), GOLD((byte)0x40), GREEN((byte)0x43);
    
    private final byte value;

    private Rarity(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
