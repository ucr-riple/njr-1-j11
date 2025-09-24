package de.codesourcery.dcpu16.codegeneration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import de.codesourcery.dcpu16.ast.AST;
import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.Block;
import de.codesourcery.dcpu16.ast.DoLoopNode;
import de.codesourcery.dcpu16.ast.ForLoopNode;
import de.codesourcery.dcpu16.ast.FunctionDefinitionNode;
import de.codesourcery.dcpu16.ast.FunctionInvocation;
import de.codesourcery.dcpu16.ast.ISymbol;
import de.codesourcery.dcpu16.ast.IfNode;
import de.codesourcery.dcpu16.ast.InlineAssemblyNode;
import de.codesourcery.dcpu16.ast.NumberLiteralNode;
import de.codesourcery.dcpu16.ast.OperatorNode;
import de.codesourcery.dcpu16.ast.ReturnNode;
import de.codesourcery.dcpu16.ast.StatementNode;
import de.codesourcery.dcpu16.ast.StringLiteralNode;
import de.codesourcery.dcpu16.ast.TermNode;
import de.codesourcery.dcpu16.ast.VariableDefinition;
import de.codesourcery.dcpu16.ast.VariableReferenceNode;
import de.codesourcery.dcpu16.ast.WhileLoopNode;
import de.codesourcery.dcpu16.codegeneration.CPUModel.SlotMapping;
import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.compiler.Identifier;
import de.codesourcery.dcpu16.compiler.Operator;
import de.codesourcery.dcpu16.parser.ASTUtils;
import de.codesourcery.dcpu16.parser.ASTUtils.IAdvancedVisitor;
import de.codesourcery.dcpu16.parser.ASTUtils.IIterationContext;
import de.codesourcery.dcpu16.util.ExpressionPrinter;

public class CodeGenerator
{
    private final LabelManager labelManager = new LabelManager();
    
    private final IAssemblyWriter writer;
    private final AST ast;

    private final ExpressionPrinter expressionPrinter = new ExpressionPrinter() {

        @Override
        protected String getLabelForVariable(VariableReferenceNode ref)
        {
            return labelManager.defineLabel(ref).identifier;
        }

    };    

    public CodeGenerator(IAssemblyWriter writer,AST ast) {
        this.writer = writer;
        this.ast = ast;
    }
    
    protected static ASTNode unwrapStatement(ASTNode n) {
        return n instanceof StatementNode ? ((StatementNode) n).child(0) : n;
    }

    public void generateCode() 
    {
        final List<FunctionDefinitionNode> functions = new ArrayList<>();
        final List<VariableDefinition> globalVars = new ArrayList<>();

        FunctionDefinitionNode mainFunction=null;
        for ( ASTNode node : ast.getChildren() ) 
        {
            node = unwrapStatement(node);
            
            if ( node instanceof FunctionDefinitionNode ) 
            {
                FunctionDefinitionNode fun = (FunctionDefinitionNode) node;
                if ( fun.getName().getStringValue().equals("main" ) ) {
                    mainFunction = fun;
                } else {
                    functions.add( fun );
                }
            } else if ( node instanceof VariableDefinition ) {
                globalVars.add( (VariableDefinition) node);
            }
        }

        final IAssemblyEmitter globalWriter = createIAssemblyWriter( ast.getGlobalScope() );

        // output .dat directives for global variables
        for ( VariableDefinition def : globalVars ) {
            outputGlobalVarDefinition( def , globalWriter );
        }   

        if ( mainFunction != null ) 
        {
            final LabelDefinition mainLabel = labelManager.getOrDefineLabel( mainFunction ); 
            final LabelDefinition endLabel = labelManager.defineLabel( ast.getGlobalScope() , new Identifier( "end" ) );   

            globalWriter.load( endLabel , mainFunction);
            globalWriter.pushValue( "store return address" ); 
            globalWriter.jumpTo( mainLabel.ref() , "jump to main method" );

            globalWriter.defineCodeLabel( endLabel );
        }

        globalWriter.outputCommentLine("======== Program ends here =========");

        globalWriter.halt("Terminate program");
        
        globalWriter.close();
        
        if ( mainFunction != null ) 
        {
            outputFunction( ast.getGlobalScope() , mainFunction );
        }

        // output functions
        for ( FunctionDefinitionNode node  : functions ) 
        {
            outputFunction( ast.getGlobalScope() , node );
        }
    }

