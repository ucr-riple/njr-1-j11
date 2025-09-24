package hep.io.root.output.demo;

import java.util.Random;
import hep.io.root.output.classes.hist.TH1D;
import hep.io.root.output.classes.hist.TH2D;
import hep.io.root.output.classes.hist.TProfile;
import hep.io.root.output.classes.hist.TProfile2D;

/**
 *
 * @author tonyj
 */
public class SimpleHistogramFiller {

    private final Random random;

    public SimpleHistogramFiller() {
        this(new Random());
    }

    public SimpleHistogramFiller(Random random) {
        this.random = random;
    }

    public TH1D create1DHistogram(String name, String title) {
        int nBins = 100;
        double[] data = new double[nBins + 2];
        double xMin = -5;
        double xMax = 5;
        final int entries = 10000;
        double sumx = 0;
        double sumx2 = 0;

        for (int i = 0; i < entries; i++) {
            double d = random.nextGaussian();
            sumx += d;
            sumx2 += d * d;
            int bin = (int) Math.floor(nBins * (d - xMin) / (xMax - xMin));
            data[1 + bin]++;
        }
        TH1D th1d = new TH1D(name, nBins, xMin, xMax, data);
        th1d.setTitle(title);
        th1d.setEntries(entries);
        th1d.setfTsumw(entries);
        th1d.setfTsumw2(entries);
        th1d.setfTsumwx(sumx);
        th1d.setfTsumwx2(sumx2);
        return th1d;
    }

    public TH2D create2DHistogram(String name, String title) {
        int nXBins = 100;
        int nYBins = 100;
        double[] data = new double[(nXBins + 2) * (nYBins + 2)];
        double xMin = -5;
        double xMax = 5;
        double yMin = -5;
        double yMax = 5;
        final int entries = 100000;
        double sumx = 0;
        double sumx2 = 0;
        double sumy = 0;
        double sumy2 = 0;
        double sumxy = 0;

        for (int i = 0; i < entries; i++) {
            double x = random.nextGaussian();
            double y = random.nextGaussian();
            sumx += x;
            sumx2 += x * x;
            sumy += y;
            sumy2 += y * y;
            sumxy += x * y;
            int xBin = (int) Math.floor(nXBins * (x - xMin) / (xMax - xMin));
            int yBin = (int) Math.floor(nXBins * (y - yMin) / (yMax - yMin));
            data[1 + xBin + (1 + yBin) * (nXBins + 2)]++;
        }
        TH2D th2d = new TH2D(name, nXBins, xMin, xMax, nYBins, yMin, yMax, data);
        th2d.setTitle(title);
        th2d.setEntries(entries);
        th2d.setfTsumw(entries);
        th2d.setfTsumw2(entries);
        th2d.setfTsumwx(sumx);
        th2d.setfTsumwx2(sumx2);
        th2d.setfTsumwy(sumy);
        th2d.setfTsumwy2(sumy2);
        th2d.setfTsumwxy(sumxy);
        return th2d;
    }

    public TProfile createProfile(String name, String title) {
        int nBins = 100;
        double[] yw = new double[nBins + 2];
        double[] yyw = new double[nBins + 2];
        double[] w = new double[nBins + 2];
        double[] w2 = new double[nBins + 2];
        double xMin = -5;
        double xMax = 5;
        final int nEntries = 25000;
        double sumx = 0;
        double sumx2 = 0;
        double sumy = 0;
        double sumy2 = 0;

        for (int i = 0; i < nEntries; i++) {
            double px = random.nextGaussian();
            double py = random.nextGaussian();
            double pz = px * px + py * py;
            sumx += px;
            sumx2 += px * px;
            sumy += pz;
            sumy2 += pz * pz;
            int bin = (int) Math.floor(nBins * (px - xMin) / (xMax - xMin));
            w[1 + bin] += 1.;
            w2[1 + bin] += 1.;
            yw[1 + bin] += pz;
            yyw[1 + bin] += pz * pz;
        }
        TProfile profile = new TProfile(name, nBins, xMin, xMax, yyw, yw, w, w2);
        profile.setTitle(title);
        profile.setEntries(nEntries);
        profile.setfTsumw(nEntries);
        profile.setfTsumw2(nEntries);
        profile.setfTsumwx(sumx);
        profile.setfTsumwx2(sumx2);
        profile.setfTsumwy(sumy);
        profile.setfTsumwy2(sumy2);
        return profile;
    }

    public TProfile2D createProfile2D(String name, String title) {
        int nXBins = 100;
        int nYBins = 100;
        int nBins = (nXBins + 2) * (nYBins + 2);
        double[] zw = new double[nBins];
        double[] zzw = new double[nBins];
        double[] w = new double[nBins];
        double[] w2 = new double[nBins];
        double xMin = -5;
        double xMax = 5;
        double yMin = -5;
        double yMax = 5;
        final int nEntries = 25000;
        double sumx = 0;
        double sumx2 = 0;
        double sumy = 0;
        double sumy2 = 0;
        double sumz = 0;
        double sumz2 = 0;

        for (int i = 0; i < nEntries; i++) {
            double px = random.nextGaussian();
            double py = random.nextGaussian();
            double pz = px * px + py * py;
            sumx += px;
            sumx2 += px * px;
            sumy += py;
            sumy2 += py * py;
            sumz += pz;
            sumz2 += pz * pz;
            int xBin = (int) Math.floor(nXBins * (px - xMin) / (xMax - xMin));
            int yBin = (int) Math.floor(nXBins * (py - yMin) / (yMax - yMin));
            int bin = 1 + xBin + (1 + yBin) * (nXBins + 2);
            w[bin] += 1.;
            w2[bin] += 1.;
            zw[bin] += pz;
            zzw[bin] += pz * pz;
        }
        TProfile2D profile = new TProfile2D(name, nXBins, xMin, xMax, nYBins, yMin, yMax, zzw, zw, w, w2);
        profile.setTitle(title);
        profile.setEntries(nEntries);
        profile.setfTsumw(nEntries);
        profile.setfTsumw2(nEntries);
        profile.setfTsumwx(sumx);
        profile.setfTsumwx2(sumx2);
        profile.setfTsumwy(sumy);
        profile.setfTsumwy2(sumy2);
        profile.setfTsumwz(sumz);
        profile.setfTsumwz2(sumz2);
        return profile;
    }
}
