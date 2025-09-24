package fw.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Converter {

  public static <T> Collection<T> toCollection(final T[] array) {
    return Arrays.asList(array);
  }

  public static Collection<Byte> toCollection(final byte[] array) {
    final ArrayList<Byte> collection = new ArrayList<Byte>();

    for (final byte value : array) {
      collection.add(Byte.valueOf(value));
    }

    return collection;
  }

  public static Collection<Boolean> toCollection(final boolean[] array) {
    final ArrayList<Boolean> collection = new ArrayList<Boolean>();

    for (final boolean value : array) {
      collection.add(Boolean.valueOf(value));
    }

    return collection;
  }

  public static Collection<Character> toCollection(final char[] array) {
    final ArrayList<Character> collection = new ArrayList<Character>();

    for (final char value : array) {
      collection.add(Character.valueOf(value));
    }

    return collection;
  }

  public static Collection<Short> toCollection(final short[] array) {
    final ArrayList<Short> collection = new ArrayList<Short>();

    for (final short value : array) {
      collection.add(Short.valueOf(value));
    }

    return collection;
  }

  public static Collection<Integer> toCollection(final int[] array) {
    final ArrayList<Integer> collection = new ArrayList<Integer>();

    for (final int value : array) {
      collection.add(Integer.valueOf(value));
    }

    return collection;
  }

  public static Collection<Long> toCollection(final long[] array) {
    final ArrayList<Long> collection = new ArrayList<Long>();

    for (final long value : array) {
      collection.add(Long.valueOf(value));
    }

    return collection;
  }

  public static Collection<Float> toCollection(final float[] array) {
    final ArrayList<Float> collection = new ArrayList<Float>();

    for (final float value : array) {
      collection.add(Float.valueOf(value));
    }

    return collection;
  }

  public static Collection<Double> toCollection(final double[] array) {
    final ArrayList<Double> collection = new ArrayList<Double>();

    for (final double value : array) {
      collection.add(Double.valueOf(value));
    }

    return collection;
  }

}
