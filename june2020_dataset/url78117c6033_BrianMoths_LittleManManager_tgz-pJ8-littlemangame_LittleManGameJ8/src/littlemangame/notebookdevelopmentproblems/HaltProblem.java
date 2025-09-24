/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdevelopmentproblems;

import littlemangame.notebookdeveloper.notebooktester.NotebookTester;
import littlemangame.notebookdeveloper.notebooktester.OnlineNotebookTester;

/**
 *
 * @author brian
 */
public class HaltProblem extends NotebookDevelopmentProblem {

    private static final String problemDescription = "The little man must halt. The little man must not output anything or ask for any input.";

    static public NotebookTester produceNotebookTester() {
        OnlineNotebookTester instanceNotebookTester = new OnlineNotebookTester();
        instanceNotebookTester.addHaltEvent();
        return instanceNotebookTester;
    }

    public HaltProblem() {
        super(produceNotebookTester(), problemDescription);
    }

}
