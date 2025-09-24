package de.codesourcery.dcpu16.compiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import de.codesourcery.dcpu16.ast.ParameterDeclaration;

public class MethodSignature
{
    private final Identifier name;
    private final DataType returnType;
    private final List<ParameterDeclaration> params;
    
    public MethodSignature(Identifier functionName, DataType returnType)
    {
        this(functionName,returnType, new ArrayList<ParameterDeclaration>() );
    }
    
    public MethodSignature(Identifier identifier, DataType returnType, List<ParameterDeclaration> parameters)
    {
        this.name = identifier;
        this.returnType = returnType;
        this.params = parameters;
    }
    
    public Identifier getName()
    {
        return name;
    }
    
    public DataType getReturnType()
    {
        return returnType;
    }
    
    public boolean isVoidFunction() {
        return DataType.VOID.equals( returnType );
    }
    
    public List<ParameterDeclaration> getParameters()
    {
        return params;
    }

    public int getParameterCount() {
    	return getParameters().size();
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        result = prime * result + hashCode( getParameters() );
        result = prime * result + returnType.hashCode();
        return result;
    }
    
    private int hashCode(List<ParameterDeclaration> list) {
        final int prime = 31;
        int result = 1;
        for ( ParameterDeclaration p : list ) {
            result = prime * result + hashCode(p);
        }
        return result;
    }
    
    private int hashCode(ParameterDeclaration p) {
        return p.getDataType().hashCode();
    }

    @Override
    public boolean equals(Object obj) 
    {
        if ( obj == this ) {
            return true;
        }
        if ( !(obj instanceof MethodSignature)) {
            return false;
        }
        
        final MethodSignature other = (MethodSignature) obj;
        if ( ! this.name.equals( other.name ) ) {
            return false;
        }
        
        if ( ! this.returnType.equals( other.returnType ) ) {
            return false;
        }     
        
        List<ParameterDeclaration> p1 = getParameters();
        List<ParameterDeclaration> p2 = getParameters();
        
        if ( p1.size() != p2.size() ) {
            return false;
        }         
        for ( int i = 0 ; i < p1.size() ; i++ ) {
            if ( ! p1.get(i).getDataType().equals( p2.get(i).getDataType() ) ) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() 
    {
        final StringBuffer parameters = new StringBuffer();
        final Iterator<ParameterDeclaration> it = getParameters().iterator();
        
        while ( it.hasNext() ) 
        {
            final ParameterDeclaration p = it.next();
            parameters.append( p.getDataType().toString()+" "+p.getName().toString() );
            if ( it.hasNext() ) {
                parameters.append(" , ");
            }
        }
        return returnType.toString()+" "+name.getStringValue()+"("+parameters+")";
    }

    public int getParameterIndex(Identifier name)
    {
        final List<ParameterDeclaration> parameters = getParameters();
        for (int j = 0; j < parameters.size(); j++) {
            final ParameterDeclaration i = parameters.get(j);
            if ( name.equals( i.getName() ) ) {
                return j;
            }
        }
        throw new NoSuchElementException("Failed to find parameter '"+name+"' in "+toString());
    }    
}
