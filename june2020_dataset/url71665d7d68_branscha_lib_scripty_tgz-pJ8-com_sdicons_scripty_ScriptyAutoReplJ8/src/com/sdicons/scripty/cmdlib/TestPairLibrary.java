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
import com.sdicons.scripty.parser.Pair;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPairLibrary
{
    private ScriptyStreamProcessor scripty;

    @Before
    public void initialize()
    throws ExtensionException
    {
        scripty = new ScriptyStreamProcessor();
        scripty.addLibraryClasses(PairLibrary.class);
    }

    @Test
    public void testPairCreation()
    throws ProcessorException
    {
        Object lResult = scripty.process("let (p1=a=uno p2=b=duo p3=c=ter) $p1");
        Assert.assertTrue(lResult instanceof Pair);
        Pair lPair = (Pair) lResult;
        Assert.assertEquals("a", lPair.getLeft());
        Assert.assertEquals("uno", lPair.getRight());
    }

    @Test
    public void isPair1()
    throws ProcessorException
    {
        Object lResult = scripty.process("let (p1=a=uno p2=b=duo p3=c=ter) (pair? $p1)");
        Assert.assertTrue(lResult instanceof Boolean);
        Assert.assertTrue((Boolean) lResult);
    }

    @Test
    public void isPair2()
    throws ProcessorException
    {
        Object lResult = scripty.process("pair? abc");
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void isPair3()
    throws ProcessorException
    {
        Object lResult = scripty.process("pair? $null");
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void pairLeft1()
    throws ProcessorException
    {
        Object lResult = scripty.process("let (p1=a=uno p2=b=duo p3=c=ter) (pair-left $p1)");
        Assert.assertTrue(lResult instanceof String);
        Assert.assertEquals("a", lResult);
    }

    @Test
    public void pairRight1()
    throws ProcessorException
    {
        Object lResult = scripty.process("let (p1=a=uno p2=b=duo p3=c=ter) (pair-right $p1)");
        Assert.assertTrue(lResult instanceof String);
        Assert.assertEquals("uno", lResult);
    }
}