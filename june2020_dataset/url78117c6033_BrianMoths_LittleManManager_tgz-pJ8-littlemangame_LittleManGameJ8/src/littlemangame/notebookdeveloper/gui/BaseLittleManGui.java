/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdeveloper.gui;

import littlemangame.GenericLittleManGui;
import littlemangame.notebookdeveloper.NotebookDeveloper;

/**
 *
 * @author brian
 */
public class BaseLittleManGui extends GenericLittleManGui<BaseNotebookDeveloperGui, NotebookEditorPanel, NotebookDeveloper> {

    public BaseLittleManGui() {
        super(new BaseNotebookDeveloperGui(), new NotebookEditorPanel());
    }

}
