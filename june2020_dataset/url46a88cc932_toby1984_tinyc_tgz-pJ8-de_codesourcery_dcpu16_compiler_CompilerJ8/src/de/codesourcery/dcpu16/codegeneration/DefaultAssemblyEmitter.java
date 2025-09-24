package de.codesourcery.dcpu16.codegeneration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.ISymbol;
import de.codesourcery.dcpu16.ast.InlineAssemblyNode;
import de.codesourcery.dcpu16.ast.NumberLiteralNode;
import de.codesourcery.dcpu16.ast.ParameterDeclaration;
import de.codesourcery.dcpu16.ast.StringLiteralNode;
import de.codesourcery.dcpu16.ast.VariableSymbol;
import de.codesourcery.dcpu16.codegeneration.CPUModel.SlotMapping;
import de.codesourcery.dcpu16.compiler.IScope;

public abstract class DefaultAssemblyEmitter extends CPUModel implements IAssemblyEmitter
{
    private final IAssemblyWriter writer;
    private final IScope scope;
    
    private boolean valueInAccumulatorImportant = false;
    private String lastSpilledDescription;
    private String lastValueDescription;
    
    public DefaultAssemblyEmitter(IAssemblyWriter writer, IScope scope)
    {
        super(writer);
        this.scope = scope;
        this.writer = writer;
    }

    @Override
    public void endOfStatement(ASTNode node)
    {
        valueInAccumulatorImportant = false;
        lastValueDescription=null;
    }
    
    private void setAccumulatorLoaded(String msg) {
        lastValueDescription=msg;
        valueInAccumulatorImportant = true;
    }
    
    @Override
    public void discardAccumulator() {
    	valueInAccumulatorImportant = false;
    	lastValueDescription=null;
    }
    private boolean spill(String...comment) 
    {
//    	new Exception("========== splill =========").printStackTrace(System.err);
//    	System.err.flush();
        boolean spilled = false;
        if ( valueInAccumulatorImportant ) 
        {
            lastSpilledDescription = lastValueDescription;
            outputCommentLine("spilling "+lastValueDescription);
            pushAccumulator(comment);
            spilled = true;
            lastValueDescription = null;
            valueInAccumulatorImportant=false;
        }
        return spilled;
    }

    @Override
    public void indirection()
    {
        SlotTarget indirect = ACCUMULATOR.indirect();
		output( new AssemblyCommand(OpCode.SET , ACCUMULATOR , indirect ) );
        setAccumulatorLoaded("with indirection");
    }

    @Override
    public void load(LabelDefinition value, ASTNode node)
    {
        load(new MemoryTarget(value.identifier,false),node);
    }
    
    protected void load(SlotMapping mapping,String... comment) {
        load(mapping.target,null,comment);
    }
    
    public void loadAddress(ASTNode node,String... comments) 
    {
        spill("backup acc. before loading address of "+node);
        if ( isFunctionParameter( node ) ) 
        {
        	VariableSymbol sym = (VariableSymbol) node;
            final ParameterDeclaration param = (ParameterDeclaration) sym.getDefinitionSite();
            final int index = 2+ param.getFunctionDeclaration().getParameterIndex( param.getName() );
            
            /*
             * Stack layout at start of method (see onFunctionEntry)
             * 
             * func(a,b,c);
             * 
             * +-------------------------+
             * |    b                    | SP+3
             * +-------------------------+
             * |    a                    | SP+2
             * +-------------------------+
             * |  return address         | SP+1
             * +-------------------------+
             * |  caller's frame pointer | <= SP
             * +-------------------------+
             */
            
            output( new AssemblyCommand(OpCode.SET , ACCUMULATOR , FRAME_POINTER ) );
            output( new AssemblyCommand(OpCode.ADD , ACCUMULATOR , new ConstantValueTarget(index ) ) );

        } else {
        	SlotTarget target = slotTarget( node , true );
        	output( new AssemblyCommand(OpCode.SET , ACCUMULATOR , target ).withComment(comments) );
        }
        setAccumulatorLoaded("load( effective address "+node+" ) , comment: "+extract(comments) );
    }
    
    protected void load(SlotTarget source,ASTNode node,String... comment) 
    {
        spill("backup acc. before loading "+source+" : "+extract(comment));
        output( new AssemblyCommand(OpCode.SET , ACCUMULATOR , source ).withComment(comment) );
        setAccumulatorLoaded("load(SlotTarget "+source+" , comment: "+extract(comment)+", node: "+node);
    }
    
    private static String extract(String...comment) 
    {
        List<String> s=new ArrayList<>();
        if ( comment != null && comment.length > 0 ) {
            s = Arrays.<String>asList( comment );
        }
        return s.toString();
    }

