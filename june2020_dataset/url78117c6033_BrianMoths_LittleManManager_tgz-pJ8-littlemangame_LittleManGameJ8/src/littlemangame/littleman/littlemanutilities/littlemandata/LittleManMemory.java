/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.littleman.littlemanutilities.littlemandata;

import java.awt.Color;
import java.awt.Graphics;
import littlemangame.word.Word;

/**
 * The little man is able to hold to words in his head. One word is data and it
 * is for moving words between two places that can hold data. The other word is
 * a page number and it is used for operations involving the notebook. It tells
 * the little man which page of the notebook to use. It is possible for the
 * little man to not have any data remember or to not have any page number
 * remembered (or both).
 *
 *
 * This class is also
 * responsible for managing the drawing of data.
 *
 * @author brian
 */
public class LittleManMemory {

    private Word rememberedPage;
    private boolean isRememberingPage = false;
    private Word rememberedData;
    private boolean isRememberingData = false;

    /**
     * constructs a new little man memory that does not have anything stored in
     * it at all.
     */
    public LittleManMemory() {
    }

    /**
     * the given word is remembered as data. Any previous data is lost.
     *
     * @param data the word to be remembered as data
     */
    public void memorizeData(Word data) {
        this.rememberedData = data;
        isRememberingData = true;
    }

    /**
     * the given word is remembered as a page number. Any previous page number
     * is lost.
     *
     * @param page the word to be remembered as data
     */
    public void memorizePageNumber(Word page) {
        rememberedPage = page;
        isRememberingPage = true;
    }

    /**
     * removes the currently stored data from memory
     */
    public void clearDataMemory() {
        isRememberingData = false;
    }

    /**
     * removes the currently stored page number from memory.
     */
    public void clearPageNumberMemory() {
        isRememberingPage = false;
    }

    /**
     * clears both the page number and data from memory
     */
    public void clearMemory() {
        clearPageNumberMemory();
        clearDataMemory();
    }

    /**
     * tests whether any data is being remembered
     *
     * @return whether data is being remembered
     */
    public boolean isRememberingData() {
        return isRememberingData;
    }

    /**
     * tests whether any page number is being remembered
     *
     * @return whether page number is being remembered
     */
    public boolean isRememberingPageNumber() {
        return isRememberingPage;

    }

    /**
     * returns the page number being remembered and clears it from memory. If no
     * page number is being
     * remembered, an exception is thrown.
     *
     * @return the page number being remembered
     *
     * @throws IllegalStateException if no page number is being remembered
     */
    public Word useRememberedPageNumber() {
        if (!isRememberingPageNumber()) {
            throw new IllegalStateException("use remember page number called when no page number was remembered.");
        }
        final Word word = rememberedPage;
        clearPageNumberMemory();
        return word;
    }

    /**
     * returns the last page number remembered even if it has since been used.
     * This method does not throw an error.
     *
     * @return the last page number to be remembered.
     */
    public Word getRememberedPageNumber() {
        return rememberedPage;
    }

    /**
     * returns the data being remembered and clears it from memory. If no
     * data is being
     * remembered, an exception is thrown.
     *
     * @return the data being remembered
     *
     * @throws IllegalStateException if no data is being remembered
     */
    public Word useRememberedData() {
        if (!isRememberingData()) {
            throw new IllegalStateException("use remembered data called when no data was remembered.");
        }
        final Word word = rememberedData;
        clearDataMemory();
        return word;
    }

    /**
     * draws a representation of the memory at the given coordinates
     *
     * @param graphics the graphics object on which to draw
     * @param x the x coordinate specifying where to draw
     * @param y the y coordinate specifying where to draw
     */
    public void draw(Graphics graphics, int x, int y) {
        if (isRememberingData()) {
            final Color color = graphics.getColor();
            graphics.setColor(Color.red);
            graphics.drawRect(x - 15, y - 22, 22, 20);
            graphics.drawString(rememberedData.toString(), x - 12, y - 5);
            graphics.setColor(color);
        }
        if (isRememberingPageNumber()) {
            final Color color = graphics.getColor();
            graphics.setColor(Color.blue);
            graphics.drawRect(x + 15, y - 22, 22, 20);
            graphics.drawString(rememberedPage.toString(), x + 18, y - 5);
            graphics.setColor(color);
        }
    }

}
