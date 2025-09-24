/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.computer.computercomponents;

import java.awt.Point;
import littlemangame.word.Word;

/**
 * An interface for an object to accept input into a computer. This is
 * equivalent to a prompt on a real computer. The input panel has a display
 * which can be in an enabled or disabled state. When the panel is enabled, the
 * user can select a value and hit a button to finalize his selection.
 *
 * A computerInputter is meant to be used like this: first the client calls
 * requestInput. This enables the inputter. At this point the user sees the
 * inputter become enabled and will eventually add input. The client should then
 * poll the inputter by calling the isWordEnteredMethod. Once the user has
 * entered input so that isWordEnetered returns true, the client may then call
 * getEnteredWord. At this point the inputPanel becomes disabled and we are back
 * to where we started at the beginning of the paragraph. The client may disable
 * the inputter at any time by calling cancelInputRequest.
 *
 * @author brian
 */
public interface OfficeInputter {

    /**
     * Removes the user's ability to edit his selection and finalize his
     * selection.
     */
    void cancelInputRequest();

    /**
     * Enables the input panel so that the user may enter a word.
     */
    void requestInput();

    /**
     * Gives the point from which a little man may access this inputter.
     *
     * @return
     */
    Point getAccessLocation();

    /**
     * gets the word which has been entered into the inputter. This method may
     * only be called when there have been no subsequent calls to this method
     * or cancelInputRequest since the last requestInput. Additionally this
     * method may only be called when the user has entered input, i.e., when
     * isWordEntered() returns true;
     *
     * @return
     */
    Word getEnteredWord();

    /**
     * returns true if the request input has been called, and the user has not
     * yet entered input, and there have been no subsequent calls to
     * cancelInputRequest.
     *
     * @return whether this input is awaiting input.
     */
    boolean isAwaitingInput();

    /**
     * returns true if the user has entered input and there have been no
     * subsequent calls to getEnteredWord or cancelInputRequest.
     *
     * @return whether the user has entered input
     */
    boolean isWordEntered();

    /**
     * returns true if getEnteredInput or cancelInputrequest has been called and
     * there have been no subsequent calls to requestInput
     *
     * @return
     */
    boolean isDisabled();

}
