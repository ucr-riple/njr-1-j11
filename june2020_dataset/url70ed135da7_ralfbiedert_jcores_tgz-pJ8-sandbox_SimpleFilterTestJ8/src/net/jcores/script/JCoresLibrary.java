/*
 * JCoresLib.java
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



/**
 * JCoresLib allows you to quickly create a fully self contained Java library for your 
 * project. To create a library, put these lines at the very beginning
 * of some main:<br/>
 * <br/>
 * 
 * <code>JCoresLibrary.LIBRARY("MyApp").pack()</code><br/>
 * <br/>
 * 
 * This will produce a file <code>MyApp.jar</code>, that is fully self contained and can be
 * started with <code>java -jar MyApp.jar</code>.<br/><br/>
 * 
 * <b>NOTE:</b> Scripting does NOT work properly with dynamic class loading. If you application
 * does any sort of classpath tweaks, or loads code on demand, packing the script will probably 
 * succeed, but running it will fail. 
 * 
 * @author Ralf Biedert
 * @since 1.0
 */

public class JCoresLibrary extends ScriptingCommons {
    /**
     * Creates a new {@link JCoresLibrary} object.
     * 
     * @param name The object to create.
     */
    protected JCoresLibrary(String name) {
        super(name);
    }

    
    /**
     * Creates a library object for the current application. Call at the very first 
     * position of your <code>main()</code> (see the example above).
     * 
     * @param name The name of your library.
     * @return A {@link JCoresLibrary} object.
     */
    public static JCoresLibrary LIBRARY(String name) {
        return new JCoresLibraryDevtime(name);
    }

}
