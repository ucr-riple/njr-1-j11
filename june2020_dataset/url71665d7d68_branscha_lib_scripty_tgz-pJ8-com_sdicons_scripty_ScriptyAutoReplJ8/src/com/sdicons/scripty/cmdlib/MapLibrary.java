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

import com.sdicons.scripty.parser.CommandException;
import com.sdicons.scripty.parser.Pair;
import com.sdicons.scripty.annot.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** Map command module.
 *
 * <ul>
 *    <li><b><code>map-create</code></b> Create an empty map. You can immediately insert pairs. <code>(map-create key=val | str | m ...)</code></li>
 *    <li><b><code>map?</code></b> Test whether an object is a map.<code>(map? m)</code></li>
 *    <li><b><code>map-set</code></b> Insert the key/value in a map. <code>(map-set m key val)</code></li>
 *    <li><b><code>map-get</code></b> Get the value bound by the key. <code>(map-get m key)</code></li>
 *    <li><b><code>map-key?</code></b> Test whether a key is present. <code>(map-key? m key)</code></li>
 *    <li><b><code>map-keys</code></b> Get the list of keys. <code>(map-keys m)</code></li>
 *    <li><b><code>map-values</code></b> Get the list of values. <code>(map-values m)</code></li>
 *    <li><b><code>map-clear</code></b> Make the map empty. <code>(map-clear m)</code> </li>
 *    <li><b><code>map-size</code></b> Get the number of entries in the map. <code>(map-size m)</code></li>
 * </ul>
 *
 */
@ScriptyNamedArgLists(
        std={
                @ScriptyStdArgList(name = "single map", fixed={@ScriptyArg(name="arg", type="Instance java.util.Map nullAllowed=false" )})
        }
)
public class MapLibrary
{
    @ScriptyCommand(name="map-create")
    public static Map mapCreate(Object[] aArgs)
    throws CommandException
    {
        {
            Map<Object, Object> lMap = new HashMap<Object, Object>();
            for(int i = 1; i < aArgs.length; i++)
            {
                if(aArgs[i] instanceof String)
                {
                    lMap.put(aArgs[i], null);
                }
                else if(aArgs[i] instanceof Pair)
                {
                    final Pair lPair = (Pair) aArgs[i];
                    lMap.put(lPair.getLeft(), lPair.getRight());
                }
                else if(aArgs[i] instanceof Map)
                {
                    lMap.putAll((Map<?,?>) aArgs[i]);
                }
                else
                {
                    Object lCulprit = aArgs[i];
                    throw new CommandException(String.format("Command '%s' expects zero or more string or pairs.\nArgument %d is of type '%s'.", aArgs[0], i, lCulprit==null?"null":lCulprit.getClass().getCanonicalName()));
                }
            }
            return lMap;
        }
    }

    @ScriptyCommand(name="map?")
    @ScriptyStdArgList(fixed={@ScriptyArg(name="arg", type="Any nullAllowed=true")})
    public static boolean isMap(@ScriptyParam("arg") Object aArg)
    {
        return aArg instanceof Map;
    }

    @ScriptyCommand(name="map-set")
    @ScriptyStdArgList(fixed={@ScriptyArg(name="map", type="Instance java.util.Map nullAllowed=false"), @ScriptyArg(name="key", type="Any nullAllowed=false"), @ScriptyArg(name="value", type="Any")})
    public static Map mapSet(@ScriptyParam("map") Map<Object, Object> aMap, @ScriptyParam("key") Object aKey, @ScriptyParam("value") Object aValue)
    {
        aMap.put(aKey, aValue);
        return aMap;
    }

    @ScriptyCommand(name="map-get")
    @ScriptyStdArgList(fixed={@ScriptyArg(name="map", type="Instance java.util.Map nullAllowed=false"), @ScriptyArg(name="key", type="Any nullAllowed=false")})
    public static Object mapGet(@ScriptyParam("map") Map aMap, @ScriptyParam("key") Object aKey)
    throws CommandException
    {
        if(!aMap.containsKey(aKey))
            throw new CommandException(String.format("The property '%s' does not exist.", aKey));
        return aMap.get(aKey);
    }

    @ScriptyCommand(name="map-key?")
    @ScriptyStdArgList(fixed={@ScriptyArg(name="map", type="Instance java.util.Map nullAllowed=false"), @ScriptyArg(name="key", type="Any nullAllowed=false")})
    public static boolean isKey(@ScriptyParam("map") Map aMap, @ScriptyParam("key") Object aKey)
    {
        return aMap.containsKey(aKey);
    }

    @ScriptyCommand(name="map-keys")
    @ScriptyRefArgList(ref = "single map")
    public static Set mapKeys(@ScriptyParam("arg") Map aMap)
    {
        return aMap.keySet();
    }

    @ScriptyCommand(name="map-values")
    @ScriptyRefArgList(ref = "single map")
    public static Collection mapValues(@ScriptyParam("arg") Map aMap)
    {
        return aMap.values();
    }

    @ScriptyCommand(name="map-clear")
    @ScriptyRefArgList(ref = "single map")
    public static Map mapClear(@ScriptyParam("arg") Map aMap)
    {
        aMap.clear();
        return aMap;
    }

    @ScriptyCommand(name="map-size")
    @ScriptyRefArgList(ref = "single map")
    public static int mapSize(@ScriptyParam("arg") Map aMap)
    {
        return aMap.size();
    }
}
