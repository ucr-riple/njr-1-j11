package hep.io.root.output.classes.hist;

import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.annotations.Super;
import hep.io.root.output.classes.TArrayD;

/**
 * Histograms with one double per channel.
 * @see <a href="http://root.cern.ch/root/htmldoc/TH1D.html">TH1D</a>
 * @author tonyj
 */
@ClassDef(version = 1)
public class TH1D extends TH1 {
    private @Super TArrayD array;

    public TH1D(String name, int nBins, double xMin, double xMax, double[] data) {
        super(name, nBins, xMin, xMax);
        array = new TArrayD(data);
    }
}
