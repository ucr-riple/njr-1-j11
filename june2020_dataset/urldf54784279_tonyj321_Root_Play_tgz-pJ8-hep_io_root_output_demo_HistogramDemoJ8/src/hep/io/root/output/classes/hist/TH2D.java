package hep.io.root.output.classes.hist;

import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.annotations.Super;
import hep.io.root.output.classes.TArrayD;

/**
 * Histograms with one double per channel.
 * @see <a href="http://root.cern.ch/root/htmldoc/TH1D.html">TH1D</a>
 * @author tonyj
 */
@ClassDef(version = 3)
public class TH2D extends TH2 {
    private @Super TArrayD array;

    public TH2D(String name, int nXBins, double xMin, double xMax, 
            int nYBins, double yMin, double yMax, double[] data) {
        super(name, nXBins, xMin, xMax, nYBins, yMin, yMax);
        array = new TArrayD(data);
    }
}
