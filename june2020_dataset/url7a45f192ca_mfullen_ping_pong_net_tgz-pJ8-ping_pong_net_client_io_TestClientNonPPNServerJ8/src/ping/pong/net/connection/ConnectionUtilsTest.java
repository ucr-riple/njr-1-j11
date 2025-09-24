package ping.pong.net.connection;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Adrian
 */
public class ConnectionUtilsTest
{


    /**
     * Test of compressfinal method, of class ConnectionUtils.
     */
    @Test
    public void testCompressfinal()
    {
        {
            byte[] input = new byte[0];
            byte[] result = ConnectionUtils.compressfinal(input);
            assertEquals(8, result.length);
        }
        {
            byte[] input = new byte[1];
            byte[] result = ConnectionUtils.compressfinal(input);
            assertEquals(9, result.length);
        }
        {
            byte[] input = new byte[]{3,7,7,7,7,5,9,2,6,4,4,4};
            byte[] result = ConnectionUtils.compressfinal(input);
            byte[] expResult = new byte[]{120, -38, 99, 102, 7, 2, 86, 78, 38, 54, 22, 22, 22, 0, 1, -51, 0, 66};
            assertEquals(byteArrayToString(expResult), byteArrayToString(result));
        }
        {
            byte[] input = new byte[]{3,7,7,7,7,5,9,2,6,4,4,4};
            byte[] compressed = ConnectionUtils.compressfinal(input);
            byte[] decompressed = ConnectionUtils.decompress(compressed);
            assertEquals(byteArrayToString(input), byteArrayToString(decompressed));
        }
    }

    /**
     * Test of decompress method, of class ConnectionUtils.
     */
    @Test
    public void testDecompress()
    {
        {
            byte[] input = new byte[]{120, -38, 99, 102, 7, 2, 86, 78, 38, 54, 22, 22, 22, 0, 1, -51, 0, 66};
            byte[] result = ConnectionUtils.decompress(input);
            byte[] expResult = new byte[]{3,7,7,7,7,5,9,2,6,4,4,4};
            assertEquals(byteArrayToString(expResult), byteArrayToString(result));
        }
    }

    /**
     * Test of getbytes method, of class ConnectionUtils.
     */
    @Test
    public void testGetbytes() throws Exception
    {
        {
            byte[] result = ConnectionUtils.getbytes(new Integer(5));
            assertEquals("-84, -19, 0, 5, 115, 114, 0, 17, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 73, 110, 116, 101, 103, 101, 114, 18, -30, -96, -92, -9, -127, -121, 56, 2, 0, 1, 73, 0, 5, 118, 97, 108, 117, 101, 120, 114, 0, 16, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 78, 117, 109, 98, 101, 114, -122, -84, -107, 29, 11, -108, -32, -117, 2, 0, 0, 120, 112, 0, 0, 0, 5",
                         byteArrayToString(result));
        }
        {
            byte[] result = ConnectionUtils.getbytes("abcd");
            assertEquals("-84, -19, 0, 5, 116, 0, 4, 97, 98, 99, 100", byteArrayToString(result));
        }
        {
            byte[] result = ConnectionUtils.getbytes(new Integer(5));
            assertEquals(new Integer(5), ConnectionUtils.getObject(result));
            assertTrue(new Integer(6) != ConnectionUtils.getObject(result));
        }
        {
            byte[] result = ConnectionUtils.getbytes("a1");
            assertEquals("a1", ConnectionUtils.getObject(result));
            assertTrue("b2" != ConnectionUtils.getObject(result));
        }
    }

    /**
     * Test of getObject method, of class ConnectionUtils.
     */
    @Test
    public void testGetObject() throws Exception
    {
        byte[] bytes = ConnectionUtils.getbytes(new Integer(5));
        Integer result = (Integer)ConnectionUtils.getObject(bytes);
        assertEquals(5, (int)result);
    }


    protected static String byteArrayToString(byte[] byteArray)
    {
        String str = "";
        for (int i = 0; i < byteArray.length; i++)
        {
            if (i > 0)
            {
                str += ", ";
            }
            str += byteArray[i];
        }
        return str;
    }

}
