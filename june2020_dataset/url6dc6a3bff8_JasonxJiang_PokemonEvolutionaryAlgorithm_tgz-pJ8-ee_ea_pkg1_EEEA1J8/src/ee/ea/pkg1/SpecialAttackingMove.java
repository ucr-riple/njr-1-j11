/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1;


/**
 * Write a description of interface Move here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface SpecialAttackingMove extends Move
{
    /**
     * An example of a method header - replace this comment with your own
     * 
     * @param  y    a sample parameter for a method
     * @return        the result produced by sampleMethod 
     */
    
    int recoilDamage(int damageDealt);
    int statBoost();
    int statDrop();
    int recoverHealth();
    double typeModifier();
}
