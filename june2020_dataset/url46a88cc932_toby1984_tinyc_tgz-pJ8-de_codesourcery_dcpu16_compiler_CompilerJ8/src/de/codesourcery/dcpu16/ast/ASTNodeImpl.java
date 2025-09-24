package de.codesourcery.dcpu16.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.parser.Token;
import de.codesourcery.dcpu16.util.ITextRegion;
import de.codesourcery.dcpu16.util.TextRegion;

public abstract class ASTNodeImpl implements ASTNode
{
    private final List<ASTNode> children = new ArrayList<>();
    
    private ASTNode parent;
    
    private ITextRegion textRegion;
    
    public ASTNodeImpl() {
    }
    
    public final ASTNode createCopy(boolean deepCopy) 
    {
        final ASTNode result = createCopy();
        if ( deepCopy ) 
        {
            for ( ASTNode child : getChildren() ) 
            {
                result.addChild( child.createCopy( true ) );
            }
        }
        return result;
    }
    
    public void swapChildren(ASTNode child1,ASTNode child2) {
        
        int idx1 = indexOf(child1);
        int idx2 = indexOf(child2);
        if ( idx1 == -1 ) {
            throw new IllegalArgumentException("Failed to find child "+child1+" in "+this);
        }
        if ( idx2 == -1 ) {
            throw new IllegalArgumentException("Failed to find child "+child2+" in "+this);
        }
        getChildren().set( idx1, child2);
        getChildren().set( idx2, child1);
    }
    
    public IScope findScope() {
        
        ASTNode current = this;
        while ( current != null && !(current instanceof IScopeDefinition)) 
        {
            current = current.getParent();
        }
        return current == null ? null : ((IScopeDefinition) current).getScope(); 
    }
    
    public ASTNode getParent() {
        return parent;
    }
    
    public int indexOf(ASTNode child) 
    {
        int index = 0;
        for ( ASTNode n : getChildren() ) {
            if ( n == child ) {
                return index;
            }
            index++;
        }
        return -1;
    }
    
    public boolean hasLeftChild() {
        return getChildCount() > 0;
    }
    
    public ASTNode leftChild() {
        return child(0);
    }
    
    public boolean hasRightChild() {
        return getChildCount() > 1;
    } 
    
    public ASTNode rightChild() {
        return child(1);
    }    
    
    protected abstract ASTNode createCopy();
    
    public ASTNodeImpl(Token tok) {
        this( tok.getTextRegion() );
    }    
    
    public ASTNodeImpl(ITextRegion textRegion) 
    {
    	if ( textRegion != null ) {
    		this.textRegion = new TextRegion( textRegion );
    	}
    }
    
    public ASTNode child(int index) {
    	return getChildren().get(index);
    }
    
    public ITextRegion getTextRegion()
    {
        return textRegion;
    }
    
    public boolean hasNoChildren() {
        return getChildren().isEmpty();
    }
    
    public boolean hasChildren() {
        return ! getChildren().isEmpty();
    }
    
    public int getChildCount() {
        return getChildren().size();
    }
    
    public List<ASTNode> getChildren()
    {
        return children;
    }
    
    public void addChildren(Collection<? extends ASTNode> col) 
    {
    	for ( ASTNode n : col ) {
    		addChild( n );
    	}
    }
    
    public void addChild(ASTNode newChild) 
    {
        newChild.setParent( this );
        getChildren().add( newChild );        
        merge( newChild.getTextRegion() );
    }
    
    public void setParent(ASTNode parent) {
        this.parent = parent;
    }
    
    public Token merge(Token tok) {
        merge(tok.getTextRegion() );
        return tok;
    }
    
    public void merge(ITextRegion r) 
    {
        if ( r == null ) {
            return;
        }
        if ( this.textRegion == null ) {
            this.textRegion = new TextRegion(r);
        } else {
            this.textRegion.merge( r );
        }        
    }

    public void insertChild(int i, ASTNode newChild)
    {
        newChild.setParent( this );        
        getChildren().add( i , newChild );
        merge( newChild.getTextRegion() );
    }

    public void replaceWith(ASTNode newNode) {
        getParent().replaceChild( this , newNode );
    }
    
    public void replaceWith(List<ASTNode> newChildren) {
        getParent().replaceChild( this , newChildren );
    }   
    
    public void replaceChild(ASTNode childToReplace, List<ASTNode> newChildren)
    {
        if ( newChildren.isEmpty() ) {
            throw new IllegalArgumentException("No replacement nodes ?");
        }
        
        final int idx = indexOf( childToReplace );
        if ( idx == -1 ) {
            throw new IllegalArgumentException( childToReplace+" is no child of "+this);
        }
        
        final List<ASTNode> tmp = new ArrayList<>(newChildren);
        final ASTNode firstNewChild = tmp.remove(0);
        firstNewChild.setParent(this);        
        getChildren().set( idx , firstNewChild );
        
        if ( tmp.isEmpty() ) {
            return;
        }
        
        Collections.reverse(tmp); // need to reverse list because List#add(int,Object) will shift the element at index I to the right
        
        for ( ASTNode newChild : tmp ) {
            newChild.setParent( this );            
            getChildren().add( idx , newChild );
        }
    } 
    
    public void replaceChild(ASTNode childToReplace, ASTNode newChild)
    {
        final int idx = indexOf( childToReplace );
        if ( idx == -1 ) {
            throw new IllegalArgumentException( childToReplace+" is no child of "+this);
        }
        newChild.setParent(this);        
        getChildren().set( idx ,newChild );
    }
    
    public void removeFromParent() {
        getParent().removeChild( this );
    }

    public void removeChild(ASTNode node)
    {
        final int idx = indexOf( node );
        if ( idx == -1 ) {
            throw new IllegalArgumentException( node+" is no child of "+this);
        }        
        getChildren().remove( idx );
    }
}