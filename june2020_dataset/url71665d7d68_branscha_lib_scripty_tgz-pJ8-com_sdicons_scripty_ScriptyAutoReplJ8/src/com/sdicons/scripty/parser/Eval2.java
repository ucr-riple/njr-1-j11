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

package com.sdicons.scripty.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Eval2
extends AbstractEval
{
    public Eval2()
    {
        this(new BasicContext());
    }

    public Eval2(IContext aContext)
    {
        super(aContext);
    }

    public static interface IFrameHandler
    {
        public void init(EvalStack aStack, StackFrame aFrame)
        throws CommandException;

        public void handleFrame(IEval aEval, EvalStack aStack, StackFrame aFrame)
        throws CommandException;
    }

    public static class StackFrame
    {
        private static final Object[] EMPTY = new Object[0];

        private Object expr;
        private Object[] data;
        private int dataptr;
        private IContext ctx;
        private Object result;
        private boolean evaluated;
        private IFrameHandler handler;

        public StackFrame(Object aExpr, IContext aCtx)
        {
            expr = aExpr;
            data = EMPTY;
            dataptr = 0;
            ctx = aCtx;
            result = null;
            evaluated = false;
            handler = null;
        }

        public IContext getCtx()
        {
            return ctx;
        }

        public int getDataptr()
        {
            return dataptr;
        }

        public Object getExpr()
        {
            return expr;
        }

        public Object[] getData()
        {
            return data;
        }

        public Object getResult()
        {
            return result;
        }

        public boolean isEvaluated()
        {
            return evaluated;
        }

        public void setResult(Object aResult)
        {
            result = aResult;
            evaluated = true;
        }

        public void pushData(Object aData)
        {
            if(dataptr < 0 || dataptr > data.length) throw new IllegalArgumentException("Data pointer out of bounds in stackframe.");

            // Pushing a piece of data into a slot after the  normal slots will set the result.
            if(dataptr == data.length) setResult(aData);
            else data[dataptr] = aData;
            dataptr++;
        }

        public void setDataPtr(int aPos)
        {
            dataptr = aPos;
        }

        public void allocateData(int aSize)
        {
            data = new Object[aSize];
            reset();
        }

        public void setHandler(IFrameHandler aHandler)
        {
            handler = aHandler;
        }

        public IFrameHandler getHandler()
        {
            return handler;
        }

        public void setCtx(IContext aCtx)
        {
            ctx = aCtx;
        }

        /**
         * Restart evaluation of the frame.
         * The data pointer is reset to to the beginning.
         * All calculated frame slots are discarded, but the same allocated storage space remains available.
         */
        public void reset()
        {
            setDataPtr(0);
            Arrays.fill(data, null);
        }

        /**
         *  Turn the frame back a step if possible.
         *
         */
        public void backStep()
        {
            if(isEvaluated())
            {
                // The frame was completely evaluated.
                // We turn off the evaluation flag and point the datapointer to
                // the result slot (this is one after the data slots).

                evaluated = false;
                if(data.length >= 0) dataptr = data.length;
            }
            else
            {
                // We point the datapointer to the previous slot
                // and reset the current slot.
                if(dataptr > 0 && dataptr <= data.length)
                {
                    dataptr--;
                    data[dataptr] = null;
                }
            }
        }
    }

    public static class EvalStack
    {
        private List<StackFrame> stack = new ArrayList<StackFrame>();
        private EvalStack prevstack;

        public EvalStack(EvalStack aPrevious)
        {
            prevstack = aPrevious;
        }

        public EvalStack getPrevStack()
        {
            return prevstack;
        }

        public boolean isEmpty()
        {
            return stack.size() <= 0;
        }

        public StackFrame top()
        {
            if(isEmpty()) throw new IllegalArgumentException("top on empty stack.");
            return stack.get(stack.size() - 1);
        }

        public StackFrame pop()
        {
            if(isEmpty()) throw new IllegalArgumentException("pop on empty stack.");
            return stack.remove(stack.size() - 1);
        }

        public void push(Object aExpr, IContext aCtx)
        {
            StackFrame lFrame = new StackFrame(aExpr, aCtx);
            stack.add(lFrame);
        }

        public int size()
        {
            return stack.size();
        }

        public EvalStack merge(EvalStack aNested)
        {
            if(aNested != null) this.stack.addAll(aNested.stack);
            return this;
        }

        public boolean hasMoreSteps()
        {
            // Only take the current stack into account.
            // Do not consider the previous stacks, these are the responsability
            // of other invocations.
            // The semantics is "steps during this invocation", the other stacks are for other invocations.
            return size() > 1 || (size() == 1 && !top().isEvaluated());
        }

        public void reset()
        {
            if(size()>=1)
            {
                // Delete all stack frames except for the very first one.
                StackFrame lBottom = stack.get(0);
                stack.clear();
                stack.add(lBottom);
                // Reset the slot pointer.
                // Reset the slot data.
                lBottom.reset();
            }
        }

        public void dropFrame()
        {
            if(size() > 1)
            {
                // Remove the topmost stack frame.
                stack.remove(size() - 1);
            }
        }

        @Override
        public String toString()
        {
            // Accumulate the result here.
            final StringBuilder lBuilder = new StringBuilder();

            // We print the stack from top to bottom, we have to start at the end of the list.
            for(int i = stack.size() - 1; i >= 0; i--)
            {
                final StackFrame lFrame = stack.get(i);
                // Show the expression itself.
                if(lFrame.getExpr() != null)
                    lBuilder.append(lFrame.getExpr().toString());
                // Show the frame slots if there are any.
                if(lFrame.getData() != StackFrame.EMPTY)
                {
                    lBuilder.append(":").append(lFrame.getDataptr());
                    if(lFrame.getDataptr() == lFrame.getData().length) lBuilder.append("!");
                    lBuilder.append(" ~ ").append(Arrays.deepToString(lFrame.getData()));
                }
                // Show the result of the evaluation if we have it.
                if(lFrame.isEvaluated())
                    lBuilder.append(" ==> ").append(lFrame.getResult());
                // Next frame.
                lBuilder.append("\n");
            }

            if(prevstack != null) lBuilder.append("----------\n").append(prevstack.toString());
            return lBuilder.toString();
        }
    }

    public static class EvalEvent
    extends EventObject
    {
        private static final long serialVersionUID = -514101948441517829L;
        private EvalStack stack;
        private Object result;
        private Exception exception;

        public EvalEvent(Object source)
        {
            super(source);
        }

        public EvalStack getStack()
        {
            return stack;
        }

        public Object getResult()
        {
            return result;
        }

        public Exception getException()
        {
            return exception;
        }
    }

    private final EvalEvent EVENT = new EvalEvent(this);

    public static interface EvalListener
    {
        void startEval(EvalEvent aEvent);
        void stepEvent(EvalEvent aEvent);
        void finishedEval(EvalEvent aEvent);
        void receivedException(EvalEvent aEvent);
    }

    // It is an array because it is hit an *enormous* amout of times.
    // If it would be a hashed based structure, it would produce
    // a humongous amount of iterator artifacts!
    private EvalListener[] listeners = new EvalListener[0];

    public void addEvalListener(EvalListener aListener)
    {
        Set<EvalListener> lListeners = new HashSet<EvalListener>(Arrays.asList(listeners));
        lListeners.add(aListener);
        listeners = lListeners.toArray(listeners);
    }

    public void removeEvalListener(EvalListener aListener)
    {
        Set<EvalListener> lListeners = new HashSet<EvalListener>(Arrays.asList(listeners));
        lListeners.remove(aListener);
        listeners = lListeners.toArray(listeners);
    }

    private void fireStepEvent(EvalStack aStack)
    {
        EVENT.stack = aStack;
        EVENT.result = null;
        EVENT.exception = null;
        for(EvalListener lListener : listeners) lListener.stepEvent(EVENT);
    }

    private void fireStartEval(EvalStack aStack)
    {
        EVENT.stack = aStack;
        EVENT.result = null;
        EVENT.exception = null;
        for(EvalListener lListener : listeners) lListener.startEval(EVENT);
    }

    private void fireFinishedEval(EvalStack aStack, Object aResult)
    {
        EVENT.stack = aStack;
        EVENT.result = aResult;
        EVENT.exception = null;
        for(EvalListener lListener : listeners) lListener.finishedEval(EVENT);
    }

    private void fireReceivedException(EvalStack aStack, Exception aException)
    {
        EVENT.stack = aStack;
        EVENT.result = null;
        EVENT.exception = aException;
        for(EvalListener lListener : listeners) lListener.receivedException(EVENT);
    }

    public Object eval(Object aExpr)
    throws CommandException
    {
        return eval(aExpr, getContext());
    }

    private EvalStack currstack;

    public Object eval(Object aExpr, IContext aCtx)
    throws CommandException
    {
        // Create a new stack, and link the old stack to it.
        // This system is meant to work cross-commands.
        currstack = new EvalStack(currstack);

        // Push the current expression on the new stack.
        currstack.push(aExpr, aCtx);
        // Notify listeners about the start of evaluation.
        fireStartEval(currstack);

        // Lets do it!
        final Object lResult;
        try
        {
            lResult = eval2(currstack);
        }
        finally
        {
            // Replace the current stack with the previous one if there is one.
            // The stack restoring code is in a finally block because we definitively
            // have to to this in case of exception.
            currstack = currstack.getPrevStack();
        }

        // Notify listeners that we reached a result.
        fireFinishedEval(currstack, lResult);
        return lResult;
    }

    protected Object eval2(EvalStack aStack)
    throws CommandException
    {
        while(aStack.hasMoreSteps()) step(aStack);
        if(aStack.size() == 1 && aStack.top().isEvaluated()) return aStack.top().getResult();
        else throw new CommandException("Internal eval error, invalid state.", aStack);
    }

    private static final IFrameHandler ATOMIC_HANDLER = new AtomicHandler();
    private static final IFrameHandler PAIR_HANDLER = new PairHandler();

    private static final IFrameHandler QUOTE_HANDLER = new QuoteHandler();
    private static final IFrameHandler IF_HANDLER = new IfHandler();
    private static final IFrameHandler WHILE_HANDLER = new WhileHandler();
    private static final IFrameHandler AND_HANDLER = new AndHandler();
    private static final IFrameHandler OR_HANDLER = new OrHandler();
    private static final IFrameHandler NOT_HANDLER = new NotHandler();
    private static final IFrameHandler SET_HANDLER = new SetHandler();
    private static final IFrameHandler LET_HANDLER = new LetHandler();
    private static final IFrameHandler GET_HANDLER = new GetHandler();
    private static final IFrameHandler LAMBDA_HANDLER = new LambdaHandler();
    private static final IFrameHandler DEFUN_HANDLER = new DefunHandler();
    private static final IFrameHandler TIMER_HANDLER = new TimerHandler();

    private final MacroHandler MACRO_HANDLER = new MacroHandler();
    private final StandardHandler STANDARD_HANDLER = new StandardHandler();

    public void setCommandRepo(CommandRepository aRepo)
    {
        STANDARD_HANDLER.setCommands(aRepo);
    }

    public CommandRepository getCommandRepo()
    {
        return STANDARD_HANDLER.getCommands();
    }

    public void setMacroRepo(CommandRepository aRepo)
    {
        MACRO_HANDLER.setMacros(aRepo);
    }

    public CommandRepository getMacroRepo()
    {
        return MACRO_HANDLER.getMacros();
    }

    private static final Map<String, IFrameHandler> HANDLERS = new HashMap<String, IFrameHandler>();
    static
    {
        HANDLERS.put("quote", QUOTE_HANDLER);
        HANDLERS.put("if", IF_HANDLER);
        HANDLERS.put("while", WHILE_HANDLER);
        HANDLERS.put("and", AND_HANDLER);
        HANDLERS.put("or", OR_HANDLER);
        HANDLERS.put("not", NOT_HANDLER);
        HANDLERS.put("set", SET_HANDLER);
        HANDLERS.put("defvar", SET_HANDLER);
        HANDLERS.put("let", LET_HANDLER);
        HANDLERS.put("let*", LET_HANDLER);
        HANDLERS.put("get", GET_HANDLER);
        HANDLERS.put("lambda", LAMBDA_HANDLER);
        HANDLERS.put("defun", DEFUN_HANDLER);
        HANDLERS.put("timer", TIMER_HANDLER);
    }

    public void step(EvalStack aStack)
    throws CommandException
    {
        final StackFrame lFrame = aStack.top();
        final Object lExpr = lFrame.getExpr();
        final IFrameHandler lHandler = lFrame.getHandler();

        try
        {
            if(lFrame.isEvaluated())
            {
                // We are done evaluating the current stackframe.
                // We can discard it and use its results.

                if(aStack.size() > 1 || aStack.getPrevStack() != null)
                {
                    // RETURN.
                    // Substitute the result of evaluation in the
                    // previous expression on the stack.
                    aStack.pop();
                    final StackFrame lTop = aStack.top();
                    lTop.pushData(lFrame.getResult());
                }
                else
                {
                    // ERROR.
                    // There are no steps to take anymore.
                    // The stack is completely evaluated.
                    throw new CommandException("No steps anymore.", aStack);
                }
            }
            else
            {
                // Still some work to do on the current frame.

                // If there is a handler installed on the frame, then let the handler handle it.
                if(lHandler != null) lHandler.handleFrame(this, aStack, lFrame);
                else
                {
                    // We have to install a handler and then invoke it.
                    if(!(lExpr instanceof List) && !(lExpr instanceof Pair))
                    {
                        // Atomic expression.
                        lFrame.setHandler(ATOMIC_HANDLER);
                    }
                    else if (lExpr instanceof Pair)
                    {
                        // Pair expression.
                        lFrame.setHandler(PAIR_HANDLER);
                    }
                    else
                    {
                        // List expression.
                        final List lLstExpr = (List) lExpr;
                        final int lLstSize = lLstExpr.size();

                        // An empty list is never evaluated.
                        // This makes no sense. They evaluate to themselves.
                        // We create a new empty list though in order to prevent modification to program code.
                        if(lLstSize == 0)
                        {
                            lFrame.setResult(new ArrayList());
                            return;
                        }

                        // Lookup what could be the name of the command.
                        Object lCmdCandidate = lLstExpr.get(0);
                        // Initialize the frame with the default handler.
                        lFrame.setHandler(STANDARD_HANDLER);
                        // We try to install a more specific handler if we can find one.
                        if(lCmdCandidate instanceof String)
                        {
                            // Find the most appropriate handler and install it in the stackframe.
                            // We only have to do this lookup once for a new stackframe.
                            final IFrameHandler lNewHandler = HANDLERS.get(lCmdCandidate);
                            if(lNewHandler != null) lFrame.setHandler(lNewHandler);
                            else if(MACRO_HANDLER.hasMacro((String)lCmdCandidate)) lFrame.setHandler(MACRO_HANDLER);
                        }
                    }

                    // Immediately take the first steps on our newly installed handlers.
                    // There should be some progress in each step.
                    lFrame.getHandler().init(aStack, lFrame);
                    // Call the handler.
                    // Warning, don't put the handler in a local variable,
                    // the init might have replaced the handler with a more
                    // specific one.
                    lFrame.getHandler().handleFrame(this, aStack, lFrame);
                }
            }

            // Progress has been made, a return has been performed or a frame handler
            // has been called to do some work. Notify the listeners about this progress.
            fireStepEvent(aStack);
        }
        catch(CommandException e)
        {
            // Notify the listeners about this exception.
            fireReceivedException(aStack, e);
            // Re-throw it.
            throw e;
        }
    }
}

