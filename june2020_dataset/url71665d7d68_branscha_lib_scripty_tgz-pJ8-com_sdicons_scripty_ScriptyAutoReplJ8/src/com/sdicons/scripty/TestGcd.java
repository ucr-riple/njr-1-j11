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

package com.sdicons.scripty;

import com.sdicons.scripty.cmdlib.ListLibrary;
import com.sdicons.scripty.cmdlib.LoadLibrary;
import com.sdicons.scripty.cmdlib.MathLibrary;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class TestGcd
{
    private ScriptyStreamProcessor scripty;

    @Before
    public void setup()
    throws ExtensionException, ProcessorException
    {
        scripty = new ScriptyStreamProcessor();
        scripty.addLibraryClasses(LoadLibrary.class, MathLibrary.class, ListLibrary.class);
        scripty.process("(load cp:/gcd.lsp)");
    }

    public BigDecimal gcd(int a, int b)
    throws  ProcessorException
    {
        Object lResult = scripty.process(String.format("(gcd %d %d)", a, b));
        return (BigDecimal) lResult;
    }

    @Test
    public void gcd()
    throws ProcessorException
    {
        Assert.assertEquals(84, gcd(1176, 2100).floatValue(), 0.0001);
        Assert.assertEquals(19177, gcd(1975231, 1936877).floatValue(), 0.0001);
    }
}
