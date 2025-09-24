package de.codesourcery.dcpu16.ast;

import java.util.Collection;
import java.util.List;

import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.parser.Token;
import de.codesourcery.dcpu16.util.ITextRegion;

public interface ASTNode 
{
    public void addChildren(Collection<? extends ASTNode> col); 
    
	public void addChild(ASTNode n);

	public ASTNode child(int index);

	public ASTNode createCopy(boolean deepCopy);

	public IScope findScope();

	public int getChildCount();

	public List<ASTNode> getChildren();

	public ASTNode getParent();

	public ITextRegion getTextRegion();

	public boolean hasChildren();

	public boolean hasLeftChild();

	public boolean hasNoChildren();

	public boolean hasRightChild();

	public int indexOf(ASTNode child);

	public void insertChild(int i, ASTNode token);

	public ASTNode leftChild();

	public void merge(ITextRegion r);

	public Token merge(Token tok);

	public void removeChild(ASTNode node);

	public void removeFromParent();

	public void replaceChild(ASTNode childToReplace, ASTNode newChild);

	public void replaceChild(ASTNode childToReplace, List<ASTNode> newChildren);

	public void replaceWith(ASTNode newNode);

	public void replaceWith(List<ASTNode> newChildren);

	public ASTNode rightChild();

	public void setParent(ASTNode parent);

	public void swapChildren(ASTNode child1, ASTNode child2);
}
