package hep.io.root.output.demo;

import hep.io.root.output.classes.TObjString;
import java.io.IOException;
import hep.io.root.output.TDirectory;
import hep.io.root.output.TFile;

/**
 *
 * @author tonyj
 */
public class NestedDirectoryDemo {
    public static void main(String[] args) throws IOException {
        
        try (TFile file = new TFile("nested.root")) {
            TDirectory dir = file.mkdir("sub-dir");
            TDirectory sdir = dir.mkdir("sub-sub-dir");
            sdir.add(new TObjString("I am a root file written from Java!"));
        }
    }
}
