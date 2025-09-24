package be.demmel.fun.jgwcaconstants.bll;

public enum Dye {
    BLUE((byte)2), GREEN((byte)3), PURPLE((byte)4), RED((byte)5), YELLOW((byte)6), 
    BROWN((byte)7), ORANGE((byte)8), SILVER((byte)9), BLACK((byte)10), 
    GRAY((byte)11), WHITE((byte)12);
    
    private final byte value;

    private Dye(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
