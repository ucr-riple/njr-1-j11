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

package com.sdicons.scripty.cmdlib;

import com.sdicons.scripty.ExtensionException;
import com.sdicons.scripty.ProcessorException;
import com.sdicons.scripty.ScriptyStreamProcessor;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TestMathLibrary
{
    private ScriptyStreamProcessor scripty;
    
    @Before
    public void initialize()
    throws ExtensionException
    {
        scripty = new ScriptyStreamProcessor();
        scripty.addLibraryClasses(MathLibrary.class);
    }
    
    @Test
    public void addNoArgs()
    throws ProcessorException
    {
        Object lResult = scripty.process("+");
        Assert.assertEquals(BigDecimal.ZERO, lResult);
    }

    @Test
    public void addOneArg()
    throws ProcessorException
    {
        Object lResult = scripty.process("+ 13");
        Assert.assertEquals(BigDecimal.valueOf(13), lResult);
    }

    @Test
    public void addMultiple()
    throws ProcessorException
    {
        Object lResult = scripty.process("+ 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25");
        Assert.assertEquals(BigDecimal.valueOf(325), lResult);
    }

    @Test(expected = ProcessorException.class)
    public void minusNoArg()
    throws ProcessorException
    {
        // It should throw an error, more than one
        // argument is needed ...
        scripty.process("-");
        Assert.fail();
    }

    @Test
    public void minusMultiple()
    throws ProcessorException
    {
        Object lResult = scripty.process("- 13 17 20");
        Assert.assertEquals(BigDecimal.valueOf(-24), lResult);
    }

    @Test
    public void div()
    throws ProcessorException
    {
        Object lResult = scripty.process("/ 111 3");
        Assert.assertEquals(BigDecimal.valueOf(37), lResult);
    }

    @Test
    public void mult()
    throws ProcessorException
    {
        Object lResult = scripty.process("* 1 2 3 4 5 6");
        Assert.assertEquals(BigDecimal.valueOf(720), lResult);
    }

    @Test
    public void pow1()
    throws ProcessorException
    {
        Object lResult = scripty.process("^ 2 8");
        Assert.assertEquals(BigDecimal.valueOf(256), lResult);
    }

    @Test(expected = ProcessorException.class)
    public void pow2()
    throws ProcessorException
    {
        // This should generate an exception because the second
        // argument should be an integer.
        scripty.process("^ 2 8.3");
        Assert.fail();
    }

    @Test
    public void rem()
    throws ProcessorException
    {
        Object lResult = scripty.process("rem 123 7");
        Assert.assertEquals(BigDecimal.valueOf(4), lResult);
    }

    @Test
    public void abs1()
    throws ProcessorException
    {
        Object lResult = scripty.process("abs -123");
        Assert.assertEquals(BigDecimal.valueOf(123), lResult);
    }

    @Test
    public void abs2()
    throws ProcessorException
    {
        Object lResult = scripty.process("abs 456");
        Assert.assertEquals(BigDecimal.valueOf(456), lResult);
    }

    @Test
    public void test1()
    throws ProcessorException
    {
        Object lResult = scripty.process("number? 123");
        Assert.assertTrue((Boolean) lResult);
    }

    @Test
    public void test2()
    throws ProcessorException
    {
        Object lResult = scripty.process("number? abc");
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void lt1()
    throws ProcessorException
    {
        Object lResult = scripty.process("< 13 14");
        Assert.assertTrue((Boolean) lResult);
    }

    @Test
    public void lt2()
    throws ProcessorException
    {
        Object lResult = scripty.process("< 13 13");
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void lt3()
    throws ProcessorException
    {
        Object lResult = scripty.process("< 14 13");
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void gt1()
    throws ProcessorException
    {
        Object lResult = scripty.process("> 23 22");
        Assert.assertTrue((Boolean) lResult);
    }

    @Test
    public void gt2()
    throws ProcessorException
    {
        Object lResult = scripty.process("> 22 22");
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void gt3()
    throws ProcessorException
    {
        Object lResult = scripty.process("> 22 23");
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void le1()
    throws ProcessorException
    {
        Object lResult = scripty.process("<~ 13 14");
        Assert.assertTrue((Boolean) lResult);
    }

    @Test
    public void le2()
    throws ProcessorException
    {
        Object lResult = scripty.process("<~ 13 13");
        Assert.assertTrue((Boolean) lResult);
    }

    @Test
    public void le3()
    throws ProcessorException
    {
        Object lResult = scripty.process("<~ 14 13");
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void ge1()
    throws ProcessorException
    {
        Object lResult = scripty.process(">~ 23 22");
        Assert.assertTrue((Boolean) lResult);
    }

    @Test
    public void ge2()
    throws ProcessorException
    {
        Object lResult = scripty.process(">~ 22 22");
        Assert.assertTrue((Boolean) lResult);
    }

    @Test
    public void ge3()
    throws ProcessorException
    {
        Object lResult = scripty.process(">~ 22 23");
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void eq1()
    throws ProcessorException
    {
        Object lResult = scripty.process("~ 13 13");
        Assert.assertTrue((Boolean) lResult);
    }

    @Test
    public void eq2()
    throws ProcessorException
    {
        Object lResult = scripty.process("~ 14 13");
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void zero1()
    throws ProcessorException
    {
        Object lResult = scripty.process("zero? 0");
        Assert.assertTrue((Boolean) lResult);
    }

    @Test
    public void zero2()
    throws ProcessorException
    {
        Object lResult = scripty.process("zero? 0.001");
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void convert()
    throws ProcessorException
    {
        Object lResult = scripty.process("float->int 10.3333557");
        Assert.assertEquals(BigInteger.valueOf(10), lResult);
    }

    @Test
    public void fin1()
    throws ProcessorException
    {
        Object lResult = scripty.process("fin 10.3333557");
        Assert.assertEquals(BigDecimal.valueOf(10.33), lResult);
    }

    @Test
    public void fin2()
    throws ProcessorException
    {
        Object lResult = scripty.process("fin 10.337");
        Assert.assertEquals(BigDecimal.valueOf(10.34), lResult);
    }
}
