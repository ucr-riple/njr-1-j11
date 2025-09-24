package mi.common;

/**
 * User: goldolphin
 * Time: 2013-06-06 22:35
 */
public class Bitmap {
    private long[] longs;

    private static final int M = Long.SIZE;

    /**
     *
     * @param requestedCapacity in bits
     */
    public Bitmap(int requestedCapacity) {
        longs = new long[(requestedCapacity+M-1)/M];
    }

    public boolean get(int n) {
        return (longs[n/M] & (1 << (n%M))) != 0;
    }

    public void set(int n, boolean value) {
        if (value) {
            longs[n/M] |= 1 << (n%M);
        } else {
            longs[n/M] &= ~(1 << (n%M));
        }
    }

    public static void main(String[] args) {
        Bitmap map = new Bitmap(256*256);
        char c = '我';
        System.out.println((int)'们');
        System.out.println(map.get(c));
        map.set(c, true);
        System.out.println(map.get(c));
        map.set(c, false);
        System.out.println(map.get(c));
    }
}
