package hep.io.root.output;

/**
 * Utility for computing root checksums.
 * @author tonyj
 */
class Checksum {

    private int id = 0;

    void compute(String string) {
        for (int i = 0; i < string.length(); i++) {
            compute(string.charAt(i));
        }
    }
    void compute(int i) {
        id = id * 3 + i;
    }

    int getValue() {
        return id;
    }
}
