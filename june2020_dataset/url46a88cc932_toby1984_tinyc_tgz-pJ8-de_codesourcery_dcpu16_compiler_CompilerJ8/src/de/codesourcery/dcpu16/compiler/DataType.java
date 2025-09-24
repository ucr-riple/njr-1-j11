package de.codesourcery.dcpu16.compiler;

import java.util.HashMap;
import java.util.Map;

import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.NumberLiteralNode;
import de.codesourcery.dcpu16.ast.StringLiteralNode;

public class DataType
{
	public static final Identifier UNKNOWN_TYPE = new Identifier("__UNKNOWN__");
	public static final Identifier VOID_TYPE = new Identifier("void");
	public static final Identifier BOOLEAN_TYPE = new Identifier("boolean");
	public static final Identifier INTEGER_TYPE = new Identifier("int");
	public static final Identifier CHAR_TYPE = new Identifier("char");
	
    /**
     * Unknown data type , used when inferring some type failed because not enough information is available.
     */
    public static final DataType UNKNOWN = new DataType( UNKNOWN_TYPE );
    public static final DataType VOID = new DataType( VOID_TYPE );
    
    public static final DataType INTEGER = new DataType( INTEGER_TYPE ) {
        
        @Override
        protected Object internalExtractValue(ASTNode node) 
        {
            return node instanceof NumberLiteralNode ? ((NumberLiteralNode) node).getValue() : null;
        }       
    };
    
    public static final DataType CHAR = new DataType( CHAR_TYPE) {
        
        @Override
        protected Object internalExtractValue(ASTNode node) 
        {
            return node instanceof StringLiteralNode ? ((StringLiteralNode) node).getValue() : null;
        }        
    };
    
    private static final Map<Identifier,Map<Integer,DataType> > INSTANCE_CACHE = new HashMap<>();
    private static final Map<Identifier,Map<Integer,DataType> > ARRAY_INSTANCE_CACHE = new HashMap<>();
    
    private final Identifier identifier;
    private final boolean isArray;
    private final boolean isBuiltIn;
    private final int ptrCount;
    
    static {
    	putDataType( UNKNOWN );
    	putDataType( VOID );
    	putDataType( INTEGER );
    	putDataType( CHAR );
    }
    
    private DataType(Identifier identifier)
    {
    	this(identifier,false,true);
    }
    
    private DataType(Identifier identifier,boolean isArray,boolean isBuiltIn)
    {
    	this(identifier,isArray,isBuiltIn , 0 );
    }
    
    public DataType(Identifier identifier,boolean isArray,int ptrCount)
    {
    	this(identifier,isArray,isBuiltInType( identifier ) , ptrCount );
    }
    
    private DataType(Identifier identifier,boolean isArray,boolean isBuiltIn,int ptrCount)
    {
        if (identifier == null) {
            throw new IllegalArgumentException("identifier must not be NULL.");
        }
        this.ptrCount = ptrCount;
        this.identifier = identifier;
        this.isArray = isArray;
        this.isBuiltIn = isBuiltIn;
    }
    
    public static DataType getDataType(Identifier identifier,boolean isArray,int ptrCount) 
    {
    	return getDataType(identifier,isBuiltInType( identifier ) , isArray, ptrCount );
    }
    
    private static DataType getDataType(Identifier identifier,boolean isBuiltInType, boolean isArray,int ptrCount) 
    {
    	final Map<Identifier,Map<Integer,DataType> > cache;
    	if ( isArray ) {
    		cache=ARRAY_INSTANCE_CACHE;
    	} else {
    		cache = INSTANCE_CACHE;
    	}
    	
    	Map<Integer, DataType> map = cache.get( identifier );
    	if ( map == null ) {
    		map = new HashMap<>();
    		cache.put( identifier , map );
    	}
    	DataType result = map.get( ptrCount );
    	if ( result != null ) {
    		return result;
    	}
    	result = new DataType(identifier,isArray,isBuiltInType(identifier),ptrCount);
    	map.put(ptrCount,result);
    	return result;
    }
    
    private static void putDataType(DataType type) 
    {
    	final Map<Identifier,Map<Integer,DataType> > cache;
    	if ( type.isArray ) {
    		cache=ARRAY_INSTANCE_CACHE;
    	} else {
    		cache = INSTANCE_CACHE;
    	}
    	
    	Map<Integer, DataType> map = cache.get( type.identifier );
    	if ( map == null ) {
    		map = new HashMap<>();
    		cache.put( type.identifier , map );
    	}
    	
    	map.put(type.ptrCount,type);
    }
    