class AtomicHandler
implements Eval2.IFrameHandler
{
    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        // No stack frame space needed for atoms.
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final Object lExpr = aFrame.getExpr();
        final IContext lCtx = aFrame.getCtx();

        if(lExpr instanceof String && ((String)lExpr).startsWith("$"))
        {
            // Syntactic sugar. $name is equivalent to (get name).
            aFrame.setResult(lCtx.getBinding(((String)lExpr).substring(1)));
        }
        else
        {
            // All other values except lists evaluate to
            // themselves! This is quite different from the original LISP.
            // Our goal is to have a handy and flexible REPL that can handle model objects or "handles"
            // that do not have a list representation.
            aFrame.setResult(lExpr);
        }
    }
}

class PairHandler
implements Eval2.IFrameHandler
{
    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        // Allocate stack data.
        aFrame.allocateData(2);
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final Object[] lData = aFrame.getData();
        final IContext lCtx = aFrame.getCtx();
        final Pair lPair = (Pair) aFrame.getExpr();
        switch(aFrame.getDataptr())
        {
        case 0:
            // Put the left side on the stack for evaluation.
            aStack.push(lPair.getLeft(), lCtx);
            return;
        case 1:
            // Put the right side on the stack for evaluation.
            aStack.push(lPair.getRight(), lCtx);
            return;
        default:
            // Create a new pair and add it as the result of the current
            // expression to the stack frame.
            aFrame.setResult(new Pair(lData[0], lData[1]));
        }
    }
}

