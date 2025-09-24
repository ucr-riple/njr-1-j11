/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.tutorial.tutorialoffice.components;

import Renderer.Drawable;
import java.awt.Graphics;
import java.awt.Point;
import littlemangame.computer.computercomponents.OutputPanel;

/**
 *
 * @author brian
 */
public class TutorialOutputPanel extends OutputPanel implements Drawable {

    static private final int arrowLength = 30;
    private boolean isArrowShown = false;
    private final IndicatorArrow indicatorArrow;

    public TutorialOutputPanel() {
        super();
        indicatorArrow = new IndicatorArrow(0, 0, 0, 0);
    }

    private void resetArrowPosition() {
        Point accessPoint = getAccessLocation();
        indicatorArrow.setHeadX(accessPoint.x);
        indicatorArrow.setHeadY(accessPoint.y);
        indicatorArrow.setTailX(accessPoint.x + arrowLength);
        indicatorArrow.setTailY(accessPoint.y);
    }

    @Override
    public void draw(Graphics graphics) {
        if (isArrowShown) {
            resetArrowPosition();
            indicatorArrow.draw(graphics);
        }
    }

    public boolean isArrowShown() {
        return isArrowShown;
    }

    public void setIsArrowShown(boolean isArrowShown) {
        this.isArrowShown = isArrowShown;
    }

}
