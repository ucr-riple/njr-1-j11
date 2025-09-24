package hep.io.root.output;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import hep.io.root.output.demo.SimpleHistogramFiller;

/**
 *
 * @author tonyj
 */
public class HistogramTest {

    @Test
    public void test1() throws IOException {

        TFile.setTimeWarp(true);
        File tmp = File.createTempFile("histogram", "root");
        tmp.deleteOnExit();
        try (TFile file = new TFile(tmp)) {
            file.setCompressionLevel(0);
            SimpleHistogramFiller demo = new SimpleHistogramFiller(new Random(123456));
            file.add(demo.create1DHistogram("test1", "Histogram created from Java"));
            file.add(demo.create1DHistogram("test2", "Histogram created from Java"));
        }
        assertEquals(4053281214L, POJOTest.computeChecksum(tmp));
    }

    @Test
    public void test2() throws IOException {

        TFile.setTimeWarp(true);
        File tmp = File.createTempFile("histogram", "root");
        tmp.deleteOnExit();
        try (TFile file = new TFile(tmp)) {
            SimpleHistogramFiller demo = new SimpleHistogramFiller(new Random(123456));
            file.add(demo.create1DHistogram("test1", "Histogram created from Java"));
            file.add(demo.create1DHistogram("test2", "Histogram created from Java"));
        }
        assertEquals(3804655589L, POJOTest.computeChecksum(tmp));
    }

    @Test
    public void test3() throws IOException {

        TFile.setTimeWarp(true);
        File tmp = File.createTempFile("histogram", "root");
        tmp.deleteOnExit();
        try (TFile file = new TFile(tmp)) {
            SimpleHistogramFiller demo = new SimpleHistogramFiller(new Random(123456));
            file.add(demo.create1DHistogram("test1", "Histogram created from Java"));
            file.add(demo.create1DHistogram("test2", "Histogram created from Java"));
            file.add(demo.create2DHistogram("test3", "Histogram created from Java"));
        }
        assertEquals(3110815932L, POJOTest.computeChecksum(tmp));
    }

    @Test
    public void test4() throws IOException {

        TFile.setTimeWarp(true);
        File tmp = File.createTempFile("histogram", "root");
        tmp.deleteOnExit();
        try (TFile file = new TFile(tmp)) {
            SimpleHistogramFiller demo = new SimpleHistogramFiller(new Random(123456));
            file.add(demo.createProfile("test4", "Profile created from Java"));
        }
        assertEquals(848739441L, POJOTest.computeChecksum(tmp));
    }

    @Test
    public void test5() throws IOException {

        TFile.setTimeWarp(true);
        File tmp = File.createTempFile("histogram", "root");
        tmp.deleteOnExit();
        try (TFile file = new TFile(tmp)) {
            SimpleHistogramFiller demo = new SimpleHistogramFiller(new Random(123456));
            file.add(demo.createProfile2D("test5", "Profile created from Java"));
        }
        assertEquals(3333471590L, POJOTest.computeChecksum(tmp));
    }
}
