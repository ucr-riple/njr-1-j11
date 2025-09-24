/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdeveloper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import littlemangame.computer.computercomponents.Notebook;
import littlemangame.notebookdevelopmentproblems.HaltProblem;
import littlemangame.notebookdevelopmentproblems.NotebookDevelopmentProblem;
import littlemangame.notebookdevelopmentproblems.Output42;
import littlemangame.notebookdevelopmentproblems.OutputAnything;

/**
 * A notebook problem set represents an ordered set of notebook development
 * problems. At any time, this set has a "current problem". This current problem
 * may be iterated through with the {@link NotebookProblemSet#beginNextProblem()
 * } method. Attempts to solve the current problem may be made with the {@link NotebookDevelopmentProblem#NotebookDevelopmentProblem(littlemangame.notebookdeveloper.notebooktester.NotebookTester, java.lang.String)
 * } method. The results can be obtained with the {@link NotebookDevelopmentProblem#wasLastTestCorrect()
 * } and {@link NotebookDevelopmentProblem#getMessageFromLastTest() } methods.
 *
 * @author brian
 */
public class NotebookProblemSet {

    /**
     * generates a reasonable notebook problem set
     *
     * @return a reasonable notebook problem set
     */
    public static NotebookProblemSet makeDefaultNotebookProblemSet() {
        List<NotebookDevelopmentProblem> notebookDevelopmentProblems = new ArrayList<>();
        notebookDevelopmentProblems.add(new HaltProblem());
        notebookDevelopmentProblems.add(new OutputAnything());
        notebookDevelopmentProblems.add(new Output42());
        return new NotebookProblemSet(notebookDevelopmentProblems);
    }

    private final ListIterator<NotebookDevelopmentProblem> notebookDevelopmentProblemIterator;
    private NotebookDevelopmentProblem notebookDevelopmentProblem;

    /**
     * constructs a notebook problem set where the underlying notebook
     * development problems are given by the given collection of notebook
     * development problems (in the order returned by the collection's
     * iterator).
     *
     * @param notebookDevelopmentProblems the notebook development problems
     * represented by this notebook problem set.
     */
    public NotebookProblemSet(Collection<? extends NotebookDevelopmentProblem> notebookDevelopmentProblems) {
        final List<NotebookDevelopmentProblem> notebookDevelopmentProblemsCopy = new ArrayList<>(notebookDevelopmentProblems);
        notebookDevelopmentProblemIterator = notebookDevelopmentProblemsCopy.listIterator();
    }

    /**
     * tests if the given notebook passes the current notebook development
     * problem of this set.
     *
     * @param notebook the notebook to be tested
     *
     * @return whether the given notebook solved the current problem of this
     * set
     */
    public boolean verifyNotebook(Notebook notebook) {
        notebookDevelopmentProblem.testNotebook(notebook);
        return notebookDevelopmentProblem.wasLastTestCorrect();

    }

    /**
     * tests whether this set has another problem after the current problem.
     *
     * @return whether this set has another problem after the current problem
     */
    public boolean hasNextProblem() {
        return notebookDevelopmentProblemIterator.hasNext();
    }

    /**
     * iterates through to the next notebook development problem of this set.
     */
    public void beginNextProblem() {
        notebookDevelopmentProblem = notebookDevelopmentProblemIterator.next();
    }

    /**
     * returns the description of the current notebook development problem.
     *
     * @return the description of the current notebook development problem
     *
     * @see NotebookDevelopmentProblem#getProblemDescription()
     */
    public String getCurrentProblemDescription() {
        return notebookDevelopmentProblem.getProblemDescription();
    }

    /**
     * returns the message from the last test run on the current notebook
     * development problem.
     *
     * @return the message from the last test run on the current notebook
     * development problem
     *
     * @see NotebookDevelopmentProblem#getMessageFromLastTest()
     */
    public String getMessageFromLastTest() {
        return notebookDevelopmentProblem.getMessageFromLastTest();
    }

}
