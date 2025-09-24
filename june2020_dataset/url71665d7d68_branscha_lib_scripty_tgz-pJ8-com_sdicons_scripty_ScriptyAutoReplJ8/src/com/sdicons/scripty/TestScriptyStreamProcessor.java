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

import com.sdicons.scripty.testlib.MyCommandLib;
import com.sdicons.scripty.testlib.MyCommandLib2;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestScriptyStreamProcessor
{
    private ScriptyStreamProcessor scripty;

    @Before
    public void initialize()
    {
        scripty = new ScriptyStreamProcessor();
    }

    @Test
    public void testMyCommandLib()
    throws ExtensionException, ProcessorException
    {
        scripty.addLibraryClasses(MyCommandLib.class);
        Object lResult = scripty.process("add 1 2");
        Assert.assertNotNull(lResult);
        Assert.assertTrue(lResult instanceof Integer);
        Assert.assertEquals("expression result", 3, lResult);
        
        lResult = scripty.process("inverse 11 12 add");
        Assert.assertNotNull(lResult);
    }

    @Test
    public void testMyCommandLib2()
            throws ExtensionException
    {
        scripty.addLibraryClasses(MyCommandLib2.class);
    }
}
