package terminal.tools;

import java.io.UnsupportedEncodingException;

/**
 * 16-bit CRC, cyclic redundancy checks, computed from scratch.
 *
 * @author Roedy Green, Canadian Mind Products
 * @version 1.0 2009-01-01 - initial version
 * @since 2009-01-01
 */
public final class HexTools
{
    /**
     * generator polynomial
     */
    public static final int poly = 0x1021;/* x16 + x12 + x5 + 1 generator polynomial */
    /**
     * scrambler lookup table for fast computation.
     */
    public static final int[] crcTable = new int[256];

    static
    {
        // initialise scrambler table
        for ( int i = 0; i < 256; i++ )
        {
            int fcs = 0;
            int d = i << 8;
            for ( int k = 0; k < 8; k++ )
            {
            	fcs = ( ( ( fcs ^ d ) & 0x8000 ) != 0 ) ? ( fcs << 1 ) ^ poly : ( fcs << 1 );
                d <<= 1;
                fcs &= 0xffff;
            }
            
            crcTable[ i ] = fcs;
        }
    }

    /**
     * Calc 16-bit CRC with CCITT method.
     *
     * @param ba byte array to compute CRC on
     *
     * @return 16-bit CRC, unsigned
     */
    public static int crc16( byte[] ba ) throws UnsupportedEncodingException
    {
        int work = 0x0000;

        for ( byte b : ba )
        {
            // xor the next data byte with the high byte of what we have so far to
            // look up the scrambler.
            // xor that with the low byte of what we have so far.
            // Mask back to 16 bits.
            work = ( crcTable[ ( b ^ ( work >>> 8 ) ) & 0xff ] ^ ( work << 8 ) ) & 0xffff;
        }
        
        return work;
    }

    public static byte[] fromHexString(String encoded) {
		if ((encoded.length() % 2) != 0)
			encoded = "0"+encoded;
            //throw new IllegalArgumentException("Input string must contain an even number of characters");

        final byte result[] = new byte[encoded.length() / 2];
        final char enc[] = encoded.toCharArray();
        
        for (int i = 0; i < enc.length; i += 2) {
            StringBuilder curr = new StringBuilder(2);
            curr.append(enc[i]).append(enc[i + 1]);
            result[i / 2] = (byte) Integer.parseInt(curr.toString(), 16);
        }

        return result;
    }
	
	public static String getHexString(byte[] b, int len)
	{
		String result = "";

		for (int i=0; i < len; i++)
			result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );

		return result;
  	}
	
	public static byte[] getCRC16(byte[] dataBuffer, int len, int k)
    {
    	/*int Sum=0; 
    	for (int i=0; i<len; i++ )
    	{
  	          Sum ^= dataBuffer[i] & 0xFF;
  	       
   	          for (int j=0; j<8; j++)
   	          {
   	        	  if((Sum & 0x1) !=0)
   	        	  {
    	             Sum >>=1;
    				 Sum = (Sum^k);
   	        	  }
   	        	  else
   	        	  {
   	        		  Sum >>=1;
   	        	  }
   	          }
    	 }*/
	 
	 int crc = 0;
		 
	    for (int i = 0; i < len; i++) {
	 	        crc ^= dataBuffer[i] & 0xFF;
	         for (int j = 0; j < 8; j++) {
	 	            if ((crc & 0x0001) != 0) {
	 	                crc = k ^ crc >>> 1;
	 	            } else {
	 	                crc >>>= 1;
	 	            }
	 	        }
	 	    }

    	String st = String.format("%4s", Integer.toHexString(crc)).replace(' ', '0');
    	return HexTools.fromHexString(st);
    }
	
	public static String tool(String str) { 
		   try { 
			   String x = "D";
			   String y = "M";
		        java.security.MessageDigest dg = java.security.MessageDigest.getInstance(y + x + (2+3)); 
		        byte[] array = dg.digest(str.getBytes()); 
		        StringBuffer sb = new StringBuffer(); 
		        for (int i = 0; i < array.length; ++i) { 
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3)); 
		       } 
		        return sb.toString(); 
		    } catch (java.security.NoSuchAlgorithmException e) { 
		    } 
		    return null; 
		}
	
	public static String hexToAscii(String s) 
	{ 
		  int n = s.length(); 
		  StringBuilder sb = new StringBuilder(n / 2); 
		  for (int i = 0; i < n; i += 2) { 
		    char a = s.charAt(i); 
		    char b = s.charAt(i + 1); 
		    sb.append((char) ((hexToInt(a) << 4) | hexToInt(b))); 
		  } 
		  return sb.toString(); 
	} 
	
	private static int hexToInt(char ch) { 
		  if ('a' <= ch && ch <= 'f') { return ch - 'a' + 10; } 
		  if ('A' <= ch && ch <= 'F') { return ch - 'A' + 10; } 
		  if ('0' <= ch && ch <= '9') { return ch - '0'; } 
		  throw new IllegalArgumentException(String.valueOf(ch)); 
	}
}