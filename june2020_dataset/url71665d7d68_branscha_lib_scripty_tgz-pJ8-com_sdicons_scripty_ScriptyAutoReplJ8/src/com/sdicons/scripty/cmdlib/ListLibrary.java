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

import com.sdicons.scripty.annot.*;

import java.util.*;

/**
 * Some Lisp like commands that act on lists. This module provides the basic
 * data manipulation commands, they are not built-in but provided as a separate module.
 * The semantics deviates from common lisp because we based our lists on Java lists and not on cons constructs.
 * Consing for example modifies the existing list, while in Lisp it creates a new version, the original binding refers to the sublist.
 * This can lead to surprising results if one is used to the original Lisp semantics.
 * <p>
 * <ul>
 *    <li><b>list?</b> A test to see if the argument is a list or not. The other command types are only applicable if this test turns out positive.</li>
 *    <li><b>empty?</b> A test to see if the list is empty.</li>
 *    <li><b>member?</b> A test to see if an element is part of the list or not. <code>(member? &lt;list> &lt;el>)</code></li>
 *    <li><b>car</b> The first element of the list. Non destructible on the original.</li>
 *    <li><b>cdr</b> A copy of the list without the first element. Non destructible on the original.</li>
 *    <li><b>pop</b> Get the element at the end of the list. Modifies the list and returns the element.</li>
 *    <li><b>shift</b> Get the first element of the list and modifies the list. It returns the element.</li>
 *    <li><b>push</b> Add one or more elements at the end of the list. Modifies the list and returns the list. <code>(push &lt;list> el1 ... eln)</code></li> *
 *    <li><b>unshift</b> Insert one or more elements at the beginning of the list. <code>(unshift &lt;list> el1 ... eln)</code></li>
 *    <li><b>cons</b> Insert one element at the beginning of the list. The list is modified, destructible on the original.<code>(cons el &lt;list>)</code></li>
 *    <li><b>append</b> Append two or more lists into a single new list. Non destructible.</li>
 *    <li><b>size</b> The number of elements in the list. The result is a string representing an integer.</li>
 *    <li><b>dup</b> Make a shallow copy of the list.</li>
 *    <li><b>null?</b> A test to see if the argument is null.</li>
 * </ul>
 *
 */
@ScriptyLibrary(name="Lisp", type=ScriptyLibraryType.STATIC)
@ScriptyNamedArgLists(
        std={
                @ScriptyStdArgList(name="1object", fixed={@ScriptyArg(name="arg", type="Any nullAllowed=true")}),
                @ScriptyStdArgList(name="1list/+elements", fixed={@ScriptyArg(name="arg", type="ListOf(Any) minLength=1")}),
                @ScriptyStdArgList(name="1list/*elements", fixed={@ScriptyArg(name="arg", type="ListOf(Any)")}),
                @ScriptyStdArgList(name="list+object", fixed={@ScriptyArg(name="lst", type="ListOf(Any)"), @ScriptyArg(name="obj", type="Any")}),
                @ScriptyStdArgList(name="object+list", fixed={@ScriptyArg(name="obj", type="Any"), @ScriptyArg(name="lst", type="ListOf(Any)")}),
                @ScriptyStdArgList(name="object+object", fixed={@ScriptyArg(name="obj", type="Any"), @ScriptyArg(name="lst", type="Any")})
        },
        var={
                @ScriptyVarArgList(name="list+objects", fixed={@ScriptyArg(name="lst", type="ListOf(Any)")}, vararg=@ScriptyArg(name="objs", type="Any"), minLength=1),
                @ScriptyVarArgList(name="list of lists", fixed={}, vararg=@ScriptyArg(name="lists", type="ListOf(Any)")),
                @ScriptyVarArgList(name="list of elements", fixed={}, vararg=@ScriptyArg(name="elements", type="Any"))
        } 
)
public class ListLibrary
{
    // Prevent instantiation.
    private ListLibrary()
    {
    }

