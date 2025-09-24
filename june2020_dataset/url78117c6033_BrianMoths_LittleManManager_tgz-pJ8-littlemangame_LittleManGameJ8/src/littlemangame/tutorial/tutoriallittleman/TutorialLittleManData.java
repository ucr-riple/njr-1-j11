/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.tutorial.tutoriallittleman;

import littlemangame.tutorial.tutorialoffice.TutorialOffice;
import littlemangame.littleman.PositionGetterAdapter;
import littlemangame.littleman.littlemanutilities.littlemandata.GenericLittleManData;

/**
 *
 * @author brian
 */
public class TutorialLittleManData extends GenericLittleManData<TutorialOffice> {

    public TutorialLittleManData(TutorialOffice computer, PositionGetterAdapter positionGetterAdapter) {
        super(computer, positionGetterAdapter);
    }

    public void setIsWorksheetArrowShown(boolean isArrowShown) {
        getComputer().setIsWorksheetArrowShown(isArrowShown);
    }

    public void setIsNotebookArrowShown(boolean isArrowShown) {
        getComputer().setIsNotebookArrowShown(isArrowShown);
    }

    public void setIsNotebookPageSheetArrowShown(boolean isArrowShown) {
        getComputer().setIsNotebookPageSheetArrowShown(isArrowShown);
    }

    public void setIsInputPanelArrowShown(boolean isArrowShown) {
        getComputer().setIsInputPanelArrowShown(isArrowShown);
    }

    public void setIsOutputPanelArrowShown(boolean isArrowShown) {
        getComputer().setIsOutputPanelArrowShown(isArrowShown);
    }

}
