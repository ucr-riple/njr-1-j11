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

import java.util.ArrayList;
import java.util.List;

public class TestListLibrary
{
    private ScriptyStreamProcessor scripty;

    @Before
    public void initialize()
    throws ExtensionException
    {
        scripty = new ScriptyStreamProcessor();
        scripty.addLibraryClasses(ListLibrary.class);
    }

    @Test
    // 'list' without arguments should create an empty list.
    public void createList1()
    throws ProcessorException
    {
        Object lResult = scripty.process("list");
        Assert.assertTrue(lResult instanceof java.util.List);
        Assert.assertTrue(((List) lResult).isEmpty());
    }

    @Test
    // Standard case to create a list.
    public void createList2()
    throws ProcessorException
    {
        Object lResult = scripty.process("list 1 2 3 4 5");
        Assert.assertTrue(lResult instanceof java.util.List);
        Assert.assertTrue(((List) lResult).size() == 5);
    }

    @Test
    // An empty list should test as empty.
    public void isEmpty1()
    throws ProcessorException
    {
        Object lResult = scripty.process("empty? (list)");
        Assert.assertTrue(Boolean.TRUE.equals(lResult));
    }

    @Test
    // A non-empty list should not be empty.
    public void isEmpty2()
    throws ProcessorException
    {
        Object lResult = scripty.process("empty? (list 1 2 3 4 5)");
        Assert.assertTrue(Boolean.FALSE.equals(lResult));
    }

    @Test(expected = ProcessorException.class)
    // 'empty?' without arguments should throw an exception.
    public void isEmpty3()
    throws ProcessorException
    {
        scripty.process("empty?");
        Assert.fail();
    }

    @Test(expected = ProcessorException.class)
    // 'list?' without argument should throw an exception.
    public void isList1()
    throws ProcessorException
    {
        scripty.process("list?");
        Assert.fail();
    }

    @Test
    // Normal case, test that a list is a list.
    public void isList2()
    throws ProcessorException
    {
        Object lResult = scripty.process("list? (list 1 2 3 4 5)");
        Assert.assertTrue(Boolean.TRUE.equals(lResult));
    }

    @Test
    // An empty list is a list as well.
    public void isList3()
    throws ProcessorException
    {
        Object lResult = scripty.process("list? (list)");
        Assert.assertTrue(Boolean.TRUE.equals(lResult));
    }

    @Test
    // A string is not a list.
    public void isList4()
    throws ProcessorException
    {
        Object lResult = scripty.process("list? abc");
        Assert.assertTrue(Boolean.FALSE.equals(lResult));
    }

    @Test
    // The 'list?' should not blow up on null values.
    public void isList5()
    throws ProcessorException
    {
        Object lResult = scripty.process("list? $null");
        Assert.assertTrue(Boolean.FALSE.equals(lResult));
    }

    @Test
    public void isMember1()
    throws ProcessorException
    {
        Object lResult = scripty.process("member? (list 1 2 3 4 5) 13");
        Assert.assertTrue(Boolean.FALSE.equals(lResult));
    }

    @Test
    public void isMember2()
    throws ProcessorException
    {
        Object lResult = scripty.process("member? (list 1 2 3 4 5) 3");
        Assert.assertTrue(Boolean.TRUE.equals(lResult));
    }

    @Test
    public void car1()
    throws ProcessorException
    {
        Object lResult = scripty.process("car (list 17 13 19 23)");
        Assert.assertTrue("17".equals(lResult));
    }

    @Test(expected = ProcessorException.class)
    public void car2()
    throws ProcessorException
    {
        scripty.process("car (list)");
        Assert.fail();
    }

    @Test(expected = ProcessorException.class)
    public void car3()
    throws ProcessorException
    {
        scripty.process("car ()");
        Assert.fail();
    }

    @Test
    public void car4()
    throws ProcessorException
    {
        Object lResult = scripty.process(
                "   (let " +
                "      (lst=(list 17 13 19 23)) " +
                "      (progn (car $lst) $lst))");
        Assert.assertTrue(lResult instanceof List);
        Assert.assertTrue(((List) lResult).size() == 4);
    }

    @Test
    public void cdr1()
    throws ProcessorException
    {
        Object lResult = scripty.process("cdr (list 17 13 19 23)");
        Assert.assertTrue(lResult instanceof  List);
        List lList = (List) lResult;
        Assert.assertTrue(lList.size() == 3);
        Assert.assertTrue("13".equals(lList.get(0)));
        Assert.assertTrue("19".equals(lList.get(1)));
        Assert.assertTrue("23".equals(lList.get(2)));
    }

    @Test(expected = ProcessorException.class)
    public void cdr2()
    throws ProcessorException
    {
        scripty.process("cdr ()");
        Assert.fail();
    }

    @Test
    public void shift1()
    throws ProcessorException
    {
        Object lResult = scripty.process("shift (list 17 13 19 23)");
        Assert.assertTrue("17".equals(lResult));
    }

    @Test(expected = ProcessorException.class)
    public void shift2()
    throws ProcessorException
    {
        scripty.process("shift (list)");
        Assert.fail();
    }

    @Test(expected = ProcessorException.class)
    public void shift3()
    throws ProcessorException
    {
        scripty.process("shift ()");
        Assert.fail();
    }

    @Test
    public void shift4()
    throws ProcessorException
    {
        Object lResult = scripty.process(
                " (let " +
                        " (lst=(list 17 13 19 23)) " +
                        " (progn (shift $lst) $lst))");
        Assert.assertTrue(lResult instanceof List);
        Assert.assertTrue(((List) lResult).size() == 3);
    }

