/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdeveloper.notebooktester.littleManMock;

import java.awt.Point;
import littlemangame.computer.computercomponents.OfficeOutputter;
import littlemangame.word.Word;

/**
 *
 * @author brian
 */
public class ComputerOutputterMock implements OfficeOutputter {

    final OutputEventListener outputEventListener;

    public ComputerOutputterMock(OutputEventListener outputEventListener) {
        this.outputEventListener = outputEventListener;
    }

    @Override
    public void clear() {

    }

    @Override
    public Point getAccessLocation() {
        return new Point();
    }

    @Override
    public void printlnUnsigned(Word word) {
        outputEventListener.acceptOutput(word);
    }

}
