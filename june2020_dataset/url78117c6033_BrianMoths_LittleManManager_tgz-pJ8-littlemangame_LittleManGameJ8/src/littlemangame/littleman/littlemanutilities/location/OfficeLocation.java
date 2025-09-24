/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.littleman.littlemanutilities.location;

import java.awt.Point;

/**
 * this class represents a location in the office.
 *
 * @author brian
 */
public enum OfficeLocation {

    /**
     * the location of the page number sheet
     */
    PAGE_NUMBER_SHEET(new PointPositionGetter() {

        @Override
        Point getPoint(LittleManPosition littleManPosition) {
            return littleManPosition.getPositionGetterAdapter().getPageNumberSheetPosition();
        }

    }),
    /**
     * the location of the worksheet
     */
    WORKSHEET(new PointPositionGetter() {
        @Override
        Point getPoint(LittleManPosition littleManPosition) {
            return littleManPosition.getPositionGetterAdapter().getWorksheetPosition();

        }

    }),
    /**
     * the location of the remembered notebook page
     */
    REMEMBERED_NOTEBOOK_PAGE(new PointPositionGetter() {
        @Override
        Point getPoint(LittleManPosition littleManPosition) {
            return littleManPosition.getPositionGetterAdapter().getRememberedPagePosition();
        }

    }),
    /**
     * the location of the output panel
     */
    OUTPUT_PANEL(new PointPositionGetter() {
        @Override
        Point getPoint(LittleManPosition littleManPosition) {
            return littleManPosition.getPositionGetterAdapter().getOutputPanelPosition();

        }

    }),
    /**
     * the location of the input panel
     */
    INPUT_PANEL(new PointPositionGetter() {

        @Override
        Point getPoint(LittleManPosition littleManPosition) {
            return littleManPosition.getPositionGetterAdapter().getInputPanelPosition();
        }

    }),
    /**
     * the current little man's location
     */
    CURRENT_LOCATION(new PositionGetter() {
        @Override
        public boolean goTo(LittleManPosition littleManPosition) {
            return true;
        }

        @Override
        public boolean isHere(LittleManPosition littleManPosition) {
            return true;
        }

    });
    private final PositionGetter positionGetter;

    private OfficeLocation(PositionGetter positionGetter) {
        this.positionGetter = positionGetter;
    }

    boolean goTo(LittleManPosition littleManPosition) {
        return positionGetter.goTo(littleManPosition);

    }

    boolean isHere(LittleManPosition littleManPosition) {
        return positionGetter.isHere(littleManPosition);
    }

    private static interface PositionGetter {

        public boolean goTo(LittleManPosition littleManPosition);

        boolean isHere(LittleManPosition littleManPosition);

    }

    private static abstract class PointPositionGetter implements PositionGetter {

        abstract Point getPoint(LittleManPosition littleManPosition);

        @Override
        public boolean goTo(LittleManPosition littleManPosition) {
            return littleManPosition.goToPoint(getPoint(littleManPosition));
        }

        @Override
        public boolean isHere(LittleManPosition littleManPosition) {
            return littleManPosition.isAtPoint(getPoint(littleManPosition));
        }

    }

}
