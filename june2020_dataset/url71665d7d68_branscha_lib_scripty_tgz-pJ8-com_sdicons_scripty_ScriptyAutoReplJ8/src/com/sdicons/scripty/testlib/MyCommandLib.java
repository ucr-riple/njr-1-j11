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

package com.sdicons.scripty.testlib;

import com.sdicons.scripty.annot.*;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@ScriptyStdArgList(
        name = "unoduo",
        fixed={
                @ScriptyArg(name="uno", type="Integer"),
                @ScriptyArg(name="duo", type="Integer")},
        named={
                @ScriptyArg(name="factor", type="Integer", optional=true, value="1")})
public class MyCommandLib
{
    @ScriptyCommand
    public static void hello(@ScriptyBindingParam("*output") PrintWriter aWriter, Object ... aArgs)
    {
        aWriter.print("Hello: ");
        for (Object lArg : aArgs) aWriter.print(lArg.toString());
        aWriter.println();
    }

    @ScriptyCommand
    @ScriptyVarArgList(vararg=@ScriptyArg(name="intlist", type="Integer"), minLength = 1)
    public static int add(
            @ScriptyParam("intlist") Object[] aIntList,
            @ScriptyBindingParam("*output")PrintWriter aWriter)
    {
        int lSum = (Integer) aIntList[0];
        for(int i = 1; i < aIntList.length; i++) lSum +=  (Integer) aIntList[i];
        aWriter.println(lSum);
        return lSum;

    }

    @ScriptyCommand
    @ScriptyStdArgList(fixed={@ScriptyArg(name="abc", type="ListOf(Integer) minLength=2")})
    public static void add2(
            @ScriptyParam("abc") List<Integer> aIntList,
            @ScriptyBindingParam("*output")PrintWriter aWriter)
    {
        int lSum = 0;
        for (int i : aIntList) lSum += i;
        aWriter.println(lSum);
    }

    @ScriptyCommand
    @ScriptyRefArgList(ref="unoduo")
    public static void sub( @ScriptyParam("uno") int first,
                            @ScriptyParam("duo") int second,
                            @ScriptyBindingParam("*output")PrintWriter aWriter)
    {
        aWriter.println(first - second );
    }
    
    @ScriptyMacro(name = "inverse")
    public static List omgekeerd(Object[] aArgs)
    {
        List aExpr = new LinkedList(Arrays.asList(aArgs));

        aExpr.remove(0);
        Collections.reverse(aExpr);
        return aExpr;
    }
    
    @ScriptyCommand(name="def-bruno")
    @ScriptyDefBinding("bruno")
    public static Object defBruno(Object[] aArgs)
    {
        return aArgs[1];
    }

    @ScriptyCommand(name="set-bruno")
    @ScriptySetBinding("bruno")
    public static Object setBruno(Object[] aArgs)
    {
        return aArgs[1];
    }

//    @ScriptyMacro(name = "xxx")
//    public static Integer omgekeerd(Integer aExpr)
//    {
//
//        return aExpr;
//    }
}
