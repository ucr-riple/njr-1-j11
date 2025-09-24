/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdeveloper.notebooktester.littleManMock;

import littlemangame.computer.Office;
import littlemangame.littleman.LittleMan;
import littlemangame.littleman.littlemanutilities.location.OfficeLocation;

/**
 *
 * @author brian
 */
public class LittleManMock extends LittleMan {

    private final HaltListener haltListener;

    public LittleManMock(Office computer, HaltListener haltListener) {
        super(computer);
        this.haltListener = haltListener;
    }

    @Override
    public boolean goToComputerLocation(OfficeLocation computerLocation) {
        return true;
    }

    @Override
    public void halt() {
        super.halt();
        haltListener.acceptHalt();
    }

}
