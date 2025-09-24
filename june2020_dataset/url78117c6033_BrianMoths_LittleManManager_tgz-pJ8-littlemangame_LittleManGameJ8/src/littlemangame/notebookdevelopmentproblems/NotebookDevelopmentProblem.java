/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdevelopmentproblems;

import littlemangame.computer.computercomponents.Notebook;
import littlemangame.notebookdeveloper.notebooktester.NotebookTester;

/**
 * This is a class representing a notebook development problem. A description of
 * the problem, which specifies what constitutes a correct notebook (typically
 * by specifying the desired little man behavior), can be obtained by the {@link NotebookDevelopmentProblem#getProblemDescription()
 * } method. A notebook can be submitted through the {@link NotebookDevelopmentProblem#testNotebook(littlemangame.computer.computercomponents.Notebook)
 * } method.Once the notebook has been submitted through this method, one can
 * see if the notebook solved the problem with a call to the {@link NotebookDevelopmentProblem#wasLastTestCorrect()
 * } method. If more detail is required on why the test failed, or to get a
 * message after the test passed, the {@link NotebookDevelopmentProblem#getMessageFromLastTest()
 * } method may be called.
 *
 * @author brian
 */
public class NotebookDevelopmentProblem {

    private final NotebookTester notebookTester;
    private boolean wasLastTestCorrect;
    private final String problemDescription;

    /**
     * constructs a notebook development problem where the solutions are tested
     * with the given notebook tester and the description of the problem is the
     * given problem description
     *
     * @param notebookTester the notebook tester used to test proposed solutions
     * to this notebook development problem
     * @param problemDescription a description of this notebook development
     * problem. This description should give the desired behavior of the little
     * man.
     */
    public NotebookDevelopmentProblem(NotebookTester notebookTester, String problemDescription) {
        this.notebookTester = notebookTester;
        this.problemDescription = problemDescription;
    }

    /**
     * tests the given notebook to see if it is a solution to this problem.
     * Whether or not the test passed can be found by a subsequent call to the
     * {@link NotebookDevelopmentProblem#wasLastTestCorrect()} method. A message
     * concerning the result of the test may be obtained through a call to the
     * {@link NotebookDevelopmentProblem#getMessageFromLastTest()} method.
     *
     * @param notebook the notebook to test
     */
    public void testNotebook(Notebook notebook) {
        wasLastTestCorrect = notebookTester.isNotebookCorrect(notebook);
    }

    /**
     * returns whether the last call to
     * {@link NotebookDevelopmentProblem#testNotebook(littlemangame.computer.computercomponents.Notebook)}
     * resulted in a pass.
     * Unspecified behavior results if this method had not yet been called on
     * this object.
     *
     * @return whether the last test passed.
     */
    public boolean wasLastTestCorrect() {
        return wasLastTestCorrect;
    }

    /**
     * returns the message describing the results from the last call to
     * {@link NotebookDevelopmentProblem#testNotebook(littlemangame.computer.computercomponents.Notebook)}.
     * Unspecified behavior results if this method had not yet been called on
     * this object.
     *
     * @return the message from the last test.
     */
    public String getMessageFromLastTest() {
        return notebookTester.getMessageFromTest();
    }

    /**
     * returns a description of this notebook development problem.
     *
     * @return a description of this notebook development problem.
     */
    public String getProblemDescription() {
        return problemDescription;
    }

}
