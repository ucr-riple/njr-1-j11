package de.codesourcery.dcpu16.optimizer.cfg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.FunctionDefinitionNode;

public class ControlFlowGraph {

    private static final Integer DUMMY = Integer.valueOf(1);

    private final FunctionDefinitionNode function;

    private int nodeId = 1;

    private final IdentityHashMap<BasicBlock,Integer> allBlocks = new IdentityHashMap<>();

    private final BasicBlock functionEntry = new BasicBlock("_method_entry") 
    {

        @Override
        public String toString() {
            return "_method_entry";
        }
    };

    private final BasicBlock functionExit = new BasicBlock("_method_exit") {
        @Override
        public String toString() {
            return "_method_exit";
        }		
    };

    public BasicBlock newBlock() {
        return new BasicBlock();
    }

    public List<BasicBlock> getAllBlocks() 
    {
        return new ArrayList<>( allBlocks.keySet() );
    }

    public String toDOT() 
    {
        final StringBuffer buffer = new StringBuffer("digraph \""+escape( function.getSignatureAsString() ) +"\" {\n");

        // list node IDs
        for ( Iterator<BasicBlock> it = getAllBlocks().iterator() ; it.hasNext() ; ) 
        {
            BasicBlock b = it.next();
            final String label= escape( b.toString() );
            buffer.append( b.getIdentifier()+" [label=\""+label+"\"]\n");
        }

        for ( BasicBlock b : getAllBlocks() )
        {
            for ( BasicBlock bb : b.getSuccessors() ) {
                buffer.append( b.getIdentifier()+" -> "+bb.getIdentifier()+"\n");				
            }
            //			for ( BasicBlock bb : b.getPredecessors() ) {
            //				buffer.append( bb.getIdentifier()+" -> "+b.getIdentifier()+"\n");				
            //			}
        }
        buffer.append("}");
        return buffer.toString();
    }

    public void removeBlock(BasicBlock bb) 
    {
        allBlocks.remove( bb );
        for ( BasicBlock block : getAllBlocks() ) {
            block.blockRemoved( bb );
        }
    }

    private static String escape(String s) 
    {
        return s.replaceAll("\"" , "\\\"" ).replaceAll("\'" , "\\\'" ).replaceAll("\n", "\\\\n");
    }

    public void postProcess() {

        // remove empty blocks with no predecessor
        for ( BasicBlock block : getAllBlocks() ) 
        {
            if ( block.equals( functionEntry ) || block.equals( functionExit ) ) 
            {
                continue; // never remove those 
            }
            if ( block.getASTNodes().isEmpty() && block.getPredecessors().isEmpty() ) 
            {
                removeBlock( block );
            }
        }
        
        // merge blocks with only one a single success or and predecessor
        mergeBlocks();
    }

    private void mergeBlocks()
    {
        boolean cont = false;
        do 
        {
            cont = false;
            for ( BasicBlock block : getAllBlocks() ) 
            {
                if ( block.equals( functionEntry ) || block.equals( functionExit ) ) 
                {
                    continue; // never remove those 
                }

                final Set<BasicBlock> predecessors = block.getPredecessors();
                if ( predecessors.size() == 1 ) 
                {
                    final Set<BasicBlock> successors = block.getSuccessors();
                    /* merge with predecessor:
                     * 
                     *  1 -> 2 -> 3
                     *  1   ->    3
                     */
                    BasicBlock predecessor = predecessors.iterator().next();
                    if ( predecessor.equals( functionEntry ) || predecessor.getSuccessors().size() > 1 ) {
                        continue;
                    }
                    
                    predecessor.addASTNodes( block.getASTNodes() );
                    allBlocks.remove( block );
                    
                    predecessor.blockRemoved( block );
                    for ( BasicBlock successor : successors ) {
                        successor.blockRemoved( block );
                        predecessor.addSuccessor( successor );                        
                    }
                    cont = true;
                    break;
                }
            }
        } while( cont );
    }

    public  static final class Edge 
    {
        public final BasicBlock from;
        public final BasicBlock to;

        public Edge(BasicBlock from, BasicBlock to)
        {
            this.from = from;
            this.to = to;
        }

