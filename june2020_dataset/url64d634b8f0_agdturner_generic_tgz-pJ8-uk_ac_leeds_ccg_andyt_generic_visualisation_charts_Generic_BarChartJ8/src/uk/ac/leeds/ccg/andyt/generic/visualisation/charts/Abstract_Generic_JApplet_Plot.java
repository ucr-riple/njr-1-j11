/**
 * Copyright 2012 Andy Turner, The University of Leeds, UK
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.ac.leeds.ccg.andyt.generic.visualisation.charts;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JApplet;
import javax.swing.JFrame;
import uk.ac.leeds.ccg.andyt.generic.visualisation.Generic_Visualisation;

/**
 * An abstract class for creating 2D plot visualisations.
 */
public abstract class Abstract_Generic_JApplet_Plot extends JApplet {

    Generic_Plot _Generic_Plot;

    public void run(JFrame f) {
        initPaint(f);
        // This is done twice as the image grows to accomodate the X axis label that sticks out
        paint(_Generic_Plot.getG2());
        resize(f);
        /*
         * To show/not show the JFrame on screen and dispose/not disposed of it 
         * then uncomment  and comment the next 2 lines respectively as 
         * appropriate
         */
        f.setVisible(true);
        //f.dispose();

        /*
         * Save the image to a File 
         */
        Generic_Visualisation.saveImage(
                null,
                _Generic_Plot,
                _Generic_Plot.getBufferedImage(),
                10000,
                _Generic_Plot.getFormat(),
                _Generic_Plot.getFile());            
    }
    
    @Override
    public void init() {
//        GridBagLayout aGridBagLayout = new GridBagLayout();
//        getContentPane().setLayout(aGridBagLayout);
//        GridBagConstraints aGridBagConstraints = new GridBagConstraints();
        //Initialize drawing colors
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
    }

    protected void initPaint(JFrame f) {
        f.getContentPane().add("Center", this);
        init();
        f.pack();
        f.setSize(
                getImageWidth(),
                getImageHeight());
        f.setVisible(false);
    }

    protected void resize(JFrame f) {
        f.pack();
        // Hardcoded extra height needed for frame boundary
        int extraHeight = 30;
        // Hardcoded extra width needed for frame boundary
        int extraWidth = 9;
        f.setSize(
                getImageWidth() + extraWidth,
                getImageHeight() + extraHeight);
    }

