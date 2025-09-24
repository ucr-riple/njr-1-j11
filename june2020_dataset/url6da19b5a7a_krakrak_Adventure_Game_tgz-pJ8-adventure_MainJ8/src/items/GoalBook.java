/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import commands.Game;
import commands.GameState;
import java.util.List;

/**
 *
 * @author Irakiza Krak
 */
public class GoalBook extends AbstractItemMovable{
    
    private String goal = " \n The companions \n \n"
            + "Warriors dedicated to protect the paradise from hell just underneath it"
            + "\n Warriors that work in the shadow and unknown to the world "
            + "\n Unless they decide to show themselves"
            + "\n Their commander is Kodlak Whitemane, found in Klastorka Roklina"
            + "\n The companions headquarter"
            + "\n \n Author : Unknown";
    
    public GoalBook(int num){
        super("Goal Book"+ num, "A yellow book with the Companions sign","Goal bo");
    }
    @Override
    public List<Item> getItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameState use(Game game) {
    System.out.println(this.getName() +"\n \t"+this.getGoal());
    game.getBackPack().remove(this.getName());
    return game.getGameState();
    }

    @Override
    public void addItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getGoal(){
        return this.goal;
    }
    
    public void setGoal(String nGoal){
        this.goal = nGoal;
    }
}
