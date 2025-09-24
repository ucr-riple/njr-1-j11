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
import com.sdicons.scripty.parser.IContext;
import com.sdicons.scripty.parser.Pair;
import com.sdicons.scripty.annot.*;

import java.beans.*;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ScriptyLibrary(type = ScriptyLibraryType.INSTANCE)
@ScriptyNamedArgLists(
        std = {@ScriptyStdArgList(name = "path", optional = {@ScriptyArg(name="path", type = "String", value = ".")})}
)
public class BeanLibrary
{

    private String currentDirectory = "/";

    // Goto another location. Absolute or relative paths allowed.
    // - / is the context of the repl.
    // - . and .. have normal meaning.
    // - [5] arrays, lists can be indexed.
    //   xyz[5] has same semantics as xyz/5
    //
    @ScriptyCommand(name="bean-cd")
    @ScriptyStdArgList(fixed = {@ScriptyArg(name="path", type = "String")})
    public Object beanCd(@ScriptyParam("path") String aPath, IContext aCtx)
    throws CommandException
    {
        // Follow the path to see if it leads somewhere.
        final Resolution lRes = resolve(aPath, aCtx);
        // We succeeded following the path, so it exists.
        if(!filter(lRes)) throw new CommandException("ERROR - cannot go there.");
        // We remember the current directory.
        currentDirectory = lRes.getPath();
        return lRes.getVal();
    }

    // Print the current path to the repl.
    //
    @ScriptyCommand(name="bean-pwd")
    @ScriptyStdArgList
    public Object beanPwd(@ScriptyBindingParam("*output") PrintWriter aWriter)
    {
        aWriter.println(currentDirectory);
        return currentDirectory;
    }

    // List the contents of the denoted object.
    // The action depends on the type of the object, properties, array elements are listed.
    //
    @ScriptyCommand(name="bean-ls")
    @ScriptyRefArgList(ref = "path")
    public Object beanLs(@ScriptyParam("path") String aPath, IContext aCtx, @ScriptyBindingParam("*output") PrintWriter aWriter)
    throws CommandException
    {
        // Resolve the path.
        final Resolution lRes = resolve( aPath, aCtx);
        if(! filter(lRes)) throw new CommandException("Cannot show filtered.");
        // Render the target object.
        final Object lVal = lRes.getVal();
        render(lVal, aWriter);
        return lVal;
    }

    // Convert the denoted object to a string and print it on the repl.
    // The difference between ls and cat is that ls lists the subelements while
    // cat calls the toString() method.
    //
    @ScriptyCommand(name="bean-cat")
    @ScriptyStdArgList(fixed = {@ScriptyArg(name="path", type = "String")})
    public Object beanCat(@ScriptyParam("path") String aPath, IContext aCtx, @ScriptyBindingParam("*output") PrintWriter aWriter)
    throws CommandException
    {
        // Resolve the path.
        final Resolution lRes = resolve(aPath, aCtx);
        if(! filter(lRes)) throw new CommandException("Cannot cat filtered.");
        final Object lVal = lRes.getVal();
        aWriter.println(lVal == null ? "null" : lVal.toString());
        return lVal;
    }

    // Convert a pathname to the object itself.
    // We can obtain a direct reference to the object in this way.
    // It is what the other commands do automatically. This command is in fact the
    // link between other command libraries and this one.
    //
    @ScriptyCommand(name="bean-rslv")
    @ScriptyStdArgList(fixed = {@ScriptyArg(name="path", type = "String")})
    public Object beanRslv(@ScriptyParam("path") String aPath, IContext aCtx)
    throws CommandException
    {
        // Resolve the path.
        final Resolution lRes = resolve(aPath, aCtx);
        if(! filter(lRes)) throw new CommandException("Cannot rslv filtered.");
        else return lRes.getVal();
    }

