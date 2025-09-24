package fw;

import java.util.Collection;

import fw.collections.IncludeMatcher;
import fw.collections.SizeMatcher;
import fw.equality.ReferenceMatcher;
import fw.equality.ValueMatcher;
import fw.numbers.BaseNumberMatcher;
import fw.numbers.NumberMatcher;
import fw.string.RegExpMatcher;
import fw.types.TypeMatcher;
import fw.utils.Converter;
import fw.values.BooleanMatcher;
import fw.values.NullMatcher;

public class SyntaxicTestCase {

  protected static final BooleanMatcher BE_TRUE = BooleanMatcher.TRUE;
  protected static final BooleanMatcher BE_FALSE = BooleanMatcher.FALSE;
  protected static final NullMatcher BE_NULL = NullMatcher.INSTANCE;

  /* -- Generic methods -- */

  public final <V> void should(final V value, final Matcher<V> matcher) {
    if (matcher.evaluate(value)) {
      matcher.printSuccess(value);
    } else {
      matcher.raiseFailure(value);
    }
  }

  public final <V> void shouldNot(final V value, final Matcher<V> matcher) {
    if (!matcher.evaluate(value)) {
      matcher.printNegationSuccess(value);
    } else {
      matcher.raiseNegationFailure(value);
    }
  }

  /* -- Generic methods for primitives types -- */

  public final void should(final byte value, final Matcher<Byte> matcher) {
    should(Byte.valueOf(value), matcher);
  }

  public final void should(final boolean value, final Matcher<Boolean> matcher) {
    should(Boolean.valueOf(value), matcher);
  }

  public final void should(final char value, final Matcher<Character> matcher) {
    should(Character.valueOf(value), matcher);
  }

  public final void should(final short value, final Matcher<Short> matcher) {
    should(Short.valueOf(value), matcher);
  }

  public final void should(final int value, final Matcher<Integer> matcher) {
    should(Integer.valueOf(value), matcher);
  }

  public final void should(final long value, final Matcher<Long> matcher) {
    should(Long.valueOf(value), matcher);
  }

  public final void should(final float value, final Matcher<Float> matcher) {
    should(Float.valueOf(value), matcher);
  }

  public final void should(final double value, final Matcher<Double> matcher) {
    should(Double.valueOf(value), matcher);
  }

  public final void shouldNot(final byte value, final Matcher<Byte> matcher) {
    shouldNot(Byte.valueOf(value), matcher);
  }

  public final void shouldNot(final boolean value, final Matcher<Boolean> matcher) {
    shouldNot(Boolean.valueOf(value), matcher);
  }

  public final void shouldNot(final char value, final Matcher<Character> matcher) {
    shouldNot(Character.valueOf(value), matcher);
  }

  public final void shouldNot(final short value, final Matcher<Short> matcher) {
    shouldNot(Short.valueOf(value), matcher);
  }

  public final void shouldNot(final int value, final Matcher<Integer> matcher) {
    shouldNot(Integer.valueOf(value), matcher);
  }

  public final void shouldNot(final long value, final Matcher<Long> matcher) {
    shouldNot(Long.valueOf(value), matcher);
  }

  public final void shouldNot(final float value, final Matcher<Float> matcher) {
    shouldNot(Float.valueOf(value), matcher);
  }

  public final void shouldNot(final double value, final Matcher<Double> matcher) {
    shouldNot(Double.valueOf(value), matcher);
  }

  /* -- Matchers for equality -- */

  public final Matcher<Object> eql(final Object reference) {
    return new ReferenceMatcher(reference);
  }

  public final Matcher<Object> equal(final Object reference) {
    return new ValueMatcher(reference);
  }

  /* -- Matchers for type -- */

  public final TypeMatcher beInstanceOf(final Class<?> type) {
    return new TypeMatcher(type);
  }

  /* -- Matchers for string -- */

  public final Matcher<String> match(final String pattern) {
    return new RegExpMatcher(pattern);
  }

  /* -- Matchers for collections -- */

  public final SizeMatcher have(final int size) {
    return new SizeMatcher(size);
  }

  public final IncludeMatcher include(final Object item) {
    return new IncludeMatcher(item);
  }

  public final IncludeMatcher include(final byte item) {
    return new IncludeMatcher(Byte.valueOf(item));
  }

  public final IncludeMatcher include(final boolean item) {
    return new IncludeMatcher(Boolean.valueOf(item));
  }

  public final IncludeMatcher include(final char item) {
    return new IncludeMatcher(Character.valueOf(item));
  }

  public final IncludeMatcher include(final short item) {
    return new IncludeMatcher(Short.valueOf(item));
  }

  public final IncludeMatcher include(final int item) {
    return new IncludeMatcher(Integer.valueOf(item));
  }

  public final IncludeMatcher include(final long item) {
    return new IncludeMatcher(Long.valueOf(item));
  }

