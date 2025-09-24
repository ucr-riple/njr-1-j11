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
public abstract class InputOutputEvent {

    abstract public InputOutputEventType getEventType();

    public String getExpectedActionDescription() {
        return "I expected him to " + getExpectedBareInfinitiveClause();
    }

    public String getActualActionDescription() {
        return "The little man " + getActualVerbClause();
    }

    protected String getExpectedBareInfinitiveClause() {
        return getEventType().getBareInfinitive();
    }

    protected String getActualVerbClause() {
        return getEventType().getPastTense();
    }

}