class StandardHandler
implements Eval2.IFrameHandler
{
    private CommandRepository commands = new CommandRepository();

    public StandardHandler()
    {
    }

    public CommandRepository getCommands()
    {
        return commands;
    }

    public void setCommands(CommandRepository aCommands)
    {
        commands = aCommands;
    }

    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();
        // Allocate enough space to evaluate all elements of the expression.
        // So the space should be as large as the original expression.
        aFrame.allocateData(lLstSize);
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();
        final IContext lCtx = aFrame.getCtx();
        final Object[] lData = aFrame.getData();
        final int lDataPtr = aFrame.getDataptr();

        if(lDataPtr < lLstSize)
        {
            // Keep on evaluating.
            // Evaluate all arguments even the first element in the list. The first element can evaluate to a function!
            aStack.push(lLstExpr.get(lDataPtr), lCtx);
            return;
        }

        // Re-fetch the command candidate, it might have changed by the evaluation.
        Object lCmdCandidate = lData[0];
        if(lData.length > 0)
        {
            // Now we try to execute the list as a command or a function.

            if(lCmdCandidate instanceof String)
            {
                // Create a String view on the command candidate.
                final String lCmdName = ((String) lCmdCandidate).trim();

                // Built-in forms.
                // These are not like the special forms, because they list is
                // evaluated like all other lists. They should be seen as built-in functionality.
                /////////////////////////////////////////////////////////////////////////////////

                if("eval".equals(lCmdName))
                {
                    if(lLstSize != 2) throw new CommandException("The 'eval' form should have a single argument.", aStack);
                    aStack.push(lData[1], lCtx);
                }
                else if("eq".equals(lCmdName))
                {
                    if(lLstSize != 3) throw new CommandException("The 'eq' form should have two arguments.", aStack);
                    Object lArg1 = lData[1];
                    Object lArg2 = lData[2];
                    Boolean lResult;
                    if(lArg1 == null && lArg2 == null) lResult = Boolean.TRUE;
                    else if(lArg1 == null) lResult = Boolean.FALSE;
                    else if(lArg2 == null) lResult = Boolean.FALSE;
                    else lResult = lArg1.equals(lArg2);
                    aFrame.setResult(lResult);
                }
                else if("bound?".equals(lCmdName))
                {
                    if(lLstSize != 2) throw new CommandException("The 'bound?' form should have a single argument.", aStack);
                    if(lData[1] instanceof String)
                    {
                        String lName = (String) lData[1];
                        aFrame.setResult(lCtx.isBound(lName));
                    }
                    else throw new CommandException("The 'bound?' form should have a single string argument.", aStack);
                }
                else if("progn".equals(lCmdName))
                {
                    if(lLstSize < 2) throw new CommandException("The 'progn' form should have at least one argument.", aStack);
                    aFrame.setResult(lData[lData.length - 1]);
                }
                else if("funcall".equals(lCmdName))
                {
                    // This is the 'official' way of executing a method.
                    // The other methods are macro's that will sooner or later evaluate to this construct.
                    // This is the real deal (whereas the other constructs should be seen as syntactic sugar).

                    // Quick test on the number of arguments.
                    if(lLstSize < 2) throw new CommandException("The 'funcall' form should have the format (funcall name <args>).", aStack);
                    // Test the function name / lambda.
                    final Object lName = lData[1];

                    // Fetch the function.
                    //////////////////////

                    Lambda lFun;
                    if(lName instanceof Lambda)
                    {
                        // Easy part.
                        lFun = (Lambda) lName;
                    }
                    else if(lName instanceof String)
                    {
                        // We have to do a lookup.
                        Object lFunCandidate = lCtx.getBinding((String) lName);
                        if(!(lFunCandidate instanceof Lambda)) throw new CommandException(String.format("Function \"%s\" was not found in the context.", lName), aStack);
                        lFun = (Lambda) lFunCandidate;
                    }
                    else
                    {
                        // Trouble.
                        // We found something in the beginning of the list that does not evaluate to a lambda name or a lambda itself.
                        if(lName == null) throw new CommandException("The first argument in the 'funcall' form should evaluate to a string or a lambda, but we received 'null'.", aStack);
                        throw new CommandException(String.format("The first argument in the 'funcall' form should evaluate to a string or a lambda.\n Received an instance of class '%s'.", lName.getClass().getCanonicalName()), aStack);
                    }

                    // Context that binds the parameters to the arguments in addition to the lexical context.
                    // Note that we skip the 'funcall' constant and the function name/lambda when constructing
                    // the argument list.
                    final IContext lFunCtx = lFun.createContext(lData, 2, lLstSize);
                    aStack.push(lFun.getExpr(), lFunCtx);

                }
                else if(commands.hasCommand(lCmdName))
                {
                    try
                    {
                        final ICommand lCmd = commands.getCommand(lCmdName);
                        aFrame.setResult(lCmd.execute(aEval, lCtx, lData));
                    }
                    catch(CommandException e)
                    {
                        // This type of error will be handled by our general mechanism.
                        // It does not need special handling here.
                        Eval2.EvalStack lNestedStack = e.getStack();
                        e.setStack(aStack.merge(lNestedStack));
                        throw e;
                    }
                    catch(Exception e)
                    {
                        // A non-CommandException is converted into a command exception here.
                        // Extract information about the exception. For some exceptions this is the only
                        // way to get some detail in this way (e.g. NullPointerException).
                        final ByteArrayOutputStream lBos = new ByteArrayOutputStream();
                        final PrintStream lPrinter = new PrintStream(lBos);
                        e.printStackTrace(lPrinter);
                        final String lMsg = lBos.toString();
                        try {lPrinter.close(); lBos.close(); } catch (IOException ignored) {}

                        // Re-throw the exception with more information.
                        throw new CommandException(String.format("Command '%s' failed.\n%s", lCmdName, lMsg), e, aStack);
                    }
                }
                else if(lCtx.isBound(lCmdName) && lCtx.getBinding(lCmdName) instanceof Lambda)
                {
                    // Immediately execute the lambda.
                    final Lambda lFun = (Lambda) lCtx.getBinding(lCmdName);
                    final IContext lFunCtx = lFun.createContext(lData, 1, lLstSize);
                    aStack.push(lFun.getExpr(), lFunCtx);
                }
                else
                {
                    throw new CommandException(String.format("Command or form '%s' does not exist.", lCmdName), aStack);
                }
            }
            else if(lCmdCandidate instanceof Lambda)
            {
                // Immediately execute the lambda.
                final Lambda lFun = (Lambda) lCmdCandidate;
                final IContext lFunCtx = lFun.createContext(lData, 1, lLstSize);
                aStack.push(lFun.getExpr(), lFunCtx);
            }
            else
            {
                // Error, name of the command should be a string or a lambda.
                if(lCmdCandidate == null) throw new CommandException(String.format("The command name should evaluate to a string or a lambda. Found null."), aStack);
                else throw new CommandException(String.format("The command name should evaluate to a string or a lambda.\nFound an instance '%s' of class \"%s\", which cannot be interpreted as a function.", lCmdCandidate.toString(), lCmdCandidate.getClass().getName()), aStack);
            }
        }
        else
        {
            // Error, the list does not contain a command.
            throw new CommandException(String.format("The expression '%s' cannot be executed.", lLstExpr), aStack);
        }
    }
}

