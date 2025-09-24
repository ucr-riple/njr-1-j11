package de.codesourcery.dcpu16.optimizer.cfg;

import java.util.Collections;
import java.util.Set;

import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.DoLoopNode;
import de.codesourcery.dcpu16.ast.ForLoopNode;
import de.codesourcery.dcpu16.ast.FunctionDefinitionNode;
import de.codesourcery.dcpu16.ast.IfNode;
import de.codesourcery.dcpu16.ast.ReturnNode;
import de.codesourcery.dcpu16.ast.WhileLoopNode;
import de.codesourcery.dcpu16.optimizer.cfg.ControlFlowGraph.BasicBlock;

public class ControlFlowAnalyzer {

	public static boolean POST_PROCESS_GRAPH = true;
	
	public ControlFlowGraph createGraph(FunctionDefinitionNode function) 
	{
		final ControlFlowGraph result = new ControlFlowGraph( function );
		
		new NodeVisitor( result ).calc( function );
				
		if (POST_PROCESS_GRAPH) {
		    result.postProcess();
		}
		
		return result;
	}
	
	protected static final class NodeVisitor {

		private final ControlFlowGraph graph;
		private BasicBlock currentBlock;
		
		public NodeVisitor(ControlFlowGraph graph) 
		{
			this.graph = graph;
		}
		
		public void calc(ASTNode node) 
		{
			currentBlock = newBlock();
			graph.getFunctionEntry().addSuccessor( currentBlock );
			
			final Set<BasicBlock> predecessors = calcSuccessors( node , Collections.singleton( currentBlock ) );
			
			for ( BasicBlock bb : predecessors ) {
				graph.getFunctionExit().addPredecessor( bb );
			}
		}
		
		private Set<BasicBlock> calcSuccessors(ASTNode n,Set<BasicBlock> predecessors)
		{
			if ( n instanceof ReturnNode ) 
			{
				return processReturnNode(n,predecessors);
			} 
			
			if ( n instanceof IfNode) 
			{
				return processIfNode(n,predecessors);
			} 
			
            if ( n instanceof DoLoopNode) 
            {
                return processDoNode(n,predecessors);
            } 			
            
            if ( n instanceof WhileLoopNode) 
            {
                return processWhileNode(n,predecessors);
            }  
            
            if ( n instanceof ForLoopNode) 
            {
                return processForNode(n,predecessors);
            }              
			
			currentBlock.addASTNode( n );
			
			Set<BasicBlock> pred = predecessors;
			for ( ASTNode child : n.getChildren() ) 
			{
				pred = calcSuccessors( child , pred );
			}
			return pred;
		}
        
        private Set<BasicBlock> processReturnNode(ASTNode n,Set<BasicBlock> predecessors) 
        {
            final BasicBlock returnBlock = newBlock();
            addSuccessor(returnBlock,predecessors);
            recursivelyAddAllNodes( returnBlock , n );
            
            graph.getFunctionExit().addPredecessor( returnBlock );
            
            currentBlock = newBlock();
            return Collections.singleton(currentBlock);
        }		
		
        private Set<BasicBlock> processIfNode(ASTNode n,Set<BasicBlock> predecessors) 
        {
            final IfNode ifNode = (IfNode) n;
            
            // add condition
            final BasicBlock followingBlock = newBlock();
            
            final BasicBlock conditionBlock = newBlock();
            conditionBlock.addASTNode( n );
            recursivelyAddAllNodes( conditionBlock , ifNode.getCondition() );
            addSuccessor( conditionBlock , predecessors );
            
            // add IF body
            final BasicBlock bodyBlock = newBlock();
            conditionBlock.addSuccessor( bodyBlock );
            
            currentBlock = bodyBlock;
            predecessors = calcSuccessors( ifNode.getBody() , Collections.singleton( bodyBlock ) );
            
            // add ELSE block
            if ( ifNode.hasElseBlock() ) 
            {
                BasicBlock elseBlock= newBlock();
                
                currentBlock = elseBlock;
                bodyBlock.addSuccessor( elseBlock );
                predecessors = calcSuccessors( ifNode.getElseBlock() , Collections.singleton( elseBlock ) );
            } else {
                conditionBlock.addSuccessor( followingBlock );
            }
            
            addSuccessor( followingBlock, predecessors );
            currentBlock = followingBlock;
            return Collections.singleton( followingBlock );
        }
        
