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
public interface NotebookTester {

    public boolean isNotebookCorrect(Notebook memory);

    public String getMessageFromTest();

}