class QuoteHandler
implements Eval2.IFrameHandler
{
    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();

        // If it is a quoted list, we don't evaluate its elements, the result is again, the list.
        // In this way you can use lists as data structures and not as a function call.
        // It is a special form because it influences the way the expression is (not) evaluated, it is non-standard.
        if(lLstSize != 2) throw new CommandException("The 'quote' form should have the format (quote <expr>).", aStack);
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();

        // Immediately return the result, no extra evaluation steps needed.
        aFrame.setResult(lLstExpr.get(1));
    }
}

class IfHandler
implements Eval2.IFrameHandler
{
    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();

        // Quick test on the number of arguments.
        if(lLstSize < 3 || lLstSize > 4) throw new CommandException("The 'if' form should have the format (if <bool-expr> <then-expr> [<else-expr>]).", aStack);

        // Allocate frame space.
        aFrame.allocateData(2);
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        // It is a special form because only one of the then or else part is evaluated depending on the outcome of the test.
        // It shortcuts evaluation of the other branch.

        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();
        final IContext lCtx = aFrame.getCtx();
        final Object[] lData = aFrame.getData();

        switch(aFrame.getDataptr())
        {
        case 0:
            // Push the boolean expression on the stack for evaluation.
            aStack.push(lLstExpr.get(1), lCtx);
            return;
        case 1:
            // If the boolean expression evaluates positive we push the then-part
            // on the stack, otherwise the else part.
            if(Eval2.boolEval(lData[0])) aStack.push(lLstExpr.get(2), lCtx);
            // The else is optional.
            else if(lLstSize == 4) aStack.push(lLstExpr.get(3), lCtx);
            // The result is null if there is no else part.
            else aFrame.setResult(null);
            return;
        default:
            // The result is the then-part or else-part.
            aFrame.setResult(lData[1]);
        }
    }
}

