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
import com.sdicons.scripty.parser.Lambda;
import com.sdicons.scripty.annot.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// General notes:
//  * It is intentionally based on the Java File object (for re-usability in other commands).
//  * As a consequence, a move only accepts 2 files, no wildcards.
//  * No wildcard globbing by the shell. The ls provides a grep on the short name. The find
//    command provides a lookup facility.
//  * Delete was not implemented for safety reasons.
//
@ScriptyLibrary(type = ScriptyLibraryType.INSTANCE)
@ScriptyNamedArgLists(std = {
        @ScriptyStdArgList(name = "noarg + quiet", named = {@ScriptyArg(name = "quiet", type = "Boolean", value = "false", optional = true)}),
        @ScriptyStdArgList(name = "file + quiet", optional = {@ScriptyArg(name = "file", type = "OneOf (String) (Instance java.io.File)", value = ".")}, named = {@ScriptyArg(name = "quiet", type = "Boolean", value = "false", optional = true)}),
        @ScriptyStdArgList(name = "2files + quiet", fixed = {@ScriptyArg(name = "arg1", type = "OneOf (String) (Instance java.io.File)", value = "null"), @ScriptyArg(name = "arg2", type = "OneOf (String) (Instance java.io.File)", value = "null")}, named = {@ScriptyArg(name = "quiet", type = "Boolean", value = "false", optional = true)})
})
public class FileLibrary
{
    private static final Pattern DRIVE = Pattern.compile("^\\p{Alpha}:.*$");
    private File currentDirectory;

    // Always quit. The option is allowed but does not have any effect.
    //
    @ScriptyCommand(name = "cd")
    @ScriptyStdArgList(
            optional = {
                    @ScriptyArg(name = "file", type = "OneOf (String) (Instance java.io.File)", value = ".")},
            named = {
                    @ScriptyArg(name = "quiet", type = "Boolean", value = "false", optional = true)
            })
    public File cd(
            @ScriptyParam("file") Object aFileRepr)
            throws CommandException
    {
        File lFile;
        if (aFileRepr instanceof File) lFile = (File) aFileRepr;
        else lFile = resolveFile((String) aFileRepr);
        if (!lFile.isDirectory())
            throw new CommandException(String.format("Cannot go to '%s', it is not a directory.", lFile));
        else currentDirectory = lFile;
        return lFile;
    }

    // Print the currrent directory and return the File object.
    //
    @ScriptyCommand(name = "pwd")
    @ScriptyRefArgList(ref = "noarg + quiet")
    public File pwd(@ScriptyParam("quiet") boolean aQuiet, @ScriptyBindingParam("*output") PrintWriter aWriter)
            throws CommandException
    {
        final File lCurDir = getCurrentDirectory();

        try
        {
            if (!aQuiet) aWriter.println(lCurDir.getCanonicalPath());
            return lCurDir;
        } catch (IOException e)
        {
            throw new CommandException(String.format("Cannot calculate the canonical name for '%s'.", lCurDir), e);
        }

    }