    @Test
    public void unshift1()
    throws ProcessorException
    {
        Object lResult = scripty.process(
                " (let " +
                        " (lst=(list 17 13 19 23)) " +
                        " (progn (unshift $lst 2 3 5) $lst))");
        Assert.assertTrue(lResult instanceof List);
        Assert.assertTrue(((List) lResult).size() == 7);
        Assert.assertTrue("5".equals(((List) lResult).get(0)));
        Assert.assertTrue("3".equals(((List) lResult).get(1)));
        Assert.assertTrue("2".equals(((List) lResult).get(2)));
    }

    @Test
    // We test the side effect of cons, viz. it changes the original list.
    public void cons1()
    throws ProcessorException
    {
        Object lResult = scripty.process(
                " (let " +
                        " (lst=(list 17 13 19 23)) " +
                        " (progn (cons 5 $lst) $lst))");
        Assert.assertTrue(lResult instanceof List);
        Assert.assertTrue(((List) lResult).size() == 5);
        Assert.assertTrue("5".equals(((List) lResult).get(0)));
    }

    @Test
    // We test the side effect of pop, viz. it changes
    // the original list.
    public void pop1()
    throws ProcessorException
    {
        Object lResult = scripty.process(
                " (let " +
                        " (lst=(list 17 13 19 23)) " +
                        " (progn (pop $lst) $lst))");
        Assert.assertTrue(lResult instanceof List);
        Assert.assertTrue(((List) lResult).size() == 3);
        Assert.assertTrue("17".equals(((List) lResult).get(0)));
        Assert.assertTrue("13".equals(((List) lResult).get(1)));
        Assert.assertTrue("19".equals(((List) lResult).get(2)));
    }

    @Test
    // We test the main functionality of pop, viz. it returns
    // the last element.
    public void pop2()
    throws ProcessorException
    {
        Object lResult = scripty.process(
                " (let " +
                        " (lst=(list 17 13 19 23)) " +
                        " (pop $lst))");
        Assert.assertTrue("23".equals(lResult));
    }

    @Test
    // We test for the side efffect of 'push' by pushing on the list, then returning the original list.
    public void push1()
    throws ProcessorException
    {
        Object lResult = scripty.process(
                " (let " +
                        " (lst=(list 17 13 19 23)) " +
                        " (progn (push $lst 2 3 5) $lst))");
        Assert.assertTrue(lResult instanceof List);
        Assert.assertTrue(((List) lResult).size() == 7);
        Assert.assertTrue("5".equals(((List) lResult).get(6)));
        Assert.assertTrue("3".equals(((List) lResult).get(5)));
        Assert.assertTrue("2".equals(((List) lResult).get(4)));
    }

    @Test
    // We test the main functionality of the append.
    public void append1()
    throws ProcessorException
    {
        Object lResult = scripty.process(
                "(let " +
                    " (lst1=(list 1 2 3) lst2=(list 4 5 6) lst3=(list 7 8 9)) " +
                    " (append $lst1 $lst2 $lst3)" +
                ")");
        Assert.assertTrue(lResult instanceof List);
        List lList = (List) lResult;
        Assert.assertTrue(lList.size() == 9);
        for (int i = 1; i < 10; i++) Assert.assertTrue(lList.contains("" + i));
    }

    @Test
    // The original lists should be unmodified.
    // Here we check out lst1 and see if it remained unchanged.
    public void append2()
    throws ProcessorException
    {
        Object lResult = scripty.process(
                "(let " +
                        " (lst1=(list 1 2 3) lst2=(list 4 5 6) lst3=(list 7 8 9)) " +
                        " (progn (append $lst1 $lst2 $lst3) $lst1)" +
                        ")");
        Assert.assertTrue(lResult instanceof List);
        List lList = (List) lResult;
        Assert.assertTrue(lList.size() == 3);
        for (int i = 1; i < 4; i++) Assert.assertTrue(lList.contains("" + i));
    }

    @Test
    // Main functionality of 'size' on a normal list.
    public void size1()
    throws ProcessorException
    {
        Object lResult = scripty.process(
                "(let " +
                        " (lst1=(list 1 2 3 4 5 6 7 8 9)) " +
                        " (size $lst1)" +
                        ")");
        Assert.assertTrue("9".equals(lResult));
    }

    @Test
    // The empty list should have size 0.
    public void size2()
    throws ProcessorException
    {
        Object lResult = scripty.process(
                "(let " +
                        " (lst1='()) " +
                        " (size $lst1)" +
                        ")");
        Assert.assertTrue("0".equals(lResult));
    }

    @Test
    // Test the main dup functionality.
    public void dup()
    throws ProcessorException
    {
        // Prepare a list with 1000 elements.
        final List lOrig = new ArrayList();
        for(int i = 0; i < 1000; i++) lOrig.add(i);

        // Duplicate it.
        scripty.getContext().defBinding("lst", lOrig);
        Object lResult = scripty.process("dup $lst");

        // Test that it is equals but not the same.
        Assert.assertTrue(lResult.equals(lOrig));
        Assert.assertTrue(lResult != lOrig);
    }

    @Test
    // 'null?' should not blow up on null values.
    //
    public void isNull()
    throws ProcessorException
    {
        // Duplicate it.
        scripty.getContext().defBinding("one", null);
        scripty.getContext().defBinding("two", "abc");
        
        Assert.assertTrue( (Boolean) scripty.process("null? $one"));
        Assert.assertFalse( (Boolean) scripty.process("null? $two"));
    }
}
