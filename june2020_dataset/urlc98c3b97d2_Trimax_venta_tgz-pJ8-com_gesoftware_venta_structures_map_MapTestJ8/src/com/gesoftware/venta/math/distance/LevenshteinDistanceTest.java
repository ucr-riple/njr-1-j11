package com.gesoftware.venta.math.distance;

import com.gesoftware.venta.logging.LoggingUtility;

public final class LevenshteinDistanceTest {
    private final static LevenshteinDistance c_Metrics = new LevenshteinDistance();

    private static double getDistance(final String string1, final String string2) {
        return c_Metrics.getDistance(string1, string2);
    }

    private static double longestStringLength(final String string1, final String string2) {
        return java.lang.Math.max(string1.length(), string2.length());
    }

    private static double getRelativeDistance(final String string1, final String string2) {
        return getDistance(string1, string2) / longestStringLength(string1, string2);
    }

    private static void computeDistance(final String string1, final String string2) {
        LoggingUtility.info("Distance between <" + string1 + "> and <" + string2 + "> = " + getDistance(string1, string2) + "; [" + getRelativeDistance(string1, string2) + "%]");
    }

    public static void main(final String args[]) {
        LoggingUtility.setLoggingLevel(LoggingUtility.LoggingLevel.LEVEL_DEBUG);

        final String strings[] = {"cat", "dog", "cut", "test", "set", "god"};
        for (final String string1 : strings)
            for (final String string2 : strings)
                computeDistance(string1, string2);
    }
}