package de.codesourcery.dcpu16.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.compiler.Identifier;
import de.codesourcery.dcpu16.compiler.MethodSignature;
import de.codesourcery.dcpu16.util.ITextRegion;

public class FunctionDefinitionNode extends FunctionSymbol
{
    public FunctionDefinitionNode(Identifier functionName,DataType returnType,ITextRegion region)
    {
    	super(region);
        setSignature( new MethodSignature(functionName, returnType) {
            @Override
            public List<ParameterDeclaration> getParameters()
            {
                return FunctionDefinitionNode.this.getParameters();
            }
        });
    }
    
    @Override
    public Identifier getUniqueIdentifier()
    {
        return new Identifier( getScope().getUniqueIdentifier().getStringValue()+"_"+getName().getStringValue() );
    }      
    
    @Override
    protected ASTNode createCopy()
    {
    	final FunctionDefinitionNode result = new FunctionDefinitionNode( getName() , getReturnType() , getTextRegion() );
    	result.setScope( getScope() );
    	return result;
    }    
    
    public List<ParameterDeclaration> getParameters() 
    {
    	List<ParameterDeclaration> result = new ArrayList<>();
    	for ( ASTNode child : getChildren() ) {
    		if ( child instanceof ParameterDeclaration) {
    			result.add( (ParameterDeclaration) child );
     		}
    	}
    	return result;
    }
    
    public Block getBody() {
    	for ( ASTNode child : getChildren() ) {
    		if ( child instanceof Block) {
    			return (Block) child;
    		}
    	}
    	throw new IllegalStateException("Function definition "+getSignatureAsString()+" has no BLOCK node ?");
    }
    
    public IScope getDefinitionScope() {
    	return getScope().getParent();
    }
    
    @Override
    public String toString()
    {
        return "Function definition: "+getSignatureAsString();
    }

    public FunctionDeclarationNode toImplicitFunctionDeclaration()
    {
        return new FunctionDeclarationNode( getScope() , getName() , false , getReturnType() , getTextRegion() , true );
    }

    public ParameterDeclaration getParameter(Identifier identifier)
    {
        for ( ParameterDeclaration p : getParameters() ) {
            if ( p.getName().equals( identifier ) ) {
                return p;
            }
        }
        throw new NoSuchElementException("Method "+getSignatureAsString()+" has no parameter named "+identifier);
    }
}