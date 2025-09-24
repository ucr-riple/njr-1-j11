package be.demmel.fun.jgwcaconstants.bll;

public enum Hero {
    NORGU((byte)1), GOREN((byte) 2), TAHLIKORA((byte)3), MASTER_OF_WHISPERS((byte)4), ACOLYTE_JIN((byte)5), KOSS((byte)6), DUNKORO((byte)7), ACOLYTE_SOUSUKE((byte)8),
    MELONNI((byte)9), ZHED_SHADOWHOOF((byte)10), GENERAL_MORGAHN((byte)11), MARGRID_THE_SLY((byte)12), OLIAS((byte)14), RAZAH((byte)15), MOX((byte)16),
    JORA((byte)18), PYRE_FIERCESHOT((byte)19), LIVIA((byte)21), HAYDA((byte)22), KAHMU((byte)23), GWEN((byte)24), XANDRA((byte)25), VEKK((byte)26), OGDEN((byte)27);
    
    private final byte value;

    private Hero(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
