/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.computer;

import littlemangame.computer.computercomponents.OfficeInputter;
import littlemangame.computer.computercomponents.OfficeOutputter;
import littlemangame.computer.computercomponents.Notebook;
import littlemangame.computer.computercomponents.NotebookPageSheet;
import littlemangame.computer.computercomponents.Worksheet;

/**
 *
 * @author brian
 */
public class Office extends GenericOffice<Worksheet, Notebook, NotebookPageSheet, OfficeOutputter, OfficeInputter> {

    public Office(OfficeOutputter outputPanel, OfficeInputter inputPanel) {
        super(new Worksheet(), new Notebook(), new NotebookPageSheet(), outputPanel, inputPanel);
    }

}