    // Call java methods on an object denoted by a path.
    // The target object should be denoted by a pathname.
    // The method can be specied by a method instance or by a name (and a lookup will occur).
    // The arguments are not resolved, they are passed directly to the method. You can use the
    // rslv method above to accomplish argument resolution as well.
    //
    @ScriptyCommand(name="bean-call")
    public Object beanCall(Object[] aArgs, IContext aCtx)
    throws CommandException
    {
        try
        {
            final String lPath = guardStringOther(aArgs);
            final Resolution lRes = resolve(lPath, aCtx);
            if(! filter(lRes)) throw new CommandException("Cannot call filtered.");
            final Object lTarget = lRes.getVal();

            Object[] lArgs = new Object[aArgs.length - 3];
            for(int i = 0; i < lArgs.length; i++) lArgs[i] = aArgs[i + 3];

            Method lMeth = null;
            if(aArgs[2] instanceof Method)
            {
                lMeth = (Method) aArgs[2];
            }
            else if (aArgs[2] instanceof String)
            {
                Class[] lClasses = new Class[lArgs.length];
                for(int i = 0; i < lArgs.length; i++) lClasses[i] = (lArgs[i]==null)?null:lArgs[i].getClass();

                lMeth = lTarget.getClass().getMethod((String) aArgs[2], lClasses);
            }

            if(lMeth == null)
                throw new CommandException("Method could not be resolved.");

            return lMeth.invoke(lTarget, lArgs);
        }
        catch (Exception e)
        {
            throw new CommandException("Invocation failed.\n" + e.getMessage(), e);
        }
    }

    // Update the properties of a bean.
    // (bean-upd BEAN prop1=val1 prop2=val2 ...)
    //
    @ScriptyCommand(name="bean-upd")
    public void beanUpd(Object[] aArgs, IContext aCtx)
    throws CommandException
    {
        final String lPath = guardStringOther(aArgs);
        final Resolution lRes = resolve(lPath, aCtx);
        if(! filter(lRes)) throw new CommandException("Cannot update filtered.");
        for(int i = 2; i < aArgs.length; i++)
        {
            if(!(aArgs[i] instanceof Pair))
                throw new CommandException("Pair expected.");
            Pair lPair = (Pair) aArgs[i];
            update(lRes.getVal(), lPair.getLeft(), lPair.getRight());
        }
    }

    private String guardStringOther(Object[] aArgs)
    throws CommandException
    {
        if(aArgs.length < 3)
            throw new CommandException("ERROR - Too few args.");
        if(!(aArgs[1] instanceof String))
            throw new CommandException("ERROR - expected a string as first arg.");
        return (String) aArgs[1];
    }

    public static interface IFilter
    {
        boolean filter(String aPath, Object aValue);
    }

    private IFilter[] filters = new IFilter[]{};

    private static Pattern PAT_FIELDNAME_BIS =  Pattern.compile("^\\s*([^\\s\\[\\]]+)?\\s*(\\[\\s*([^\\s\\[\\]]+)\\s*\\])*\\s*$");

    private static class Resolution
    {
        private Object val;
        private String path;

        public Resolution(String aPath, Object aObj)
        {
            path = aPath;
            val = aObj;
        }

        public String getPath()
        {
            return path;
        }

        public Object getVal()
        {
            return val;
        }
    }

    public Resolution resolve(String aPath, IContext aCtx)
    throws CommandException
    {
        if(aPath.startsWith("/"))
        {
            final String lRelPath = aPath.substring(1);
            List<String> lPieces = canonize(lRelPath);

            Object lVal;
            if("".equals(lRelPath)) lVal = aCtx;
            else lVal = resolveCanonical(lPieces, 0, aCtx);

            final StringBuilder lBuilder = new StringBuilder("/");
            for(String lPart : lPieces)
            {
                lBuilder.append(lPart);
                if(lPieces.get(lPieces.size() - 1) != lPart)
                    lBuilder.append("/");
            }
            return new Resolution(lBuilder.toString(), lVal);
        }
        else
        {
            String lAbsPath;
            if("/".equals(currentDirectory)) lAbsPath = currentDirectory + aPath;
            else lAbsPath = currentDirectory + "/" + aPath;
            return resolve(lAbsPath, aCtx);
        }
    }

