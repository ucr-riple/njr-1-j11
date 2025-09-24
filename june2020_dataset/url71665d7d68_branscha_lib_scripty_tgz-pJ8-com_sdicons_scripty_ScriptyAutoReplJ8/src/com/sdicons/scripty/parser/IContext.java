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

import java.util.Map;

public interface IContext 
{
    // Changes an existing binding.
    // The change can occur at a nested level.
	public void setBinding(String aKey, Object aValue) 
	throws CommandException;
	// Define a  binding in the current context. The value can be null.
	// The new binding will always be at the top level.
    public void defBinding(String aKey, Object aValue);
    
	public Object getBinding(String aKey);
	public boolean isBound(String aKey);
    public void removeBinding(String aKey);
    public IContext getRootContext();
    
    public Map<String, Object> dumpBindings();
}
