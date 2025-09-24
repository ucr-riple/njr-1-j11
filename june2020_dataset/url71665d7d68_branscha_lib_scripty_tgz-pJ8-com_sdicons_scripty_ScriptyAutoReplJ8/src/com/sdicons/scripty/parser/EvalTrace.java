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


import java.util.ArrayList;
import java.util.List;

/**
 * Debugger.
 * Protocol: consructor - step* - getresult.
 *  - Resultaat *moet* opgehaald worden, de eval wacht op een step of een getresult.
 *
 */
public class EvalTrace
{
    private Eval2 eval;
    private Eval2.EvalStack stack;

    private Thread worker;

    // A reference is not enough, null could
    // be the result as well.
    private Object result;
    private boolean hasResult = false;
    private boolean halted = false;
    private boolean excepted = false;
    private Exception exception = null;
    private BreakpointSet breakpoints = new BreakpointSet();
    private boolean breakpoint = false;

    // We need the stepcount while stepping with breakpoints.
    // We only break on a breakpoint if we took at least 1 step.
    // Otherwise it would be impossible to continue stepping after a matching breakpoint was encountered.
    private long stepcount = 0;

    public static interface IBreakpoint
    {
        public String getName();
        public void setEnabled(boolean aEnabled);
        public boolean breakHere(Eval2.EvalStack aStack);
    }

    public static class BreakpointSet
    {
        private List<IBreakpoint> breakpoints = new ArrayList<IBreakpoint>();

        public void addBreakpoint(IBreakpoint aBpt)
        {
            breakpoints.add(aBpt);
        }

        public void removeBreakpoint(String aName)
        {
            for(IBreakpoint lBpt: breakpoints)
            {
                if(lBpt.getName() != null && lBpt.getName().equals(aName))
                {
                    breakpoints.remove(lBpt);
                    return;
                }
            }
        }

        public void enableBreakpoint(String aName, boolean aEnable)
        {
            for(IBreakpoint lBpt: breakpoints)
            {
                if(lBpt.getName() != null && lBpt.getName().equals(aName))
                {
                    lBpt.setEnabled(aEnable);
                }
            }
        }

        public void removeAllBreakpoints()
        {
            breakpoints.clear();
        }

        public List<IBreakpoint> findAllMatchingBreakpoints(Eval2.EvalStack aStack)
        {
            List<IBreakpoint> lResult = new ArrayList<IBreakpoint>();
            if(aStack != null)
            {
                for(IBreakpoint lBpt: breakpoints)
                    if(lBpt.breakHere(aStack)) lResult.add(lBpt);
            }
            return lResult;
        }

        public IBreakpoint findFirstMatchingBreakpoint(Eval2.EvalStack aStack)
        {
            for(IBreakpoint lBpt : breakpoints)
                if(lBpt.breakHere(aStack)) return lBpt;
            return null;
        }

        public boolean breakHere(Eval2.EvalStack aStack)
        {
            return findFirstMatchingBreakpoint(aStack) != null;
        }

        @Override
        public String toString()
        {
            final StringBuilder lBuilder = new StringBuilder();
            for(IBreakpoint lBpt: breakpoints)
            {
                lBuilder.append(lBpt.toString()).append("\n");
            }
            return lBuilder.toString();
        }
    }

    public static class BreakpointFunc
    implements EvalTrace.IBreakpoint
    {
        private String name;
        private String func;
        private boolean enabled = true;

        public BreakpointFunc(String aName, String aFunc)
        {
            name = aName;
            func = aFunc;
        }

        public void setEnabled(boolean enabled)
        {
            this.enabled = enabled;
        }

        public boolean breakHere(Eval2.EvalStack aStack)
        {
            final Eval2.StackFrame lFrame = aStack.top();
            final Object lExpr = lFrame.getExpr();

            if(enabled && name != null && lExpr != null && lExpr instanceof List)
            {
                // Fetch the expression that is being evaluated.
                final List lExprLst = (List) lExpr;
                // Only break if the name of the expression is the one we are looking for,
                // and also that the frame slot pointer is at the beginning of the frame,
                // we only want to break the first time an expression is pushed on the stack,
                // not each time it is on top of the stack.
                return (lExprLst.size() > 0) && func.equals(lExprLst.get(0)) && (lFrame.getDataptr() == 0);
            }
            return false;
        }

        public String getName()
        {
            return name;
        }

        @Override
        public String toString()
        {
            StringBuilder lBuilder = new StringBuilder();
            lBuilder.append(this.name).append(", ").append("name break: '").append(this.func).append("'").append(enabled?", enabled":", paused");
            return lBuilder.toString();
        }
    }

