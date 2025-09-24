package fw;

import java.util.ArrayList;
import java.util.Collection;

public class Test extends SyntaxicTestCase {

  public void run() {
    final int valueInt = 3;
    final double valueDbl = 1.2;
    final float valueFloat = 1.3f;
    final boolean valueBool = true;
    final char valueChar = 'a';
    final byte valueByte = 0x0;

    final String valueStr = "hello world";
    final Object valueObj = Boolean.TRUE;
    final Object reference = Boolean.FALSE;
    final Object valueNull = null;
    final Collection<Integer> collection = new ArrayList<Integer>();
    collection.add(Integer.valueOf(valueInt));
    final int ints[] = new int[] { 1, 2, 3 };
    final Integer[] arrayInts = new Integer[] { 1, 2 };

    /* -- ------------------------------------------ -- */

    should(true, BE_TRUE);
    should(false, BE_FALSE);
    shouldNot(false, BE_TRUE);
    shouldNot(true, BE_FALSE);
    // Don't compile
    // should(valueInt, BE_TRUE);
    // should(valueObj, BE_TRUE);

    /* -- ------------------------------------------ -- */

    shouldNot(valueInt, BE_NULL);
    shouldNot(valueBool, BE_NULL);
    shouldNot(valueObj, BE_NULL);
    should(valueNull, BE_NULL);

    /* -- ------------------------------------------ -- */

    shouldNot(valueObj, beInstanceOf(String.class));
    should(valueStr, beInstanceOf(String.class));

    /* -- ------------------------------------------ -- */

    should("hello world", match("o w"));
    should("hello world", match("(h|w)[a-z]"));
    shouldNot("hello world", match("bisounours"));

    /* -- ------------------------------------------ -- */

    should(valueInt, equal(valueInt));
    should(valueBool, equal(valueBool));
    shouldNot(valueObj, equal(valueInt));
    shouldNot(valueInt, equal(reference));
    shouldNot(valueBool, equal(reference));
    should(valueObj, equal(valueObj));

    /* -- ------------------------------------------ -- */

    shouldNot(valueInt, eql(reference));
    shouldNot(valueBool, eql(reference));
    shouldNot(valueObj, eql(reference));
    should(valueObj, eql(valueObj));
    // Don't compile
    // should(valueInt, match("regexp"));
    // should(valueObj, match("regexp"));

    /* -- ------------------------------------------ -- */

    should(collection, include(valueInt));
    shouldNot(collection, include(valueDbl));
    shouldNot(collection, include(valueFloat));
    shouldNot(collection, include(valueBool));
    shouldNot(collection, include(valueChar));
    shouldNot(collection, include(valueByte));

    should(ints, include(valueInt));
    shouldNot(arrayInts, include(valueInt));

    /* -- ------------------------------------------ -- */

    should(collection, have(1));
    shouldNot(collection, have(2).atLeast());
    should(collection, have(3).atMost());

    shouldNot(ints, have(1));
    should(ints, have(2).atLeast());
    should(ints, have(3).atMost());

    shouldNot(arrayInts, have(1));
    should(arrayInts, have(2).atLeast());
    should(arrayInts, have(3).atMost());

    /* -- ------------------------------------------ -- */

    should(valueInt, be(valueInt).equal());
    shouldNot(valueInt, be(valueInt).greaterThan());
    shouldNot(valueInt, be(valueInt).lowerThan());
    should(valueInt, be(valueInt).equal().orGreater());
    should(valueInt, be(valueInt).equal().orLower());
    should(valueInt, be(valueInt).greaterThan().orEqual());
    should(valueInt, be(valueInt).lowerThan().orEqual());

    // should(value, respondTo("functionName"));
    // should(variable, change());
  }
}
