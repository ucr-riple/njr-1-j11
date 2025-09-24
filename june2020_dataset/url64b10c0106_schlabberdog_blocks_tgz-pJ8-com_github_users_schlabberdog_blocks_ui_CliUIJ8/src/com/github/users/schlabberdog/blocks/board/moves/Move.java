package com.github.users.schlabberdog.blocks.board.moves;

import com.github.users.schlabberdog.blocks.board.Block;
import com.github.users.schlabberdog.blocks.board.Board;
import com.github.users.schlabberdog.blocks.mccs.Coord;

public class Move implements IMove {
    public final Block block;
    public final int deltaX;
    public final int deltaY;

    public final String debugMsg;

	public Move(Block block, int deltaX, int deltaY) {
		this(block, deltaX, deltaY, null);
	}

    public Move(Block block, int deltaX, int deltaY, String debugMsg) {
        this.block = block;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.debugMsg = debugMsg;
    }

    public void apply(Board board) {
	    Coord old = board.getBlockCoord(block);

        int newX = old.x + deltaX;
        int newY = old.y + deltaY;

	    //um nicht die gültigkeit des boards zu verlieren müssen wir den block
	    //entfernen, dann prüfen ob er sich an der neuen position platzieren lässt.
        board.removeBlock(block);
        board.insertBlockAt(block,newX,newY);
    }

    @Override
    public IMove mergeWith(IMove nextMove) {
        if(nextMove == this)
            throw new RuntimeException("Merge with myself! ("+this+")");

        if(nextMove instanceof Move) {
            Move next = (Move) nextMove;
            if(next.block == this.block) {
                //ein merge bei dem sich beide aufheben lohnt sich nicht
                if(next.deltaX+this.deltaX == 0 && next.deltaY+this.deltaY == 0)
                    return null;

                String dbg = "{Merging "+this+" with "+next+"}";
               // System.out.println(dbg);
                //da wir ja netterweise deltas (vektoren) haben können wir die einfach addieren (vektoraddition)
                return new Move(this.block,next.deltaX+this.deltaX,next.deltaY+this.deltaY, dbg);
            }
        }

        return null;
    }

    @Override
    public String toString() {
	    StringBuilder sb = new StringBuilder("Move{");
	    sb.append(block);
	    sb.append(": -> V[");
	    sb.append(deltaX);
	    sb.append('|');
	    sb.append(deltaY);
	    sb.append(']');
	    if(debugMsg != null) {
		    sb.append(" (Created on: ");
		    sb.append(debugMsg);
		    sb.append(')');
        }
	    sb.append('}');
		return sb.toString();
    }
}
