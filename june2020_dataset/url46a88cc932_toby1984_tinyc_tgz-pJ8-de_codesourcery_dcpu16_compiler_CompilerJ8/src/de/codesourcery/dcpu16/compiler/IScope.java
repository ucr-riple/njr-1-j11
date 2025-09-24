package de.codesourcery.dcpu16.compiler;

import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.FunctionDeclarationNode;
import de.codesourcery.dcpu16.ast.FunctionDefinitionNode;
import de.codesourcery.dcpu16.ast.ParameterDeclaration;
import de.codesourcery.dcpu16.ast.VariableDefinition;

public interface IScope
{
    public static final Identifier GLOBAL_SCOPE = new Identifier("_GLOBAL_");
    
    public Identifier getIdentifier();
    
    /**
     * Returns this scope's unique identifier.
     * 
     * <p>A scope's unique identifier is constructed by concatenating
     * the identifiers of all parent scopes with this scope's identifier.</p>
     * 
     * @return
     */
    public Identifier getUniqueIdentifier();
    
    public IScope getParent();
    
    public boolean isGlobalScope();
    
    public IScope getTopLevelScope();    
    
    /**
     * Returns the definition site of this scope (usually a block element).
     * 
     * @return
     */
    public ASTNode getDefinitionSite();
    
    public boolean isDefined(Identifier identifier,boolean searchParentScopes);
    
    public IScope getScope(Identifier identifier);
    
    /**
     * Returns the definition site of a given identifier or 
     * <code>null</code>.
     * 
     * <p>Possible results are:</p>
     * 
     * <ul>
     *   <li>variable identifier: ParameterDeclaration or a VariableDefinition</li>
     *   <li>function identifier: FunctionDefinition or (is function is only declared but not defined) FunctionDeclaration</li>
     *   <li>DataType identifier: Always throws {@link UnsupportedOperationException} since currently custom data types cannot be defined</li>
     * </ul>
     * 
     * @param identifier
     * @param searchParentScopes
     * 
     * @return declaration (definition) site or <code>null</code> if no symbol with this identifier could be found
     * in this scope (or it's parents)
     * 
     * @see #getParent()
     * @see #isDefined(Identifier)
     * @throws UnsupportedOperationException
     */
    public ASTNode getDefinitionSite(Identifier identifier,boolean searchParentScopes);
    
    public DataType defineDataType(Identifier name,boolean isArray,int ptrCount);
    
    public DataType getDataType(Identifier name,boolean isArray,int ptrCount);    
    
    public boolean isDataTypeDefined(Identifier type);
    
    public boolean isVariableDefined(Identifier name);
    
    public boolean isFunctionDefined(Identifier functionName);
    
    public boolean isFunctionDeclared(Identifier functionName);    
    
    /**
     * Searches for a specific function in this scope, returning <code>null</code>
     * if no matching function has found.
     * 
     * @param functionName
     * @return function definition or <code>null</code>
     */
    public FunctionDefinitionNode getFunction(Identifier functionName);
    
    public void declareFunction(FunctionDeclarationNode declarationSite);
    
    public void defineFunction(FunctionDefinitionNode definitionSite);
    
    public void defineVariable(ParameterDeclaration declarationSite);
    
    public void defineVariable(VariableDefinition definitionSite);

    public void remove(Identifier name);
}
