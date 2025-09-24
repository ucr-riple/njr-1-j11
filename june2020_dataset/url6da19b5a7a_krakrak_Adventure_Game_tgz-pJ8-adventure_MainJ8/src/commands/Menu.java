/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import java.util.Scanner;

/**
 * Menu of the game
 * @author Irakiza Krak
 */
public class Menu extends AbstractCommand{

    private int choix;
    
    //Constructeur
    public Menu(){
        super("MENU", "Shows the menu", "menu");
    }

    /**
     *
     * @param game
     */
    @Override
        public void execute(Game game, String params) {
            if(params.equalsIgnoreCase("quit")){
                System.out.println("Slovensky raj : Welcome");
                System.out.println("******************************");
                System.out.println("Menu :");
                System.out.println("1 - Restart Game");
                System.out.println("2 - Quit Game");
            
                    
                Scanner reader;
                reader = new Scanner( System.in );
                choix = reader.nextInt();
                while((choix != 1 && choix != 2)){
                    System.out.println("Enter a valid value");
                    reader = new Scanner(System.in);
                    choix = reader.nextInt();
                }
                if(choix == 1){
                    game.init();
                }
                else if(choix == 2){
                    Command end;
                    end = new End();
                    end.execute(game, null);
                }
            }
            else if(params.equalsIgnoreCase("finished")){
                System.out.println("Slovensky raj : Want to try again ?");
                System.out.println("******************************");
                System.out.println("Menu :");
                System.out.println("1 - Restart Game");
                System.out.println("2 - Quit Game");
            
                    
                Scanner reader;
                reader = new Scanner( System.in );
                choix = reader.nextInt();
                while((choix != 1 && choix != 2)){
                    System.out.println("Enter a valid value");
                    reader = new Scanner(System.in);
                    choix = reader.nextInt();
                }
                if(choix == 1){
                    game.init();
                }
                else if(choix == 2){
                    Command end;
                    end = new End();
                    end.execute(game, null);
                }
            }else {
                System.out.println("Welcome to the Adventure Game");
                System.out.println("******************************");
                System.out.println("Initial Menu :");
                System.out.println("1 - Start Game");
                System.out.println("2 - Quit Game");
            
                Scanner reader;
                reader = new Scanner( System.in );
                choix = reader.nextInt();
                while((choix != 1 && choix != 2)){
                    System.out.println("Enter a valid value");
                    reader = new Scanner(System.in);
                    choix = reader.nextInt();
                }
                if(choix == 1){
                    game.start();
                }
                else if(choix == 2){
                    Command end;
                    end = new End();
                    end.execute(game, null);
                }
            }
        }
        
    }
    
