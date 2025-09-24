package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.util.ITextRegion;

public class ReturnNode extends ASTNodeImpl
{
    public ReturnNode(ITextRegion textRegion)
    {
        super(textRegion);
    }
    
    @Override
    public String toString()
    {
        if ( returnsSomething() ) {
            return "return "+getReturnValue();
        }
        return "return";
    }
    
    public boolean returnsSomething() {
        return hasChildren();
    }
    
    public TermNode getReturnValue() {
        return (TermNode) child(0);
    }
    
    public boolean matchesSignature(FunctionDefinitionNode function) 
    {
        if ( function.isVoidFunction() ) {
            final boolean matched = ! returnsSomething();
            if ( ! matched ) {
                System.err.println("matchesSignature(): returning a value from a VOID function: "+function.getSignatureAsString());
            }
            return matched;
        }
        
        if ( ! returnsSomething() ) {
            System.err.println("matchesSignature(): returning NO value from non-void function: "+function.getSignatureAsString());
            return false;
        }
        
        boolean matched = getReturnedDataType().isAssignableTo( function.getReturnType() );
        if ( ! matched ) {
            System.err.println("matchesSignature(): returned value of type "+getReturnedDataType()+" is " +
            		" not compatible with return type of function: "+function.getSignatureAsString());
            return false;
        }
        return true;
    }    
    
    public DataType getReturnedDataType() 
    {
        return getReturnValue().getDataType();
    }
    
    @Override
    protected ReturnNode createCopy()
    {
        return new ReturnNode( getTextRegion() );
    }     
}