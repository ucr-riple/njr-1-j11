/*
 * Checklist.java
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
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * The foundation or our black- and whitelist.
 * 
 * @author Ralf Biedert
 * @since 1.0
 */
public class Checklist {

    /** The files to blacklist */
    private Collection<String> files = $.list();
    
    /** The classes to blacklist */
    private Collection<String> classes = $.list();

    /** Non-public consntructor */
    protected Checklist() {
        super();
    }

    /**
     * Blacklists the given file.
     * 
     * @since 1.0
     * @param file The file to blacklist.
     * @return This object.
     */
    public Checklist file(String file) {
        this.files.add(file);
        return this;
    }

    /**
     * Blacklists the given class.
     * 
     * @since 1.0
     * @param file The class to blacklist.
     * @return This object.
     */
    public Checklist clazz(String file) {
        this.classes.add(file);
        return this;
    }

    /**
     * Tests if the given file is listed.
     * 
     * @since 1.0
     * @param file The file to test.
     * @return True if it is listed.
     */
    public boolean isListedFile(File file) {
        String f = file.getAbsolutePath();
        f = f.replaceAll("\\\\", "/");
        
        for (String s : this.files) {
            
            final Pattern pattern = Pattern.compile(".*" + s + ".*");
            if(pattern.matcher(f).matches()) return true;
        }
    
        return false;
    }

    /**
     * Tests if the given class is listed.
     * 
     * @since 1.0
     * @param clazz The class to test.
     * @return True if it is listed.
     */
    public boolean isListedClass(String clazz) {
        for (String s : this.classes) {
            final Pattern pattern = Pattern.compile(".*" + s + ".*");
            if(pattern.matcher(clazz).matches()) return true;
        }
    
        return false;
    }

    /**
     * Tests if the given class is listed.
     * 
     * @since 1.0
     * @param clazz The class to test.
     * @return True if it is listed.
     */
    public boolean isListedClass(File clazz) {
        String clz = clazz.getAbsolutePath().replaceAll("\\\\", "/");
        clz = clz.replaceAll("/", ".");
    
       return isListedClass(clz);
    }

}