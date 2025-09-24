package hep.io.root.output.classes.hist;

import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.annotations.Title;

/**
 * The base 2d histogram class.
 *
 * @see <a href="http://root.cern.ch/root/htmldoc/TH2.html">TH2</a>
 * @author tonyj
 */
@ClassDef(version = 4)
public class TH2 extends TH1 {

   @Title("Scale factor") 
   private double     fScalefactor = 1;
   @Title("Total Sum of weight*Y")
   private double     fTsumwy;
   @Title("Total Sum of weight*Y*Y")
   private double     fTsumwy2;
   @Title("Total Sum of weight*X*Y")
   private double     fTsumwxy;
    
    public TH2(String name, int nXBins, double xMin, double xMax,
            int nYBins, double yMin, double yMax) {
        super(name, nXBins, xMin, xMax);
        fYaxis = new TAxis("yaxis", nYBins, yMin, yMax);
        fNcells = (nXBins + 2) * (nYBins + 2);
    }

    public double getfScalefactor() {
        return fScalefactor;
    }

    public void setfScalefactor(double fScalefactor) {
        this.fScalefactor = fScalefactor;
    }

    public double getfTsumwy() {
        return fTsumwy;
    }

    public void setfTsumwy(double fTsumwy) {
        this.fTsumwy = fTsumwy;
    }

    public double getfTsumwy2() {
        return fTsumwy2;
    }

    public void setfTsumwy2(double fTsumwy2) {
        this.fTsumwy2 = fTsumwy2;
    }

    public double getfTsumwxy() {
        return fTsumwxy;
    }

    public void setfTsumwxy(double fTsumwxy) {
        this.fTsumwxy = fTsumwxy;
    }

}