        @Override
        public int hashCode()
        {
            int result = 31 + from.hashCode();
            result = 31 * result + to.hashCode();
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj) {
                return true;
            }
            if ( !(obj instanceof Edge) ) {
                return false;
            }
            final Edge other = (Edge) obj;
            return this.from.equals( other.from ) && this.to.equals(other.to);
        }
    }
    public class BasicBlock 
    {
        private final String identifier;
        private final Set<Edge> edges = new HashSet<>();

        private final Map<String,Object> metaData = new HashMap<>();
        private final List<ASTNode> astNodes = new ArrayList<>();

        public BasicBlock() {
            identifier="block_"+nodeId;
            nodeId++;
        }
        
        public boolean hasSuccessor(BasicBlock bb) {
            return getSuccessors().contains(bb);
        }
        public Map<String, Object> getMetaData()
        {
            return metaData;
        }

        public void blockRemoved(BasicBlock bb)
        {
            for (Iterator<Edge> it = edges.iterator(); it.hasNext();) {
                Edge e = it.next();
                if ( e.from.equals( bb ) || e.to.equals( bb ) ) {
                    it.remove();
                }
            }
        }

        @Override
        public boolean equals(Object obj)
        {
            return obj instanceof BasicBlock && ((BasicBlock) obj).identifier.equals( this.identifier );
        }

        @Override
        public int hashCode()
        {
            return identifier.hashCode();
        }

        public BasicBlock(String identifier) {
            this.identifier = identifier;
        }

        public Set<BasicBlock> getSuccessors() 
        {
            Set<BasicBlock> result = new HashSet<>();
            for ( Edge e : edges ) {
                if ( e.from.equals( this ) ) {
                    result.add(e.to);
                }
            }
            return result;
        }

        public Set<BasicBlock> getPredecessors() 
        {
            Set<BasicBlock> result = new HashSet<>();
            for ( Edge e : edges ) {
                if ( e.to.equals( this ) ) {
                    result.add(e.from);
                }
            }
            return result;		    
        }

        public List<ASTNode> getASTNodes() {
            return astNodes;
        }

        @Override
        public String toString() {
            int line = 1;
            StringBuffer result = new StringBuffer();
            for (Iterator<ASTNode> it = astNodes.iterator(); it.hasNext();) {
                ASTNode n = it.next();
                result.append( line+": "+n.toString() );
                if ( it.hasNext() ) {
                    result.append("\n");
                }
                line++;
            }
            return result.toString();
        }

        public String getIdentifier() {
            return identifier;
        }

        public void addPredecessors( Collection<BasicBlock> bb ) 
        {
            for ( BasicBlock block : bb) {
                addPredecessor(block);
            }
        }

        public void addPredecessor( BasicBlock bb ) 
        {
            final Edge edge = new Edge(bb , this );
            this.edges.add( edge ); // predecessor
            bb.edges.add( edge ); // successor
        }

        public void addSuccessor( BasicBlock bb ) 
        {
            final Edge edge = new Edge(this , bb );
            this.edges.add( edge ); 
            bb.edges.add( edge ); 
        }		


        public void addASTNodes(List<ASTNode> nodesToAdd)
        {
            astNodes.addAll( nodesToAdd );
        }
        
        public void addASTNode(ASTNode node) {
            astNodes.add( node );
        }

        public boolean containsASTNode(ASTNode n) {
            return astNodes.contains(n);
        }

        public BasicBlock findBasicBlock(ASTNode node) 
        {
            return findBasicBlock( node , new HashSet<BasicBlock>() );
        }

        public BasicBlock findBasicBlock(ASTNode node,Set<BasicBlock> visited) 
        {
            if ( visited.contains( this ) ) {
                return null;
            }
            visited.add( this );

            if ( containsASTNode( node ) ) {
                return this;
            }
            for ( BasicBlock next : getSuccessors() ) {
                BasicBlock result = next.findBasicBlock( node , visited );
                if ( result != null ) {
                    return result;
                }
            }
            return null;
        }
    }

    public BasicBlock getFunctionEntry() {
        return functionEntry;
    }

    public BasicBlock getFunctionExit() {
        return functionExit;
    }	

    public BasicBlock addBlock(BasicBlock bb) 
    {
        if (bb== null) {
            throw new IllegalArgumentException("function must not be null");
        }
        allBlocks.put( bb , DUMMY );
        return bb;
    }

    public ControlFlowGraph(FunctionDefinitionNode function) {
        this.function = function;
        allBlocks.put( getFunctionEntry() , DUMMY );
        allBlocks.put( getFunctionExit() , DUMMY );
    }

    public FunctionDefinitionNode getFunction() {
        return function;
    }

    public BasicBlock findBasicBlock(ASTNode node) 
    {
        return getFunctionEntry().findBasicBlock( node );
    }

    public boolean isReachable(ASTNode node)
    {
        final Set<BasicBlock> visited = new HashSet<>();
        return isReachable( getFunctionEntry() , node , visited );
    }   
    
    public boolean isReachable(BasicBlock block , ASTNode node,Set<BasicBlock> visited)
    {
        if ( visited.contains( block ) ) {
            return false; 
        }
        visited.add( block );
        if ( block.containsASTNode( node ) ) {
            return true;
        }
        for ( BasicBlock bb : block.getSuccessors() ) 
        {
            if ( isReachable( bb  , node , visited ) ) {
                return true;
            }
        }
        return false;
    }
}
