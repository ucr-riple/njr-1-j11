package java5.util;
import java.util.*;

public class Arrays
{
  private Arrays()
  {
  }

  public static void fill(byte[] a, int fromIndex, int toIndex, byte val)
  {
    if (fromIndex > toIndex)
      throw new IllegalArgumentException();
    for (int i = fromIndex; i < toIndex; i++)
      a[i] = val;
  }

  public static byte[] copyOf(byte[] original, int newLength)
  {
    if (newLength < 0)
      throw new NegativeArraySizeException("The array size is negative.");
    byte[] newArray = new byte[newLength];
    if (newLength > original.length) {
        System.arraycopy(original, 0, newArray, 0, original.length);
        fill(newArray, original.length, newArray.length, (byte)0);
    } else
      System.arraycopy(original, 0, newArray, 0, newLength);
    return newArray;
  }
}