    /**
     * Implementations are expected to override this yet call to it
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        initG2Image();
        initG2();
        _Generic_Plot.draw();
    }

    public void initG2() {
        Graphics g = getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        setG2(g2);
        drawOutline();
        //g.dispose();
        setFontMetrics(g2.getFontMetrics());
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void initG2Image() {
        BufferedImage bi = new BufferedImage(
                getImageWidth(),
                getImageHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2image = (Graphics2D) bi.getGraphics();
        setG2image(g2image);
        g2image.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }
    
//    //public abstract Dimension draw();
//
//    public abstract void drawData();

    public void draw(Line2D l) {
        _Generic_Plot.draw(l);
    }

//    public void drawTitle(String t) {
//        _Generic_Plot.drawTitle(t);
//    }
    /**
     * The title is draw above the data area and centred on the whole width of
     * the image.
     *
     * @param title
     */
    //@Override
    public void drawTitle(
            String title) {
        _Generic_Plot.drawTitle(title);
//        setPaint(Color.BLACK);
//        int oldExtraHeightTop = getExtraHeightTop();
//        int textHeight = getTextHeight();
//        setExtraHeightTop(Math.max(oldExtraHeightTop, textHeight * 3));
//        //int oldImageHeight = imageHeight;
//        int titleTextWidth = getTextWidth(title) + (textHeight * 2);
//        int imageWidth = getImageWidth();
//        if (titleTextWidth <= imageWidth) {
//            int startPosition = (imageWidth / 2) - (titleTextWidth / 2) + textHeight;
//            if (startPosition < 0) {
//                startPosition = 0;
//            }
//            drawString(title, startPosition, textHeight * 2);
//        }
//        if (titleTextWidth > imageWidth) {
//            //int titleTextHeight = getTextHeight() + titleHeight / 4;
//            int extrax = titleTextWidth - imageWidth;
//            int extraWidthRight = Math.max(getExtraWidthRight(), extrax / 2);
//            setExtraWidthRight(extraWidthRight);
//            int newExtraWidthLeft = extrax - extraWidthRight;
//            if (newExtraWidthLeft > getExtraWidthLeft()) {
//                setExtraWidthLeft(newExtraWidthLeft);
//                setDataStartCol(getExtraWidthLeft());
//                setDataEndCol(getDataStartCol() + getDataWidth());
//                setOriginCol();
//            }
//            setImageWidth(titleTextWidth);
//        }
//        //+ (boundaryThickness * 2);
//        if (getExtraHeightTop() > oldExtraHeightTop) {
//            int extraHeightDiff = getExtraHeightTop() - oldExtraHeightTop;
//            setImageHeight(getImageHeight() + extraHeightDiff);
//            setDataStartRow(getExtraHeightTop());
//            setDataEndRow(getDataStartRow() + getDataHeight());
//            setDataMiddleRow(((getDataEndRow() - getDataStartRow()) / 2) + getDataStartRow());
//            setOriginRow();
//        }
//        //imageWidth = extrax + dataWidth + (boundaryThickness * 2);
//        //imageHeight += titleTextHeight + dataHeight;// + (boundaryThickness * 2);
//
////        // Debug
////        System.out.println("imageHeight " + imageHeight);
    }

    public void drawOutline() {
        _Generic_Plot.drawOutline();
    }

    public void setPaint(Color c) {
        _Generic_Plot.setPaint(c);
    }

    public void drawAxes(
            int ageInterval,
            int startAgeOfEndYearInterval) {
        _Generic_Plot.drawAxes(
                ageInterval,
                startAgeOfEndYearInterval);
    }

//    /**
//     *
//     * @param interval
//     * @param startAgeOfEndYearInterval
//     */
//    //@Override
//    public void drawAxes(
//            int interval,
//            int startAgeOfEndYearInterval) {
//        int yAxisExtraWidthLeft;
////        int yAxisExtraHeightTop = 0;
////        int yAxisExtraHeightBottom = 0;
//        int xAxisExtraWidthLeft;
//        int xAxisExtraWidthRight;
//        int xAxisExtraHeightBottom;
//        Line2D ab;
//
//        int scaleTickLength = 5;
//        int scaleTickAndTextSeparation = 3;
//        int partTitleGap = 2;
//        int textHeight = _Generic_Plot.getTextHeight();
//
//        // Draw Y axis
//        int[] yAxisDimensions = drawYAxis(
//                interval,
//                textHeight,
//                startAgeOfEndYearInterval,
//                scaleTickLength,
//                scaleTickAndTextSeparation,
//                partTitleGap);
//        yAxisExtraWidthLeft = yAxisDimensions[0];
//        if (yAxisExtraWidthLeft > _Generic_Plot.extraWidthLeft) {
//            int diff = yAxisExtraWidthLeft - _Generic_Plot.extraWidthLeft;
//            _Generic_Plot.imageWidth += diff;
//            _Generic_Plot.dataStartCol += diff;
//            _Generic_Plot.dataEndCol += diff;
//            _Generic_Plot.extraWidthLeft = yAxisExtraWidthLeft;
//            setOriginCol();
//        }
//        _Generic_Plot.yAxisWidth = yAxisExtraWidthLeft;
//
//        // Draw X axis
//        int[] xAxisDimensions = drawXAxis(
//                textHeight,
//                scaleTickLength,
//                scaleTickAndTextSeparation,
//                partTitleGap);
//        xAxisExtraWidthLeft = xAxisDimensions[0];
//        xAxisExtraWidthRight = xAxisDimensions[1];
//        xAxisExtraHeightBottom = xAxisDimensions[2];
//        if (xAxisExtraWidthLeft > _Generic_Plot.extraWidthLeft) {
//            int diff = xAxisExtraWidthLeft - _Generic_Plot.dataStartCol;
//            _Generic_Plot.imageWidth += diff;
//            _Generic_Plot.dataStartCol += diff;
//            _Generic_Plot.dataEndCol += diff;
//            _Generic_Plot.extraWidthLeft = xAxisExtraWidthLeft;
//            setOriginCol();
//        }
//        if (xAxisExtraWidthRight > _Generic_Plot.extraWidthRight) {
//            _Generic_Plot.imageWidth += xAxisExtraWidthRight - _Generic_Plot.extraWidthRight;
//            _Generic_Plot.extraWidthRight = xAxisExtraWidthRight;
//        }
//        _Generic_Plot.xAxisHeight = xAxisExtraHeightBottom;
//        if (xAxisExtraHeightBottom > _Generic_Plot.extraHeightBottom) {
//            _Generic_Plot.imageHeight += xAxisExtraHeightBottom - _Generic_Plot.extraHeightBottom;
//            _Generic_Plot.extraHeightBottom = xAxisExtraHeightBottom;
//        }
//    }
    //@Override

//    public void initData() {
//        if (_Generic_Plot.getData() == null) {
//            Object[] data = getDefaultData();
//            _Generic_Plot.setData(data);
//            initialiseParameters(data);
//        }
//    }
    public Object[] getData() {
        return _Generic_Plot.getData();
    }

