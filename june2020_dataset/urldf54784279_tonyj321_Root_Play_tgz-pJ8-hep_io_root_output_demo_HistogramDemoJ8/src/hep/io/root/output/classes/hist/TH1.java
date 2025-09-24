package hep.io.root.output.classes.hist;

import java.io.IOException;
import hep.io.root.output.RootOutput;
import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.Type;
import hep.io.root.output.annotations.Counter;
import hep.io.root.output.annotations.FieldType;
import hep.io.root.output.annotations.Super;
import hep.io.root.output.annotations.Title;
import hep.io.root.output.classes.TArrayD;
import hep.io.root.output.classes.TList;
import hep.io.root.output.classes.TNamed;

/**
 * The base histogram class.
 *
 * @see <a href="http://root.cern.ch/root/htmldoc/TH1.html">TH1</a>
 * @author tonyj
 */
@ClassDef(version = 6, checkSum = -381522971)
@Title("1-Dim histogram base class")
public class TH1 extends TNamed {

    @Super
    private TAttLine tAttLine = new TAttLine();
    @Super
    private TAttFill tAttFill = new TAttFill();
    @Super
    private TAttMarker tAttMarker = new TAttMarker();
    @Title("number of bins(1D), cells (2D) +U/Overflows")
    int fNcells;
    @Title("X axis descriptor")
    private TAxis fXaxis;
    @Title("Y axis descriptor")
    TAxis fYaxis;
    @Title("Z axis descriptor")
    private TAxis fZaxis;
    @Title("(1000*offset) for bar charts or legos")
    private short fBarOffset = 0;
    @Title("(1000*width) for bar charts or legos")
    private short fBarWidth = 1000;
    @Title("Number of entries")
    double fEntries = 0;
    @Title("Total Sum of weights")
    double fTsumw = 0;
    @Title("Total Sum of squares of weights")
    double fTsumw2 = 0;
    @Title("Total Sum of weight*X")
    double fTsumwx = 0;
    @Title("Total Sum of weight*X*X")
    double fTsumwx2 = 0;
    @Title("Maximum value for plotting")
    private double fMaximum = -1111;
    @Title("Minimum value for plotting")
    private double fMinimum = -1111;
    @Title("Normalization factor")
    private double fNormFactor = 0;
    @Title("Array to display contour levels")
    private TArrayD fContour;
    @Title("Array of sum of squares of weights")
    private TArrayD fSumw2;
    @Title("histogram options")
    private String fOption = "";
    @Title("Pointer to list of functions (fits and user)")
    @FieldType(value = Type.kObjectp)
    private TList fFunctions = new TList();
    @Title("fBuffer size")
    private int fBufferSize = 0;
    @Title("entry buffer")
    @Counter("fBufferSize")
    private double[] fBuffer = null;
    private transient EBinErrorOpt fBinStatErrOpt = EBinErrorOpt.kNormal;

    private enum EBinErrorOpt {

        kNormal, // errors with Normal (Wald) approximation: errorUp=errorLow= sqrt(N)
        kPoisson, // errors from Poisson interval at 68.3% (1 sigma)
        kPoisson2 // errors from Poisson interval at 95% CL (~ 2 sigma)
    }

    public TH1(String name, int nBins, double xMin, double xMax) {
        super(name, "");
        fXaxis = new TAxis("xaxis", nBins, xMin, xMax);
        fYaxis = new TAxis("yaxis", 1, 0, 1);
        fZaxis = new TAxis("zAxis", 1, 0, 1);
        fNcells = nBins + 2;
    }

    private void write(RootOutput out) throws IOException {
        out.writeObject(tAttLine);
        out.writeObject(tAttFill);
        out.writeObject(tAttMarker);
        out.writeInt(fNcells);
        out.writeObject(fXaxis);
        out.writeObject(fYaxis);
        out.writeObject(fZaxis);
        out.writeShort(fBarOffset);
        out.writeShort(fBarWidth);
        out.writeDouble(fEntries);
        out.writeDouble(fTsumw);
        out.writeDouble(fTsumw2);
        out.writeDouble(fTsumwx);
        out.writeDouble(fTsumwx2);
        out.writeDouble(fMaximum);
        out.writeDouble(fMinimum);
        out.writeDouble(fNormFactor);
        out.writeObject(fContour);
        out.writeObject(fSumw2);
        out.writeObject(fOption);
        out.writeObject(fFunctions);
        out.writeInt(fBufferSize);
        for (int i = 0; i < fBufferSize; i++) {
            out.writeDouble(fBuffer[i]);
        }
        out.writeByte(fBinStatErrOpt.ordinal());
    }

