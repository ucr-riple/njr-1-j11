package hep.io.root.output;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tonyj
 */
public class ChecksumTest {

    @Test
    public void testChecksum1() {
        Checksum ck = new Checksum();
        ck.compute("TAttFill");
        ck.compute("fFillColor");
        ck.compute("Color_t");
        ck.compute("fFillStyle");
        ck.compute("Style_t");
        assertEquals(1204118360, ck.getValue());
    }

    @Test
    public void testChecksum2() {
        Checksum ck = new Checksum();
        ck.compute("TH1D");
        ck.compute("TH1");
        ck.compute("TArrayD");
        assertEquals(187205993, ck.getValue());
    }
}
