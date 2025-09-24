package frs.hotgammon.view.tools;

import java.awt.Point;
import java.awt.event.MouseEvent;

import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Game;
import frs.hotgammon.framework.GameObserver;
import frs.hotgammon.framework.Location;
import frs.hotgammon.view.Convert;
import frs.hotgammon.view.figures.CheckerFigure;

import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.framework.Tool;
import minidraw.standard.AbstractTool;
import minidraw.standard.NullTool;
import minidraw.standard.handlers.DragTracker;

public class MoveTool extends AbstractTool{

	private Game game;
	private DrawingEditor editor;
	private Point originalPt;
	
	/** Subtool to delegate to. The selection tool is in itself
    a state tool that may be in one of several states given
    by the sub tool.
    Class Invariant: fChild tool is never null */
	protected Tool fChild;
	/** helper null tool to avoid creating and destroying objects
    all the time */
	protected Tool cachedNullTool;
 
	/** the figure that is being dragged. If null then its operation
     is not that of dragging a figure (or a set of figures) */
	protected Figure draggedFigure;


	
	public MoveTool(DrawingEditor editor, Game game) {
		super(editor);
		this.editor = editor;
		this.game = game;

		fChild = cachedNullTool = new NullTool();
		draggedFigure = null;
	}

	private boolean isCheckerFigure(Figure f){
		return f != null && (f instanceof CheckerFigure);
	}
	
	  
	 /**
	  * Handles mouse down events and starts the corresponding tracker.
	  */
	 public void mouseDown(MouseEvent e, int x, int y)
	 {
	   Drawing model = editor.drawing();
	   
	   model.lock();
	
	   draggedFigure = model.findFigure(e.getX(), e.getY());
	   
	   model.unlock();
	
	   if ( isCheckerFigure(draggedFigure) ) {
	     fChild = createDragTracker( draggedFigure );
	 	 originalPt = new Point(e.getX(), e.getY());
	   }
	   else{
		   //Notify Observers
			  for( GameObserver gO : this.game.getObservers() ){
				  gO.setStatus("It is " + this.game.getPlayerInTurn().toString() + "'s turn, you must move " + this.game.getNumberOfMovesLeft() + " checker(s)");
			  }
			//
	   }
	   
	   fChild.mouseDown(e, x, y);
	 }
	     
	 public void mouseDrag(MouseEvent e, int x, int y) {
	   fChild.mouseDrag(e, x, y);
	 }
	
	 public void mouseMove(MouseEvent e, int x, int y) {
	   fChild.mouseMove(e, x, y);
	 }
	
	 public void mouseUp(MouseEvent e, int x, int y) {
	
	   if ( isCheckerFigure(draggedFigure) ) {
		   Location from = Convert.xy2Location(originalPt.x, originalPt.y);
		   Location to = Convert.xy2Location(e.getX(), e.getY());


		   draggedFigure.moveBy(originalPt.x - e.getX(), originalPt.y - e.getY());
		   
		   if( isLocation(to) && isLocation(from) ){
			   if(game.move(from, to)){
				   
					Point pTo = Convert.locationAndCount2xy(to, (game.getCount(to) - 1));
					Point pFrom = Convert.locationAndCount2xy(from, game.getCount(from));
					draggedFigure.moveBy(pTo.x - pFrom.x, pTo.y - pFrom.y);
					
			   }
		   }
		   
	   } 
	   
	   fChild.mouseUp(e, x, y);
	   fChild = cachedNullTool;
	   draggedFigure = null;
	   
	 }
	  
	 private boolean isLocation(Location loc){
		 if(loc == null){
			 return false;
		 }
		 for(Location location : Location.values()){
			 if(loc.equals(location)){
				 return true;
			 }
		 }
		return false;
		 
	 }
	
	 /**
	  * Factory method to create a Drag tracker. It is used to drag a figure.
	  */
	 protected Tool createDragTracker(Figure f) {
	   return new DragTracker(editor, f);
	 }

	

}