    protected void setData(Object[] data) {
        _Generic_Plot.setData(data);
    }

    /**
     * Constructs and returns an initialised JFrame.
     *
     * @param title
     * @return
     */
    protected static JFrame getJFrame(String title) {
        JFrame jFrame = new JFrame(
                title);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        return jFrame;
    }

    public abstract void setOriginCol();

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

    //public abstract void initialiseParameters(Object[] data);
    public void initialiseParameters(Object[] data) {
        this._Generic_Plot.initialiseParameters(data);
    }

    public void fillRect(
            int x,
            int y,
            int width,
            int height) {
        _Generic_Plot.fillRect(x, y, width, height);
    }

    public void draw(
            Rectangle2D r) {
        _Generic_Plot.draw(r);
    }

    public void draw(Point2D p) {
        _Generic_Plot.draw(p);
    }

    public void drawString(String s, int col, int row) {
        _Generic_Plot.drawString(s, col, row);
    }

    public int getTextWidth(String s) {
        return _Generic_Plot.getTextWidth(s);
    }

    public int getTextHeight() {
        return _Generic_Plot.getTextHeight();
    }

    public void writeText(String s, double angle, int col, int row) {
        _Generic_Plot.writeText(s, angle, col, row);
    }

    public Graphics2D getG2image() {
        return _Generic_Plot.getG2image();
    }

    public void setG2image(Graphics2D g2image) {
        _Generic_Plot.setG2image(g2image);
    }

    public Graphics2D getG2() {
        return _Generic_Plot.getG2();
    }

    public void setG2(Graphics2D g2) {
        _Generic_Plot.setG2(g2);
    }

    public FontMetrics getFontMetrics() {
        return _Generic_Plot.getFontMetrics();
    }

    public void setFontMetrics(FontMetrics fontMetrics) {
        _Generic_Plot.setFontMetrics(fontMetrics);
    }
    
    public String getTitle() {
        return _Generic_Plot.getTitle();
    }

    protected void setTitle(String title) {
        _Generic_Plot.setTitle(title);
    }

    public int getImageWidth() {
        return _Generic_Plot.getImageWidth();
    }

    protected void setImageWidth(int imageWidth) {
        _Generic_Plot.setImageWidth(imageWidth);
    }

    public int getImageHeight() {
        return _Generic_Plot.getImageHeight();
    }

