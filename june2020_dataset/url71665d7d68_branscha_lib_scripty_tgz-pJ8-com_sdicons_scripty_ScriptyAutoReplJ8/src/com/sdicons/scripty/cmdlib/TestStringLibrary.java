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

import java.util.List;

public class TestStringLibrary
{
    private ScriptyStreamProcessor scripty;

    @Before
    public void initialize()
    throws ExtensionException
    {
        scripty = new ScriptyStreamProcessor();
        scripty.addLibraryClasses(StringLibrary.class);
    }

    @Test
    public void isString1()
    throws ProcessorException
    {
        Object lResult = scripty.process("str? abc");
        Assert.assertTrue(lResult instanceof Boolean);
        Assert.assertTrue((Boolean) lResult);
    }

    @Test
    public void isString2()
    throws ProcessorException
    {
        Object lResult = scripty.process("str? '()");
        Assert.assertTrue(lResult instanceof Boolean);
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void isString3()
    throws ProcessorException
    {
        Object lResult = scripty.process("str? $null");
        Assert.assertTrue(lResult instanceof Boolean);
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void trim1()
    throws ProcessorException
    {
        Object lResult = scripty.process("str-trim \"  abc  \"");
        Assert.assertTrue(lResult instanceof String);
        Assert.assertEquals("abc", lResult);
    }

    @Test
    public void format1()
    throws ProcessorException
    {
        Object lResult = scripty.process("str-format \"1-%s, 2-%s\" uno duo");
        Assert.assertTrue(lResult instanceof String);
        Assert.assertEquals("1-uno, 2-duo", lResult);
    }

    @Test
    public void match1()
    throws ProcessorException
    {
        Object lResult = scripty.process("str-match \"(a+)(b*)c\" aaabbbbbc");
        Assert.assertTrue(lResult instanceof List);
        List lListResult = (List) lResult;
        Assert.assertTrue(lListResult.size() ==3);
        Assert.assertEquals("aaabbbbbc", lListResult.get(0));
        Assert.assertEquals("aaa", lListResult.get(1));
        Assert.assertEquals("bbbbb", lListResult.get(2));
    }

    @Test
    public void match2()
    throws ProcessorException
    {
        Object lResult = scripty.process("str-match* \"(\\d+)\\s*\" \"10 11 12 13\"");
        Assert.assertTrue(lResult instanceof List);
        List lListResult = (List) lResult;
        Assert.assertTrue(lListResult.size() == 4);

        Assert.assertEquals("10", ((List) lListResult.get(0)).get(1));
        Assert.assertEquals("11", ((List) lListResult.get(1)).get(1));
        Assert.assertEquals("12", ((List) lListResult.get(2)).get(1));
        Assert.assertEquals("13", ((List) lListResult.get(3)).get(1));
    }

    @Test
    public void isMatch1()
    throws ProcessorException
    {
        Object lResult = scripty.process("str-match? \"(a+)(b*)c\" aaabbbbbc");
        Assert.assertTrue(lResult instanceof Boolean);
        Assert.assertTrue(((Boolean) lResult));
    }

    @Test
    public void isMatch2()
    throws ProcessorException
    {
        Object lResult = scripty.process("str-match? \"(a+)(b*)c\" aaabbbbb");
        Assert.assertTrue(lResult instanceof Boolean);
        Assert.assertFalse(((Boolean) lResult));
    }
}