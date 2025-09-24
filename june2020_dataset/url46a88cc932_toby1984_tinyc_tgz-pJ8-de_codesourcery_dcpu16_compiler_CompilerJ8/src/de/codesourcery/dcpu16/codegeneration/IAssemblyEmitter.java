package de.codesourcery.dcpu16.codegeneration;

import java.util.List;

import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.InlineAssemblyNode;
import de.codesourcery.dcpu16.codegeneration.CPUModel.SlotMapping;

public interface IAssemblyEmitter
{
    public static final Register METHOD_RESULT_REGISTER = Register.X;
    
    public void discardAccumulator();
    
    public SlotMapping getHandle(ASTNode node);
    
    public void load(LabelDefinition value , ASTNode node);
    
    public void load(SlotMapping mapping, ASTNode node,String... comment);
    
    public void endOfStatement(ASTNode node);
    
    public void indirection();
    
    public void loadIndirect(String... comments);    
    
    public void loadAddress(ASTNode node,String... comments); 
    
    public void load(ASTNode node,String... comments); 
    
    public void close();
    
    // instructions
    
    public void pushValue(String... comment);
    
    public void pushValue(SlotMapping mapping,String... comment);
    
    public SlotMapping popValueToAux(String... comment);
    
    public void popValues(int count,String... comment);
    
    public void jumpTo(SlotMapping mapping,String... comment);
    
    public void onFunctionEntry(String... comment);
    
    public void beforeFunctionExit(String... comment);
    
    public void jumpTo(LabelReference label,String... comment);
    
    public void returnFromSubroutine(String... comment);
    
    public void popValue(SlotMapping destination, String... comment);
    
    public void storeValue(SlotMapping destination,String... comment);
    
    public void storeValueIndirect(SlotMapping destination,String... comment);
    
    public void popValueIndirect(String... comment);
    
    public void popValueIndirect(SlotMapping destination,String... comment);    
    
    public void defineCodeLabel(LabelDefinition def, String... comment);

    public void defineDataLabel(LabelDefinition def, String... comment);
    
    public void bitwiseOr(String... comment);
    
    public void multiply(String... comment);
    
    public void add(String... comment);
    
    public void subtract(String... comment);

    public void outputCommentLine(String comment);

    public void halt(String... comment);
    
    public void branchOnBoolean(LabelReference falseLabel,String... comment);
    
    public void compareEqualsWithResult(String... comment);
    
    public void compareGreaterThanWithResult(String... comment);
    
    public void compareLessThanWithResult(String... comment);
    
    public void compareNotEqualWithResult(String... comment);
    
    public void compareLessThanEqual(LabelReference falseLabel, String... comment);
    
    public void compareLessThanEqualWithResult(LabelReference falseLabel,String... comment);    
    
    public void compareGreaterThanEqual(LabelReference falseLabel,String... comment);      
    
    public void compareGreaterThanEqualWithResult(LabelReference falseLabel,String... comment);     

    public void compareEquals( String... comment);

    public void compareNotEqual( String... comment);

    public void compareLessThan( String... comment);

    public void compareGreaterThan( String... comment);
    
    public void outputInlineAssembly(InlineAssemblyNode node);
    
    /**
     * 
     * @param label optional label, may be <code>null</code>
     * @param values
     * @param comment
     */
    public void outputValues(LabelDefinition label, List<String> values,String... comment);
    
    public void reserveUninitializedMemory(LabelDefinition label,int wordCount,String... comment);
}