class WhileHandler
implements Eval2.IFrameHandler
{
    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();

        // Quick test on the number of arguments.
        if(lLstSize < 2 || lLstSize > 3) throw new CommandException("The 'while' form should have the format (while <bool-expr> [<expr>]).", aStack);

        // Allocate frame space. One location for the conditional expression and one for the body evaluation.
        aFrame.allocateData(2);
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        // It is a special form because the test is evaluated again and again.

        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();
        final IContext lCtx = aFrame.getCtx();
        Object[] lData = aFrame.getData();

        switch(aFrame.getDataptr())
        {
        case 0:
            // Push the boolean expression on the stack for evaluation.
            aStack.push(lLstExpr.get(1), lCtx);
            return;
        case 1:
            // If the boolean expression evaluates positive we push the body on the stack.
            if(Eval2.boolEval(lData[0]))
            {
                if(lLstSize == 3)
                {
                    // We evaluate the body.
                    aStack.push(lLstExpr.get(2), lCtx);
                    return;
                }
                else
                {
                    // While expression without body.
                    // Move to slot 0 to receive the result of boolean evaluation.
                    aFrame.setDataPtr(0);
                    // There is no body, we re-evaluate the boolean expression.
                    aStack.push(lLstExpr.get(1), lCtx);
                    return;
                }
            }
            else
            {
                // Done.
                aFrame.setResult(lData[1]);
                return;
            }
        default:
            // Re-evaluate the boolean expression.
            aFrame.setDataPtr(0);
            aStack.push(lLstExpr.get(1), lCtx);
        }
    }
}

class AndHandler
implements Eval2.IFrameHandler
{
    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();

        if(lLstSize <= 1) throw new CommandException("The 'and' form should have the format (and <bool-expr>+).", aStack);

        //Allocate frame space. We allocate enough space to evaluate all arguments.
        aFrame.allocateData(lLstSize - 1);
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        // It is a special form because it shortcuts evaluation if it encounters a
        // false value.

        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();
        final IContext lCtx = aFrame.getCtx();
        final Object[] lData = aFrame.getData();
        final int lDataPtr = aFrame.getDataptr();

        switch(lDataPtr)
        {
        case 0:
            // Push the first expression on the stack for evaluation.
            aStack.push(lLstExpr.get(1), lCtx);
            return;
        default:
            // Check the result of the last parameter.
            if(!Eval2.boolEval(lData[lDataPtr - 1]))
            {
                // We shortcut the evaluation if we find a false value.
                // We can stop on the first false value we encounter.
                aFrame.setResult(Boolean.FALSE);
            }
            else
            {
                if(lDataPtr < (lLstSize - 1))
                {
                    // There are still some and-expressions to evaluate, so we
                    // evaluate the next one.
                    aStack.push(lLstExpr.get(lDataPtr + 1), lCtx);
                }
                else
                {
                    // We evaluated all expressions, and the all evaluated
                    // to true.
                    aFrame.setResult(Boolean.TRUE);
                }
            }
        }
    }
}

