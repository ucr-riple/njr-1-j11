package com.gesoftware.venta.utility;

import com.gesoftware.venta.logging.LoggingUtility;
import java.util.*;

public final class CollectionUtilityTest {
    private final static String c_Collection1[] = {"R", "G", "B"};
    private final static String c_Collection2[] = {"B", "G", "R"};
    private final static String c_Collection3[] = {"X", "Y", "Z"};
    private final static String c_Collection4[] = {"One element"};

    private final static Collection<String> list       = new LinkedList<String>(Arrays.asList(c_Collection1));
    private final static Collection<String> set        = new HashSet<String>(Arrays.asList(c_Collection2));
    private final static Collection<String> array      = new ArrayList<String>(Arrays.asList(c_Collection3));
    private final static Collection<String> collection = new TreeSet<String>(Arrays.asList(c_Collection4));

    private final static Collection c_Collections[] = {list, set, array, collection};

    private static void equalityTests() {
        LoggingUtility.info("Collection 1 = " + Arrays.toString(c_Collection1));
        LoggingUtility.info("Collection 2 = " + Arrays.toString(c_Collection2));
        LoggingUtility.info("Collection 3 = " + Arrays.toString(c_Collection3));
        LoggingUtility.info("Collection 4 = " + Arrays.toString(c_Collection4));

        for (int i = 0; i < c_Collections.length; i++)
            for (int j = 0; j < c_Collections.length; j++)
                LoggingUtility.info("Collections (" + (i+1) + ", " + (j+1) + ") equality: " + CollectionUtility.equals(c_Collections[i], c_Collections[j]) + "; Ignoring order: " + CollectionUtility.equalsIgnoreOrder(c_Collections[i], c_Collections[j]));
    }

    private static void sortingTests() {
        LoggingUtility.info("Sorted collection 1 = " + CollectionUtility.sort(list));
        LoggingUtility.info("Sorted collection 2 = " + CollectionUtility.sort(set));
        LoggingUtility.info("Sorted collection 3 = " + CollectionUtility.sort(array));
        LoggingUtility.info("Sorted collection 4 = " + CollectionUtility.sort(collection));

        LoggingUtility.info("Original collection 1 = " + list);
        LoggingUtility.info("Original collection 2 = " + set);
        LoggingUtility.info("Original collection 3 = " + array);
        LoggingUtility.info("Original collection 4 = " + collection);
    }

    public static void main(final String args[]) {
        LoggingUtility.setLoggingLevel(LoggingUtility.LoggingLevel.LEVEL_DEBUG);

        equalityTests();
        sortingTests();
    }
}
