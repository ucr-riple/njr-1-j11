package encoder;


import junit.framework.TestCase;



/**
 * Created with IntelliJ IDEA.
 * User: Tatiana
 * Date: 17/05/13
 * Time: 17:58
 * To change this template use File | Settings | File Templates.
 */
public class CipherEncoderTest extends TestCase {

    public void testEncodeDecode() {
        final CipherEncoder plainEncoder = new CipherEncoder();

        final String decode = plainEncoder.decode("some".getBytes());
        final byte[] encode = plainEncoder.encode("some");

        assertEquals("Encode - Decode Test Pass", decode, new String(encode));
    }
}
