package de.codesourcery.dcpu16.compiler;

import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.NumberLiteralNode;
import de.codesourcery.dcpu16.ast.TermNode;
import de.codesourcery.dcpu16.util.ITextRegion;
import de.codesourcery.dcpu16.util.TextRegion;


public enum Operator
{   
    // see http://www.difranco.net/compsci/C_Operator_Precedence_Table.htm
    // max. precedence = 31
    PLUS("+",12) {

        @Override
        public boolean internalSupportsTypes(DataType type1, DataType... types)
        {
        	final DataType type2 = types[0];
        	
        	if ( type1.isArray() || type2.isArray() ) {
        		return false;
        	}
        	
        	if ( type1.isPtr() && type2.isPtr() ) 
        	{
        		// ptr + ptr
        		if ( type1.getPtrCount() != type2.getPtrCount() ) {
        			return false;
        		}
        		return type1.getBaseType().equals( type2.getBaseType() );
        	} 
        	else if ( type1.isPtr() && ! type2.isPtr() ) {
        		// ptr + int
        		return type2.getBaseType().isIntegerType();
        	} else if ( ! type1.isPtr() && type2.isPtr() ) {
        		// int + ptr
        		return type1.getBaseType().isIntegerType();
        	}
            return type1.getBaseType().isIntegerType() && type2.getBaseType().isIntegerType();
        }
        
        @Override
        public DataType internalInferType(DataType type1, DataType type2)
        {
            if ( ! supportsTypes( type1,type2 ) ) {
                return DataType.UNKNOWN;
            }
            if ( type1.isPtr() ) {
                return type1;
            } else if ( type2.isPtr() ) {
                return type2;
            }
            return DataType.INTEGER;
        }
        
        @Override
        public boolean isCommutative() {
            return true;
        }        

        @Override
        protected TermNode internalApply(TermNode operand1, TermNode... operands)
        {
            final TermNode value1 = operand1.reduce(  );
            final TermNode value2 = operands[0].reduce(  );
            
            if ( canCalculate( value1,value2 ) )
            {
                final Long l1 = (Long) DataType.INTEGER.extractValue( value1 );
                final Long l2 = (Long) DataType.INTEGER.extractValue( value2 );
                
                final ITextRegion r= new TextRegion( value1.getTextRegion() );
                r.merge( value2.getTextRegion() );
                return new NumberLiteralNode( l1 + l2 , r );
            }
            return null;
        }
    },
    ARRAY_SUBSCRIPT("[]",15) {

    	@Override
    	public boolean mayBeParsedByLexer() {
    		return false;
    	}
    	
		@Override
		protected boolean internalSupportsTypes(DataType type1, DataType... types) 
		{
			return type1.isArray() || type1.isPtr();
		}
		
		@Override
		public int getOperandCount() {
			return 2;
		}
		
		@Override
		public boolean isLeftAssociative() {
			return true;
		}

        @Override
        protected TermNode internalApply(TermNode operand1, TermNode... operands) 
        {
            final DataType type = operand1.getDataType();
            if ( ! type.isArray() && ! type.isPtr() ) {
                return null;
            }
            
            final DataType dereferencedType = internalInferType(operand1.getDataType(),operands[0].getDataType() );
            if ( ! dereferencedType.isLValue() ) {
                throw new IllegalArgumentException("Cannot de-reference lvalue "+operand1);
            }           
            final TermNode copy = (TermNode) operand1.createCopy(true);
            copy.setDataType( dereferencedType );
            copy.setParent( operand1.getParent() ); // link to old parent so code that traverses the AST upwards looking for scope definitions doesn't choke
            return copy;
        }

        @Override
        protected DataType internalInferType(DataType type1, DataType argType2)
        {
            if ( type1.isArray() ) {
                return DataType.getDataType( type1.getIdentifier(),false, type1.getPtrCount() );
            }
            if ( type1.isPtr() ) {
                return DataType.getDataType( type1.getIdentifier(),false, type1.getPtrCount()-1 );                
            }
            return DataType.UNKNOWN;
        }		
    },   
    BITWISE_OR("|",6) {

    	@Override
    	public boolean mayBeParsedByLexer() {
    		return true;
    	}
    	
		@Override
		protected boolean internalSupportsTypes(DataType type1, DataType... types) 
		{
			// TODO: This needs to check the effective type after &* etc. 
			return type1.isBasicType() && types[0].isBasicType()
				   && type1.isIntegerType() && types[0].isIntegerType();			
		}
		
		@Override
		protected DataType internalInferType(DataType argType1, DataType argType2) {
			return DataType.INTEGER;
		}

		@Override
		protected TermNode internalApply(TermNode operand1, TermNode... operands) 
		{
            final TermNode value1 = operand1.reduce(  );
            final TermNode value2 = operands[0].reduce(  );
            
            if ( canCalculate( value1,value2 ) )
            {
                final Long l1 = (Long) DataType.INTEGER.extractValue( value1 );
                final Long l2 = (Long) DataType.INTEGER.extractValue( value2 );
                
                final ITextRegion r= new TextRegion( value1.getTextRegion() );
                r.merge( value2.getTextRegion() );
                return new NumberLiteralNode( l1 | l2 , r );
            }
            return null;
		}
    },    
    BITWISE_NOT("~",14) {

    	@Override
    	public boolean mayBeParsedByLexer() {
    		return true;
    	}
    	
		@Override
		protected boolean internalSupportsTypes(DataType type1, DataType... types) 
		{
			return type1.isBasicType()&& type1.isIntegerType();
		}
		
		@Override
		public int getOperandCount() {
			return 1;
		}
		
		@Override
		public boolean isLeftAssociative() {
			return false;
		}
		
		@Override
		protected DataType internalInferType(DataType argType1, DataType argType2) {
			return DataType.INTEGER;
		}

		@Override
		protected TermNode internalApply(TermNode operand1, TermNode... operands) 
		{
            final TermNode value1 = operand1.reduce(  );
            
            if ( canCalculate( value1) )
            {
                final Long l1 = (Long) DataType.INTEGER.extractValue( value1 );
                
                final ITextRegion r= new TextRegion( value1.getTextRegion() );
                final long result = ~l1; 
                return new NumberLiteralNode( result , r );
            }
            return null;
		}
    },     
    LOGICAL_NOT("!",14) {

    	@Override
    	public boolean mayBeParsedByLexer() {
    		return true;
    	}
    	
		@Override
		protected boolean internalSupportsTypes(DataType type1, DataType... types) 
		{
			return type1.isBasicType()&& type1.isIntegerType();
		}
		
		@Override
		public int getOperandCount() {
			return 1;
		}
		
		@Override
		public boolean isLeftAssociative() {
			return false;
		}
		
		@Override
		protected DataType internalInferType(DataType argType1, DataType argType2) {
			return DataType.INTEGER;
		}

		@Override
		protected TermNode internalApply(TermNode operand1, TermNode... operands) 
		{
            final TermNode value1 = operand1.reduce(  );
            
            if ( canCalculate( value1) )
            {
                final Long l1 = (Long) DataType.INTEGER.extractValue( value1 );
                
                final ITextRegion r= new TextRegion( value1.getTextRegion() );
                long result = 1; 
                if ( l1 != 0 ) {
                	result = 0;
                } 
                return new NumberLiteralNode( result , r );
            }
            return null;
		}
    },     
    LOGICAL_OR("||",4) {

    	@Override
    	public boolean mayBeParsedByLexer() {
    		return true;
    	}
    	
		@Override
		protected boolean internalSupportsTypes(DataType type1, DataType... types) 
		{
			// TODO: This needs to check the effective type after &* etc. 
			return type1.isBasicType() && types[0].isBasicType()
				   && type1.isIntegerType() && types[0].isIntegerType();			
		}
		
		@Override
		protected DataType internalInferType(DataType argType1, DataType argType2) {
			return DataType.INTEGER;
		}

		@Override
		protected TermNode internalApply(TermNode operand1, TermNode... operands) 
		{
            final TermNode value1 = operand1.reduce(  );
            final TermNode value2 = operands[0].reduce(  );
            
            if ( canCalculate( value1,value2 ) )
            {
                final Long l1 = (Long) DataType.INTEGER.extractValue( value1 );
                final Long l2 = (Long) DataType.INTEGER.extractValue( value2 );
                
                final ITextRegion r= new TextRegion( value1.getTextRegion() );
                r.merge( value2.getTextRegion() );
                long result = 0; 
                if ( l1 != 0 || l2 != 0 ) {
                	result = 1;
                } 
                return new NumberLiteralNode( result , r );
            }
            return null;
		}
    },      
    LOGICAL_AND("&&",5) {

    	@Override
    	public boolean mayBeParsedByLexer() {
    		return false;
    	}
    	
		@Override
		protected boolean internalSupportsTypes(DataType type1, DataType... types) 
		{
			return type1.isBasicType() && types[0].isBasicType()
				   && type1.isIntegerType() && types[0].isIntegerType();			
		}
		
		@Override
		protected DataType internalInferType(DataType argType1, DataType argType2) {
			return DataType.INTEGER;
		}

		@Override
		protected TermNode internalApply(TermNode operand1, TermNode... operands) 
		{
            final TermNode value1 = operand1.reduce(  );
            final TermNode value2 = operands[0].reduce(  );
            
            if ( canCalculate( value1,value2 ) )
            {
                final Long l1 = (Long) DataType.INTEGER.extractValue( value1 );
                final Long l2 = (Long) DataType.INTEGER.extractValue( value2 );
                
                final ITextRegion r= new TextRegion( value1.getTextRegion() );
                r.merge( value2.getTextRegion() );
                long result = 0; 
                if ( l1 != 0 && l2 != 0 ) {
                	result = 1;
                } 
                return new NumberLiteralNode( result , r );
            }
            return null;
		}
    },     
    BITWISE_AND("&",8) {

    	@Override
    	public boolean mayBeParsedByLexer() {
    		return false;
    	}
    	
		@Override
		protected boolean internalSupportsTypes(DataType type1, DataType... types) 
		{
			return type1.isBasicType() && types[0].isBasicType() && type1.isIntegerType() && types[0].isIntegerType();			
		}
		
		@Override
		protected DataType internalInferType(DataType argType1, DataType argType2) {
			return DataType.INTEGER;
		}

		@Override
		protected TermNode internalApply(TermNode operand1, TermNode... operands) 
		{
            final TermNode value1 = operand1.reduce(  );
            final TermNode value2 = operands[0].reduce(  );
            
            if ( canCalculate( value1,value2 ) )
            {
                final Long l1 = (Long) DataType.INTEGER.extractValue( value1 );
                final Long l2 = (Long) DataType.INTEGER.extractValue( value2 );
                
                final ITextRegion r= new TextRegion( value1.getTextRegion() );
                r.merge( value2.getTextRegion() );
                return new NumberLiteralNode( l1 & l2 , r );
            }
            return null;
		}
    },      
    ADDRESS_OF("&",14) {

    	@Override
    	public boolean mayBeParsedByLexer() {
    		return false;
    	}
    	
		@Override
		protected boolean internalSupportsTypes(DataType type1, DataType... types) 
		{
			// TODO: Implement me properly
			return ! type1.isPtr();
		}

		@Override
		public int getOperandCount() {
			return 1;
		}
		
		@Override
		public boolean isLeftAssociative() {
			return false;
		}

		@Override
		protected DataType internalInferType(DataType argType1, DataType argType2) {
			// TODO: Implement me properly
			return argType1.dereference();
		}

		@Override
		protected TermNode internalApply(TermNode operand1, TermNode... operands) 
		{
			// TODO: Implement me properly
			return operand1;
		}
    },    
    MULTIPLY("*",13) {

    	@Override
    	public boolean mayBeParsedByLexer() {
    		return false;
    	}
    	
		@Override
		protected boolean internalSupportsTypes(DataType type1, DataType... types) 
		{
			return type1.isBasicType() && types[0].isBasicType() && type1.isIntegerType() && types[0].isIntegerType();
		}
		
		@Override
		protected DataType internalInferType(DataType argType1, DataType argType2) {
			return DataType.INTEGER;
		}

		@Override
		protected TermNode internalApply(TermNode operand1, TermNode... operands) 
		{
            final TermNode value1 = operand1.reduce(  );
            final TermNode value2 = operands[0].reduce(  );
            
            if ( canCalculate( value1,value2 ) )
            {
                final Long l1 = (Long) DataType.INTEGER.extractValue( value1 );
                final Long l2 = (Long) DataType.INTEGER.extractValue( value2 );
                
                final ITextRegion r= new TextRegion( value1.getTextRegion() );
                r.merge( value2.getTextRegion() );
                return new NumberLiteralNode( l1 * l2 , r );
            }
            return null;
		}
    },    
    DEREFERENCE("*",14) {

    	@Override
    	public boolean mayBeParsedByLexer() {
    		return false;
    	}
    	
		@Override
		protected boolean internalSupportsTypes(DataType type1, DataType... types) 
		{
			return type1.isPtr();
		}
		
		@Override
		public int getOperandCount() {
			return 1;
		}		
		
		@Override
		public boolean isLeftAssociative() {
			return false;
		}

		@Override
		protected DataType internalInferType(DataType argType1, DataType argType2) {
			return argType1.dereference();
		}

		@Override
		protected TermNode internalApply(TermNode operand1, TermNode... operands) 
		{
		    final DataType type = operand1.getDataType();
		    final DataType dereferencedType = type.dereference();
            if ( ! dereferencedType.isLValue() ) {
                throw new IllegalArgumentException("Cannot de-reference lvalue "+operand1);
            }		    
		    final TermNode copy = (TermNode) operand1.createCopy(true);
		    copy.setDataType( dereferencedType );
		    copy.setParent( operand1.getParent() ); // link to old parent so code that traverses the AST upwards looking for scope definitions doesn't choke
			return copy;
		}
    },
    MINUS("-",12) {
        @Override
        public boolean internalSupportsTypes(DataType type1, DataType... types)
        {
            return type1.equals(DataType.INTEGER) && types[0].equals( DataType.INTEGER );
        }

        @Override
        public DataType internalInferType(DataType argType1, DataType argType2)
        {
            return DataType.INTEGER;
        }        
        
        @Override
        protected TermNode internalApply(TermNode operand1, TermNode... operands)
        {
            final TermNode value1 = operand1.reduce(  );
            final TermNode value2 = operands[0].reduce(  );
            
            if ( canCalculate( value1,value2 ) )
            {
                final Long l1 = (Long) DataType.INTEGER.extractValue( value1 );
                final Long l2 = (Long) DataType.INTEGER.extractValue( value2 );
                
                final ITextRegion r= new TextRegion( value1.getTextRegion() );
                r.merge( value2.getTextRegion() );
                return new NumberLiteralNode( l1 - l2 , r );
            }
            return null;
        }        
    },
    ASSIGNMENT("=",2) 
    {
        @Override
        public boolean internalSupportsTypes(DataType type1, DataType... types)
        {
            if ( ! type1.equals( types[0] ) ) {
                return false;
            }
            if ( type1.equals(DataType.INTEGER) ) {
                return true;
            }
            return false;
        }

        @Override
        public DataType internalInferType(DataType argType1, DataType argType2)
        {
            return argType1;
        }
        
        @Override
        protected TermNode internalApply(TermNode operand1, TermNode... operands)
        {
            final TermNode value = operands[0].reduce( );
            
            if ( value.isLiteralValue() )
            {
                return value;
            }
            return null;
        }         
        
        @Override
        protected TermNode internalReduce(TermNode operand1, TermNode... operands)
        {
            return null;
        }
    },
    NOT_EQUAL("!=",9) 
    {
        @Override
        public boolean internalSupportsTypes(DataType type1, DataType... types)
        {
            return ! type1.equals(DataType.VOID) && 
                    ! types[0].equals( DataType.VOID ) &&
                    ! type1.equals(DataType.UNKNOWN) && 
                    ! types[0].equals( DataType.UNKNOWN );                    
        }
        
        @Override
        public boolean isCommutative() {
            return true;
        }           

        @Override
        public DataType internalInferType(DataType argType1, DataType argType2)
        {
            return DataType.INTEGER;
        }
        
        @Override
        protected TermNode internalApply(TermNode operand1, TermNode... operands)
        {
            final Integer result = compare( operand1 , operands );
            if ( result == null )
            {
                return null;
            }
            
            final ITextRegion r= new TextRegion( operand1.getTextRegion() );
            r.merge( operands[0].getTextRegion() );
            
            return result.intValue() != 0 ?  new NumberLiteralNode( 1 , r ) :  new NumberLiteralNode( 0 , r );                 
        }         
    },    
    EQUALS("==",9) 
    {
        @Override
        public boolean internalSupportsTypes(DataType type1, DataType... types)
        {
            return ! type1.equals(DataType.VOID) && 
                    ! types[0].equals( DataType.VOID ) &&
                    ! type1.equals(DataType.UNKNOWN) && 
                    ! types[0].equals( DataType.UNKNOWN );                    
        }
        
        @Override
        public boolean isCommutative() {
            return true;
        }           

        @Override
        public DataType internalInferType(DataType argType1, DataType argType2)
        {
            return DataType.INTEGER;
        }
        
        @Override
        protected TermNode internalApply(TermNode operand1, TermNode... operands)
        {
            final Integer result = compare( operand1 , operands );
            if ( result == null )
            {
                return null;
            }
            
            final ITextRegion r= new TextRegion( operand1.getTextRegion() );
            r.merge( operands[0].getTextRegion() );
            
            return result.intValue() == 0 ?  new NumberLiteralNode( 1 , r ) :  new NumberLiteralNode( 0 , r );                 
        }         
    },
    LESS_THAN_EQUAL("<=",10) {
        @Override
        public boolean internalSupportsTypes(DataType type1, DataType... types)
        {
            return type1.equals(DataType.INTEGER) && types[0].equals( DataType.INTEGER );
        }

        @Override
        public DataType internalInferType(DataType argType1, DataType argType2)
        {
            return DataType.INTEGER;
        }          
        
        @Override
        protected TermNode internalApply(TermNode operand1, TermNode... operands)
        {
            final Integer result = compare( operand1 , operands );
            if ( result == null )
            {
                return null;
            }
            
            final ITextRegion r= new TextRegion( operand1.getTextRegion() );
            r.merge( operands[0].getTextRegion() );
            
            return result.intValue() <= 0 ?  new NumberLiteralNode( 1 , r ) :  new NumberLiteralNode( 0 , r );                 
        }           
    },    
    LESS_THAN("<",10) {
        @Override
        public boolean internalSupportsTypes(DataType type1, DataType... types)
        {
            return type1.equals(DataType.INTEGER) && types[0].equals( DataType.INTEGER );
        }

        @Override
        public DataType internalInferType(DataType argType1, DataType argType2)
        {
            return DataType.INTEGER;
        }          
        
        @Override
        protected TermNode internalApply(TermNode operand1, TermNode... operands)
        {
            final Integer result = compare( operand1 , operands );
            if ( result == null )
            {
                return null;
            }
            
            final ITextRegion r= new TextRegion( operand1.getTextRegion() );
            r.merge( operands[0].getTextRegion() );
            
            return result.intValue() < 0 ?  new NumberLiteralNode( 1 , r ) :  new NumberLiteralNode( 0 , r );                 
        }           
    },
    GREATER_THAN_EQUAL(">=",10) 
    {
        @Override
        public boolean internalSupportsTypes(DataType type1, DataType... types)
        {
            return type1.equals(DataType.INTEGER) && types[0].equals( DataType.INTEGER );
        }

        @Override
        public DataType internalInferType(DataType argType1, DataType argType2)
        {
            return DataType.INTEGER;
        }      
        
        @Override
        protected TermNode internalApply(TermNode operand1, TermNode... operands)
        {
            final Integer result = compare( operand1 , operands );
            if ( result == null )
            {
                return null;
            }
            
            final ITextRegion r= new TextRegion( operand1.getTextRegion() );
            r.merge( operands[0].getTextRegion() );
            
            return result.intValue() >= 0 ?  new NumberLiteralNode( 1 , r ) :  new NumberLiteralNode( 0 , r );                 
        }         
    },    
    GREATER_THAN(">",10) 
    {
        @Override
        public boolean internalSupportsTypes(DataType type1, DataType... types)
        {
            return type1.equals(DataType.INTEGER) && types[0].equals( DataType.INTEGER );
        }

        @Override
        public DataType internalInferType(DataType argType1, DataType argType2)
        {
            return DataType.INTEGER;
        }      
        
        @Override
        protected TermNode internalApply(TermNode operand1, TermNode... operands)
        {
            final Integer result = compare( operand1 , operands );
            if ( result == null )
            {
                return null;
            }
            
            final ITextRegion r= new TextRegion( operand1.getTextRegion() );
            r.merge( operands[0].getTextRegion() );
            
            return result.intValue() > 0 ?  new NumberLiteralNode( 1 , r ) :  new NumberLiteralNode( 0 , r );                 
        }         
    };
    
