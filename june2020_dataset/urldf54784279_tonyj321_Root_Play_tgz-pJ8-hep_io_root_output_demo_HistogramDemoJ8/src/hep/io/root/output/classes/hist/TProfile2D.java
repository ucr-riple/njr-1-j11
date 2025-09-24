package hep.io.root.output.classes.hist;

import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.annotations.Title;
import hep.io.root.output.classes.TArrayD;

/**
 * 2-Dim profile.
 *
 * @see <a href="http://root.cern.ch/root/htmldoc/TProfile2D.html">TProfile2D</a>
 * @author onoprien
 */
@ClassDef(version = 7, checkSum=1460523088)
@Title("2-Dim profile class")
public class TProfile2D extends TH2D {
    @Title("number of entries per bin")
    private TArrayD fBinEntries;
    @Title("Option to compute errors")
    private EErrorType fErrorMode = EErrorType.kERRORMEAN;
    @Title("Lower limit in Z (if set)")
    private double fZmin;
    @Title("Upper limit in Z (if set)")
    private double fZmax;
    @Title("Total Sum of weight*Z")
    private double fTsumwz;
    @Title("Total Sum of weight*Z*Z")
    private double fTsumwz2;
    @Title("Array of sum of squares of weights per bin")
    private TArrayD fBinSumw2;    
    
    public TProfile2D(String name, int nXBins, double xMin, double xMax, int nYBins, double yMin, double yMax, double[] zzw, double[] zw, double[] w, double[] w2) {
        super(name, nXBins, xMin, xMax, nYBins, yMin, yMax, zw);
        this.fBinEntries = new TArrayD(w);
        this.fBinSumw2 = new TArrayD(w2);
        setfSumw2(new TArrayD(zzw));    }

    public void setfTsumwz(double sumz) {
        this.fTsumwz = sumz;
    }

    public void setfTsumwz2(double sumz2) {
        this.fTsumwz2 = sumz2;
    }
}
