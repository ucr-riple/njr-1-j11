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

public class TestSqrt
{
    private ScriptyStreamProcessor scripty;

    @Before
    public void setup()
    throws ExtensionException, ProcessorException
    {
        scripty = new ScriptyStreamProcessor();
        scripty.addLibraryClasses(LoadLibrary.class, MathLibrary.class, ListLibrary.class);
        scripty.process("(load cp:/sqrt.lsp)");
    }

    public BigDecimal sqrt(double n)
    throws  ProcessorException
    {
        Object lResult = scripty.process(String.format("(sqrt %s)", n));
        return (BigDecimal) lResult;
    }

    @Test
    public void sqrt()
    throws ProcessorException
    {
        Assert.assertEquals(10.0, sqrt(100).floatValue(), 0.0001);
        Assert.assertEquals(351.36306, sqrt(123456).floatValue(), 0.0001);
        Assert.assertEquals(2, sqrt(4).floatValue(), 0.0001);
        Assert.assertEquals(1.77258, sqrt(Math.PI).floatValue(), 0.0001);
    }
}