class OrHandler
implements Eval2.IFrameHandler
{
    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();

        if(lLstSize <= 1) throw new CommandException("The 'or' form should have the format (and <bool-expr>+).", aStack);

        // Allocate frame space. We allocate enough space to evaluate all arguments.
        aFrame.allocateData(lLstSize - 1);
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        // It is a special form because it shortcuts evaluation when a single true value
        // was found

        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();
        final IContext lCtx = aFrame.getCtx();
        final Object[] lData = aFrame.getData();
        final int lDataPtr = aFrame.getDataptr();

        switch(lDataPtr)
        {
        case 0:
            // Push the first expression on the stack for evaluation.
            aStack.push(lLstExpr.get(1), lCtx);
            return;
        default:
            // Check the result of the last parameter.
            if(Eval2.boolEval(lData[lDataPtr - 1]))
            {
                // We shortcut the evaluation if we find a true value.
                // We can stop on the first true value we encounter.
                aFrame.setResult(Boolean.TRUE);
            }
            else
            {
                if(lDataPtr < (lLstSize - 1))
                {
                    // There are still some and-expressions to evaluate, so we
                    // evaluate the next one.
                    aStack.push(lLstExpr.get(lDataPtr + 1), lCtx);
                }
                else
                {
                    // We evaluated all expressions, and the all evaluated
                    // to false.
                    aFrame.setResult(Boolean.FALSE);
                }
            }
        }
    }
}

class NotHandler
implements Eval2.IFrameHandler
{
    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();

        // Quick test on the number of arguments.
        if(lLstSize != 2) throw new CommandException("The 'not' form should have the format (not <bool-expr>).", aStack);
        // We allocate enough space to evaluate our expression.
        aFrame.allocateData(1);
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final IContext lCtx = aFrame.getCtx();
        final Object[] lData = aFrame.getData();

        switch(aFrame.getDataptr())
        {
        case 0:
            // Push the boolean expression on the stack for evaluation.
            aStack.push(lLstExpr.get(1), lCtx);
            return;
        default:
            if(Eval2.boolEval(lData[0])) aFrame.setResult(Boolean.FALSE);
            else aFrame.setResult(Boolean.TRUE);
        }
    }
}

class SetHandler
implements Eval2.IFrameHandler
{
    private static class PairHandler
    implements Eval2.IFrameHandler
    {
        public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
        throws CommandException
        {
            final List lLstExpr = (List) aFrame.getExpr();
            final IContext lCtx = aFrame.getCtx();
            final Object[] lData = aFrame.getData();
            final int lDataPtr = aFrame.getDataptr();

            switch(lDataPtr)
            {
            case 0:
                Object lPairCand = lLstExpr.get(1);
                if(!(lPairCand instanceof Pair))
                    throw new CommandException(String.format("The '%s' form should have the format (%s name value).",lLstExpr.get(0), lLstExpr.get(0)), aStack);
                Pair lPair = (Pair) lPairCand;
                aStack.push(lPair.getLeft(), lCtx);
                return;
            case 1:
                lPairCand = lLstExpr.get(1);
                if(!(lPairCand instanceof Pair))
                    throw new CommandException(String.format("The '%s' form should have the format (%s name value).",lLstExpr.get(0), lLstExpr.get(0)), aStack);
                 lPair = (Pair) lPairCand;
                aStack.push(lPair.getRight(), lCtx);
                return;
            default:
                // Check the type of the name.
                if(!(lData[0] instanceof String)) throw new CommandException(String.format("The first argument in the '%s' form should evaluate to a string.", lLstExpr.get(0)), aStack);
                final String lNameRepr = (String) lData[0];

                if("set".equals(lLstExpr.get(0))) lCtx.setBinding(lNameRepr, lData[1]);
                else lCtx.getRootContext().defBinding(lNameRepr, lData[1]);

                aFrame.setResult(lData[1]);
            }
        }

        public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
        throws CommandException
        {
        }
    }

    private static class CoupleHandler
    implements Eval2.IFrameHandler
    {
        public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
        throws CommandException
        {
            final List lLstExpr = (List) aFrame.getExpr();
            final IContext lCtx = aFrame.getCtx();
            final Object[] lData = aFrame.getData();
            final int lDataPtr = aFrame.getDataptr();

            switch(lDataPtr)
            {
            case 0:
                aStack.push(lLstExpr.get(1), lCtx);
                return;
            case 1:
                aStack.push(lLstExpr.get(2), lCtx);
                return;
            default:
                // Check the type of the name.
                if(!(lData[0] instanceof String))
                    throw new CommandException(String.format("The first argument in the '%s' form should evaluate to a string.", lLstExpr.get(0)), aStack);
                final String lNameRepr = (String) lData[0];

                if("set".equals(lLstExpr.get(0))) lCtx.setBinding(lNameRepr, lData[1]);
                else lCtx.getRootContext().defBinding(lNameRepr, lData[1]);

                aFrame.setResult(lData[1]);
            }
        }

        public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
        throws CommandException
        {
        }
    }

    private static final Eval2.IFrameHandler PAIR_HANDLER = new PairHandler();
    private static final Eval2.IFrameHandler COUPLE_HANDLER = new CoupleHandler();

    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        // Allocate enpough space to evaluate the left and right side of the assignment.
        aFrame.allocateData(2);

        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();

        // Replace the handlers with more specific ones.
        if(lLstSize == 2)
        {
            // Variant (xxx name=value)
            aFrame.setHandler(PAIR_HANDLER);
        }
        else if(lLstSize == 3)
        {
            // Variant (xxx name value)
            aFrame.setHandler(COUPLE_HANDLER);
        }
        else
        {
            throw new CommandException(String.format("The '%s' form should have the format (%s name value).",lLstExpr.get(0), lLstExpr.get(0)), aStack);
        }
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        // This handler will never be called, by now the frame handler will be replaced
        // by a more specific handler.
    }
}

