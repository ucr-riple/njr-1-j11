package uk.ac.leeds.ccg.andyt.generic.visualisation.charts;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import uk.ac.leeds.ccg.andyt.generic.data.Generic_XYNumericalData;
import uk.ac.leeds.ccg.andyt.generic.execution.Generic_Runnable;
import uk.ac.leeds.ccg.andyt.generic.math.Generic_BigDecimal;
import uk.ac.leeds.ccg.andyt.generic.visualisation.Generic_Drawable_Interface;
import uk.ac.leeds.ccg.andyt.generic.visualisation.Generic_Visualisation;

/**
 * An class for creating 2D plot visualisations.
 */
public abstract class Abstract_Generic_Plot extends Generic_Runnable implements Generic_Drawable_Interface, Runnable {

    private Object[] data;
    private String format;
    private File file;
    private Graphics2D g2image;
    private Graphics2D g2;
    private BufferedImage _BufferedImage;
    private boolean drawOriginLinesOnPlot;
    private FontMetrics fontMetrics;
    private String title;
    //public int boundaryThickness;
    private int imageWidth;
    private int imageHeight;
    // dataWidth is the width in pixels of the data section part of the image 
    private int dataWidth;
    // dataHeight is the height in pixels of the data section part of the image 
    private int dataHeight;
    // dataStartRow is the top row index of the data section in the image
    private int dataStartRow;
    private int dataMiddleRow;
    // dataEndRow is the bottom row index of the data section in the image
    private int dataEndRow;
    // dataStartCol is the left column index of the data section in the image
    private int dataStartCol;
    //public int dataMiddleCol;
    // dataEndCol is the right column index of the data section in the image
    private int dataEndCol;
    // xAxisHeight is the height in pixels of the x axis
    private int xAxisHeight;
    // yAxisWidth is the width in pixels of the y axis
    private int yAxisWidth;
    // For storing the main x axis label text
    private String xAxisLabel;
    // For storing the main y axis label text
    private String yAxisLabel;
    private int extraWidthLeft;
    private int extraWidthRight;
    private int extraHeightTop;
    private int extraHeightBottom;
    // maxX is the maximum value of x in the data
    private BigDecimal maxX;
    // minX is the minimum value of x in the data
    private BigDecimal minX;
    // maxY is the maximum value of y in the data
    private BigDecimal maxY;
    // minY is the minimum value of y in the data
    private BigDecimal minY;
    private int decimalPlacePrecisionForCalculations;
    private int decimalPlacePrecisionForDisplay;
    private int significantDigits;
    private RoundingMode roundingMode;
    /**
     * cellHeight is for storing the height of a pixel in the data units of y
     */
    private BigDecimal cellHeight;
    /**
     * cellWidth is for storing the width of a pixel in the data units of x
     */
    private BigDecimal cellWidth;
    private BigDecimal cellHeightDiv2;
    private BigDecimal cellWidthDiv2;
    // originRow the row index on which the origin is located (y = 0)
    private int originRow;
    // originCol; the column index on which the origin is located (x = 0)
    private int originCol;
    private int legendHeight;
    private int legendWidth;
    private boolean addLegend;
    //MediaTracker mediaTracker;
    private int ageInterval;
    private Integer startAgeOfEndYearInterval;

    protected transient ExecutorService executorService;

    protected ExecutorService getExecutorService() {
        if (executorService == null) {
            //executorService = Executors.newFixedThreadPool(6);
            // The advantage of the Single ThreadExecutor is that it's queue is 
            // effectively unlimited.
            executorService = Executors.newSingleThreadExecutor();
        }
        return executorService;
    }
    public Object[] getData() {
        return data;
    }

    public void setData(Object[] data) {
        initData(data);
        initialiseParameters(data);
    }