    private static Object resolveCanonical(List<String> aPieces, int aCurrPiece, Object aBase)
    throws CommandException
    {
        if(aPieces.size() <= aCurrPiece) return aBase;
        final String lPiece = aPieces.get(aCurrPiece);
        int lIdx = -1;
        try
        {
            lIdx = Integer.parseInt(lPiece);
        }
        catch (NumberFormatException e1)
        {
            // Ignore this exception!
        }

        if(aBase instanceof IContext && lIdx < 0)
        {
            final IContext lCtx = (IContext) aBase;
            if(lCtx.isBound(lPiece))
                return resolveCanonical(aPieces, aCurrPiece + 1, lCtx.getBinding(lPiece));
            else
                throw new CommandException(String.format("Cannot find '%s' in context.", lPiece));
        }
        else if(aBase instanceof Map && lIdx < 0)
        {
            final Map lMap = (Map) aBase;
            if(lMap.containsKey(lPiece))
                return resolveCanonical(aPieces, aCurrPiece + 1, lMap.get(lPiece));
            else
                throw new CommandException("ERROR - map.");
        }
        else if(aBase instanceof List)
        {
            final List lList = (List) aBase;
            if(lIdx < 0) throw new CommandException("List expects an index.");
            if(lIdx >= lList.size()) throw new CommandException("List index out of range.");
            else return resolveCanonical(aPieces, aCurrPiece + 1, lList.get(lIdx));
        }
        else  if(aBase != null)
        {
            if(aBase.getClass().isArray())
            {
                // Array rendering.
                ///////////////////

                if(lIdx >=0 && lIdx < Array.getLength(aBase))
                    return resolveCanonical(aPieces, aCurrPiece + 1, Array.get(aBase, lIdx));
                else throw new CommandException("ERROR - index out of range/wrong index type/no index.");
            }
            else
            {
                try
                {
                    // Try bean.
                    //
                    final BeanInfo lInfo = Introspector.getBeanInfo(aBase.getClass());
                    final PropertyDescriptor lProps[] = lInfo.getPropertyDescriptors();
                    for (PropertyDescriptor lProp : lProps)
                    {
                        if (lProp.getName().equals(lPiece))
                        {
                            Method lGetter = lProp.getReadMethod();

                            Object[] lArgs;
                            if(lIdx >= 0) lArgs = new Object[]{lIdx};
                            else  lArgs = new Object[]{};

                            Object lVal = lGetter.invoke(aBase, lArgs);
                            return resolveCanonical(aPieces, aCurrPiece + 1, lVal);
                        }
                    }
                }
                catch(CommandException e)
                {
                    throw e;
                }
                catch (Exception e)
                {
                    throw new CommandException("ERROR - calling getter method.", e);
                }
            }

            // Oops, no property found.
            // What should we try next ?
            // TODO: alternative lookups.
            // - based on annotations.
            // - ...
            throw new CommandException("ERROR - no property.");
        }
        else if(aBase instanceof List && lIdx > 0)
        {
            List lList = (List) aBase;
            if(lIdx > lList.size() - 1)
                throw new CommandException("ERROR index out of bounds.");
            return resolveCanonical(aPieces, aCurrPiece + 1, lList.get(lIdx));
        }
        else throw new CommandException("ERROR - null.");
    }

    private static List<String> canonize(String aPath)
            throws CommandException
    {
        final String[] lPieces = aPath.split("/");
        final List<String> lResult= new ArrayList<String>(lPieces.length);
        for(String lPiece : lPieces)
        {
            if(".".equals(lPiece))
                // Just skip this.
                continue;
            else if("..".equals(lPiece))
            {
                // Delete the last part and go back to the previous part.
                int lResultSize = lResult.size();
                if(lResultSize > 0) lResult.remove(lResultSize - 1);
            }
            else
            {
                // Note: Could be done more efficiently using a custom parser.
                final Matcher m = PAT_FIELDNAME_BIS.matcher(lPiece);
                if(!m.matches())
                    throw new CommandException(String.format("Illegal path '%s'.", lPiece));
                // Fetch the prefix.
                final String lName = m.group(1);
                if(lName != null) lResult.add(lName);
                // Get the indices.
                int lStart = m.end(1)<0?0:m.end(1);
                final String[] lIndices = lPiece.substring(lStart).split("\\[");
                // Skip the first empty string.
                for(int i = 1; i < lIndices.length; i++)
                {
                    String lIdx = lIndices[i];
                    int lClosing = lIdx.lastIndexOf(']');
                    if(lClosing > 0) lIdx = lIdx.substring(0, lClosing).trim();
                    lResult.add(lIdx);
                }
            }
        }
        return lResult;
    }

