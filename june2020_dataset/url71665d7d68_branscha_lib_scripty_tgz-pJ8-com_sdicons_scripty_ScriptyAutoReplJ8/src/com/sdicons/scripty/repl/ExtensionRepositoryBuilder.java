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

package com.sdicons.scripty.repl;

import com.sdicons.scripty.parser.*;
import com.sdicons.scripty.spec.args.ArgSpecException;
import com.sdicons.scripty.spec.args.IArgList;
import com.sdicons.scripty.ExtensionException;
import com.sdicons.scripty.IExtensions;
import com.sdicons.scripty.annot.*;
import com.sdicons.scripty.parser.MethodCommand;
import com.sdicons.scripty.spec.args.ArgListBuilderUtil;
import com.sdicons.scripty.spec.map.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ExtensionRepositoryBuilder
implements IExtensions
{
    private CommandRepository commandRepo;
    private CommandRepository macroRepo;

    public ExtensionRepositoryBuilder()
    {
        commandRepo = new CommandRepository();
        macroRepo = new CommandRepository();
    }

    public void addCommand(String aName, ICommand aCommand)
    throws ExtensionException
    {
        commandRepo.registerCommand(aName, aCommand);
    }

    public void addMacro(String aName, ICommand aMacro)
    throws ExtensionException
    {
        macroRepo.registerCommand(aName, aMacro);
    }

    public void addLibraryClasses(Class ... aLibraryClasses)
    throws ExtensionException
    {
        for(Class lClass : aLibraryClasses)
        {
            String lLibraryName = lClass.getSimpleName();
            ScriptyLibraryType lLibraryType = ScriptyLibraryType.AUTO;
            ScriptyLibrary lScriptyLibrary =  (ScriptyLibrary) lClass.getAnnotation(ScriptyLibrary.class);
            if(lScriptyLibrary != null)
            {
                lLibraryName = lScriptyLibrary.name();
                lLibraryType = lScriptyLibrary.type();
            }
            
            
            // Try to instantiate the class in order to be able to use non static 
            // methods as well. 
            Object lLibInstance = null;
            if(lLibraryType != ScriptyLibraryType.STATIC)
            {
                try
                {
                    lLibInstance = lClass.newInstance();
                } 
                catch (Exception e)
                {
                    // Nope we could not instantiate the library, so we 
                    // will only be able to add the static methods.
                    lLibInstance = null;
                }
            }

            if(lLibInstance == null && lLibraryType == ScriptyLibraryType.INSTANCE)
            {
                throw new ExtensionException(String.format("Library '%s' was marked as being of type INSTANCE, but it was impossible to create an instance.\nMaybe you should create an instance manually and provide the instance.", lClass.getName()));
            }

            addLibrary(lLibraryName, lLibInstance, lClass);
        }
    }
    
    private void registerClassArgList(Class aClass, ScriptyStdArgList aStdLst, Map<String, ArgListBuilderUtil.Tuple<IArgList, Map<String, IArgMapping>>> aNamedLists)
    throws ExtensionException
    {
        final String lName = aStdLst.name();
        if(lName.length() == 0)
        {
            final String lMsg = String.format("Class '%s' contains an unnamed standard argument list specification. An argument list specification associated with a command library should always have a name.", aClass.getName());
            throw new ExtensionException(lMsg);
        }

        try
        {
            // Build it.
            final ArgListBuilderUtil.Tuple<IArgList, Map<String, IArgMapping>> lTuple = ArgListBuilderUtil.buildArgList(aStdLst);
            // Remember it for references if the argument list spec has a name that is.
            aNamedLists.put(lName, lTuple);
        }
        catch (ArgSpecException e)
        {
            final String lMsg = String.format("Class '%s' contains a standard argument list specification '%s' with errors.\n%s", aClass.getName(), aStdLst.name(), e.getMessage());
            throw new ExtensionException(lMsg, e);
        }
    }

    private void registerClassArgList(Class aClass, ScriptyVarArgList aVarLst, Map<String, ArgListBuilderUtil.Tuple<IArgList, Map<String, IArgMapping>>> aNamedLists)
    throws ExtensionException
    {
        final String lName = aVarLst.name();
        if(lName.length() == 0)
        {
            final String lMsg = String.format("Class '%s' contains an unnamed variable argument list specification. An argument list specification associated with a command library should always have a name.", aClass.getName());
            throw new ExtensionException(lMsg);
        }

        try
        {
            // Build it.
            final ArgListBuilderUtil.Tuple<IArgList, Map<String, IArgMapping>> lTuple = ArgListBuilderUtil.buildArgList(aVarLst);
            // Remember it for references if the argument list spec has a name that is.
            aNamedLists.put(lName, lTuple);
        }
        catch (ArgSpecException e)
        {
            final String lMsg = String.format("Class '%s' contains a variable argument list specification '%s' with errors.\n%s", aClass.getName(), aVarLst.name(), e.getMessage());
            throw new ExtensionException(lMsg, e);
        }
    }

    private void addLibrary(String aLibName, Object aLibInstance, Class aClass)
    throws ExtensionException
    {
        // We keep track of the named arglists in this datastructure. 
        Map<String, ArgListBuilderUtil.Tuple<IArgList, Map<String, IArgMapping>>> lNamedArgLists = new HashMap<String, ArgListBuilderUtil.Tuple<IArgList, Map<String, IArgMapping>>>();

        // Library (Class) annotations.
        ///////////////////////////////
        
        ScriptyStdArgList lStdArgListClassAnnot = (ScriptyStdArgList) aClass.getAnnotation(ScriptyStdArgList.class);
        ScriptyVarArgList lVarArgListClassAnnot = (ScriptyVarArgList) aClass.getAnnotation(ScriptyVarArgList.class);
        ScriptyNamedArgLists lNamedArgListsAnnot = (ScriptyNamedArgLists) aClass.getAnnotation(ScriptyNamedArgLists.class);
        
        int lClassAnnotCounter = 0;
        if(lStdArgListClassAnnot != null) lClassAnnotCounter++;
        if(lVarArgListClassAnnot != null) lClassAnnotCounter++;
        if(lNamedArgListsAnnot != null) lClassAnnotCounter++;
        if(lClassAnnotCounter > 1)
        {
            final String lMsg = String.format("Class '%s' contains more than one argument list spefification. Use @ScriptyNamedArgLists to associate multiple argument list specifications with a single class.", aClass.getName());
            throw new ExtensionException(lMsg);
        }
        
        if(lVarArgListClassAnnot != null)
        {
            registerClassArgList(aClass, lVarArgListClassAnnot, lNamedArgLists);
        }
        else if(lStdArgListClassAnnot != null)
        {
            registerClassArgList(aClass, lStdArgListClassAnnot, lNamedArgLists);
        }
        else if(lNamedArgListsAnnot != null)
        {
            ScriptyStdArgList[] lStdLists = lNamedArgListsAnnot.std();
            ScriptyVarArgList[] lVarLists = lNamedArgListsAnnot.var();
            
            for (ScriptyStdArgList lStd : lStdLists)
            {
                registerClassArgList(aClass, lStd, lNamedArgLists);
            }
            
            for(ScriptyVarArgList lVar : lVarLists)
            {
                registerClassArgList(aClass, lVar, lNamedArgLists);
            }
        }

        // Find command/macro methods.
        //////////////////////////////

        final Method[] lMethods = aClass.getMethods();

        for(Method lMethod:lMethods)
        {
            // Result binding
            /////////////////

            IResultMapping lResultMapping = null;
            ScriptySetBinding lSetBinding = lMethod.getAnnotation(ScriptySetBinding.class);
            ScriptyDefBinding lDefBinding = lMethod.getAnnotation(ScriptyDefBinding.class);
            if(lSetBinding != null && lDefBinding != null)
            {
                final String lMsg = String.format("Class '%s' has a macro/command method '%s' is annotated with both def/set binding of the result to the context.", aClass.getName(), lMethod.getName());
                throw new ExtensionException(lMsg);
            }

            if(lSetBinding != null)
            {
                lResultMapping = new SetResultMapping(lSetBinding.value());
            }

            if(lDefBinding != null)
            {
                lResultMapping = new DefResultMapping(lDefBinding.value());
            }

            // Argument list specifications.
            ////////////////////////////////

            IArgList lArgList = null;
            Map<String, IArgMapping> lMappings = null;

            ScriptyStdArgList lStdArgListAnnot = lMethod.getAnnotation(ScriptyStdArgList.class);
            ScriptyVarArgList lVarArgListAnnot = lMethod.getAnnotation(ScriptyVarArgList.class);
            ScriptyRefArgList lRefArgListAnnot = lMethod.getAnnotation(ScriptyRefArgList.class);
            
            int lArgListCounter = 0;
            if(lStdArgListAnnot != null) lArgListCounter++;
            if(lVarArgListAnnot != null) lArgListCounter++;
            if(lRefArgListAnnot != null) lArgListCounter++;            
            if(lArgListCounter > 1)
            {
                final String lMsg = String.format("Class '%s' has a macro/command method '%s' with more than one argument list specification.", aClass.getName(), lMethod.getName());
                throw new ExtensionException(lMsg);
            }

            if(lRefArgListAnnot != null)
            {
                final String lRef = lRefArgListAnnot.ref();
                if(lNamedArgLists.containsKey(lRef))
                {
                    ArgListBuilderUtil.Tuple<IArgList, Map<String, IArgMapping>> lTuple = lNamedArgLists.get(lRef);
                    lArgList = lTuple.getX();
                    lMappings = lTuple.getY();
                }
                else
                {
                    final String lMsg = String.format("Class '%s' has a macro/command method '%s' with a reference to a named argument list '%s' which does not exist.", aClass, lMethod.getName(), lRef);
                    throw new ExtensionException(lMsg);
                }
            }
            else if(lStdArgListAnnot != null)
            {
                try
                {
                    // Build the arglist.
                    ArgListBuilderUtil.ArgListTuple lTuple = ArgListBuilderUtil.buildArgList(lStdArgListAnnot);
                    // Remember it for references if the argument list spec has a name that is.
                    if(lStdArgListAnnot.name().length() > 0)
                    {
                        lNamedArgLists.put(lStdArgListAnnot.name(), lTuple);
                    }
                    lArgList = lTuple.getX();
                    lMappings = lTuple.getY();
                } 
                catch (ArgSpecException e)
                {
                    final String lMsg = String.format("While processing a standard argument list specification for class '%s' on method '%s'.\n%s", aClass.getName(), lMethod.getName(), e.getMessage());
                    throw new ExtensionException(lMsg, e);
                }
            }
            else if (lVarArgListAnnot != null)
            {
                try
                {
                    // Build the arglist.
                    ArgListBuilderUtil.ArgListTuple lTuple = ArgListBuilderUtil.buildArgList(lVarArgListAnnot);
                    // Remember it for references if the argument list spec has a name that is.
                    if(lVarArgListAnnot.name().length() > 0)
                    {
                        lNamedArgLists.put(lVarArgListAnnot.name(), lTuple);
                    }
                    lArgList = lTuple.getX();
                    lMappings = lTuple.getY();
                }
                catch (ArgSpecException e)
                {
                    final String lMsg = String.format("While processing a variable argument list specification for class '%s' on method '%s'.\n%s", aClass.getName(), lMethod.getName(), e.getMessage());
                    throw new ExtensionException(lMsg, e);
                }
            }

            // Command or Macro.
            ////////////////////
            
            ScriptyCommand lCmdAnnot = lMethod.getAnnotation(ScriptyCommand.class);
            ScriptyMacro lMacroAnnot = lMethod.getAnnotation(ScriptyMacro.class);

            if(lCmdAnnot != null && lMacroAnnot != null)
            {
                // The method is annotated as a command AND macro.
                // A method can be a command or a macro, but not both things at the same time.
                throw new ExtensionException(String.format("Method '%s' in class '%s' is annotated as command and as a macro simultaneously.", lMethod.getName(), aClass.getSimpleName()));
            }
            else if(!(lCmdAnnot == null && lMacroAnnot == null))
            {
                int lMethodModifiers = lMethod.getModifiers();
                boolean lIsStaticMethod = Modifier.isStatic(lMethodModifiers);
                if(!lIsStaticMethod && aLibInstance == null)
                {
                    throw new ExtensionException(String.format("Method '%s' in class '%s' is not static, and there is no library instance.", lMethod.getName(), aClass.getSimpleName()));
                }

                // Construct the argument mapping.
                ArgListMapping lArgListMapping = null;
                try
                {
                    lArgListMapping = ArgMappingBuilderUtil.buildArgMapping(lMethod, lMappings);
                }
                catch (ArgMappingException e)
                {
                    final String lMsg = String.format("While constructing the argument mapping for class '%s' on method '%s'.\n%s", aClass.getName(), lMethod.getName(), e.getMessage());
                    throw new ExtensionException(lMsg, e);
                }

                if(lCmdAnnot != null)
                {
                    // If the annotation does not contain a name, we will use the
                    // method name as a name.
                    String lCmdName = lCmdAnnot.name();
                    if(lCmdName.length() == 0) lCmdName = lMethod.getName();

                    commandRepo.registerCommand(lCmdName, new MethodCommand(aLibInstance, lMethod, lArgList, lArgListMapping, lResultMapping));
                }
                else if (lMacroAnnot != null)
                {
                    String lMacroName = lMacroAnnot.name();
                    if(lMacroName.length() == 0) lMacroName = lMethod.getName();
                    macroRepo.registerCommand(lMacroName, new MethodCommand(aLibInstance, lMethod, lArgList, lArgListMapping, lResultMapping));
                }
            }
        }
    }

    public void addLibraryInstances(Object ... aLibraryInstances)
    throws ExtensionException
    {
        for(Object lLib : aLibraryInstances)
        {
            String lLibraryName = lLib.getClass().getSimpleName();
            ScriptyLibraryType lLibraryType = ScriptyLibraryType.AUTO;

            ScriptyLibrary lScriptyLibrary =  (ScriptyLibrary) lLib.getClass().getAnnotation(ScriptyLibrary.class);
            if(lScriptyLibrary != null)
            {
                lLibraryName = lScriptyLibrary.name();
                lLibraryType = lScriptyLibrary.type();
            }

            if(lLibraryType == ScriptyLibraryType.STATIC)
                throw new ExtensionException(String.format("Class '%s' is marked as a static library, but an instance was added.", lLib.getClass().getName()));
            
            addLibrary(lLibraryName, lLib, lLib.getClass());
        }
    }
    
    public CommandRepository getCommandRepository()
    {
        return commandRepo;
    }
    
    public CommandRepository getMacroRepository()
    {
        return macroRepo;
    }

    public void setCommandRepository(CommandRepository aCommandRepo)
    {
        commandRepo = aCommandRepo;
    }

    public void setMacroRepository(CommandRepository aMacroRepo)
    {
        macroRepo = aMacroRepo;
    }
}