        private Set<BasicBlock> processWhileNode(ASTNode n,Set<BasicBlock> predecessors) 
        {
            final WhileLoopNode whileLoop = (WhileLoopNode) n;
            
            currentBlock.addASTNode( n );
            
            // add condition block
            final BasicBlock conditionBlock= newBlock();
            addSuccessor( conditionBlock , predecessors );
            currentBlock = conditionBlock;
            calcSuccessors( whileLoop.getCondition() , Collections.singleton( conditionBlock ) ); 
            
            if ( whileLoop.hasBody() ) 
            {
                final BasicBlock bodyBlock = newBlock();
                conditionBlock.addSuccessor( bodyBlock );
                currentBlock = bodyBlock;
                calcSuccessors( whileLoop.getBody() , Collections.singleton( conditionBlock ) );
                bodyBlock.addSuccessor( conditionBlock );
            }
            return Collections.singleton( conditionBlock );
        }   
        
        private Set<BasicBlock> processForNode(ASTNode n,Set<BasicBlock> predecessors) 
        {
            final ForLoopNode forLoop = (ForLoopNode) n;
            
            currentBlock.addASTNode( n );
            
            // add initializer block
            if ( forLoop.hasInitializerBlock() ) 
            {
                predecessors = calcSuccessors( forLoop.getInitializerBlock() , predecessors );
            }
            
            // add condition block
            final BasicBlock conditionBlock= newBlock();
            addSuccessor( conditionBlock , predecessors );
            currentBlock = conditionBlock;
            calcSuccessors( forLoop.getCondition() , Collections.singleton( conditionBlock ) ); 
            
            // add body block
            BasicBlock bodyBlock = null;
            BasicBlock incrementBlock = null;
            
            if ( forLoop.hasIncrementBlock() ) {
                incrementBlock = newBlock();
            }
            
            if ( forLoop.hasBody() ) 
            {
                bodyBlock = newBlock();
                conditionBlock.addSuccessor( bodyBlock );
                currentBlock = bodyBlock;
                calcSuccessors( forLoop.getBody() , Collections.singleton( bodyBlock ) );
                if ( forLoop.hasIncrementBlock() ) {
                    bodyBlock.addSuccessor( incrementBlock );                    
                } else {
                    bodyBlock.addSuccessor( conditionBlock );
                }
            }
            
            // add increment block
            if ( forLoop.hasIncrementBlock() ) 
            {
                currentBlock = incrementBlock;
                predecessors = calcSuccessors( forLoop.getIncrementBlock() , Collections.singleton( incrementBlock ) );
                addSuccessor( conditionBlock , predecessors ); 
            }
            
            return Collections.singleton( conditionBlock );
        }          
        
        private Set<BasicBlock> processDoNode(ASTNode n,Set<BasicBlock> predecessors) 
        {
            final DoLoopNode doLoopNode = (DoLoopNode) n;
            
            currentBlock.addASTNode( n );
            
            // add body
            final BasicBlock bodyBlock = newBlock();
            final BasicBlock conditionBlock= newBlock();
            
            addSuccessor( bodyBlock , predecessors );
            
            currentBlock = bodyBlock;
            
            calcSuccessors( doLoopNode.getBody() , Collections.singleton( bodyBlock ) );
            
            // add condition block
            conditionBlock.addSuccessor( bodyBlock );
            conditionBlock.addPredecessor( bodyBlock );
            
            currentBlock = conditionBlock;
            
            calcSuccessors( doLoopNode.getCondition() , Collections.singleton( conditionBlock ) );
            
            return Collections.singleton( conditionBlock );
        }        
        
        // ========= helper functions =========
        
		private void recursivelyAddAllNodes(BasicBlock bb,ASTNode subtree) 
		{
			bb.addASTNode( subtree );
			for ( ASTNode child : subtree.getChildren() ) {
				recursivelyAddAllNodes( bb , child );
			}
		}

		private void addSuccessor(BasicBlock successor,Set<BasicBlock> predecessors) {
			for ( BasicBlock bb : predecessors ) {
				bb.addSuccessor( successor );
			}
		}
		
		private BasicBlock newBlock() {
		    BasicBlock result=graph.newBlock();
		    graph.addBlock( result );
		    return result;
		}
	}
}