    public void setEntries(double fEntries) {
        this.fEntries = fEntries;
    }
    

    public void settAttLine(TAttLine tAttLine) {
        this.tAttLine = tAttLine;
    }

    public void settAttFill(TAttFill tAttFill) {
        this.tAttFill = tAttFill;
    }

    public void settAttMarker(TAttMarker tAttMarker) {
        this.tAttMarker = tAttMarker;
    }

    public void setfNcells(int fNcells) {
        this.fNcells = fNcells;
    }

    public void setfXaxis(TAxis fXaxis) {
        this.fXaxis = fXaxis;
    }

    public void setfYaxis(TAxis fYaxis) {
        this.fYaxis = fYaxis;
    }

    public void setfZaxis(TAxis fZaxis) {
        this.fZaxis = fZaxis;
    }

    public void setfBarOffset(short fBarOffset) {
        this.fBarOffset = fBarOffset;
    }

    public void setfBarWidth(short fBarWidth) {
        this.fBarWidth = fBarWidth;
    }

    public void setfEntries(double fEntries) {
        this.fEntries = fEntries;
    }

    public void setfTsumw(double fTsumw) {
        this.fTsumw = fTsumw;
    }

    public void setfTsumw2(double fTsumw2) {
        this.fTsumw2 = fTsumw2;
    }

    public void setfTsumwx(double fTsumwx) {
        this.fTsumwx = fTsumwx;
    }

    public void setfTsumwx2(double fTsumwx2) {
        this.fTsumwx2 = fTsumwx2;
    }

    public void setfMaximum(double fMaximum) {
        this.fMaximum = fMaximum;
    }

    public void setfMinimum(double fMinimum) {
        this.fMinimum = fMinimum;
    }

    public void setfNormFactor(double fNormFactor) {
        this.fNormFactor = fNormFactor;
    }

    public void setfContour(TArrayD fContour) {
        this.fContour = fContour;
    }

    public void setfSumw2(TArrayD fSumw2) {
        this.fSumw2 = fSumw2;
    }

    public void setfOption(String fOption) {
        this.fOption = fOption;
    }

    public void setfFunctions(TList fFunctions) {
        this.fFunctions = fFunctions;
    }

    public void setfBufferSize(int fBufferSize) {
        this.fBufferSize = fBufferSize;
    }

    public void setfBuffer(double[] fBuffer) {
        this.fBuffer = fBuffer;
    }
    

    public TAttLine gettAttLine() {
        return tAttLine;
    }

    public TAttFill gettAttFill() {
        return tAttFill;
    }

    public TAttMarker gettAttMarker() {
        return tAttMarker;
    }

    public int getfNcells() {
        return fNcells;
    }

    public TAxis getfXaxis() {
        return fXaxis;
    }

    public TAxis getfYaxis() {
        return fYaxis;
    }

    public TAxis getfZaxis() {
        return fZaxis;
    }

    public short getfBarOffset() {
        return fBarOffset;
    }

    public short getfBarWidth() {
        return fBarWidth;
    }

    public double getfEntries() {
        return fEntries;
    }

    public double getfTsumw() {
        return fTsumw;
    }

    public double getfTsumw2() {
        return fTsumw2;
    }

    public double getfTsumwx() {
        return fTsumwx;
    }

    public double getfTsumwx2() {
        return fTsumwx2;
    }

    public double getfMaximum() {
        return fMaximum;
    }

    public double getfMinimum() {
        return fMinimum;
    }

    public double getfNormFactor() {
        return fNormFactor;
    }

    public TArrayD getfContour() {
        return fContour;
    }

    public TArrayD getfSumw2() {
        return fSumw2;
    }

    public String getfOption() {
        return fOption;
    }

    public TList getfFunctions() {
        return fFunctions;
    }

    public int getfBufferSize() {
        return fBufferSize;
    }

    public double[] getfBuffer() {
        return fBuffer;
    }

}
