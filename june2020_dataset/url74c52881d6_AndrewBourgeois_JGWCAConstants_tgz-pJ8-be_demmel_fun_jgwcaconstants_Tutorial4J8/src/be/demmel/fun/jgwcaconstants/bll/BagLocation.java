package be.demmel.fun.jgwcaconstants.bll;

public enum BagLocation {
    BACKPACK((byte)1), BELT_POUCH((byte)2), BAG1((byte)3), BAG2((byte)4), EQUIPMENT_PACK((byte)5), UNCLAIMED_ITEMS((byte)7), 
    STORAGE_1((byte)8), STORAGE_2((byte)9), STORAGE_3((byte)10), STORAGE_4((byte)11), STORAGE_5((byte)12), STORAGE_6((byte)13),
    STORAGE_7((byte)14), STORAGE_8((byte)15), STORAGE_ANNIVERSARY((byte)16);
    
    private final byte value;

    private BagLocation(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
