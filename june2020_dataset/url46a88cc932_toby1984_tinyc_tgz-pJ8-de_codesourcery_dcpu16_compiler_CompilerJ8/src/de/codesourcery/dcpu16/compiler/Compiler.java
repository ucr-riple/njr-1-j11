package de.codesourcery.dcpu16.compiler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import de.codesourcery.dcpu16.ast.AST;
import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.DoLoopNode;
import de.codesourcery.dcpu16.ast.FunctionDefinitionNode;
import de.codesourcery.dcpu16.ast.IfNode;
import de.codesourcery.dcpu16.ast.NumberLiteralNode;
import de.codesourcery.dcpu16.ast.OperatorNode;
import de.codesourcery.dcpu16.ast.ReturnNode;
import de.codesourcery.dcpu16.ast.StringLiteralNode;
import de.codesourcery.dcpu16.ast.TermNode;
import de.codesourcery.dcpu16.ast.VariableDefinition;
import de.codesourcery.dcpu16.ast.VariableReferenceNode;
import de.codesourcery.dcpu16.ast.VariableSymbol;
import de.codesourcery.dcpu16.ast.WhileLoopNode;
import de.codesourcery.dcpu16.codegeneration.CodeGenerator;
import de.codesourcery.dcpu16.codegeneration.DefaultAssemblyWriter;
import de.codesourcery.dcpu16.codegeneration.IAssemblyWriter;
import de.codesourcery.dcpu16.codegeneration.Register;
import de.codesourcery.dcpu16.optimizer.cfg.ControlFlowAnalyzer;
import de.codesourcery.dcpu16.optimizer.cfg.ControlFlowGraph;
import de.codesourcery.dcpu16.optimizer.cfg.ControlFlowGraph.BasicBlock;
import de.codesourcery.dcpu16.optimizer.dataflow.DataFlowAnalyzer;
import de.codesourcery.dcpu16.optimizer.dataflow.InterferenceGraph;
import de.codesourcery.dcpu16.optimizer.dataflow.InterferenceGraph.Node;
import de.codesourcery.dcpu16.parser.ASTUtils;
import de.codesourcery.dcpu16.parser.ASTUtils.IASTNodeMatcher;
import de.codesourcery.dcpu16.parser.ASTUtils.IAdvancedVisitor;
import de.codesourcery.dcpu16.parser.ASTUtils.IIterationContext;
import de.codesourcery.dcpu16.parser.ASTUtils.IVisitor;
import de.codesourcery.dcpu16.parser.Parser;
import de.codesourcery.dcpu16.util.ExpressionPrinter;
import de.codesourcery.dcpu16.util.IInputStreamProvider;

public class Compiler
{
    private boolean optimize = true;
    private boolean debugCodeGeneration = false; 
    private boolean debugRegisterAllocator = false;

    public static final File DOT_OUTPUT = new File("/home/tgierke/tmp/graph.dot");

    public static void main(String[] args) throws IOException 
    {
        final String source = "int maxRows=12;\n" + 
        		"int maxColumns=32;\n" + 
        		"void main() {\n" + 
        		"  print( 5,5, \"test\" );\n" + 
        		"}\n" + 
        		"\n" + 
        		"void print(int currentRow,int currentCol , char *text)\n" + 
        		"{\n" + 
        		"   char *ptr = 0x8000 + currentRow*maxColumns + currentCol;\n" + 
        		"   while ( *text ) {\n" + 
        		"     *ptr=*text | 0x1200;\n" + 
        		"      ptr = ptr + 1;\n" + 
        		"      text = text + 1;\n" + 
        		"   }\n" + 
        		"} ";
        final IInputStreamProvider in = new IInputStreamProvider() {

            @Override
            public InputStream createInputStream() throws IOException
            {
                return new ByteArrayInputStream(source.getBytes());
            }
        };     

        new Compiler().compile(in);
    }

    public void compile(IInputStreamProvider in) throws IOException {

        // parse
        final AST ast = parse(in);

        System.out.println("AST: "+ASTUtils.toString( ast ) );
        System.out.println("========================");        

        // validate ast
        validate( ast );

        // rewrite (simplify etc.) expression trees 
        if ( optimize ) {
            optimizeAST( ast );
        }

        addImplicitReturnToVoidMethods(ast);
        
//        printCFG(ast);

        System.out.println("REWRITTEN: "+ASTUtils.toString( ast ) );
        System.out.println("========================");

        // generate code
        IAssemblyWriter writer = new DefaultAssemblyWriter(System.err);
        try {
            generateCode( ast , writer );
        } finally {
            writer.flush();
        }
    }

