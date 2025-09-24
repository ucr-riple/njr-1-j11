package mi.common;

import mi.common.Bitmap;

/**
 * User: goldolphin
 * Time: 2013-06-07 23:13
 */
public class CharacterSet {
    private static final int MAX_NUM = 256*256;
    private Bitmap[] maps;
    private final int slotSize;

    public CharacterSet() {
        this(32);
    }

    public CharacterSet(int slotNum) {
        maps = new Bitmap[slotNum];
        slotSize = (MAX_NUM + slotNum - 1)/slotNum;
    }

    public void add(char c) {
        int n = c/slotSize;
        if (maps[n] == null) {
            maps[n] = new Bitmap(slotSize);
        }
        maps[n].set(c%slotSize, true);
    }

    public void remove(char c) {
        int n = c/slotSize;
        if (maps[n] == null) {
            return;
        }
        maps[n].set(c%slotSize, false);
    }

    public boolean contains(char c) {
        int n = c/slotSize;
        if (maps[n] == null) {
            return false;
        }
        return maps[n].get(c%slotSize);
    }
}
