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
import com.sdicons.scripty.spec.type.BigDecimalType;
import com.sdicons.scripty.spec.type.ITypeSpec;
import com.sdicons.scripty.spec.type.TypeSpecException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class TestFib
{
    private ScriptyStreamProcessor scripty;

    @Before
    public void setup()
    throws ExtensionException, ProcessorException
    {
        scripty = new ScriptyStreamProcessor();
        scripty.addLibraryClasses(LoadLibrary.class, MathLibrary.class, ListLibrary.class);
        scripty.process("(load cp:/fib.lsp)");
    }

    private ITypeSpec bdspec = new BigDecimalType();

    public BigDecimal fib(int n)
    throws ProcessorException, TypeSpecException
    {
        Object lResult = scripty.process(String.format("(fib %d)", n));
        return (BigDecimal) bdspec.guard(lResult, scripty.getContext());
    }

    public BigDecimal fib2(int n)
    throws ProcessorException, TypeSpecException
    {
        Object lResult = scripty.process(String.format("(fib2 %d)", n));
        return (BigDecimal) bdspec.guard(lResult, scripty.getContext());
    }

    @Test
    public void fib()
    throws ProcessorException, TypeSpecException
    {
        for(int i = 2; i < 20; i++)
        {
            Assert.assertEquals(fib(i).floatValue(), fib2(i).floatValue(), 0.0001);
        }
    }
}
