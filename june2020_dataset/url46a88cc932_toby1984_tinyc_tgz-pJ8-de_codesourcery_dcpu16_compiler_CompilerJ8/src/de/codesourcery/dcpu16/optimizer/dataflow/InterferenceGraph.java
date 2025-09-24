package de.codesourcery.dcpu16.optimizer.dataflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import de.codesourcery.dcpu16.ast.FunctionDefinitionNode;
import de.codesourcery.dcpu16.ast.VariableSymbol;
import de.codesourcery.dcpu16.compiler.Identifier;
import de.codesourcery.dcpu16.optimizer.cfg.ControlFlowGraph;

public class InterferenceGraph
{
    private final ControlFlowGraph cfg;
    private final Map<Identifier,Node> allNodes = new HashMap<>();
    
    public static class Node 
    {
        public final VariableSymbol variable;
        public final Identifier identifier;
        private Object color;
        private final Set<Node> edges = new HashSet<>();
        
        public Object getColor()
        {
            return color;
        }
        
        public boolean hasColor() {
            return color != null;
        }
        
        public void setColor(Object color)
        {
            this.color = color;
        }
        
        @Override
        public String toString()
        {
            return "Node[ color="+color+" , identifier = "+identifier+"]";
        }
        
        public Node(VariableSymbol variable)
        {
            this.variable = variable;
            this.identifier = variable.getUniqueIdentifier();
        }
        
        public void addEdge(Node n) {
            if ( n.equals( this ) ) {
                throw new IllegalArgumentException("Won't add "+this+" to itself");
            }
            this.edges.add( n );
        }
        
        public boolean hasEdges() {
            return ! edges.isEmpty();
        }
        
        public Set<Node> getEdges()
        {
            return edges;
        }
        
        @Override
        public boolean equals(Object obj)
        {
            return (obj instanceof Node) && ((Node) obj).identifier.equals( this.identifier );
        }
        
        @Override
        public int hashCode()
        {
            return identifier.hashCode();
        }
    }
    
    public InterferenceGraph(ControlFlowGraph cfg)
    {
        this.cfg = cfg;
    }
    
    public ControlFlowGraph getControlFlowGraph()
    {
        return cfg;
    }

    public FunctionDefinitionNode getFunction()
    {
        return cfg.getFunction();
    }

    public Collection<Node> getAllNodes()
    {
        return allNodes.values();
    }
    
    public void addNode(Node n) {
        if ( allNodes.put( n.identifier,n) != null ) {
            throw new IllegalStateException("Graph node overwritten?");
        }
    }
    
    public String toString() 
    {

        List<Node> tmp = new ArrayList<>(getAllNodes());
        Collections.sort( tmp , new Comparator<Node>() {

            @Override
            public int compare(Node o1, Node o2)
            {
                return o1.identifier.getStringValue().compareTo(o2.identifier.getStringValue());
            }
        });
        
        StringBuffer result = new StringBuffer();
        for (Iterator<Node> it = tmp.iterator(); it.hasNext();) {
            Node n = it.next();
            result.append( n );
            if ( it.hasNext() ) 
            {
                result.append("\n");
            }
        }
        return result.toString();
    }
    
    public Node getOrCreateNode(VariableSymbol symbol) 
    {
        Identifier key = symbol.getUniqueIdentifier();
        Node result = allNodes.get( key );
        if ( result == null ) {
            result = new Node(symbol);
            allNodes.put( key,result);
        }
        return result;
    }

    
    public void removeNode(Node toRemove)
    {
        if ( allNodes.remove( toRemove.identifier ) == null ) {
            throw new NoSuchElementException( toRemove.toString() );
        }
        for (Node node: allNodes.values()) {
            node.getEdges().remove( toRemove ); 
        }
    }
}
