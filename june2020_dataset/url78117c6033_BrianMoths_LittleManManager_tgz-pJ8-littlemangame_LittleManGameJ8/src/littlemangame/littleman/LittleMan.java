/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.littleman;

import littlemangame.computer.Office;
import littlemangame.littleman.littlemanutilities.littlemandata.LittleManData;

/**
 *
 * @author brian
 */
public class LittleMan extends GenericLittleMan<LittleManData> {

    public LittleMan(Office office) {
        this(office, new PositionGetterAdapter());
    }

    private LittleMan(Office office, PositionGetterAdapter positionGetterAdapter) {
        super(new LittleManData(office, positionGetterAdapter), positionGetterAdapter);
    }

}
