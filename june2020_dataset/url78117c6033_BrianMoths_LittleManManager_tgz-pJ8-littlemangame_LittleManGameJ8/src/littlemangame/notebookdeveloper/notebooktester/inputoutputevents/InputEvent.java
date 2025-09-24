/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdeveloper.notebooktester.inputoutputevents;

/**
 *
 * @author brian
 */
public class InputEvent extends InputOutputEvent {

    @Override
    public InputOutputEventType getEventType() {
        return InputOutputEventType.INPUT;
    }

}
