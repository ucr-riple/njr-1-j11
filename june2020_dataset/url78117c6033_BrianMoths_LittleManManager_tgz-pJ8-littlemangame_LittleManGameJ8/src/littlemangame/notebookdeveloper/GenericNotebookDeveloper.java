/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdeveloper;

import Renderer.Drawable;
import java.awt.Graphics;
import littlemangame.computer.computercomponents.Notebook;
import littlemangame.littlemancommands.GenericLittleManCommander;

/**
 * This class is responsible for keeping track of the notebook problems to be
 * solved, accepting attempts at solving the notebook problem, and managing the
 * little man commander as he does the instructions in the notebook given as an
 * attempt to solve the problem.
 *
 * This class has a method to accept a notebook. Once a notebook is accepted,
 * that notebook can be tested by running the little man commander
 * associated with the notebook developer through this classes doFrames method.
 * In addition to testing to the notebook, it is possible to check if the
 * notebook is a solution to the problem through the submitNotebook method.
 *
 * @author brian
 * @param <T>
 */
public class GenericNotebookDeveloper<T extends GenericLittleManCommander<?>>
        implements Drawable {

    protected final T littleManCommander;
    private final Notebook notebook;
    private final NotebookProblemSet notebookProblemSet;

    /**
     * constructs a notebook developer to be shown in the given office view, and
     * to command the given little man commnader.
     *
     * @param officeView the office view in which to show this notebook
     * developer
     * @param littleManCommander the little man commander used for testing
     * pruposes by this little man commander
     */
    public GenericNotebookDeveloper(T littleManCommander) {
        this.littleManCommander = littleManCommander;
        notebook = new Notebook();
        notebookProblemSet = NotebookProblemSet.makeDefaultNotebookProblemSet();
        notebookProblemSet.beginNextProblem();
    }

    /**
     * this notebook developer makes the little man commander to the given
     * number of cycles.
     *
     * @param numFrames the number of cycles for the little man commander to do.
     */
    public void doFrames(int numFrames) {
        for (int i = 0; i < numFrames; i++) {
            littleManCommander.doCycle();
        }
    }

    /**
     * ends the little man commander's test of the notebook.
     */
    public void endTest() {
        littleManCommander.reset();
    }

    /**
     * submits this notebook developer's notebook for verification. Returns a
     * string explaining why the notebook is wrong or a message saying the
     * notebook is correct.
     *
     * @return a string explaining if the notebook submitted was correct or not
     */
    public String submitNotebookSolutionAttempt() {
        final boolean isCorrect = notebookProblemSet.verifyNotebook(notebook);
        final StringBuilder resultStringBuilder = new StringBuilder(notebookProblemSet.getMessageFromLastTest());
        if (isCorrect) {
            if (notebookProblemSet.hasNextProblem()) {
                notebookProblemSet.beginNextProblem();
            } else {
                resultStringBuilder.append("You beat the game!");
            }
        }
        return resultStringBuilder.toString();
    }

    /**
     * returns a description of the current notebook development problem
     *
     * @return a description of the current notebook development problem
     */
    public String getCurrentProblemDescription() {
        return notebookProblemSet.getCurrentProblemDescription();
    }

    /**
     * returns a copy of the notebook solution attempt currently held by this
     * notebook developer
     *
     * @return a copy of the notebook solution attempt currently held by this
     * notebook developer
     */
    public Notebook getNotebookSolutionAttempt() {
        return new Notebook(notebook);
    }

    /**
     * set the notebook solution attempt for this notebook developer.
     *
     * @param notebook the notebook solution attempt for this notebook
     * developer.
     */
    public void setNotebookSolutionAttempt(Notebook notebook) {
        this.notebook.loadCopyOfNotebook(notebook);
        littleManCommander.reset();
        littleManCommander.loadCopyOfNotebook(notebook);
    }

    protected final T getLittleManComander() {
        return littleManCommander;
    }

    @Override
    public void draw(Graphics graphics) {
        littleManCommander.draw(graphics);
    }

}
