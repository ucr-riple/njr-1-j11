package mi.legacy.parser;

import mi.stream.StringStream;
import org.junit.Assert;

/**
 * User: goldolphin
 * Time: 2014-02-15 17:40
 */
public class ParserTest {

    @org.junit.Test
    public void testParse1() throws Exception {
        ParserBuilder parserBuilder = new ParserBuilder();
        Parser parser = parserBuilder.build(new Grammar() {
            @Override
            protected Nonterm define() {
                addProduction(N("A"), T("abcde"));
                return null;
            }
        }, false);
        Assert.assertTrue(parser.parse(new StringStream("abcde")));
        Assert.assertFalse(parser.parse(new StringStream("abcdef")));
        Assert.assertFalse(parser.parse(new StringStream("abcd")));
        Assert.assertFalse(parser.parse(new StringStream("bcd")));
        Assert.assertFalse(parser.parse(new StringStream("")));
    }

    @org.junit.Test
    public void testParse2() throws Exception {
        ParserBuilder parserBuilder = new ParserBuilder();
        Parser parser = parserBuilder.build(new Grammar() {
            @Override
            protected Nonterm define() {
                addProduction(N("A"), N("B"), T("b"));
                addProduction(N("B"), N("A"), T("a"));
                addProduction(N("A"), T("z"));
                addProduction(N("A"), T("cd"), N("A"), T("ef"));
                return null;
            }
        }, true);
        Assert.assertTrue(parser.parse(new StringStream("z"))); // A
        Assert.assertTrue(parser.parse(new StringStream("zaba"))); // B
        Assert.assertTrue(parser.parse(new StringStream("zabab"))); // A
        Assert.assertTrue(parser.parse(new StringStream("cdcdzefef"))); // A
        Assert.assertTrue(parser.parse(new StringStream("cdzabef"))); // A
        Assert.assertTrue(parser.parse(new StringStream("cdcdzababefef"))); // A
        Assert.assertFalse(parser.parse(new StringStream("cdcdzababefefe")));
    }

    @org.junit.Test
    public void testParse3() throws Exception {
        ParserBuilder parserBuilder = new ParserBuilder();
        Parser parser = parserBuilder.build(new Grammar() {
            @Override
            protected Nonterm define() {
                addProduction(N("A"), T("a"), T("b"));
                addProduction(N("A"), T("a"), N("B"));
                addProduction(N("B"), T("b"), T("c"));
                return null;
            }
        }, true);
        Assert.assertTrue(parser.parse(new StringStream("ab"))); // A
        Assert.assertTrue(parser.parse(new StringStream("abc"))); // A
    }

    @org.junit.Test
    public void testParse4() throws Exception {
        ParserBuilder parserBuilder = new ParserBuilder();
        Parser parser = parserBuilder.build(new Grammar() {
            @Override
            protected Nonterm define() {
                addProduction(N("Equal"), N("Equal"), T("=="), N("Rel"));
                addProduction(N("Equal"), N("Equal"), T("!="), N("Rel"));
                addProduction(N("Equal"), N("Rel"));

                addProduction(N("Rel"), N("Rel"), T("<"), N("Shift"));
                addProduction(N("Rel"), N("Rel"), T(">"), N("Shift"));
                addProduction(N("Rel"), N("Rel"), T("<="), N("Shift"));
                addProduction(N("Rel"), N("Rel"), T(">="), N("Shift"));
                addProduction(N("Rel"), N("Shift"));

                addProduction(N("Shift"), N("Shift"), T("<<"), N("Add"));
                addProduction(N("Shift"), N("Shift"), T(">>"), N("Add"));
                addProduction(N("Shift"), N("Shift"), T(">>>"), N("Add"));
                addProduction(N("Shift"), N("Add"));

                addProduction(N("Add"), N("Add"), T("+"), N("Mul"));
                addProduction(N("Add"), N("Add"), T("-"), N("Mul"));
                addProduction(N("Add"), N("Mul"));

                addProduction(N("Mul"), N("Mul"), T("*"), N("Unary"));
                addProduction(N("Mul"), N("Mul"), T("/"), N("Unary"));
                addProduction(N("Mul"), N("Mul"), T("%"), N("Unary"));
                addProduction(N("Mul"), N("Unary"));

                addProduction(N("Unary"), T("u"));
                addProduction(N("Unary"), T("("), N("Equal"), T(")"));
                return N("Equal");
                //return null;
            }
        }, true);
        //Assert.assertTrue(parser.parse(new StringStream("u/u")));
        Assert.assertTrue(parser.parse(new StringStream("u-u<=u*(u+u)/u%u")));
    }
}