    @ScriptyCommand(name = "list")
    @ScriptyRefArgList(ref="list of elements")
    public static List createList(@ScriptyParam("elements") Object[] aElements)
    {
        List lResult = new ArrayList(aElements.length);
        Collections.addAll(lResult, aElements);
        return lResult;
    }

    @ScriptyCommand(name = "empty?")
    @ScriptyRefArgList(ref="1list/*elements")
    public static boolean isEmpty(@ScriptyParam("arg") List aArg)
    {
        return aArg.size() <= 0;
    }

    @ScriptyCommand(name = "list?")
    @ScriptyRefArgList(ref = "1object")
    public static boolean isList(@ScriptyParam("arg") Object aArg)
    {
        return aArg instanceof List;
    }

    @ScriptyCommand(name = "member?")
    @ScriptyRefArgList(ref="list+object")
    public static boolean isMember(@ScriptyParam("lst") List aList, @ScriptyParam("obj") Object aObj)
    {
        return aList.contains(aObj);
    }

    @ScriptyCommand(name = "car")
    @ScriptyRefArgList(ref="1list/+elements")
    public static Object car(@ScriptyParam("arg") List aArg)
    {
        return aArg.get(0);
    }

    @ScriptyCommand(name="cdr")
    @ScriptyRefArgList(ref="1list/+elements")
    public static List cdr(@ScriptyParam("arg") List aList)
    {
        List lNewList = new ArrayList(aList);
        lNewList.remove(0);
        return lNewList;
    }

    @ScriptyCommand(name = "shift")
    @ScriptyRefArgList(ref="1list/+elements")
    public static Object shift(@ScriptyParam("arg") List aList)
    {
        return aList.remove(0);
    }

    @ScriptyCommand(name = "unshift")
    @ScriptyRefArgList(ref="list+objects")
    public static List unshift(@ScriptyParam("lst") List aList, @ScriptyParam("objs") Object[] aElements)
    {
        for (final Object aElement : aElements) aList.add(0, aElement);
        return aList;
    }

    @ScriptyMacro(name = "cons")
    @ScriptyRefArgList(ref = "object+object")
    public static List cons(@ScriptyParam("obj") Object aEl, @ScriptyParam("lst") Object aList)
    {
        return Arrays.asList("unshift", aList, aEl);
    }

    @ScriptyCommand(name = "pop")
    @ScriptyRefArgList(ref="1list/+elements")
    public static Object pop(@ScriptyParam("arg") List aList)
    {
        return aList.remove(aList.size() - 1);
    }

    @ScriptyCommand(name="push")
    @ScriptyRefArgList(ref="list+objects")
    public static List push(@ScriptyParam("lst") List aList, @ScriptyParam("objs") Object[] aElements)
    {
        for (int i = 0; i < aElements.length; i++) aList.add(aElements[i]);
        return aList;
    }

    @ScriptyCommand(name = "append")
    @ScriptyRefArgList(ref = "list of lists")
    public static List append(@ScriptyParam("lists") Object[] aLists)
    {
        final List<Object> lResult = new ArrayList<Object>();
        for (final Object aList : aLists)
        {
            List lPart = (List) aList;
            lResult.addAll(lPart);
        }
        return lResult;
    }

    @ScriptyCommand(name = "size")
    @ScriptyRefArgList(ref="1list/*elements")
    public static String size(@ScriptyParam("arg") List aList)
    {
        // Note that we return a string an d not an integer.
        // We use strings as our basic communication medium.
        return Integer.toString(aList.size());
    }

    @ScriptyCommand(name = "dup")
    @ScriptyRefArgList(ref="1list/*elements")
    public static List dup(@ScriptyParam("arg") List aList)
    {
        final List<Object> lResult = new ArrayList<Object>(aList.size());
        lResult.addAll(aList);
        return lResult;
    }

    @ScriptyCommand(name = "null?")
    @ScriptyRefArgList(ref = "1object")
    public static boolean isNull(@ScriptyParam("arg") Object aArg)
    {
        return aArg == null;
    }
}
