/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.tutorial.tutorialnotebookdeveloper;

import littlemangame.notebookdeveloper.GenericNotebookDeveloper;
import littlemangame.tutorial.gui.TutorialOfficeView;
import littlemangame.tutorial.tutoriallittleman.TutorialLittleMan;
import littlemangame.tutorial.tutoriallittlemancommands.TutorialLittleManCommander;

/**
 *
 * @author brian
 */
public class TutorialNotebookDeveloper extends GenericNotebookDeveloper<TutorialLittleManCommander> {

    public TutorialNotebookDeveloper(TutorialOfficeView tutorialOfficeView) {
        super(new TutorialLittleManCommander(tutorialOfficeView));
    }

    public void setIsWorksheetArrowShown(boolean isArrowShown) {
        getLittleManComander().setIsWorksheetArrowShown(isArrowShown);
    }

    public void setIsNotebookArrowShown(boolean isArrowShown) {
        getLittleManComander().setIsNotebookArrowShown(isArrowShown);
    }

    public void setIsNotebookPageSheetArrowShown(boolean isArrowShown) {
        getLittleManComander().setIsNotebookPageSheetArrowShown(isArrowShown);
    }

    public void setIsInputPanelArrowShown(boolean isArrowShown) {
        getLittleManComander().setIsInputPanelArrowShown(isArrowShown);
    }

    public void setIsOutputPanelArrowShown(boolean isArrowShown) {
        getLittleManComander().setIsOutputPanelArrowShown(isArrowShown);
    }

    public TutorialLittleMan getLittleMan() {
        return littleManCommander.getTutorialLittleMan();
    }

}