    @Override
    public SlotMapping getHandle(ASTNode node)
    {
        return new SlotMapping(slotTarget(node , false  ) );
    }
    
    @Override
    public void load(SlotMapping mapping, ASTNode node,String... comment) {
    	load( mapping.target , node, comment  );
    }

    @Override
    public void load(ASTNode node,String... comments)
    {
        load( slotTarget(node , false ) , node , comments );
    }

    @Override
    public void loadIndirect(String... comments) 
    {
        SlotTarget indirect = ACCUMULATOR.indirect();
		output( new AssemblyCommand(OpCode.SET , ACCUMULATOR , indirect ) );
        setAccumulatorLoaded("with indirection");
    }
    
    protected abstract String createLocalLabel(IScope scope);
    
    protected abstract String getLabel(ISymbol symbol);    

    private boolean isFunctionParameter(ASTNode node) {
    	if ( node instanceof VariableSymbol) {
            return ((VariableSymbol) node).getDefinitionSite() instanceof ParameterDeclaration;
    	}
    	return false;
    }
    
    protected SlotTarget slotTarget(ASTNode node,boolean loadAddress) 
    {
        if ( node instanceof NumberLiteralNode) {
            return new ConstantValueTarget( ((NumberLiteralNode) node).getValue());
        } 
        else if ( node instanceof StringLiteralNode ) 
        {
            StringLiteralNode str = (StringLiteralNode) node;
            if ( str.isSingleCharacter() ) {
                return new ConstantValueTarget( str.getValue().charAt(0) );
            }
            String label = createLocalLabel(scope);
            outputValues( new LabelDefinition(label), Collections.singletonList( ((StringLiteralNode) node).getValue() ) );
            return new MemoryTarget( label ,  false );
        } 
        else if ( node instanceof VariableSymbol ) 
        {
            VariableSymbol sym = (VariableSymbol) node;
            
            // local variable or function parameter
            if ( isFunctionParameter( sym ) )
            {
                final ParameterDeclaration param = (ParameterDeclaration) sym.getDefinitionSite();
                int index = param.getFunctionDeclaration().getParameterIndex( param.getName() );
                /*
                 * Stack layout at start of method (see onFunctionEntry)
                 * 
                 * func(a,b,c);
                 * 
                 * +-------------------------+
                 * |    b                    | SP+3
                 * +-------------------------+
                 * |    a                    | SP+2
                 * +-------------------------+
                 * |  return address         | SP+1
                 * +-------------------------+
                 * |  caller's frame pointer | <= SP
                 * +-------------------------+
                 */
                 return new FunctionParameterTarget( index+2 );
            }
            // local variable or global variable definition
            return new MemoryTarget( getLabel( sym ) ,  ! loadAddress );
        }
        throw new RuntimeException("Unhandled node: "+node);
    }

    @Override
    public void close()
    {
        flushCommandQueue();
        writer.flush();
    }

    @Override
    public void pushValue(SlotMapping mapping,String... comment)
    {
        output( new AssemblyCommand(OpCode.SET , "PUSH" , mapping.target ).withComment( comment ) );
    }
    
    @Override
    public void pushValue(String... comment)
    {
        output( new AssemblyCommand(OpCode.SET , "PUSH" , ACCUMULATOR ).withComment( comment ) );
    }    

    @Override
    public SlotMapping popValueToAux(String... comment)
    {
        output( new AssemblyCommand(OpCode.SET , AUX_REGISTER , "POP" ).withComment( comment ) );
        return new SlotMapping(AUX_REGISTER);
    }

    @Override
    public void popValues(int count, String... comment)
    {
        output( new AssemblyCommand(OpCode.ADD , SP , new ConstantValueTarget(count) ).withComment(comment) );        
    }

    @Override
    public void jumpTo(SlotMapping mapping, String... comment)
    {
        output( new AssemblyCommand(OpCode.SET , PC , mapping.target ).withComment(comment) );          
    }

    @Override
    public void jumpTo(LabelReference label, String... comment)
    {
        output( new AssemblyCommand(OpCode.SET , PC , label.identifier ).withComment(comment) );          
    }

    @Override
    public void returnFromSubroutine(String... comment)
    {
        output( new AssemblyCommand(OpCode.SET , PC , "POP" ).withComment(comment) );           
    }

    @Override
    public void storeValue(SlotMapping destination, String... comment)
    {
        if ( destination.target instanceof MemoryTarget ) {
            output( new AssemblyCommand(OpCode.SET , destination.target.indirect() , ACCUMULATOR ).withComment(comment) );
        } else {
            output( new AssemblyCommand(OpCode.SET , destination.target , ACCUMULATOR ).withComment(comment) );
        }
    }
    
