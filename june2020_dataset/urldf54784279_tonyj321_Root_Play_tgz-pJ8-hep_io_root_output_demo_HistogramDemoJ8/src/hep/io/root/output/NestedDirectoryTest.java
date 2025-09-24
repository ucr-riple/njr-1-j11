package hep.io.root.output;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import hep.io.root.output.classes.TObjString;

/**
 *
 * @author tonyj
 */
public class NestedDirectoryTest {

    @Test
    public void test1() throws IOException {
        TFile.setTimeWarp(true);
        File tmp = File.createTempFile("nested", "root");
        tmp.deleteOnExit();
        try (TFile file = new TFile(tmp)) {
            file.setCompressionLevel(0);
            TDirectory dir = file.mkdir("sub-dir");
            TDirectory sdir = dir.mkdir("sub-sub-dir");
            sdir.add(new TObjString("I am a root file written from Java!"));
        }
        assertEquals(3349830476L, POJOTest.computeChecksum(tmp));
    }

    @Test
    public void test2() throws IOException {
        TFile.setTimeWarp(true);
        File tmp = File.createTempFile("nested2", "root");
        tmp.deleteOnExit();
        try (TFile file = new TFile(tmp)) {
            file.setCompressionLevel(0);
            TDirectory sdir = file.mkdir("sub-dir/sub-sub-dir",true);
            sdir.add(new TObjString("I am a root file written from Java!"));
            
            TDirectory sdir2 = file.findDir("sub-dir/sub-sub-dir",true);
            assertEquals(sdir,sdir2);
            
            try {
                file.mkdir("sub-dir");
                assertTrue("Should have thrown exception",false);
            } catch (IllegalArgumentException x) {
                // OK, exception expected
            }
        }
        assertEquals(3349830476L, POJOTest.computeChecksum(tmp));
    }
}
