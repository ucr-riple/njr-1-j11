/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdeveloper.notebooktester.littleManMock;

import java.awt.Point;
import littlemangame.computer.computercomponents.OfficeInputter;
import littlemangame.computer.computercomponents.InputPanelState;
import littlemangame.word.Word;

/**
 *
 * @author brian
 */
public class ComputerInputterMock implements OfficeInputter {

    private final InputProducerMock inputProducerMock;
    private InputPanelState inputPanelState;

    public ComputerInputterMock(InputProducerMock inputProducerMock) {
        this.inputProducerMock = inputProducerMock;
        inputPanelState = InputPanelState.DISABLED;
    }

    @Override
    public void cancelInputRequest() {
        inputPanelState = InputPanelState.DISABLED;
    }

    @Override
    public void requestInput() {
        inputPanelState = InputPanelState.HAS_INPUT; //a mock object immediately knows what it wants to say next.
    }

    @Override
    public Point getAccessLocation() {
        return new Point();
    }

    @Override
    public Word getEnteredWord() {
        if (inputPanelState != InputPanelState.HAS_INPUT) {
            throw new IllegalStateException("getEnteredWordCalled before user entered input. Use isWordEntered to ensure the user has entered input before getEnteredWord is called.");
        }
        return inputProducerMock.getInputWord();
    }

    @Override
    public boolean isAwaitingInput() {
        return inputPanelState == InputPanelState.AWAITING_INPUT; //always false
    }

    @Override
    public boolean isWordEntered() {
        return inputPanelState == InputPanelState.HAS_INPUT;
    }

    @Override
    public boolean isDisabled() {
        return inputPanelState == InputPanelState.DISABLED;
    }

}
