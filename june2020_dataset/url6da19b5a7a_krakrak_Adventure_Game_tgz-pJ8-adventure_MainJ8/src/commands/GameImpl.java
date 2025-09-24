/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import items.*;
import NPC.*;
import rooms.*;

/**
 * Method implementing the interface game
 * @author Irakiza Krak
 */
public class GameImpl implements Game{
    private GameState gameState ;
    private Room current;
    private Game game;
    private BackPack backPack;
    private Parser cmd = new ParserImpl();
    private Command usedCommand;
  
    @Override
    public GameState getGameState(){
        return gameState;
    }
    
    @Override
    //Use switch
    public void setGameState(String state){
        for(GameState name : GameState.values())
            if(state.equals(name.name()))
                gameState = GameState.valueOf(state);
    }
    /**
     *
     * @param com
     */
    @Override
    public void processCommand(String com){
        usedCommand = cmd.getCommand(com);
        usedCommand.execute(this, com);
    }
    
    @Override
    public void start(){
            Textual nGame = new Textual();
            //Initializing backpack
            game.setBackPack(4);
            nGame.loop(game);
        }
    
    //Game initialisation
    
    @Override
    public void init(){
        //Initializing game
        Game g;
        g = new GameImpl();
        gameState = GameState.PLAYING;
        g.setGameState(gameState.name());
        createRoom();
        g.setCurrentGame(this);
        g.setCurrentRoom(current);
        Menu menu = new Menu();
        menu.execute(g, "");
        
        
        
    }
    