    @Override
    public void popValue(SlotMapping destination, String... comment)
    {
        if ( destination.target instanceof MemoryTarget ) {
            output( new AssemblyCommand(OpCode.SET , destination.target.indirect() , "POP" ).withComment(comment) );
        } else {
            output( new AssemblyCommand(OpCode.SET , destination.target , "POP" ).withComment(comment) );
        }
    }    
    

    @Override
    public void popValueIndirect(String... comment)
    {
        output( new AssemblyCommand(OpCode.SET , ACCUMULATOR.indirect() , "POP" ).withComment(comment) );   
    }
    
    @Override
    public void popValueIndirect(SlotMapping destination,String... comment)
    {
        if ( destination.target instanceof RegisterTarget) {
            if ( ((RegisterTarget) destination.target).isIndirect() )
            {
                load( destination, comment );
            }
        } else {
            load( destination, comment );
        }
        output( new AssemblyCommand(OpCode.SET , destination.target.indirect() , "POP" ).withComment(comment) );         
    }
    
    @Override
    public void storeValueIndirect(SlotMapping destination,String... comment)
    {
        load( destination, comment );
        output( new AssemblyCommand(OpCode.SET , destination.target.indirect() , ACCUMULATOR ).withComment(comment) );         
    }    
    

    @Override
    public void defineCodeLabel(LabelDefinition def, String... comment)
    {
        flushCommandQueue();
        writer.codeSegment();
        writer.write( def.identifier+":\n" );
    }
    
    @Override
    public void defineDataLabel(LabelDefinition def, String... comment)
    {
        writer.dataSegment();
        if ( comment == null || comment.length == 0 ) {
            writer.write(" ; "+comment[0]+"\n");
        } 
        writer.write( def.identifier+":\n" );
    }    

    @Override
    public void bitwiseOr(String... comment)
    {
        output( new AssemblyCommand(OpCode.BOR , ACCUMULATOR , "POP") );
    }

    @Override
    public void multiply(String... comment)
    {
        output( new AssemblyCommand(OpCode.MUL , ACCUMULATOR , "POP" ) );
    }

    @Override
    public void add(String... comment)
    {
        output( new AssemblyCommand(OpCode.ADD , ACCUMULATOR , "POP" ) );
    }

    @Override
    public void subtract(String... comment)
    {
        output( new AssemblyCommand(OpCode.SUB , ACCUMULATOR , "POP" ) );
    }

    @Override
    public void outputCommentLine(String comment)
    {
        writer.write( "; "+comment+"\n" );
    }

    @Override
    public void halt(String... comment)
    {
        output(new AssemblyCommand(OpCode.HALT,"0" ) );
    }

    @Override
    public void branchOnBoolean(LabelReference falseLabel, String... comment)
    {
        output(new AssemblyCommand(OpCode.IFE, ACCUMULATOR , "0" ).withComment(comment) );
        jumpTo( falseLabel , comment);
    }

    @Override
    public void compareEqualsWithResult(String... comment)
    {
        compareNotEqual( comment );
        internalCompareWithResult(comment);
    }
    
    private SlotMapping internalCompareWithResult(String...comment) 
    {
        return internalCompareWithResult(null,comment);
    }
    
    private SlotMapping internalCompareWithResult(LabelDefinition userProvidedFalseBranch, String...comment) 
    {
        /*
         * Input comparison is assumed to be ALWAYS negated.
         */
        final String falseLabel=createLocalLabel( scope );
        final String continueLabel=createLocalLabel( scope );
        
        // jump to "false" branch
        output(new AssemblyCommand(OpCode.SET, PC , falseLabel ).withComment(comment) );
        
        // "true" branch
        output(new AssemblyCommand(OpCode.SET, ACCUMULATOR , new ConstantValueTarget(0) ).withComment(comment) );
        output(new AssemblyCommand(OpCode.SET, PC , continueLabel ).withComment(comment) );
        
        // "false" branch
        defineCodeLabel(new LabelDefinition(falseLabel));
        output(new AssemblyCommand(OpCode.SET, ACCUMULATOR , new ConstantValueTarget(1) ).withComment(comment) );
        if ( userProvidedFalseBranch != null ) 
        {
            output(new AssemblyCommand(OpCode.SET, PC , userProvidedFalseBranch.identifier ).withComment(comment) );
        }
        
        // continue branch
        defineCodeLabel(new LabelDefinition(falseLabel));
        
        return new SlotMapping(ACCUMULATOR);
    }

