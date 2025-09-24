package de.codesourcery.dcpu16.codegeneration;

import de.codesourcery.dcpu16.util.StringInterpolator;

public class CPUModel
{
    public static final boolean INSTRUCTION_QUEUE_DISABLED = false;
    
    public static final boolean DEBUG_PEEPHOLE = false;
    
    protected final RegisterTarget FRAME_POINTER = new RegisterTarget(Register.Z);

    protected final RegisterTarget ACCUMULATOR = new RegisterTarget(Register.A);

    protected final RegisterTarget AUX_REGISTER = new RegisterTarget(Register.B);

    protected final RegisterTarget PC = new RegisterTarget(Register.PC);

    protected final RegisterTarget SP = new RegisterTarget(Register.SP);

    private final RegisterTarget[] AVAILABLE_REGISTERS = { new RegisterTarget(Register.C),
            new RegisterTarget(Register.X),
            new RegisterTarget(Register.Y),
            new RegisterTarget(Register.B)};

    private IAssemblyWriter writer;

    protected static enum OpCode 
    {
        SET("SET"),
        SUB("SUB"),
        ADD("ADD"),
        MUL("MUL"),
        DIV("DIV"),        
        BOR("BOR"),
        HALT("HCF"),
        // comparison;
        IFN("IFN"),
        IFE("IFE"),
        IFG("IFG"),
        IFL("IFL");


        private final String op;

        private OpCode(String op)
        {
            this.op = op;
        }

        @Override
        public String toString()
        {
            return op;
        }
    }

    public CPUModel(IAssemblyWriter writer)
    {
        this.writer = writer;
    }

    protected final void pushAccumulator(String... comment) 
    {
        // SET PUSH , currentRegister
        output( new AssemblyCommand( OpCode.SET , "PUSH" , ACCUMULATOR ).withComment(comment) );
    }

    protected final class AssemblyCommand 
    {
    	protected final OpCode mnemonic;
        
        protected final String operand1;
        protected SlotTarget operand1Target;
        
        protected final String operand2;
        protected SlotTarget operand2Target;
        
        private String comment;

        protected AssemblyCommand(OpCode opcode)
        {
            this(opcode,(String)null);
        }
        
        public boolean isCommand(OpCode code) {
            return code.equals( mnemonic );
        }

        public AssemblyCommand(OpCode opcode, SlotTarget operand1)
        {
            this(opcode,operand1,(SlotTarget) null);
            operand1Target = operand1;
        }        

        public AssemblyCommand(OpCode opcode, String operand1)
        {
            this(opcode,operand1,(String)null);
        }   

        protected AssemblyCommand(OpCode mnemonic,String operand1, SlotTarget operand2)
        {        
            this(mnemonic,operand1,operand2 != null ? operand2.toOperandString() : null);
            operand2Target = operand2;
        }

        protected AssemblyCommand(OpCode mnemonic,SlotTarget operand1, String operand2)
        {        
            this(mnemonic,operand1 != null ? operand1.toOperandString() : null ,operand2 );
            operand1Target = operand1;
        }        

        protected AssemblyCommand(OpCode mnemonic,SlotTarget operand1, SlotTarget operand2)
        {
            this(mnemonic, operand1 != null ? operand1.toOperandString() : null , operand2 != null ? operand2.toOperandString() : null );
            this.operand1Target = operand1;
            operand2Target = operand2;
        }

        public AssemblyCommand withComment(String... comment)
        {
            this.comment = comment != null && comment.length > 0 ? comment[0] : null;
            return this;
        }

        protected AssemblyCommand(OpCode mnemonic,String operand1, String operand2)
        {
            this.mnemonic = mnemonic;
            this.operand1 = operand1;
            this.operand2 = operand2;
        }        

        @Override
        public String toString()
        {
            final String s;
            if ( operand1 == null ) {
                s = mnemonic.toString();
            } else if ( operand2 == null ) {
                s = StringInterpolator.interpolate( mnemonic+" ${0}" , operand1);
            } else  {
                s = StringInterpolator.interpolate( mnemonic+" ${0} , ${1}" , operand1 , operand2 );            
            }
            return comment == null ? s : s+" ; "+comment;
        }
    }

    public static class SlotMapping
    {
        public final SlotTarget target;

        public SlotMapping(SlotTarget target)
        {
            this.target = target;
        }
    }    

    protected abstract class SlotTarget {

        public abstract String toOperandString();

        public abstract SlotTarget indirect();
        
        public abstract boolean isIndirect();

        @Override
        public String toString()
        {
            return toOperandString();
        }        
    }

    protected final class RegisterTarget extends SlotTarget 
    {
        private final Register register;
        private final boolean indirect;

        public RegisterTarget(Register register)
        {
            this(register,false);
        }
        
        public boolean equals(Object o) 
        {
        	if ( this == o ) {
        		return true;
        	}
        	if ( o instanceof RegisterTarget) {
        		RegisterTarget other = (RegisterTarget) o;
        		return this.register == other.register && this.indirect == other.indirect;
        	}
        	return false;
        }
        
        public Register getRegister() {
			return register;
		}

        public RegisterTarget(Register register,boolean indirect)
        {
            this.register = register;
            this.indirect = indirect;
        }

        @Override
        public String toOperandString()
        {
            if ( indirect ) {
                return "["+register.getIdentifier()+"]";
            }
            return register.getIdentifier();
        }

        @Override
        public SlotTarget indirect()
        {
            if ( indirect ) {
                return this;
            }
            return new RegisterTarget(register,true);
        }

        public boolean isIndirect()
        {
            return indirect;
        }
    }

    protected final class MemoryTarget extends SlotTarget 
    {
        private final String label;
        private final boolean indirect;

