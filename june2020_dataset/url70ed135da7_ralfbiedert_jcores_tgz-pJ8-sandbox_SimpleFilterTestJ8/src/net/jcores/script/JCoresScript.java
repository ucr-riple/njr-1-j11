/*
 * JCoresScript.java
 * 
 * Copyright (c) 2011, Ralf Biedert All rights reserved.
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

import net.jcores.script.input.Input;
import net.jcores.script.output.Output;

/**
 * JCores scripting allows you to quickly hack a small (script-like) Java / jCores application
 * and create a self contained JAR. Usually this is done with small jCores applications that
 * should be portable. To create a <i>script</i>, put these lines at the very beginning
 * of your application's main:<br/>
 * <br/>
 * 
 * <code>JCoresScript.SCRIPT("MyApp", args).pack()</code><br/>
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
public abstract class JCoresScript extends ScriptingCommons {
    /** If set to true, a console will always be shown */
	protected boolean console = false;
	
	/** The input object containing all the parameters we have */
	protected Input input = null;

    /** The output object containing all the parameters we have */
    protected Output output = null;

    /** If set to true, this script is assumed to be transferable */
    protected boolean transferable = false;
    
    /** The args the application received at start */
    protected final String[] args;

    /**
     * Store parameters.
     * 
     * @param name
     * @param args
     */
    protected JCoresScript(String name, String[] args) {
        super(name);
        
        this.args = args;
    }

 
    /**
     * Creates a script object for the current application. Call at the very first 
     * position of your <code>main()</code> (see the example above).
     * 
     * @param name The name of your application.
     * @param args The command line arguments that were passed to main.
     * @return A {@link JCoresScript} object.
     */
    public static JCoresScript SCRIPT(String name, String args[]) {

        // If we have the flag the devtime created, go into runtime mode
        if (JCoresScript.class.getResource("jcores.script.mode") != null) { return new JCoresScriptRuntime(name, args); }

        return new JCoresScriptDevtime(name, args);
    }
    
    
    /**
     * Call this method to register inputs and outputs to your script.
     * 
     * @param _input The input to accept.
     * @param _output The output to create.
     * @return This object
     */
    public JCoresScript io(Input _input, Output _output) {
        this.input = _input;
        this.output = _output;
        return this;
    }
    
    
    /**
     * Call this method to register inputs and outputs to your script.
     * 
     * @param _input The input to accept.
     * @return This object
     */
    public JCoresScript io(Input _input) {
        return io(_input, null);
    }
    
    /**
     * Call this method to register inputs and outputs to your script.
     * 
     * @param _output The output to create.
     * @return This object
     */
    public JCoresScript io(Output _output) {
        return io(null, _output);
    }
    
    
    /**
     * Call this method if you want your script to always provide a console. If a terminal
     * or console is detected at runtime nothing is being done. If no console is detected,
     * a console window will be spawned.
     * 
     * @return This object.
     */
    public JCoresScript console() {
    	this.console = true;
    	return this;
    }
    
    
    /**
     * Call this method if you want your script to be transferable to another host. In that case,
     * the script will take all inputs with it, migrate to the other host, execute there, and then 
     * return with its outputs.
     * 
     * @return This object.
     */
    public JCoresScript transferable() {
        this.transferable = true;
        return this;
    }
}
