package frs.hotgammon.view;

import java.awt.Point;

import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.standard.StandardDrawing;

import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Game;
import frs.hotgammon.framework.GameObserver;
import frs.hotgammon.framework.Location;
import frs.hotgammon.view.figures.CheckerFigure;
import frs.hotgammon.view.figures.DieFigure;
import frs.hotgammon.view.tools.HotGammonTool;

public class HotGammonDrawing extends StandardDrawing implements GameObserver {

	private DieFigure[] dice = new DieFigure[2];
	private int diceIdx = 0;
	Point[] diePoints = {new Point(216, 202), new Point(306, 202)};
	protected Game subject;
	protected DrawingEditor editor;

	public HotGammonDrawing(DrawingEditor editor){
		this.editor = editor;
	}

	public HotGammonDrawing(DrawingEditor editor, Game game) {
		this(editor);
		setGame(game);
	}

	public void setGame(Game subject){
		this.subject = subject;
	}


	private boolean isGameMotivatedMove(Location from, Location to){
		return (from.equals(Location.R_BEAR_OFF) || 
				from.equals(Location.B_BEAR_OFF) || 
				to.equals(Location.B_BAR) || 
				to.equals(Location.R_BAR)) 
				&& (isMove(from,to));
	}
	
	private boolean isMove(Location from, Location to){
		return !(from.equals(to));
	}
	
	@Override
	public void checkerMove(Location from, Location to) {


		if(isGameMotivatedMove(from, to)){
			
			Point pFrom = Convert.locationAndCount2xy(from, subject.getCount(from));
			Point pTo = Convert.locationAndCount2xy(to, subject.getCount(to) - 1);

			lock();

		    Figure clickedFig = findFigure(pFrom.x, pFrom.y);

		    unlock();

		    if(!isChecker(clickedFig)){
		    	Color color = subject.getColor(to);
		    	clickedFig = new CheckerFigure(color, pFrom);
		    	lock();
				add(clickedFig);	
				unlock();
		    }

		    lock();
			clickedFig.moveBy(pTo.x - pFrom.x, pTo.y - pFrom.y);
			unlock();

		}

		if(!(from.equals(Location.R_BEAR_OFF) || from.equals(Location.B_BEAR_OFF))  && this.subject.getNumberOfMovesLeft() == 0){
			((HotGammonTool) this.editor.tool()).setState(HotGammonTool.DIE_ROLL_TOOL);
		}
		
	}

	protected boolean isChecker(Figure fig){
		return (fig != null && fig instanceof CheckerFigure);
	}

	@Override
	public void diceRolled(int[] values) {
		for(int i = 0; i < values.length; i++){
			addDie(values[i]);
		}

		if((this.subject.getPlayerInTurn() == Color.NONE)){
			((HotGammonTool) this.editor.tool()).setState(HotGammonTool.DIE_ROLL_TOOL);
		}
		else{
			((HotGammonTool) this.editor.tool()).setState(HotGammonTool.MOVE_TOOL);
		}
	}

	public void addDie(int value) {
		int diceIdxVal = diceIdx % 2;

		if ( dice[diceIdxVal] == null ){
			dice[diceIdxVal] = new DieFigure(value, diePoints[diceIdxVal]);
			add(dice[diceIdxVal]);
		}
		else{
			dice[diceIdxVal].set("die" + value, diePoints[diceIdxVal]);
			dice[diceIdxVal].changed();
		}
		diceIdx++;
	}

	public void addChecker(Color color, Point pt){
		CheckerFigure cF = new CheckerFigure(color, pt);
		add(cF);
		cF.changed();
	}

	@Override
	public void setStatus(String status) {
		this.editor.showStatus(status);
	}

	@Override
	public void gameOver() {
		((HotGammonTool) this.editor.tool()).setState(HotGammonTool.GAME_OVER_TOOL);		
	}


}