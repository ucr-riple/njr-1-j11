package de.codesourcery.dcpu16.ast;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.parser.Token;
import de.codesourcery.dcpu16.util.ITextRegion;

public class ForLoopNode extends ASTNodeImpl implements IScopeDefinition {

	private IScope scope;
	private Block initializerBlock;
	private TermNode condition;
	private Block incrementBlock;
	private Block body;
	
	public ForLoopNode(Token tok) {
		super(tok);
	}
	
	public void setScope(IScope scope) {
		if (scope == null) {
			throw new IllegalArgumentException("scope must not be null");
		}
		this.scope = scope;
	}
	
	public ForLoopNode(ITextRegion region,IScope scope) {
		super(region);
		this.scope = scope;
	}
	
	@Override
	public String toString() {
		return "for-loop";
	}
	
	public void setBody(Block body) 
	{
		this.body = setParentIfNotNull( body );
	}
	
	private <T extends ASTNode> T setParentIfNotNull(T newChild) {
		if ( newChild != null ) {
			newChild.setParent( this );
		}
		return newChild;
	}
	
	public void setCondition(TermNode condition) 
	{
		if (condition == null) {
			throw new IllegalArgumentException("condition must not be null");
		}
		this.condition = setParentIfNotNull( condition );
	}
	
	public void setIncrementBlock(Block incrementBlock) {
		this.incrementBlock = setParentIfNotNull( incrementBlock ); 
	}
	
	public void setInitializerBlock(Block initializerBlock) {
		this.initializerBlock = setParentIfNotNull( initializerBlock );
	}
	
	private List<ASTNode> createBackingList() 
	{
		final List<ASTNode> result = new ArrayList<ASTNode>();
		addIfNotNull( initializerBlock , result );
		addIfNotNull( condition , result );
		addIfNotNull( incrementBlock , result );
		addIfNotNull( body , result );
		return result;
	}
	
	private static void addIfNotNull(ASTNode node,List<ASTNode> list) {
		if ( node != null ) {
			list.add( node );
		}
	}
	
	@Override
	public List<ASTNode> getChildren() 
	{
		final List<ASTNode> tmp = createBackingList();
		return new AbstractList<ASTNode>() 
		{
			@Override
			public void add(int index, ASTNode element) 
			{
				throw new UnsupportedOperationException("cannot modify for() loop body");
			}
			
			@Override
			public ASTNode get(int index) 
			{
				return tmp.get(index);
			}
			
			@Override
			public ASTNode remove(int index) 
			{
				ASTNode removed = tmp.remove( index );
				if ( removed == initializerBlock ) {
					initializerBlock = null;
				} else if ( removed == condition ) {
					condition = null;
				} else if ( removed == incrementBlock) {
					incrementBlock = null;
				} else if ( removed == body ) {
					body = null;
				} else {
					throw new IllegalStateException("Failed to find removed node ??");
				}
				return removed;
			}
			
			@Override
			public int size() {
				return tmp.size();
			}
		};
	}
	
	@Override
	protected ASTNode createCopy() {
		return new ForLoopNode( getTextRegion() , getScope() );
	}

	@Override
	public IScope getScope() {
		return scope;
	}

	public Block getInitializerBlock() {
		return initializerBlock;
	}

	public TermNode getCondition() {
		return condition;
	}

	public Block getIncrementBlock() {
		return incrementBlock;
	}

	public Block getBody() {
		return body;
	}
	
	public boolean hasBody() {
		return body != null && ! body.isEmpty();
	}
	
	public boolean hasInitializerBlock() {
		return initializerBlock != null && ! initializerBlock.isEmpty();
	}
	
	public boolean hasIncrementBlock() {
		return incrementBlock != null && ! incrementBlock.isEmpty();
	}
}