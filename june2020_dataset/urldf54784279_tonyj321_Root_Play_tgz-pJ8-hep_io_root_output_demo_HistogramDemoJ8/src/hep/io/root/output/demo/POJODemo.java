package hep.io.root.output.demo;

import java.io.IOException;
import hep.io.root.output.TFile;

/**
 *
 * @author tonyj
 */
public class POJODemo {

    public static void main(String[] args) throws IOException {
        try (TFile file = new TFile("pojo.root")) {
            file.add(new POJO());
        }
    }

    public static class POJO {

        int i = 25;
        double d = 1234.567;
        String hello = "Hello World";
    }
}