    @SuppressWarnings("unchecked")
    private void update(Object aTarget, Object aProp, Object aVal)
            throws CommandException
    {
        String lPropName = "";
        int lIdx = -1;

        if(aProp instanceof String) lPropName = (String) aProp;
        else throw new CommandException("Don't know what to do with non-string keys ... (for now).");

        try
        {
            // Try to interprete the property as
            // an integer.
            lIdx = Integer.parseInt(lPropName);
        }
        catch (NumberFormatException e1)
        {
            // Ignore this exception!
        }

        if(aTarget instanceof List)
        {
            final List lTargetList = (List) aTarget;
            if(lIdx < 0)
                throw new CommandException("Modifying list should use an index.");
            if(lIdx >= lTargetList.size())
                throw new CommandException("Array index out of range.");

            // TODO: catch errors on this one.
            lTargetList.set(lIdx, aVal);

        }
        else if(aTarget != null)
        {
            if(aTarget.getClass().isArray())
            {
                // Array access.
                if(lIdx < 0)
                    throw new CommandException("Modifying array should use an index.");
                if(lIdx >= Array.getLength(aTarget))
                    throw new CommandException("Array index out of range.");

                // TODO: catch errors on this one.
                Array.set(aTarget, lIdx, aVal);
            }
            else
            {
                try
                {
                    // JavaBean access.
                    BeanInfo lInfo = Introspector.getBeanInfo(aTarget.getClass());
                    PropertyDescriptor lProps[] = lInfo.getPropertyDescriptors();
                    for (PropertyDescriptor lProp : lProps)
                    {
                        if(lProp.getDisplayName().equals(lPropName))
                        {
                            Method lMeth = lProp.getWriteMethod();
                            if(lMeth == null)
                                throw new CommandException("Property cannot be written.");

                            if(lProp.getPropertyType() != aVal.getClass() && aVal instanceof String)
                            {
                                PropertyEditor lEditor = lProp.createPropertyEditor(aTarget);
                                lEditor.setAsText((String) aVal);
                            }
                            else lMeth.invoke(aTarget, new Object[]{aVal});
                        }
                    }
                }
                catch (Exception e)
                {
                    throw new CommandException(e.getMessage(), e);
                }
            }
        }
    }

    private void render(Object aObj, PrintWriter aWriter)
            throws CommandException
    {
        if(aWriter == null) return;

        if(aObj instanceof IContext)
        {
            IContext lCtx = (IContext) aObj;
            Map<String, Object> lDump = lCtx.dumpBindings();
            for(String lKey : lDump.keySet())
            {
                Object lVal = lDump.get(lKey);
                aWriter.print(lKey);
                aWriter.print("=");
                aWriter.println(lVal == null ? "null" : lVal.toString());
            }
        }
        else if(aObj instanceof List)
        {
            // Show indexed ...
            aWriter.println(aObj.toString());
        }
        else if(aObj instanceof Map)
        {
            // Show map ....
            aWriter.println(aObj.toString());
        }
        else if(aObj != null)
        {
            if(aObj.getClass().isArray())
            {
                // Array rendering.
                ///////////////////

                aWriter.println(String.format("=== Type '%s' length %d ===", aObj.getClass().getSimpleName(), Array.getLength(aObj)));
                for(int i = 0; i < Array.getLength(aObj); i++)
                {
                    Object lObj = Array.get(aObj, i);
                    aWriter.println(String.format("%6s %s", "[" + i + "]", lObj==null?"null":lObj.toString()));
                }
            }
            else
            {
                try
                {
                    // Try JavaBean...
                    ///////////////////

                    aWriter.println(String.format("=== Type '%s' ===", aObj.getClass().getSimpleName()));
                    BeanInfo lInfo = Introspector.getBeanInfo(aObj.getClass());
                    PropertyDescriptor lProps[] = lInfo.getPropertyDescriptors();
                    for (PropertyDescriptor lProp : lProps)
                    {
                        Method lRead = lProp.getReadMethod();
                        Method lWrite = lProp.getWriteMethod();
                        String lRw = "" + ((lRead!=null)?"r":"-") + ((lWrite!=null)?"w": "-");
                        aWriter.println(String.format("%s (%s, %s)", lProp.getDisplayName(), lProp.getPropertyType().getSimpleName(), lRw));
                    }
                }
                catch (IntrospectionException e)
                {
                    throw new CommandException("ERROR - ...", e);
                }
            }
        }
        else
            throw new CommandException("ERROR - null encountered.");
    }

    private boolean filter(Resolution aRes)
    {
        final String lPath = aRes.getPath();
        final Object lVal = aRes.getVal();

        for(IFilter lFilt: filters)
        {
            if(!lFilt.filter(lPath, lVal)) return false;
        }
        return true;
    }
}
