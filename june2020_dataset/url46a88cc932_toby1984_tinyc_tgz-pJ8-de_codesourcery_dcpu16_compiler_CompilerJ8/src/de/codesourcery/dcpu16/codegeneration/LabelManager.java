package de.codesourcery.dcpu16.codegeneration;

import java.util.HashMap;
import java.util.Map;

import de.codesourcery.dcpu16.ast.ISymbol;
import de.codesourcery.dcpu16.ast.ParameterDeclaration;
import de.codesourcery.dcpu16.ast.VariableReferenceNode;
import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.compiler.Identifier;

public class LabelManager
{
    // used to generate unique temporary labels
    private int labelId=0;    

    private final Map<String,LabelDefinition> labels = new HashMap<>();
    
    private String getKey(ISymbol symbol) 
    {
        IScope scope = symbol.getScope();
        if ( symbol instanceof VariableReferenceNode ) {
            scope = ((VariableReferenceNode) symbol).getDefinitionSite().getScope();
        } 
        return getKey( scope  , symbol.getName().getStringValue() );
    }

    private String getKey(IScope scope , String name) {
        return scope.getUniqueIdentifier()+"_"+name;
    }    
    
    public LabelDefinition defineLabel(ISymbol symbol) 
    {
        final String prefix = getKey( symbol );
        return defineGlobalLabel(prefix);        
    }

    public LabelDefinition defineLabel(IScope scope , Identifier name) 
    {
        final String prefix = getKey( scope , name.getStringValue() );
        return defineGlobalLabel(prefix);
    }    
    
    public boolean isLabelDefined(ISymbol symbol) 
    {
        final String prefix = getKey( symbol );
        return labels.containsKey(prefix);        
    }
    
    public boolean isLabelDefined(IScope scope , Identifier name) 
    {
        final String prefix = getKey( scope , name.getStringValue() );
        return labels.containsKey(prefix);
    }

    public LabelDefinition defineTemporaryLabel(IScope scope) 
    {
        String prefix = getKey( scope , "tmp");
        final String uniqueId = prefix+"_"+labelId;
        labelId++;
        LabelDefinition result = new LabelDefinition( uniqueId );
        labels.put( prefix , result );
        return result;        
    } 
    
    public LabelDefinition getLabel( ISymbol symbol) 
    {
        final String prefix = getKey( symbol );
        LabelDefinition existing = labels.get(prefix);
        if ( existing == null ) {
            throw new RuntimeException("Found no label for symbol: "+symbol.getName()+" in scope "+symbol.getScope() );            
        }        
        return existing;
    }

    public LabelDefinition getOrDefineLabel( ISymbol symbol) 
    {
        final String prefix = getKey( symbol );
        if ( labels.containsKey( prefix ) ) {
            return labels.get( prefix );
        }        
        return defineGlobalLabel(prefix);
    }

    private LabelDefinition defineGlobalLabel(String prefix) 
    {
        if ( labels.containsKey( prefix ) ) {
            throw new RuntimeException("Internal error,duplicate label "+prefix);
        }

        LabelDefinition result = new LabelDefinition( prefix );
        labels.put( prefix , result );
        return result;
    }
}