class LetHandler
implements Eval2.IFrameHandler
{
    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();
        final IContext lCtx = aFrame.getCtx();

        if(lLstSize != 3) throw new CommandException(String.format("The '%s' form should have the format (%s ((name val)...) expr).",lLstExpr.get(0), lLstExpr.get(0)), aStack);
        final Object lBindings = lLstExpr.get(1);

        // Check the type of the list of bindings.
        if(!(lBindings instanceof List))
            throw new CommandException(String.format("The '%s' form should have the format (%s ((name val)...) expr).\nThe first parameter should be a list of bindings but encountered an instance of type '%s'.",lLstExpr.get(0), lLstExpr.get(0), lBindings==null?"null":lBindings.getClass().getCanonicalName()), aStack);

        // Allocate space for all new bindings and also the expression.
        aFrame.allocateData(((List)lBindings).size() + 1);

        // Install a new context.
        aFrame.setCtx(new CompositeContext(new BasicContext(), lCtx));
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final IContext lCtx = aFrame.getCtx();
        final Object[] lData = aFrame.getData();
        final int lDataPtr = aFrame.getDataptr();
        final boolean letrec = "let*".equals(lLstExpr.get(0));

        if(lDataPtr < (lData.length - 1))
        {
            List lBindings = (List) lLstExpr.get(1);
            Object lBinding = lBindings.get(lDataPtr);

            if(letrec && (lDataPtr > 0))
            {
                Pair lBindingPair = (Pair) lData[lDataPtr - 1];
                lCtx.defBinding((String) lBindingPair.getLeft(), lBindingPair.getRight());
            }

            if(lBinding instanceof String)
            {
                final String lName = (String) lBinding;
                aFrame.pushData(new Pair(lName, null));
                if(letrec) lCtx.defBinding(lName, null);
            }
            else if(lBinding instanceof List || lBinding instanceof Pair)
            {
                Object lKey;
                Object lValExpr;

                if(lBinding instanceof List)
                {
                    // Variant (name value)
                    //
                    final List lBindingList = (List) lBinding;
                    if(lBindingList.size() != 2)
                        throw new CommandException(String.format("The 'let' form should have the format (let ((name val) | name=val ...) expr).\nEach binding should be a list of length 2 of the form (var val)."), aStack);
                    lKey = lBindingList.get(0);
                    lValExpr = lBindingList.get(1);
                }
                else
                {
                    // Variant name=value
                    //
                    final Pair lBindingPair = (Pair) lBinding;
                    lKey = lBindingPair.getLeft();
                    lValExpr = lBindingPair.getRight();
                }

                if(!(lKey instanceof String))
                    throw new CommandException(String.format("The 'let' special should have the format (let ((name val) | name=val ...) expr).\nEach binding should be a list  of the form (var val).\nThe first element should be a string but encountered an instance of type '%s'.", lKey==null?"null":lKey.getClass().getCanonicalName()), aStack);

                aStack.push(new Pair(lKey, lValExpr), lCtx);
            }
            else
            {
                throw new CommandException(String.format("The 'let' form should have the format (let ((name val) | name=val ...) expr).\nEach binding should be a list or a string or a pair but encountered an instance of type '%s'.", lBinding==null?"null":lBinding.getClass().getCanonicalName()), aStack);
            }
        }
        else if(lDataPtr == (lData.length - 1))
        {
            // All bindings were evaluated. Now we can go for the body.
            if(letrec && (lDataPtr > 0))
            {
                Pair lBindingPair = (Pair) lData[lDataPtr - 1];
                lCtx.defBinding((String) lBindingPair.getLeft(), lBindingPair.getRight());
            }
            else
            {
                for(int i = 0; i < (lData.length - 1); i++)
                {
                    Pair lBindingPair = (Pair) lData[i];
                    lCtx.defBinding((String) lBindingPair.getLeft(), lBindingPair.getRight());
                }
            }
            aStack.push(lLstExpr.get(2), lCtx);
        }
        else
        {
            // Done evaluating everyting.
            aFrame.setResult(lData[lData.length - 1]);
        }
    }
}

class GetHandler
implements Eval2.IFrameHandler
{
    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();

        // Quick test on the number of arguments.
        if(lLstSize != 2) throw new CommandException("The 'get' form should have the format (get name).", aStack);
        // Allocate one.
        aFrame.allocateData(1);
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame) throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final IContext lCtx = aFrame.getCtx();
        final Object[] lData = aFrame.getData();
        final int lDataPtr = aFrame.getDataptr();

        if(lDataPtr == 0)
        {
            // Evaluate the expression.
            aStack.push(lLstExpr.get(1), lCtx);
        }
        else
        {
            // Pick up the result.
            // Check the type of the name.
            if(!(lData[0] instanceof String)) throw new CommandException("The first argument in the 'get' form should evaluate to a string.", aStack);
            aFrame.setResult(lCtx.getBinding((String) lData[0]));
        }
    }
}

