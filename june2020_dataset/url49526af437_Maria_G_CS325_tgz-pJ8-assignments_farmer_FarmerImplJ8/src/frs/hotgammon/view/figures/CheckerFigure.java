package frs.hotgammon.view.figures;

import java.awt.Point;
import frs.hotgammon.framework.Color;
import minidraw.standard.ImageFigure;

public class CheckerFigure extends ImageFigure{

	private Color color;
	
	public CheckerFigure(Color color, Point p){
		super(color.toString().toLowerCase() + "checker", p);
		this.color = color;
	}
	
	public Color getColor(){
		return color;
	}
}
