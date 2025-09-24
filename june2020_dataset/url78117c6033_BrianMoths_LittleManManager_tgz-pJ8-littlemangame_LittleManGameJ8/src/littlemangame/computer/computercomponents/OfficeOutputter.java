/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.computer.computercomponents;

import java.awt.Point;
import littlemangame.word.Word;

/**
 * An interface for an object that is to be used to display output from a
 * computer. It is supposed to be analogous to how a normal program outputs to a
 * terminal.
 *
 * It has the capability of outputting a word at a time, each on its own line.
 * It can also clear the output.
 *
 * @author brian
 */
public interface OfficeOutputter {

    /**
     * Clears everything displayed on this outputter.
     */
    void clear();

    /**
     * gives the point the littleman must go to to access this outputter.
     *
     * @return the point the littleman must go to to access this outputter.
     */
    Point getAccessLocation();

    /**
     * Prints the given word to the output panel on its own line.
     *
     * @param word the word to be printed.
     */
    void printlnUnsigned(Word word);

}
