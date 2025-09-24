/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.littleman.littlemanutilities.littlemandata;

import littlemangame.computer.Office;
import littlemangame.littleman.PositionGetterAdapter;

/**
 * most simple instance of {@link GenericLittleManData} class
 *
 * @author brian
 */
public class LittleManData extends GenericLittleManData<Office> {

    /**
     * creates a little man data with the given office and position getter
     * adapter
     *
     * @param office the office of the little man data
     * @param positionGetterAdapter the position getter adapter of the little
     * man data
     */
    public LittleManData(Office office, PositionGetterAdapter positionGetterAdapter) {
        super(office, positionGetterAdapter);
    }

}
