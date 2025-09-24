package other;

/**
 * Created by sobercheg on 12/4/13.
 */
public class HanoiTowers {

    public static void move(int n, int from, int to, int by) {
        if (n == 0) return;
        move(n - 1, from, by, to);
        System.out.println("Moving from " + from + " to " + to + " disk " + n);
        move(n - 1, by, to, from);
    }

    public static void main(String[] args) {
        move(4, 1, 2, 3);
    }
}