    private void printCFG(AST ast) 
    {
        for ( ASTNode node : ast.getChildren() ) 
        {
            if ( node instanceof FunctionDefinitionNode ) 
            {
                final FunctionDefinitionNode function = (FunctionDefinitionNode) node;
                final ControlFlowGraph graph = new ControlFlowAnalyzer().createGraph( function);

                System.out.println("====== "+function.getSignatureAsString()+" ========");

                try ( FileWriter writer = new FileWriter( DOT_OUTPUT ) )
                {
                    writer.write( graph.toDOT() );
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println( graph.toDOT() );
                
                final DataFlowAnalyzer analyzer = new DataFlowAnalyzer() {

                    @Override
                    protected Node pickNodeToSpill(InterferenceGraph graph)
                    {
                        return graph.getAllNodes().iterator().next();
                    }
                };
                
                analyzer.assignLiveVariables( graph );
                
                final List<BasicBlock> sorted = graph.getAllBlocks();
                Collections.sort(sorted,new Comparator<BasicBlock>() {

                    @Override
                    public int compare(BasicBlock o1, BasicBlock o2)
                    {
                        if ( o1.equals( graph.getFunctionEntry() ) ) {
                            return -1;
                        }
                        if ( o1.equals( graph.getFunctionExit() ) ) {
                            return 1;
                        }    
                        if ( o2.equals( graph.getFunctionEntry() ) ) {
                            return 1;
                        }
                        if ( o2.equals( graph.getFunctionExit() ) ) {
                            return -1;
                        }                         
                        return o1.getIdentifier().compareTo(o2.getIdentifier());
                    }
                } );

                for ( BasicBlock b : sorted ) 
                {
                    System.out.println("============ BLOCK "+b.getIdentifier()+" ===========");
                    System.out.println("ALIVE: "+b.getMetaData().get( DataFlowAnalyzer.ALIVE_VARS_METADATA_KEY ) );
                    System.out.println("====================================================");
                    System.out.println( b );
                    System.out.println("====================================================");
                }
                
                // color graph
                InterferenceGraph interferenceGraph = analyzer.createInterferenceGraph( graph );
                final List<Object> available = Arrays.<Object>asList( Register.A , Register.B , Register.C );
                
                final List<Node> spilled = analyzer.colorGraph( interferenceGraph , available );
                
                System.out.println("============= COLORED ============\n"+interferenceGraph);

                System.out.println("============= SPILLED ============\n");
                for (Node n : spilled ) {
                    System.out.println(n);
                }
                System.out.println("==================================\n");
            }
        }
    }
    
    private void addImplicitReturnToVoidMethods(AST ast) 
    {
    	final IAdvancedVisitor visitor = new IAdvancedVisitor() {
			
			@Override
			public void visit(ASTNode n, int depth, IIterationContext context) {
				if ( n instanceof FunctionDefinitionNode) {
					addImplicitReturnToVoidMethod((FunctionDefinitionNode) n);
					context.dontGoDeeper();
				}
			}
		};
		ASTUtils.visitInOrder( ast , visitor );
    }

    private void addImplicitReturnToVoidMethod(FunctionDefinitionNode n) 
    {
        if ( ! n.isVoidFunction() ) {
            return; 
        }

        ASTNode lastLeaf = null;
        for ( ASTNode child : n.getBody().getChildren() ) 
        {
            lastLeaf = ASTUtils.getLastLeaf( child );
        }    	

        ASTNode tmp = lastLeaf;
        while ( tmp != n && !(tmp instanceof ReturnNode) ) {
            tmp = tmp.getParent();
        }
        if ( tmp instanceof ReturnNode ) {
            return;
        }
        System.out.println("*** adding implicit return to "+n.getSignatureAsString());
        n.getBody().addChild( new ReturnNode( null ) );
    }

    private AST parse(IInputStreamProvider in) throws IOException 
    {
        final Parser p = new Parser( in );
        return p.parse();
    }

    private void validate(AST ast) 
    {

        final IAdvancedVisitor visitor = new IAdvancedVisitor() {

            @Override
            public void visit(ASTNode n, int depth, IIterationContext context)
            {
                if ( n instanceof OperatorNode ) 
                {
                    OperatorNode node = (OperatorNode) n;
                    if ( node.getOperator() == Operator.ASSIGNMENT ) 
                    {
                        final TermNode leftSide = (TermNode) node.child(0);
                        final TermNode rightSide = (TermNode) node.child(1);

                        final DataType leftType = leftSide.getDataType();
                        final DataType rightType = rightSide.getDataType();
                        if ( ! leftType.isAssignableFrom( rightType ) ) 
                        {
                            throw new RuntimeException("Cannot assign '"+node.child(1)+"' to '"+
                                    leftSide+"' , incompatible types "+leftType+" <-> "+rightType);
                        }
                    }
                }
            }
        };
        ASTUtils.visitDepthFirst( ast , visitor );

        // TODO: Check that assignment expressions are "lvalue = rvalue"
        // TODO: check that VOID methods do not return anything
        // TODO: check that non-VOID methods return anything of the required type
        // TODO: check that conditions in IF/WHILE/FOR yields a boolean result
    }

    private void generateCode(AST ast, IAssemblyWriter writer) throws IOException 
    {
        final CodeGenerator generator = new CodeGenerator( writer , ast );
        generator.generateCode();
    }

    private void optimizeAST(AST ast) 
    {
        while( true) 
        {
            // do expression folding first
            foldExpressions(ast);

            // inline variables that are effectively constant
            while ( inlineConstants(ast) ) 
            {
                foldExpressions(ast);
            }

            /* TODO: Simplify expressions: 
             * 
             * term-term => 0
             * 1*term => term
             * 0*term => 0
             * term1*term2+term1*term3 = term1*(term2+term3)
             */
            // simplifyExpressions(ast)

            // dead code removal
            if ( ! removeDeadCode(ast) ) {
                break; // stop optimizing if we failed to remove any dead code
            }
            // optimize again , dead code removal might've provided more opportunities for optimization
        }

        skewTree(ast);        
    }

    private boolean removeDeadCode(AST ast) 
    {
        /*
         * TODO: We require a control flow graph to really remove all dead code
         * 
         * Think:
         * 
         * int main() 
         * {
         *   boolean a = true;
         *   if ( a == true ) {
         *      return 1;
         *   }
         *   return 0;\n"+
         * }
         * 
         * Elimination of unreachable block currently yields:
         * 
         * int main() 
         * {
         *   return 1;
         *   return 0;
         * }
         * 
         * Where the second RETURN is actually never reached...
         */

        // TODO: if a method has an empty body , this method is NOT the main() method and all invocations
        // TODO: are side-effect free, remove the method along with all invocations

        // remove unreachable if bodys / else blocks
        boolean astModified = removeUnreachableIfBlocks(ast);

        astModified |= removeLoopingFromDoBlocks(ast); 

        // Inline/remove while() loops where condition is always true/false
        astModified |= removeWhileBlocks(ast);

        astModified |= removeUnreachableNodes(ast);
        return astModified;
    }

    private boolean removeUnreachableNodes(AST ast) 
    {
        final List<ASTNode> functions = ASTUtils.collect( ast ,  new IASTNodeMatcher() {

            @Override
            public boolean matches(ASTNode node)
            {
                return node instanceof FunctionDefinitionNode;
            }
        });

        final AtomicBoolean astModified = new AtomicBoolean(false);
        ControlFlowAnalyzer analyzer = new ControlFlowAnalyzer();
        for ( ASTNode n : functions ) 
        {
            final ControlFlowGraph cfg = analyzer.createGraph( (FunctionDefinitionNode) n);
            final List<ASTNode> children =  new ArrayList<ASTNode>( n.getChildren() );

            for ( ASTNode child : children) 
            {
                final IAdvancedVisitor visitor = new IAdvancedVisitor() {

                    @Override
                    public void visit(ASTNode n, int depth, IIterationContext context)
                    {
                        if ( ! cfg.isReachable( n ) ) 
                        {
                            astModified.set(true);
                            final IAdvancedVisitor removalVisitor = new IAdvancedVisitor() {

                                @Override
                                public void visit(ASTNode n, int depth, IIterationContext context)
                                {
                                    System.out.println("Removing unreachable node: "+n);
                                    n.removeFromParent();
                                }
                            };
                            ASTUtils.visitDepthFirst( n , removalVisitor , true );
                            context.dontGoDeeper();
                        }
                    }
                };
                ASTUtils.visitInOrder( child , visitor , true );
            }
        }
        return astModified.get();
    }       

    private boolean removeWhileBlocks(AST ast) 
    {
        return rewriteAST( ast , new ASTRewriter() {

            @Override
            protected boolean rewriteNode(ASTNode n, IIterationContext context)
            {
                if ( n instanceof WhileLoopNode ) 
                {
                    final WhileLoopNode whileNode = (WhileLoopNode) n;
                    if ( whileNode.getCondition() instanceof NumberLiteralNode ) 
                    {
                        final boolean isAlwaysTrue = ((NumberLiteralNode) whileNode.getCondition()).getValue() != 0;
                        System.out.println("Removing dead code ("+ ( isAlwaysTrue ? "always true" : "always false")+") from: "+ASTUtils.toString( whileNode ) );

                        if ( isAlwaysTrue ) 
                        { 
                            // replace while() with body or remove completely if body is empty
                            if ( whileNode.hasBody() && ! whileNode.getBody().isEmpty() ) {
                                whileNode.replaceWith( whileNode.getBody() );
                            } else {
                                whileNode.removeFromParent();
                            }
                        } else {
                            whileNode.removeFromParent();
                            // update scope: remove any variables that were defined in this block                            
                            codeRemoved( whileNode );
                        }
                        return true;
                    }
                }
                return false;
            }

            @Override
            protected void rewriteAST(ASTNode ast)
            {
                ASTUtils.visitDepthFirst( ast , this );                   
            }
        });           
    }

    private boolean removeLoopingFromDoBlocks(AST ast) 
    {
        return rewriteAST( ast , new ASTRewriter() {

            @Override
            protected boolean rewriteNode(ASTNode n, IIterationContext context)
            {
                if ( n instanceof DoLoopNode ) 
                {
                    final DoLoopNode whileNode = (DoLoopNode) n;
                    if ( whileNode.getCondition() instanceof NumberLiteralNode ) 
                    {
                        final boolean isTrue = ((NumberLiteralNode) whileNode.getCondition()).getValue() != 0;
                        if ( ! isTrue ) 
                        {
                            // do {} while ( false ) => remove condition evaluation
                            System.out.println("Removing loop from do {} (always false): "+ASTUtils.toString( whileNode ) );
                            whileNode.replaceWith( whileNode.getBody() );
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            protected void rewriteAST(ASTNode ast)
            {
                ASTUtils.visitDepthFirst( ast , this );                   
            }
        });           
    }    

    private boolean removeUnreachableIfBlocks(AST ast) 
    {
        return rewriteAST( ast , new ASTRewriter() {

            @Override
            protected boolean rewriteNode(ASTNode n, IIterationContext context)
            {
                if ( n instanceof IfNode ) 
                {
                    final IfNode ifNode = (IfNode) n;
                    if ( ifNode.getCondition() instanceof NumberLiteralNode ) 
                    {
                        final boolean isAlwaysTrue = ((NumberLiteralNode) ifNode.getCondition()).getValue() != 0;
                        System.out.println("Removing dead code ("+ ( isAlwaysTrue ? "always true" : "always false")+") from: "+ASTUtils.toString( ifNode ) );

                        final ASTNode removedBlock;
                        if ( isAlwaysTrue ) 
                        { 
                            // if ( ALWAYS TRUE ) => replace IF with BODY block
                            if ( ifNode.hasElseBlock() ) {
                                // else block was removed
                                removedBlock = ifNode.getElseBlock();
                            } else {
                                removedBlock = null; // if has no else block => nothing removed
                            }
                            if ( ! ifNode.getBody().hasChildren() ) { 
                                ifNode.removeFromParent();
                            } else {
                                ifNode.replaceWith( ifNode.getBody() );
                            }
                        } 
                        else 
                        {
                            // if ( ALWAYS FALSE ) => replace IF with ELSE block (if any)
                            if ( ifNode.hasElseBlock() ) 
                            {
                                // replace if () with else block
                                removedBlock = ifNode.getBody();
                                if ( ifNode.getElseBlock().hasChildren() ) {
                                    ifNode.replaceWith( ifNode.getElseBlock() );
                                } else {
                                    ifNode.removeFromParent(); // empty block, remove IF completely
                                }
                            } else {
                                // remove if completely , body is never executed
                                ifNode.getParent().removeChild( ifNode );
                                removedBlock = ifNode;
                            }
                        }

                        // update scope: remove any variables that were defined in this block
                        if ( removedBlock != null ) {
                            codeRemoved( removedBlock );
                        }
                        return true;
                    }
                }
                return false;
            }

            @Override
            protected void rewriteAST(ASTNode ast)
            {
                ASTUtils.visitDepthFirst( ast , this );                   
            }
        });        
    }

    private boolean inlineConstants(final AST ast)
    {
        return rewriteAST( ast , new ASTRewriter() {

            @Override
            protected boolean rewriteNode(ASTNode n, IIterationContext context)
            {
                // for each FunctionDefinition 
                //   for each variable definition (NOT parameter definition)
                //      - check whether the variable is assigned to no more than once
                //        if yes AND
                //           assigned value is a literal (number,string => replace with ConstantDefinition
                //           TODO: assigned value is another variable => replace variable with the assigned one in all expressions
                if ( n instanceof FunctionDefinitionNode ) 
                {
                    final FunctionDefinitionNode function = (FunctionDefinitionNode) n;
                    final AtomicBoolean variableInlined = new AtomicBoolean(false);

                    final IAdvancedVisitor functionVisitor = new IAdvancedVisitor() 
                    {
                        @Override
                        public void visit(ASTNode n, int depth, IIterationContext context)
                        {
                            if ( n instanceof VariableDefinition && ! ( (VariableDefinition) n).isArrayDefinition() ) // TODO: inline arrays as well !!!!
                            {
                                // we found a local variable definition
                                final VariableDefinition var = (VariableDefinition) n;

                                // count how many times this variable is (re) assigned in this function
                                final AtomicInteger assignmentCount = new AtomicInteger(0);
                                final IAdvancedVisitor variableVisitor = new IAdvancedVisitor() 
                                {
                                    @Override
                                    public void visit(ASTNode n, int depth, IIterationContext context)
                                    {
                                        if ( n instanceof OperatorNode ) 
                                        { 
                                            if ( ( (OperatorNode) n).isAssignmentTo( var ) ) 
                                            {
                                                assignmentCount.incrementAndGet();
                                            }
                                        }
                                    }
                                };

                                ASTUtils.visitInOrder( function , variableVisitor );

                                if ( assignmentCount.get() == 0 ) 
                                {
                                    /* TODO: When adding inlining for array definitions: 
                                     * 
                                     * VariableDefinition has one child PER element !! 
                                     */

                                    final ASTNode value = var.child(0); 
                                    // variable is assigned only once, check if the assigned value is a constant expression
                                    if ( value instanceof NumberLiteralNode || 
                                            value instanceof StringLiteralNode ||
                                            value instanceof VariableReferenceNode )
                                    {
                                        // inline variable
                                        if ( inlineVariable( function , var , (TermNode) value ) ) {
                                            System.out.println("INLINED: "+var+" = "+value);  
                                        } else {
                                            System.out.println("REMOVED UNUSED VARIABLE: "+var+" = "+value);                                            
                                        }

                                        // remove unused variable definition
                                        var.removeFromParent();

                                        // update scope: remove variable definition
                                        codeRemoved( var );

                                        variableInlined.set(true);
                                        context.stop();
                                        return;
                                    } 
                                    System.err.println("INLINER: Can't inline "+var+" , value is "+value);
                                }
                                context.dontGoDeeper();
                            }
                        }
                    };

                    ASTUtils.visitInOrder( function , functionVisitor );
                    if ( variableInlined.get() ) {
                        return true;
                    }
                    context.dontGoDeeper();
                }
                return false;
            }

            @Override
            protected void rewriteAST(ASTNode ast)
            {
                ASTUtils.visitDepthFirst( ast , this );                   
            }
        });        
    }

    private void codeRemoved(ASTNode n) 
    {
        final IVisitor visitor = new IVisitor() {

            @Override
            public boolean visit(ASTNode node, int depth)
            {
                if ( node instanceof VariableDefinition) 
                {
                    final VariableDefinition var = (VariableDefinition) node;
                    final IScope scope = node.findScope();
                    System.out.println("Deleted variable "+var.getName()+" from scope "+scope);
                    scope.remove( var.getName() );
                }                
                return true;
            }
        };
        ASTUtils.visitInOrder( n , visitor );
    }

    private boolean inlineVariable(FunctionDefinitionNode function, final VariableDefinition var,final TermNode newValue) 
    {
        final VariableSymbol definitionSite = var.getDefinitionSite();
        final Identifier varName = var.getName();

        return rewriteAST( function , new ASTRewriter() {

            @Override
            protected boolean rewriteNode(ASTNode n, IIterationContext context)
            {
                if ( n instanceof VariableReferenceNode) 
                {
                    final VariableReferenceNode ref = (VariableReferenceNode) n;
                    if ( ref.getName().equals( varName ) )
                    {
                        VariableSymbol actualSite = ref.getDefinitionSite();
                        if ( definitionSite == actualSite ) 
                        {
                            System.out.println("REPLACED: "+ExpressionPrinter.printDebug( (TermNode) n )+" => "+ExpressionPrinter.printDebug( newValue ) );
                            n.replaceWith( newValue.createCopy(true ) );
                            return true;
                        } 
                    }
                } 
                return false;
            }

            @Override
            protected void rewriteAST(ASTNode ast)
            {
                ASTUtils.visitDepthFirst( ast , this );                   
            }
        });        
    }

    private void foldExpressions(AST ast)
    {
        rewriteAST( ast , new ASTRewriter() {

            @Override
            protected boolean rewriteNode(ASTNode n, IIterationContext context)
            {
                if ( n instanceof TermNode ) 
                {
                    final TermNode reduced = ((TermNode) n).reduce();
                    if ( reduced != null && reduced != n) 
                    {
                        System.out.println("FOLDED: "+ExpressionPrinter.printDebug((TermNode) n )+" => "+ExpressionPrinter.printDebug(reduced));
                        n.replaceWith( reduced );
                        return true;
                    }
                }
                return false;
            }

            @Override
            protected void rewriteAST(ASTNode ast)
            {
                ASTUtils.visitDepthFirst( ast , this );                   
            }
        });
    }

    private void skewTree(AST ast)
    {
        rewriteAST( ast , new ASTRewriter() {

            @Override
            protected boolean rewriteNode(ASTNode n, IIterationContext context)
            {

                /*
                 * Rewrite expressions with commutative operators (like +,|,*)
                 * to reduce register pressure in allocator.
                 * 
                 *   +
                 *  / \
                 * 3   +
                 *    / \
                 *   1   2
                 *   
                 * into
                 * 
                 *      +
                 *     / \
                 *     +  3
                 *    / \
                 *   1   2                     
                 */
                if ( isCommutativeOperator(n) )
                {
                    final OperatorNode op=(OperatorNode) n;
                    if ( isValue( op.rightChild() ) && ! isValue( op.leftChild() ) ) {
                        op.swapChildren( op.leftChild() , op.rightChild() );
                        return true;
                    }
                }
                return false;           
            }

            @Override
            protected void rewriteAST(ASTNode ast)
            {
                ASTUtils.visitInOrder( ast , this );                
            }
        });
    }

    private static boolean isValue(ASTNode node) 
    {
        return node instanceof NumberLiteralNode || 
                node instanceof StringLiteralNode ||
                node instanceof VariableReferenceNode;
    }

    private static boolean isCommutativeOperator(ASTNode node) {
        return node instanceof OperatorNode && ((OperatorNode) node).getOperator().isCommutative();
    }

    protected static abstract class ASTRewriter implements IAdvancedVisitor {

        private boolean astModified = false;

        public final boolean rewrite(ASTNode ast) {
            astModified = false;
            rewriteAST(ast);
            return astModified;
        }

        @Override
        public final void visit(ASTNode n, int depth, IIterationContext context) {
            if ( rewriteNode( n , context ) ) {
                context.stop();
                astModified=true;
            }
        }

        protected abstract boolean rewriteNode(ASTNode node,IIterationContext context);

        protected abstract void rewriteAST(final ASTNode ast);
    }

    private static boolean rewriteAST(ASTNode ast, ASTRewriter rewriter) 
    {
        boolean rewritten = false;
        while( rewriter.rewrite( ast ) ) {
            rewritten = true;
        }
        return rewritten;
    }    
}