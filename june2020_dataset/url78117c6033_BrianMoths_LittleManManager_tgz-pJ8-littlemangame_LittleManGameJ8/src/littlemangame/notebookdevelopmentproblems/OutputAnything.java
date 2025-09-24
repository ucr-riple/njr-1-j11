/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdevelopmentproblems;

import littlemangame.notebookdeveloper.notebooktester.NotebookTester;
import littlemangame.notebookdeveloper.notebooktester.OnlineNotebookTester;
import littlemangame.notebookdeveloper.notebooktester.WordPredicate;

/**
 *
 * @author brian
 */
public class OutputAnything extends NotebookDevelopmentProblem {

    private static final String problemDescription = "The little man must output any word. Then the little man must halt.";

    public static NotebookTester produceNotebookTester() {
        OnlineNotebookTester instanceNotebookTester = new OnlineNotebookTester();
        instanceNotebookTester.addOutputEvent(WordPredicate.alwaysTruePredicate);
        instanceNotebookTester.addHaltEvent();
        return instanceNotebookTester;
    }

    public OutputAnything() {
        super(produceNotebookTester(), problemDescription);
    }

}
