package ar.fiuba.tecnicas.framework.JTest;

import java.util.Arrays;
/*
Responsabilidad: Proveer una biblioteca de métodos a usar para realizar test de assertion
 */
public class Assert{
    public static<T> void assertArrayEquals(T[] expecteds, T[] actuals)throws AssertionError{
        if (!Arrays.equals(expecteds, actuals)) throw new AssertionError();
    }
    public static<T> void assertArrayEquals(String message, T[] expecteds, T[] actuals)throws AssertionError{
        if (!Arrays.equals(expecteds,actuals)) throw new AssertionError(message);
    }
    public static<T> void assertEquals(double expected, double actual, double delta)throws AssertionError{
        double realdelta= Math.abs(expected-actual);
        if (realdelta>delta) throw new AssertionError();
    }
    public static<T> void assertEquals(String message, double expected, double actual, double delta)throws AssertionError{
        double realdelta= Math.abs(expected-actual);
        if (realdelta>delta) throw new AssertionError(message);
    }
    public static<T> void assertEquals(T expected, T actual)throws AssertionError{
        if (!expected.equals(actual)) throw new AssertionError();
    }
    public static<T> void assertEquals(String message, T expected, T actual)throws AssertionError{
        if (!expected.equals(actual)) throw new AssertionError(message);
    }
    public static<T> void assertFalse(boolean condition)throws AssertionError{
        if (condition) throw new AssertionError();
    }
    public static<T> void assertFalse(String message, boolean condition)throws AssertionError{
        if (condition) throw new AssertionError(message);
    }
    public static<T> void assertNotNull(T object)throws AssertionError{
        if (object ==null) throw new AssertionError();
    }
    public static<T> void assertNotNull(String message, T object)throws AssertionError{
        if (object==null) throw new AssertionError(message);
    }
    public static<T> void assertNotSame(T unexpected, T actual)throws AssertionError{
         if (unexpected==actual) throw new AssertionError();
    }
    public static<T> void assertNotSame(String message, T unexpected, T actual)throws AssertionError{
        if (unexpected==actual) throw  new AssertionError(message);
    }
    public static<T> void assertNull(T object)throws AssertionError{
        if (object!=null) throw new AssertionError();
    }
    public static<T> void assertNull(String message, T object)throws AssertionError{
        if (object!= null) throw new AssertionError(message);
    }
    public static<T> void assertSame(T expected, T actual)throws AssertionError{
        if (expected!=actual) throw new AssertionError();
    }
    public static<T> void assertSame(String message, T expected, T actual)throws AssertionError{
        if (expected!=actual) throw new AssertionError(message);
    }
    public static <k,T> void assertThat(String reason, T actual, org.hamcrest.Matcher<k> matcher)throws AssertionError{
        if (!matcher.matches(actual)) throw new AssertionError(reason);
    }
    public static <k,T> void assertThat(T actual, org.hamcrest.Matcher<k> matcher)throws AssertionError{
        if (!matcher.matches(actual)) throw new AssertionError();
    }
    public static void assertTrue(boolean condition)throws AssertionError{
        if (!condition) throw new AssertionError();
    }
    public static void assertTrue(String message, boolean condition)throws AssertionError{
        if (!condition) throw new AssertionError(message);
    }
    public static void fail()throws AssertionError{
        throw new AssertionError();
    }
    public static void fail(String message)throws AssertionError{
        throw new AssertionError(message);
    }
}
