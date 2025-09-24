/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nextgen.model;

import java.util.HashMap;

/**
 *
 * @author Rodrigo
 */
public class Package extends Entity {

    public Package(String name, String description) {
        super(name, description);
    }
    
    @Override
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = super.toHashMap();        
        return map;
    }

}