        public MemoryTarget(String label,boolean indirect)
        {
            this.label = label;
            this.indirect = indirect;
        }
        
        @Override
        public boolean isIndirect() {
            return indirect;
        }

        @Override
        public String toOperandString()
        {
            if ( indirect ) {
                return "["+label+"]";
            }
            return label;
        }        

        @Override
        public SlotTarget indirect()
        {
            if ( indirect ) {
                return this;
            }
            return new MemoryTarget(label,true);
        }
    }    

    protected class FunctionParameterTarget extends SlotTarget 
    {
        private final int index; // index relative to FRAME_POINTER

        public FunctionParameterTarget(int index)
        {
            this.index=index;
        }
        
        @Override
        public boolean isIndirect() {
            return true;
        }        

        @Override
        public String toOperandString()
        {
            final String reg = FRAME_POINTER.toOperandString();
            if ( index > 0 ) 
            {
                return "["+reg+"+"+index+"]";                
            } 
            if ( index < 0 ) {
                return "["+reg+"-"+(-index)+"]";
            }
            return "["+reg+"]";
        }        

        @Override
        public SlotTarget indirect()
        {
            return this;
        }
    }

    protected final class ConstantValueTarget extends SlotTarget 
    {
        private final long value;

        public ConstantValueTarget(long value)
        {
            this.value=value;
        }

        @Override
        public boolean isIndirect() {
            throw new UnsupportedOperationException("Literal value");
        }
        
        @Override
        public String toOperandString()
        {
            return Long.toString(value);
        }        

        @Override
        public SlotTarget indirect()
        {
            throw new UnsupportedOperationException("Indirection not possible for constant value "+this);
        }
    }     
    
    private AssemblyCommand queue;
    
    protected final void flushCommandQueue() 
    {
        if ( queue != null ) 
        {
            output( queue );
            queue = null;
        }
    }
    
    protected final void output(AssemblyCommand currentCmd) 
    {
        if ( INSTRUCTION_QUEUE_DISABLED ) {
            internalOutput(currentCmd);
            return;
        }
        
        // discard SET A , A etc.
        if ( currentCmd.isCommand( OpCode.SET )  && equals( currentCmd.operand1 , currentCmd.operand2) ) { 
             return;
        }        
        
        if ( queue == null ) {
            queue = currentCmd;
            return;
        }
        
        /*
         * Combine:
         * 
         * SET A , _GLOBAL__print_block_1_ptr ; loading EA , in assignment
         * SET A , [A]
         * 
         * into
         * 
         * SET A , [_GLOBAL__print_block_1_ptr] ; loading EA , in assignment
         */
        if ( currentCmd.isCommand( OpCode.SET ) && queue.isCommand( OpCode.SET ) ) 
        {
        	if ( isRegisterIndirection( currentCmd ) && isLoadAddress( queue ) )
        	{
        		final MemoryTarget oldTarget = (MemoryTarget) queue.operand2Target;
        		final MemoryTarget newTarget = new MemoryTarget( oldTarget.label , true );
                queue = new AssemblyCommand( OpCode.SET , currentCmd.operand1Target , newTarget ).withComment( currentCmd.comment );
                return;
        	}
        }
        
        /*
         * Combine:
         * 
         * SET $2 , $1
         * SET $3 , $2
         * 
         * into
         * 
         * SET $3 , $1
         * 
         * TODO: This will break stuff if something relies on the values availability in A 
         */
        if ( currentCmd.isCommand( OpCode.SET ) && queue.isCommand( OpCode.SET ) ) 
        {
            final String loadSource = queue.operand2;
            final String loadDestination = queue.operand1;
            final String storeSource = currentCmd.operand2;
            if ( equals( storeSource, loadDestination ) ) 
            {
                final String storeDestination = currentCmd.operand1;                
                AssemblyCommand newCmd = new AssemblyCommand( OpCode.SET , storeDestination , loadSource ).withComment( currentCmd.comment );
                if ( DEBUG_PEEPHOLE ) {
                    System.err.println("; PEEPHOLE: "+queue+" + "+currentCmd+" => "+newCmd);
                }
                queue = newCmd;
                return;
            }
        } 
        
        internalOutput( queue );
        queue = currentCmd;
    }
    
    private static boolean isRegisterIndirection(AssemblyCommand currentCmd) 
    {
		if ( currentCmd.isCommand( OpCode.SET) &&
			 currentCmd.operand1Target instanceof RegisterTarget &&
			 currentCmd.operand2Target instanceof RegisterTarget ) 
		{
			RegisterTarget dest = (RegisterTarget) currentCmd.operand1Target;
			RegisterTarget source = (RegisterTarget) currentCmd.operand2Target;
			return source.getRegister() == dest.getRegister() &&
				   source.isIndirect() && ! dest.isIndirect();
		}
		return false;
    }
    
    private static boolean isLoadAddress(AssemblyCommand currentCmd) 
    {
		if ( currentCmd.isCommand( OpCode.SET) &&
			 currentCmd.operand1Target instanceof RegisterTarget &&
			 currentCmd.operand2Target instanceof MemoryTarget ) 
		{
			RegisterTarget dest = (RegisterTarget) currentCmd.operand1Target;
			MemoryTarget source = (MemoryTarget) currentCmd.operand2Target;
			return ! dest.isIndirect() && ! source.isIndirect();
		}
		return false;
    }    
    
    private final void internalOutput(AssemblyCommand cmd) 
    {
        writer.codeSegment();
        writer.write( cmd.toString()+"\n" );
    }

    private static boolean equals(String s1,String s2) 
    {
        if ( s1 == null || s2 == null ) {
            return s1 == s2;
        }
        return s1.equals(s2);
    }

}