    private IAssemblyEmitter createIAssemblyWriter(IScope scope) 
    {
        return new DefaultAssemblyEmitter( writer , scope ) 
        {

            protected String createLocalLabel(IScope scope) {
                return labelManager.defineTemporaryLabel( scope ).identifier;
            }
            
            @Override
            protected String getLabel(ISymbol symbol) 
            {
                return labelManager.getLabel( symbol ).identifier;
            }
        };
    }

    private void outputFunction(final IScope globalScope , final FunctionDefinitionNode node) 
    {
        // Use a _NEW_ IAssemblyWriter for each method body
        final IAssemblyEmitter manager = createIAssemblyWriter( node.getScope() );

        // output method label
        LabelDefinition functionLabel = labelManager.getOrDefineLabel( node );

        manager.outputCommentLine(" ========== FUNCTION: "+node.getSignatureAsString() );
        manager.defineCodeLabel( functionLabel );
        
        manager.onFunctionEntry();        

        // process BODY block
        Block body = null;
        for ( ASTNode child : node.getChildren() ) 
        {
            if ( child instanceof Block ) 
            {
                if ( body != null ) {
                    throw new IllegalStateException("Function "+node+" has more than one body block ?");
                }
                body = (Block) child;
            }
        }

        if ( body == null || body.hasNoChildren() ) 
        {
            throw new IllegalStateException("Function "+node+" has no / empty body block ?");
        }

        // visit method body
        for (Iterator<ASTNode> it = body.getChildren().iterator(); it.hasNext();) 
        {
            final ASTNode child = it.next();
            emit( node , node.getScope() , child , manager );
        }
    }

    private void returnFromFunction(FunctionDefinitionNode node,IAssemblyEmitter writer) 
    {
        writer.beforeFunctionExit( );
        
        if ( ! node.isVoidFunction() ) 
        {
            // pop return address to auxiliary register 
            final SlotMapping returnAddress = writer.popValueToAux( "return address of "+node.getSignatureAsString() );

            // push result
            writer.pushValue( "result of "+node.getSignatureAsString());

            // set PC to return address
            writer.jumpTo(returnAddress, "return from "+node.getSignatureAsString() );                
        } 
        else 
        {
            // return from method
            writer.returnFromSubroutine( "return from "+node.getSignatureAsString() );
        }

        writer.outputCommentLine("=== end of function: "+node.getSignatureAsString());
        writer.close();
    }