  public final IncludeMatcher include(final float item) {
    return new IncludeMatcher(Float.valueOf(item));
  }

  public final IncludeMatcher include(final double item) {
    return new IncludeMatcher(Double.valueOf(item));
  }

  public final <T> void should(final T[] array, final Matcher<Collection<?>> collection) {
    should(Converter.toCollection(array), collection);
  }

  public final void should(final byte[] array, final Matcher<Collection<?>> collection) {
    should(Converter.toCollection(array), collection);
  }

  public final void should(final boolean[] array, final Matcher<Collection<?>> collection) {
    should(Converter.toCollection(array), collection);
  }

  public final void should(final char[] array, final Matcher<Collection<?>> collection) {
    should(Converter.toCollection(array), collection);
  }

  public final void should(final short[] array, final Matcher<Collection<?>> collection) {
    should(Converter.toCollection(array), collection);
  }

  public final void should(final int[] array, final Matcher<Collection<?>> collection) {
    should(Converter.toCollection(array), collection);
  }

  public final void should(final long[] array, final Matcher<Collection<?>> collection) {
    should(Converter.toCollection(array), collection);
  }

  public final void should(final float[] array, final Matcher<Collection<?>> collection) {
    should(Converter.toCollection(array), collection);
  }

  public final void should(final double[] array, final Matcher<Collection<?>> collection) {
    should(Converter.toCollection(array), collection);
  }

  public final <T> void shouldNot(final T[] array, final Matcher<Collection<?>> collection) {
    shouldNot(Converter.toCollection(array), collection);
  }

  public final void shouldNot(final byte[] array, final Matcher<Collection<?>> collection) {
    shouldNot(Converter.toCollection(array), collection);
  }

  public final void shouldNot(final boolean[] array, final Matcher<Collection<?>> collection) {
    shouldNot(Converter.toCollection(array), collection);
  }

  public final void shouldNot(final char[] array, final Matcher<Collection<?>> collection) {
    shouldNot(Converter.toCollection(array), collection);
  }

  public final void shouldNot(final short[] array, final Matcher<Collection<?>> collection) {
    shouldNot(Converter.toCollection(array), collection);
  }

  public final void shouldNot(final int[] array, final Matcher<Collection<?>> collection) {
    shouldNot(Converter.toCollection(array), collection);
  }

  public final void shouldNot(final long[] array, final Matcher<Collection<?>> collection) {
    shouldNot(Converter.toCollection(array), collection);
  }

  public final void shouldNot(final double[] array, final Matcher<Collection<?>> collection) {
    shouldNot(Converter.toCollection(array), collection);
  }

  public final void shouldNot(final float[] array, final Matcher<Collection<?>> collection) {
    shouldNot(Converter.toCollection(array), collection);
  }

  /* -- Matchers for numbers -- */

  public NumberMatcher<Integer> be(final int value) {
    return new BaseNumberMatcher<Integer>(Integer.valueOf(value));
  }

  public NumberMatcher<Integer> beEqual(final int value) {
    return be(value).equal();
  }

  public NumberMatcher<Integer> beGreateThan(final int value) {
    return be(value).greaterThan();
  }

  public NumberMatcher<Integer> beLower(final int value) {
    return be(value).lowerThan();
  }

  public NumberMatcher<Double> be(final double value) {
    return new BaseNumberMatcher<Double>(Double.valueOf(value));
  }

  public NumberMatcher<Double> beEqual(final double value) {
    return be(value).equal();
  }

  public NumberMatcher<Double> beGreateThan(final double value) {
    return be(value).greaterThan();
  }

  public NumberMatcher<Double> beLower(final double value) {
    return be(value).lowerThan();
  }

  public NumberMatcher<Float> be(final float value) {
    return new BaseNumberMatcher<Float>(Float.valueOf(value));
  }

  public NumberMatcher<Float> beEqual(final float value) {
    return be(value).equal();
  }

  public NumberMatcher<Float> beGreateThan(final float value) {
    return be(value).greaterThan();
  }

  public NumberMatcher<Float> beLower(final float value) {
    return be(value).lowerThan();
  }

  public NumberMatcher<Long> be(final long value) {
    return new BaseNumberMatcher<Long>(Long.valueOf(value));
  }

  public NumberMatcher<Long> beEqual(final long value) {
    return be(value).equal();
  }

  public NumberMatcher<Long> beGreateThan(final long value) {
    return be(value).greaterThan();
  }

  public NumberMatcher<Long> beLower(final long value) {
    return be(value).lowerThan();
  }

  public NumberMatcher<Short> be(final short value) {
    return new BaseNumberMatcher<Short>(Short.valueOf(value));
  }

  public NumberMatcher<Short> beEqual(final short value) {
    return be(value).equal();
  }

  public NumberMatcher<Short> beGreateThan(final short value) {
    return be(value).greaterThan();
  }

  public NumberMatcher<Short> beLower(final short value) {
    return be(value).lowerThan();
  }

}
