package frs.hotgammon.view.tools;

import java.awt.event.MouseEvent;

import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Game;
import frs.hotgammon.framework.GameObserver;
import frs.hotgammon.view.figures.DieFigure;

import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.standard.AbstractTool;

public class DieRollTool extends AbstractTool{

	private Game game;
	
	public DieRollTool(DrawingEditor editor, Game game) {
		super(editor);
		this.game = game;
	}

	public void mouseUp(MouseEvent e, int x, int y) { 
		
		Drawing model = editor().drawing();
	    
	    model.lock();
	    
	    Figure clickedFig = model.findFigure(e.getX(), e.getY());
		
	    model.unlock();
	    
	    if (isDieFigure(clickedFig)){
	    	game.nextTurn();
	    }
	    else{
		   //Notify Observers
			  for( GameObserver gO : this.game.getObservers() ){
				  gO.setStatus(this.game.getPlayerInTurn().toString() + "'s turn is over. The dice must be rolled for the next turn to start.");
			  }
			//
	    }

	}
	
	private boolean isDieFigure(Figure f){
		return f != null && (f instanceof DieFigure);
	}

}
