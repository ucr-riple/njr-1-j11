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
import java.util.Iterator;
import java.util.List;


/** The evaluator understands a simplified lisp syntax.
 * Explicitly lacking: data structures, data structure manipulation. It should be done using Java commands and an
 * underlying Java model. The language on top should help to manipulate the underlying Java model.
 * <p>
 * Built-in commands:
 * <ul>
 *  <li><b><code>quote</code></b> prevents evaluation of an expression: <code>(quote &lt;expr&gt;)</code> or the shorthand <code>'&lt;expr&gt.</code>
 *     It is necessary to provide this construct so that the user can use unevaluated expressions to describe data structures or other parameters
 *     that can be provided to the commands.</li>
 *  <li><b><code>if</code></b> expression has the form <code>(if &lt;bool-expr> &lt;then-expr> &lt;else-expr>)</code>.
 *      It is a special form because the evaluation of the <code>then-expr</code> or <code>else-expr</code> depends on the outcome of the test.
 *      Since the evaluation order of all boolean constructs is deviant from normal evaluation they have to be built into the core.
 *    <ul>
 *       <li><i>TRUTHY</i>: yes, y, on, true, t, non-null</li>
 *       <li><i>FALSY</i>: 0, no, off, false, null, empty collection, non-empty strings different from the negative values listed before.</li>
 *     </ul>
 *  </li>
 *  <li><b><code>while</code></b> <code>(while &lt;bool-expr> &lt;expr>)</code>.
 *  <li><b><code>set</code></b> changes an existing binding. It evaluates the value before setting, the result is the value: <code>(set name val) | (set name=val)</code>.
 *    Set generates an error if the binding does not exist. A binding can initially be created using one of the following constructs. Afther a binding is created it
 *    can be modified with <code>set</code>.
 *    <ul>
 *       <li>A <code>defvar</code> which creates/overwrites a global binding.
 *       <li>A <code>defun</code> which (re-)binds a global variable to a lambda.
 *       <li>A <code>let</code> or <code>let*</code> block which adds a number of bindings for the duration of a block.
 *       <li>Some commands add something to the context too. It is up to the various commands to specify this.
 *    </ul>
 *  </li>
 *  <li><b><code>get</code></b> retrieves a binding. It does not do any evaluation: <code>(get name)</code> or the shorthand notational convenience <b><code>$name</code></b> does exactly the same thing.
 *      It does not do Perl-like interpolation in strings, don't let the notation mislead you.</li>
 *  <li><b><code>defvar</code></b> creates a global binding. It evaluates the value before setting, the result is the value: <code>(defvar name val) | (defvar name=val)</code>. The value can be changed with <code>set</code>.</li>
 *  <li><code><b>let, let*</b></code> defines variables locally: <code>(let ((var val) | var=val | var ...) expr)</code></li>
 *  <li><code><b>bound?</b></code> See if a binding exists.</li>
 *  <li><b><code>and, or, not</code></b>. Shortcut boolean evaluation operators.</li>
 *  <li><b><code>eq</code></b> has Java equals semantics. It is the only data inspection that is implemented in the Eval.
 *    It is included because it is a standard Java Object method, applicable to all instances.</li>
 *  <li><b><code>eval</code></b> evaluate an expression.</li>
 *  <li><b><code>lambda</code></b> A nameless function, it contains a reference to the lexical context where it was defined. So this creates closures.</li>
 *  <li><b><code>defun</code></b> <code>(defun name (&lt;params>) &lt;expr>)</code> User defined functions, they are bound in the
 *      same context as the variables are. Functions are bound in the global context.
 *     <ul>
 *        <li><b><code>name</code></b> should be a string. The name is not evaluated.</li>
 *        <li><b><code>(&lt;params>)</code></b>The parameter list a list of strings. The list is not evaluated.</li>
 *     </ul>
 *  </li>
 *  <li><b><code>funcall</code></b> <code>(funcall name &lt;args&gt;)</code>. It is the official way to call a user defined function,
 *     but the shorthand is a call of the form <code>(name arg-list)</code>. This form will lead to a function call if there was no registered command with the same name.
 *     <ul>
 *        <li><b><code>name</code></b> should be a string and is not evaluated.</li>
 *        <li><b><code>&lt;args&gt;</code></b> The arguments in a separate list.</li>
 *     </ul>
 *  </li>
 *  <li><b><code>progn</code></b> which accepts a list of expressions which are evaluated in order: <code>(progn expr1 expr2 ...)</code>.</li>
 * </ul>
 * <p>
 * Remarks:
 * <ul>
 *    <li>Lists are Java lists and not conses. So list semantics is different (and maybe less efficient).
 *        There is no 'nil' concept; a list does not end with a nil, a nil is not the same as an empty list.</li>
 *    <li>No separate name spaces for different constructs, there is only a single context stack.</li>
 *    <li>Contexts have side effects, bindings can be changed.</li>
 *    <li>Only strings are provided, there is no 'symbol' concept. If an application wants e.g. numbers
 *        for calculation, the commands should parse the strings.</li>
 *    <li>Binding context lookup order. Scoping is lexical. The global context is always available for everybody,
 *        there is no need to introduce dynamic scoping.
 *        <ol>
 *           <li>Call context, arguments are bound to parameters. This context is especially created for this call. It contains all local bindings.</li>
 *           <li>Lexical (static) context, where the function or lambda was defined. It is the closure of the lambda.</li>
 *        </ol></li>
 *     <li>Interrupting the eval thread will lead to a CommandException. This is the preferred way to stop a runaway script.</li>
 * </ul>
 *
 */
