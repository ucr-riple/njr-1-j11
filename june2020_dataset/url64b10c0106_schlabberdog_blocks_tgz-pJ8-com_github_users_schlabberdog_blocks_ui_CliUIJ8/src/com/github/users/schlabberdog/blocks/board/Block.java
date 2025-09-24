package com.github.users.schlabberdog.blocks.board;

import com.github.users.schlabberdog.blocks.board.moves.IMove;
import com.github.users.schlabberdog.blocks.board.moves.Move;
import com.github.users.schlabberdog.blocks.mccs.Coord;
import com.github.users.schlabberdog.blocks.mccs.Rect;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Stellt einen beliebig großen, RECHTECKIGEN Block dar.
 */
public abstract class Block {
    public final int width;
    public final int height;

    protected Block(int w,int h) {
        width = w;
        height = h;
    }

	public abstract Color getColor();

	public abstract char getRepresentation();

	public boolean coversArea(Coord myOrigin,Rect other) {
        for (Rect myRect : getRectSet(myOrigin)) {
            if(other.intersect(myRect) != null)
                return true;
        }
        return false;
    }

    public boolean coversArea(Coord myOrigin, Rect[] other) {
        for (Rect rect : other) {
            if(coversArea(myOrigin,rect))
                return true;
        }
        return false;
    }

    public Rect[] getRectSet(Coord origin) {
        return new Rect[]{
		        new Rect(origin.x, origin.y, width, height)
        };
    }

	/**
	 * Schreibt eine einfache grafische Repräsentation auf ein Board
	 * @param origin Die Ursprungsposition (oben links) des Blocks auf dem Board
	 * @param m Das Ausgabe-Board. Achtung: Die äußere Dimension ist y, die innere x!
	 */
    public void printOntoMap(Coord origin,char[][] m) {
        char c = getRepresentation();
        for (int x = origin.x; x <(origin.x+width); x++) {
            for (int y = origin.y; y < (origin.y+height); y++) {
                m[y][x] = c;
            }
        }
    }

    public Area drawShape(Coord myOrigin,double bw, double bh) {
        Area out = new Area();
        for (Rect rect : getRectSet(myOrigin)) {
            out.add(new Area(new Rectangle2D.Double((bw*rect.getX()+10),(bh*rect.getY()+10),(bw*rect.getWidth()-15),(bh*rect.getHeight()-15))));
        }
        return out;
    }

    /**
     * Weist einen Block an alle seine möglichen Züge auf ein Array zu schreiben
     * @param myOrigin Ursprungsposition dieses Blocks auf dem Board (von dort aus werden Alternativen gesucht)
     * @param board Board, auf dem sich der Block befindet
     * @param alts Array, dem die Alternativen hinzugefügt werden sollen
     */
    public void addAlts(Coord myOrigin,Board board, ArrayList<IMove> alts) {
        //um einen block nach links/rechts (oben/unten) zu verschieben muss jeweils links/rechts (oben/unten) davon eine Spalte (Zeile)
        //in Höhe (Breite) des Blocks frei sein.
		int y = myOrigin.y;
	    int x = myOrigin.x;
        //oben
        {
            if(y > 0 && !board.intersectsWithRect(new Rect(x,y-1,width,1)) )
                alts.add(new Move(this,0,-1,myOrigin+": Block Up"));
        }
        //links
        {
            if(x > 0 && !board.intersectsWithRect(new Rect(x-1,y,1,height)) )
                alts.add(new Move(this,-1,0,myOrigin+": Block Left"));
        }
        //rechts
        {
            if(x+width < board.width && !board.intersectsWithRect(new Rect(x+width,y,1,height)) )
                alts.add(new Move(this,1,0,myOrigin+": Block Right"));
        }
        //unten
        {
            if(y+height < board.height && !board.intersectsWithRect(new Rect(x,y+height,width,1)) )
                alts.add(new Move(this,0,1,myOrigin+": Block Down"));
        }
    }

}
