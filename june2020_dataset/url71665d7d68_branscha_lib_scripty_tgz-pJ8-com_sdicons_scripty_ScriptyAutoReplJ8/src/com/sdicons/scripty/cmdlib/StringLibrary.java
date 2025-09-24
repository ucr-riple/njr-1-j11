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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ScriptyLibrary(name="String", type=ScriptyLibraryType.STATIC)
@ScriptyNamedArgLists(
        std={
                @ScriptyStdArgList(name = "1object", fixed={@ScriptyArg(name="arg", type="Any nullAllowed=true")}),
                @ScriptyStdArgList(name = "1string", fixed={@ScriptyArg(name="arg", type="String")}),
                @ScriptyStdArgList(name = "2strings", fixed={@ScriptyArg(name="arg1", type="String"), @ScriptyArg(name="arg2", type="String")})
        },
        var = {
                @ScriptyVarArgList(name = "1string + objects", fixed={@ScriptyArg(name="str", type="String")}, vararg = @ScriptyArg(name="objs", type="Any"))
        }
)
public class StringLibrary
{
    @ScriptyCommand(name="str?")
    @ScriptyRefArgList(ref = "1object")
    public static boolean isString(@ScriptyParam("arg") Object aArg)
    {
        return aArg instanceof String;
    }
    
    @ScriptyCommand(name="str-trim")
    @ScriptyRefArgList(ref="1string")
    public static String trim(@ScriptyParam("arg") String aArg)
    {
        return aArg.trim();
    }
    
    @ScriptyCommand(name="str-format")
    @ScriptyRefArgList(ref = "1string + objects")
    public static String format(@ScriptyParam("str") String aFormat, @ScriptyParam("objs") Object[] aArgs)
    {
        return String.format(aFormat, aArgs);
    }
    
    @ScriptyCommand(name="str-match")
    @ScriptyRefArgList(ref = "2strings")
    public static List<String> match(@ScriptyParam("arg1") String aPattern, @ScriptyParam("arg2") String aArg)
    {
        final Pattern lRegexp = Pattern.compile(aPattern);
        final Matcher lMatcher = lRegexp.matcher(aArg);
        final List<String> lRes = new ArrayList<String>();
        if(lMatcher.find())
        {
            // Note: there is one more group then the count indicates.
            // This is because of group 0 which matches the complete string.
            int lCount = lMatcher.groupCount();
            for(int i = 0; i <= lCount; i++) lRes.add(lMatcher.group(i));
        }
        return lRes;
    }
    
    @ScriptyCommand(name="str-match*")
    @ScriptyRefArgList(ref="2strings")
    public static List<List<String>> matchRepeat(@ScriptyParam("arg1") String aPattern, @ScriptyParam("arg2") String aArg)
    {
        final Pattern lRegexp = Pattern.compile(aPattern);
        final Matcher lMatcher = lRegexp.matcher(aArg);
        final List<List<String>> lRes = new ArrayList<List<String>>();
        while(lMatcher.find())
        {
            final List<String> lMatch = new ArrayList<String>();
            // Note: there is one more group then the count indicates.
            // This is because of group 0 which matches the complete string.
            int lCount = lMatcher.groupCount();
            for(int i = 0; i <= lCount; i++) lMatch.add(lMatcher.group(i));
            lRes.add(lMatch);
        }
        return lRes;
    }
    
    @ScriptyCommand(name="str-match?")
    @ScriptyRefArgList(ref="2strings")
    public static boolean isMatch(@ScriptyParam("arg1") String aPattern, @ScriptyParam("arg2") String aArg)
    {
        final Pattern lRegexp = Pattern.compile(aPattern);
        final Matcher lMatcher = lRegexp.matcher(aArg);
        return lMatcher.matches();
    }
}
