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

import com.sdicons.scripty.annot.ScriptyCommand;
import com.sdicons.scripty.annot.ScriptyLibrary;
import com.sdicons.scripty.annot.ScriptyLibraryType;
import com.sdicons.scripty.cmdlib.ListLibrary;
import com.sdicons.scripty.cmdlib.LoadLibrary;
import com.sdicons.scripty.cmdlib.MathLibrary;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestHanoi
{
    private PrintPlusCmd printer;
    private ScriptyStreamProcessor scripty;

    @ScriptyLibrary(type = ScriptyLibraryType.INSTANCE)
    public static class PrintPlusCmd
    {
        private int counter = 0;

        @ScriptyCommand
        public void print()
        {
            counter ++;
        }

        @ScriptyCommand
        public void println()
        {
            counter++;
        }

        public int getCounter()
        {
            return counter;
        }

        public void reset()
        {
            counter = 0;
        }
    }

    @Before
    public void setup()
    throws ExtensionException, ProcessorException
    {
        scripty = new ScriptyStreamProcessor();
        printer = new PrintPlusCmd();

        scripty.addLibraryClasses(LoadLibrary.class, MathLibrary.class, ListLibrary.class);
        scripty.addLibraryInstances(printer);

        scripty.process("(load cp:/hanoi.lsp)");
    }

    public int hanoi(int n)
    throws  ProcessorException
    {
        printer.reset();
        scripty.process(String.format("(hanoi %d)", n));
        return printer.getCounter();
    }

    @Test
    public void hanoi()
    throws ProcessorException
    {
        Assert.assertEquals(1, hanoi(1));
        Assert.assertEquals(3, hanoi(2));
        Assert.assertEquals(7, hanoi(3));
        Assert.assertEquals(15, hanoi(4));
        Assert.assertEquals(31, hanoi(5));
        Assert.assertEquals(63, hanoi(6));
        Assert.assertEquals(127, hanoi(7));
        Assert.assertEquals(255, hanoi(8));
    }
}
