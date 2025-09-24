/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdeveloper.gui;

import littlemangame.computer.computercomponents.InputPanel;
import littlemangame.computer.computercomponents.OutputPanel;

/**
 *
 * @author brian
 */
public class OfficeView extends GenericOfficeView<InputPanel, OutputPanel> {

    public OfficeView() {
        super(new InputPanel(), new OutputPanel());
    }

}
