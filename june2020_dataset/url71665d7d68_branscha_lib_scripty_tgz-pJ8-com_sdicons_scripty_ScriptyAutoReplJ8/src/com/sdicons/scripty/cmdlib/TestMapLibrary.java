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

import java.util.*;

public class TestMapLibrary
{
    private ScriptyStreamProcessor scripty;

    @Before
    public void initialize()
    throws ExtensionException
    {
        scripty = new ScriptyStreamProcessor();
        scripty.addLibraryClasses(MapLibrary.class);
    }

    @Test
    public void createMap1()
    throws ProcessorException
    {
        Object lResult = scripty.process("map-create");
        Assert.assertTrue(lResult instanceof java.util.Map);
        Assert.assertTrue(((Map) lResult).isEmpty());
    }

    @Test
    public void createMap2()
    throws ProcessorException
    {
        Object lResult = scripty.process("map-create a=uno b=duo c=tres d=quattuor");

        Assert.assertTrue(lResult instanceof java.util.Map);
        Map lResultMap = (Map) lResult;

        Assert.assertTrue(lResultMap.size() == 4);
        Assert.assertEquals(lResultMap.get("a"), "uno");
        Assert.assertEquals(lResultMap.get("b"), "duo");
        Assert.assertEquals(lResultMap.get("c"), "tres");
        Assert.assertEquals(lResultMap.get("d"), "quattuor");
    }

    @Test
    public void createMap3()
    throws ProcessorException
    {
        Map<String, Object> lMap = new HashMap<String, Object>();
        lMap.put("a", "uno");
        lMap.put("b", "duo");
        lMap.put("c", "tres");
        lMap.put("d", "quattuor");
        scripty.getContext().defBinding("map", lMap);

        Object lResult = scripty.process("map-create $map");

        Assert.assertTrue(lResult instanceof java.util.Map);
        Map lResultMap = (Map) lResult;

        Assert.assertTrue(lResultMap.size() == 4);
        Assert.assertEquals(lResultMap.get("a"), "uno");
        Assert.assertEquals(lResultMap.get("b"), "duo");
        Assert.assertEquals(lResultMap.get("c"), "tres");
        Assert.assertEquals(lResultMap.get("d"), "quattuor");
    }

    @Test(expected = ProcessorException.class)
    public void createMap4()
    throws ProcessorException
    {
        scripty.process("map-create '()");
        Assert.fail();
    }

    @Test
    public void createMap5()
    throws ProcessorException
    {
        Object lResult = scripty.process("map-create a b c d");

        Assert.assertTrue(lResult instanceof java.util.Map);
        Map lResultMap = (Map) lResult;

        Assert.assertTrue(lResultMap.size() == 4);
        Assert.assertEquals(lResultMap.get("a"), null);
        Assert.assertEquals(lResultMap.get("b"), null);
        Assert.assertEquals(lResultMap.get("c"), null);
        Assert.assertEquals(lResultMap.get("d"), null);
    }

    @Test
    public void testMap1()
    throws ProcessorException
    {
        Object lResult = scripty.process("map? (map-create)");
        Assert.assertTrue((Boolean) lResult);
    }

