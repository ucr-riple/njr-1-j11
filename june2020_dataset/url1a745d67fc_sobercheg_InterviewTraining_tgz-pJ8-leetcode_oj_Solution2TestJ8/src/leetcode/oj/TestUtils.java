package leetcode.oj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Sobercheg on 2/2/14.
 */
public class TestUtils {
    public static void assertEquals(Object expected, Object actual) {
        if (checkNulls(expected, actual)) return;
        if (!expected.equals(actual))
            throw new IllegalStateException(String.format("Expected [%s] is not equal to actual [%s]", expected, actual));
    }

    public static void assertEquals(int[] expected, int[] actual) {
        if (checkNulls(expected, actual)) return;
        assertEquals(expected, actual, Math.max(expected.length, actual.length));
    }

    public static void assertEquals(int[] expected, int[] actual, int size) {
        for (int i = 0; i < size; i++) {
            if (actual.length <= i || expected.length <= i || expected[i] != actual[i])
                throw new IllegalStateException(String.format("Expected [%s] is not equal to actual [%s]",
                        Arrays.toString(expected), Arrays.toString(actual)));
        }
    }

    public static void assertEquals(String[] expected, String[] actual) {
        if (checkNulls(expected, actual)) return;
        if (!Arrays.equals(expected, actual))
            throw new IllegalStateException(String.format("Expected [%s] is not equal to actual [%s]",
                    Arrays.toString(expected), Arrays.toString(actual)));
    }

    public static void assertArrayEquals(char[][] expected, char[][] actual) {
        if (checkNulls(expected, actual)) return;
        if (!Arrays.deepEquals(expected, actual))
            throw new IllegalStateException(String.format("Expected [%s] is not equal to actual [%s]",
                    Arrays.deepToString(expected), Arrays.deepToString(actual)));
    }

    public static void assertArrayEquals(int[][] expected, int[][] actual) {
        if (checkNulls(expected, actual)) return;
        if (!Arrays.deepEquals(expected, actual))
            throw new IllegalStateException(String.format("Expected [%s] is not equal to actual [%s]",
                    Arrays.deepToString(expected), Arrays.deepToString(actual)));
    }

    private static boolean checkNulls(Object expected, Object actual) {
        if (expected == null && actual == null) return true;
        if (expected == null) throw new IllegalStateException("Actual is not null");
        if (actual == null) throw new IllegalStateException("Expected is not null");
        return false;
    }

    public static <T> ArrayList<T> arrayListOf(T elem1, T... elements) {
        ArrayList<T> arrayList = new ArrayList<T>();
        arrayList.add(elem1);
        Collections.addAll(arrayList, elements);
        return arrayList;
    }

    public static <T> ArrayList<T> arrayListOf() {
        return new ArrayList<T>();
    }

    public static <T> ArrayList<ArrayList<T>> arrayListOf(ArrayList<T> elem1) {
        ArrayList<ArrayList<T>> arrayLists = new ArrayList<ArrayList<T>>();
        arrayLists.add(elem1);
        return arrayLists;
    }

    public static <T> ArrayList<ArrayList<T>> arrayListOf(ArrayList<T> elem1, ArrayList<T> elem2) {
        ArrayList<ArrayList<T>> arrayLists = arrayListOf(elem1);
        arrayLists.add(elem2);
        return arrayLists;
    }

    public static <T> ArrayList<ArrayList<T>> arrayListOf(ArrayList<T> elem1, ArrayList<T> elem2, ArrayList<T> elem3) {
        ArrayList<ArrayList<T>> arrayLists = arrayListOf(elem1, elem2);
        arrayLists.add(elem3);
        return arrayLists;
    }

    public static <T> ArrayList<ArrayList<T>> arrayListOf(ArrayList<T> elem1, ArrayList<T> elem2, ArrayList<T> elem3, ArrayList<T> elem4) {
        ArrayList<ArrayList<T>> arrayLists = arrayListOf(elem1, elem2, elem3);
        arrayLists.add(elem4);
        return arrayLists;
    }

    public static <T> ArrayList<ArrayList<T>> arrayListOf(ArrayList<T> elem1, ArrayList<T> elem2, ArrayList<T> elem3, ArrayList<T> elem4, ArrayList<T> elem5) {
        ArrayList<ArrayList<T>> arrayLists = arrayListOf(elem1, elem2, elem3, elem4);
        arrayLists.add(elem5);
        return arrayLists;
    }

    public static <T> ArrayList<ArrayList<T>> arrayListOf(ArrayList<T> elem1, ArrayList<T> elem2, ArrayList<T> elem3, ArrayList<T> elem4, ArrayList<T> elem5, ArrayList<T> elem6) {
        ArrayList<ArrayList<T>> arrayLists = arrayListOf(elem1, elem2, elem3, elem4, elem5);
        arrayLists.add(elem6);
        return arrayLists;
    }

    public static <T> ArrayList<ArrayList<T>> arrayListOf(ArrayList<T> elem1, ArrayList<T> elem2, ArrayList<T> elem3, ArrayList<T> elem4, ArrayList<T> elem5, ArrayList<T> elem6, ArrayList<T> elem7) {
        ArrayList<ArrayList<T>> arrayLists = arrayListOf(elem1, elem2, elem3, elem4, elem5, elem6);
        arrayLists.add(elem7);
        return arrayLists;
    }
}
