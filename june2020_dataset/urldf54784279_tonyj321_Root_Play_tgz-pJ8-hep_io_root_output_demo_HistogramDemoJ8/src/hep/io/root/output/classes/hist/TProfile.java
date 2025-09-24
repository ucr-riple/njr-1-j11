package hep.io.root.output.classes.hist;

import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.annotations.Title;
import hep.io.root.output.classes.TArrayD;

/**
 * 1-Dim profile.
 *
 * @see <a href="http://root.cern.ch/root/htmldoc/TProfile.html">TProfile</a>
 * @author onoprien
 */
@ClassDef(version = 6, checkSum=-1823164045)
@Title("1-Dim profile class")
public class TProfile extends TH1D {

    @Title("number of entries per bin")
    private TArrayD fBinEntries;
    @Title("Option to compute errors")
    private EErrorType fErrorMode = EErrorType.kERRORMEAN;
    @Title("Lower limit in Y (if set)")
    private double fYmin;
    @Title("Upper limit in Y (if set)")
    private double fYmax;
    @Title("Total Sum of weight*Y")
    private double fTsumwy;
    @Title("Total Sum of weight*Y*Y")
    private double fTsumwy2;
    @Title("Array of sum of squares of weights per bin")
    private TArrayD fBinSumw2;

    public TProfile(String name, int nBins, double xMin, double xMax, double[] yyw, double[] yw, double[] w, double[] w2) {
        super(name, nBins, xMin, xMax, yw);
        this.fBinEntries = new TArrayD(w);
        this.fBinSumw2 = new TArrayD(w2);
        setfSumw2(new TArrayD(yyw));
    }

    public void setfTsumwy(double sumy) {
        this.fTsumwy = sumy;
    }

    public void setfTsumwy2(double sumy2) {
        this.fTsumwy2 = sumy2;
    }
}