    private void createRoom(){
        //Test Chest avec le design pattern
        Item chest1 = ChestCreator.createChest();
        Item chest2 = ChestCreator.createChest();
        Item chest3 = ChestCreator.createChest();
        Item chest4 = ChestCreator.createChest();
        Item chest5 = ChestCreator.createChest();
        Item chest6 = ChestCreator.createChest();
        Item chest7 = ChestCreator.createChest();
        Item chest8 = ChestCreator.createChest();
        Item chest9 = ChestCreator.createChest();
        chest9.addItem(new DiamondCompanionsSword());
        //chest9.addItem(new BlackKey()); //TO REMOVE FOR THE FINAL VERSION OF THE GAME
        Item dragonbody = new DragonBody();
        dragonbody.setName("Dragon Body");
        dragonbody.setDescription("The body of the killed dragon");
        dragonbody.setShortC("bdy");
        dragonbody.addItem(new DragonHead());
        GoalBook goalbook2 = new GoalBook(2);
        goalbook2.setGoal(" \n Find the member of The Companions called Skjor");
        GoalBook goalbook3 = new GoalBook(3);
        goalbook3.setGoal("\n Find the Wuuthraad axe fragment");
        Item sword = new Sword();
        Item key = new Key();
        Item goalbook1 = new GoalBook(1);
        chest1.addItem(key);
        chest2.addItem(sword);
        chest2.addItem(goalbook1);
        chest2.addItem(key);
        chest3.addItem(sword);
        chest3.addItem((new RottenApple()));
        chest4.addItem(new CompanionsRing());
        chest5.addItem(key);
        chest5.addItem(sword);
        chest5.addItem(new Apple());
        chest5.addItem(goalbook2);
        chest6.addItem(new Apple());
        chest6.addItem(key);
        chest6.addItem(sword);
        chest6.addItem(goalbook3);
        chest6.addItem(new FriedChicken());
        chest6.addItem(new RottenFriedChicken());
        chest7.addItem(new WuuthradFragment());
        chest7.addItem(new DustmansCairnKey());
        chest7.addItem(new RottenFriedChicken());
        chest8.addItem(new FriedChicken());
        
        //Room creation
        Room Vydrnik =  new NormalRoom("Vydrnik", "You are at the train station, It's in the morning, you are fresh and ready to take over the Slovak Paradise");
        Room Hrabusice =  new NormalRoom("Hrabusice","Small village, in a sunny day, people pumping water from a well \n You're not used to seeing that \n You're infront of the big map of the Slovak Paradise \n where to go now? \n Anywhere, this place doesn't offer"
                + "many alternatives \n Only one road crosses the village, if you continue strait, you'll head to Podlesok");
        Room Podlesok =  new NormalRoom("Podlesok","This is the place where every hiker comes before taking over the adventure. You can find useful things here and choose which way to go \n"
                + "Since this is the central place of the Slovensky Raj, you\'ll have to come back here again" );
        Room Betlanovce =  new NormalRoom("Betlanovce", "Village around the entry around the slovak paradis, chance of equiping yourself");
        Room KlastorkaRoklina = new LockedRoom("Klastorka Roklina","Still breathing heavily, \n "
                + "You find yourself in what looks like the Companions lair \n"
                + "all of them wear the Companions clothes \n"
                + "On old man seems like the leader you head to him "
                + "\n He is with another ma, busy talking");
        Room Klastorisko = new LockedRoom("Klastorisko","Training yard with a range of some straw dummies");
        
        Room SuchaBela =  new NormalRoom("Sucha Bela", "You start right away, the beginning is easy. You can see a mysterious hole on your right. There is no other way by the way"
                + " Let's try this !!");
        //Row of caves of Sucha Bela
        
        Room caveSB1 = new LockedRoom("Cave", "The cave is dark, you want to go back, but your curiosity exceeds your fear "
                + "\n Furthermore you want to find out about these Companions"
                + "\n Must be some sort of joke");
        Room caveSB2 = new LockedRoom("Cave", "The next cave is darker than the previous one, you hear some strange sounds, there's a strange creature \n"
                
                + "you want to run back, but there's no more exits \n \n"
                + "You can see an exit but it's locked, find your way out \n"
                + "Hint : Kill the skeleton in the room, it might drop something (this goes for every locked room where there is a creature)");
        Room caveSB3 = new Cave("Cave", "Finally you can see the exit, you don't believe what's happening here \n"
                + "You are still sweaty and tired after these brief battle");
        //Setting hidden exits for this locked room
        
        Room HrdloHornadu =  new NormalRoom("Hrdlo Hornadu", "In this little village where you can see a thick forest on your left \n"
                + "There is a group of people \n"
                + "You get closer and you see what seems to be a giant lying in the middle of them. Their sword are stained in blood "
                + "A man and woman are looking at you");
        //Row of caves of Hrdlo Hornadu
        
        Cave caveL1 = new Cave("Cave", "In this cave, Strangely very lighted, you find Aela \n"
                + "You can't hide your surpise \n"
                + "It seems that they have been following you since the beginning \n"
                + "You wonder if all this was not planned");
        Cave caveL2 = new Cave("Cave", "By curiosity, you followed Aela, three skeletons are waiting for you, Aela kills two while \n"
                + "you hesitate");
        Room caveL3 = new LockedRoom("Cavelet", "In the center of this cave, you can see a bowl like for some ritual \n \n"
                + "Suddenly Aela turns into a werewolf ");
        Room TomasovskyVyhlad = new NormalRoom("Tomasovsky Vyhlad","Finally out of these caves, you don't believe what you saw, still out of breath \n"
                + "Aela is not with you anymore");
        
        Room Letanovce = new LockedRoom("Letanovce"," You are at the forge, the biggest you have ever seen  \n"
                + "You wonder how there could still be a forge this days \n"
                + "Suddenly you realize that maybe your normal life is gone");
        
        Room LetanovskyMlyin = new NormalRoom("Letanovsky Mylin","Skjor place, shy lighting, he is sitting and reading ");
        Room NadPodleskom = new LockedRoom("Nad Podleskom","This place looks strange, there is a door but it's locked");
        //Row of cas of NadPodleskom
        
        Cave caveNP1 = new Cave("Dustman's Cairn\'s cave", "In this first cave, at first you don't see anything"
                + " \n Too dark!"
                + "You become slowly accustomed by the light");
      
        Room caveNP2 = new MagicRoom("Dustman's Cairn\'s second cave", "You continue to the second cave, in this cave you encounter a new type of creatures \n"
                + "And you feel this place is of a different kind from the other places.\n"
                + "Farkas tells you that they are called Draugr \n"
                + "Farkas deals with one while you deal with the second");
        Cave caveNP3 = new Cave("Dustamn's Cairn's third cave", "This chamber contains two thrones, and only one exit blocked by a gate except the one you came from");
        
        Cave caveNP4 = new Cave("Dustman's Cairn's fourth cave", "It's a trap ! \n"
                + "The lever is broken in the previous cave, no way to go back \n"
                + "Farkas is still in the other Room and through the gate, you can see that \n"
                + "many Draugrs coming from nowhere \n"
                + "are sorrounding him \n"
                + "He is totally outnumbered \n"
                + "Suddenly he turns into a werewolf and wipes them \n"
                + "out easily");
        
        Room caveNP5 = new LockedRoom("Dustman's Cairn's fifth cave", "You managed to see the lever sealed in the wall after witnessing the battle between. \n"
                + "Farkas must have found another way ");
        
        Room caveNP6 = new LockedRoom("Dustman's Crypt", "A small crypt, looks like all the draugrs were coming from here. All the tombs are empty");
        
        Room Cingov = new NormalRoom("Cingov", "Outside of Dustman's caves, you find Vilkas waiting for you");
        Room KosiamyBriezok = new NormalRoom("Kosiamy Briezok", "You find most of the Companion's members where Vilkas led you. You wonder what's going on. \n"
                + "You decide to talk to all of them to understand");
        Room MedvediaHlava = new NormalRoom("Medvedia Hlava", "...");
        Room KralorParamen = new LockedRoom("Kralor Paramen", "...");
        Room caveKP1 = new MagicRoom("Skjor's lair", "What is happening, this room is filled with \n"
                + "alot of monsters. And You are trapped! \n You have to fight your way aout");
        Room caveKP2 = new Cave("Skjor's lair 2", "You find the most influential people in the Companion's"
                + " sarrounding an empty fountain");
        Room Pieskovec = new NormalRoom("Pieskovec", "Lights are shivering, moving to the rythm of some heavy and hot breath");
        
        Room SpisskaNovaVes = new NormalRoom("Spisska Nova Ves", "\"Next Stop Vydrinik \", wait ... \n"
                + "You know this voice \n"
                + "Where have you heard it ? \n"
                + "... \n  \n"
                + "Aaah yes ! The woman in the train that announces stops \n"
                + "So this was all a dream \n"
                + "You can't believe it \n"
                + "You lay your head on the chair \n"
                + "And doze off again....");
        
        //Setting exits
        Vydrnik.setExits(null, Hrabusice, null, null); 
        Hrabusice.setExits(Vydrnik, Podlesok, null, null);
        Podlesok.setExits(Hrabusice, SuchaBela, HrdloHornadu, Betlanovce);
        Betlanovce.setExits(null, null, Podlesok, null);
        SuchaBela.setExits(Podlesok, null, null, caveSB1);
        caveSB1.setExits(null, null, SuchaBela, caveSB2);
        caveSB2.setExits(null, null, null, null);
        caveSB3.setExits(null, null, null, KlastorkaRoklina);
        HrdloHornadu.setExits(null, null, null, Podlesok);
        KlastorkaRoklina.setExits(null, null, caveSB3, null);
        Klastorisko.setExits(KlastorkaRoklina, null, Letanovce, null);
        Letanovce.setExits(null, null, null, Klastorisko);
        caveL1.setExits(Letanovce, null, null, caveL2);
        caveL2.setExits(null, caveL3, caveL2, null);
        caveL3.setExits(caveL2, null, null, null);
        TomasovskyVyhlad.setExits(caveL3, NadPodleskom, null, LetanovskyMlyin );
        LetanovskyMlyin.setExits(null, null, TomasovskyVyhlad, null);
        NadPodleskom.setExits(TomasovskyVyhlad, null, null, null);
        caveNP1.setExits(NadPodleskom, null, null, caveNP2);
        caveNP2.setExits(null, null, null, caveNP1);
        caveNP3.setExits(null, null, caveNP2, null);
        caveNP4.setExits(null, null, null, null);
        caveNP5.setExits(caveNP4, null, null, null);
        caveNP6.setExits(caveNP5, null, null, null);
        Cingov.setExits(caveNP6, null, null,KosiamyBriezok);
        KosiamyBriezok.setExits(MedvediaHlava, KralorParamen, Cingov, Pieskovec);
        Pieskovec.setExits(null, null,KosiamyBriezok , null);
        KralorParamen.setExits(KosiamyBriezok , null, null, null);
        MedvediaHlava.setExits(null, KosiamyBriezok, null, null);
        caveKP1.setExits(null, null, null, null);
        caveKP2.setExits(null, null, null, null);
        
        //Creating secret exits
        KlastorkaRoklina.setSecretExit(game, Klastorisko);
        caveSB1.setSecretExit(game, caveSB2);
        caveSB2.setSecretExit(game, caveSB3);
        caveL3.setSecretExit(game, TomasovskyVyhlad);
        Letanovce.setSecretExit(game, caveL1);
        NadPodleskom.setSecretExit(game, caveNP1);
        caveNP2.setSecretExit(game, caveNP3);
        caveNP3.setSecretExit(game, caveNP4);
        caveNP4.setSecretExit(game, caveNP5);
        caveNP5.setSecretExit(game, caveNP6);
        caveNP6.setSecretExit(game, Cingov);
        KralorParamen.setSecretExit(game, caveKP1);
        caveKP1.setSecretExit(game, caveKP2);
        caveKP2.setSecretExit(game, SpisskaNovaVes);
        
        
        //Creating chests & items in the room
        Hrabusice.addItem(new Key());
        Podlesok.addItem(chest1);
        Betlanovce.addItem(chest2);
        caveSB1.addItem(new Scroll(1));
        caveSB2.addItem(chest3);
        caveSB3.addItem(chest4);
        caveSB3.addItem(key);
        TomasovskyVyhlad.addItem(key);
        TomasovskyVyhlad.addItem(chest5);
        caveNP3.addItem(new Lever());
        caveNP4.addItem(new Lever());
        caveNP6.addItem(chest7);
        KosiamyBriezok.addItem(chest8);
        Pieskovec.addItem(chest9);
        
        
        //Adding Npcs to the roomwe
        Podlesok.addNpc(new Samoth());
        Podlesok.addNpc(new Zrogezrg());
        Hrabusice.addNpc(new Aela());
        HrdloHornadu.addNpc(new Farkas());
        HrdloHornadu.addNpc(new Ria());
        Npc skeleton1 = new Skeleton();
        skeleton1.addItem(new UnlockSpellBook());
        caveSB2.addNpc(skeleton1);
        caveSB1.addNpc(skeleton1);
        KlastorkaRoklina.addNpc(new KodlakWhitemane());
        KlastorkaRoklina.addNpc(new Vilkas());
        Npc strawdummy = new StrawDummy();
        strawdummy.addItem(new CompanionsSword());
        Klastorisko.addNpc(strawdummy);
        Klastorisko.addNpc(new Vilkas());
        Letanovce.addNpc(new Eorlund());
        caveL1.addNpc(new Aela("blabla"));
        caveL2.addNpc(new Skeleton());
        Npc aela = new WereWolfAela();
        aela.addItem(new TransformBackSpellBook());
        caveL3.addNpc(aela);
        LetanovskyMlyin.addNpc(new Skjor());
        Npc farkas = new Farkas();
        farkas.setStatement("So we see each other again ! Don't get in my way \n"
                + "I will retrieve this fragment");
        caveNP1.addNpc(farkas);
        caveNP1.addItem(chest6);
        Npc farkas2 = new Farkas();
        farkas2.setStatement("Show me what you got ! Take care of the Draugrs, you can't kill them with a normal sword");
        caveNP2.addNpc(farkas2);
        Npc draugr = new Draugr();
        draugr.addItem(new UnlockSpellBookMagic());
        caveNP2.addNpc(draugr);
        Npc farkas3 = new Farkas();
        farkas3.setStatement("Go and use the lever to open the gate !");
        caveNP3.addNpc(farkas3);
        Npc draugr2 = new Draugr();
        draugr2.addItem(new UnlockSpellBook());
        draugr2.addItem(new Apple());
        caveNP5.addNpc(draugr2);
        Cingov.addNpc(new Vilkas());
        KosiamyBriezok.addNpc(new KodlakWhitemane());
        KosiamyBriezok.addNpc(new Farkas());
        KosiamyBriezok.addNpc(new Aela());
        KosiamyBriezok.addNpc(new Vilkas());
        KosiamyBriezok.addNpc(new Skjor());
        KosiamyBriezok.addNpc(new Eorlund());
        KralorParamen.addNpc(new Skjor());
        MedvediaHlava.addNpc(new Eorlund());
        Npc dragon = new Dragon();
        dragon.addItem(dragonbody);
        Npc draugr3 = new Draugr();
        draugr3.addItem(key);
        Pieskovec.addNpc(draugr3);
        Pieskovec.addNpc(dragon);
        //Pieskovec.addItem(new BlackKey()); //TO REMOVE FOR THE FINAL VERSION
        caveKP1.addNpc(new Skeleton());
        caveKP1.addNpc(new Skeleton());
        caveKP1.addNpc(new Skeleton());
        caveKP1.addNpc(new Draugr());
        caveKP1.addNpc(new Draugr());
        caveKP1.addNpc(new Draugr());
        caveKP1.addNpc(new Skeever());
        caveKP1.addNpc(new Skeever());
        Npc skeever = new Skeever();
        skeever.addItem(new UnlockSpellBookMagic());
        caveKP1.addNpc(skeever);
        caveKP2.addNpc(new Aela());
        caveKP2.addNpc(new KodlakWhitemane());
        caveKP2.addNpc(new Vilkas());
        caveKP2.addNpc(new Skjor());
        
        
        
        
        
        
        
        setCurrentRoom(Vydrnik);
        
    }

    @Override
    public Room getCurrentRoom() {
        return this.current;
    }


    @Override
    public void setCurrentRoom(Room room) {
        current = room;
    }
    
    //Method that allows us to get the game variable private in this class
    public Game getCurrentGame(){
        return game;
    }
    
    @Override
    public void setCurrentGame(Game currGame){
        game = currGame;
    }

    @Override
    public BackPack getBackPack() {
        return backPack;
    }

    @Override
    public void setBackPack(int size) {
        backPack = new BackPackImpl(4);
    }
 }
