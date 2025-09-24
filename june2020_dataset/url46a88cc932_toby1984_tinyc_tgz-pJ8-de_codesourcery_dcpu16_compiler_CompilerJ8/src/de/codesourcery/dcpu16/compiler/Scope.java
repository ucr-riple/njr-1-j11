package de.codesourcery.dcpu16.compiler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.FunctionDeclarationNode;
import de.codesourcery.dcpu16.ast.FunctionDefinitionNode;
import de.codesourcery.dcpu16.ast.ParameterDeclaration;
import de.codesourcery.dcpu16.ast.VariableDefinition;

public class Scope implements IScope
{
    private final Identifier scopeName;
    private final ASTNode thisScopeDefinitionSite;
    
    private final IScope parent;
    
    private final List<DataType> dataTypes = new ArrayList<>();
    private final Map<Identifier,FunctionDefinitionNode> functionDefinitions = new HashMap<>();
    private final Map<Identifier,FunctionDeclarationNode> functionDeclarations = new HashMap<>();
    
    // value is either an ParameterDeclaration or a VariableDefinition
    private final Map<Identifier,ASTNode> variables= new HashMap<>();
    
    private final Set<Identifier> allSymbols = new HashSet<>();
    
    public Scope( Identifier identifier,ASTNode definitionSite) {
        this(identifier,definitionSite , null);
    }
    
    public void registerSymbol(Identifier id,boolean failOnDuplicate) 
    {
        if ( failOnDuplicate && allSymbols.contains( id ) ) {
            throw new RuntimeException("Duplicate symbol "+id);
        }
        allSymbols.add( id );
    }
    
    public Scope( Identifier identifier,ASTNode definitionSite,IScope parent) {
        this.thisScopeDefinitionSite = definitionSite;
        this.scopeName = identifier;
        this.parent = parent;
    }

    @Override
    public IScope getTopLevelScope() 
    {
    	IScope current = this;
    	while ( current.getParent() != null ) {
    		current = current.getParent();
    	}
    	return current;
    }
    
    @Override
    public DataType getDataType(Identifier name, boolean isArray,int ptrCount)
    {
        if ( DataType.isBuiltInType( name ) ) 
        {
        	return DataType.getDataType( name , isArray, ptrCount );
        }
        throw new NoSuchElementException("Unknown data type "+name+( isArray ? "[]" : "" ) );
    }

    @Override
    public boolean isDataTypeDefined(Identifier type)
    {
        if ( DataType.isBuiltInType( type ) ) {
        	return true;
        }
        
        for ( DataType t : dataTypes ) {
            if ( t.getIdentifier().equals( type ) ) {
                return true;
            }
        }          
        return false;
    }    
    
    @Override
    public DataType defineDataType(Identifier identifier,boolean isArray,int ptrCount) 
    {
    	if ( DataType.isBuiltInType( identifier ) ) {
    		return DataType.getDataType(identifier,isArray,ptrCount);
    	}
    	throw new IllegalArgumentException("Not a built-in data type: "+identifier);
    }
    
    @Override
    public boolean isVariableDefined(Identifier name)
    {
        return variables.containsKey( name );
    }

    @Override
    public boolean isFunctionDefined(Identifier functionName)
    {
        return functionDefinitions.containsKey( functionName );
    }
    
    @Override
    public boolean isFunctionDeclared(Identifier functionName)
    {
        return functionDeclarations.containsKey( functionName );
    }    

    @Override
    public void defineFunction(FunctionDefinitionNode definitionSite)
    {
        final Identifier functionName = definitionSite.getName();
        final FunctionDefinitionNode existing = functionDefinitions.get( functionName );
        if ( existing != null ) 
        {
            throw new IllegalStateException( definitionSite+" is already defined differently as "+existing);
        }        
        
        registerSymbol( functionName , false ); 
        
        System.out.println("DEFINED FUNCTION: "+functionName+" (scope: "+getIdentifier()+")");
        functionDefinitions.put( functionName , definitionSite );
        
        declareFunction( definitionSite.toImplicitFunctionDeclaration() );
    }

    @Override
    public void defineVariable(ParameterDeclaration declarationSite) 
    {
    	internalDefineVariable(declarationSite.getName(),declarationSite);
    }
    
    @Override
    public void defineVariable(VariableDefinition definitionSite) 
    {
    	internalDefineVariable(definitionSite.getName(),definitionSite);
    }
    
    private void internalDefineVariable(Identifier name,ASTNode definition) 
    {
        if ( variables.containsKey( name ) ) {
            throw new IllegalStateException("Variable "+name+" is already defined at "+variables.get( name ) );
        }
        System.out.println("VARIABLE: "+name+" (scope: "+getIdentifier()+")");
        registerSymbol( name , true );
        variables.put( name , definition );  
    }
    
    @Override
    public Identifier getIdentifier()
    {
        return scopeName;
    }

    @Override
    public IScope getParent()
    {
        return parent;
    }

    @Override
    public ASTNode getDefinitionSite()
    {
        return thisScopeDefinitionSite;
    }