    @Override
    public void compareGreaterThanWithResult(String... comment)
    {
        throw new UnsupportedOperationException("");
    }

    @Override
    public void compareLessThanWithResult(String... comment)
    {
        compareGreaterThanEqualWithResult(null,comment);
    }

    @Override
    public void compareGreaterThanEqual(LabelReference falseLabel,String... comment)
    {
        throw new UnsupportedOperationException("");
    }

    @Override
    public void compareGreaterThanEqualWithResult(LabelReference falseLabel, String... comment)
    {
        throw new UnsupportedOperationException("");
    }    

    @Override
    public void compareNotEqualWithResult(String... comment)
    {
        compareEquals(comment );
        internalCompareWithResult(comment);
    }

    @Override
    public void compareLessThanEqual(LabelReference falseLabel,String... comment)
    {
        // <= negated is > 
        compareLessThan( comment );
        
        internalCompareWithResult( comment );
        
        final LabelDefinition trueLabel = new LabelDefinition( createLocalLabel( scope ) );
        // true case
        jumpTo( trueLabel.ref() );
        
        // false case
        jumpTo(falseLabel,comment);
        defineCodeLabel( trueLabel , comment );
    }

    @Override
    public void compareLessThanEqualWithResult(LabelReference falseLabel, String... comment)
    {
        // <= negated is > 
        compareGreaterThan( comment );
        
        SlotMapping result= internalCompareWithResult( comment );
        
        final LabelDefinition trueLabel = new LabelDefinition( createLocalLabel( scope ) );
        // true case
        jumpTo( trueLabel.ref() );
        
        // false case
        jumpTo(falseLabel,comment);
        defineCodeLabel( trueLabel , comment );
    }

    @Override
    public void compareEquals(String... comment)
    {
        output(new AssemblyCommand(OpCode.IFE, ACCUMULATOR , "POP" ).withComment(comment) );        
    }

    @Override
    public void compareNotEqual(String... comment)
    {
        output(new AssemblyCommand(OpCode.IFN, ACCUMULATOR , "POP" ).withComment(comment) );          
    }

    @Override
    public void compareLessThan(String... comment)
    {
        output(new AssemblyCommand(OpCode.IFL, ACCUMULATOR , "POP").withComment(comment) );             
    }

    @Override
    public void compareGreaterThan(String... comment)
    {
        output(new AssemblyCommand(OpCode.IFG, ACCUMULATOR , "POP" ).withComment(comment) );           
    }

    @Override
    public void outputInlineAssembly(InlineAssemblyNode node)
    {
        String code = node.getCode();
        writer.codeSegment();
        writer.write( code );
    }

    @Override
    public void outputValues(LabelDefinition label, List<String> values, String... comment)
    {
        if ( values == null || values.isEmpty() ) {
            throw new IllegalArgumentException("values must not be NULL/empty.");
        }
        
        final StringBuffer result=new StringBuffer();
        if ( comment != null && comment.length > 0 ) 
        {
            result.append( "; "+comment[0]+"\n" );
        }
        if ( label != null ) {
            result.append( label.identifier+":\n" );
        }
        
        result.append("DAT ");
        for (Iterator<String> it = values.iterator(); it.hasNext();) {
            String s = it.next();
            result.append( s );
            if ( it.hasNext() ) 
            {
                result.append(" , ");
            }
        }
        writer.dataSegment();
        writer.write( result.toString() );
        writer.write("\n");
        writer.codeSegment();
    }
    
    public void reserveUninitializedMemory(LabelDefinition label,int wordCount,String... comment) 
    {
        if ( label == null ) {
            throw new IllegalArgumentException("label must not be NULL.");
        }
        if ( wordCount < 0 ) {
            throw new IllegalArgumentException("Wordcount must be positive");
        }
        
        writer.dataSegment();
        if ( comment != null && comment.length > 0 ) 
        {
            writer.write( "; "+comment[0]+"\n" );
        }        
        writer.write( label.identifier+":\n");
        writer.write("RESERVE "+wordCount+"\n");
        writer.codeSegment();
    }
    
    public void onFunctionEntry(String... comment) {
        // SET FRAME_POINTER , SP
        output( new AssemblyCommand(OpCode.SET, "PUSH", FRAME_POINTER ).withComment("backup caller frame pointer") );        
        output( new AssemblyCommand(OpCode.SET, FRAME_POINTER , SP ).withComment("setup our frame pointer") );
    }
    
    public void beforeFunctionExit(String... comment) {
        output( new AssemblyCommand(OpCode.SET, FRAME_POINTER , "POP" ).withComment("restore caller's frame pointer") );
        flushCommandQueue();
        writer.flushCodeSegment();
    }
}