    public static class BreakpointWhen
    implements IBreakpoint
    {
        private String name;
        private Object lWhenExpr;
        private IEval eval = new Eval();
        private boolean enabled = true;

        public BreakpointWhen(String aName, Object aExpr)
        {
            this.name = aName;
            this.lWhenExpr = aExpr;
        }

        public String getName()
        {
            return name;
        }

        public void setEnabled(boolean enabled)
        {
            this.enabled = enabled;
        }

        public boolean breakHere(Eval2.EvalStack aStack)
        {
            try
            {
                return enabled && AbstractEval.boolEval(eval.eval(lWhenExpr, aStack.top().getCtx()));
            }
            catch (CommandException e)
            {
                return false;
            }
        }

        @Override
        public String toString()
        {
            StringBuilder lBuilder = new StringBuilder();
            lBuilder.append(this.name).append(", ").append("expression break: '").append(lWhenExpr.toString()).append("'").append(enabled?", enabled":", paused");
            return lBuilder.toString();
        }
    }

    public static class BreakpointStackdepth
    implements IBreakpoint
    {
        private String name;
        private boolean enabled = true;
        private int depth;

        public BreakpointStackdepth(String aName, int aDepth)
        {
            this.name = aName;
            this.depth = aDepth;
        }

        public String getName()
        {
            return name;
        }

        public void setEnabled(boolean enabled)
        {
            this.enabled = enabled;
        }

        public boolean breakHere(Eval2.EvalStack aStack)
        {
            return enabled && aStack.size() >= depth;
        }

        @Override
        public String toString()
        {
            StringBuilder lBuilder = new StringBuilder();
            lBuilder.append(this.name).append(", ").append("stack depth break: '").append(depth).append("'").append(enabled?", enabled":", paused");
            return lBuilder.toString();
        }
    }

    public static class BreakpointNot
    implements IBreakpoint
    {
        private String name;
        private boolean enabled = true;
        private IBreakpoint bp;

        public BreakpointNot(String aName, IBreakpoint aBp)
        {
            this.name = aName;
            this.bp = aBp;
        }

        public String getName()
        {
            return name;
        }

        public void setEnabled(boolean enabled)
        {
            this.enabled = enabled;
        }

        public boolean breakHere(Eval2.EvalStack aStack)
        {
            return enabled && !(bp.breakHere(aStack));
        }

        @Override
        public String toString()
        {
            StringBuilder lBuilder = new StringBuilder();
            lBuilder.append(this.name).append(", ").append("not-composite").append(enabled?", enabled":", paused");
            lBuilder.append("\n\t").append(bp.toString().replace("\n\t", "\n\t\t"));
            return lBuilder.toString();
        }
    }

    public static class BreakpointAnd
    implements IBreakpoint
    {
        private String name;
        private boolean enabled = true;
        private List<IBreakpoint> bps;

        public BreakpointAnd(String aName, List<IBreakpoint> aBps)
        {
            this.name = aName;
            this.bps = aBps;
        }

        public String getName()
        {
            return name;
        }

        public void setEnabled(boolean enabled)
        {
            this.enabled = enabled;
        }

        public boolean breakHere(Eval2.EvalStack aStack)
        {
            boolean lBreak = enabled;
            for(IBreakpoint lBp: bps)
            {
                lBreak = lBreak && lBp.breakHere(aStack);
                if(!lBreak) return lBreak;
            }
            return lBreak;
        }

        @Override
        public String toString()
        {
            StringBuilder lBuilder = new StringBuilder();
            lBuilder.append(this.name).append(", ").append("and-composite").append(enabled?", enabled":", paused");
            for(IBreakpoint lBp: bps)
                lBuilder.append("\n\t * ").append(lBp.toString().replace("\n\t", "\n\t\t"));
            return lBuilder.toString();
        }
    }

