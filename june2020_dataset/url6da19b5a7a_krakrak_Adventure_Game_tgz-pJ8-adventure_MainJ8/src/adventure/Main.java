/*
 * Object oriented programming course
 * Department of Computers and Informatics at Technical University of Kosice
 * http://kpi.fei.tuke.sk
 */

package adventure;
import commands.*;


/**
 * Main class
 * @author AUTHOR
 */
public class Main{

    /**
     * Main method of the text game, creates new game and initialize it
     * <p>
     * @param args the command line arguments
     * 
     */
    public static void main(String[] args) {
        Game game = new GameImpl();
        game.init();
        
    }
}
