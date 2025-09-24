/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.tutorial.gui;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import littlemangame.notebookdeveloper.gui.NotebookEditorPanel;

/**
 *
 * @author brian
 */
public class TutorialNotebookEditorPanel extends NotebookEditorPanel {

    public TutorialNotebookEditorPanel() {
        super();
    }

    public void setPageEditorIsEnabled(int pageNumber, boolean isEnabled) {
        memorySlotChoosers.get(pageNumber).setEnabled(isEnabled);
    }

    public void addPageEditorItemListener(int pageNumber, ItemListener itemListener) {
        memorySlotChoosers.get(pageNumber).addItemListener(itemListener);
    }

    public void removePageEditorItemListener(int pageNumber, ItemListener itemListener) {
        memorySlotChoosers.get(pageNumber).removeItemListener(itemListener);
    }

    public void setSaveButtonIsEnabled(boolean isEnabled) {
        saveButton.setEnabled(isEnabled);
    }

    public void addSaveButtonActionListener(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }

    public void removeSaveButtonActionListener(ActionListener actionListener) {
        saveButton.removeActionListener(actionListener);
    }

}
