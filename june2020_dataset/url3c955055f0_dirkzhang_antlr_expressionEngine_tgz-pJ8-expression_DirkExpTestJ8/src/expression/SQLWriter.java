package expression;

import java.io.StringWriter;

/**
 * sql writer
 * @author dirk.zhang
 * 2013-2-25 上午10:42:03
 */
public class SQLWriter extends StringWriter {

	// ------------------------------ FIELDS ------------------------------

	    int indent = 0;

	    boolean atBeginningOfLine = true;

	    public static final String newLine = System.getProperty("line.separator");

	// -------------------------- OTHER METHODS --------------------------

	    public void resetIndent(){
	    	indent = 0;
	    }
	    
	    public void addIndent()
	    {
	        indent ++;
	    }

	    public void subIndent()
	    {
	        indent --;
	    }

	    public void newLineAddIndent()
	    {
	        addIndent();
	        writeLine();
	    }

	    public void newLineSubIndent()
	    {
	        subIndent();
	        writeLine();
	    }
	    
	    

	    @Override
	    public void write(String value)
	    {
	        if (value.equals(newLine))
	        {
	            super.write(newLine);
	            atBeginningOfLine = true;
	        }
	        else
	        {
	            if (atBeginningOfLine)
	            {
	                if (indent > 0)
	                {
	                    for (int i = 0; i < indent; i++)
	                    {
	                        super.write("\t");
	                    }
	                }
	                atBeginningOfLine = false;
	            }
	            super.write(value);
	        }
	    }

	    public void writeLine()
	    {
	        super.write(newLine);
	        atBeginningOfLine = true;
	    }
	
}
