package hep.io.root.output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import org.junit.Test;
import hep.io.root.output.demo.POJODemo;
import static org.junit.Assert.*;

/**
 *
 * @author tonyj
 */
public class POJOTest {

    @Test
    public void test1() throws IOException {

        TFile.setTimeWarp(true);
        File tmp = File.createTempFile("pojo", "root");
        tmp.deleteOnExit();
        try (TFile file = new TFile(tmp)) {
            file.setCompressionLevel(0);
            file.add(new POJODemo.POJO());
        }
        assertEquals(3428885616L, computeChecksum(tmp));
    }

    static long computeChecksum(File file) throws FileNotFoundException, IOException {
        try (CheckedInputStream in = new CheckedInputStream(new FileInputStream(file), new Adler32())) {
            byte[] buffer = new byte[4096];
            for (;;) {
                int l = in.read(buffer);
                if (l < 0) {
                    break;
                }
            }
            return in.getChecksum().getValue();
        }
    }
}
