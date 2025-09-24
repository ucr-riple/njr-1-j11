package de.codesourcery.dcpu16.parser;

import java.util.ArrayList;
import java.util.List;

import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.IScopeDefinition;
import de.codesourcery.dcpu16.ast.NumberLiteralNode;
import de.codesourcery.dcpu16.ast.StringLiteralNode;
import de.codesourcery.dcpu16.compiler.IScope;

public class ASTUtils
{
    public interface IIterationContext 
    {
        public void stop();
        public void dontGoDeeper();
        public void continueTraversal();        
    }

    public interface IASTNodeMatcher {
        public boolean matches(ASTNode node);
    }

    public static List<ASTNode> collect(ASTNode subtree,IASTNodeMatcher matcher) 
    {
        final List<ASTNode> result = new ArrayList<>();
        collect( subtree , matcher ,result );
        return result;
    }
    
    private static void collect(ASTNode node,IASTNodeMatcher matcher,List<ASTNode> result) 
    {
        if ( matcher.matches( node ) ) {
            result.add( node );
        }
        for ( ASTNode child : node.getChildren() ) {
            collect(child,matcher,result);
        }
    }
    
    public interface IAdvancedVisitor 
    {
        public void visit(ASTNode n,int depth,IIterationContext context);
    }

    public static abstract class IVisitor implements IAdvancedVisitor
    {
        public abstract boolean visit(ASTNode n,int depth);

        public void visit(ASTNode n,int depth,IIterationContext context) 
        {
            if ( ! visit(n,depth) ) {
                context.stop();
            } else {
                context.continueTraversal();
            }
        }
    }
    
    public static void visitDepthFirst(ASTNode node,IAdvancedVisitor visitor) 
    {
        visitDepthFirst(node, visitor,false);
    }

    public static void visitDepthFirst(ASTNode node,IAdvancedVisitor visitor,boolean copyChildren) 
    {
        if ( node == null ) {
            throw new IllegalArgumentException("node not be NULL.");
        }
        if ( visitor == null ) {
            throw new IllegalArgumentException("visitor must not be NULL.");
        }
        visitDepthFirst(node,visitor, new IterationContext() , 0,copyChildren);
    }

    public static void visitDepthFirst(ASTNode node,IAdvancedVisitor visitor,IterationContext context,int depth,boolean copyChildren) {

        if ( context.state == State.STOP ) {
            return;
        }
        
        if ( node == null ) {
            throw new IllegalStateException("NULL node ?");
        }
        final List<ASTNode> children = copyChildren ? new ArrayList<>( node.getChildren() ) : node.getChildren();
        for ( ASTNode child : children )
        {
            visitDepthFirst( child , visitor , context, depth+1 , copyChildren );
            if ( context.state == State.STOP ) {
                return;
            }
        }
        
        context.state = State.CONTINUE;
        visitor.visit( node , depth , context );
    }
    
    protected static enum State {
        STOP,
        CONTINUE,
        DONT_GO_DEEPER;
    }
    
    protected static final class IterationContext implements IIterationContext {

        public State state=State.CONTINUE;
        
        @Override
        public void stop()
        {
            state=State.STOP;            
        }

        @Override
        public void dontGoDeeper()
        {
            state=State.DONT_GO_DEEPER;            
        }
        
        @Override
        public void continueTraversal()
        {
            state=State.CONTINUE;            
        }        
    }

    public static void visitInOrder(ASTNode node,IAdvancedVisitor visitor) 
    {
        visitInOrder(node,visitor,false);
    }
    
    public static void visitInOrder(ASTNode node,IAdvancedVisitor visitor,boolean copyChildren) 
    {
        visitInOrder(node,new IterationContext(),visitor,0,copyChildren);
    }

    public static void visitInOrder(ASTNode node,IterationContext context,IAdvancedVisitor visitor,int depth,boolean copyChildren) {

        visitor.visit( node , depth , context );
        
        if ( context.state == State.DONT_GO_DEEPER ) {
        	context.state = State.CONTINUE;
        	return;
        }
        
        if ( context.state == State.STOP ) 
        {
            return;
        }

        final List<ASTNode> children = copyChildren ? new ArrayList<>( node.getChildren() ) : node.getChildren();
        for ( ASTNode child : children ) 
        {
            visitInOrder( child , context, visitor , depth+1 , copyChildren );
            if ( context.state == State.STOP ) 
            {
                return;
            }            
        }
        context.state = State.CONTINUE;
    }    

    public static String toString(ASTNode tree) {

        final StringBuilder b = new StringBuilder();

        final IVisitor v = new IVisitor() {

            @Override
            public boolean visit(ASTNode n, int depth)
            {
            	String scope = "";
            	if ( n instanceof IScopeDefinition ) {
            		scope = "( scope: "+scopeToString( ((IScopeDefinition) n).getScope() )+")";
            	}
            	
                String indent = "";
                for ( int i = 0 ; i < depth ; i++ ) {
                    indent += "  ";
                }
                b.append( indent+n.toString()+scope+"\n" );
                return true;
            }
        };
        visitInOrder( tree , v );
        return b.toString();
    }
    
    private static String scopeToString(IScope scope) 
    {
        String result = scope.getIdentifier().getStringValue();
        scope = scope.getParent();
        while ( scope != null ) {
            result = result+" => "+scope.getIdentifier().getStringValue();
            scope = scope.getParent();
        }
        return result;
    }
    
    public static boolean isLiteralValue(ASTNode node) 
    {
        return node instanceof NumberLiteralNode || node instanceof StringLiteralNode;
    }

    public static ASTNode getLastLeaf(ASTNode subtree) 
    {
    	final ASTNode[] lastLeaf={null};
		final IVisitor visitor = new IVisitor() {

			private int currentDepth = -1;

			@Override
			public boolean visit(ASTNode n, int depth) {
				if ( depth >= currentDepth ) {
					currentDepth = depth;
					lastLeaf[0] = n;
				}
				return true;
			}
		};

		ASTUtils.visitDepthFirst( subtree , visitor );
		return lastLeaf[0];
    }
}
