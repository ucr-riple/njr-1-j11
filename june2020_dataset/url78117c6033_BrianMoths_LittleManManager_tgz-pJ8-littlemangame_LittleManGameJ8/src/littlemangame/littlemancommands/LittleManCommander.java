/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.littlemancommands;

import littlemangame.computer.Office;
import littlemangame.littleman.LittleMan;
import littlemangame.notebookdeveloper.gui.OfficeView;

/**
 *
 * @author brian
 */
public class LittleManCommander extends GenericLittleManCommander<LittleMan> {

    /**
     * constructs a little man commander which is commanding the given little
     * man
     *
     * @param littleMan the little man to be commanded
     */
    public LittleManCommander(LittleMan littleMan) {
        super(littleMan);
    }

    /**
     * constructs a little man commander that commands a little man working in
     * the office of the given office view
     *
     * @param officeView the office view that the commanded little man should
     * work with
     */
    public LittleManCommander(OfficeView officeView) {
        this(new Office(officeView.getOutputPanel(), officeView.getInputPanel()));
    }

    /**
     * creates a little man commander commanding a new little man to work in the
     * given office
     *
     * @param office the office the commanded little man is to work in
     */
    public LittleManCommander(Office office) {
        super(new LittleMan(office));
    }

}