class LambdaHandler
implements Eval2.IFrameHandler
{
    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        // It is a special form because the parameter list nor the function body
        // should be evaluated at this point.
        // The strange thing is that a lambda evaluation does not do an eval itself, it can be executed in a single pass!

        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();
        final IContext lCtx = aFrame.getCtx();

        //Quick test on the number of arguments.
        if(lLstSize != 3) throw new CommandException("The 'lambda' form should have the format (lambda (<params>) <expr>).", aStack);
        // Parameters are *not* evaluated ...
        final Object lParams = lLstExpr.get(1);
        final Object lBody = lLstExpr.get(2);

        // Do some checking.
        if(lParams == null || !(lParams instanceof List)) throw new CommandException("The first argument in the 'lambda' form should evaluate to a list of parameters.", aStack);
        List lParamList = (List) lParams;
        for(Object lParam:lParamList) if(!(lParam instanceof String)) throw new CommandException("The first argument in the 'lambda' form, the parameter list,  should evaluate to a list of strings.", aStack);
        if(lBody == null) throw new CommandException("The second argument in the 'lambda' form should be an expression.", aStack);

        // Construct the lambda.
        final String[] lStrArgs = new String[lParamList.size()];
        for(int i = 0; i < lParamList.size(); i++) lStrArgs[i] = (String) lParamList.get(i);
        final Lambda lFunc = new Lambda(lStrArgs, lBody, lCtx);
        aFrame.setResult(lFunc);
    }
}

class DefunHandler
implements Eval2.IFrameHandler
{
    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();

        // Quick test on the number of arguments.
        if(lLstSize != 4) throw new CommandException("The 'defun' form should have the format (defun name (<params>) <expr>).", aStack);

        // One location to evaluate our lambda macro.
        aFrame.allocateData(1);
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        // It is a special form because the parameter list nor the function body
        // should be evaluated at this point.

        final List lLstExpr = (List) aFrame.getExpr();
        final IContext lCtx = aFrame.getCtx();
        final Object[] lData = aFrame.getData();
        final int lDataPtr = aFrame.getDataptr();

        // Name and parameters are *not* evaluated ...
        final Object lName = lLstExpr.get(1);
        final Object lParams = lLstExpr.get(2);
        final Object lBody = lLstExpr.get(3);

        if(lDataPtr == 0)
        {
            // Do some checking.
            if(lName == null || !(lName instanceof String)) throw new CommandException("The first argument in the 'defun' form should evaluate to a string.", aStack);
            if(lParams == null || !(lParams instanceof List)) throw new CommandException("The second argument in the 'defun' form should evaluate to a list of parameters.", aStack);
            final List lParamList = (List) lParams;
            for(Object lParam:lParamList) if(!(lParam instanceof String)) throw new CommandException("The second argument in the 'defun' form, the parameter list,  should evaluate to a list of strings.", aStack);
            if(lBody == null) throw new CommandException("The third argument in the 'defun' form should be an expression.", aStack);

            // Create a lambda macro.
            final List<Object> lLambdaMacro = new ArrayList<Object>(3);
            lLambdaMacro.add("lambda");
            lLambdaMacro.add(lParams);
            lLambdaMacro.add(lBody);

            // Evaluate the lambda.
            // Bind the resulting lambda to the name GLOBALLY!
            aStack.push(lLambdaMacro, lCtx);
        }
        else
        {
            lCtx.getRootContext().defBinding((String) lName, lData[0]);
            aFrame.setResult(lData[0]);
        }
    }
}

class TimerHandler
implements Eval2.IFrameHandler
{
    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final int lLstSize = lLstExpr.size();

        if(lLstSize != 2) throw new CommandException("The 'timer' form should have the format (timer expr).", aStack);

        // Allocate a single slot for the expression we have to time and another slot for the start time.
        aFrame.allocateData(2);
    }

    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final IContext lCtx = aFrame.getCtx();
        final Object[] lData = aFrame.getData();
        final int lDataPtr = aFrame.getDataptr();

        if(lDataPtr == 0)
        {
            long lStart = System.currentTimeMillis();
            aFrame.pushData(lStart);
            aStack.push(lLstExpr.get(1), lCtx);
            return;
        }
        else
        {
            long lStop = System.currentTimeMillis();
            long lStart = (Long) lData[0];
            aFrame.setResult(lStop - lStart);
        }
    }
}

class MacroHandler
implements Eval2.IFrameHandler
{
    private CommandRepository macros = new CommandRepository();

    public MacroHandler()
    {
    }

    public CommandRepository getMacros()
    {
        return macros;
    }

    public void setMacros(CommandRepository aMacros)
    {
        macros = aMacros;
    }

    public boolean hasMacro(String aName)
    {
        return macros.hasCommand(aName);
    }

    public void init(Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        // Allocate a single slot for the evaluation of the macro expansion.
        aFrame.allocateData(1);
    }

    @SuppressWarnings("unchecked")
    public void handleFrame(IEval aEval, Eval2.EvalStack aStack, Eval2.StackFrame aFrame)
    throws CommandException
    {
        final List lLstExpr = (List) aFrame.getExpr();
        final IContext lCtx = aFrame.getCtx();
        final Object[] lData = aFrame.getData();
        final int lDataPtr = aFrame.getDataptr();

        if(lDataPtr == 0)
        {
            try
            {
                // Built-in macro call.
                final ICommand lMacro = macros.getCommand((String)lLstExpr.get(0));
                final List lArgs = new ArrayList(lLstExpr.size());
                lArgs.addAll(lLstExpr);
                aStack.push(lMacro.execute(aEval, lCtx, lArgs.toArray()), lCtx);
            }
            catch(CommandException e)
            {
                Eval2.EvalStack lNestedStack = e.getStack();
                e.setStack(aStack.merge(lNestedStack));
                throw e;
            }
            catch(Exception e)
            {
                // A non-CommandException is converted into a command exception here.
                throw new CommandException(String.format("Macro '%s' failed.\n%s", lLstExpr.get(0), e.getMessage()), aStack);
            }
        }
        else
        {
            aFrame.setResult(lData[0]);
        }
    }
}

