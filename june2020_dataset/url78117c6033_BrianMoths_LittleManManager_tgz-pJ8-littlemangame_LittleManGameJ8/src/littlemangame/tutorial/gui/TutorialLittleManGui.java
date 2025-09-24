/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.tutorial.gui;

import java.awt.event.ActionListener;
import littlemangame.GenericLittleManGui;
import littlemangame.tutorial.tutorialnotebookdeveloper.TutorialNotebookDeveloper;

/**
 *
 * @author brian
 */
public class TutorialLittleManGui extends GenericLittleManGui<TutorialNotebookDeveloperGui, TutorialNotebookEditorPanel, TutorialNotebookDeveloper> {

    public TutorialLittleManGui() {
        super(new TutorialNotebookDeveloperGui(new SubmissionControllerTutorialGui()), new TutorialNotebookEditorPanel());
    }

}
