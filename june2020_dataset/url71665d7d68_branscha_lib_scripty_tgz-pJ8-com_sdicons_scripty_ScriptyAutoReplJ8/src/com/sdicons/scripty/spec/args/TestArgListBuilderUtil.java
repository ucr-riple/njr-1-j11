/*
 * Scripty Programming Language
 * Copyright (C) 2010-2012 Bruno Ranschaert, S.D.I.-Consulting BVBA
 * http://www.sdi-consulting.be
 * mailto://info@sdi-consulting.be
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.sdicons.scripty.spec.args;

import com.sdicons.scripty.ExtensionException;
import com.sdicons.scripty.ProcessorException;
import com.sdicons.scripty.ScriptyStreamProcessor;
import com.sdicons.scripty.annot.ScriptyArg;
import com.sdicons.scripty.annot.ScriptyCommand;
import com.sdicons.scripty.annot.ScriptyParam;
import com.sdicons.scripty.annot.ScriptyStdArgList;
import com.sdicons.scripty.cmdlib.TeaLibrary;
import com.sdicons.scripty.parser.IContext;
import com.sdicons.scripty.spec.type.ITypeSpec;
import com.sdicons.scripty.spec.type.TypeSpecException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class TestArgListBuilderUtil
{
    private ScriptyStreamProcessor scripty;

    @Before
    public void initialize()
            throws ExtensionException
    {
        scripty = new ScriptyStreamProcessor();
        scripty.addLibraryClasses(TeaLibrary.class);
    }

    public static class AnyTypeLibrary
    {
        @ScriptyCommand(name="go")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="Any")})
        public static Object command(@ScriptyParam("arg") Object aArg)
        {
            return aArg;
        }

        @ScriptyCommand(name="go2")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="Any nullAllowed=true")})
        public static Object command2(@ScriptyParam("arg") Object aArg)
        {
            return aArg;
        }
    }
    
    @Test
    public void testAny1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(AnyTypeLibrary.class);
        Object lResult = scripty.process("go teststring");
        Assert.assertTrue("teststring".equals(lResult));
    }

    @Test(expected = ProcessorException.class)
    // The 'Any' type does not allow null values if not
    // explicitly allowed.
    public void testAny2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(AnyTypeLibrary.class);
        scripty.process("go $null");
        Assert.fail();
    }

    @Test
    // The 'Any' type should allow null values if nullAllowed=true is
    // specified.
    public void testAny3()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(AnyTypeLibrary.class);
        Object lResult = scripty.process("go2 $null");
        Assert.assertTrue(null == lResult);
    }
    
    public static class StandardTypes
    {
        @ScriptyCommand(name="do-bigdecimal")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="BigDecimal")})
        public Object doBigDecimal(@ScriptyParam("arg")BigDecimal aNr)
        {
            return aNr;
        }

        @ScriptyCommand(name="do-integer")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="Integer")})
        public Object doInteger(@ScriptyParam("arg") Integer aNr)
        {
            return aNr;
        }

        @ScriptyCommand(name="do-integer-range")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="IntegerRange min=3 max=13")})
        public Object doIntegerRange(@ScriptyParam("arg") Integer aNr)
        {
            return aNr;
        }

        @ScriptyCommand(name="do-biginteger")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="BigInteger")})
        public Object doBigInteger(@ScriptyParam("arg") BigInteger aNr)
        {
            return aNr;
        }

        @ScriptyCommand(name="do-boolean")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="Boolean")})
        public Object doBoolean(@ScriptyParam("arg") Boolean aNr)
        {
            return aNr;
        }

        @ScriptyCommand(name="do-byte")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="Byte")})
        public Object doByte(@ScriptyParam("arg") Byte aNr)
        {
            return aNr;
        }

        @ScriptyCommand(name="do-double")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="Double")})
        public Object doByte(@ScriptyParam("arg") Double aNr)
        {
            return aNr;
        }

        @ScriptyCommand(name="do-float")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="Float")})
        public Object doFloat(@ScriptyParam("arg") Float aNr)
        {
            return aNr;
        }

        @ScriptyCommand(name="do-long")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="Long")})
        public Object doLong(@ScriptyParam("arg") Long aNr)
        {
            return aNr;
        }

        @ScriptyCommand(name="do-short")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="Short")})
        public Object doShort(@ScriptyParam("arg") Short aNr)
        {
            return aNr;
        }

        @ScriptyCommand(name="do-string")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="String")})
        public Object doString(@ScriptyParam("arg") String aNr)
        {
            return aNr;
        }
    }
    
    @Test
    public void testBigDecimal1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        Object lResult=scripty.process("do-bigdecimal 123.456") ;
        Assert.assertTrue(lResult instanceof BigDecimal);
        Assert.assertEquals(new BigDecimal("123.456"), lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testBigDecimal2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        scripty.process("do-bigdecimal abc") ;
        Assert.fail();
    }

    @Test
    public void testInteger1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        Object lResult=scripty.process("do-integer 123456") ;
        Assert.assertTrue(lResult instanceof Integer);
        Assert.assertEquals(new Integer("123456"), lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testInteger2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        scripty.process("do-integer abc") ;
        Assert.fail();
    }

    @Test
    public void testIntegerRange1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        Object lResult=scripty.process("do-integer-range 11") ;
        Assert.assertTrue(lResult instanceof Integer);
        Assert.assertEquals(new Integer("11"), lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testIntegerRange2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        scripty.process("do-integer-range 2") ;
        Assert.fail();
    }

    @Test(expected = ProcessorException.class)
    public void testIntegerRange3()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        scripty.process("do-integer-range 14") ;
        Assert.fail();
    }

    @Test
    public void testBigInteger1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        Object lResult=scripty.process("do-biginteger 123456") ;
        Assert.assertTrue(lResult instanceof BigInteger);
        Assert.assertEquals(new BigInteger("123456"), lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testBigInteger2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        scripty.process("do-biginteger abc") ;
        Assert.fail();
    }

    @Test
    public void testBoolean1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        Object lResult=scripty.process("do-boolean true") ;
        Assert.assertTrue(lResult instanceof Boolean);
        Assert.assertEquals(Boolean.TRUE, lResult);
    }

    @Test
    public void testBoolean2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        Object lResult=scripty.process("do-boolean false") ;
        Assert.assertTrue(lResult instanceof Boolean);
        Assert.assertEquals(Boolean.FALSE, lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testBoolean3()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        scripty.process("do-boolean abc") ;
        Assert.fail();
    }

    @Test
    public void testByte1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        Object lResult=scripty.process("do-byte -128") ;
        Assert.assertTrue(lResult instanceof Byte);
        Assert.assertEquals(new Byte("-128"), lResult);
    }

    @Test
    public void testByte2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        Object lResult=scripty.process("do-byte 127") ;
        Assert.assertTrue(lResult instanceof Byte);
        Assert.assertEquals(new Byte("127"), lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testByte3()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        scripty.process("do-byte abc") ;
        Assert.fail();
    }

    @Test
    public void testDouble1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        Object lResult=scripty.process("do-double 1234.567") ;
        Assert.assertTrue(lResult instanceof Double);
        Assert.assertEquals(new Double("1234.567"), lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testDouble2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        scripty.process("do-double abc") ;
        Assert.fail();
    }

    @Test
    public void testFloat1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        Object lResult=scripty.process("do-float 1234.567") ;
        Assert.assertTrue(lResult instanceof Float);
        Assert.assertEquals(new Float("1234.567"), lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testFloat2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        scripty.process("do-float abc") ;
        Assert.fail();
    }

    @Test
    public void testLong1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        Object lResult=scripty.process("do-long 1234567") ;
        Assert.assertTrue(lResult instanceof Long);
        Assert.assertEquals(new Long("1234567"), lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testLong2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        scripty.process("do-long abc") ;
        Assert.fail();
    }

    @Test
    public void testShort1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        Object lResult=scripty.process("do-short 1234") ;
        Assert.assertTrue(lResult instanceof Short);
        Assert.assertEquals(new Short("1234"), lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testShort2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        scripty.process("do-short abc") ;
        Assert.fail();
    }

    @Test
    public void testString1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        Object lResult=scripty.process("do-string Hallo") ;
        Assert.assertTrue(lResult instanceof String);
        Assert.assertEquals("Hallo", lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testString2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(StandardTypes.class);
        scripty.process("do-string $null") ;
        Assert.fail();
    }

    public static class ListTypes
    {
        @ScriptyCommand(name="do-intlist-unbounded")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="ListOf (Integer)")})
        public Object unboundedList(@ScriptyParam("arg") List aList)
        {
            return aList;
        }

        @ScriptyCommand(name="do-intlist-bounded")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="ListOf (Integer) minLength=5 maxLength=10")})
        public Object boundedList(@ScriptyParam("arg") List aList)
        {
            return aList;
        }
    }

    @Test(expected = ProcessorException.class)
    public void testList1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(ListTypes.class);
        scripty.process("do-intlist-unbounded $null") ;
        Assert.fail();
    }

    @Test
    public void testList2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(ListTypes.class);
        Object lResult=scripty.process("do-intlist-unbounded ()") ;
        Assert.assertTrue(lResult instanceof List);
        Assert.assertTrue(((List) lResult).size() <= 0);
    }

    @Test
    public void testList3()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(ListTypes.class);
        Object lResult=scripty.process("do-intlist-unbounded '(1 2 3 4 5 6 7 8 9 10)") ;
        Assert.assertTrue(lResult instanceof List);
        Assert.assertTrue(((List) lResult).size() == 10);
    }

    @Test
    // Correct length viz. [5, 10]
    public void testList4()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(ListTypes.class);
        Object lResult=scripty.process("do-intlist-bounded '(1 2 3 4 5 6 7 8 9 10)") ;
        Assert.assertTrue(lResult instanceof List);
        Assert.assertTrue(((List) lResult).size() == 10);
    }

    @Test(expected = ProcessorException.class)
    // Too short < 5
    public void testList5()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(ListTypes.class);
        scripty.process("do-intlist-bounded '(1 2 3 4 )") ;
        Assert.fail();
    }

    @Test(expected = ProcessorException.class)
    // Too long > 10
    public void testList6()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(ListTypes.class);
        scripty.process("do-intlist-bounded '(1 2 3 4 5 6 7 8 9 10 11)") ;
        Assert.fail();
    }

    @Test
    // Type checker should leave the list unmodified, we
    // expect the original list to be returned.
    // Note that we should provide a modifiable list, otherwise Scripty will create
    // a copy for us.
    public void testList7()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(ListTypes.class);
        List<Integer> lOrig = new ArrayList<Integer>();
        lOrig.addAll(Arrays.asList(531, 337, 111));
        scripty.getContext().defBinding("lst", lOrig);

        Object lResult=scripty.process("do-intlist-unbounded $lst") ;
        Assert.assertTrue(lResult instanceof List);
        Assert.assertTrue(lOrig == lResult);
    }

    @Test
    // If we provide a unmodifiable list, Scripty should create a new one for us.
    // It should not blow up on this kind of thing.
    //
    public void testList8()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(ListTypes.class);
        List<String> lOrig = Arrays.asList("1", "2", "3");
        scripty.getContext().defBinding("lst", lOrig);

        Object lResult=scripty.process("do-intlist-unbounded $lst") ;
        Assert.assertTrue(lResult instanceof List);
        Assert.assertTrue(((List) lResult).size() == 3);
    }

    @Test(expected = ProcessorException.class)
    // Too long > 10
    public void testList9()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(ListTypes.class);
        scripty.process("do-intlist-unbounded '(1 2 3 4 notinteger 6 7 8 9 10 11)") ;
        Assert.fail();
    }

    public static class InstanceTypes
    {
        @ScriptyCommand(name="do-binding")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="InstanceOrBinding(Integer)")})
        public Object binding(@ScriptyParam("arg") Integer aNr)
        {
            return aNr;
        }

        @ScriptyCommand(name="do-instance")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="Instance java.util.List")})
        public Object instance(@ScriptyParam("arg") List aList)
        {
            return aList;
        }

        @ScriptyCommand(name="do-instance-null")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="Instance java.util.List nullAllowed=true")})
        public Object instance2(@ScriptyParam("arg") List aList)
        {
            return aList;
        }
    }

    @Test
    public void testBinding1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(InstanceTypes.class);
        scripty.getContext().defBinding("nr", 13);

        Object lResult=scripty.process("do-binding nr") ;
        Assert.assertTrue(lResult instanceof Integer);
        Assert.assertEquals(13, lResult);
    }

    @Test
    public void testBinding2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(InstanceTypes.class);
        Object lResult=scripty.process("do-binding 17") ;
        Assert.assertTrue(lResult instanceof Integer);
        Assert.assertEquals(17, lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testBinding3()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(InstanceTypes.class);
        scripty.process("do-binding abc") ;
        Assert.fail();
    }

    @Test(expected = ProcessorException.class)
    public void testBinding4()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(InstanceTypes.class);
        scripty.process("do-binding '(1 2 3)") ;
        Assert.fail();
    }

    @Test
    public void testInstance1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(InstanceTypes.class);
        Object lResult=scripty.process("do-instance '(1 2 3)") ;
        Assert.assertTrue(lResult instanceof List);
    }

    @Test(expected = ProcessorException.class)
    public void testInstance2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(InstanceTypes.class);
        scripty.process("do-instance abc") ;
        Assert.fail();
    }

    @Test(expected = ProcessorException.class)
    public void testInstance3()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(InstanceTypes.class);
        scripty.process("do-instance $null") ;
        Assert.fail();
    }

    @Test
    public void testInstance4()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(InstanceTypes.class);
        Object lResult=scripty.process("do-instance-null $null") ;
        Assert.assertNull(lResult);
    }

    public static class UnionTypes
    {
        @ScriptyCommand(name="do-union")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="OneOf (ListOf(Integer)) (Boolean)")})
        public Object union(@ScriptyParam("arg") Object aArg)
        {
            return aArg;
        }
    }

    @Test
    public void testUnion1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(UnionTypes.class);
        Object lResult=scripty.process("do-union '(1 2 3)") ;
        Assert.assertTrue(lResult instanceof List);
    }

    @Test
    public void testUnion2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(UnionTypes.class);
        Object lResult=scripty.process("do-union true") ;
        Assert.assertTrue(lResult instanceof Boolean);
        Assert.assertEquals(Boolean.TRUE, lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testUnion3()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(UnionTypes.class);
        scripty.process("do-union abc") ;
        Assert.fail();
    }

    @Test(expected = ProcessorException.class)
    public void testUnion4()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(UnionTypes.class);
        scripty.process("do-union '(1 2 xyz)") ;
        Assert.fail();
    }

    public static class CustomTypes
    {
        @ScriptyCommand(name="do-custom")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="Custom com.sdicons.scripty.spec.args.TestArgListBuilderUtil$CustomSpec")})
        public Object custom(@ScriptyParam("arg") Object aArg)
        {
            return aArg;
        }
    }
    
    public static class CustomSpec
    implements ITypeSpec
    {
        private Pattern pattern = Pattern.compile("a+b*");
        
        public Object guard(Object aArg, IContext aCtx) 
        throws TypeSpecException 
        {
            if(aArg instanceof String)
            {
                Matcher lMatcher = pattern.matcher(((String) aArg));
                if(lMatcher.matches()) return aArg;
            }
            throw new TypeSpecException("Not a+b*.");
        }

        public String getSpecName() 
        {
            return "custom pattern";
        }
    }

    @Test
    public void testCustom1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(CustomTypes.class);
        Object lResult=scripty.process("do-custom aaaabbbbbbbbbb") ;
        Assert.assertTrue(lResult instanceof String);
        Assert.assertEquals("aaaabbbbbbbbbb", lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testCustom2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(CustomTypes.class);
        scripty.process("do-custom bbb") ;
        Assert.fail();
    }

    public static class EnumTypes
    {
        @ScriptyCommand(name="do-enum")
        @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="Enum uno duo tres")})
        public Object enumtype(@ScriptyParam("arg") Object aArg)
        {
            return aArg;
        }
    }

    @Test
    public void testEnum1()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(EnumTypes.class);

        Object lResult=scripty.process("do-enum uno") ;
        Assert.assertTrue(lResult instanceof String);
        Assert.assertEquals("uno", lResult);

        lResult=scripty.process("do-enum duo") ;
        Assert.assertTrue(lResult instanceof String);
        Assert.assertEquals("duo", lResult);

        lResult=scripty.process("do-enum tres") ;
        Assert.assertTrue(lResult instanceof String);
        Assert.assertEquals("tres", lResult);
    }

    @Test(expected = ProcessorException.class)
    public void testEnum2()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(EnumTypes.class);
        scripty.process("do-enum quattuor") ;
        Assert.fail();
    }
}