    public static class BreakpointOr
    implements IBreakpoint
    {
        private String name;
        private boolean enabled = true;
        private List<IBreakpoint> bps;

        public BreakpointOr(String aName, List<IBreakpoint> aBps)
        {
            this.name = aName;
            this.bps = aBps;
        }

        public String getName()
        {
            return name;
        }

        public void setEnabled(boolean enabled)
        {
            this.enabled = enabled;
        }

        public boolean breakHere(Eval2.EvalStack aStack)
        {
            if(!enabled) return false;
            boolean lBreak = false;
            for(IBreakpoint lBp: bps)
            {
                lBreak = lBreak || lBp.breakHere(aStack);
                if(lBreak) return lBreak;
            }
            return lBreak;
        }

        @Override
        public String toString()
        {
            StringBuilder lBuilder = new StringBuilder();
            lBuilder.append(this.name).append(", ").append("or-composite").append(enabled?", enabled":", paused");
            for(IBreakpoint lBp: bps)
                lBuilder.append("\n\t * ").append(lBp.toString().replace("\n\t", "\n\t\t"));
            return lBuilder.toString();
        }
    }

    public EvalTrace(final Eval2 aEval, final Object aExpr)
    {
        // Keep track of the eval under scrutiny.
        // Add an event listener to it.
        eval = aEval;
        eval.addEvalListener(new Eval2.EvalListener()
        {
            public void finishedEval(Eval2.EvalEvent aEvent)
            {
                synchronized (EvalTrace.this)
                {
                    try
                    {
                        // The eval is done. Set the flags.
                        halted = true;
                        hasResult = true;
                        excepted = false;
                        // Make data available.
                        result = aEvent.getResult();
                        stack = aEvent.getStack();
                        exception = null;
                        // Awake the debugger.
                        EvalTrace.this.notifyAll();
                        // The eval must wait, otherwise it might attempt
                        // to evaluate a brand new expression which will destroy the result of
                        // this earlier evaluation!
                        EvalTrace.this.wait();
                        halted = false;
                    }
                    catch (InterruptedException e)
                    {
                        halted = true;
                    }
                }
            }

            public void startEval(Eval2.EvalEvent aEvent)
            {
                synchronized(EvalTrace.this)
                {
                    try
                    {
                        // The eval marks itself as being halted (see a bit further).
                        halted = true;
                        result = null;
                        hasResult = false;
                        excepted = false;
                        exception = null;
                        // The eval makes its stack available for the debugger.
                        stack = aEvent.getStack();
                        // It wakes up the debugger.
                        EvalTrace.this.notifyAll();
                        // The eval puts itself in wait on te trace manager.
                        EvalTrace.this.wait();
                        // If we pass through the wait method, the eval kicks into action.
                        halted = false;
                    }
                    catch (InterruptedException e)
                    {
                        halted = true;
                    }
                }
            }

            public void stepEvent(Eval2.EvalEvent aEvent)
            {
                synchronized (EvalTrace.this)
                {
                    try
                    {
                        // Set the halted flag.
                        halted = true;
                        // Provide the stack for the debugger to examine.
                        stack = aEvent.getStack();
                        // Wake up the debugger.
                        EvalTrace.this.notifyAll();
                        // If there are more steps to take, we let the eval wait a bit.
                        // If this was the last step, we can let the eval worker finish its job
                        // and prepare the result for us.
                        if(stack.hasMoreSteps())
                        {
                            // The eval goes to sleep ...
                            // We wait for a signal of the tracer.
                            EvalTrace.this.wait();
                            halted = false;
                        }
                    }
                    catch (InterruptedException e)
                    {
                        halted = true;
                    }
                }
            }

            public void receivedException(Eval2.EvalEvent aEvent)
            {
                synchronized (EvalTrace.this)
                {
                    halted = true;
                    stack = aEvent.getStack();
                    exception = aEvent.getException();
                    excepted = true;
                    result = null;
                    hasResult = false;
                    // Signal the exception event to any processes
                    // that are waiting for something to happen.
                    EvalTrace.this.notifyAll();
                }
            }
        });

        // Create a separate suspendable thread to evaluate our expresion in.
        worker = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    eval.eval(aExpr);
                }
                catch (CommandException e)
                {
                    // The eval will have notified any listeners.
                    // Our EvalTrace will have received the exception event and should have
                    // taken appropriate action.
                    // We can let this thread die.
                }
            }
        };

        // Start the eval thread.
        worker.setName("eval/trace");
        worker.setDaemon(true);
        worker.start();
    }

    public synchronized boolean hasMoreSteps()
    {
        if(worker == null || excepted || !worker.isAlive() ) return false;

        try
        {
            while (!halted) this.wait();
            // This is a difficult piece of code.
            // We halt if there is an exception, this is the easy part.
            // We also halt if the stack has no more steps AND there is not previous stack.
            // There can be multiple stacks if there is a nesting, i.e. a command invokes eval itself.
            // In the nested case, the debugger can go on stepping, no problem.
            return stack != null && !excepted && (stack.hasMoreSteps() || stack.getPrevStack() != null);
        }
        catch (InterruptedException e)
        {
            halted = true;
            return false;
        }
    }

    public synchronized void step()
    {
        if(worker == null || excepted || !worker.isAlive()) throw new IllegalStateException();

        try
        {
            while (!halted) this.wait();
            if(!excepted)
            {
                // Clear the breakpoint flag.
                breakpoint = false;

                // Wake up the eval to take a step for us.
                this.notifyAll();
                // We put ourselves in wait mode until the eval has taken a step.
                // The eval has to get us out of the wait queue.
                this.wait();
                stepcount ++;
            }
        }
        catch (InterruptedException e)
        {
            halted = true;
        }
    }

    public synchronized Eval2.EvalStack getStack()
    {
        // No guard because you could always get the last stack for examination,
        // even post mortem.

        try
        {
            // We cant access the stack while the
            // eval is working on it. So we are patient.
            while(!halted) this.wait();
            // If the eval is halted, we can access its stack.
            return stack;
        }
        catch (InterruptedException e)
        {
            halted = true;
            return null;
        }
    }

    /**
     * Obtain the last result produced by the eval under trace.
     * Note that the result can get overwritten by subsequent evaluations, especially by
     * commands using the eval.
     *
     * @return
     */
    public synchronized Object getResult()
    {
        // No guard because you always could get the last result for examination,
        // even post mortem.

        try
        {
            while(!halted) this.wait();
            return result;
        }
        catch (InterruptedException e)
        {
            // The wait could be interrupted because the
            // eval thread ended.
            halted = true;
            return result;
        }
    }

    /**
     * Check if the eval under trace has already reached a result.
     * Note that the result could also be null, it is a valid result.
     *
     * @return
     */
    public synchronized boolean hasResult()
    {
        // No guard because you always could get the last result for examination,
        // even post mortem.

        try
        {
            while(!halted) this.wait();
            return hasResult;
        }
        catch (InterruptedException e)
        {
            // The wait could be interrupted because the
            // eval thread ended.
            halted = true;
            return hasResult;
        }
    }

    /**
     * Stop tracing. No operation is possible after the tracer has halted.
     * All resources held by the eval being traced will be released.
     * Stopping a tracer is necessary in order not to clutter the memory.
     *
     */
   public synchronized void terminate()
   {
       if(worker == null || excepted || !worker.isAlive()) throw new IllegalStateException();

       try
        {
            while(!halted) this.wait();
            worker.interrupt();
            worker = null;
            halted = true;
        }
        catch (InterruptedException e)
        {
            halted = true;
        }
   }

   /**
    * Check if the trace has been halted or not.
    * If it is halted, nothing can be done with it anymore.
    *
    * @return
    */
   public synchronized boolean isTerminated()
   {
       return (worker == null);
   }

   /**
    * Restart the evaluation of the current expression to the beginning.
    * Ignore all intermediate results obtained so far.
    *
    */
   public synchronized void reset()
   {
       if(worker == null || excepted || !worker.isAlive()) throw new IllegalStateException();

       try
        {
            while(!halted) this.wait();
            stack.reset();
        }
        catch (InterruptedException e)
        {
            halted = true;
        }
   }

   /**
    * Restart the evaluation of the current expression to the beginning.
    * Ignore all intermediate results obtained so far.
    *
    */
   public synchronized void dropFrame()
   {
       if(worker == null || excepted || !worker.isAlive()) throw new IllegalStateException();

       try
        {
            while(!halted) this.wait();
            stack.dropFrame();
        }
        catch (InterruptedException e)
        {
            halted = true;
        }
   }

   /**
    * Step backwards. Note that side effects will not be undone.
    *
    */
   public synchronized void backStep()
   {
       if(worker == null || excepted || !worker.isAlive()) throw new IllegalStateException();

       try
        {
            while(!halted) this.wait();

            final Eval2.StackFrame lFrame = stack.top();
            if(lFrame.getDataptr() > 0) lFrame.backStep();
            else stack.dropFrame();
        }
        catch (InterruptedException e)
        {
            halted = true;
        }
   }

   /**
    * Keep on stepping as long as there are more expressions to evaluate.
    * This will walk over the results, this can happen when an intermediate command
    * keeps on evaluating expressions in sequence.
    *
    */
   public synchronized void run()
   {
       // Initialize statistics.
       stepcount = 0;
       breakpoint = false;

       // Take some steps.
       steploop: while(hasMoreSteps() && !excepted)
           if(stepcount > 0 && breakpoints.breakHere(stack))
           {
               breakpoint = true;
               break steploop;
           }
           else step();
   }

   /**
    * Keep on stepping as long as the expression has not been completely evaluated.
    * Note that after the result has been reached, new expressions might be evaluated, so this is
    * not necessarily the end of evaluation. This can happen with intermediary commands.
    *
    */
   public synchronized void runToResult()
   {
       // Initialize run statistics.
       stepcount = 0;
       breakpoint = false;

       // Take the steps.
       steploop: while(!hasResult() && !excepted)
           if(stepcount > 0 && breakpoints.breakHere(stack))
           {
               breakpoint = true;
               break steploop;
           }
           else step();
   }



   public synchronized void stepOut()
   {
       // First we run until the current frame has been evaluated.
       // Note that we keep holding on to the same frame for testing,
       // the one that was on the top when this method was called.
       final Eval2.StackFrame lFrame = getStack().top();
       stepcount = 0;
       breakpoint = false;

       // Take some steps.
       steploop: while(!lFrame.isEvaluated() && !excepted)
           if(stepcount > 0 && breakpoints.breakHere(stack))
           {
               breakpoint = true;
               break steploop;
           }
           else step();
       // Now we take a single step to step out of the frame.
       if(this.hasMoreSteps())
           if(stepcount > 0 && breakpoints.breakHere(stack))breakpoint = true;
           else step();
   }

   public synchronized void runToReady()
   {
       // We run until the current frame has been evaluated.
       // Note that we keep holding on to the same frame for testing,
       // the one that was on the top when this method was called.
       final Eval2.StackFrame lFrame = getStack().top();
       stepcount = 0;
       breakpoint = false;

       // Take some steps.

       steploop: while(
           // If there is no handler, the data slots will not have been initialized (this is the task of the handler)
           // and the slot pointer will not be a reliable metric.
           (lFrame.getHandler() == null) ||
           ((lFrame.getDataptr() < lFrame.getData().length) && !excepted))
           if(stepcount > 0 && breakpoints.breakHere(stack))
           {
               breakpoint = true;
               break steploop;
           }
           else step();
   }

   public synchronized void stepOver()
   {
       if(excepted) return;

       final Eval2.StackFrame lFrame = getStack().top();
       final int lStartSlot = lFrame.getDataptr();

       // Initialize runstatistics.
       stepcount = 0;
       breakpoint = false;

       // Take some steps.
       if(lFrame.isEvaluated())
       {
           if(stepcount > 0 && breakpoints.breakHere(stack)) breakpoint = true;
           else step();
       }
       else
       {
           steploop: while(
               // If there is not yet a handler, the frame slots will not have
               // been initialized and the datapointer will not be a reliable metric.
               lFrame.getHandler() == null ||
               (!lFrame.isEvaluated() && !excepted && (lFrame.getDataptr() <= lStartSlot)))
               if(stepcount > 0 && breakpoints.breakHere(stack))
               {
                   breakpoint = true;
                   break steploop;
               }
               else step();
       }
   }

   public synchronized boolean isExcepted()
   {
       try
       {
           while(!halted) this.wait();
           return excepted;
       }
       catch (InterruptedException e)
       {
           // The wait could be interrupted because the eval thread ended.
           halted = true;
           return excepted;
       }
   }

   public synchronized Exception getException()
   {
       // No guard because you always could get the last result for examination,
       // even post mortem.

       try
       {
           while(!halted) this.wait();
           return exception;
       }
       catch (InterruptedException e)
       {
           // The wait could be interrupted because the
           // eval thread ended.
           halted = true;
           return exception;
       }
   }

    public BreakpointSet getBreakpoints()
    {
        return breakpoints;
    }

    public void setBreakpoints(BreakpointSet breakpoints)
    {
        this.breakpoints = breakpoints;
    }

    public boolean isBreakpointEncountered()
    {
        return breakpoint;
    }
}