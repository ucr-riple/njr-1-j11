/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.littleman;

import java.awt.Point;

/**
 * A class which can hold a {@link PositionGetter}. It has methods to get the
 * position location,but these methods must not be called until the position
 * getter has been given.
 *
 * @author brian
 */
public class PositionGetterAdapter {

    /**
     * class responsible for knowing where the positions of various things in
     * the office
     *
     * @author brian
     */
    public static interface PositionGetter {

        /**
         * returns the position of the worksheet
         *
         * @return the position of the worksheet
         */
        public Point getWorksheetPosition();

        /**
         * returns the position of the remembered page
         *
         * @return the position of the remembered page
         */
        public Point getRememberedPagePosition();

        /**
         * returns the position of the page number sheet
         *
         * @return the position of the page number sheet
         */
        public Point getPageNumberSheetPosition();

        /**
         * returns the position of the output panel
         *
         * @return the position of the output panel
         */
        public Point getOutputPanelPosition();

        /**
         * returns the position of the input panel
         *
         * @return the position of the input panel
         */
        public Point getInputPanelPosition();

    }

    private PositionGetter positionGetter;

    /**
     * constructs a new position getter adapter which has no position getter.
     * <t>setPositionGetter</t> must be called on it before any of the
     * getPosition methods
     */
    public PositionGetterAdapter() {
    }

    /**
     * sets the position getter
     *
     * @param positionGetter the position getter to be used to give the
     * positions of various things in the office.
     */
    public void setPositionGetter(PositionGetter positionGetter) {
        this.positionGetter = positionGetter;
    }

    /**
     * returns the position of the worksheet
     *
     * @return the position of the worksheet
     */
    public Point getWorksheetPosition() {
        return positionGetter.getWorksheetPosition();
    }

    /**
     * returns the position of the remembered page
     *
     * @return the position of the remembered page
     */
    public Point getRememberedPagePosition() {
        return positionGetter.getRememberedPagePosition();
    }

    /**
     * returns the position of the page number sheet
     *
     * @return the position of the page number sheet
     */
    public Point getPageNumberSheetPosition() {
        return positionGetter.getPageNumberSheetPosition();
    }

    /**
     * returns the position of the output panel
     *
     * @return the position of the output panel
     */
    public Point getOutputPanelPosition() {
        return positionGetter.getOutputPanelPosition();
    }

    /**
     * returns the position of the input panel
     *
     * @return the position of the input panel
     */
    public Point getInputPanelPosition() {
        return positionGetter.getInputPanelPosition();
    }

}