public class Eval
extends AbstractEval
{
    private CommandRepository commands;
    private CommandRepository macros;

    public Eval()
    {
        this(new BasicContext());
    }

    public Eval(IContext aContext)
    {
        super(aContext);
        commands = new CommandRepository();
        macros = new CommandRepository();
    }

    public void setCommandRepo(CommandRepository aRepo)
    {
        commands = aRepo;
    }

    public CommandRepository getCommandRepo()
    {
        return commands;
    }

    public void setMacroRepo(CommandRepository aRepo)
    {
       macros = aRepo;
    }

    public CommandRepository getMacroRepo()
    {
        return macros;
    }

    /**
     * Evaluate an expression in the root REPL context.
     *
     * @param aExpr An expression.
     * @return The result of evaluation.
     * @throws CommandException
     */
    public Object eval(Object aExpr)
    throws CommandException
    {
        try
        {
            return eval(aExpr, getContext());
        }
        catch (StackOverflowError e)
        {
            // Protection against runaway user functions. This probably is a bug
            // like an uncontrolled recursion or a indefinite loop.
            throw new CommandException("Stack overflow, too deep of a recursion.");
        }
    }

    /**
     * Evaluate an expression in an arbitrary context.
     * Commands could use this method to evaluated expressions internally.
     *
     * @param aExpr An expression.
     * @param aContext An evaluation context.
     * @return The result of evaluation.
     * @throws CommandException
     */
    @SuppressWarnings("unchecked")
    public Object eval(Object aExpr, IContext aContext)
    throws CommandException
    {
        try
        {
            // Stop evaluation if the current evaluation thread got a signal to
            // stop processing the expression.
            if(Thread.interrupted()) throw new CommandException("The evaluation has been interrupted.");

            if(!(aExpr instanceof List))
            {
                // I. Atomic expressions
                ////////////////////////

                if(aExpr instanceof String && ((String)aExpr).startsWith("$"))
                {
                    // Syntactic sugar. $name is equivalent to (get name).
                    return aContext.getBinding(((String)aExpr).substring(1));
                }
                else if(aExpr instanceof Pair)
                {
                    // Pairs are meant to make key/value pairs easier.
                    final Pair lPair = (Pair) aExpr;
                    final Object lEvaluatedLeft = eval(lPair.getLeft(), aContext);
                    final Object lEvaluatedRight = eval(lPair.getRight(), aContext);
                    return new Pair(lEvaluatedLeft, lEvaluatedRight);
                }
                else
                {
                    // All other values except lists evaluate to
                    // themselves! This is quite different from the original LISP.
                    // Our goal is to have a handy and flexible REPL that can handle model objects or "handles"
                    // that do not have a list representation.
                    ////////////////////////////////////////////
                    return aExpr;
                }
            }
            else
            {
                // II. List composite expressions
                /////////////////////////////////

                // Add a List view to the expression.
                final List<Object> lList = (List<Object>) aExpr;
                final int lListSize = lList.size();

                // An empty list is never evaluated, but we must
                // return a new empty list as a result in order to prevent
                // modification to the expresson itself.
                if(lListSize == 0) return new ArrayList();
                Object lCmdCandidate = lList.get(0);

                // 1. Special forms are not evaluated as a normal form.
                //    Order of evaluation of the arguments can be controlled
                //    in this section.
                ////////////////////////////////////////////////////////////

                if("quote".equals(lCmdCandidate))
                {
                    // If it is a quoted list, we don't evaluate its elements, the result is again, the list.
                    // In this way you can use lists as data structures and not as a function call.
                    // It is a special form because it influences the way the expression is (not) evaluated, it is non-standard.
                    if(lListSize == 2) return lList.get(1);
                    else throw new CommandException("The 'quote' form should have the format (quote <expr>).");
                }
                else if("if".equals(lCmdCandidate))
                {
                    // It is a special form because only one of the then or else part is evaluated depending on the outcome of the test.
                    // It shortcuts evaluation of the other branch.

                    // Quick test on the number of arguments.
                    if(lListSize < 3 || lListSize > 4) throw new CommandException("The 'if' form should have the format (if <bool-expr> <then-expr> [<else-expr>]).");
                    if(boolEval(eval(lList.get(1), aContext))) return eval(lList.get(2), aContext);
                    else if(lListSize == 4) return eval(lList.get(3), aContext);
                    else return null;
                }
                else if("while".equals(lCmdCandidate))
                {
                    // It is a special form because the test is evaluated again and again.

                    // Quick test on the number of arguments.
                    if(lListSize < 2 || lListSize > 3) throw new CommandException("The 'while' form should have the format (while <bool-expr> [<expr>]).");
                    Object lLastResult = null;
                    while(boolEval(eval(lList.get(1), aContext)))
                        if(lListSize == 3) lLastResult = eval(lList.get(2), aContext);
                    return lLastResult;
                }
                else if("and".equals(lCmdCandidate))
                {
                    // It is a special form because it shortcuts evaluation if it encounters a
                    // false value.

                    // Quick test on the number of arguments.
                    if(lListSize <= 1) throw new CommandException("The 'and' form should have the format (and <bool-expr>+).");
                    Iterator<Object> lIter = lList.iterator();
                    // Skip the "and"
                    lIter.next();
                    // We evaluate the arguments until we encounter a false one.
                    // We don't evaluate the arguments after the false one.
                    while(lIter.hasNext())
                    {
                        final Object lArg = lIter.next();
                        if(!boolEval(eval(lArg, aContext))) return Boolean.FALSE;
                    }
                    return Boolean.TRUE;
                }
                else if("or".equals(lCmdCandidate))
                {
                    // It is a special form because it shortcuts evaluation when a single true value
                    // was found

                    // Quick test on the number of arguments.
                    if(lListSize <= 1) throw new CommandException("The 'or' form should have the format (and <bool-expr>+).");
                    Iterator<Object> lIter = lList.iterator();
                    // Skip the "or"
                    lIter.next();
                    // We evaluate the arguments until we encounter a true one.
                    // We don't evaluate the arguments after the true one.
                    while(lIter.hasNext())
                    {
                        final Object lArg = lIter.next();
                        if(boolEval(eval(lArg, aContext))) return Boolean.TRUE;
                    }
                    return Boolean.FALSE;
                }
                else if("not".equals(lCmdCandidate))
                {
                    // Quick test on the number of arguments.
                    if(lListSize != 2) throw new CommandException("The 'not' form should have the format (not <bool-expr>).");
                    if(boolEval(eval(lList.get(1), aContext))) return Boolean.FALSE;
                    else return Boolean.TRUE;
                }
                else if("set".equals(lCmdCandidate) || "defvar".equals(lCmdCandidate))
                {
                    Object lName;
                    Object lValue;

                    if(lListSize == 2)
                    {
                        // Variant (xxx name=value)
                        //
                        Object lPairCand = lList.get(1);
                        if(!(lPairCand instanceof Pair))
                            throw new CommandException(String.format("The '%s' form should have the format (%s name value).",lCmdCandidate, lCmdCandidate));
                        Pair lPair = (Pair) lPairCand;
                        lName = eval(lPair.getLeft(), aContext);
                        lValue = eval(lPair.getRight(), aContext);
                    }
                    else if(lListSize == 3)
                    {
                        // Variant (xxx name value)
                        //
                        lName = eval(lList.get(1), aContext);
                        lValue = eval(lList.get(2), aContext);
                    }
                    else
                    {
                        throw new CommandException(String.format("The '%s' form should have the format (%s name value).",lCmdCandidate, lCmdCandidate));
                    }

                    // Check the type of the name.
                    if(!(lName instanceof String)) throw new CommandException(String.format("The first argument in the '%s' form should evaluate to a string.", lCmdCandidate));
                    final String lNameRepr = (String) lName;

                    if("set".equals(lCmdCandidate)) aContext.setBinding(lNameRepr, lValue);
                    else aContext.getRootContext().defBinding(lNameRepr, lValue);

                    return lValue;
                }
                else if("let".equals(lCmdCandidate) || "let*".equals(lCmdCandidate))
                {
                    if(lListSize != 3) throw new CommandException(String.format("The '%s' form should have the format (%s ((name val)...) expr).",lCmdCandidate, lCmdCandidate));
                    final Object lBindings = lList.get(1);
                    final Object lExpr = lList.get(2);
                    final boolean letrec = "let*".equals(lCmdCandidate);

                    // Check the type of the list of bindings.
                    if(!(lBindings instanceof List))
                        throw new CommandException(String.format("The '%s' form should have the format (%s ((name val)...) expr).\nThe first parameter should be a list of bindings but encountered an instance of type '%s'.",lCmdCandidate, lCmdCandidate, lBindings==null?"null":lBindings.getClass().getCanonicalName()));
                    final List lBindingsList = (List) lBindings;

                    // For let we will accumulate the eval results of the bindings
                    // in this list and add them to the context after all bindings have been evaluated.
                    final List<Pair> lBindingPrep  = new ArrayList<Pair>();
                    // The let or let* context.
                    // For let* we will incrementally use the new context.
                    final IContext lLetCtx = new CompositeContext(new BasicContext(), aContext);

                    for(Object lBinding : lBindingsList)
                    {
                        if(lBinding instanceof String)
                        {
                            final String lName = (String) lBinding;
                            if(letrec) lLetCtx.defBinding(lName, null);
                            else lBindingPrep.add(new Pair(lName, null));
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
                                    throw new CommandException(String.format("The '%s' form should have the format (%s ((name val) | name=val ...) expr).\nEach binding should be a list of length 2 of the form (var val).",lCmdCandidate, lCmdCandidate));
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
                                throw new CommandException(String.format("The '%s' special should have the format (%s ((name val) | name=val ...) expr).\nEach binding should be a list  of the form (var val).\nThe first element should be a string but encountered an instance of type '%s'.",lCmdCandidate, lCmdCandidate, lKey==null?"null":lKey.getClass().getCanonicalName()));

                            final String lName = (String) lKey;
                            if(letrec) lLetCtx.defBinding(lName, eval(lValExpr, lLetCtx));
                            else lBindingPrep.add(new Pair(lName, eval(lValExpr, lLetCtx)));
                        }
                        else
                        {
                            throw new CommandException(String.format("The '%s' form should have the format (%s ((name val) | name=val ...) expr).\nEach binding should be a list or a string or a pair but encountered an instance of type '%s'.",lCmdCandidate, lCmdCandidate, lBinding==null?"null":lBinding.getClass().getCanonicalName()));
                        }
                    }

                    if(!letrec)
                    {
                        // for let (not for let*) we will now add the bindings to the context.
                        for (Pair lPair:lBindingPrep) lLetCtx.defBinding((String)lPair.getLeft(), lPair.getRight());
                    }
                    // Evaluate the body in our freshly created context.
                    return eval(lExpr, lLetCtx);
                }
                else if("get".equals(lCmdCandidate))
                {
                    // Quick test on the number of arguments.
                    if(lListSize != 2) throw new CommandException("The 'get' form should have the format (get name).");
                    Object lName = eval(lList.get(1), aContext);

                    // Check the type of the name.
                    if(!(lName instanceof String)) throw new CommandException("The first argument in the 'get' form should evaluate to a string.");
                    return aContext.getBinding((String) lName);
                }
                else if ("lambda".equals(lCmdCandidate))
                {
                    // It is a special form because the parameter list nor the function body
                    // should be evaluated at this point.

                    //Quick test on the number of arguments.
                    if(lListSize != 3) throw new CommandException("The 'lambda' form should have the format (lambda (<params>) <expr>).");
                    // Parameters are *not* evaluated ...
                    final Object lParams = lList.get(1);
                    final Object lBody = lList.get(2);

                    // Do some checking.
                    if(lParams == null || !(lParams instanceof List)) throw new CommandException("The first argument in the 'lambda' form should evaluate to a list of parameters.");
                    List lParamList = (List) lParams;
                    for(Object lParam:lParamList) if(!(lParam instanceof String)) throw new CommandException("The first argument in the 'lambda' form, the parameter list,  should evaluate to a list of strings.");
                    if(lBody == null) throw new CommandException("The second argument in the 'lambda' form should be an expression.");

                    // Construct the lambda.
                    final String[] lStrArgs = new String[lParamList.size()];
                    for(int i = 0; i < lParamList.size(); i++) lStrArgs[i] = (String) lParamList.get(i);

                    return new Lambda(lStrArgs, lBody, aContext);
                }
                else if("defun".equals(lCmdCandidate))
                {
                    // It is a special form because the parameter list nor the function body
                    // should be evaluated at this point.

                    // Quick test on the number of arguments.
                    if(lListSize != 4) throw new CommandException("The 'defun' form should have the format (defun name (<params>) <expr>).");
                    // Name and parameters are *not* evaluated ...
                    final Object lName = lList.get(1);
                    final Object lParams = lList.get(2);
                    final Object lBody = lList.get(3);

                    // Do some checking.
                    if(lName == null || !(lName instanceof String)) throw new CommandException("The first argument in the 'defun' form should evaluate to a string.");
                    if(lParams == null || !(lParams instanceof List)) throw new CommandException("The second argument in the 'defun' form should evaluate to a list of parameters.");
                    final List lParamList = (List) lParams;
                    for(Object lParam:lParamList) if(!(lParam instanceof String)) throw new CommandException("The second argument in the 'defun' form, the parameter list,  should evaluate to a list of strings.");
                    if(lBody == null) throw new CommandException("The third argument in the 'defun' form should be an expression.");

                    // Create a lambda macro.
                    final List lLambdaMacro = new ArrayList(3);
                    lLambdaMacro.add("lambda");
                    lLambdaMacro.add(lParams);
                    lLambdaMacro.add(lBody);

                    // Evaluate the lambda.
                    // Bind the resulting lambda to the name GLOBALLY!
                    final Lambda lLambda = (Lambda) eval(lLambdaMacro, aContext);
                    aContext.getRootContext().defBinding((String) lName, lLambda);
                    return lLambda;
                }
                else if("timer".equals(lCmdCandidate))
                {
                    if(lListSize != 2) throw new CommandException("The 'timer' form should have the format (timer expr).");

                    long lStart = System.currentTimeMillis();
                    eval(lList.get(1), aContext);
                    long lStop = System.currentTimeMillis();
                    return lStop - lStart;
                }
                else if(lCmdCandidate instanceof String && macros.hasCommand((String)lCmdCandidate))
                {
                    // Built-in macro call.
                    final ICommand lMacro = macros.getCommand((String)lCmdCandidate);
                    final List lArgs = new ArrayList(lList.size());
                    lArgs.addAll(lList);

                    try
                    {
                        // Macro expansion in progress!
                        //
                        return eval(lMacro.execute(this, aContext, lArgs.toArray()), aContext);
                    }
                    catch(CommandException e)
                    {
                        // This type of error will be handled by our general mechanism.
                        // It does not need special handling here.
                        throw e;
                    }
                    catch(Exception e)
                    {
                        // A non-CommandException is converted into a command exception here.
                        throw new CommandException(String.format("Macro '%s' failed.\n%s", lCmdCandidate, concatExceptionMessages(e)));
                    }
                }

                // 2. All the other lists are evaluated in a standard way.
                //    This part is the standard evaluation.
                //    Order of evaluation cannot be controlled after this because
                //    they have been evaluated automatically.
                ///////////////////////////////////////////////////////////

                // Evaluation should be non destructible, therefore we must evaluate to a new list.
                // If we would replace the expressions by their evaluations, we would not
                // be able to re-evaluate expressions ...
                final List lEvalList = new ArrayList(lList.size());

                // Evaluate all the elements in the list.
                // The number of elements should remain the same.
                for(Object lExpr : lList)
                    lEvalList.add(eval(lExpr, aContext));

                // Re-fetch the command candidate, it might have changed by the evaluation
                lCmdCandidate = lEvalList.get(0);

                if(lListSize > 0)
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
                            if(lListSize != 2) throw new CommandException("The 'eval' form should have a single argument.");
                            return eval(lEvalList.get(1), aContext);
                        }
                        else if("eq".equals(lCmdName))
                        {
                            if(lListSize != 3) throw new CommandException("The 'eq' form should have two arguments.");
                            Object lArg1 = eval(lEvalList.get(1), aContext);
                            Object lArg2 = eval(lEvalList.get(2), aContext);
                            if(lArg1 == null && lArg2 == null) return Boolean.TRUE;
                            else if(lArg1 == null) return Boolean.FALSE;
                            else if(lArg2 == null) return Boolean.FALSE;
                            else return lArg1.equals(lArg2);
                        }
                        else if("bound?".equals(lCmdName))
                        {
                            if(lListSize != 2) throw new CommandException("The 'bound?' form should have a single argument.");
                            Object lArg = lEvalList.get(1);
                            if(lArg instanceof String)
                            {
                                String lName = (String) lArg;
                                return aContext.isBound(lName);
                            }
                            else throw new CommandException("The 'bound?' form should have a single string argument.");
                        }
                        else if ("progn".equals(lCmdCandidate))
                        {
                            if(lListSize < 2) throw new CommandException("The 'progn' form should have at least one argument.");
                            return lEvalList.get(lEvalList.size() - 1);
                        }
                        else if("funcall".equals(lCmdName))
                        {
                            // This is the 'official' way of executing a method.
                            // The other methods are macro's that will sooner or later evaluate to this construct.
                            // This is the real deal (whereas the other constructs should be seen as syntactic sugar).

                            // Quick test on the number of arguments.
                            if(lListSize < 2) throw new CommandException("The 'funcall' form should have the format (callfun name <args>).");
                            // Test the function name / lambda.
                            final Object lName = lEvalList.get(1);

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
                                Object lFunCandidate = aContext.getBinding((String) lName);
                                if(!(lFunCandidate instanceof Lambda)) throw new CommandException(String.format("Function \"%s\" was not found in the context.", lName));
                                lFun = (Lambda) lFunCandidate;
                            }
                            else
                            {
                                // Trouble.
                                // We found something in the beginning of the list that does not evaluate to a lambda name or a lambda itself.
                                if(lName == null) throw new CommandException("The first argument in the 'funcall' form should evaluate to a string or a lambda, but we received 'null'.");
                                throw new CommandException(String.format("The first argument in the 'funcall' form should evaluate to a string or a lambda.\n Received an instance of class '%s'.", lName.getClass().getCanonicalName()));
                            }

                            try
                            {
                                // Finally THE REAL DEAL!
                                /////////////////////////

                                // Context that binds the parameters to the arguments in addition to the lexical context.
                                // Note that we skip the 'funcall' constant and the function name/lambda when constructing
                                // the argument list.
                                final IContext lFunCtx = lFun.createContext(lEvalList.subList(2, lEvalList.size()).toArray(), 0, lEvalList.size() - 2);
                                return eval(lFun.getExpr(), lFunCtx);
                            }
                            catch(CommandException e)
                            {
                                // This type of error will be handled by our general mechanism.
                                // It does not need special handling here.
                                throw e;
                            }
                            catch(Exception e)
                            {
                                // A non-CommandException is converted into a command exception here.
                                throw new CommandException(String.format("Function call '%s' failed.\n%s", lName, concatExceptionMessages(e)));
                            }
                        }
                        else if(commands.hasCommand(lCmdName))
                        {
                            try
                            {
                                final ICommand lCmd = commands.getCommand(lCmdName);
                                return lCmd.execute(this, aContext, lEvalList.toArray());
                            }
                            catch(CommandException e)
                            {
                                // This type of error will be handled by our general mechanism.
                                // It does not need special handling here.
                                throw e;
                            }
                            catch(Exception e)
                            {
                                // A non-CommandException is converted into a command exception here.
                                throw new CommandException(String.format("Command '%s' failed.\n%s", lCmdName, concatExceptionMessages(e)));
                            }
                        }
                        else if(aContext.isBound(lCmdName) && aContext.getBinding(lCmdName) instanceof Lambda)
                        {
                            // SYNTACTIC SUGAR.
                            // This is syntactic sugar so that know and declared user functions
                            // can be called without 'funcall' just like the built-in functions.

                            final List lMacro = funcallMacro(lEvalList);
                            return eval(lMacro, aContext);
                        }
                        else
                        {
                            throw new CommandException(String.format("Command or form '%s' does not exist.", lCmdName));
                        }
                    }
                    else if(lCmdCandidate instanceof Lambda)
                    {
                        // SYNTACTIC SUGAR.
                        // This is syntactic sugar so that lambda's
                        // can be called without 'funcall' just like the built-in functions.

                        final List lMacro = funcallMacro(lEvalList);
                        return eval(lMacro, aContext);
                    }
                    else
                    {
                        // Error, name of the command should be a string or a lambda.
                        if(lCmdCandidate == null) throw new CommandException(String.format("The command name should evaluate to a string or a lambda. Found null."));
                        else throw new CommandException(String.format("The command name should evaluate to a string or a lambda.\nFound an instance '%s' of class \"%s\", which cannot be interpreted as a function.", lCmdCandidate.toString(), lCmdCandidate.getClass().getName()));
                    }
                }
                else
                {
                    // Error, the list does not contain a command.
                    throw new CommandException(String.format("An empty list cannot be executed."));
                }
            }
        }
        // The structure of the exception handling block is important:
        // * First we catch our own internal CommandExceptions.
        //   We know that this exception already contains an explanatory message, because we create these ourselves.
        //   We augment the message with a stack trace, each eval invocation will add information as
        //   the stack trace is unwound.
        // * The second clause catches general Exceptions. This indicates a programming error.
        //   Nevertheless, we catch this and turn it into our own CommandException.
        //   After this, it will participate in the stack-unwinding-info as well.
        catch(CommandException e)
        {
            // There are limits to our helpfulness ...
            final int lTotalMsgLim = 1000;
            final int lEntryMsgLim = 80;

            String lMsg = e.getMessage();
            // If the message becomes too large, we will stop augmenting it in order
            // not to crash our little evaluator.
            if(lMsg.length() >= lTotalMsgLim)
            {
                if(!lMsg.endsWith("-> ..."))
                    lMsg = lMsg + "\n-> ...";
            }
            // We augment the exception with information so that the user can pinpoint the error.
            else lMsg = String.format("%s\n-> %s", e.getMessage(), limitMsg(aExpr, lEntryMsgLim));
            throw new CommandException(lMsg);
        }
        catch(Exception e)
        {
            // A non-CommandException is converted into a command exception here.
            throw new CommandException(String.format("Evaluation failed.\n%s", concatExceptionMessages(e)));
        }
    }

    private String concatExceptionMessages(Throwable e)
    {
        final String lInitialMsg = e.getMessage()==null?e.getClass().getSimpleName():e.getMessage();
        final StringBuilder lBuilder = new StringBuilder(lInitialMsg);
        Throwable t = e;
        while (t.getCause() != null)
        {
            t = t.getCause();
            final String lMsg = t.getMessage()==null?t.getClass().getSimpleName():t.getMessage();
            lBuilder.append("\n").append(lMsg);
        }
        return lBuilder.toString();
    }

    private String limitMsg(Object aObj, int aLen)
    {
        if(aObj == null) return "";
        String lMsg = aObj.toString();
        if(lMsg.length() > aLen) return lMsg.substring(0, aLen - 5) + " ...";
        else return lMsg;
    }

    @SuppressWarnings("unchecked")
    private List funcallMacro(List anExpr)
    {
        // Prepare a new expression.
        final List lMacro = new ArrayList(anExpr.size() + 1);
        lMacro.add("funcall");
        // We have to quote the arguments so that they are not evaluated anymore!
        // All the arguments have already been evaluated by the automatic evaluation process.
        for(Object lArg: anExpr)
        {
            final List lQuotedArg = new ArrayList(2);
            lQuotedArg.add("quote");
            lQuotedArg.add(lArg);
            lMacro.add(lQuotedArg);
        }
        return lMacro;
    }
}
