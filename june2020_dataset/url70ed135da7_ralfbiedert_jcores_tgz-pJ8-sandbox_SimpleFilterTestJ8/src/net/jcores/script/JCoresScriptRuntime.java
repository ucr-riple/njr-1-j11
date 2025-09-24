/*
 * JCoresScriptDevTime.java
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

import static net.jcores.jre.CoreKeeper.$;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Properties;
import java.util.Set;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.jcores.jre.interfaces.functions.F0;
import net.jcores.jre.options.Option;
import net.jcores.jre.utils.internal.structures.ProfileInformation;
import net.jcores.script.util.console.JCoresConsole;

/**
 * Development time scripting environment.
 * 
 * @author Ralf Biedert
 * @since 1.0
 */
public class JCoresScriptRuntime extends JCoresScript {

    /** Our console */
    JCoresConsole consoleWindow = null;

    /** The banner to print */
    String banner;

    /*
     * (non-Javadoc)
     * 
     * @see net.jcores.shared.script.JCoresScript#pack()
     */
    protected JCoresScriptRuntime(String name, String[] args) {
        super(name, args);

        final ProfileInformation pi = $.profileInformation();
        this.banner = this.name + " Console - jCores Script (" + pi.numCPUs + " CPUs)";
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.jcores.script.ScriptingCommons#blacklist(net.jcores.script.Blacklist)
     */
    @SuppressWarnings("hiding")
    @Override
    public ScriptingCommons blacklist(Checklist blacklist) {
        super.blacklist(blacklist);
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.jcores.shared.script.JCoresScript#pack()
     */
    @Override
    public void pack(Option... options) {
        // Check if we should create a console
        if (System.console() == null && this.console) {

            // In case we need a console, create it in the EDT
            $.ui.edtnow(new F0() {
                @Override
                public void f() {
                    initUI();
                    JCoresScriptRuntime.this.consoleWindow = new JCoresConsole(JCoresScriptRuntime.this.banner);
                }
            });

            // Register our termination hook
            this.consoleWindow.addTerminationHook(Thread.currentThread());
        }
    }

    /**
     * Set the system user interface
     */
    void initUI() {
        // Beautiful menu bar on Mac OS
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        // Whoever designed these APIs must have had an 'Exception'al fetish ...
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        final KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        kfm.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() != KeyEvent.KEY_TYPED) return false;
                if (e.getKeyChar() == 'i') printCommonDebug();
                if (e.getKeyChar() == 'p') printPropertiesDebug();

                return false;
            }
        });
    }

    /**
     * Prints debug information into the console
     */
    void printCommonDebug() {
        // Print banner
        System.out.println();
        System.out.println();
        System.out.println(this.banner);

        // Print runtime information
        final Runtime runtime = Runtime.getRuntime();
        final long maxMemory = runtime.maxMemory() / (1024 * 1024);
        final long freeMemory = runtime.freeMemory() / (1024 * 1024);
        final long totalMemory = runtime.totalMemory() / (1024 * 1024);
        System.out.println("Memory: " + freeMemory + "Mb free, " + totalMemory + "Mb total, " + maxMemory + "Mb max");

        // Print thread information
        final int activeCount = Thread.activeCount();
        System.out.println("Threads: " + activeCount + " active");

        System.out.println();
    }

    /**
     * Prints properties to the output
     */
    void printPropertiesDebug() {
        // Print properties
        System.out.println();
        System.out.println();
        System.out.println("System Properties:");
        final Properties properties = System.getProperties();
        final Set<Object> set = properties.keySet();
        for (Object object : set) {
            System.out.println(object + ": " + properties.getProperty((String) object));
        }

        System.out.println();
    }
}
