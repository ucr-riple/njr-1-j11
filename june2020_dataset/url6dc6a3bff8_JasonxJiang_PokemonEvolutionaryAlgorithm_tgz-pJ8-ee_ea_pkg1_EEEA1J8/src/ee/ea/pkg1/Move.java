/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1;

import ee.ea.pkg1.types.Type;


/**
 * Write a description of interface Move here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface Move
{
    int recoilDamage(int damageDealt);
    int statBoost();
    int statDrop();
    int recoverHealth();
    double typeModifier();
    Type getType();
    String showType();
}
