package cscie97.asn1.knowledge.engine;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper Class: contains different helper generic methods e.g. search, char remover, bin table generator 
 * @author APGalush
 *
 */
public class Utilities
{
	/**
	 * Check if the Array of Strings contains any certain String
	 * @param array - type: array of Strings
	 * @param target - type: String
	 * @return true/false
	 */
	public boolean arrayContains( String[] array, String target )
	{
		for(String s: array)
		{
			if( s.equals( target ) )
				return true;
		}
		return false;
	}

	/**
	 * Removes last character from the string
	 * @param s - type: String
	 * @return
	 */
	public String removeLastChar(String s) {
	    if (!s.isEmpty() && s != null)
	    {
	        s = s.substring(0, s.length()-1);
	    }
	    return s;
	}
	
	/**
	 * Creates binary table representing decimal numbers (0 to ceiling decimal number)
	 * @param number - type: int, ceiling decimal number of the bin table 
	 * @return binTable - type: List of int arrays; binary table
	 */
	public List<int[]> getBinTable ( int number )
	{	
		//I could hardcode binary table, but have decided to implement the function
		List<int[]> binTable = new ArrayList<int[]>();
    	for ( int i = 0; i <= number; i++)
		{
    		String binString;
    		binString = String.format("%03d", new BigInteger(Integer.toBinaryString(i)));
    		
            int[] binNum = new int[binString.length()];

            for (int j = 0; j < binString.length(); j++)
            {
            	binNum[j] = binString.charAt(j) - '0';
            }
            
            binTable.add(binNum);    
		}
   
		return binTable;
	}
}
