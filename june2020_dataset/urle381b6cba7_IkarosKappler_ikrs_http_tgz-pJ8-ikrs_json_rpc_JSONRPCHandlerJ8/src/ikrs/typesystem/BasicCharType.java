package ikrs.typesystem;

/**
 * @author Ikaros Kappler
 * @date 2012-04-22
 * @version 1.0.0
 **/



public class BasicCharType
    extends BasicTypeAdapter
    implements BasicType {
	
    private String c;
    
    public BasicCharType( char c ) {
	super( BasicType.TYPE_CHAR );

	this.c = new String(""+c);
    }
    
    public char getChar() 
	throws BasicTypeException {
	
	return this.c.charAt(0);
    }
    

    public String getString() 
	throws BasicTypeException {
	
	return c;
    }
}