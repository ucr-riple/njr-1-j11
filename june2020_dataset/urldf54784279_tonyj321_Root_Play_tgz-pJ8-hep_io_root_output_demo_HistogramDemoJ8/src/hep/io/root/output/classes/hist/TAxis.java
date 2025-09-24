package hep.io.root.output.classes.hist;

import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.Type;
import hep.io.root.output.annotations.FieldType;
import hep.io.root.output.annotations.Super;
import hep.io.root.output.annotations.Title;
import hep.io.root.output.classes.TArrayD;
import hep.io.root.output.classes.THashList;
import hep.io.root.output.classes.TNamed;

/**
 * This class manages histogram axis.
 *
 * @see <a href="http://root.cern.ch/root/htmldoc/TAxis.html">TAxis</a>
 * @author tonyj
 */
@ClassDef(version = 9, checkSum = 2116140609)
public class TAxis extends TNamed {

    private @Super TAttAxis tAttAxis = new TAttAxis();
    @Title("Number of bins")
    int fNbins;
    @Title("low edge of first bin")
    double fXmin;
    @Title("upper edge of last bin")
    double fXmax;
    @Title("Bin edges array in X")
    private TArrayD fXbins;
    @Title("first bin to display")
    private int fFirst = 0;
    @Title("last bin to display")
    private int fLast = 0;
    @Title("second bit status word")
    @FieldType(value = Type.kUShort)
    private short fBits2 = 0;
    @Title("on/off displaying time values instead of numerics")
    private boolean fTimeDisplay = false;
    @Title("Date&time format, ex: 09/12/99 12:34:00")
    private String fTimeFormat;
    @Title("List of labels")
    @FieldType(value = Type.kObjectP)
    private THashList fLabels;

    TAxis(String name, int nBins, double xMin, double xMax) {
        super(name, "");
        this.fNbins = nBins;
        this.fXmin = xMin;
        this.fXmax = xMax;
    }

    public void settAttAxis(TAttAxis tAttAxis) {
        this.tAttAxis = tAttAxis;
    }

    public void setfNbins(int fNbins) {
        this.fNbins = fNbins;
    }

    public void setfXmin(double fXmin) {
        this.fXmin = fXmin;
    }

    public void setfXmax(double fXmax) {
        this.fXmax = fXmax;
    }

    public void setfXbins(TArrayD fXbins) {
        this.fXbins = fXbins;
    }

    public void setfFirst(int fFirst) {
        this.fFirst = fFirst;
    }

    public void setfLast(int fLast) {
        this.fLast = fLast;
    }

    public void setfBits2(short fBits2) {
        this.fBits2 = fBits2;
    }

    public void setfTimeDisplay(boolean fTimeDisplay) {
        this.fTimeDisplay = fTimeDisplay;
    }

    public void setfTimeFormat(String fTimeFormat) {
        this.fTimeFormat = fTimeFormat;
    }

    public void setfLabels(THashList fLabels) {
        this.fLabels = fLabels;
    }

}