    protected void setImageHeight(int imageHeight) {
        _Generic_Plot.setImageHeight(imageHeight);
    }

    public int getDataWidth() {
        return _Generic_Plot.getDataWidth();
    }

    protected void setDataWidth(int dataWidth) {
        _Generic_Plot.setDataWidth(dataWidth);
    }

    public int getDataHeight() {
        return _Generic_Plot.getDataHeight();
    }

    protected void setDataHeight(int dataHeight) {
        _Generic_Plot.setDataHeight(dataHeight);
    }

    public int getDataStartRow() {
        return _Generic_Plot.getDataStartRow();
    }

    protected void setDataStartRow(int dataStartRow) {
        _Generic_Plot.setDataStartRow(dataStartRow);
    }

    public int getDataMiddleRow() {
        return _Generic_Plot.getDataMiddleRow();
    }

    protected void setDataMiddleRow(int dataMiddleRow) {
        _Generic_Plot.setDataMiddleRow(dataMiddleRow);
    }

    public int getDataEndRow() {
        return _Generic_Plot.getDataEndRow();
    }

    protected void setDataEndRow(int dataEndRow) {
        _Generic_Plot.setDataEndRow(dataEndRow);
    }

    public int getDataStartCol() {
        return _Generic_Plot.getDataStartCol();
    }

    protected void setDataStartCol(int dataStartCol) {
        _Generic_Plot.setDataStartCol(dataStartCol);
    }

    public int getDataEndCol() {
        return _Generic_Plot.getDataEndCol();
    }

    protected void setDataEndCol(int dataEndCol) {
        _Generic_Plot.setDataEndCol(dataEndCol);
    }

    public int getxAxisHeight() {
        return _Generic_Plot.getxAxisHeight();
    }

    protected void setxAxisHeight(int xAxisHeight) {
        _Generic_Plot.setxAxisHeight(xAxisHeight);
    }

    public int getyAxisWidth() {
        return _Generic_Plot.getyAxisWidth();
    }

    protected void setyAxisWidth(int yAxisWidth) {
        _Generic_Plot.setyAxisWidth(yAxisWidth);
    }

    public String getxAxisLabel() {
        return _Generic_Plot.getxAxisLabel();
    }

    protected void setxAxisLabel(String xAxisLabel) {
        _Generic_Plot.setxAxisLabel(xAxisLabel);
    }

    public String getyAxisLabel() {
        return _Generic_Plot.getyAxisLabel();
    }

    protected void setyAxisLabel(String yAxisLabel) {
        _Generic_Plot.setyAxisLabel(yAxisLabel);
    }

    public int getExtraWidthLeft() {
        return _Generic_Plot.getExtraWidthLeft();
    }

    protected void setExtraWidthLeft(int extraWidthLeft) {
        _Generic_Plot.setExtraWidthLeft(extraWidthLeft);
    }

    public int getExtraWidthRight() {
        return _Generic_Plot.getExtraWidthRight();
    }

    protected void setExtraWidthRight(int extraWidthRight) {
        _Generic_Plot.setExtraWidthRight(extraWidthRight);
    }

    public int getExtraHeightTop() {
        return _Generic_Plot.getExtraHeightTop();
    }

    protected void setExtraHeightTop(int extraHeightTop) {
        _Generic_Plot.setExtraHeightTop(extraHeightTop);
    }

    public int getExtraHeightBottom() {
        return _Generic_Plot.getExtraHeightBottom();
    }

    protected void setExtraHeightBottom(int extraHeightBottom) {
        _Generic_Plot.setExtraHeightBottom(extraHeightBottom);
    }

    public BigDecimal getMaxX() {
        return _Generic_Plot.getMaxX();
    }

    protected void setMaxX(BigDecimal maxX) {
        _Generic_Plot.setMaxX(maxX);
    }

    public BigDecimal getMinX() {
        return _Generic_Plot.getMinX();
    }

    protected void setMinX(BigDecimal minX) {
        _Generic_Plot.setMinX(minX);
    }

