package de.codesourcery.dcpu16.ast;

import java.util.ArrayList;
import java.util.List;

import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.compiler.Identifier;
import de.codesourcery.dcpu16.compiler.MethodSignature;
import de.codesourcery.dcpu16.compiler.Scope;
import de.codesourcery.dcpu16.util.ITextRegion;

public class FunctionDeclarationNode extends FunctionSymbol
{
    private final boolean isExtern;
    private final boolean implicitlyGenerated;
    
    public FunctionDeclarationNode(IScope scope,Identifier identifier,boolean isExtern,DataType returnType,ITextRegion region)
    {
        this( scope , identifier ,isExtern, returnType ,region,false );
    }
    
    public FunctionDeclarationNode(IScope scope,Identifier identifier,boolean isExtern,DataType returnType,ITextRegion region,boolean preserveScope)
    {
        super(region);
        setSignature( new MethodSignature( identifier , returnType ) {
            @Override
            public List<ParameterDeclaration> getParameters()
            {
                return FunctionDeclarationNode.this.getParameters();
            }
        } );
        setScope( preserveScope ? scope : new Scope( identifier,this,scope) );        
        this.isExtern = isExtern;
        this.implicitlyGenerated = preserveScope;
    }

    @Override
    protected FunctionDeclarationNode createCopy()
    {
        return new FunctionDeclarationNode( getScope() , getName() , isExtern , getReturnType() , getTextRegion() , true );
    }
    
    public boolean isExtern()
    {
        return isExtern;
    }
    
    public boolean matches(FunctionDefinitionNode other) 
    {
        if ( isExtern() ) {
            throw new RuntimeException("Internal error, external function "+getSignatureAsString()+" must not be compared to definition "+other);
        }
        return this.getSignature().equals( other.getSignature() );
    }     
    
    public String getSignatureAsString() 
    {
        return ( isExtern ? "extern " : "" )+getSignature().toString();
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
    
    @Override
    public Identifier getUniqueIdentifier()
    {
        return new Identifier( getScope().getUniqueIdentifier().getStringValue()+"_"+getName().getStringValue() );
    }      
    
    public IScope getDefinitionScope() {
        return getScope().getParent();
    }
    
    @Override
    public String toString()
    {
        return "Function declaration: "+getSignatureAsString();
    }

    public boolean isImplicitlyGenerated()
    {
        return implicitlyGenerated;
    }
}