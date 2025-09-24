package frs.hotgammon.view.tools;

import java.awt.event.MouseEvent;
import java.util.HashMap;

import frs.hotgammon.framework.Game;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Tool;
import minidraw.standard.AbstractTool;

public class HotGammonTool extends AbstractTool{

	private Tool currentTool;
	final public static String DIE_ROLL_TOOL = "DIE_ROLL_TOOL";
	final public static String MOVE_TOOL = "MOVE_TOOL";
	final public static String GAME_OVER_TOOL = "GAME_OVER_TOOL";
	private HashMap<String, Tool> states;
		
	public HotGammonTool( DrawingEditor editor, Game game, String initialState, HashMap<String,Tool> states) {
		super(editor);		
		this.states = states;
		setState(initialState);
	}

	public void mouseUp(MouseEvent e, int x, int y) { 
		
	    this.currentTool.mouseUp(e,x,y);
	}
	
	public void mouseDrag(MouseEvent e, int x, int y) {

	    this.currentTool.mouseDrag(e,x,y);
	}
	
	public void mouseDown(MouseEvent e, int x, int y) {

	    this.currentTool.mouseDown(e,x,y);
	}
		
	public void setState(String toolKey){
		this.currentTool = states.get(toolKey);
	}
	
}