    // Print a listing of the current directory and return it als an array.
    //  - grep=regexp: applied to the absolute pathname.
    //  - quiet=true|*false.
    //  - files=*true|false : include files.
    //  - dirs=*true|false : include directories.
    //  - exec=lambda: process the files using a lambda. Processing is done after grepping and filtering.
    //
    @ScriptyCommand(name = "ls")
    @ScriptyStdArgList(
            optional = {
                    @ScriptyArg(name = "file", type = "OneOf (String) (Instance java.io.File)", value = ".")},
            named = {
                    @ScriptyArg(name = "quiet", type = "Boolean", value = "false", optional = true),
                    @ScriptyArg(name = "files", type = "Boolean", value = "true", optional = true),
                    @ScriptyArg(name = "dirs", type = "Boolean", value = "true", optional = true),
                    @ScriptyArg(name = "grep", type = "String", value = ".*", optional = true),
                    @ScriptyArg(name = "exec", type = "Instance com.sdicons.scripty.parser.Lambda nullAllowed=true", value = "{null}", optional = true),
                    @ScriptyArg(name = "recursive", type = "Boolean", value = "false", optional = true)
            })
    public Object[] ls(
            @ScriptyParam("quiet") boolean aQuiet,
            @ScriptyParam("grep") String aGrep,
            @ScriptyParam("files") boolean aFiles,
            @ScriptyParam("dirs") boolean aDirs,
            @ScriptyParam("exec") Lambda aLambda,
            @ScriptyParam("recursive") boolean aRecursive,
            @ScriptyParam("file") Object aFileRepr,
            @ScriptyBindingParam("*output") PrintWriter aWriter,
            IContext aCtx,
            IEval aEval)
            throws CommandException
    {
        try
        {
            File lFile;
            if (aFileRepr instanceof File) lFile = (File) aFileRepr;
            else lFile = resolveFile((String) aFileRepr);

            if (!lFile.isDirectory())
                throw new CommandException(String.format("Cannot show '%s', it is not a directory.", lFile));

            // Calculate the grep pattern.
            Pattern lPattern = Pattern.compile(aGrep);
            // The worklist handles recursion.
            List<File> lWorkList = new LinkedList<File>();
            List<File> lResultList = new LinkedList<File>();
            // Push our starting point on the directory worklist.
            lWorkList.add(lFile);

            while (lWorkList.size() > 0)
            {
                // Get one from the worklist.
                File lDir = lWorkList.remove(0);
                File[] lXfiles = lDir.listFiles();

                // A guard, listing files can result in
                // a null value ... meaning that there probably are
                // security constraints on the directory.
                if (lXfiles != null)
                {
                    for (File lXFile : lXfiles)
                    {
                        if (!".".equals(lXFile.getName()) && !"..".equals(lXFile.getName()))
                        {
                            if (lXFile.isDirectory())
                            {
                                // Remember if we want dirs.
                                if (aDirs && lPattern.matcher(lXFile.getCanonicalPath()).matches())
                                    lResultList.add(lXFile);
                                // Add to the worklist if recursive.
                                if (aRecursive) lWorkList.add(lXFile);
                            } else if (lXFile.isFile())
                            {
                                // Remember if we want files.
                                if (aFiles && lPattern.matcher(lXFile.getCanonicalPath()).matches())
                                    lResultList.add(lXFile);
                            }
                        }
                    }
                }
            }

            for (File lEntry : lResultList)
            {
                if (aLambda != null) aEval.eval(lambdaMacro(aLambda, lFile), aCtx);

                if (!aQuiet)
                {
                    // Get basic file info.
                    String lDirFlag = lEntry.isDirectory() ? "d" : " ";
                    String lReadFlag = lEntry.canRead() ? "r" : " ";
                    String lWriteFlag = lEntry.canWrite() ? "w" : " ";
                    String lHiddenFlag = lEntry.isHidden() ? "h" : " ";
                    long lLastModified = lEntry.lastModified();
                    // Print the info.
                    if (aWriter != null)
                        aWriter.println(String.format("%s%s%s%s  %5$tF %5$tR  %6$s", lDirFlag, lReadFlag, lWriteFlag, lHiddenFlag, lLastModified, lEntry.getCanonicalFile()));
                }
            }
            return lResultList.toArray();
        } catch (IOException e)
        {
            throw new CommandException(String.format("Internal error.\n%s", e.getMessage()), e);
        }
    }


    // Not implemented.
    //
    @ScriptyCommand(name = "cat")
    public void cat()
    {

    }

    // Resolve is the same as a 'quiet' pwd, but pwd only returns directories,
    // whereas rslv can also resolve to files.
    // This command is *very* important for integration with other libraries.
    // The other command libraries should not be dependent on this one, they
    // can simply request a File argument and this command can resolve the path to the File.
    //
    @ScriptyCommand(name = "rslv")
    @ScriptyRefArgList(ref = "file + quiet")
    public File rslv(@ScriptyParam("file") Object aFileRepr)
            throws CommandException
    {
        File lFile;
        if (aFileRepr instanceof File) lFile = (File) aFileRepr;
        else lFile = resolveFile((String) aFileRepr);
        return lFile;
    }

    // Rename a file.
    // It does not use wildcards sinds the implementation is based on the Java File class which
    // does not accept wildcards.
    //
    @ScriptyCommand(name = "mv")
    @ScriptyRefArgList(ref = "2files + quiet")
    public File mv(@ScriptyParam("arg1") Object aArg1, @ScriptyParam("arg2") Object aArg2)
            throws CommandException
    {
        File lFrom;
        if (aArg1 instanceof File) lFrom = (File) aArg1;
        else lFrom = resolveFile((String) aArg1);

        File lTo;
        if (aArg2 instanceof File) lTo = (File) aArg2;
        else lTo = resolveFile((String) aArg2);

        boolean lResult = lFrom.renameTo(lTo);

        if (!lResult)
            throw new CommandException("Rename failure.");
        return lTo;
    }

    @SuppressWarnings("unchecked")
    private List lambdaMacro(Lambda aLambda, File aFile)
    {
        List lMacro = new ArrayList();
        lMacro.add(aLambda);
        lMacro.add(aFile);
        return lMacro;
    }

    private File resolveFile(String aPath)
            throws CommandException
    {
        // Check the setup. Other command libraries
        // might invoke this method directly, and in that case
        // this is the first initialization point.
        initCurrDir();

        File lFile;
        final Matcher lMatcher = DRIVE.matcher(aPath);
        if (aPath.startsWith("/") || lMatcher.matches())
            // Absolute path when it does not start with a /
            // or when it does not start with a drive designation.
            lFile = new File(aPath);
        else
            // Relative path in all other cases.
            lFile = new File(getCurrentDirectory(), aPath);
        return lFile;
    }

    private void initCurrDir()
            throws CommandException
    {
        if (currentDirectory == null)
            currentDirectory = new File(".");
    }


    private File getCurrentDirectory()
            throws CommandException
    {
        // Check the setup. Other command libraries
        // might invoke this method directly, and in that case
        // this is the first initialization point.
        initCurrDir();
        return currentDirectory;
    }
}
