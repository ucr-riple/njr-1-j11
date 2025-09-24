/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.types;

/**
 *
 * @author Jason
 */
public class None extends Type{
    
    public None()
    {
        effectiveTypes = new Type [0];
        resistantTypes = new Type [0];
        immuneTypes = new Type [0];
        nothing = 0;
        name = "None";
    }
}
