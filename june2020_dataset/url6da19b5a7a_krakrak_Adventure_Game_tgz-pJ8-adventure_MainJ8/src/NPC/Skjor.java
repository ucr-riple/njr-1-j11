/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NPC;

import commands.Game;
import commands.GameState;
import items.FriedChicken;
import items.IronCompanionsSword;
import items.Item;
import items.Key;
import items.SilverCompanionsSword;
import items.UnlockSpellBook;

/**
 *
 * @author Irakiza Krak
 */
public class Skjor extends AbstractNpc{
    private Item ironCompanionsSword = null;
    
     public Skjor(){
        super("Skjor","Short and muscular man with a red sign of the Companions on his surcoat", "Finally here you are, here a mission for you : ", "Skj");
        
    }
    @Override
    public GameState action(Game game){
        boolean contains = false;
        if(game.getCurrentRoom().getName().equalsIgnoreCase("Kosiamy Briezok")){
            System.out.println("Congratulations ! Meet me in "+game.getCurrentRoom().getSouth().getName() + " for your next assignment and let's hope not your last assignment");
            for(Item key : game.getBackPack().getItems()){
                if(key instanceof Key)
                    contains = true;
            }
            if(!contains){
                game.getBackPack().add(new Key());
                System.out.println(this.getName() +" added a key to your backpack");
            }
            game.getBackPack().add(new FriedChicken());
            System.out.println(this.getName() +" added a fried chicken to your backpack");
            game.getCurrentRoom().removeNpcs(this);
        }else if(game.getCurrentRoom().getName().equalsIgnoreCase("Kralor Paramen")){
            if(game.getBackPack().getItem("Steel Companion's Sword") != null){
                System.out.println("So here you are, the new Companion \n"
                        + "This is not specially an assignment, but you will see. Here take this :");
                game.getBackPack().add(new UnlockSpellBook());
            }else {
                System.out.println("Go and meet Eorlund Grey-Mane first and take whet he gives, you will need it");
            }
        }
        else if(game.getCurrentRoom().getName().equalsIgnoreCase("Skjor's lair 2")){
            System.out.println("Finally here, hope that those creatures were not very difficult for you");
        }
        else{
            System.out.println(this.getStatement());
            System.out.println("You'll team up with Farkas and head to the NadPodleskom caves \" Dustman's Cairns\". \n this might help you find them");
            game.getBackPack().add(new UnlockSpellBook());
            System.out.println("And this Silver Sword of the Companion's might help you, since this time you'll be facing \n"
                    + "real monsters");
            for(Item weapon : game.getBackPack().getItems()){
                if(weapon instanceof IronCompanionsSword){
                    ironCompanionsSword = weapon;
                    System.out.println("Iron Companions Sword replaced by : \n \t");
                }
            }
            if(ironCompanionsSword != null) 
                game.getBackPack().remove(ironCompanionsSword.getName());
        
            System.out.println(new SilverCompanionsSword().getName());
            game.getBackPack().setCapacity(3);
            System.out.println(this.getName() + " increased the capacity of your backpack");
            game.getBackPack().add(new SilverCompanionsSword());
            System.out.println("\t One thing that has been wanted by the Companion's to be able to defeat the mighty evil is the  \n"
                   + "the two-handed silver axe called \" Wuuthradd \"."
                    + "\n We have a few fragments of it, one has been located in this caves \" Dustman's Cairns \" "
                    + "So you and Farkas will head there and retrieve it !");
        }
        return game.getGameState();
    }
}