    public static boolean isBuiltInType(Identifier identifier) 
    {
    	switch( identifier.getStringValue() ) {
    		case "void":
    		case "boolean":
    		case "char":
    		case "int":
    			return true;
    	}
    	return false;
    }
    
    protected Object internalExtractValue(ASTNode node) 
    {
        throw new UnsupportedOperationException("internalExtractValue() not implemented for data type "+this);        
    }
    
    public final Object extractValue(ASTNode node) 
    {
        Object result = internalExtractValue(node);
        if ( result == null ) {
            throw new IllegalArgumentException("Data type "+this+" cannot extract a value from "+node);
        }
        return result;
    }
    
    public boolean isArray()
    {
        return isArray;
    }
    
    public boolean isIntegerType() 
    {
    	return isBasicType() && isAssignableTo( DataType.INTEGER ); 
    }

    public boolean isBuiltIn() {
        return isBuiltIn;
    }
    
    public Identifier getIdentifier()
    {
        return identifier;
    }
    
    @Override
    public String toString()
    {
    	String result = identifier.getStringValue();
    	for ( int i = 0 ; i < ptrCount ; i++) {
    		result = result+"*";
    	}
    	if ( isArray ) {
    		result+="[]";
    	}
        return result;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + identifier.hashCode();
        result = prime * result + (isArray ? 1231 : 1237);
        result = prime * result + (isBuiltIn ? 1231 : 1237);
        return result;
    }
    
    public final boolean isAssignableTo(DataType other) {
        return other.isAssignableFrom( this );
    }
    
    public final boolean isAssignableFrom(DataType other) 
    {
    	if ( this.equals( other ) ) {
    		return true;
    	}
    	if ( this.equals(UNKNOWN) || other.equals(UNKNOWN ) ) {
    		return false;
    	}
    	if ( this.equals(VOID) || other.equals( VOID ) ) 
    	{
    		return true;
    	}
    	
    	if ( this.isArray && ! other.isArray ) {
    		return false;
    	}
    	
    	if ( this.isPtr() && ! other.isPtr() ) 
    	{
    		return other.isBasicType() && other.getBaseType().isIntegerType();
    	}
    	
    	if ( DataType.INTEGER.equals( this ) ) 
    	{
    		return other.getIdentifier().equals( INTEGER_TYPE ) || other.getIdentifier().equals( CHAR_TYPE );
    	}
    	
    	if ( DataType.CHAR.equals( this ) ) 
    	{
            return other.getIdentifier().equals( CHAR_TYPE ) || other.getIdentifier().equals( INTEGER_TYPE );    	    
    	} 
    	return this.equals( other );
    }
    
    public boolean canDereference() {
    	return ptrCount > 0;
    }
    
    public final DataType dereference() 
    {
    	if ( ptrCount == 0 ) {
    		throw new IllegalStateException("Cannot de-reference data type "+this);
    	}
   		return getDataType( this.identifier , this.isArray , this.ptrCount-1);
    }

    @Override
    public final boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (! (obj instanceof DataType) ) {
            return false;
        }
        final DataType other = (DataType) obj;
        if ( ! this.identifier.equals( other.identifier ) )
        {
            return false;
        }
        
        if (isArray != other.isArray) {
            return false;
        }
        if (isBuiltIn != other.isBuiltIn) {
            return false;
        }
        if ( this.ptrCount != other.ptrCount ) {
        	return false;
        }
        return true;
    }

	public int getPtrCount() {
		return ptrCount;
	}
	
	public boolean isPtr() {
		return ptrCount != 0;
	}
	
	public boolean isBasicType() {
		return ! isPtr() && ! isArray() && isBuiltIn();
	}
	
	public boolean isLValue() 
	{
	    /*
	     * Numeric literals, such as 3 and 3.14159, are rvalues. 
	     * So are character literals, such as 'a'. 
	     * An identifier that refers to an object is an lvalue, 
	     * but an identifier that names an enumeration constant is an rvalue.
	     */
	    if ( getIdentifier().equals( INTEGER_TYPE ) ) {
	        if ( getPtrCount() == 0 ) {
	            return true;
	        }
	        return false;
	    }
	    if ( getIdentifier().equals( CHAR_TYPE ) ) 
	    {
            if ( getPtrCount() == 0 ) {
                return true;
            }	        
	        return false;
	    }
	    return false;
	}
	
	public DataType getBaseType() {
	    return DataType.getDataType( this.getIdentifier() , false , 0 );
	}
}
