package mi.common;

/**
 * User: goldolphin
 * Time: 2013-06-15 06:47
 */
public final class Utils {
    public static void performance(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Time elapsed: " + elapsed + "ms");
    }

    public static void verify(boolean condition) {
        if (!condition) {
            throw new RuntimeException();
        }
    }
}
