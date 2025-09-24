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

public class TestTeaLibrary
{
    private ScriptyStreamProcessor scripty;

    @Before
    public void initialize()
    throws ExtensionException
    {
        scripty = new ScriptyStreamProcessor();
        scripty.addLibraryClasses(TeaLibrary.class);
    }

    @Test
    public void encryptStandard()
    throws ProcessorException
    {
        Object lResult = scripty.process("tea-encrypt \"Bruno Ranschaert is my name\"");
        Assert.assertEquals("58eac04cbf4629f6b576c80570e0e1898e96a6105f0d3b2780b570873f21580e601f30ab6d365853601f30ab6d365853601f30ab6d365853", lResult);
    }

    @Test
    public void decryptStandard()
    throws ProcessorException
    {
        Object lResult = scripty.process("tea-decrypt 58eac04cbf4629f6b576c80570e0e1898e96a6105f0d3b2780b570873f21580e601f30ab6d365853601f30ab6d365853601f30ab6d365853");
        Assert.assertEquals("Bruno Ranschaert is my name", lResult);
    }

    @Test
    public void encryptPwd()
    throws ProcessorException
    {
        Object lResult = scripty.process("tea-encrypt \"Bruno Ranschaert is my name\" password=\"Lang leve de koning!\"");
        Assert.assertEquals("ebe7d6aff359a43aaf6bb157504eb980633005aa56fa84b5642b928a2d72d193d60c1af1435c043bd60c1af1435c043bd60c1af1435c043b", lResult);
    }

    @Test
    public void decryptPwd()
    throws ProcessorException
    {
        Object lResult = scripty.process("tea-decrypt ebe7d6aff359a43aaf6bb157504eb980633005aa56fa84b5642b928a2d72d193d60c1af1435c043bd60c1af1435c043bd60c1af1435c043b password=\"Lang leve de koning!\"");
        Assert.assertEquals("Bruno Ranschaert is my name", lResult);
    }
}
