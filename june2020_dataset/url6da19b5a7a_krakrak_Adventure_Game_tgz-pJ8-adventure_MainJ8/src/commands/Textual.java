/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import java.util.Scanner;
import rooms.*;

/**
 * The user interface implementation that has the loop
 * @author Irakiza Krak
 */
public class Textual implements UserInterface{
        private Room location;
        private  ParserImpl parse = new ParserImpl();
    /**
     *
     * @param text
     */
    @Override
    public void println(java.lang.String text){
        System.out.println(text);          
    }

    @Override
    public void loop(Game game) {
        GameState currentState = GameState.valueOf("PLAYING");
        location = game.getCurrentRoom();
        location.show();
        String line;
        Scanner reader;
            while(game.getGameState().equals(currentState)){
                reader = new Scanner( System.in );
                line = reader.nextLine();
                if (parse.getCommand(line) != null){
                    System.out.println("!__________________________________________________________________!");
                    game.processCommand(line);
                }else
                    System.out.println("Such Command doesn't exist");
            }
            if(game.getGameState() == GameState.GAMEOVER){
                System.out.println("Game over, restart or quit ?");
                Menu menu = new Menu();
                menu.execute(game, "quit");
            }
            else if(game.getGameState() == GameState.SOLVED){
                System.out.println("Congratulations !!! \n"
                        + "You finished the game successfuly \n"
                        + "You can replay or quit \n"
                        + "Next time");
                new Version().execute(game, "");
                Menu menu = new Menu();
                menu.execute(game, "finished");
            }

        }
    

    @Override
    public void print(String text) {
        System.out.print(text);
    }
    
    
}