    private void emit(final FunctionDefinitionNode currentFunction, 
            final IScope scope , 
            final ASTNode expression , 
            final IAssemblyEmitter writer) 
    {
        final IAdvancedVisitor v = new IAdvancedVisitor() {

            @Override
            public void visit(ASTNode n, int depth, IIterationContext context) 
            {
                if ( n instanceof StatementNode ) {
                    writer.endOfStatement(n.child(0));
                    return;
                }
                
                if (n instanceof VariableReferenceNode ) 
                {
                	boolean loadAddress = false;
                	if ( isAssignmentOperator( n.getParent() ) && n.getParent().indexOf( n ) == 0 ) {
                		loadAddress = true;
                	} 
                	else if ( isDereferenceOperator( n.getParent() ) && isAssignmentOperator( n.getParent().getParent() ) &&
                			n.getParent().getParent().indexOf( n.getParent() ) == 0 ) 
                	{
                		loadAddress = true;
                	}
                	if ( loadAddress ) {
                		writer.loadAddress( n , "loading EA , in assignment");
                	} else {
                		writer.load( n );
                	}
                } 
                else if ( n instanceof StringLiteralNode ) 
                {
                    StringLiteralNode str = (StringLiteralNode) n;
                    if ( str.isSingleCharacter() ) 
                    {
                        writer.getHandle( str );
                    }
                    else 
                    {
                        LabelDefinition label = labelManager.defineTemporaryLabel( scope );
                        writer.outputValues( label, valuesToString( Collections.singletonList( n ) ) );
                        writer.load( label , str );
                    }
                } 
                else if ( n instanceof OperatorNode) 
                {
                    final OperatorNode op = (OperatorNode) n;
                    final Operator operatorType = op.getOperator();

                    final TermNode child0 = (TermNode) op.child(0);
                    final TermNode child1 = operatorType.getOperandCount() > 1 ? (TermNode) op.child(1) : null;

                    if ( operatorType == Operator.ARRAY_SUBSCRIPT ) 
                    {
                        emit( currentFunction , scope, child1 , writer );                           
                        emit( currentFunction , scope, child0 , writer );                        
                        writer.add();
                        writer.loadIndirect( "array subscript: "+n.toString() );                        
                    } 
                    else if ( operatorType == Operator.DEREFERENCE ) 
                    {
                        emit( currentFunction , scope, child0 , writer );
                        writer.discardAccumulator(); // prevent PUSH
                        writer.loadIndirect( "*("+n.child(0).toString() +")" );
                    } 
                    else if ( operatorType == Operator.ASSIGNMENT ) 
                    {
                        final String debug = ExpressionPrinter.printDebug( (TermNode) n );

                        // evaluate RHS BEFORE LHS
                        emit( currentFunction , scope, child1 , writer );
                        emit( currentFunction,scope,child0,writer );
                        
                        if ( ! child0.isLValue() ) {
                            throw new RuntimeException("Not an L-Value: "+child0);
                        }
                        writer.popValueIndirect( "assignment: "+debug );
                    } 
                    else 
                    {
                        // common case: binary operator
                        if ( child1 != null ) 
                        {
                            emit( currentFunction , scope , child1 , writer );
                        }                            
                        emit( currentFunction , scope,child0 , writer ); 

                        switch( operatorType ) 
                        {
                            case NOT_EQUAL:
                            case GREATER_THAN_EQUAL:
                            case LESS_THAN_EQUAL:
                            case EQUALS:
                            case GREATER_THAN:
                            case LESS_THAN:
                                outputComparison(operatorType, null , writer );
                                break;
                                /*
                                 * ARITHMETICS
                                 */
                            case MULTIPLY:
                                writer.multiply();
                                break;								
                            case MINUS:
                                writer.subtract();
                                break;
                            case PLUS:
                                writer.add();
                                break;
                            case BITWISE_OR:
                                writer.bitwiseOr();
                                break;								
                            default:
                                throw new RuntimeException("Internal error,unhandled operator "+op);
                        } // END: switch( op.getOperator )
                    }
                    context.dontGoDeeper();
                } 
                else if ( n instanceof IfNode ) 
                {
                    final IfNode ifNode = (IfNode) n;

                    TermNode condition = ifNode.getCondition();

                    emit( currentFunction , scope, condition , writer );

                    final LabelDefinition falseLabel = labelManager.defineTemporaryLabel( scope );
                    writer.branchOnBoolean( falseLabel.ref() );

                    final LabelDefinition continueLabel = labelManager.defineTemporaryLabel( scope );

                    /* 
                     * "true" block
                     */
                    emit( currentFunction , scope , ifNode.getBody() , writer );   
                    writer.jumpTo( continueLabel.ref() , "jump to continue"); // => jump to continue_block                    

                    /*
                     * "false" block
                     */
                    writer.defineCodeLabel( falseLabel , "condition FALSE block" ); 
                    if ( ifNode.hasElseBlock() ) {
                        emit( currentFunction , scope , ifNode.getElseBlock() , writer );
                    }

                    /*
                     * continue block
                     */
                    writer.defineCodeLabel( continueLabel , " IF continue" );    

                    writer.endOfStatement(n);
                    context.dontGoDeeper();
                }
                else if ( n instanceof FunctionInvocation ) 
                {
                    /*
                     * Return argument is passed in IAssemblyEmitter#METHOD_RESULT_REGISTER
                     * 
                     * Stack layout upon call: 
                     * 
                     * |  return address | <-- [SP]
                     * |   arg #2        |
                     * |   arg #1        |
                     * 
                     * Caller cleans stack after the call returns (excluding return address that is popped by the callee).
                     */
                    final FunctionInvocation fn = (FunctionInvocation) n;

                    // push arguments on stack (right->left)
                    for ( int i = fn.getChildCount()-1 ; i >= 0; i-- )
                    {
                        final ASTNode child = fn.child(i);
                        if ( child instanceof StringLiteralNode && ! ((StringLiteralNode) child).isSingleCharacter() ) 
                        {
                            /* TODO: will fail for stuff like:
                             *  
                             * char *p;
                             * func( ( p = "test") );
                             * ...                            
                             * void func(char *x);
                             */
                            LabelDefinition def = labelManager.defineTemporaryLabel( scope );
                            writer.outputValues( def , valuesToString( Collections.singletonList( child ) ) );
                            writer.load( def , child );
                        } else {
                            emit( currentFunction , scope , child , writer );
                        }
                        writer.pushValue( "push method argument no. "+(i+1));
                        writer.discardAccumulator();
                    }

                    // push return target address                   
                    final LabelDefinition continueLabel = labelManager.defineTemporaryLabel( scope );
                    writer.load( continueLabel , null );
                    writer.pushValue( "push return address" );                  

                    // invoke function
                    final FunctionDefinitionNode def = findFunction( scope , fn.getFunctionName() );
                    final LabelDefinition func = labelManager.getOrDefineLabel( def );

                    writer.jumpTo( func.ref() , "JSR "+def.getSignatureAsString() );

                    // :continueLabel
                    writer.defineCodeLabel( continueLabel );

                    // method result (if any) is now in IAssemblyWriter.METHOD_RESULT_REGISTER
                    if ( ! def.isVoidFunction() ) 
                    {
                        // TODO: Unnecessary if the method result is never used .... but we need data-flow analysis to detect this 
                        writer.popValueToAux( "pop method result" );
                    }
                    // pop arguments from stack
                    if ( fn.getArgumentCount() > 0 ) 
                    {
                        writer.popValues( fn.getArgumentCount() , "pop "+fn.getArgumentCount()+" arguments from call to "+def.getSignatureAsString());
                    }
                    
                    writer.endOfStatement(n);
                    context.dontGoDeeper();
                } 
                else if ( n instanceof ForLoopNode ) 
                {
                    final ForLoopNode forLoopNode = (ForLoopNode) n;
                    final IScope forScope = forLoopNode.getScope();

                    // emit initializer
                    if ( forLoopNode.hasInitializerBlock() ) 
                    {
                        emit(currentFunction, forScope, forLoopNode.getInitializerBlock() , writer);
                    }

                    // check whether we need to emit a condition check or
                    // if the condition is just always TRUE / FALSE

                    TermNode condition = forLoopNode.getCondition();

                    final Long fixedValue = NumberLiteralNode.valueOf( condition );
                    final boolean emitConditionCheck;
                    if ( fixedValue == null ) 
                    {
                        emitConditionCheck = true;
                        while ( ! (condition instanceof OperatorNode) && condition.getChildCount() == 1 )  
                        {
                            condition = (TermNode) condition.child(0);
                        }
                    } 
                    else 
                    {
                        // condition resembles a fixed value
                        if ( fixedValue.longValue() == 0 ) { // FALSE
                            // for { ; false ; } => loop body + increment expression are never executed
                            context.dontGoDeeper();
                            return;
                        }

                        // for { ; true ; } => loop body + increment expression are always executed
                        emitConditionCheck = false;
                    }

                    // :condition_check
                    final LabelDefinition afterLoop = labelManager.defineTemporaryLabel( scope );     
                    final LabelDefinition conditionCheck = labelManager.defineTemporaryLabel( scope );

                    writer.defineCodeLabel( conditionCheck , "start of for-loop condition check" );

                    if ( emitConditionCheck ) 
                    {
                        emit( currentFunction , forScope, forLoopNode.getCondition() , writer );
                        writer.branchOnBoolean( afterLoop.ref() , " exit for() loop");
                    }

                    // for-loop body
                    if ( forLoopNode.hasBody() ) {
                        emit( currentFunction , scope, forLoopNode.getBody() , writer );
                    }

                    // emit increment expression
                    if ( forLoopNode.hasIncrementBlock() ) {
                        emit( currentFunction , scope, forLoopNode.getIncrementBlock() , writer );
                    }
                    writer.jumpTo( conditionCheck.ref() , "goto start of for() loop");                    

                    // code after for() loop
                    writer.defineCodeLabel( afterLoop , " after for() loop" );    

                    writer.endOfStatement(n);
                    
                    context.dontGoDeeper();                	
                }
                else if ( n instanceof WhileLoopNode ) 
                {
                    final WhileLoopNode whileNode = (WhileLoopNode) n;

                    final LabelDefinition loopStart = labelManager.defineTemporaryLabel( scope );
                    final LabelDefinition loopBody = labelManager.defineTemporaryLabel( scope);
                    final LabelDefinition afterLoop = labelManager.defineTemporaryLabel( scope );

                    writer.defineCodeLabel( loopStart , "start of while() loop" );
                    
                    emit( currentFunction , scope, whileNode.getCondition() , writer );
                    writer.branchOnBoolean( afterLoop.ref() );

                    writer.endOfStatement(n);
                    
                    // true case => execute method body

                    writer.defineCodeLabel( loopBody, " while() loop body " );                        
                    if ( whileNode.hasBody() ) 
                    {
                        emit( currentFunction , scope, whileNode.getBody() , writer );
                    }
                    writer.jumpTo( loopStart.ref() , "goto start of while()");                    

                    /*
                     * continue block
                     */
                    writer.defineCodeLabel( afterLoop , " after while() loop" );    

                    writer.endOfStatement(n);
                    
                    context.dontGoDeeper();
                } 
                else if ( n instanceof DoLoopNode) {

                    final DoLoopNode whileNode = (DoLoopNode) n;

                    final LabelDefinition loopBody = labelManager.defineTemporaryLabel( scope);
                    final LabelDefinition afterLoop = labelManager.defineTemporaryLabel( scope );

                    // start of method body
                    writer.defineCodeLabel( loopBody, "start of do() loop" );

                    // true case => execute method body
                    emit( currentFunction , scope, whileNode.getBody() , writer );

                    // output comparison
                    emit( currentFunction , scope, whileNode.getCondition() , writer );
                    writer.branchOnBoolean( afterLoop.ref() );

                    writer.jumpTo( loopBody.ref() , "goto start of do()");                    

                    /*
                     * continue block
                     */
                    writer.defineCodeLabel( afterLoop , " after do() loop" );    

                    writer.endOfStatement(n);
                    
                    context.dontGoDeeper();
                } 
                else if ( n instanceof InlineAssemblyNode ) 
                {
                    writer.outputInlineAssembly( (InlineAssemblyNode) n);
                    context.dontGoDeeper();
                } 
                else if ( n instanceof ReturnNode ) 
                {
                    ReturnNode rn = (ReturnNode) n;
                    // sanity check , should be caught be AST validation already
                    if ( ! rn.matchesSignature( currentFunction ) ) 
                    {
                        throw new RuntimeException("Internal error, return statement '"+rn+";' not " +
                                "compatible with current method signature: "+currentFunction.getSignatureAsString());
                    }

                    if ( ! currentFunction.isVoidFunction() ) 
                    {
                        if ( ! rn.returnsSomething() ) {
                            throw new RuntimeException("Internal error, RETURN node in non-void function returns nothing ?");
                        }
                        // evaluate RETURN <arg>
                        emit( currentFunction , scope , rn.getReturnValue() , writer );
                    } 
                    returnFromFunction( currentFunction , writer );
                    writer.endOfStatement(n);
                    context.dontGoDeeper();
                } 
                else if ( n instanceof VariableDefinition) 
                {
                    emitVariableDefinition(currentFunction, writer, (VariableDefinition) n); 
                    writer.endOfStatement(n);
                    context.dontGoDeeper();
                }
                else if ( n instanceof NumberLiteralNode) 
                {
                    writer.load( n  );
                } 
            }

            private void outputComparison(final Operator operator, LabelReference falseLabel, final IAssemblyEmitter writer)
            {
                if ( operator.equals( Operator.EQUALS ) ) 
                {
                    writer.compareEqualsWithResult();
                }
                else if ( operator.equals( Operator.NOT_EQUAL ) ) 
                {
                    writer.compareNotEqualWithResult();
                } 
                else if ( operator.equals( Operator.GREATER_THAN ) ) 
                {
                    // TODO: Need to handle signed/unsigned here
                    writer.compareGreaterThanWithResult();
                }
                else if ( operator.equals( Operator.LESS_THAN ) ) 
                {
                    writer.compareLessThanWithResult();
                } 
                else if ( operator.equals( Operator.LESS_THAN_EQUAL ) ) 
                {
                    writer.compareLessThanEqualWithResult(falseLabel );
                } 
                else if ( operator.equals( Operator.GREATER_THAN_EQUAL ) ) 
                {
                    writer.compareGreaterThanEqualWithResult( falseLabel );
                } else {
                	throw new RuntimeException("Internal error,unhandled conditional operator "+operator);
                }
            }
        };          

        ASTUtils.visitInOrder( expression , v );   
    }
    