    private final String literal;
    private final int precedence;
    private final int operandCount;
    private final boolean leftAssociative;
    
    private Operator(String literal,int precedence)
    {
        this(literal,precedence,2);
    }
    
    private Operator(String literal,int precedence,int operandCount)
    {
        this(literal, precedence, operandCount, true);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Integer compare(TermNode operand1, TermNode... operands)
    {
        final TermNode value1 = operand1.reduce( );
        final TermNode value2 = operands[0].reduce( );
        
        if ( canCompare( value1, value2) )
        {
            final Comparable o1 = (Comparable) value1.getDataType( ).extractValue( value1 );
            final Comparable o2 = (Comparable) value1.getDataType( ).extractValue( value1 );

            final ITextRegion r= new TextRegion( value1.getTextRegion() );
            r.merge( value2.getTextRegion() );                
            return o1.compareTo( o2 );
        }
        return null;        
    }
    
    private Operator(String literal,int precedence,int operandCount,boolean leftAssociative)
    {
        this.literal = literal;
        this.precedence = precedence;
        this.operandCount = operandCount;
        this.leftAssociative = leftAssociative;
    }

    public int getOperandCount()
    {
        return operandCount;
    }
    
    public String getLiteral()
    {
        return literal;
    }
    
    public int getPrecedence()
    {
        return precedence;
    }
    
    public boolean hasHigherPrecedenceAs(Operator other) {
        return this.getPrecedence() > other.getPrecedence();
    }
    
    public boolean isLeftAssociative()
    {
        return leftAssociative;
    }
    
    @Override
    public String toString()
    {
        return literal;
    }
    
    public static Operator parseOperator(String s) 
    {
        for ( Operator op : values() ) {
            if ( op.mayBeParsedByLexer() && op.literal.equals( s ) ) {
                return op;
            }
        }
        throw new IllegalArgumentException("Not a valid operation: >"+s+"<");
    }    
    
    public static boolean isValidOperator(String s) 
    {
        for ( Operator op : values() ) 
        {
            if ( op.literal.equals( s ) ) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean canCalculate(TermNode value1,TermNode value2) 
    {
    	return canCalculate(value1) && canCalculate( value2 );
    }
    
    private static boolean canCalculate(TermNode value1) 
    {
        return value1.isLiteralValue() && value1.getDataType().equals( DataType.INTEGER );
    }    
    
    private static boolean canCompare(TermNode value1,TermNode value2) 
    {
        // TODO: AST validation is expected to fail on comparisons between incompatible data types 
        return value1.isLiteralValue() && value2.isLiteralValue() &&
                value1.getDataType().equals( value2.getDataType() );
    }    
    
    protected abstract boolean internalSupportsTypes(DataType type1,DataType... types);
    
    protected abstract DataType internalInferType(DataType argType1,DataType argType2);

    public final boolean supportsTypes(DataType type1, DataType... types)
    {
        int count = 1 + ( types != null ? types.length : 0 );
        if ( count != getOperandCount() ) {
            throw new IllegalArgumentException("Operand count mismatch for "+this+" , expected "+getOperandCount()+" but got "+count);
                    
        }
        return internalSupportsTypes( type1 , types );
    }

    public final DataType inferType(DataType argType1, DataType argType2)
    {
        if ( getOperandCount() == 1 ) 
        {
            if (! supportsTypes( argType1 ) ) {
                throw new IllegalArgumentException("Operator "+this+" cannot infer types from unsupported type "+argType1);
            }
        } 
        else 
        {
            if (! supportsTypes( argType1 , argType2 ) ) {
                throw new IllegalArgumentException("Operator "+this+" cannot infer types from unsupported types "+argType1+" , "+argType2);
            }            
        }

        return internalInferType( argType1,argType2);
    }
    
    /**
     * This method <b>IGNORES</b> all operators whose 
     * {@link #mayBeParsedByLexer()}  method returns <code>false</code>.
     * 
     * @param s
     * @return
     * @see #mayBeParsedByLexer()
     */
    public static boolean isOperatorPrefix(String s) 
    {
        for ( Operator op : values() ) {
            if ( op.mayBeParsedByLexer() && op.literal.startsWith( s ) ) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isCommutative() {
        return false;
    }
    
    protected abstract TermNode internalApply(TermNode operand1,TermNode... operands);
    
    public final ASTNode apply(TermNode operand1,TermNode... operands) 
    {
        final int count = 1 + ( operands != null ? operands.length : 0 );
        if ( count != getOperandCount() ) {
            throw new IllegalArgumentException("Operand count mismatch for "+this+" , expected "+getOperandCount()+" but got "+count);
                    
        }
        return internalApply(operand1,operands);
    }
    
    public final ASTNode reduce(TermNode operand1,TermNode... operands) 
    {
        final int count = 1 + ( operands != null ? operands.length : 0 );
        if ( count != getOperandCount() ) {
            throw new IllegalArgumentException("Operand count mismatch for "+this+" , expected "+getOperandCount()+" but got "+count);
                    
        }
        return internalReduce(operand1,operands);
    }    
    
    protected TermNode internalReduce(TermNode operand1,TermNode... operands) 
    {
        return internalApply(operand1,operands);
    }
    
    public final boolean isComparisonOperator() 
    {
        switch(this)  {
            case NOT_EQUAL:
            case EQUALS:
            case GREATER_THAN:
            case GREATER_THAN_EQUAL:
            case LESS_THAN:
            case LESS_THAN_EQUAL:
                return true;
            default:
                return false;
        }
    }
    
    public Operator getNegatedComparisonOperator() 
    {
        if ( ! isComparisonOperator() ) {
            throw new UnsupportedOperationException("Cannot negate, not a comparison operator: "+this);
        }          
        
        switch( this ) {
            case EQUALS:
                return NOT_EQUAL;
            case NOT_EQUAL:
                return EQUALS;
            case GREATER_THAN:
                return LESS_THAN_EQUAL;
            case LESS_THAN:
                return GREATER_THAN_EQUAL;
            case LESS_THAN_EQUAL:
                return GREATER_THAN;
            case GREATER_THAN_EQUAL:
                return LESS_THAN;
            default:
                throw new RuntimeException("Internal error,unhandled comparison operator "+this);
        }
    }
    
    public boolean mayBeParsedByLexer() {
    	return true;
    }
    
    public static final DataType tryInferDataType(Operator op,TermNode op1,TermNode... otherOperands) 
    {
        final TermNode op2 = otherOperands != null && otherOperands.length > 0 ? otherOperands[0] : null;
        
        DataType type1 = op1.getDataType();
        DataType type2 = op.getOperandCount() > 1 ? otherOperands[0].getDataType() : null;
        
        switch( op ) 
        {
            case ASSIGNMENT:
                return type1;
            case EQUALS:
            case DEREFERENCE:
            	return type1.dereference();
            case NOT_EQUAL:                
            case GREATER_THAN:
            case GREATER_THAN_EQUAL:
            case LESS_THAN:
            case LESS_THAN_EQUAL:
                return DataType.INTEGER;                
            case MINUS:
                if ( op1.getDataType().isIntegerType() && op2.getDataType().isIntegerType() ) 
                {
                    return DataType.INTEGER;
                }
                // TODO: Handle ( ptr - integer )
                break;
            case PLUS:
                
                // handle special case: ptr + integer / ptr + ptr
                if ( type1.isPtr() || type2.isPtr() ) 
                {
                    if ( type1.isPtr() && type2.isPtr() && 
                         type1.getBaseType().equals( type2.getBaseType() ) &&
                         type1.getPtrCount() == type2.getPtrCount() ) 
                    {
                        return type1;
                    }
                    // ptr + integer
                    if ( type1.isPtr() && ! type2.isPtr() && type2.getBaseType().isAssignableTo(DataType.INTEGER) ) {
                        return type1;
                    }
                    if ( type2.isPtr() && ! type1.isPtr() && type1.getBaseType().isAssignableTo(DataType.INTEGER) ) {
                        return type1;
                    }                    
                }
                
                if ( type1.isIntegerType() && type2.isIntegerType() ) 
                {
                    return DataType.INTEGER;
                }
                break;
        }
        throw new RuntimeException("Internal error, type inference failed for: "+op1+" "+op+" "+op2);
//        return DataType.UNKNOWN;        
    }
    
    public final boolean isLValue(TermNode op1,TermNode... otherOperands)
    {
        int count = 1 + ( otherOperands != null ? otherOperands.length : 0 );
        if ( count != getOperandCount() ) {
            throw new IllegalArgumentException("Invalid operand count "+count+" for operator "+this);
        }
        ASTNode result = apply( op1 , otherOperands );
        if ( result instanceof TermNode) {
            return ((TermNode) result).getDataType().isLValue();
        }
        return false;
    }

	public boolean isLogicalOperator() {
		return false;
	}
}