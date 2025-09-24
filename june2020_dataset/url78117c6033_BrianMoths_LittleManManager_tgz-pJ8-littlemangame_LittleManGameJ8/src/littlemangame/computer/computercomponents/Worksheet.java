/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.computer.computercomponents;

import Renderer.Drawable;
import java.awt.Graphics;
import java.awt.Point;
import littlemangame.word.Word;
import littlemangame.word.WordContainer;

/**
 * A worksheet is a {@link WordContainer}. The Worksheet class extends
 * WordContainer by adding methods to get its position and draw itself.
 *
 * A worksheet is meant to be analogous to a register in a real computer.
 *
 * @author brian
 */
public class Worksheet extends WordContainer implements Drawable {

    static protected final int xPosition = 200;
    static protected final int yPosition = 300;
    static protected final int width = 22;
    static protected final int height = 20;

    /**
     * Constructs a worksheet, initialized to hold the
     * zero
     * word.
     */
    public Worksheet() {
        super(Word.ZERO_WORD);
    }

    /**
     * returns the place the littleman has to go to use the worksheet.
     *
     * @return the place the littleman has to go to use the worksheet
     */
    public Point getAccessLocation() {
        return new Point(xPosition + width / 2, yPosition - 10);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawRect(xPosition, yPosition, width, height);
        graphics.drawString(getWord().toString(), xPosition + 3, yPosition + height - 3);
    }

}
