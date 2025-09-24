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
import com.sdicons.scripty.ExtensionException;
import com.sdicons.scripty.IExtensions;

import java.io.*;
import java.util.List;

/**
 * The Repl understands a simplified lisp syntax, only strings and lists can be expressed
 * on the command line. Command evaluation can have any kind of Java object as a result.
 * <p>
 * Outermost parenthesis should be omitted so that the user is not set back by the lisp syntax.
 * <p>
 * Explicitly lacking: data structures, data structure manipulation. It should be done using Java commands and an
 * underlying Java model. The language on top should help to manipulate the underlying Java model.
 * <p>
 * Editing commands:
 * <ul>
 *    <li><b><code>break</code></b> will delete the current command that is being edited and 
 *    will start a fresh line.</li>
 *    <li>A line with a <b><code>backslash</code></b> will automatically continue with the next line. This allows for some
 *    nice formatting.</li>
 * </ul>
 *
 */
public class ReplEngine
implements IExtensions
{
    private ExtensionRepositoryBuilder extensions = new ExtensionRepositoryBuilder();

    public static final String INPUT = "*input";
    public static final String OUTPUT = "*output";
    public static final String ERROR = "*error";

    private IContext context;
    private IEval eval;
    private StringBuilder currCmd = new StringBuilder();
    private Parser parser = new Parser();
    private static final String PROMPT_NORMAL = "> ";
    private static final String PROMPT_CONTINUE = "+ > ";
    private String prompt = "> ";
    private LineNumberReader input;
    private PrintWriter output;
    private PrintWriter error;
    private boolean stopRequested = false;
    private boolean interactiveMode = true;
    private Object lastResult = null;
    
    private static class NullWriter
    extends Writer
    {
        private Writer parentWriter;

        public NullWriter()
        {
            super();
        }

        public NullWriter(Object lock)
        {
            super(lock);
        }

        public NullWriter(Writer parentWriter)
        {
            super();
            this.parentWriter = parentWriter;
        }

        public Writer getParentWriter()
        {
            return parentWriter;
        }

        public void write(int c) throws IOException
        {
        }

        public void write(char cbuf[]) throws IOException
        {
        }

        public void write(String str) throws IOException
        {
        }

        public void write(String str, int off, int len) throws IOException
        {
        }

        public Writer append(CharSequence csq) throws IOException
        {
            return this;
        }

        public Writer append(CharSequence csq, int start, int end) throws IOException
        {
            return this;
        }

        public Writer append(char c) throws IOException
        {
            return this;
        }

        public void write(char cbuf[], int off, int len) throws IOException
        {
        }

        public void flush() throws IOException
        {
        }

        public void close() throws IOException
        {
        }
    }

    public ReplEngine()
    {
        context = new BasicContext();
        eval = new Eval(context);
        eval.setCommandRepo(extensions.getCommandRepository());
        eval.setMacroRepo(extensions.getMacroRepository());
    }
    
    private void recover()
    {
        // Clear the current command, since we are done with it.
        currCmd.setLength(0);
        // Change the command prompt to normal.
        setPrompt(PROMPT_NORMAL);
    }
    
    protected void handleLine(String aLine)
    throws ReplEngineException
    {
        // Quickly return if there is nothing to do.
        if(aLine == null)
        {
            // We did not receive a line, so the user did not press enter.
            // This is because some error occurred.
            // So we must issue an extra newline.
            if(interactiveMode) output.println();
            return;
        }
        if(aLine.trim().length() == 0)
            // We got an empty line, so the user pressed enter.
            return;
        
        // If the user doesn't know how to correct the command 
        // he can always start a new command on a fresh line.
        if(interactiveMode && "break".equals(aLine.trim().toLowerCase())) 
        {
            writeLine("Cancelling the command.");
            recover();  
            return;
        }
        
        if(aLine.endsWith("\\" ) && aLine.length() >= 2)
        {
        	// Line that ends with backslash, we will not attempt
        	// to parse the command.
        	currCmd.append(aLine.substring(0, aLine.length() - 1));
        	// Command not compete.
            setPrompt(PROMPT_CONTINUE);
        }
        else
        {
	        // Entering a command that consists of multiple lines.
	        // We test if the command can be parsed or not, and on that basis we decide if 
	        // the command is complete or not.
            // Keep the newlines so that we know which line produced the error.
	        currCmd.append(aLine).append("\n");
	        // Add implicit parenthesis if necessary.
	        String lCmd;
	        String lCurrCmdRepr = currCmd.toString().trim();
	        if(lCurrCmdRepr.startsWith("(") && lCurrCmdRepr.endsWith(")"))
	            // No need to add parenthesis, they are already there.
	            lCmd = lCurrCmdRepr;
	        else 
	            // Yes for the user convenience we add the parenthesis.
	            lCmd = "(" + currCmd.toString() + ")";        
	        Object lAst = parser.parseExpression(lCmd);
	        if(lAst instanceof List)
	        {                
	            recover();
	            // Time for evaluation.
	            try
	            {
	                lastResult = eval.eval(lAst);
	            }
	            catch (CommandException e)
	            {
	            	// Normal command error.
	               throw new ReplEngineException(input.getLineNumber(), 1, e.getMessage());
	            }
	        }
	        else if(lAst instanceof Token)
	        {
	            // Command might not be complete.
	            Token lErr = (Token) lAst;
	            if(lErr.isEof())
	            {
	                // Command not compete.
	                setPrompt(PROMPT_CONTINUE);
	            }
	            else
	            {
	                // Real error, we have to recover.
	                recover();
                    throw new ReplEngineException(input.getLineNumber(), lErr.getCol(), lErr.getValue());
	            }
	        }
	        else
	        {
	            // Error.
	            recover();
	        }       
        }
    }

    public void startInteractive()
    {
        this.startInteractive(System.in, System.out, System.out);
    }

    public void startInteractive(InputStream aIn, OutputStream aOut, OutputStream aErr)
    {
        interactiveMode = true;
        
        input = new LineNumberReader(new InputStreamReader(aIn));
        output = new PrintWriter(new OutputStreamWriter(aOut));
        error = new PrintWriter(new OutputStreamWriter(aErr));

        bindStreamsToContext();
        
        while(!stopRequested)
        {
            final String lLine = readLine();

            // Reset the line number if not accumulating a command.
            if(currCmd.length() <= 0) input.setLineNumber(1);
            
            try
            {
                handleLine(lLine);
            }
            catch(ReplEngineException e)
            {
                writeErrorLine(e.getMessage());
            }
            catch(Exception e)
            {
                // Probably a bug in a command.
                // A REPL has to keep on working, no matter what happens.
                writeErrorLine("ERROR: Unexpected exception occurred.");
                writeErrorLine(e.getMessage());
            }
        }
        stopRequested = false;
    }

    private void bindStreamsToContext()
    {
        context.getRootContext().defBinding(INPUT, input);
        context.getRootContext().defBinding(OUTPUT, output);
        context.getRootContext().defBinding(ERROR, error);
    }
    
    public Object startNonInteractive(String aExpr)
    throws  ReplEngineException
    {
        return this.startNonInteractive(new StringReader(aExpr), new NullWriter(), new NullWriter());
    }

    public Object startNonInteractive(InputStream aIn, OutputStream aOut, OutputStream aErr)
    throws ReplEngineException
    {
        return this.startNonInteractive(new InputStreamReader(aIn), new OutputStreamWriter(aOut),new OutputStreamWriter(aErr));
    }

    public Object startNonInteractive(InputStream aIn)
    throws ReplEngineException
    {
        return this.startNonInteractive(new InputStreamReader(aIn), new NullWriter(), new NullWriter());
    }

    public Object startNonInteractive(Reader aIn)
    throws ReplEngineException
    {
        return startNonInteractive(aIn, new NullWriter(), new NullWriter());
    }

    public Object startNonInteractive(Reader aIn, Writer aOut, Writer aErr)
    throws ReplEngineException
    {
        interactiveMode = false;
        lastResult = null;
        recover();

        input = new LineNumberReader(aIn);
        output = new PrintWriter(aOut);
        error = new PrintWriter(aErr);

        bindStreamsToContext();

        String lLine = readLine();
        while(!stopRequested && (lLine != null))
        {
            handleLine(lLine);
            lLine = readLine();
        }
        if(!stopRequested)
        {
            if(currCmd.length() > 0)
            {
                // The stream did not finish cleanly, there is a residue
                // that cannot be interpreted as a command.
                // We should report on this.
                throw new ReplEngineException(input.getLineNumber(), 1, "ERROR: Syntax error:\n" + currCmd.toString());
            }
        }
        stopRequested = false;
        return lastResult;
    }

    public void stop()
    {
        stopRequested = true;
    }

    private String readLine()
    {
        try
        {
            // Only write a prompt if there is no information to read
            // in the buffer. This could be the case when the user
            // pastes multiple lines, only the first will be read, and the
            // rest will be available in the buffer. We don't want to
            // write a prompt in this case.
            if(interactiveMode && !input.ready())
            {
                write(prompt);
            }
            return input.readLine();
        }
        catch (IOException e)
        {
            return null;
        }
    }

    private void write(String aTxt)
    {
       output.print(aTxt);
       output.flush();
    }

//    private void writeError(String aTxt)
//    {
//        error.print(aTxt);
//        error.flush();
//    }

    private void writeLine(String aLine)
    {
        output.println(aLine);
        output.flush();
    }

    private void writeErrorLine(String aLine)
    {
        error.println(aLine);
        error.flush();
    }

    public String getPrompt()
    {
        return prompt;
    }

    public void setPrompt(String prompt)
    {
        this.prompt = prompt;
    }

    public Object exec(String anExpression)
    throws CommandException
    {
        return eval.eval(parser.parseExpression(anExpression));
    }

    public IContext getContext()
    {
        return eval.getContext();
    }

    public void setContext(IContext aContext)
    {
        // Remember the context at repl level.
        context = aContext;
        bindStreamsToContext();

        // Register the new context.
        eval.setContext(context);
    }

    public void addCommand(String aName, ICommand aCommand)
    throws ExtensionException
    {
        extensions.addCommand(aName, aCommand);
    }

    public void addMacro(String aName, ICommand aMacro)
    throws ExtensionException
    {
        extensions.addMacro(aName, aMacro);
    }

    public void addLibraryClasses(Class ... aLibraryClasses)
    throws ExtensionException
    {
        extensions.addLibraryClasses(aLibraryClasses);
    }

    public void addLibraryInstances(Object ... aLibraryInstances)
    throws ExtensionException
    {
        extensions.addLibraryInstances(aLibraryInstances);
    }

    public CommandRepository getCommandRepository()
    {
        return extensions.getCommandRepository();
    }
    
    public void setCommandRepository(CommandRepository aCommands)
    {
        extensions.setCommandRepository(aCommands);
    }

    public CommandRepository getMacroRepository()
    {
        return extensions.getMacroRepository();
    }

    public void setMacroRepository(CommandRepository aMacros)
    {
        extensions.setMacroRepository(aMacros);
    }
}