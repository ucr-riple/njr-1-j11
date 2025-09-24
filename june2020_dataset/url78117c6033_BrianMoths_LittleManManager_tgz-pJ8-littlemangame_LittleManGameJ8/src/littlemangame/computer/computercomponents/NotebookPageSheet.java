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
 * A NotebookPageSheet is a {@link WordContainer}. The NotebookPageSheet class
 * extends
 * WordContainer by adding methods to get its position and draw itself.
 *
 * A NotebookPageSheet is meant to be analogous to the instruction pointer in a
 * real computer.
 *
 * @author brian
 */
public class NotebookPageSheet extends WordContainer implements Drawable {

    static protected final int xPosition = 200;
    static protected final int yPosition = 100;
    static protected final int width = 22;
    static protected final int height = 20;

    /**
     * Contructs a NotebookPageSheet initialized to the zero word.
     */
    public NotebookPageSheet() {
        super(Word.ZERO_WORD);
    }

    /**
     * returns the place the littleman has to go to use this notebook page
     * sheet.
     *
     * @return the place the littleman has to go to use this notebook page
     * sheet.
     */
    public Point getAccessLocation() {
        return new Point(xPosition + width / 2 - 2, yPosition + height + 3);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawRect(xPosition, yPosition, width, height);
        graphics.drawString(getWord().toString(), xPosition + 3, yPosition + height - 3);
    }

}
