/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.tutorial.tutorialoffice.components;

import java.awt.Graphics;
import littlemangame.computer.computercomponents.Notebook;

/**
 *
 * @author brian
 */
public class TutorialMemory extends Notebook {

    static private final int arrowLength = 30;
    private boolean isArrowShown = false;
    private final IndicatorArrow indicatorArrow;

    public TutorialMemory() {
        super();
        indicatorArrow = new IndicatorArrow(xPosition - arrowLength, yPosition + 200, xPosition - 2, yPosition + 200);
    }

    @Override
    public void draw(Graphics graphics) {
        super.draw(graphics); //To change body of generated methods, choose Tools | Templates.
        if (isArrowShown) {
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