    public void initData(Object[] data) {
        this.data = data;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     *
     * @param data
     */
    protected abstract void initialiseParameters(Object[] data);

    public Graphics2D getG2image() {
        return g2image;
    }

    protected void setG2image(Graphics2D g2image) {
        this.g2image = g2image;
    }

    public Graphics2D getG2() {
        return g2;
    }

    protected void setG2(Graphics2D g2) {
        this.g2 = g2;
    }

    public BufferedImage getBufferedImage() {
        return _BufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this._BufferedImage = bufferedImage;
    }

    public boolean isDrawOriginLinesOnPlot() {
        return drawOriginLinesOnPlot;
    }

    public void setDrawOriginLinesOnPlot(boolean drawOriginLinesOnPlot) {
        this.drawOriginLinesOnPlot = drawOriginLinesOnPlot;
    }

    public FontMetrics getFontMetrics() {
        return fontMetrics;
    }

    protected void setFontMetrics(FontMetrics fontMetrics) {
        this.fontMetrics = fontMetrics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    protected void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    protected void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getDataWidth() {
        if (dataWidth < 1) {
            return 1;
        }
        return dataWidth;
    }

    protected void setDataWidth(int dataWidth) {
        this.dataWidth = dataWidth;
    }

    public int getDataHeight() {
        if (dataHeight == 0) {
            //dataHeight = getImageHeight();
            dataHeight = 1;
        }
        return dataHeight;
    }

    protected void setDataHeight(int dataHeight) {
        this.dataHeight = dataHeight;
    }

    public int getDataStartRow() {
        return dataStartRow;
    }

    protected void setDataStartRow(int dataStartRow) {
        this.dataStartRow = dataStartRow;
    }

    public int getDataMiddleRow() {
        return dataMiddleRow;
    }

    protected void setDataMiddleRow(int dataMiddleRow) {
        this.dataMiddleRow = dataMiddleRow;
    }

    public int getDataEndRow() {
        return dataEndRow;
    }

    protected void setDataEndRow(int dataEndRow) {
        this.dataEndRow = dataEndRow;
    }

    public int getDataStartCol() {
        return dataStartCol;
    }

    protected void setDataStartCol(int dataStartCol) {
        this.dataStartCol = dataStartCol;
    }

    public int getDataEndCol() {
        return dataEndCol;
    }

    protected void setDataEndCol(int dataEndCol) {
        this.dataEndCol = dataEndCol;
    }

    public int getxAxisHeight() {
        return xAxisHeight;
    }

    protected void setxAxisHeight(int xAxisHeight) {
        this.xAxisHeight = xAxisHeight;
    }

    public int getyAxisWidth() {
        return yAxisWidth;
    }

    protected void setyAxisWidth(int yAxisWidth) {
        this.yAxisWidth = yAxisWidth;
    }

    public String getxAxisLabel() {
        return xAxisLabel;
    }

    protected void setxAxisLabel(String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
    }

    public String getyAxisLabel() {
        return yAxisLabel;
    }

    protected void setyAxisLabel(String yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
    }

    public int getExtraWidthLeft() {
        return extraWidthLeft;
    }

    protected void setExtraWidthLeft(int extraWidthLeft) {
        this.extraWidthLeft = extraWidthLeft;
    }

    public int getExtraWidthRight() {
        return extraWidthRight;
    }

    protected void setExtraWidthRight(int extraWidthRight) {
        this.extraWidthRight = extraWidthRight;
    }

    public int getExtraHeightTop() {
        return extraHeightTop;
    }

    protected void setExtraHeightTop(int extraHeightTop) {
        this.extraHeightTop = extraHeightTop;
    }

    public int getExtraHeightBottom() {
        return extraHeightBottom;
    }

    protected void setExtraHeightBottom(int extraHeightBottom) {
        this.extraHeightBottom = extraHeightBottom;
    }

    public final BigDecimal getMaxX() {
        if (maxX == null) {
            return new BigDecimal("0");
        }
        return maxX;
    }

    protected void setMaxX(BigDecimal maxX) {
        this.maxX = maxX;
    }

    public final BigDecimal getMinX() {
//        if (minX == null) {
//            return new BigDecimal("0");
//        }
        return minX;
    }

    protected void setMinX(BigDecimal minX) {
        this.minX = minX;
    }

    public BigDecimal getMaxY() {
        return maxY;
    }

    protected void setMaxY(BigDecimal maxY) {
        this.maxY = maxY;
    }

    public BigDecimal getMinY() {
        return minY;
    }

    protected void setMinY(BigDecimal minY) {
        this.minY = minY;
    }

    public int getDecimalPlacePrecisionForCalculations() {
        return decimalPlacePrecisionForCalculations;
    }

    protected void setDecimalPlacePrecisionForCalculations(int decimalPlacePrecisionForCalculations) {
        this.decimalPlacePrecisionForCalculations = decimalPlacePrecisionForCalculations;
    }

    public int getDecimalPlacePrecisionForDisplay() {
        return decimalPlacePrecisionForDisplay;
    }

    protected void setDecimalPlacePrecisionForDisplay(int decimalPlacePrecisionForDisplay) {
        this.decimalPlacePrecisionForDisplay = decimalPlacePrecisionForDisplay;
    }

    public int getSignificantDigits() {
        return significantDigits;
    }

    protected void setSignificantDigits(int significantDigits) {
        this.significantDigits = significantDigits;
    }

    protected RoundingMode getDefaultRoundingMode() {
        return RoundingMode.HALF_DOWN;
    }

    public RoundingMode getRoundingMode() {
        if (roundingMode == null) {
            return getDefaultRoundingMode();
        }
        return roundingMode;
    }

    protected void setRoundingMode(RoundingMode _RoundingMode) {
        this.roundingMode = _RoundingMode;
    }

    public BigDecimal getCellHeight() {
        if (cellHeight == null) {
            return new BigDecimal("1");
        }
//        if (cellHeight.compareTo(BigDecimal.ZERO) != 1) {
//// The problem with this is that later dividing by this value and rounding to 
//// an int using intValueExact throws an ArithmeticException 
////            return new BigDecimal(
////                    BigInteger.ONE,
////                    getDecimalPlacePrecisionForCalculations());
//            return BigDecimal.ONE;
//        }
        return cellHeight;
    }

    protected void setCellHeight(BigDecimal cellHeight) {
//        if (cellHeight.compareTo(BigDecimal.ONE) == -1) {
//            String msg = "Warning: Image and data area size too small for "
//                    + "rendering clearly. Suggest changing plot parameters to "
//                    + "generalise, accept unclear rendering, or increase sizes.";
//            System.out.println(msg);
//        }
        this.cellHeight = cellHeight;
    }

    public BigDecimal getCellWidth() {
        if (cellWidth == null) {
            return new BigDecimal("0");
        }
        return cellWidth;
    }

    protected void setCellWidth(BigDecimal cellWidth) {
        this.cellWidth = cellWidth;
    }

    public int getOriginRow() {
        return originRow;
    }

    protected void setOriginRow(int originRow) {
        this.originRow = originRow;
    }

    public int getOriginCol() {
        return originCol;
    }

    protected void setOriginCol(int originCol) {
        this.originCol = originCol;
    }

    public boolean isAddLegend() {
        return addLegend;
    }

    public void setAddLegend(boolean addLegend) {
        this.addLegend = addLegend;
    }

    public int getLegendHeight() {
        return legendHeight;
    }

    protected void setLegendHeight(int legendHeight) {
        this.legendHeight = legendHeight;
    }

    public int getLegendWidth() {
        return legendWidth;
    }

    protected void setLegendWidth(int legendWidth) {
        this.legendWidth = legendWidth;
    }

    public int getAgeInterval() {
        return ageInterval;
    }

    protected void setAgeInterval(int ageInterval) {
        this.ageInterval = ageInterval;
    }

    public int getStartAgeOfEndYearInterval() {
        return startAgeOfEndYearInterval;
    }

    protected void setStartAgeOfEndYearInterval(Integer startAgeOfEndYearInterval) {
        this.startAgeOfEndYearInterval = startAgeOfEndYearInterval;
    }

    /**
     * 
     * @param executorService
     * @param file
     * @param format
     * @param title
     * @param dataWidth
     * @param dataHeight
     * @param xAxisLabel
     * @param yAxisLabel
     * @param drawOriginLinesOnPlot
     * @param decimalPlacePrecisionForCalculations
     * @param significantDigits
     * @param roundingMode 
     */
    protected void init(
            ExecutorService executorService,
            File file,
            String format,
            String title,
            int dataWidth,
            int dataHeight,
            String xAxisLabel,
            String yAxisLabel,
            boolean drawOriginLinesOnPlot,
            int decimalPlacePrecisionForCalculations,
            int significantDigits,
            RoundingMode roundingMode) {
        this.executorService = executorService;
        this.file = file;
        this.format = format;
        this.title = title;
        // initialise this.imageWidth
        this.imageWidth = dataWidth;
        // initialise this.imageHeight
        this.imageHeight = dataHeight;
        this.dataWidth = dataWidth;
        this.dataHeight = dataHeight;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.drawOriginLinesOnPlot = drawOriginLinesOnPlot;
        this.dataStartRow = 0;//extraHeightTop + boundaryThickness;
        this.dataMiddleRow = dataStartRow + dataHeight / 2;
        this.dataEndRow = dataStartRow + dataHeight;
        this.dataStartCol = 0;
        this.dataEndCol = dataStartCol + dataWidth;
        this.decimalPlacePrecisionForCalculations = decimalPlacePrecisionForCalculations;
        this.decimalPlacePrecisionForDisplay = significantDigits;
        this.significantDigits = significantDigits;
        this.roundingMode = roundingMode;
//        if (data == null) {
//            setData(getDefaultData());
//        } else {
//            initialiseParameters(data);
//        }
    }

    public abstract Object[] getDefaultData();

//    public void output() {
//            Generic_Visualisation.saveImage(
//                    this,
//                    getBufferedImage(),
//                    0,
//                    getFormat(),
//                    getFile());
//    }

    protected void resize(JFrame f) {
        f.pack();
        f.setSize(
                imageWidth,
                imageHeight);
    }

    public void initG2(Graphics g) {
        g2 = (Graphics2D) g;
    }

    public void initFontMetrics() {
        if (fontMetrics == null) {
            if (g2 != null) {
                fontMetrics = g2.getFontMetrics();
            }
            Font f = Generic_Visualisation.getDefaultFont();
            fontMetrics = new Canvas().getFontMetrics(f);
        }
    }

    public void initG2Image() {
        _BufferedImage = new BufferedImage(
                imageWidth,
                imageHeight,
                BufferedImage.TYPE_INT_ARGB);
        g2image = (Graphics2D) _BufferedImage.getGraphics();
        g2image.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //mediaTracker = new MediaTracker(this);
        //mediaTracker.addImage(_BufferedImage, 0);
    }

    public BigDecimal imageRowToYCoordinate(
            double row) {
        return dataRowToYCoordinate(row - getDataStartRow());
        //return BigDecimal.valueOf(dataHeight - row).multiply(cellHeight).subtract(halfCellHeight);
        //return BigDecimal.valueOf(dataHeight - row).multiply(cellHeight);
    }

    public BigDecimal imageColToXCoordinate(
            double col) {
        return dataColToXCoordinate(col - getDataStartCol());
//        //return BigDecimal.valueOf(dataWidth - col).multiply(cellWidth).add(this.minX);
//        return BigDecimal.valueOf(dataWidth - col).multiply(cellWidth);
    }

    public BigDecimal dataRowToYCoordinate(double row) {
        BigDecimal result = BigDecimal.ONE; // default value
        BigDecimal theMinY = getMinY();
        if (theMinY != null) {
            result = BigDecimal.valueOf(getDataHeight() - row).multiply(getCellHeight()).add(theMinY);
        }
        return result;
    }

    public BigDecimal dataColToXCoordinate(double col) {
        BigDecimal result = BigDecimal.ONE; // default value
        BigDecimal theMinX = getMinX();
        if (theMinX != null) {
            result = BigDecimal.valueOf(col).multiply(getCellWidth()).add(theMinX);
        }
        return result;
    }

    /**
     * Calculates and returns the row and column in the image for the data at
 coordinate titleTextWidth, titleTextHeight as a Point2D.Double using
 RoundingMode roundingMode
     *
     * @param p
     * @return a Point2D.Double located at pixel(col, row)
     */
    public Generic_XYNumericalData dataPointToCoordinate(
            Point2D p) {
        Generic_XYNumericalData result = new Generic_XYNumericalData();
        BigDecimal x = dataRowToYCoordinate(p.getX());
        BigDecimal y = dataColToXCoordinate(p.getY());
        result.setX(x);
        result.setY(y);
        return result;
    }

    /**
     * Calculates and returns the column in the image for the data with value
 titleTextWidth RoundingMode roundingMode is used.
     *
     * @param x
     * @return the column in the image for the data with value titleTextWidth
     */
    public int coordinateToScreenCol(
            BigDecimal x) {
        return coordinateToScreenCol(
                x,
                getRoundingMode());
    }

    /**
     * Calculates and returns the column in the image for the data with value
     * titleTextWidth using RoundingMode aRoundingMode
     *
     * @param x
     * @param titleTextWidth
     * @param roundingMode
     * @return the column in the image for the data with value titleTextWidth
     */
    public int coordinateToScreenCol(
            BigDecimal x,
            RoundingMode roundingMode) {
        int col = 0;
        BigDecimal theCellWidth = getCellWidth();
        BigDecimal theMinX = getMinX();
        if (theMinX != null) {
            if (theCellWidth.compareTo(BigDecimal.ZERO) != 0) {
//            col = Generic_BigDecimal.divideRoundToFixedDecimalPlaces(
//                    x.subtract(getMinX()),
//                    theCellWidth,
//                    0,
//                    roundingMode).intValueExact();
                col = Generic_BigDecimal.divideRoundToFixedDecimalPlaces(
                        x.subtract(theMinX),
                        theCellWidth,
                        0,
                        roundingMode).intValue();
            }
        }
        col += getDataStartCol();
        return col;
    }

    /**
     * Calculates and returns the row in the image for the data with value
     * titleTextHeight. RoundingMode roundingMode is used.
     *
     * @param y
     * @return the row in the image for the data with value titleTextHeight
     */
    public int coordinateToScreenRow(
            BigDecimal y) {
        return coordinateToScreenRow(y,
                getRoundingMode());
    }

    /**
     * Calculates and returns the row in the image for the data with value
     * titleTextHeight using RoundingMode aRoundingMode
     *
     * @param y
     * @param aRoundingMode
     * @return the row in the image for the data with value titleTextHeight
     */
    public int coordinateToScreenRow(
            BigDecimal y,
            RoundingMode aRoundingMode) {
        int row = 0;
        BigDecimal theCellHeight = getCellHeight();
        BigDecimal theMinY = getMinY();
        if (theMinY != null) {
            if (theCellHeight != null) {
                if (theCellHeight.compareTo(BigDecimal.ZERO) != 0) {
                    row = getDataHeight() - Generic_BigDecimal.divideRoundToFixedDecimalPlaces(
                            y.subtract(theMinY),
                            getCellHeight(),
                            0,
                            aRoundingMode).intValue();
//                row = getDataHeight() - Generic_BigDecimal.divideRoundToFixedDecimalPlaces(
//                        y.subtract(getMinY()),
//                        getCellHeight(),
//                        0,
//                        aRoundingMode).intValueExact();
                }
            }
        }
        row += getDataStartRow();
        return row;
    }

    /**
     * Calculates and returns the row and column in the image for the data at
 coordinate titleTextWidth, titleTextHeight as a Point2D.Double using
 RoundingMode roundingMode
     *
     * @param x
     * @param titleTextWidth
     * @param y
     * @return a Point2D.Double located at pixel(col, row)
     */
    public Point2D coordinateToScreen(
            BigDecimal x,
            BigDecimal y) {
        Point2D result = new Point2D.Double();
        //System.out.println("titleTextWidth " + titleTextWidth);
        //System.out.println("titleTextHeight " + titleTextHeight);
        int row = coordinateToScreenRow(y);
        int col = coordinateToScreenCol(x);
        result.setLocation(col, row);
        //System.out.println("result " + result);
        return result;
    }

    //public abstract void initialiseParameters(Object[] data);
    public void setCellHeight() {
        int theDecimalPlacePrecisionForCalculations = getDecimalPlacePrecisionForCalculations();
        RoundingMode roundingMode = getRoundingMode();
        BigDecimal theMinY = getMinY();
        if (theMinY == null) {
            cellHeight = BigDecimal.valueOf(2);
            cellHeightDiv2 = BigDecimal.ONE;
        } else {
            cellHeight = Generic_BigDecimal.divideRoundIfNecessary(
                    getMaxY().subtract(theMinY),
                    BigDecimal.valueOf(getDataHeight()),
                    theDecimalPlacePrecisionForCalculations,
                    roundingMode);
            cellHeightDiv2 = Generic_BigDecimal.divideRoundIfNecessary(
                    cellHeight,
                    BigDecimal.valueOf(2),
                    theDecimalPlacePrecisionForCalculations,
                    roundingMode);
        }
    }

    public void setCellWidth() {
        int theDecimalPlacePrecisionForCalculations = getDecimalPlacePrecisionForCalculations();
        RoundingMode roundingMode = getRoundingMode();
        BigDecimal theMinX = getMinX();
        if (theMinX == null) {
            cellWidth = BigDecimal.valueOf(2);
            cellWidthDiv2 = BigDecimal.ONE;
        } else {
            cellWidth = Generic_BigDecimal.divideRoundIfNecessary(
                    getMaxX().subtract(theMinX),
                    BigDecimal.valueOf(getDataWidth()),
                    theDecimalPlacePrecisionForCalculations,
                    roundingMode);
            cellWidthDiv2 = Generic_BigDecimal.divideRoundIfNecessary(
                    cellWidth,
                    BigDecimal.valueOf(2),
                    theDecimalPlacePrecisionForCalculations,
                    roundingMode);
        }
    }

    public void setOriginRow() {
        int theDataEndRow = getDataEndRow();
        BigDecimal theMaxY = getMaxY();
        if (theMaxY == null) {
            originRow = theDataEndRow;
        } else {
            if (theMaxY.compareTo(BigDecimal.ZERO) == 0) {
                originRow = theDataEndRow;
            } else {
                if (cellHeight.compareTo(BigDecimal.ZERO) == 0) {
                    originRow = theDataEndRow;
                } else {
//                originRow = Generic_BigDecimal.divideRoundIfNecessary(
//                        maxY,
//                        cellHeight,
//                        0,
//                        roundingMode).intValueExact()
//                        + dataStartRow;
                    originRow = coordinateToScreenRow(BigDecimal.ZERO);
                }
            }
        }
    }

    public abstract void setOriginCol();

    public void setYAxisWidth(int width) {
        //if (width > 0) {
        if (width > yAxisWidth) {
            int diff = width - yAxisWidth;
            originCol += diff;
            dataStartCol += diff;
            //dataMiddleCol += diff;
            dataEndCol += diff;
            yAxisWidth = width;
            if (width > extraWidthLeft) {
                imageWidth += diff;
                extraWidthLeft = width;
            }
        }
    }

    public void setXAxisHeight(int height) {
        //if (height > 0) {
        if (height > xAxisHeight) {
            int diff = height - xAxisHeight;
            xAxisHeight = height;
            if (height > extraHeightBottom) {
                imageHeight += diff;
                extraHeightBottom = height;
            }
        }
    }

//    public void setLegendHeight(int height) {
//        if (height > 0) {
//            int diff = height - this.legendHeight;
//            imageHeight += diff;
//            this.legendHeight = height;
//        }
//    }
    public void setPaint(Color c) {
//        g2.setPaint(c);
//        g2image.setPaint(c);
        if (g2 != null) {
            g2.setPaint(c);
        }
        if (g2image != null) {
            g2image.setPaint(c);
        }
    }

    public void draw(Line2D line) {
//        g2.draw(line);
//        g2image.draw(line);
        if (g2 != null) {
            g2.draw(line);
        }
        if (g2image != null) {
            g2image.draw(line);
        }
    }

    public void draw(
            Point2D point) {
//        // draw as a line with zero length
//        draw(new Line2D.Double(point, point));
        // draw as an x cross
        int crossLength = 4;
        Point2D a = new Point2D.Double(
                (int) point.getX() - crossLength,
                (int) point.getY() - crossLength);
        Point2D b = new Point2D.Double(
                (int) point.getX() + crossLength,
                (int) point.getY() + crossLength);
        draw(new Line2D.Double(a, b));
        Point2D c = new Point2D.Double(
                (int) point.getX() - crossLength,
                (int) point.getY() + crossLength);
        Point2D d = new Point2D.Double(
                (int) point.getX() + crossLength,
                (int) point.getY() - crossLength);
        draw(new Line2D.Double(c, d));
//        // draw as an x cross
//        double crossLength = 1.0d;
//        Point2D a = new Point2D.Double(
//                point.getX() - crossLength, 
//                point.getY() - crossLength);
//        Point2D b = new Point2D.Double(
//                point.getX() + crossLength, 
//                point.getY() + crossLength);
//        draw(new Line2D.Double(a, b));
//        Point2D c = new Point2D.Double(
//                point.getX() - crossLength,
//                point.getY() + crossLength);
//        Point2D d = new Point2D.Double(
//                point.getX() + crossLength,
//                point.getY() - crossLength);
//        draw(new Line2D.Double(c, d));
    }

    public void drawString(
            String text,
            int col,
            int row) {
//        g2.drawString(text, col, row);
//        g2image.drawString(text, col, row);
        if (g2 != null) {
            g2.drawString(text, col, row);
        }
        if (g2image != null) {
            g2image.drawString(text, col, row);
        }
    }

    public void fillRect(
            int col,
            int row,
            int width,
            int height) {
//        g2.fillRect(col, row, width, height);
//        g2image.fillRect(col, row, width, height);
        if (g2 != null) {
            g2.fillRect(col, row, width, height);
        }
        if (g2image != null) {
            g2image.fillRect(col, row, width, height);
        }
    }

    public void draw(Rectangle2D aRectangle2D) {
//        g2.draw(aRectangle2D);
//        g2image.draw(aRectangle2D);
        if (g2 != null) {
            g2.draw(aRectangle2D);
        }
        if (g2image != null) {
            g2image.draw(aRectangle2D);
        }
    }

    public void transform(AffineTransform aAffineTransform) {
//        g2.transform(aAffineTransform);
//        g2image.transform(aAffineTransform);
        if (g2 != null) {
            g2.transform(aAffineTransform);
        }
        if (g2image != null) {
            g2image.transform(aAffineTransform);
        }
    }

    public void setTransform(AffineTransform aAffineTransform) {
//        g2.setTransform(aAffineTransform);
//        g2image.setTransform(aAffineTransform);
        if (g2 != null) {
            g2.setTransform(aAffineTransform);
        }
        if (g2image != null) {
            g2image.setTransform(aAffineTransform);
        }
    }

    public int getDefaultScaleTickLength() {
        return 5;
    }

    public int getDefaultScaleTickAndTextSeparation() {
        return 3;
    }

    public int getDefaultPartTitleGap() {
        return 2;
    }

    public void drawAxes(
            int interval,
            int startAgeOfEndYearInterval) {
        drawAxes(
                this,
                interval,
                startAgeOfEndYearInterval);
    }

    public void drawAxes(
            Abstract_Generic_Plot plot,
            int interval,
            int startAgeOfEndYearInterval) {
        int yAxisExtraWidthLeft;
//        int yAxisExtraHeightTop = 0;
//        int yAxisExtraHeightBottom = 0;
        int xAxisExtraWidthLeft;
        int xAxisExtraWidthRight;
        int xAxisExtraHeightBottom;
        int scaleTickLength = getDefaultScaleTickLength();
        int scaleTickAndTextSeparation = getDefaultScaleTickAndTextSeparation();
        int partTitleGap = getDefaultPartTitleGap();
        int textHeight = getTextHeight();
        int seperationDistanceOfAxisAndData = textHeight;
        // Draw Y axis
        int[] yAxisDimensions = plot.drawYAxis(
                interval,
                textHeight,
                startAgeOfEndYearInterval,
                scaleTickLength,
                scaleTickAndTextSeparation,
                partTitleGap,
                seperationDistanceOfAxisAndData);
        yAxisExtraWidthLeft = yAxisDimensions[0];
        if (yAxisExtraWidthLeft > extraWidthLeft) {
            int diff = yAxisExtraWidthLeft - extraWidthLeft;
            imageWidth += diff;
            dataStartCol += diff;
            dataEndCol += diff;
            extraWidthLeft = yAxisExtraWidthLeft;
            setOriginCol();
        }
        yAxisWidth = yAxisExtraWidthLeft;
        // Draw X axis
        int[] xAxisDimensions = plot.drawXAxis(
                textHeight,
                scaleTickLength,
                scaleTickAndTextSeparation,
                partTitleGap,
                seperationDistanceOfAxisAndData);
        xAxisExtraWidthLeft = xAxisDimensions[0];
        xAxisExtraWidthRight = xAxisDimensions[1];
        xAxisExtraHeightBottom = xAxisDimensions[2];
        if (xAxisExtraWidthLeft > extraWidthLeft) {
            int diff = xAxisExtraWidthLeft - dataStartCol;
            imageWidth += diff;
            dataStartCol += diff;
            dataEndCol += diff;
            extraWidthLeft = xAxisExtraWidthLeft;
            setOriginCol();
        }
        if (xAxisExtraWidthRight > extraWidthRight) {
            imageWidth += xAxisExtraWidthRight - extraWidthRight;
            extraWidthRight = xAxisExtraWidthRight;
        }
        xAxisHeight = xAxisExtraHeightBottom;
        if (xAxisExtraHeightBottom > extraHeightBottom) {
            imageHeight += xAxisExtraHeightBottom - extraHeightBottom;
            extraHeightBottom = xAxisExtraHeightBottom;
        }
    }

    public abstract int[] drawXAxis(
            int textHeight,
            int scaleTickLength,
            int scaleTickAndTextSeparation,
            int partTitleGap,
            int seperationDistanceOfAxisAndData);

    public abstract int[] drawYAxis(
            int interval,
            int textHeight,
            int startOfEndInterval,
            int scaleTickLength,
            int scaleTickAndTextSeparation,
            int partTitleGap,
            int seperationDistanceOfAxisAndData);

    public void drawOutline() {
        //Color color = g2.getColor();
        setPaint(Color.WHITE);
        Rectangle2D rect = new Rectangle2D.Double(0, 0, imageWidth, imageHeight);
        fillRect(0, 0, imageWidth, imageHeight);
        setPaint(Color.DARK_GRAY);
        draw(rect);
        //setPaint(color);
    }

    /**
     * The title is draw above the data area and centred on the whole width of
     * the image.
     *
     * @param title
     */
    public void drawTitle(
            String title) {
        setPaint(Color.BLACK);
        int oldExtraHeightTop = extraHeightTop;
        int textHeight = getTextHeight();
        extraHeightTop = textHeight * 3;
        int titleTextWidth = getTextWidth(title) + (textHeight * 2);
        if (titleTextWidth <= imageWidth) {
            int startPosition = (imageWidth / 2) - (titleTextWidth / 2) + textHeight;
            if (startPosition < 0) {
                startPosition = 0;
            }
            drawString(title, startPosition, textHeight * 2);
        }
        if (titleTextWidth > imageWidth) {
            int extrax = titleTextWidth - imageWidth;
            extraWidthRight = extrax / 2;
            int newExtraWidthLeft = extrax - extraWidthRight;
            if (newExtraWidthLeft > extraWidthLeft) {
                extraWidthLeft = newExtraWidthLeft;
                dataStartCol = extraWidthLeft;
                dataEndCol = dataStartCol + dataWidth;
                setOriginCol();
            }
            imageWidth = titleTextWidth;
        }
        if (extraHeightTop > oldExtraHeightTop) {
            int extraHeightDiff = extraHeightTop - oldExtraHeightTop;
            imageHeight += extraHeightDiff;
            dataStartRow = extraHeightTop;
            dataEndRow = dataStartRow + dataHeight;
            dataMiddleRow = ((dataEndRow - dataStartRow) / 2) + dataStartRow;
            setOriginRow();
        }
//        // Debug
//        System.out.println("imageHeight " + imageHeight);
    }

    public int getTextWidth(String text_String) {
        char[] text_charArray = text_String.toCharArray();
        int result = fontMetrics.charsWidth(text_charArray, 0, text_charArray.length);
        return result;
    }

    public int getTextHeight() {
        if (fontMetrics == null) {
            initFontMetrics();
        }
        return fontMetrics.getHeight();
    }

    public void writeText(
            String text,
            double angle,
            int startCol,
            int startRow) {
        // Store the current transform to return the graphics environment to
        AffineTransform currentTransform = null;
        if (g2 != null) {
            currentTransform = g2.getTransform();
        }
        AffineTransform newTransform = AffineTransform.getRotateInstance(
                angle,
                startCol,
                startRow);
        transform(newTransform);
        drawString(
                text,
                startCol,
                startRow);
        if (currentTransform != null) {
            setTransform(currentTransform);
        }

    }

    @Override
    public Dimension draw() {
        drawOutline();
        drawTitle(getTitle());
        drawAxes(
                getAgeInterval(),
                getStartAgeOfEndYearInterval());
        drawData();
        Dimension newDim = new Dimension(getImageWidth(), getImageHeight());
        return newDim;
    }

    public abstract void drawData();

    @Override
    public Dimension draw(Graphics2D g2) {
        this.g2 = g2;
        return draw();
    }
}
