/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdeveloper.notebooktester.inputoutputevents;

import littlemangame.word.Word;

/**
 *
 * @author brian
 */
public class OutputEvent extends InputOutputEvent {

    private final Word word;

    public OutputEvent(Word word) {
        this.word = word;
    }

    @Override
    public InputOutputEventType getEventType() {
        return InputOutputEventType.OUTPUT;
    }

    @Override
    protected String getActualVerbClause() {
        return super.getActualVerbClause() + " " + getWord().toString(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String getExpectedBareInfinitiveClause() {
        return super.getExpectedBareInfinitiveClause() + " " + getWord().toString(); //To change body of generated methods, choose Tools | Templates.
    }

    public Word getWord() {
        return word;
    }

}