    public BigDecimal getMaxY() {
        return _Generic_Plot.getMaxY();
    }

    protected void setMaxY(BigDecimal maxY) {
        _Generic_Plot.setMaxY(maxY);
    }

    public BigDecimal getMinY() {
        return _Generic_Plot.getMinY();
    }

    protected void setMinY(BigDecimal minY) {
        _Generic_Plot.setMinY(minY);
    }

    public int getDecimalPlacePrecisionForCalculations() {
        return _Generic_Plot.getDecimalPlacePrecisionForCalculations();
    }

    protected void setDecimalPlacePrecisionForCalculations(int decimalPlacePrecisionForCalculations) {
        _Generic_Plot.setDecimalPlacePrecisionForCalculations(decimalPlacePrecisionForCalculations);
    }

    public int getDecimalPlacePrecisionForDisplay() {
        return _Generic_Plot.getDecimalPlacePrecisionForDisplay();
    }

    protected void setDecimalPlacePrecisionForDisplay(int decimalPlacePrecisionForDisplay) {
        _Generic_Plot.setDecimalPlacePrecisionForDisplay(decimalPlacePrecisionForDisplay);
    }

    public int getSignificantDigits() {
        return _Generic_Plot.getSignificantDigits();
    }

    protected void setSignificantDigits(int significantDigits) {
        this.setSignificantDigits(significantDigits);
    }
    
    public RoundingMode getRoundingMode() {
        return _Generic_Plot.getRoundingMode();
    }

    protected void setRoundingMode(RoundingMode roundingMode) {
        _Generic_Plot.setRoundingMode(roundingMode);
    }
    
    public BigDecimal getCellHeight() {
        return _Generic_Plot.getCellHeight();
    }

    protected void setCellHeight(BigDecimal cellHeight) {
        _Generic_Plot.setCellHeight(cellHeight);
    }

    protected void setCellHeight() {
        _Generic_Plot.setCellHeight();
    }

    public BigDecimal getCellWidth() {
        return _Generic_Plot.getCellWidth();
    }

    protected void setCellWidth(BigDecimal cellWidth) {
        _Generic_Plot.setCellWidth(cellWidth);
    }

    public void setCellWidth() {
        _Generic_Plot.setCellWidth();
    }

    protected int getOriginRow() {
        return _Generic_Plot.getOriginRow();
    }

    public void setOriginRow(int originRow) {
        _Generic_Plot.setOriginRow(originRow);
    }

    protected void setOriginRow() {
        _Generic_Plot.setOriginRow();
    }

    public int getOriginCol() {
        return _Generic_Plot.getOriginCol();
    }

    protected void setOriginCol(int originCol) {
        _Generic_Plot.setOriginCol(originCol);
    }

    public boolean isAddLegend() {
        return _Generic_Plot.isAddLegend();
    }

    public void setAddLegend(boolean addLegend) {
        _Generic_Plot.setAddLegend(addLegend);
    }

    public int getLegendHeight() {
        return _Generic_Plot.getLegendHeight();
    }

    protected void setLegendHeight(int legendHeight) {
        _Generic_Plot.setLegendHeight(legendHeight);
    }

    public int getLegendWidth() {
        return _Generic_Plot.getLegendWidth();
    }

    protected void setLegendWidth(int legendWidth) {
        _Generic_Plot.setLegendWidth(legendWidth);
    }

    public int getAgeInterval() {
        return _Generic_Plot.getAgeInterval();
    }

    protected void setAgeInterval(int ageInterval) {
        _Generic_Plot.setAgeInterval(ageInterval);
    }

    public int getStartAgeOfEndYearInterval() {
        return _Generic_Plot.getStartAgeOfEndYearInterval();
    }

    protected void setStartAgeOfEndYearInterval(int startAgeOfEndYearInterval) {
        _Generic_Plot.setStartAgeOfEndYearInterval(startAgeOfEndYearInterval);
    }
}
