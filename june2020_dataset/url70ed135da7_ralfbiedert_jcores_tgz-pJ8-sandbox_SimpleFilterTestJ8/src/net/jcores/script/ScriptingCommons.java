/*
 * ScriptingCommons.java
 * 
 * Copyright (c) 2011, Ralf Biedert, DFKI. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. Redistributions in binary form must reproduce the
 * above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of the author nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package net.jcores.script;

import static net.jcores.jre.CoreKeeper.$;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import net.jcores.jre.annotations.Beta;
import net.jcores.jre.options.Option;
import net.jcores.jre.utils.internal.Options;

/**
 * This class provides basic methods for all scripting functionality.
 * 
 * @author Ralf Biedert
 * @since 1.0
 */
public abstract class ScriptingCommons {

    /** The name of this script */
    protected final String name;

    /** By default we have no blacklist */
    protected Checklist blacklist = null;

    /** By default we have no whitelist */
    protected Checklist whitelist = null;


    /**
     * Creates a new {@link ScriptingCommons} object.
     * 
     * @param name The name of the application to create.
     */
    protected ScriptingCommons(String name) {
        this.name = name;
    }

    /**
     * Sets the blacklist with classes and files to ignore. If both a blacklist and whitelist
     * are given, first only the elements of the whitelist are considered, afterwards everything on
     * the blacklist is removed.
     * 
     * @since 1.0
     * @param blacklist The blacklist to set.
     * @return This object.
     */
    @SuppressWarnings("hiding")
    public ScriptingCommons blacklist(Checklist blacklist) {
        this.blacklist = blacklist;
        return this;
    }
    
    /**
     * Sets the whitelist with classes and files to ignore. If both a blacklist and whitelist
     * are given, first only the elements of the whitelist are considered, afterwards everything on
     * the blacklist is removed.
     * 
     * @since 1.0
     * @param whitelist The blacklist to set.
     * @return This object.
     */
    @SuppressWarnings("hiding")
    public ScriptingCommons whitelist(Checklist whitelist) {
        this.whitelist = whitelist;
        return this;
    }
    
    /**
     * Performs the actual pack operation with the given manifest.
     * 
     * @since 1.0
     * @param manifest The manifest (can be null).
     */
    protected void pack(Manifest manifest, Option... options) {
        final Options options$ = Options.$($, options);
        final ClassLoader systemloader = ClassLoader.getSystemClassLoader().getParent();
        final boolean debug = options$.debug();
        URLClassLoader loader = $(getClass().getClassLoader()).get(URLClassLoader.class, null);

        // Check the current loader
        if (loader == null) {
            System.err.println("Unable to get the classpath for this script. Cannot pack. Sorry.");
            return;
        }

        // Get all URLs
        List<URL> urls = new ArrayList<URL>();
        while (loader != null && loader != systemloader) {
            urls.addAll(Arrays.asList(loader.getURLs()));
            loader = $(loader.getParent()).get(URLClassLoader.class, null);
        }

        // We reverse the order so that elements early in the classpath (which are loaded
        // first), will be copied last, therefore overwriting other elements
        Collections.reverse(urls);

        // Now, go through all elements, when it's a JAR, unpack it, when its a dir, copy it
        final File tempdir = $.sys.tempdir();
        for (URL url : urls) {
            final File file = $(url).file().get(0);
            
            // When we have a whitelist, ignore what is not on the list
            if(this.whitelist != null && !this.whitelist.isListedFile(file)) {
                if (debug) System.out.println("Ignoring (not whitelisted) " + file);
                continue;
            }

            // Dont process blacklisted files ...
            if (this.blacklist != null && this.blacklist.isListedFile(file)) {
                if (debug) System.out.println("Ignoring (blacklisted) " + file);
                continue;
            }

            if (debug) System.out.println("Packing " + file);

            // Now pack according to the type
            if (file.getAbsolutePath().endsWith("jar")) {
                $(file).input().zipstream().unzip(tempdir.getAbsolutePath());
            } else {
                $(file).copy(tempdir.getAbsolutePath());
            }
        }

        // Check if the final classes were blacklisted.
        for (File file : $(tempdir).dir()) {
            // When the file was blacklisted, remove it
            if (this.blacklist != null && this.blacklist.isListedClass(file)) {
                if (debug) System.out.println("Removing (blacklisted)  " + file);
                $(file).delete();
            }
        }

        $(tempdir.getAbsoluteFile() + "/net/jcores/script/jcores.script.mode").file().delete().append("runtime");
        $(tempdir).jar(this.name + ".jar", manifest);

        // Finally output what we did, and quit
        System.out.println("Packed as '" + this.name + ".jar'");
        System.exit(0);
    }

    /**
     * Packs the current script / library into a single file ready for deployment. Check the console
     * for any output. The application will terminate after this function has been executed
     * However, at your script's runtime this command will be ignored.<br/>
     * <br/>
     * 
     * If you want to pack your application, <code>pack()</code> must be <b>the last</b> of all
     * scripting functions executed, i.e., no other function of this object may be executed
     * afterwards (which is a direct result of the remark above).
     * 
     * @param options The default jCores {@link Option} objects we support.
     */
    public void pack(Option... options) {
        final String appmain = $(Thread.currentThread().getStackTrace()).get(-1).getClassName();

        // Eventually pack the script
        final Manifest manifest = new Manifest();
        final Attributes mainAttributes = manifest.getMainAttributes();
        mainAttributes.putValue("Manifest-Version", "1.0");
        mainAttributes.putValue("Main-Class", appmain);

        pack(manifest, options);
    }

    /**
     * TODO
     * 
     * @param string
     * @return
     */
    @Beta
    protected JCoresScript src(String string) {
        // TODO Auto-generated method stub
        return null;
    }
}