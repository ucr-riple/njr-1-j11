package de.codesourcery.dcpu16.optimizer.dataflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.OperatorNode;
import de.codesourcery.dcpu16.ast.VariableReferenceNode;
import de.codesourcery.dcpu16.ast.VariableSymbol;
import de.codesourcery.dcpu16.optimizer.cfg.ControlFlowGraph;
import de.codesourcery.dcpu16.optimizer.cfg.ControlFlowGraph.BasicBlock;
import de.codesourcery.dcpu16.optimizer.dataflow.InterferenceGraph.Node;

public abstract class DataFlowAnalyzer
{
    /**
     * @see {@link BasicBlock#getMetaData()}
     */
    public static final String ALIVE_VARS_METADATA_KEY = "aliveVars";
    
    protected static final class AliveVar 
    {
        public final VariableSymbol definition;
        public final String identifier;

        private AliveVar(VariableSymbol definition)
        {
            this.definition = definition;
            identifier = definition.getScope().getUniqueIdentifier()+"."+definition.getName().getStringValue();
        }

        @Override
        public int hashCode()
        {
            return 31 + identifier.hashCode();
        }
        
        @Override
        public String toString()
        {
            return identifier;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AliveVar)) {
                return false;
            }
            final AliveVar other = (AliveVar) obj;
            return this.identifier.equals(other.identifier);
        }
    }
    
    private static boolean isAssignmentOperator(ASTNode node) 
    {
        if (node instanceof OperatorNode) {
            return ((OperatorNode) node).isAssignment();
        }
        return false; 
    }
    
    protected static final class WorkListEntry {
        public final Set<AliveVar> inState;
        public final BasicBlock block;
        
        private WorkListEntry(BasicBlock block, Set<AliveVar> inState)
        {
            this.inState = new HashSet<>(inState);
            this.block = block;
        }
        
        @Override
        public String toString()
        {
            return "WorkListEntry[ block: "+block+" ,inState: "+inState+"  ]";
        }
    }
    
    public InterferenceGraph createInterferenceGraph(ControlFlowGraph graph) 
    {
        InterferenceGraph result = new InterferenceGraph( graph );
        assignLiveVariables( graph );
        
        final Set<BasicBlock> visited=new HashSet<>();
        for ( BasicBlock bb : graph.getFunctionExit().getPredecessors() ) 
        {
            populateInterferenceGraph(  bb , graph , result , visited );
        }
        return result;
    }
    
    protected abstract Node pickNodeToSpill(InterferenceGraph graph);
    
    private void populateInterferenceGraph(BasicBlock bb,ControlFlowGraph cfg,InterferenceGraph graph,Set<BasicBlock> visited)
    {
        final Set<AliveVar> aliveVars = getAliveVars(bb);
        if ( visited.contains( bb ) ) {
            return;
        }
        visited.add(bb);
        
        if ( bb.equals( cfg.getFunctionEntry() ) || bb.equals( cfg.getFunctionExit() ) ) {
            return;
        }        
        
        for ( AliveVar var1 : aliveVars  ) 
        {
            for ( AliveVar var2 : aliveVars  ) 
            {
                if ( var1 != var2 ) 
                {
                    Node n1 = graph.getOrCreateNode( var1.definition );
                    Node n2 = graph.getOrCreateNode( var2.definition );
                    n1.addEdge( n2 );
                    n2.addEdge( n1 );
                }
            }
        }
        for ( BasicBlock pred : bb.getPredecessors() ) {
            populateInterferenceGraph( pred , cfg, graph , visited );
        }
    }
    
    public List<Node> colorGraph(InterferenceGraph graph,List<Object> colors) 
    {
        // while G cannot be k-colored 
        List<Object> availableColors = new ArrayList<>( colors );
        final List<Node> spilled = new ArrayList<>();
        final int colorCount = colors.size();
        
        int colorPtr = 0;
        Stack<Node> stack = new Stack<>();
        while ( ! canBeKColored( graph , colorCount ) ) 
        {
            // while graph G has node N with degree less than k
            Node pick = null;
            while ( ( pick = pickNodeWithNeighbourCountLessThan( graph ,colorCount ) ) != null ) 
            {
                // remove N and its edges from G and push N on a stack S
                graph.removeNode( pick );
                stack.push(pick);
            }
             
            if ( graph.getAllNodes().isEmpty() ) { // // if all nodes removed then graph is k-colorable 
                // while stack S contains node N
                while ( ! stack.isEmpty() ) 
                {
                    Node n = stack.pop();
                    // add N to graph G 
                    graph.addNode(n);
                    // and assign it a color from k colors
                    n.setColor( availableColors.get(colorPtr++) );
                    colorPtr = colorPtr % colorCount;
                }
            } else { // else graph G cannot be colored with k colors
                
                // simplify graph G choosing node N to spill and remove node
                // TODO: (choose spill nodes based on number of definitions and uses)
                Node removed = pickNodeToSpill( graph );
                graph.removeNode( removed );
                spilled.add( removed );
            }
        }
        
        for ( Node n : graph.getAllNodes() ) 
        {
            if ( ! n.hasColor() ) 
            {
                n.setColor( availableColors.get(colorPtr++) );
                colorPtr = colorPtr % colorCount;                
            }
        }
        return spilled;
    }
    
    private Node pickNodeWithNeighbourCountLessThan(InterferenceGraph graph,int k) {
        for ( Node n1 : graph.getAllNodes() ) 
        {
            if ( n1.getEdges().size() < k ) {
                return n1;
            }
        }
        return null;
    }
    
    private boolean canBeKColored(InterferenceGraph graph,int colorCount) {
        
        int currentColor = 0;
        
        final Map<Node,Integer> assignedColors = new HashMap<>();
        
        for ( Node n1 : graph.getAllNodes() ) 
        {
            Integer col;
            if ( ! assignedColors.containsKey( n1 ) ) {
                col = currentColor++;
                assignedColors.put( n1 , currentColor );
            } else {
                col = assignedColors.get( n1 );
            }
            
            for (Node edge : n1.getEdges() ) 
            {
                Integer edgeColor = assignedColors.get( edge );
                if ( edgeColor == null || col.intValue() == edgeColor.intValue() ) 
                {
                    currentColor++;
                    assignedColors.put( edge , currentColor );
                } 
            }
        }
        return currentColor <= colorCount;
    }

    public void assignLiveVariables(ControlFlowGraph graph) 
    {
        List<WorkListEntry> workList = new ArrayList<>();
        Set<AliveVar> inState = new HashSet<>();
        
        for ( BasicBlock b : graph.getFunctionExit().getPredecessors() ) {
            System.out.println("Adding to work queue: "+b);
            workList.add( new WorkListEntry(b,inState ) );
        }

        final Set<BasicBlock> visited = new HashSet<>();
        while ( ! workList.isEmpty() ) 
        {
            WorkListEntry entry = workList.remove(0);
            processWorkListItem( visited , entry , workList );
        }
    }
    
    public static Set<AliveVar> getAliveVars(BasicBlock block) 
    {
        Set<AliveVar>  result = (Set<AliveVar>) block.getMetaData().get(ALIVE_VARS_METADATA_KEY);
        return result == null ? Collections.<AliveVar>emptySet() : result;
    }
    
    private Set<AliveVar> processWorkListItem(Set<BasicBlock> visited, WorkListEntry entry, List<WorkListEntry> workList) 
    {
        Set<AliveVar> newOutState  = getReadVars( entry.block );
        Set<AliveVar> writtenVars = getWrittenVars( entry.block );
        
        entry.block.getMetaData().put(ALIVE_VARS_METADATA_KEY,newOutState);
        
        if ( ! newOutState.equals( entry.inState ) || ( newOutState.isEmpty() && writtenVars.isEmpty() ) ) 
        {
            for ( BasicBlock b : entry.block.getPredecessors() ) 
            {
                if ( ! visited.contains( b ) ) 
                {
                    System.out.println("Adding to work queue: "+b);
                    visited.add( b );
                    workList.add( new WorkListEntry( b , newOutState ) );
                }
            }
        }
        return newOutState;
    }
    
    private Set<AliveVar> getReadVars(BasicBlock block) 
    {
        Set<AliveVar> readVars = new HashSet<>();
        for ( ASTNode child : block.getASTNodes() ) 
        {
            if ( child instanceof VariableReferenceNode ) 
            {
                final VariableReferenceNode ref = (VariableReferenceNode ) child;
                // check if we're on the LHS of an assignment
                ASTNode previous=ref;
                ASTNode current = ref.getParent();
                while( current != null && ! isAssignmentOperator( current ) ) 
                {
                    previous = current;
                    current = current.getParent();
                }
                final boolean onLHS = current != null && isAssignmentOperator( current ) && current.indexOf( previous ) == 0;
                if ( ! onLHS ) {
                    readVars.add( new AliveVar(ref.getDefinitionSite() ) );
                }
            }
        }
        return readVars;
    }
    
    private Set<AliveVar> getWrittenVars(BasicBlock block) 
    {
        Set<AliveVar> writtenVars = new HashSet<>();
        for ( ASTNode child : block.getASTNodes() ) 
        {
            if ( child instanceof VariableReferenceNode ) 
            {
                final VariableReferenceNode ref = (VariableReferenceNode ) child;
                // check if we're on the LHS of an assignment
                ASTNode previous=ref;
                ASTNode current = ref.getParent();
                while( current != null && ! isAssignmentOperator( current ) ) 
                {
                    previous = current;
                    current = current.getParent();
                }
                final boolean onLHS = current != null && isAssignmentOperator( current ) && current.indexOf( previous ) == 0;
                if ( onLHS ) {
                    writtenVars.add( new AliveVar(ref.getDefinitionSite() ) );
                }
            }
        }
        return writtenVars;
    }    
}