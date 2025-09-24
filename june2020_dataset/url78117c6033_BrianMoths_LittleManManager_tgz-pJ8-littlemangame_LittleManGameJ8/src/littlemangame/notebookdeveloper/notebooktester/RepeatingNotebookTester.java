/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdeveloper.notebooktester;

import littlemangame.computer.computercomponents.Notebook;

/**
 *
 * @author brian
 */
public class RepeatingNotebookTester implements NotebookTester {

    private final NotebookTesterFactory notebookTesterFactory;
    private final int numTests;
    private String messageFromTest;

    public RepeatingNotebookTester(NotebookTesterFactory notebookTesterFactory, int numTests) {
        this.notebookTesterFactory = notebookTesterFactory;
        this.numTests = numTests;
    }

    @Override
    public boolean isNotebookCorrect(Notebook memory) {
        boolean isCorrect = true;
        int numTestsSoFar = 0;
        while (isCorrect && numTestsSoFar < numTests) {
            final NotebookTester notebookTester = notebookTesterFactory.produceNotebookTester();
            isCorrect &= notebookTester.isNotebookCorrect(memory);
            messageFromTest = notebookTester.getMessageFromTest();
            numTestsSoFar++;
        }
        return isCorrect;

    }

    @Override
    public String getMessageFromTest() {
        return messageFromTest;
    }

}
