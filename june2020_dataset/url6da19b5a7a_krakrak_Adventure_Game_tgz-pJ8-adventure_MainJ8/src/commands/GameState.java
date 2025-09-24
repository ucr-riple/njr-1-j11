/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

/**
 * Enumeration of different types of game states
 * @author Irakiza Krak
 */
public enum GameState {
    PLAYING ("Still playing"),
    GAMEOVER ("You died a Hero, or as a fool"),
    SOLVED("Excellent");
    
    private String state = "";
    
    //Constructeur
    /**
     * sets the state to the state contained in the parameters (useless?)
     * @param name 
     */
    GameState(String name){
        this.state = name;
    }
    
    @Override
    public String toString(){
        return state;
    }
 }

