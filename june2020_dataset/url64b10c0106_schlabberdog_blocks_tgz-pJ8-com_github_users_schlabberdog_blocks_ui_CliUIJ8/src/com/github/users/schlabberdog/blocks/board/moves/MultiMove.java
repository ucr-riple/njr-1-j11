package com.github.users.schlabberdog.blocks.board.moves;

import com.github.users.schlabberdog.blocks.board.Block;
import com.github.users.schlabberdog.blocks.board.Board;
import com.github.users.schlabberdog.blocks.mccs.Coord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultiMove implements IMove {
    private ArrayList<SMove> moves = new ArrayList<SMove>();

    private static class SMove {
        Block block;
        int deltaX;
        int deltaY;

        private SMove(Block block, int deltaX, int deltaY) {
            this.block = block;
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }
    }

    public MultiMove() {
    }

    public MultiMove(Block block, int deltaX, int deltaY) {
        add(block, deltaX, deltaY);
    }

    public void add(Block block, int deltaX, int deltaY) {
        moves.add(new SMove(block,deltaX,deltaY));
    }

    @Override
    public void apply(Board board) {
	    //zuerst müssen wir die neuen koordinaten aller blocks berechnen
	    HashMap<Block,Coord> newMap = new HashMap<Block, Coord>();
	    for(SMove move : moves) {
		    Coord pos = board.getBlockCoord(move.block);
		    int newX = pos.x + move.deltaX;
		    int newY = pos.y + move.deltaY;

		    newMap.put(move.block,new Coord(newX,newY));
	    }
        //dann müssen alle blocks runter vom board, sonst könnten sie sich beim verschieben überlagern
        for (SMove move : moves) {
            board.removeBlock(move.block);
        }
        //dann nehmen wir sie mit korrekter position wieder auf
        for (Map.Entry<Block,Coord> e : newMap.entrySet()) {
            board.insertBlockAt(e.getKey(),e.getValue());
        }
    }

    @Override
    public IMove mergeWith(IMove nextMove) {
        //kommt in der lösung nicht vor...nicht implementiert
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Multi{");
        int i = 0;
        for (SMove move : moves) {
            if(i > 0)
                sb.append(", ");
            sb.append(move.block);
            sb.append(" -> V[");
            sb.append(move.deltaX);
            sb.append('|');
            sb.append(move.deltaY);
            sb.append(']');
            i++;
        }
        sb.append('}');
        return sb.toString();
    }
}
