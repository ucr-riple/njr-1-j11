package com.gesoftware.venta.math.vectors;

import com.gesoftware.venta.logging.LoggingUtility;

public final class VectorsTest {

    private static VecNr normalizationTest(final VecNr v) {
        final VecNr normalizedVector = VecNr.add(v, 1.0);
        normalizedVector.normalize();

        return normalizedVector;
    }

    public static void main(final String args[]) {
        LoggingUtility.setLoggingLevel(LoggingUtility.LoggingLevel.LEVEL_DEBUG);

        final VecNr i = new VecNr(1.0, 0.0, 0.0, 0.0);
        final VecNr j = new VecNr(0.0, 1.0, 0.0, 0.0);
        final VecNr k = new VecNr(0.0, 0.0, 1.0, 0.0);
        final VecNr w = new VecNr(0.0, 0.0, 0.0, 1.0);

        LoggingUtility.info("Basis");
        final VecNr basis[] = {i, j, k, w};
        for (final VecNr v : basis)
            LoggingUtility.info(v.toString());
        LoggingUtility.info("");

        LoggingUtility.info("Operations");
        LoggingUtility.info("      sum(i,j) = " + VecNr.add(i, j));
        LoggingUtility.info("     diff(i,j) = " + VecNr.diff(i, j));
        LoggingUtility.info(" multiply(i,j) = " + VecNr.multiply(i, j));
        LoggingUtility.info("      add(i,1) = " + VecNr.add(i, 1.0));
        LoggingUtility.info("");

        LoggingUtility.info("Complex operations");
        LoggingUtility.info("     sum(i,j,k) = " + VecNr.add(i, j, k));
        LoggingUtility.info("");

        LoggingUtility.info("Values");
        LoggingUtility.info("           dot(i,j) = " + VecNr.dot(i, j));
        LoggingUtility.info("            norm(i) = " + i.getNorm());
        LoggingUtility.info("normalize(add(i,1)) = " + normalizationTest(i));
        LoggingUtility.info("     norm(add(i,1)) = " + normalizationTest(i).getNorm());
        LoggingUtility.info("   norm(sum(i,j,k)) = " + VecNr.add(i, j, k).getNorm());
        LoggingUtility.info("");

        LoggingUtility.info("Transformations");
        LoggingUtility.info("toVecNi(i) = " + i.toVecNi());
    }
}