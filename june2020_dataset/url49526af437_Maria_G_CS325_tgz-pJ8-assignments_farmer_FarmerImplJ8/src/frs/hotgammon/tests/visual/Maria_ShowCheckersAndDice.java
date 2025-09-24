package frs.hotgammon.tests.visual;

import java.util.HashMap;

import minidraw.standard.*;
import minidraw.framework.*;

import javax.swing.*;

import frs.hotgammon.common.GameImpl;
import frs.hotgammon.common.VisualGameImpl;
import frs.hotgammon.framework.Game;
import frs.hotgammon.tests.preGuiTests.stubs.Fixed_BlackStarts_SemiMonFactory;
import frs.hotgammon.variants.factories.AlphaMonFactory;
import frs.hotgammon.variants.factories.BetaMonFactory;
import frs.hotgammon.variants.factories.DeltaMonFactory;
import frs.hotgammon.variants.factories.SemiMonFactory;
import frs.hotgammon.view.HotGammonApplication;
import frs.hotgammon.view.HotGammonDrawing;
import frs.hotgammon.view.tools.DieRollTool;
import frs.hotgammon.view.tools.GameOverTool;
import frs.hotgammon.view.tools.HotGammonTool;
import frs.hotgammon.view.tools.MoveTool;

/** Show the dice and some checkers on the
 * backgammon board.  
 * 
   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Computer Science Department
     Aarhus University
   
   This source code is provided WITHOUT ANY WARRANTY either 
   expressed or implied. You may study, use, modify, and 
   distribute it for non-commercial purposes. For any 
   commercial use, see http://www.baerbak.com/
 */
public class Maria_ShowCheckersAndDice {
  
  public static void main(String[] args) {
	    
	    //Game creation 
	    Game game = new VisualGameImpl(new BetaMonFactory());//AlphaMonFactory());//BetaMonFactory());//new DeltaMonFactory()
	   //
	  
	  DrawingEditor editor = 
    //  new MiniDrawApplication( "Show HotGammon figures...",  
     //                          new HotGammonFactory(game) );
	
	      new HotGammonApplication( "HotGammon Game",  
	                               new HotGammonFactory(), game );
	  
    editor.open();
    
    //HotGammonTool Setup
    final Tool dieRollTool = new DieRollTool(editor,game);
    final Tool moveTool = new MoveTool(editor,game);
    final Tool gameOverTool = new GameOverTool(editor);
    HashMap<String, Tool> states = new HashMap<String, Tool>(){{
		put( HotGammonTool.DIE_ROLL_TOOL, dieRollTool );
		put( HotGammonTool.MOVE_TOOL, moveTool );
		put( HotGammonTool.GAME_OVER_TOOL, gameOverTool);
		}};
	//
    
	//Add tool to Editor
    editor.setTool( 
    		new HotGammonTool(editor,game, HotGammonTool.DIE_ROLL_TOOL, states) );
    //
    
    
  }
}

class HotGammonFactory implements Factory {
		
  public DrawingView createDrawingView( DrawingEditor editor ) {
    DrawingView view = 
    		new StdViewWithBackground(editor, "board");
    return view;
  }

  public Drawing createDrawing( DrawingEditor editor ) {
    return new HotGammonDrawing(editor);
  }

  public JTextField createStatusField( DrawingEditor editor ) {
    JTextField statusField = new JTextField( "HotGammon Game" );
    statusField.setEditable(false);
    return statusField;
  }
}