    @Test
    public void testMap2()
    throws ProcessorException
    {
        Object lResult = scripty.process("map? $null");
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void testMap3()
    throws ProcessorException
    {
        Object lResult = scripty.process("map? abc");
        Assert.assertFalse((Boolean) lResult);
    }

    @Test
    public void testMapSet1()
    throws ProcessorException 
    {
        Map lMap = new HashMap();
        scripty.getContext().defBinding("map", lMap);
        
        scripty.process("map-set $map key 123");
        Assert.assertTrue(lMap.size() ==1);
        Assert.assertTrue(lMap.containsKey("key"));
        Assert.assertTrue("123".equals(lMap.get("key")));
    }

    @Test
    public void testMapGet1()
    throws ProcessorException
    {
        Map<String, Object> lMap = new HashMap<String, Object>();
        lMap.put("a", "uno");
        lMap.put("b", "duo");
        lMap.put("c", "tres");
        lMap.put("d", "quattuor");
        scripty.getContext().defBinding("map", lMap);

        Assert.assertEquals("uno", scripty.process("map-get $map a"));
        Assert.assertEquals("duo", scripty.process("map-get $map b"));
        Assert.assertEquals("tres", scripty.process("map-get $map c"));
        Assert.assertEquals("quattuor", scripty.process("map-get $map d"));
    }

    @Test(expected = ProcessorException.class)
    public void testMapGet2()
    throws ProcessorException
    {
        Map<String, Object> lMap = new HashMap<String, Object>();
        lMap.put("a", "uno");
        lMap.put("b", "duo");
        lMap.put("c", "tres");
        lMap.put("d", "quattuor");
        scripty.getContext().defBinding("map", lMap);

        scripty.process("map-get $map x");
        Assert.fail();
    }

    @Test
    public void testMapKey1()
    throws ProcessorException
    {
        Map<String, Object> lMap = new HashMap<String, Object>();
        lMap.put("a", "uno");
        lMap.put("b", "duo");
        lMap.put("c", "tres");
        lMap.put("d", "quattuor");
        scripty.getContext().defBinding("map", lMap);

        Assert.assertTrue((Boolean) scripty.process("map-key? $map a"));
        Assert.assertTrue((Boolean) scripty.process("map-key? $map b"));
        Assert.assertTrue((Boolean) scripty.process("map-key? $map c"));
        Assert.assertTrue((Boolean) scripty.process("map-key? $map d"));
        Assert.assertFalse((Boolean) scripty.process("map-key? $map x"));
    }

    @Test
    public void testMapKeys1()
    throws ProcessorException
    {
        Map<String, Object> lMap = new HashMap<String, Object>();
        lMap.put("a", "uno");
        lMap.put("b", "duo");
        lMap.put("c", "tres");
        lMap.put("d", "quattuor");
        scripty.getContext().defBinding("map", lMap);

        Set lKeys = (Set) scripty.process("map-keys $map");
        Assert.assertTrue(lKeys.size() == 4);
        Assert.assertTrue(lKeys.containsAll(Arrays.asList("a", "b", "c", "d")));
    }

    @Test
    public void testMapValues1()
    throws ProcessorException
    {
        Map<String, Object> lMap = new HashMap<String, Object>();
        lMap.put("a", "uno");
        lMap.put("b", "duo");
        lMap.put("c", "tres");
        lMap.put("d", "quattuor");
        scripty.getContext().defBinding("map", lMap);

        Collection lValues = (Collection) scripty.process("map-values $map");
        Assert.assertTrue(lValues.size() == 4);
        Assert.assertTrue(lValues.containsAll(Arrays.asList("uno", "duo", "tres", "quattuor")));
    }

    @Test
    public void testClear1()
    throws ProcessorException
    {
        Map<String, Object> lMap = new HashMap<String, Object>();
        lMap.put("a", "uno");
        lMap.put("b", "duo");
        lMap.put("c", "tres");
        lMap.put("d", "quattuor");
        scripty.getContext().defBinding("map", lMap);

        Assert.assertTrue(lMap.size() == 4);
        scripty.process("map-clear $map");
        Assert.assertTrue(lMap.size() == 0);
    }

    @Test
    public void testSize1()
    throws ProcessorException
    {
        Map<String, Object> lMap = new HashMap<String, Object>();
        lMap.put("a", "uno");
        lMap.put("b", "duo");
        lMap.put("c", "tres");
        lMap.put("d", "quattuor");
        scripty.getContext().defBinding("map", lMap);

        Assert.assertEquals(scripty.process("map-size $map"), 4);
        Assert.assertEquals(scripty.process("map-size (map-create)"), 0);
    }
}