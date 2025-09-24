package sitsiplaseeraus.random;

/**
 * Hoitaa yhtä asiaa: luo halutulla tavalla rajattuja satunnaislukuja
 */
public class Random {
    static int luo;
    
    /**
     * Palauttaa ylärajatun satunnaisluvun
     * @param max yläraja
     * @return kokonaisluku väliltä 0-max
     */
    public static int luo(int max) {
        return (int) (Math.random() * (max + 1));
    }

    /**
     * Palauttaa ylä- ja alarajatun satunnaisluvun
     * @param max yläraja
     * @param min alaraja
     * @return Kokonaisluku väliltä min - max
     */
    public static int luo(int max, int min) {
        return min + (int)(Math.random() * (max - min + 1));
    }
}
