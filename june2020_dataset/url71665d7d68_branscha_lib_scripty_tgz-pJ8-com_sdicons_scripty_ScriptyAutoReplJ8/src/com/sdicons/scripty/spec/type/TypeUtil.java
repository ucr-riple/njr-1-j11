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

package com.sdicons.scripty.spec.type;

public class TypeUtil
{
    public static final String msgExpectedOther(String aExpected, Object aObj)
    {
        String lMsg;
        if(aObj == null)
            lMsg = String.format("Expected type '%s' and received null value.", aExpected);
        else
            lMsg = String.format("Expected type '%s' and received incompatible type '%s' value '%s'.", aExpected, aObj.getClass().getCanonicalName(), aObj.toString());
        return lMsg;
    }
    
    public static final String msgBadRepr(String aExpected, String aRepr)
    {
        String lMsg;
        if(aRepr == null)
            lMsg = String.format("Expected type '%s' and received null value.", aExpected);
        else
            lMsg = String.format("Expected type '%s' and received incompatible string representation '%s'.", aExpected, aRepr);
        return lMsg;
    }
}
