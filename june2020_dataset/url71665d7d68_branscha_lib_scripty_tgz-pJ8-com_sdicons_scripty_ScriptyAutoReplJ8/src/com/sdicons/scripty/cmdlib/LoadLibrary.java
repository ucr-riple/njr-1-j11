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
import com.sdicons.scripty.parser.IEval;
import com.sdicons.scripty.spec.type.ITypeSpec;
import com.sdicons.scripty.spec.type.TypeSpecException;
import com.sdicons.scripty.annot.*;
import com.sdicons.scripty.repl.ReplEngine;
import com.sdicons.scripty.repl.ReplEngineException;
import com.sdicons.scripty.spec.type.TypeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 *    <li><b><code>load</code></b> Load and execute one or more scripts. If the file exists and is readable, it will be rememberd for the reload.<code>(load file | classpath:/resource | cp:/resource ...)</code></li>
 *    <li><b><code>reload</code></b> Reload previously loaded files. A file is remembered if it existed and was readable. <code>(reload)</code></li>
 * </ul>
 */
public class LoadLibrary
{
    private List<Loader> loaders = new ArrayList<Loader>();

    @ScriptyCommand(name="load")
    @ScriptyVarArgList(vararg = @ScriptyArg(name="loaders", type="Custom com.sdicons.scripty.cmdlib.LoadLibrary$LoaderType"))
    public void load(@ScriptyParam("loaders") Object[] aLoaders, IContext aCtx, IEval aEval)
    throws CommandException
    {
        List<Loader> lLoaders = new ArrayList<Loader>();
        for(Object lLoader : aLoaders)
        {
            lLoaders.add((Loader)lLoader);
        }
        internalLoad(lLoaders, aEval, aCtx);
    }
    
    @ScriptyCommand(name="reload")
    @ScriptyStdArgList()
    public void reload(IContext aContext, IEval aEval)
    throws CommandException
    {
        List<Loader> lLoaders = new ArrayList<Loader>();
        lLoaders.addAll(loaders);
        internalLoad(lLoaders, aEval, aContext);
    }
    
    private void internalLoad(List<Loader> aLoaders, IEval aEval, IContext aContext)
    throws CommandException
    {
        Object lInput = aContext.getBinding(ReplEngine.INPUT);
        Object lOutput = aContext.getBinding(ReplEngine.OUTPUT);
        Object lError = aContext.getBinding(ReplEngine.ERROR);
   
        final ReplEngine lEngine = new ReplEngine();
        lEngine.setContext(aContext);
        lEngine.setCommandRepository(aEval.getCommandRepo());
        lEngine.setMacroRepository(aEval.getMacroRepo());
        
        try
        {
            for(Loader lLoader: aLoaders)
            {
                loaders.remove(lLoader);

                InputStream lIn = null;
                try
                {
                    lIn = lLoader.getStream();
                    lEngine.startNonInteractive(lIn);
                }
                catch (ReplEngineException e)
                {
                    final String lMsg = String.format("While loading '%s'.\n%s", lLoader.toString(), e.getMessage());
                    throw new CommandException(lMsg);
                }
                finally
                {
                    // Cleanup. Ignore all exceptions here.
                    if(lIn != null) try{lIn.close();}catch(Exception ignored){}
                }
                
                loaders.add(lLoader);
            }
        }
        
        finally
        {
            aContext.setBinding(ReplEngine.INPUT, lInput);
            aContext.setBinding(ReplEngine.OUTPUT, lOutput);
            aContext.setBinding(ReplEngine.ERROR, lError);
        }
    }

    public static interface Loader
    {
        InputStream getStream() throws CommandException;
        void checkValidity() throws CommandException;
    }

    public static class FileLoader
    implements Loader
    {
        private File file;

        public FileLoader(File aFile)
        {
            file = aFile;
        }

        public FileLoader(String aPath)
        {
            file = new File(aPath);
        }

        public void checkValidity() throws CommandException
        {
            if(!file.exists() || !file.isFile())
                throw new CommandException(String.format("The file does not exist: \"%s\".", file.getAbsolutePath()));
            if(!file.canRead())
                throw new CommandException(String.format("The file is not readable: \"%s\".", file.getAbsolutePath()));
        }

        public InputStream getStream()
                throws CommandException
        {
            try
            {
                return new FileInputStream(file);
            }
            catch (FileNotFoundException e)
            {
                throw new CommandException(String.format("Exception while opening the file: '%s'.\n%s",file.getAbsolutePath(), e.getMessage()), e);
            }
        }

        @Override
        public String toString()
        {
            return file.getAbsolutePath();
        }
    }

    public static class ClasspathLoader
    implements Loader
    {
        String resource;

        public ClasspathLoader(String aResource)
        {
            resource = aResource;
        }

        public void checkValidity() throws CommandException
        {
            if (this.getClass().getResourceAsStream(resource) == null)
                throw new CommandException(String.format("The resource does not exist: \"%s\".", resource));
        }

        public InputStream getStream()
                throws CommandException
        {
            return this.getClass().getResourceAsStream(resource);
        }

        @Override
        public String toString()
        {
            return "classpath:" + resource;
        }
    }

    @SuppressWarnings("unused")
    public static class LoaderType
    implements ITypeSpec
    {
        public String getSpecName()
        {
            return "loader";
        }

        public Object guard(Object aArg, IContext aCtx)
        throws TypeSpecException
        {
            Loader lCandLdr;
            if(aArg instanceof String)
            {
                String lPath = (String) aArg;
                if(lPath.startsWith("classpath:"))
                {
                    lCandLdr = new ClasspathLoader(lPath.substring(10));
                }
                else if(lPath.startsWith("cp:"))
                {
                    lCandLdr = new ClasspathLoader(lPath.substring(3));
                }
                else
                {
                    // Interprete the string as a pathname.
                    lCandLdr = new FileLoader(lPath);
                }
            }
            else if (aArg instanceof File)
            {
                // Easy for us.
                lCandLdr = new FileLoader((File) aArg);
            }
            else
            {
                // Don't know how to handle this.
                throw new TypeSpecException(TypeUtil.msgBadRepr(getSpecName(), aArg.toString()));
            }

            try
            {
                lCandLdr.checkValidity();
            }
            catch (CommandException e)
            {
                throw new TypeSpecException(String.format("The resource '%s' is not available, it the contents cannot be accessed.", lCandLdr.toString()));
            }
            return lCandLdr;
        }
    }
}
