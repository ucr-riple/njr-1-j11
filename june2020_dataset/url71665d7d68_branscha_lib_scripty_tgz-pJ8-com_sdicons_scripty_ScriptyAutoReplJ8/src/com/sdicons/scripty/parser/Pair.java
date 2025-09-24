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

package com.sdicons.scripty.parser;

/**
 * A Pair is an <b>immutable</b> basic type returned by the parser.
 * It is immutable by design, because it is a parser artifact. It does not have to 
 * be copied during evaluation.
 *
 */
public class Pair
{
    private Object left;
    private Object right;
    
    public Pair(Object left, Object right)
    {
        super();
        this.left = left;
        this.right = right;
    }
    
    public Object getLeft()
    {
        return left;
    }
    public Object getRight()
    {
        return right;
    }

    @Override
    public String toString()
    {
        return "pair(" + (left==null?"null":left.toString()) + "," + (right==null?"null":right.toString()) + ")";
    }    
}