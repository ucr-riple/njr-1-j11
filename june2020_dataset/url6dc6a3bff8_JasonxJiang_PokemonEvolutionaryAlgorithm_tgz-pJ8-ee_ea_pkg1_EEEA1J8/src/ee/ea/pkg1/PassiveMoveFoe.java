package ee.ea.pkg1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Write a description of interface passiveMoveFoe here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface PassiveMoveFoe 
{
    /**
     * An example of a method header - replace this comment with your own
     * 
     * @param  y    a sample parameter for a method
     * @return        the result produced by sampleMethod 
     */
    String inflictStatus();
    String inflictSecondaryStatus();
    double boostStat();
    double reduceStat();
    double drainHealth();
}

