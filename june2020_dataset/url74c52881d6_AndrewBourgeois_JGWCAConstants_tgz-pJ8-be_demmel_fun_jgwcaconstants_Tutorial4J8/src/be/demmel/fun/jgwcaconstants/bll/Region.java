package be.demmel.fun.jgwcaconstants.bll;

public enum Region {
    INTERNATIONAL((byte) -2), AMERICA((byte) 0), ASIA_KOREAN((byte) 1), EUROPE((byte) 2), ASIA_CHINESE((byte) 3), ASIA_JAPANESE((byte) 4);
        
    private final byte value;

    private Region(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