    private static boolean isDereferenceOperator(ASTNode n) {
        return n instanceof OperatorNode && ((OperatorNode) n).getOperator()==Operator.DEREFERENCE;
    }
        
    private static boolean isAssignmentOperator(ASTNode n) {
        return n instanceof OperatorNode && ((OperatorNode) n).getOperator()==Operator.ASSIGNMENT;
    }

    protected FunctionDefinitionNode findFunction(IScope scope,Identifier identifier) {

        IScope current = scope;
        do {
            FunctionDefinitionNode result = current.getFunction( identifier );
            if ( result != null ) {
                return result;
            }
            current = current.getParent();
        } while ( current != null );

        StringBuffer searched = new StringBuffer();
        current = scope;
        do 
        {
            searched.append( current.getIdentifier()+" , " );
            current = current.getParent();
        } while ( current != null );
        throw new NoSuchElementException("Failed to find function "+identifier+"() in scopes  { "+searched+"}");
    }

    private void outputGlobalVarDefinition(VariableDefinition def,IAssemblyEmitter writer) 
    {
        emitVariableDefinition((FunctionDefinitionNode) null, writer, def);
    }

    private void emitVariableDefinition(FunctionDefinitionNode function,IAssemblyEmitter writer, VariableDefinition def) 
    {
        final DataType type = def.getDataType();
        
        final LabelDefinition label = labelManager.defineLabel( def );
        final boolean hasValue = def.hasChildren();

        final String debug = ( def.isGlobalVariable() ? "global" : "" )+" var init: "+def;
        if ( type.isPtr() || ! def.isArrayDefinition() ) 
        {
            if ( type.isPtr() && type.getPtrCount() > 1 ) {
                throw new RuntimeException("ptr type "+type+" with more than one level of indirection currently not supported");
            }
            
            // direct value assignment
            writer.outputValues( label , Collections.singletonList("0") , debug );
            
            if ( hasValue ) 
            {
                emit( function , def.getScope() , def.child(0) , writer );
                SlotMapping target = writer.getHandle( def ); 
                writer.storeValue( target , "assignment: "+def.getDataType()+" "+def.getName()+" = ...");
            } 
            writer.endOfStatement(def);
            return;
        }
        
        // array assignment
        if ( ! hasValue ) {
        
            // just reserve elements
            writer.reserveUninitializedMemory( label , def.getElementCount() , debug );
        } else {
            writer.outputValues( label , valuesToString( def.getChildren() ) );
        }
        writer.endOfStatement(def);
    }	

    private List<String> valuesToString(List<ASTNode> children) 
    {
        if (children == null || children.isEmpty() ) {
            throw new IllegalArgumentException("children must not be NULL/empty");
        }
        final List<String> values=new ArrayList<>();
        for ( ASTNode child : children ) 
        {
            TermNode reduced = ((TermNode) child).reduce();
            if ( reduced instanceof StringLiteralNode) 
            {
                final StringLiteralNode str = (StringLiteralNode) reduced;
                final String escapedValue = StringLiteralNode.escapeQuotes( str.getValue() );
                if ( str.isSingleCharacter() ) 
                {
                    if ( str.getValue().length() == 0 ) {
                        values.add( "0" ); // just write a zero byte
                    } else {
                        values.add( "'"+escapedValue+"'" );
                    }
                } 
                else 
                {
                    values.add( "\""+escapedValue+"\"" );
                    values.add( "0" );
                }
            } else if ( reduced instanceof NumberLiteralNode ) {
                values.add( Long.toString( ((NumberLiteralNode) reduced).getValue() ) );
            } else {
                values.add( expressionPrinter.print( reduced ) );
            }
        } 
        return values;
    }   
}