	@Override
	public FunctionDefinitionNode getFunction(Identifier functionName) 
	{
		return functionDefinitions.get( functionName );
	}
	
	@Override
	public String toString() {
		return getIdentifier().getStringValue();
	}

    @Override
    public void declareFunction(FunctionDeclarationNode declarationSite)
    {
        final Identifier functionName = declarationSite.getName();
        
        if ( declarationSite.isImplicitlyGenerated() ) {
            System.out.println("DECLARED FUNCTION (implicit): "+functionName+" (scope: "+getIdentifier()+")");
        } else {
            System.out.println("DECLARED FUNCTION: "+functionName+" (scope: "+getIdentifier()+")");  
        }     
        
        FunctionDeclarationNode existing = functionDeclarations.get( functionName );
        if ( existing != null ) 
        {
            // ignore duplicate declarations as long as they resemble exactly the same function
            if ( existing.matches( declarationSite ) ) 
            {
                // replace implicit declarations with explicit ones so
                // the declaration refers to the declaration AST node that 
                if ( existing.isImplicitlyGenerated() && ! declarationSite.isImplicitlyGenerated() ) 
                {
                    functionDeclarations.put( functionName , declarationSite );
                }
                return;
            }
            throw new IllegalStateException( declarationSite+" is already declared differently as "+existing);
        }
        registerSymbol( functionName , false );         
        functionDeclarations.put( functionName , declarationSite );  
    }

    @Override
    public boolean isDefined(Identifier identifier,boolean searchParentScopes)
    {
        if ( allSymbols.contains(identifier) ) {
            return true;
        }
        return searchParentScopes && parent != null ? parent.isDefined(identifier, true) : false;
    }

    @Override
    public ASTNode getDefinitionSite(Identifier identifier,boolean searchParentScopes)
    {
        if ( ! isDefined(identifier,searchParentScopes) ) {
            return null;
        }
        
        if ( functionDefinitions.containsKey( identifier ) ) {
            return functionDefinitions.get(identifier);
        }
        
        if ( functionDeclarations.containsKey( identifier ) ) {
            return functionDeclarations.get(identifier);
        }
        
        if ( variables.containsKey( identifier ) ) {
            return variables.get(identifier);
        }         
 
        if ( isDataType( identifier , searchParentScopes ) ) 
        {
            throw new UnsupportedOperationException("Data types do not have definition sites yet, type: "+identifier);
        }
        
        return searchParentScopes && parent != null ? parent.getDefinitionSite( identifier , true ) : null;
    }

    @Override
    public IScope getScope(Identifier identifier)
    {
        if ( ! isDefined( identifier ,true ) ) {
            throw new NoSuchElementException("Failed to find "+identifier+" in any scope");
        }
        
        if ( functionDefinitions.containsKey( identifier ) ) {
            return this;
        }
        
        if ( functionDeclarations.containsKey( identifier ) ) {
            return this;
        }
        
        if ( variables.containsKey( identifier ) ) {
            return this;
        }         
 
        for ( DataType t : dataTypes ) 
        {
            if ( t.getIdentifier().equals( identifier ) ) 
            {
                return this;
            }
        }        
        
        if ( parent != null ) {
            return parent.getScope( identifier );
        }
        throw new RuntimeException("Internal error, failed to find "+identifier+" in any scope ??");
    }
    
    private boolean isDataType(Identifier identifier,boolean searchParents) 
    {
        for ( DataType t : dataTypes ) 
        {
            if ( t.getIdentifier().equals( identifier ) ) 
            {
                return true;
            }
        } 
        
        if ( searchParents ) 
        {
            IScope next = getParent();
            while ( next != null ) {
                if ( next.isDataTypeDefined( identifier ) ) {
                    return true;
                }
                next = next.getParent();
            }
        }
        return false;
    }

    @Override
    public void remove(Identifier name)
    {
        if ( ! allSymbols.contains( name ) ) {
            throw new NoSuchElementException("Failed to find "+name+" in scope "+this);            
        }
        
        if ( isDataType( name ,  false ) ) {
            // TODO: Needs to be supported for user-defined data types
            throw new UnsupportedOperationException("Won't remove  data type "+name+" from scope "+this);
        }
        
        functionDefinitions.remove( name );
        functionDefinitions.remove( name );
        variables.remove( name );
        
        allSymbols.remove( name );
    }
    
    @Override
    public boolean isGlobalScope() {
    	return GLOBAL_SCOPE .equals( getIdentifier() );
    }

    @Override
    public Identifier getUniqueIdentifier()
    {
        final List<IScope> scopes = new ArrayList<>();
        IScope current = this;
        do {
            scopes.add( current );
            current = current.getParent();
        } while ( current != null );
        Collections.reverse( scopes );
        StringBuffer result = new StringBuffer();
        for (Iterator<IScope> it = scopes.iterator(); it.hasNext();) {
            final IScope i = it.next();
            result.append( i.getIdentifier().getStringValue() );
            if ( it.hasNext() ) {
                result.append("_");
            }
        }
        return new Identifier( result.toString() );
    }
}